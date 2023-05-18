package com.vo;

import cn.hutool.core.collection.CollUtil;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
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
public class ZhfmTest1 {

	public static void main(final String[] args) {
		test_1();
	}

	public static void test_1() {
		System.out.println(Thread.currentThread().getName() + "\t" + LocalDateTime.now() + "\t" + "ZhfmTest1.test_1()");
		System.out.println();

		final String string = " 全部推荐项目\n" + 
				" 程序开发\n" + 
				" 人工智能\n" + 
				" 区块链\n" + 
				" 微信开发\n" + 
				" 企业应用\n" + 
				" 建站系统\n" + 
				" WEB应用开发\n" + 
				" 应用工具\n" + 
				" 手机/移动开发\n" + 
				" DevOps/运维/网管\n" + 
				" 开发工具\n" + 
				" 服务器应用\n" + 
				" 数据库相关\n" + 
				" 游戏/娱乐\n" + 
				" 插件和扩展\n" + 
				" 其他开源\n" + 
				" \n" + 
				"程序开发\n" + 
				" \n" + 
				"12 mirrors 1578989292	\n" + 
				"码云极速下载/php-msf  PHP 微服务 GPL-2.0\n" + 
				" 32  85  25\n" + 
				"PHP微服务框架即“Micro Service Framework For PHP”，是Camera360社区服务器端团队基于Swoole自主研发现代化的PHP协程服务框架，简称m\n" + 
				"17小时前 \n" + 
				"995430 crossoverjie 1578937772	\n" + 
				"crossoverJie/SSM   Java 微服务 GPL-2.0\n" + 
				" 23  85  28\n" + 
				":two_hearts: build SSM from 0 distributed micro service.\n" + 
				"2年前 \n" + 
				"1335525 crab2died 1578949193	\n" + 
				"Crab2Died/log4j-redis-appender   Java 日志工具包 Apache-2.0\n" + 
				" 41  85  24\n" + 
				"log4j日志写入redis扩展，为logstash日志收集做缓存中间件\n" + 
				"2年前 \n" + 
				"832 duguying2008 1578913886	\n" + 
				"独孤影/pinyin   Java 常用工具包\n" + 
				" 37  84  19\n" + 
				"汉字翻译为拼音，支持多音字，支持标点模式，无标点模式，首字母模式，干翻pinyin4j\n" + 
				"1年前  1 issue\n" + 
				"333863 cheergoal 1578920372	\n" + 
				"JieWeifu/BarcodeScanner   JavaScript 条形码/二维码 PhoneGap/Cordova 插件\n" + 
				" 29  84  24\n" + 
				"Cordova Barcode Scanner\n" + 
				"4年前  1 issue\n" + 
				"HCView-HCView	\n" + 
				"HCView/HCView-CSharp   C# 桌面UI组件/框架\n" + 
				" 24  84  36\n" + 
				"类似word或wps用于文字排版相关功能的控件 可用于电子病历编辑器等文本处理场景 HCView代码遵循BSD协议\n" + 
				"9天前  1 issue\n" + 
				"103896 cnldw 1578917634	\n" + 
				"幻舞奇影/APITools   Java 网络工具 Apache-2.0\n" + 
				" 43  84  40\n" + 
				"APITools-HTTP接口调用工具\n" + 
				"2年多前  V1.9.0版本发布 更新于 7月16日 \n" + 
				"LunarSF-lunarsf	\n" + 
				"LunarSF/Lunar Markdown Editor   C# Markdown开发包 文本编辑 MIT\n" + 
				" 30  84  20\n" + 
				"Markdown to CHM editor. Can't compile, but can create CHM Project files. 最新试验版下载地址：\n" + 
				"20天前  功能更新与Bug修复 更新于 5月09日  2 issues\n" + 
				"leeyazhou-leeyazhou	\n" + 
				"leeyazhou/flower   Java 微服务 Apache-2.0\n" + 
				" 24  84  27\n" + 
				"流式微服务框架Flower\n" + 
				"5个月前 \n" + 
				"强子1985-qiangzigege	\n" + 
				"强子1985/MyFlowEngine  Java 流程引擎/工具\n" + 
				" 20  84  26\n" + 
				"自研的流程引擎，非常轻量级,为互联网企业打造\n" + 
				"1年前 \n" + 
				"1480132 china wangyu 1578952479	\n" + 
				"嗝嗝/pay   PHP 支付开发包 MIT\n" + 
				" 35  83  7\n" + 
				"本项目来自https://github.com/yansongda\n" + 
				"1年多前 \n" + 
				"142518 winfed 1578919331	\n" + 
				"winfed/smart-alipay   Java 支付开发包\n" + 
				" 26  83  28\n" + 
				"支付宝支付工具类\n" + 
				"11个月前 \n" + 
				"小而强大-penghongbin	\n" + 
				"小而强大/Duck   C++ 桌面UI组件/框架\n" + 
				" 40  83  29\n" + 
				"Duck 基于Qt的插件式开发框架，他是一个简易的插件框架，所有业务由插件处理，与主框架无关，也可以把他称为程序的集合。由于要使用此框架开发个人项目，所以贡献出来让大家参考学习\n" + 
				"接近2年前 \n" + 
				"2438 ufo5260987423 1578914119	\n" + 
				"王政/memcached.distributedSession   Java 网络开发包 Apache-2.0\n" + 
				" 43  83  35\n" + 
				"基于MemCached的session共享插件，可以使得应用服务器集群通过Memcached集群共享session。目前支持Tomcat6以上版本。\n" + 
				"5年多前 \n" + 
				"487296 diaodiaofly 1578925397	\n" + 
				"初学者/net-client  Java 网络工具 BSD-3-Clause\n" + 
				" 23  83  26\n" + 
				"net-client是一个穿透内网的工具，将外部的请求转发到本地 可以很轻易的调试微信、支付宝等相关接口回调。同时也支持本地web应该发布到外网访问\n" + 
				"3个月前 \n" + 
				"            \n" + 
				" \n" + 
				"";
		final int length = string.getBytes().length;
		System.out.println("before.encode.length = " + length);
		System.out.println(string);
		final char[] ca = string.toCharArray();
		System.out.println(ca.length);
		System.out.println(Arrays.toString(ca));

		System.out.println("------------------pinlv-------------------");
		final Map<Character, Integer> map = pl(string);
		System.out.println(map);

		final Set<Entry<Character, Integer>> es = map.entrySet();
		final List<Pinlv> orderbyCountList = es.parallelStream().map(e -> new Pinlv(e.getKey(), e.getValue()))
				.sorted(Comparator.comparing(Pinlv::getCount).reversed()).collect(Collectors.toList());

		System.out.println("orderbyCountList.size = " + orderbyCountList.size());
		System.out.println(orderbyCountList);

		
		System.out.println("------------------bt2-------------------");
		final Stack<Pinlv> stack = new Stack<>();
		stack.addAll(orderbyCountList);
		System.out.println("stack = " + stack);
		final AtomicReference<PN> pnr = new AtomicReference<>(new PN());
		bt2(stack, pnr);
		System.out.println("pnr.get = " + pnr.get());
		System.out.println();
		System.out.println("------------------en3-------------------");
		final Map<Character, String> enMap = en3(pnr.get());
		System.out.println("enMap.size = " + enMap.size());
		System.out.println(enMap);
		
		System.out.println("------------------hfmE-------------------");
		hfmE(enMap, string);
	}

