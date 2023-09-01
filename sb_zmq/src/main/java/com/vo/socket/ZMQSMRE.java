package com.vo.socket;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *  
 *
 * @author zhangzhen
 * @data Aug 10, 2020
 * 
 */
@Getter
@AllArgsConstructor
public enum ZMQSMRE {

	SUCEESS(1),
	
	FAILED(2),
	
	;
	
	private int value;
	
}
