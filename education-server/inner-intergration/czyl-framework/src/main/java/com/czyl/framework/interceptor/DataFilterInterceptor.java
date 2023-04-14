

package com.czyl.framework.interceptor;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.czyl.framework.plugin.PluginUtils;
import com.czyl.framework.web.domain.BaseEntity;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Connection;
import java.util.Map;
import java.util.Properties;

/**
 * 数据过滤
 *
 * @author hhhcccggg
 * @since 1.0.0
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class DataFilterInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);


        // 先判断是不是SELECT操作
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        if (!SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
            return invocation.proceed();
        }

        // 针对定义了rowBounds，做为mapper接口方法的参数
        BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
        String originalSql = boundSql.getSql();
        Object paramObj = boundSql.getParameterObject();
        String dataScope = "";
        if (paramObj instanceof BaseEntity) {
            dataScope = (String) BeanUtil.getFieldValue(paramObj, "scope");
        }
        if (paramObj instanceof Map && ((Map) paramObj).containsKey("scope")) {
            dataScope = (String) (((Map) paramObj).get("scope"));
        }
        if (StrUtil.isEmpty(dataScope)) {
            return invocation.proceed();
        }

        // 拼接新SQL
        originalSql = getSelect(originalSql, dataScope);

        // 重写SQL
        metaObject.setValue("delegate.boundSql.sql", originalSql);
        return invocation.proceed();
    }

    private String getSelect(String originalSql, String scope) {
        try {
            Select select = (Select) CCJSqlParserUtil.parse(originalSql);
            PlainSelect plainSelect = (PlainSelect) select.getSelectBody();

            Expression expression = plainSelect.getWhere();
            if (expression == null) {
                plainSelect.setWhere(new LongValue(scope));
            } else {
                AndExpression andExpression = new AndExpression(expression, new LongValue(scope));
                plainSelect.setWhere(andExpression);
            }

            return select.toString();
        } catch (JSQLParserException e) {
            return originalSql;
        }
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
