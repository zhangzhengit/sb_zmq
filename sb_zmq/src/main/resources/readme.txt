
1 sb_zmq mvn clean install -Dmaven.test.skip=true 

2 sb_zmq-1.0-SNAPSHOT.jar 同目录新建application.properties, 输入内容zmq.port=9998

3 java -jar sb_zmq-1.0-SNAPSHOT.jar  启动服务端

4 sb_zmq_client  mvn clean install -Dmaven.test.skip=true

5 新建maven项目,添加依赖	
		<dependency>
			<groupId>com.vo</groupId>
			<artifactId>sb_zmq_client</artifactId>
			<version>1.0-SNAPSHOT</version>
		</dependency>

6 配置 @Import(value = ZMConfiguration.class)
	application.properties 加入配置
						zmq.host=localhost
						zmq.port=9998
						
7 新建class 添加注解@ZMQComponent，声明方法，加上ZMConsumer注解。
		@ZMConsumer(topic = "z1")
		public void test1(final ZMP zmp) {
			System.out.println(Thread.currentThread().getName() + "\t" + "z1.zmp = " + zmp);
		}

8 @Autowired private ZMQClient zmqc; 使用zmqc.send来发送消息。		


