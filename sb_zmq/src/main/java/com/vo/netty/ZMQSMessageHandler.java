package com.vo.netty;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Maps;
import com.vo.dto.ZMPTypeEnum;
import com.vo.protobuf.ZMP;
import io.netty.channel.ChannelHandlerContext;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.DelayQueue;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 
 *
 * @author zhangzhen
 * @data Aug 10, 2020
 * 
 */
@Component
public class ZMQSMessageHandler {

	private final ConcurrentMap<String, BlockingQueue<ZMP>> topicMap = Maps.newConcurrentMap();
	private final Vector<String> tv = new Vector<>();

	public ConcurrentMap<String, BlockingQueue<ZMP>> getTopicMap() {
		return topicMap;
	}
	
	private void addZMP(final ZMP zmp) {
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
	
	@Async
	public ZMQSMRE handle(final ZMP zmp, final ChannelHandlerContext ctx) {
		final ZMPTypeEnum e = ZMPTypeEnum.valueOfType(zmp.getType());
		if (e == null) {
			System.err.println("zmp.type is null ,zmp = " + zmp);
			return ZMQSMRE.FAILED;
		}

		switch (e) {
		case INIT:
			ZMQSMessageHandler.init(zmp.getTopic(), ctx);
			return ZMQSMRE.SUCEESS;
		
		case MESSAGE:
			this.addZMP(zmp);
			return ZMQSMRE.SUCEESS;
			
		default:
			break;
		}

		return ZMQSMRE.SUCEESS;
	}

	private static void init(final String topic, final ChannelHandlerContext ctx) {
		ZMQSClientManager.put(topic, ctx);
	}

	private static void message(final ZMP zmp) {
		final String topic = zmp.getTopic();
		final ChannelHandlerContext ctx = ZMQSClientManager.getCTXByTopic(topic);
		if (ctx == null) {
			System.out.println("message.ctx is null,zmp.topic = " + topic);
			return;
		}

		ZMQSSender.send(zmp, ctx);
	}
}
