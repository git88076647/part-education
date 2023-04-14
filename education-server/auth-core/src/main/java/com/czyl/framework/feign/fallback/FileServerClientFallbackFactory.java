package com.czyl.framework.feign.fallback;

import com.czyl.framework.feign.FileServerClient;
import com.czyl.framework.feign.LogServerClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FileServerClientFallbackFactory implements FallbackFactory<FileServerClient>{

	@Override
	public FileServerClient create(Throwable cause) {
		log.error("feign异常",cause);
		return null;
	}

}
