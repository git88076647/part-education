package com.czyl.common.report.itf.impl;

import com.alibaba.fastjson.JSON;
import com.czyl.common.enums.DataSourceTypeEnum;
import com.czyl.common.exception.CustomException;
import com.czyl.common.meta.DataResultMeta;
import com.czyl.common.report.itf.IDataQuery;
import com.czyl.common.report.itf.IQueryResultDataVO;
import com.czyl.common.util.sql.SqlUtil;
import com.czyl.common.utils.StringUtils;
import com.czyl.common.utils.spring.SpringUtils;
import com.czyl.framework.datasource.DynamicDataSourceContextHolder;
import com.czyl.project.report.domain.RepSemantic;
import com.czyl.project.report.domain.bo.DataResultBO;
import com.github.pagehelper.Page;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Date;
import java.util.*;

/**
 * sql数据库 查询默认实现类 <br/>
 * <br/>
 * <br/>
 *
 * @author air Email:209308343@qq.com
 * @date 2020/4/2 0002 17:06
 * @project
 * @Version
 */
public class DefaultSqlDataQueryImpl implements IDataQuery {
	/**
	 * 语义模型
	 */
	private RepSemantic repSemantic;
	/**
	 * 参数map
	 */
	private Map<Integer, Object> parmarMap;

	@Override
	public IQueryResultDataVO queryAll(Map<Integer, Object> parmarMap) throws SQLException, CustomException {
		Assert.notNull(repSemantic, "语义模型不存在");

		DataResultBO dataResultBO = doQuery(parmarMap, null);
		DataResultMeta dataResultMate = getDataResultMate();

		DefaultQueryResultDataVOImpl resultDataVO = new DefaultQueryResultDataVOImpl();
		resultDataVO.setDataResultBO(dataResultBO);
		resultDataVO.setDataResultMeta(dataResultMate);

		return resultDataVO;
	}

	@Override
	public IQueryResultDataVO queryPage(Map<Integer, Object> parmarMap, Page page) throws SQLException, CustomException {
		Assert.notNull(repSemantic, "语义模型不存在");
		DataResultBO dataResultBO = doQuery(parmarMap, page);
		DataResultMeta dataResultMate = getDataResultMate();

		DefaultQueryResultDataVOImpl resultDataVO = new DefaultQueryResultDataVOImpl();
		resultDataVO.setDataResultBO(dataResultBO);
		resultDataVO.setDataResultMeta(dataResultMate);

		return resultDataVO;
	}

	/**
	 * 执行数据库查询
	 *
	 * @param page null就 不分页
	 * @return
	 * @throws SQLException
	 */
	private DataResultBO doQuery(Map<Integer, Object> parmarMap, Page page) throws SQLException {
		Map<String, List<Integer>> parmarIndexMap = SqlUtil.analyzeParmar(repSemantic.getScripttxt());
		String sql = SqlUtil.compilerSql(repSemantic.getScripttxt(), parmarMap);
		Statement statement = null;
		ResultSet resultSet = null;
		Connection conn = getConnection(repSemantic);

		if (page != null) {
			sql = buildPageSql(sql, conn, page);
		}

		try {
			if (!parmarIndexMap.isEmpty()) {
				PreparedStatement preparedStatement = conn.prepareStatement(sql);
				statement = preparedStatement;

				Iterator<Map.Entry<String, List<Integer>>> parmarIndexIterator = parmarIndexMap.entrySet().iterator();
				Map.Entry<String, List<Integer>> parmarIndexEntry;
				Object parmar;
				List<Integer> parmarIndexs;
				while (parmarIndexIterator.hasNext()) {
					parmarIndexEntry = parmarIndexIterator.next();
					parmar = parmarMap.get(parmarIndexEntry.getKey());
					parmarIndexs = parmarIndexEntry.getValue();
					if (parmarIndexs == null) {
						// 替换型 ${} 变量的，跳过他
						continue;
					}
					for (Integer parmarIndex : parmarIndexs) {
						preparedStatement.setObject(parmarIndex, parmar);
					}
				}

				resultSet = preparedStatement.executeQuery();
			} else {
				statement = conn.createStatement();
				resultSet = statement.executeQuery(sql);
			}
			DataResultBO bo = new DataResultBO();
			bo.setPageInfo(page);
			bo.setDatas(new LinkedList<>());
			Map<String, Object> line;
			ResultSetMetaData metaData = resultSet.getMetaData();
			final int columnCount = metaData.getColumnCount();
			while (resultSet.next()) {
				line = new HashMap<>(columnCount << 1);
				for (int i = 1; i <= columnCount; i++) {
					line.put(metaData.getColumnLabel(i), resultSet.getObject(i));
				}
				bo.getDatas().add(line);
			}

			return bo;
		} finally {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (conn != null) {
				releseConnection(conn);
			}
		}
	}

