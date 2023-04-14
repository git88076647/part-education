package com.czyl.framework.feign;

import com.czyl.framework.aspectj.login.LoginSuper;
import com.czyl.framework.feign.dto.OrgDto;
import com.czyl.framework.feign.fallback.HnymServerClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * hnym-server的Feign
 *
 * @author tanghx
 */
@FeignClient(value = "hnym-server", fallback = HnymServerClientFallbackFactory.class)
public interface HnymServerClient {
    /**
     * 角色数据权限
     *
     * @param ids
     * @return
     */
    @LoginSuper
    @RequestMapping(method = RequestMethod.GET, value = "/pubase/org/getOrgByIds", consumes = "application/json")
    public List<OrgDto> getOrgIds(@RequestParam(value = "ids") List<Long> ids);


}
