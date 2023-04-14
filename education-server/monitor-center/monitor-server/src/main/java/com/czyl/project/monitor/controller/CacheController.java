package com.czyl.project.monitor.controller;

import com.czyl.common.utils.StringUtils;
import com.czyl.common.web.domain.AjaxResult;
import com.czyl.framework.web.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 缓存监控
 *
 * @author tanghx
 */
@RestController
@RequestMapping("/monitor/cache")
@Slf4j
public class CacheController extends BaseController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @SuppressWarnings({"rawtypes", "unchecked"})
    @PreAuthorize("@ss.hasPermi('monitor:cache:list')")
    @GetMapping()
    public AjaxResult getApps() throws Exception {
        Properties info = (Properties) redisTemplate.execute((RedisCallback<Object>) connection -> connection.info());
        Properties commandStats = (Properties) redisTemplate.execute((RedisCallback<Object>) connection -> connection.info("commandstats"));
        Object dbSize = redisTemplate.execute((RedisCallback<Object>) connection -> connection.dbSize());

        Map<String, Object> result = new HashMap<>(3);
        result.put("info", info);
        result.put("dbSize", dbSize);
        String serverKey="";
        if (info != null) {
            Set<String> names = info.stringPropertyNames();
            if (names.iterator().next().contains(".")) {
                List<String> keys = names.stream().map(o -> o.substring(o.lastIndexOf(".") + 1, o.length())).distinct().collect(Collectors.toList());
                List<String> servers = names.stream().map(o -> o.substring(0, o.lastIndexOf("."))).distinct().sorted().collect(Collectors.toList());
                Map<String, Map> infoCluster = new HashMap<>();
                servers.stream().forEach(server -> {
                    HashMap data = new HashMap<>();
                    keys.forEach(key -> {
                        data.put(key, info.getProperty(server + "." + key));
                    });
                    infoCluster.put(server, data);
                });
                serverKey = servers.get(0);
                result.put("infoCluster", infoCluster);
                result.put("info", infoCluster.get(serverKey));
            }
        }

        List<Map<String, String>> pieList = new ArrayList<>();
        commandStats.stringPropertyNames().forEach(key -> {
            Map<String, String> data = new HashMap<>(2);
            String property = commandStats.getProperty(key);
            data.put("name", StringUtils.removeStart(key, "cmdstat_"));
            data.put("value", StringUtils.substringBetween(property, "calls=", ",usec"));
            pieList.add(data);
        });

        result.put("commandStats", pieList);
        if (pieList != null && !pieList.isEmpty()) {
            if (pieList.get(0).get("name").contains(".")) {
                Map<String, List> statsCluster = new HashMap<>();
                pieList.stream().forEach(o -> {
                    String server = o.get("name").substring(0, o.get("name").lastIndexOf("."));
                    String key = o.get("name").substring(o.get("name").lastIndexOf(".") + 1);
                    List tmp;
                    if (statsCluster.containsKey(server)) {
                        tmp = statsCluster.get(server);
                    } else {
                        tmp = new LinkedList();
                        statsCluster.put(server,tmp);
                    }
                    HashMap data = new HashMap<>();
                    data.put("name", key);
                    data.put("value", o.get("value"));
                    tmp.add(data);
                });
                result.put("commandStats",statsCluster.get(serverKey));
                result.put("commandStatsCluster", statsCluster);
            }
        }
        return AjaxResult.success(result);
    }


}
