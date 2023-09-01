package com.vo;

import com.vo.core.ZContext;
import com.vo.socket.ZMQServer;

/**
 * MQ 启动类
 *
 * @author zhangzhen
 * @data Aug 7, 2020
 *
 */
public class SbZmqApplication {

	public static void main(final String[] args) {
		ZApplication.run("com.vo", args);

		final ZMQServer server = new ZMQServer();
		ZContext.addBean(ZMQServer.class, server);

		server.start();

	}

}