	public static void hfmE(final Map<Character, String> enMap, final String string) {
		final StringBuilder builder = new StringBuilder();
		final char[] ca = string.toCharArray();
		for (final char c : ca) {
			final String en = enMap.get(c);
			builder.append(en);
		}
		System.out.println("en = " + builder);
		System.out.println("en.length = " + builder.length());
		System.out.println("after.encode.length =  " + builder.length() / Byte.SIZE);

		final int r = 7;
		final int x = builder.length();
		if (x <= r) {
			System.out.println("builer = " +builder);
			System.out.println("B = " + Byte.parseByte(builder.toString(),2));
			return;
		}
		int i = 0;
		for (; i <= x - r; i += r) {
			final String ss = builder.substring(i,i + r);
			System.out.println(ss);
		}
		System.out.println("i  = " + i);
		System.out.println("i - r  = " + (i - r));
		System.out.println("x  = " + i);
	
	}
	
	public static Map<Character, String> en3(final PN pn) {
		
		final Map<Character, String> map = new HashMap<>();
		
		final Stack<PN> pnStack = new Stack<>();
		pnStack.push(pn);
		while (!pnStack.isEmpty()) {
			final PN cpn = pnStack.pop();
			if (cpn.getLeft() != null) {
				pnStack.push(cpn.getLeft());
			}

			if (cpn.getRight() != null) {
				pnStack.push(cpn.getRight());
			}

			if (cpn.getValue() != null) {
				final StringBuilder builder = new StringBuilder();
				PN ccpn = cpn;
				while (true) {
					if (ccpn.getParent() == null) {
						break;
					}
					if (ccpn.getParent().getLeft() == ccpn) {
						builder.insert(0, '0');
					} else {
						builder.insert(0, '1');
					}
					ccpn = ccpn.getParent();
				}
//				System.out.println("ch = " + cpn.getValue() + "\t" + "code = "+ builder);
				map.put(cpn.getValue(), builder.toString());
			}
		}

		return map;
	}

	public static void bt2(final Stack<Pinlv> stack, final AtomicReference<PN> pnr) {
		if (CollUtil.isEmpty(stack)) {
			return;
		}

		final Pinlv cp = stack.pop();
		final PN cpn = pnr.get();

		if (cpn.getW() <= 0) {
			cpn.setValue(cp.getCh());
			cpn.setW(cp.getCount());
		} else {
			final PN newpn = new PN(cp.getCh(), cp.getCount(), null, null, null);
			final PN left = cpn.getW() <= newpn.getW() ? cpn : newpn;
			final PN right = cpn.getW() <= newpn.getW() ? newpn : cpn;
			final PN newRPN = new PN(null, left.getW() + right.getW(), null, left, right);
			right.setParent(newRPN);
			left.setParent(newRPN);

			pnr.set(newRPN);
		}

		bt2(stack, pnr);
	}
	
	public static Map<Character, Integer> pl(final String string) {
		final Map<Character, Integer> map = new HashMap<>();
		final char[] ca = string.toCharArray();
		for (final char c : ca) {
			Integer count = map.get(c);
			count = count == null ? 1 : count + 1;
			map.put(c, count);
		}

		return map;
	}

	@Data
	@AllArgsConstructor
	@Builder
	@NoArgsConstructor
	public static class PN {
		private Character value;
		private int w;
		private PN parent;
		private PN left;
		private PN right;

		@Override
		public String toString() {
			return "PN [value=" + value + ", w=" + w + ", left=" + left.getValue() + ", right=" + right.getValue()
					+ "]";
		}
	}

	@Data
	@AllArgsConstructor
	@Builder
	@NoArgsConstructor
	public static class Pinlv {
		private Character ch;
		private Integer count;

		@Override
		public String toString() {
			return ch + "=" + count;
		}
	}

}
