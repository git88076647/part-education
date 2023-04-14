package com.czyl.project.mq.listener;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class SimpleListener {

	static AtomicInteger count=new AtomicInteger(0);
    @KafkaListener(topics = {"topic1", "topic2"})
    public void listen1(String data) {
    	long id = Thread.currentThread().getId();
        System.out.println(id+":" +data);
        
    }
    
    @KafkaListener(topics = {"topic1", "topic2"})
    public void listen1(List<ConsumerRecord> consumerRecords,Acknowledgment ack) {
    	long id = Thread.currentThread().getId();
    	int temp=count.addAndGet(1);
    	if(temp%2 == 0) {
    		//手动提交
    		ack.acknowledge();
    		System.out.println(id+"提交");
    	}else {
    		System.out.println(id+"放弃提交");
    	}
    	if(consumerRecords.size() > 0) {
    		for (int i = 0,len=consumerRecords.size(); i < len; i++) {
    			System.out.println(id+"值:"+consumerRecords.get(i).value());
			}
    	}
    	
    }
}