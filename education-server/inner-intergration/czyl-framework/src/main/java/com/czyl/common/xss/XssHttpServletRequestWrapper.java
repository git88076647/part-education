package com.czyl.common.xss;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.czyl.common.utils.StringUtils;
import com.czyl.common.utils.html.HTMLFilter;


/**
 * XSS过滤处理
 *
 * @author tanghx
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {


    /**
     * @param request
     */
    public XssHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);

    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values != null) {
            int length = values.length;
            String[] escapseValues = new String[length];
            for (int i = 0; i < length; i++) {
                // 防xss攻击和过滤前后空格
                escapseValues[i] = new HTMLFilter().filter(values[i]).trim();
            }
            return escapseValues;
        }
        return super.getParameterValues(name);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {

        // 非json类型
        if (!isJsonRequest()) {
            return super.getInputStream();
        }
        // 不为空
        byte[] body = IOUtils.toByteArray(super.getInputStream());
        String json = new String(body, "utf-8");
        if (!StringUtils.isEmpty(json)) {
            // xss过滤
            json = new HTMLFilter().filter(json).trim();
            body = json.getBytes("utf-8");
        }

        final ByteArrayInputStream bis = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return true;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }

            @Override
            public int read() throws IOException {
                return bis.read();
            }
        };
    }

    /**
     * 是否是Json请求
     */
    @SuppressWarnings("deprecation")
    public boolean isJsonRequest() {
        String header = super.getHeader(HttpHeaders.CONTENT_TYPE);
        return MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(header) || MediaType.APPLICATION_JSON_UTF8_VALUE.equalsIgnoreCase(header);
    }
}