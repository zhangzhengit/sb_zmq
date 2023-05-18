package com.vo.api;

import com.google.common.collect.Lists;
import com.vo.netty.ZMQSMessageHandler;
import com.vo.protobuf.ZMP;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * API
 *
 * @author zhangzhen
 * @data Aug 11, 2020
 *
 */
@Controller
public class ZMQController {

	@Autowired
	private ZMQSMessageHandler zmqsMessageHandler;

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
