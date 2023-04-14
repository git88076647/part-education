package com.czyl.project.log.service;

import com.czyl.framework.web.domain.SearchBO;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * @author mod
 * @date 2019/4/24
 */
public interface ISearchService {
    /**
     * StringQuery通用搜索
     * @param indexName 索引名
     * @param search 搜索
     * @return
     */
    PageInfo<Map> strQuery(String indexName,SearchBO search);

    /**
     * 保存
     * @param indexName 索引名
     * @param map
     */
    void save(String indexName,Map[] map);
}
