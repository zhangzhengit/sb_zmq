package com.vo.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.vo.anno.ZConfiguration;
import com.vo.anno.ZConfigurationProperties;
import com.vo.validator.ZNotNull;

/**
 *
 *
 * @author zhangzhen
 * @data Aug 7, 2020
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ZConfiguration
//@ConfigurationProperties(prefix = "zmq")
@ZConfigurationProperties(prefix = "zmq.server")
public class ZMQServerProperties {

	@ZNotNull
	private Integer port;

}
