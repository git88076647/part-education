package com.czyl.common.constant;

/**
 * Trace跟踪常量
 * @author tanghx
 *
 */
public class TraceConstant {
	/**
     * 日志跟踪id名。
     */
    public static final String LOG_TRACE_ID = "traceid";
    
    public static final String LOG_B3_TRACEID = "X-B3-TraceId";

    public static final String LOG_B3_SPANID = "X-B3-SpanId";
    
    public static final String LOG_B3_USERID = "X-B3-UserId";
    
    public static final String LOG_B3_BIZ = "X-B3-Biz";
    
    /**
     * 请求头跟踪id名。
     */
    public static final String HTTP_HEADER_TRACE_ID = "app_trace_id";
    
    public static final String HTTP_HEADER_APMTRACE_ID = "apm_trace_id";
    
    
    
}
