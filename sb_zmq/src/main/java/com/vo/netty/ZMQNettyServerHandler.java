package com.vo.netty;

import io.netty.channel.ChannelHandler.Sharable;
import java.util.concurrent.atomic.AtomicInteger;
import com.vo.protobuf.ZMP;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 *
 * @author zhangzhen
 * @data Aug 10, 2020
 * 
 */
@Sharable
public class ZMQNettyServerHandler extends ChannelInboundHandlerAdapter {

	@Autowired
	private ZMQSMessageHandler zmsMessageHandler;
	
	@Override
	public void channelActive(final ChannelHandlerContext ctx) throws Exception {
//		System.out.println(Thread.currentThread().getName() + "\t" + LocalDateTime.now() + "\t"
//				+ "ZMSNettyServerHandler.channelActive()");
//		System.out.println();
	}

	AtomicInteger xxx = new AtomicInteger();
	@Override
	public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {
//		System.out.println(Thread.currentThread().getName() + "\t" + LocalDateTime.now() + "\t"
//				+ "ZMSNettyServerHandler.channelRead()");
//		System.out.println();
		
		if (msg instanceof ZMP) {
			final ZMP zmp = (ZMP) msg;
			final int x = xxx.incrementAndGet();
//			System.out.println("server.zmp.cishu = " + x);
//			System.out.println("zmp.message = " + zmp.getContent());
			this.zmsMessageHandler.handle(zmp, ctx);

		} else {

		}

	}

}
