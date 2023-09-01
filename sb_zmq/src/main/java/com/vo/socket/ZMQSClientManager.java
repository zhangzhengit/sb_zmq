package com.vo.socket;

import java.nio.channels.SocketChannel;
import java.util.concurrent.ConcurrentMap;

import com.google.common.collect.Maps;
import com.vo.core.ZLog2;

import io.netty.channel.ChannelHandlerContext;

/**
 *
 *
 * @author zhangzhen
 * @data Aug 10, 2020
 *
 */
public class ZMQSClientManager {

	private static final ZLog2 LOG = ZLog2.getInstance();

	private final static ConcurrentMap<String, SocketChannel> CLIENT_MAP = Maps.newConcurrentMap();
// FIXME 2023年9月1日 下午8:03:08 zhanghen: 改为多值map，因为一个topic会有多个消费者
	public static void put(final String topic, final SocketChannel socketChannel) {
		CLIENT_MAP.put(topic, socketChannel);
	}

	public static SocketChannel getSocketChannelByTopic(final String topic) {
		return CLIENT_MAP.get(topic);
	}

}
