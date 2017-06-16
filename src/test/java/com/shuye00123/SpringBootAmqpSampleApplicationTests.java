package com.shuye00123;

import com.shuye00123.producer.MessageProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootAmqpSampleApplicationTests {

	@Autowired
	private MessageProducer producer;

	@Test
	public void contextLoads() {
	}

	@Test
	public void ansyncProdTest() throws InterruptedException {
		for(int i = 0; i<100; i++){
			producer.sendMessage();
		}
	}

	@Test
	public void syncProdTest() throws InterruptedException {
		for(int i = 0; i<100; i++){
			producer.sendAndReceive();
		}
	}
}
