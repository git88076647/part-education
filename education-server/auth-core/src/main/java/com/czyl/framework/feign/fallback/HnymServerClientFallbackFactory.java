package com.czyl.framework.feign.fallback;

import com.czyl.framework.feign.HnymServerClient;
import com.czyl.framework.feign.dto.OrgDto;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class HnymServerClientFallbackFactory implements FallbackFactory<HnymServerClient> {

	@Override
	public HnymServerClient create(Throwable cause) {
		log.error("feign异常",cause);
		return new HnymServerClient(){
			@Override
			public List<OrgDto> getOrgIds(List<Long> ids) {
				return null;
			}
		};
	}

}
