package com.czyl.zuul.client.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czyl.common.web.domain.AjaxResult;
import com.netflix.zuul.context.RequestContext;

import lombok.extern.slf4j.Slf4j;

/**
 * zuul自定义异常格式<br/>
 * (限流处理异常)
 * @author tanghx
 */
@Slf4j
@Controller
public class ZuulErrorController implements ErrorController {

    public static final String ERROR_PATH = "/error";

    /**
     * 错误最终会到这里来
     */
    @RequestMapping(ERROR_PATH)
    @ResponseBody
    public Object error() {
        RequestContext ctx = RequestContext.getCurrentContext();
        Throwable throwable = ctx.getThrowable();
        if(throwable == null) {
        	return AjaxResult.error("404 request url not exists"); 
        }
        log.error("zuul路由异常",throwable);
        return AjaxResult.error(throwable.getMessage()) ;
    }

    

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}