package com.vo.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.DelayQueue;

import com.google.common.collect.Maps;
import com.vo.anno.ZComponent;
import com.vo.configuration.ZMQServerProperties;
import com.vo.core.ZContext;
import com.vo.core.ZLog2;
import com.vo.dto.ZMPTypeEnum;
import com.vo.protobuf.ZMP;
import com.votool.common.ZPU;
import com.votool.ze.ZE;
import com.votool.ze.ZES;

import cn.hutool.core.collection.CollUtil;
import io.netty.buffer.Unpooled;

/**
 * ZMP
 *
 * @author zhangzhen
 * @date 2023年9月1日
 *
 */
@ZComponent
public class ZMQServer {

	private static final int LENGHT_ = 4;

	private static final ZLog2 LOG = ZLog2.getInstance();

	private static final int BUFFER_SIZE = 1024 * 100;

	private static final ZE ZE = ZES.newZE(20);

	public void start() {
		final ZMQServerProperties properties = ZContext.getBean(ZMQServerProperties.class);
		LOG.trace("ZMQServer开始启动,serverPort={}", properties.getPort());

//		System.out.println("mq = " + this.mq);
//		this.mq.start();
//		this.mq.writeDBJob();

		// 创建ServerSocketChannel
		Selector selector = null;
		ServerSocketChannel serverSocketChannel;
		try {
			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.configureBlocking(false);
			serverSocketChannel.bind(new InetSocketAddress(properties.getPort()));

			// 创建Selector
			selector = Selector.open();
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		} catch (final IOException e) {
			e.printStackTrace();
		}

		LOG.trace("ZMQServer启动成功，等待连接,serverPort={}", properties.getPort());

		while (true) {
			try {
				selector.select();
			} catch (final IOException e) {
				e.printStackTrace();
			}

			final Set<SelectionKey> selectedKeys = selector.selectedKeys();
			final Iterator<SelectionKey> iterator = selectedKeys.iterator();

			while (iterator.hasNext()) {
				final SelectionKey key = iterator.next();
				iterator.remove();

				if (!key.isValid()) {
					continue;
				}

				if (key.isAcceptable()) {
					ZMQServer.handleAccept(key, selector);
				} else if (key.isReadable()) {
					final ZMP message = ZMQServer.handleRead(key);
					if (message != null) {
						System.out.println("readable-message = " + message);
//						MQThread.SOCKET_MAP.put(message.getId(), (SocketChannel) key.channel());
//						this.mq.add(message);

						final SocketChannel socketChannel = (SocketChannel) key.channel();
						hZMP(message, socketChannel);
					}
				}
			}
		}

	}

	private static void hZMP(final ZMP message, final SocketChannel socketChannel) {

		final ZMPTypeEnum typeEnum = ZMPTypeEnum.valueOfType(message.getType());
		switch (typeEnum) {
		case INIT:
			LOG.info("INIT消息，初始化消费者,topic={}", message.getTopic());
			ZMQSClientManager.put(message.getTopic(), socketChannel);

			final ZMP initR = new ZMP();
			initR.setId(message.getId());
			initR.setType(ZMPTypeEnum.INIT_OK.getType());
			initR.setContent("OK:ZMQServer收到INIT消息并初始化成功");

			final byte[] wan = ZPU.wanzhangbytearray(ZPU.serialize(initR));
			final ByteBuffer byteBuffer = ByteBuffer.wrap(wan);
			while (byteBuffer.hasRemaining()) {
				try {
					socketChannel.write(byteBuffer);
				} catch (final IOException e) {
					// FIXME 2023年9月1日 下午8:04:48 zhanghen: 发送失败，怎么处理？重连？
					e.printStackTrace();
				}
			}

			break;

		case MESSAGE:
			addZMP(message);
			n++;
			System.out.println("n-server-message-n = " + n);

			break;

		default:
			break;
		}

	}
	static int n = 0;

	private  static final ConcurrentMap<String, BlockingQueue<ZMP>> topicMap = Maps.newConcurrentMap();
	private static final Vector<String> tv = new Vector<>();

