package com.czyl.project.monitor.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.czyl.common.lock.DistributedLock;
import com.czyl.common.utils.LockUtils;
import com.czyl.common.web.domain.AjaxResult;
import com.czyl.framework.aspectj.lang.annotation.Log;
import com.czyl.framework.aspectj.lang.enums.BusinessType;
import com.czyl.framework.redis.RedisCache;
import com.czyl.framework.web.controller.BaseController;
import com.czyl.framework.web.page.TableDataInfo;

/**
 * 分布式锁释放操作
 * 
 * @author tanghx
 */
@RestController
@RequestMapping("/monitor/distributedLock")
public class SysDistributedLockController extends BaseController {
	@Autowired
	private DistributedLock distributedLock;

	@Autowired()
	public RedisCache redisCache;

	/**
	 * 获取参数配置列表
	 */
	@PreAuthorize("@ss.hasPermi('monitor:distributedLock:list')")
	@GetMapping("/list")
	public TableDataInfo list(String key) {
		Set<Object> keys = distributedLock.getKeys(key);
		List<Map<String, String>> keyList = new ArrayList<Map<String, String>>();
		if(keys != null && keys.size() >0) {
			List<Object> values = redisCache.mgetCacheList(keys);
			int index=0;
			for (Object object : keys) {
				Map<String, String> map = new HashMap<String, String>(2);
				map.put("lockKey", object.toString());
				map.put("lockValue", values.get(index) == null ? "" : values.get(index).toString());
				keyList.add(map);
				index ++;
			}
		}

		return getDataTable(keyList);
	}

	/**
	 * 删除参数配置
	 */
	@PreAuthorize("@ss.hasPermi('monitor:distributedLock:release')")
	@Log(title = "分布式锁管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{key}")
	public AjaxResult release(@PathVariable String[] key) {
		for (String k : key) {
			LockUtils.releaseLockForce(k);
		}
		return toAjax(true);
	}
}
