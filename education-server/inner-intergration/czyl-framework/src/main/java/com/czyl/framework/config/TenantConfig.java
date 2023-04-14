package com.czyl.framework.config;

import cn.hutool.core.collection.CollUtil;
import com.czyl.framework.plugin.*;
import com.czyl.framework.plugin.properties.TenantProperties;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.apache.ibatis.mapping.MappedStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 租户
 *
 * @author tanghx
 * @version 1.0
 * @date 2021/6/8 9:56
 */

@EnableConfigurationProperties(TenantProperties.class)
@Configuration
@ConditionalOnProperty(name = "czyl.tenant.enable", havingValue = "true")
public class TenantConfig {

    @Autowired
    private TenantProperties tenantProperties;

    @Bean
    public TenantHandler getTenantHandler() {
        return new TenantHandler() {
            @Override
            public Expression getTenantId(boolean select) {
                return new LongValue(TenantContextHolder.get() == null ? 0L : TenantContextHolder.get());
            }

            @Override
            public String getTenantIdColumn() {
                return "tenant_id";
            }

            @Override
            public boolean doTableFilter(String tableName) {
                return !tenantProperties.getIncludeTables().stream().anyMatch(
                        (e) -> e.equalsIgnoreCase(tableName)
                );
            }
        };
    }

    @Bean
    TenantMybatisInterceptor getTenantMybatisInterceptor(TenantHandler tenantHandler, ISqlParserFilter sqlParserFilter) {
        TenantMybatisInterceptor tenantMybatisInterceptor = new TenantMybatisInterceptor();
        TenantSqlParser tenantSqlParser = new TenantSqlParser()
                .setTenantHandler(tenantHandler);
        tenantMybatisInterceptor.setSqlParserList(CollUtil.toList(tenantSqlParser));
        tenantMybatisInterceptor.setSqlParserFilter(sqlParserFilter);
        return tenantMybatisInterceptor;
    }

    /**
     * 过滤不需要根据租户隔离的MappedStatement
     */
    @Bean
    public ISqlParserFilter sqlParserFilter() {
        return metaObject -> {
            MappedStatement ms = SqlParserHelper.getMappedStatement(metaObject);
            return tenantProperties.getIgnoreSqls().stream().anyMatch(
                    (e) -> e.equalsIgnoreCase(ms.getId())
            );
        };
    }
}
