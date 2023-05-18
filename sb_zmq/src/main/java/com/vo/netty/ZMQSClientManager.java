package com.vo.netty;

import com.google.common.collect.Maps;
import io.netty.channel.ChannelHandlerContext;
import java.util.concurrent.ConcurrentMap;

/**
 * 
 *
 * @author zhangzhen
 * @data Aug 10, 2020
 * 
 */
public class ZMQSClientManager {

	private final static ConcurrentMap<String, ChannelHandlerContext> CLIENT_MAP = Maps.newConcurrentMap();

	public static void put(final String topic, final ChannelHandlerContext ctx) {
		CLIENT_MAP.put(topic, ctx);
	}
	
	public static ChannelHandlerContext getCTXByTopic(final String topic) {
		return CLIENT_MAP.get(topic);
	}

}
