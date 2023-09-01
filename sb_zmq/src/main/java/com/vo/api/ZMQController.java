package com.vo.api;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.google.common.collect.Lists;
import com.vo.anno.ZController;
import com.vo.protobuf.ZMP;
import com.vo.socket.ZMQSMessageHandler;

/**
 *
 * API
 *
 * @author zhangzhen
 * @data Aug 11, 2020
 *
 */
@ZController
public class ZMQController {

	@Autowired
	private ZMQSMessageHandler zmqsMessageHandler;

	// FIXME 2023年9月1日 下午8:55:04 zhanghen: 继续改用 ZF该这个方法
	@GetMapping(value = "/")
	public String index(final Model model) {
		System.out.println(
				Thread.currentThread().getName() + "\t" + LocalDateTime.now() + "\t" + "ZMQController.index()");
		System.out.println();

		final ConcurrentMap<String, BlockingQueue<ZMP>> tm = this.zmqsMessageHandler.getTopicMap();
		model.addAttribute("name", "zhangsan");

		final Set<String> keySet = tm.keySet();
		final List<TCDTO> tcList = Lists.newArrayList();
		for (final String t : keySet) {
			final TCDTO tcdto = TCDTO.builder()
					.topic(t)
					.count(tm.get(t).size())
					.build();
			tcList.add(tcdto);
		}

		model.addAttribute("tcList", tcList);

		return "index";
	}

}
