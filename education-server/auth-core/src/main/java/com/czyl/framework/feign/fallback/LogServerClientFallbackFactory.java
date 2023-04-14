package com.czyl.framework.feign.fallback;

import com.czyl.framework.feign.AuthServerClient;
import com.czyl.framework.feign.LogServerClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LogServerClientFallbackFactory implements FallbackFactory<LogServerClient>{

	@Override
	public LogServerClient create(Throwable cause) {
		log.error("feign异常",cause);
		return null;
	}

}
