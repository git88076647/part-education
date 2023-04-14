package com.czyl.project.log.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.czyl.common.exception.CustomException;
import com.czyl.common.utils.DateUtils;
import com.czyl.common.utils.StringUtils;
import com.czyl.project.log.domain.SysBackLog;
import com.czyl.project.log.service.ISysBackLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import lombok.extern.slf4j.Slf4j;

/**
 * 操作日志 服务层处理
 * 
 * @author tanghx
 */
@Slf4j
@Service
public class SysBackLogServiceImpl implements ISysBackLogService {

	/**
	 * 后台日志索引
	 */
	private final String BACK_LOG_INDEX = "czyl-logindex-";

	private final String BACK_LOG_INDEX_ALL = "czyl-logindex-*";
	/**
	 * ES 报错超出最大行
	 */
	private final String ES_SIZE_PREFIX = "from + size must be less than or equal to: [";

	private final String ES_QUERY_FIELD = "message";

	private final String ES_QUERY_ORDER = "@timestamp";

	@Autowired
	private RestHighLevelClient restHighLevelClient;

	/**
	 * 查询系统后台日志集合
	 * @param begin 开始日期
	 * @param end 截止日期
	 * @param searchValue
	 * @return
	 */
	@Override
	public PageInfo<SysBackLog> selectList(String begin, String end, String searchValue) {
		return query(begin, end, searchValue, BACK_LOG_INDEX);
	}

	public PageInfo<SysBackLog> query(String begin, String end, String searchValue, String indexPrefix) {
		List<SysBackLog> list = Lists.newLinkedList();
		try {
			// 构建查询源构建器
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			if (PageHelper.getLocalPage() != null) {
				int pageNum = PageHelper.getLocalPage().getPageNum();
				int pageSize = PageHelper.getLocalPage().getPageSize();
				searchSourceBuilder.from((pageNum - 1) * pageSize);
				searchSourceBuilder.size(pageSize);
			}
			// 构建查询条件
			if (StringUtils.isNotBlank(searchValue)) {
				searchSourceBuilder.query(QueryBuilders.matchQuery(ES_QUERY_FIELD, searchValue));
				// 空格作为分词 分隔符
//				searchSourceBuilder.query(QueryBuilders.simpleQueryStringQuery(queryString).analyzer("whitespace"));
			} else {
				searchSourceBuilder.query(QueryBuilders.matchAllQuery());
			}
			searchSourceBuilder.sort(ES_QUERY_ORDER, SortOrder.ASC);
			// 调试模式
//			searchSourceBuilder.profile(true);
			// 创建查询请求对象，将查询对象配置到其中
			String[] indeces = getIndices(begin, end, indexPrefix);
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
						SysBackLog backLog = JSON.parseObject(hit.getSourceAsString(), SysBackLog.class);
						backLog.setId(hit.getId());
						backLog.setIndex(hit.getIndex());
						backLog.setType(hit.getType());
						backLog.setShard(hit.getShard() == null ? null : hit.getShard().getNodeId());
						list.add(backLog);
					}
					PageInfo<SysBackLog> pageInfo = new PageInfo<>(list);
					pageInfo.setTotal(hits.getTotalHits());
					return pageInfo;
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

	public String[] getIndices(String begin, String end, String indexPrefix) throws IOException {
		if (StringUtils.isEmpty(end)) {
			end = DateUtils.getDate();
		}
		if (StringUtils.isEmpty(begin)) {
			return new String[] { BACK_LOG_INDEX_ALL };
		}
		Set<String> indeces = Sets.newLinkedHashSet();
		Date beginD = DateUtils.parseDate(begin);
		Date endD = DateUtils.parseDate(end);
		String index;
		while (beginD.compareTo(endD) <= 0) {
			index = indexPrefix + DateUtils.dateTime(beginD);
			if (existsIndex(index)) {
				indeces.add(index);
			}
			beginD = DateUtils.addDays(beginD, 1);
		}
		return indeces.toArray(new String[indeces.size()]);
	}

	public boolean existsIndex(String index) throws IOException {
		GetIndexRequest getRequest = new GetIndexRequest();
		getRequest.indices(index);
		getRequest.local(false);
		getRequest.humanReadable(true);
		return restHighLevelClient.indices().exists(getRequest);
	}
}
