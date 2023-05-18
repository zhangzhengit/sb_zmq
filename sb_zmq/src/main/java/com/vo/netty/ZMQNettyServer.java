package com.vo.netty;

import com.vo.configuration.ZMQServerProperties;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 
 *
 * @author zhangzhen
 * @data Aug 7, 2020
 * 
 */
@Order(value = 1)
@Component
public class ZMQNettyServer implements ApplicationRunner, DisposableBean {

	@Autowired
	private ZMQServerProperties zmsp;
	@Autowired
	private ZMQNettyServerHandler zmsNettyServerHandler;
	
	private EventLoopGroup boss;
	private EventLoopGroup worker;

	@Override
	public void run(final ApplicationArguments args) throws Exception {
		System.out
				.println(Thread.currentThread().getName() + "\t" + LocalDateTime.now() + "\t" + "ZMSNettyServer.run()");
		System.out.println();

		final ServerBootstrap b = new ServerBootstrap();
		boss = new NioEventLoopGroup();
		worker = new NioEventLoopGroup();

		b.group(boss, worker);
		b.channel(NioServerSocketChannel.class);
		b.option(ChannelOption.SO_BACKLOG, 20000);
		b.option(ChannelOption.TCP_NODELAY, true);
		b.option(ChannelOption.SO_REUSEADDR, true);
		b.option(ChannelOption.SO_KEEPALIVE, true);
		b.localAddress(new InetSocketAddress(this.zmsp.getPort()));
		b.childHandler(channelInitializer(this.zmsNettyServerHandler));

		final ChannelFuture channelFuture = b.bind().sync();
		System.out.println("ZMSNettyServer.bind.port = " + this.zmsp.getPort());
		channelFuture.channel().closeFuture().sync();
	}

	private static ChannelInitializer<SocketChannel> channelInitializer(final ZMQNettyServerHandler zmsNettyServerHandler) {
		return new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(final SocketChannel ch) throws Exception {
				final ChannelPipeline p = ch.pipeline();
				p.addLast(new IdleStateHandler(2, 2, 2, TimeUnit.SECONDS));
				p.addLast(new ZMQZMPDecoder());
				p.addLast(zmsNettyServerHandler);
			}
		};
	}

	@Override
	public void destroy() throws Exception {
		System.out.println(
				Thread.currentThread().getName() + "\t" + LocalDateTime.now() + "\t" + "ZMSNettyServer.destroy()");
		System.out.println();
		
		boss.shutdownGracefully().sync();
		worker.shutdownGracefully().sync();
		
		System.out.println("ZMS-boss-worder.shutdownGracefully");
	}

}