	private static void addZMP(final ZMP zmp) {
		zmp.setScheduledMilliSeconds(zmp.getDelayMilliSeconds() + System.currentTimeMillis());
		final String topic = zmp.getTopic();
		final BlockingQueue<ZMP> zmqQueue = topicMap.get(topic);
		if (CollUtil.isEmpty(zmqQueue)) {
			final BlockingQueue<ZMP> queue = new DelayQueue<>();
			queue.add(zmp);
			topicMap.put(topic, queue);
			tv.add(topic);
		} else {
			zmqQueue.add(zmp);
		}

		message(zmp);
	}

	static int sendN = 0;
	private static void message(final ZMP message) {

		final SocketChannel socketChannel = ZMQSClientManager.getSocketChannelByTopic(message.getTopic());
		if (socketChannel == null) {
			LOG.warn("topic的消费者不在线,topic={}", message.getTopic());
			return;
		}

		final byte[] wan = ZPU.wanzhangbytearray(ZPU.serialize(message));
		final ByteBuffer byteBuffer = ByteBuffer.wrap(wan);
		while (byteBuffer.hasRemaining()) {
			try {
				socketChannel.write(byteBuffer);
			} catch (final IOException e) {
				// FIXME 2023年9月1日 下午8:04:48 zhanghen: 发送失败，怎么处理？重连？
				e.printStackTrace();
			}
		}
		sendN++;
		System.out.println("server-message-sendN = " +sendN);

//		final String topic = zmp.getTopic();
//		final ChannelHandlerContext ctx = ZMQSClientManager.getCTXByTopic(topic);
//		if (ctx == null) {
//			System.out.println("message.ctx is null,zmp.topic = " + topic);
//			return;
//		}

//		ZMQSSender.send(zmp, ctx);
	}

	private static ZMP handleRead(final SelectionKey key) {
//		System.out.println(java.time.LocalDateTime.now() + "\t" + Thread.currentThread().getName() + "\t"
//				+ "ImServer.handleRead()");

		final SocketChannel socketChannel = (SocketChannel) key.channel();
		if (!socketChannel.isOpen()) {
			return null;
		}

		// 2 读4字节，读长度的数据
		final ByteBuffer lengthBuffer = ByteBuffer.allocate(LENGHT_);
		try {
			final int length = socketChannel.read(lengthBuffer);
			if (length == -1) {
				try {
					socketChannel.close();
					key.cancel();
//				System.out.println("bytesRead == -1 | socketChannel.close();");
				} catch (final IOException e) {
					e.printStackTrace();
				}
				return null;
			}
//			System.out.println("length = " + length);

			if (length == LENGHT_) {
				final byte[] lengthArray = lengthBuffer.array();
				final int dataLength = ZPU.byteArrayToInt(lengthArray);
//				System.out.println("dataLength = " + dataLength);

				if (dataLength > 0) {

					final ByteBuffer dataBuffer = ByteBuffer.allocate(dataLength);
					socketChannel.read(dataBuffer);
					final byte[] dataArray = dataBuffer.array();
					System.out.println("dataArray = " + Arrays.toString(dataArray));

					final ZMP message = ZPU.deserialize(dataArray, ZMP.class);
					System.out.println("message = " + message);

//					message.setSocketChannel(socketChannel);

					return message;
				}
			}

		} catch (final IOException e1) {
			e1.printStackTrace();
			try {
				socketChannel.close();
				key.cancel();
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private static void handleAccept(final SelectionKey key, final Selector selector) {
		final ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
		SocketChannel socketChannel = null;
		try {
			socketChannel = serverSocketChannel.accept();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		try {
			socketChannel.configureBlocking(false);
		} catch (final IOException e) {
			e.printStackTrace();
		}
		try {
			socketChannel.register(selector, SelectionKey.OP_READ);
		} catch (final ClosedChannelException e) {
			e.printStackTrace();
		}
		try {
			System.out.println("新连接： " + socketChannel.getRemoteAddress());
		} catch (final IOException e) {
			e.printStackTrace();
		}

	}
}
