package com.vo.netty;

import com.vo.protobuf.ZMP;
import com.vo.protobuf.ZPU;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

/**
 * 
 *
 * @author zhangzhen
 * @data Aug 10, 2020
 * 
 */
public class ZMQSSender {

	public static void send(final ZMP zmp, final ChannelHandlerContext ctx) {
		try {
			final byte[] wan = ZPU.wanzhangbytearray(ZPU.serialize(zmp));
			ctx.writeAndFlush(Unpooled.copiedBuffer(wan));
		} catch (final Exception e) {
			e.printStackTrace();
			System.err.println("ZMQSSender.send异常, e = " + e);
		}
	}
}
