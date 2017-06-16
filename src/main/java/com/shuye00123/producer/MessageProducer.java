package com.shuye00123.producer;

import com.shuye00123.domain.SampleMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by shuye on 2017/6/16.
 */
@Component
public class MessageProducer {
    @Autowired
    private AmqpTemplate amqpTemplate;
    private static Lock lock = new ReentrantLock();
    private int i = 0;
    private static Log log = LogFactory.getLog(MessageProducer.class);
    public synchronized void sendMessage() throws InterruptedException {
        SampleMessage sampleMessage = new SampleMessage(i, "hello world");
        amqpTemplate.convertAndSend("sample",sampleMessage);
        i++;
    }

    public void sendAndReceive(){
        lock.lock();
        try {
            SampleMessage sampleMessage = new SampleMessage(i, "hello world");
            SampleMessage o = (SampleMessage) amqpTemplate.convertSendAndReceive("sample1",sampleMessage);
            i++;
            log.info(o.getId()+", "+ o.getMessage());
        }finally {
            lock.unlock();
        }
    }
}
