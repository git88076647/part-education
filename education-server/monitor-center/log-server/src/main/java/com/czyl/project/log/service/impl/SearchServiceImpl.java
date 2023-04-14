package com.czyl.project.log.service.impl;

import com.alibaba.fastjson.JSON;
import com.czyl.common.exception.CustomException;
import com.czyl.common.utils.DateUtils;
import com.czyl.common.utils.StringUtils;
import com.czyl.framework.web.domain.SearchBO;
import com.czyl.project.log.service.ISearchService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 通用搜索
 *
 * @author mod
 * @date 2019/4/24
 */
@Service
@Slf4j
public class SearchServiceImpl implements ISearchService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    private final String ES_QUERY_FIELD = "data";

    private final String ES_QUERY_ORDER = "ts";

    /**
     * ES 报错超出最大行
     */
    private final String ES_SIZE_PREFIX = "from + size must be less than or equal to: [";


    /**
     * StringQuery通用搜索
     *
     * @param search 搜索
     * @return
     */
    @Override
    public PageInfo<Map> strQuery(String indexName, SearchBO search) {
        //TODO 通用检索

        final List list = Lists.newLinkedList();
        try {
            if (existsIndex(indexName)) {
                // 构建查询源构建器
                SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
                if (PageHelper.getLocalPage() != null) {
                    int pageNum = PageHelper.getLocalPage().getPageNum();
                    int pageSize = PageHelper.getLocalPage().getPageSize();
                    searchSourceBuilder.from((pageNum - 1) * pageSize);
                    searchSourceBuilder.size(pageSize);
                }
                // 构建查询条件
                if (StringUtils.isNotBlank(search.getSearchValue())) {
                    searchSourceBuilder.query(QueryBuilders.matchQuery(ES_QUERY_FIELD, search.getSearchValue()));
                    // 空格作为分词 分隔符
//				searchSourceBuilder.query(QueryBuilders.simpleQueryStringQuery(queryString).analyzer("whitespace"));
                } else {
                    searchSourceBuilder.query(QueryBuilders.matchAllQuery());
                }
                searchSourceBuilder.sort(ES_QUERY_ORDER, SortOrder.DESC);
                // 调试模式
//			searchSourceBuilder.profile(true);
                // 创建查询请求对象，将查询对象配置到其中
                String[] indeces = new String[]{indexName};
                if (indeces.length > 0) {
                    SearchRequest searchRequest = new SearchRequest(indeces);
                    searchRequest.source(searchSourceBuilder);
                    // 执行查询，然后处理响应结果
                    SearchResponse searchResponse = restHighLevelClient.search(searchRequest);
                    // 根据状态和数据条数验证是否返回了数据
                    if (RestStatus.OK.equals(searchResponse.status()) && searchResponse.getHits().totalHits > 0) {
                        SearchHits hits = searchResponse.getHits();
                        for (SearchHit hit : hits) {
                            // 将 JSON 转换成对象
                            Map map = JSON.parseObject(hit.getSourceAsString(), Map.class);
                            if(map.containsKey(ES_QUERY_FIELD) && StringUtils.isNotBlank(map.get(ES_QUERY_FIELD).toString())){
                                List datas = JSON.parseObject(map.get(ES_QUERY_FIELD).toString(), List.class);
                                list.addAll(datas);
                            }
                        }
                        PageInfo<Map> pageInfo = new PageInfo<>(list);
                        pageInfo.setTotal(hits.getTotalHits());
                        return pageInfo;
                    }
                }
            }
        } catch (Exception e) {
            if (e.getSuppressed() != null && e.getSuppressed().length > 0) {
                String msg = e.getSuppressed()[0].getMessage();
                if (StringUtils.isNotBlank(msg) && msg.indexOf(ES_SIZE_PREFIX) > -1) {
                    msg = msg.substring(msg.indexOf(ES_SIZE_PREFIX) + ES_SIZE_PREFIX.length());
                    msg = msg.substring(0, msg.indexOf("]"));
                    throw new CustomException(String.format("最大允许查询行不超过%s", msg));
                }
            }
            log.error("查询异常", e);
            throw new CustomException("查询异常！");
        }
        return new PageInfo<>();

    }

    @Override
    public void save(String indexName, Map[] maps) {
        IndexRequest request = new IndexRequest(indexName, "json");
        try {
            XContentBuilder content = XContentFactory.jsonBuilder().startObject()
                    .field(ES_QUERY_FIELD, JSON.toJSONString(maps))
                    .timeField(ES_QUERY_ORDER,new Date())
                    .endObject();
            request.source(content);
            restHighLevelClient.index(request);
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.CONFLICT) {
                log.error("ES版本冲突，保存数据失败", e);
            }
            throw new CustomException("保存数据失败！", e);
        } catch (IOException e1) {
            throw new CustomException("保存数据失败！", e1);
        }
    }

    public boolean existsIndex(String index) throws IOException {
        GetIndexRequest getRequest = new GetIndexRequest();
        getRequest.indices(index);
        getRequest.local(false);
        getRequest.humanReadable(true);
        return restHighLevelClient.indices().exists(getRequest);
    }
}
