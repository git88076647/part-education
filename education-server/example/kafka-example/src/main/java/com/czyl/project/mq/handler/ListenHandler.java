//package com.czyl.project.mq.handler;
//
//import org.springframework.kafka.annotation.KafkaHandler;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.support.KafkaHeaders;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.stereotype.Component;
//
//import com.czyl.framework.web.domain.Logininfor;
//import com.czyl.framework.web.domain.OperLog;
//
//@Component
//@KafkaListener(id = "handler", topics = {"operlog", "logininfor"})
//public class ListenHandler {
//
//    @KafkaHandler
//    public void foo(@Payload OperLog log, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
//        System.out.println("key:" + key);
//        System.out.println("OperLog:" + log.toString());
//    }
// 
//    @KafkaHandler
//    public void foo(Logininfor log) {
//        System.out.println("Logininfor:" + log.toString());
//    }
//}