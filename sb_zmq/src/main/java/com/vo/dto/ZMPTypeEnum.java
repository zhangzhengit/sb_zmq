package com.vo.dto;

import com.google.common.collect.Maps;
import java.util.HashMap;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 *
 * @author zhangzhen
 * @data Aug 7, 2020
 * 
 */
@Getter
@AllArgsConstructor
public enum ZMPTypeEnum {

	INIT(1, "init"),

	MESSAGE(2, "message"),

	;

	static HashMap<Integer, ZMPTypeEnum> newHashMap = Maps.newHashMap();
	
	static {
		final ZMPTypeEnum[] vs = values();
		for (final ZMPTypeEnum zmpTypeEnum : vs) {
			newHashMap.put(zmpTypeEnum.getType(), zmpTypeEnum);

		}
	}
	
	public static ZMPTypeEnum valueOfType(final Integer type) {
		return newHashMap.get(type);
	}

	private Integer type;
	private String desc;
}
