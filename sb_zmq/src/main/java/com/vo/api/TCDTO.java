package com.vo.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 *
 * @author zhangzhen
 * @data Aug 11, 2020
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TCDTO {

	private String topic;

	private int count;

}
