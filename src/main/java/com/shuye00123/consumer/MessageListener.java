package com.shuye00123.consumer;

import com.shuye00123.domain.SampleMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.ReceiveAndReplyCallback;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by shuye on 2017/6/16.
 */
@Component
@EnableScheduling
public class MessageListener {
    @Autowired
    private AmqpTemplate amqpTemplate;
    private static Lock lock = new ReentrantLock();
    private static Log log = LogFactory.getLog(MessageListener.class);

    @RabbitListener(queues = "sample")
    public void messageHandle(SampleMessage sampleMessage){
        System.out.printf("%s,%s\n", sampleMessage.getId(), sampleMessage.getMessage());
    }

    @Scheduled(fixedDelay = 1)
    public void receiveAndReply(){
        lock.lock();
        try {
            amqpTemplate.receiveAndReply("sample1", (ReceiveAndReplyCallback<SampleMessage, SampleMessage>) message -> {
                log.info(message.getId() + ", " + message.getMessage());
                message.setMessage("hello!");
                return message;
            });
        }finally {
            lock.unlock();
        }
    }
}
