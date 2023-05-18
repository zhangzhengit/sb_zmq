package com.vo.configuration;

import com.vo.netty.ZMQNettyServerHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 *
 * @author zhangzhen
 * @data Aug 10, 2020
 * 
 */
@Configuration
public class ZMQServerConfiguration {

	@Bean
	public ZMQNettyServerHandler zmsNettyServerHandler() {
		return new ZMQNettyServerHandler();
	}
}
