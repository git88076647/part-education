package com.czyl.framework.feign;

import com.czyl.common.web.domain.AjaxResult;
import com.czyl.framework.aspectj.login.LoginSuper;
import com.czyl.framework.feign.fallback.LogServerClientFallbackFactory;
import com.czyl.framework.web.domain.Logininfor;
import com.czyl.framework.web.domain.OperLog;
import com.czyl.framework.web.domain.SearchBO;
import com.czyl.framework.web.page.TableDataInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * log-server 的Feign
 * @author tanghx
 *
 */
@FeignClient(value="log-server",fallback= LogServerClientFallbackFactory.class)
public interface LogServerClient {
	@RequestMapping(method = RequestMethod.POST, value = "/log/logininfor", consumes = "application/json")
	public AjaxResult insertLogininfor(Logininfor logininfor);

	@LoginSuper
	@RequestMapping(method = RequestMethod.POST, value = "/log/operlog", consumes = "application/json")
	public AjaxResult insertOperlog(OperLog operLog);

	@PostMapping(value="/log/search/list/{indexName}", consumes = "application/json")
	public TableDataInfo searchEsData(@PathVariable String indexName, SearchBO search);

	@LoginSuper
	@PostMapping(value="/log/search/add/{indexName}", consumes = "application/json")
	public AjaxResult saveEsData(@PathVariable String indexName, @RequestBody Map[] maps);
}