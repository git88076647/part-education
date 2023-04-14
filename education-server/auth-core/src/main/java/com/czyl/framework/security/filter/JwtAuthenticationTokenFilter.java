package com.czyl.framework.security.filter;

import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.spring.boot.autoconfigure.properties.DruidStatProperties;
import com.czyl.common.constant.Constants;
import com.czyl.common.constant.TraceConstant;
import com.czyl.common.utils.AppContextUtils;
import com.czyl.common.utils.SecurityUtils;
import com.czyl.framework.plugin.TenantContextHolder;
import com.czyl.framework.security.LoginUser;
import com.czyl.framework.security.service.TokenService;
import org.jboss.logging.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token过滤器 验证token有效性
 *
 * @author tanghx
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;

    private String urlPattern;

    @Autowired
    public void setDruidProperties(DruidStatProperties druidProperties) {
        urlPattern = druidProperties.getStatViewServlet().getUrlPattern() != null ? druidProperties.getStatViewServlet().getUrlPattern() : "/druid/*";
        urlPattern = urlPattern.substring(0, urlPattern.lastIndexOf("/") + 1);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        LoginUser loginUser = tokenService.getLoginUser(request);
        try {
            if (loginUser != null) {
                //填充租户
                TenantContextHolder.set(loginUser.getUser().getOrgId());
                //Super访问初始化Tenant
                if(loginUser.getUser().getUserId().equals(2L)){
                    String tenantId = request.getHeader(Constants.TENANT_HEADER);
                    if(StrUtil.isNotBlank(tenantId)){
                        TenantContextHolder.set(Long.valueOf(tenantId));
                    }
                }
                if (loginUser != null && SecurityUtils.getAuthentication() == null) {
                    tokenService.verifyToken(loginUser);
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
                if (loginUser != null) {
                    MDC.put(TraceConstant.LOG_B3_USERID, loginUser.getUser().getUserCode());
                    if (request.getRequestURI().startsWith(urlPattern)) {
                        if (loginUser.getUser().isAdmin() == false && !AppContextUtils.hasAdminRole(loginUser)) {
                            throw new AccessDeniedException("没有admin权限");
                        }
                    }
                }
            }
            chain.doFilter(request, response);
        } finally {
            TenantContextHolder.remove();
        }

    }
}