	@Override
	public DataResultMeta getDataResultMate() throws SQLException, CustomException {
		Assert.notNull(repSemantic, "语义模型不存在");
		if (StringUtils.isEmpty(repSemantic.getScripttxt())) {
			throw new SQLException("语义模型SQL语句不合法");
		}

		Statement statement = null;
		ResultSet resultSet = null;
		Connection conn = getConnection(repSemantic);
		try {
			statement = conn.createStatement();
			resultSet = statement.executeQuery(SqlUtil.warpParmarForNull(repSemantic.getScripttxt(), null));

			final DataResultMeta dataResultMeta = new DataResultMeta();

			ResultSetMetaData metaData = resultSet.getMetaData();
			final int columnCount = metaData.getColumnCount();
			dataResultMeta.init(columnCount);
			dataResultMeta.setDataSourceType(DataSourceTypeEnum.SQLDB);

			for (int i = 1; i <= columnCount; i++) {
				dataResultMeta.getFiled2TypeMap().put(metaData.getColumnLabel(i), getTypeClassBySqlType(metaData.getColumnType(i)));
			}

			dataResultMeta.setParmarName2IndexMap(SqlUtil.analyzeParmar(repSemantic.getScripttxt()));

			// 字段名显示名的对照表处理
			if (StringUtils.isNotEmpty(repSemantic.getFilednamejson())) {
				// {"sex": "性别", "org_id": "组织id", "psn_id": "psn_id"}
				Map maps = (Map) JSON.parse(repSemantic.getFilednamejson());
				Set keys = maps.keySet();
				HashMap<String, String> showNames = new HashMap<>(maps.size());
				for (Object key : keys) {
					showNames.put(key.toString(), maps.get(key).toString());
				}
				dataResultMeta.setFiledShowNames(showNames);
			}

			return dataResultMeta;
		} finally {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (conn != null) {
				releseConnection(conn);
			}
		}
	}

	/**
	 * 根据 java.sql.Types 获得 java class类型
	 *
	 * @param sqlType
	 * @return 如果没有匹配 返回null
	 */
	private Class getTypeClassBySqlType(int sqlType) {
		switch (sqlType) {
		case Types.BIGINT:
			return long.class;
		case Types.BIT:
			return byte.class;
		case Types.BOOLEAN:
			return boolean.class;
		case Types.CHAR:
			return char.class;
		case Types.DECIMAL:
			return BigDecimal.class;
		case Types.DOUBLE:
		case Types.NUMERIC:
			return double.class;
		case Types.FLOAT:
			return float.class;
		case Types.INTEGER:
		case Types.SMALLINT:
		case Types.TINYINT:
			return int.class;
		case Types.LONGNVARCHAR:
		case Types.LONGVARBINARY:
		case Types.LONGVARCHAR:
		case Types.NCHAR:
		case Types.VARCHAR:
			return String.class;
		case Types.DATE:
		case Types.TIME:
		case Types.TIME_WITH_TIMEZONE:
		case Types.TIMESTAMP:
		case Types.TIMESTAMP_WITH_TIMEZONE:
			return Date.class;
		default:
			return null;
		}
	}

	@Override
	public RepSemantic getRepSemantic() {
		return repSemantic;
	}

	@Override
	public void setRepSemantic(RepSemantic repSemantic) {
		this.repSemantic = repSemantic;
	}

	@Override
	public Map<Integer, Object> getParmarMap() {
		return parmarMap;
	}

	/**
	 * 获得 添加分页后的语句，根据不同数据库,如果不支持的数据库 就返回原sql
	 *
	 * @param sql
	 * @param conn
	 * @return
	 */
	private String buildPageSql(String sql, Connection conn, Page page) throws SQLException {
		/*
		 * 暂时不写，这里可以不这样，可以让前端自己写 分页语句，自己定义分页参数 String databaseProductName =
		 * conn.getMetaData().getDatabaseProductName(); if (databaseProductName == null)
		 * { databaseProductName = ""; } databaseProductName =
		 * databaseProductName.toLowerCase(); if (databaseProductName.contains("mysql"))
		 * { sql = "select * from (" + sql + " )  limit " + page.getStartRow() + "," +
		 * page.getEndRow(); } else if (databaseProductName.contains("oracle")) { } else
		 * if (databaseProductName.contains("sqlserver")) {
		 * 
		 * }
		 */

		return sql;
	}

	/**
	 * 获得数据库连接，如果配置了数据源 就用，否则 默认spring的
	 *
	 * @param repSemantic
	 * @return
	 */
	private Connection getConnection(RepSemantic repSemantic) throws SQLException {
		try {
			if (repSemantic != null) {
				DynamicDataSourceContextHolder.setDataSourceType(repSemantic.getDatasource());
			}
			Connection conn = DataSourceUtils.getConnection(SpringUtils.getBean(DataSource.class));
			conn.setAutoCommit(false);
			return conn;
		} finally {
			DynamicDataSourceContextHolder.clearDataSourceType();
		}
	}

	/**
	 * 释放 数据库连接，如果配置了数据源 就用，否则 默认spring的. 强制回滚任何事务。
	 *
	 * @param conn
	 * @return
	 */
	private void releseConnection(Connection conn) throws SQLException {
		if (conn != null && !conn.isClosed() && !conn.isReadOnly()) {
			conn.rollback();
		}
		DataSourceUtils.doReleaseConnection(conn, SpringUtils.getBean(DataSource.class));
	}

}
