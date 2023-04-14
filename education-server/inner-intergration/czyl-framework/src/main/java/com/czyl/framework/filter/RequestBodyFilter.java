package com.czyl.framework.filter;

import com.czyl.common.utils.StringUtils;
import com.czyl.common.xss.XssHttpServletRequestWrapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 复用requestBody
 *
 * @author tanghx
 */
public class RequestBodyFilter implements Filter {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (isJsonRequest((HttpServletRequest) request)) {
            RequestBodyHttpServletRequestWrapper requestBody = new RequestBodyHttpServletRequestWrapper((HttpServletRequest) request);
            chain.doFilter(requestBody, response);
        } else {
            chain.doFilter(request, response);
        }

    }

    /**
     * 是否是Json请求
     */
    @SuppressWarnings("deprecation")
    public boolean isJsonRequest(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.CONTENT_TYPE);
        return MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(header) || MediaType.APPLICATION_JSON_UTF8_VALUE.equalsIgnoreCase(header);
    }

    @Override
    public void destroy() {

    }
}