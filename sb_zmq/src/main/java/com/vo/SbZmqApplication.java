package com.vo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * MQ
 *
 * @author zhangzhen
 * @data Aug 7, 2020
 *
 */
@EnableAsync
@SpringBootApplication
public class SbZmqApplication {

	public static void main(final String[] args) {
		SpringApplication.run(SbZmqApplication.class, args);
	}

}
