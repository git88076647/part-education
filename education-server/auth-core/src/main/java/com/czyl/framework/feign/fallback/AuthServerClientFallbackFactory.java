package com.czyl.framework.feign.fallback;

import cn.hutool.core.collection.ListUtil;
import com.czyl.common.web.domain.AjaxResult;
import com.czyl.framework.feign.AuthServerClient;
import com.czyl.project.integrate.domain.SysAppreg;
import com.czyl.project.system.domain.SysBilltplItem;
import com.czyl.project.system.domain.SysDictData;
import com.czyl.project.system.domain.SysUserPermission;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Component
@Slf4j
public class AuthServerClientFallbackFactory implements FallbackFactory<AuthServerClient> {

    @Override
    public AuthServerClient create(Throwable cause) {
        log.error("feign异常", cause);
        return new AuthServerClient() {
            @Override
            public String getBizConfigValue(String key) {
                return null;
            }

            @Override
            public AjaxResult dictLabel(String dictType, String dictValue) {
                return null;
            }

            @Override
            public List<SysDictData> dictLabels(String dictType, List<String> dictValues) {
                return ListUtil.empty();
            }

            @Override
            public SysAppreg getAppReg(String code) {
                return null;
            }

            @Override
            public List<String> getUserRole(Long userId) {
                return null;
            }

            @Override
            public List<SysUserPermission> getUserPermision(Long userId) {
                return null;
            }

            @Override
            public AjaxResult importData(@RequestBody SysUserPermission entity) {
                return null;
            }

            @Override
            public List<SysBilltplItem> getBillItems(String billtplcode) {
                return ListUtil.empty();
            }

            @Override
            public AjaxResult getDataPermission(String scopeCode, String subjectType) {
                return AjaxResult.error("fegin调用失败");
            }

            @Override
            public AjaxResult getRole() {
                return AjaxResult.error("fegin调用失败");
            }

            @Override
            public AjaxResult getScopeResource() {
                return AjaxResult.error("fegin调用失败");
            }
        };
    }

}
