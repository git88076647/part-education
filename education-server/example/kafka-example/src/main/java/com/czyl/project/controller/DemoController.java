package com.czyl.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.czyl.framework.web.controller.BaseController;
import com.czyl.framework.web.domain.Logininfor;
import com.czyl.framework.web.domain.OperLog;

/**
 * 演示
 * 
 * @author tanghx
 */
@RestController
@RequestMapping("/test")
public class DemoController extends BaseController {

	@Autowired
	private KafkaTemplate<Object, Object> kafkaTemplate;

	@GetMapping("/send/{messge}")
	public String send(@PathVariable String messge) {
		for (int i = 0; i < 10; i++) {
			messge = messge + i;
			kafkaTemplate.send("topic1", "topci1:" + messge);
			kafkaTemplate.send("topic2", "topci2:" + messge);
			OperLog operlog = new OperLog();
			operlog.setBeginTime("2020-02-02");
			operlog.setBusinessType(1);
			operlog.setCreateBy(123L);
			operlog.setCreateByName(messge);
			kafkaTemplate.send("operlog", JSON.toJSONString(operlog));
			Logininfor loginfo = new Logininfor();
			loginfo.setBeginTime("2020-01-01");
			loginfo.setBrowser(messge);
			kafkaTemplate.send("logininfor", JSON.toJSONString(loginfo));
		}

		return messge;
	}

}
