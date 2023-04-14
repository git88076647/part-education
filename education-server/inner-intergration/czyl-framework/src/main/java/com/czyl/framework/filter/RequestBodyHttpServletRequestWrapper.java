package com.czyl.framework.filter;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.IOUtils;


/**
 * 复用requestBody
 * 
 * @author tanghx
 */
public class RequestBodyHttpServletRequestWrapper extends HttpServletRequestWrapper {
	private final byte[] body;

	/**
	 * @param request
	 */
	public RequestBodyHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		this.body = IOUtils.toByteArray(super.getInputStream());
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {

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

}