//package com.czyl.zuul.client.filter;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.stereotype.Component;
//
//import com.netflix.zuul.ZuulFilter;
//import com.netflix.zuul.context.RequestContext;
//
///**
// * 
// * @author tanghx
// *
// */
//@Component
//public class AccessFilter extends ZuulFilter {
//
//     
//
//    @Override
//    public String filterType() {
//        return "pre";
//    }
//
//    @Override
//    public int filterOrder() {
//        return 0;
//    }
//
//    @Override
//    public boolean shouldFilter() {
//        return true;
//    }
//    
//    @Override
//    public Object run() {
//        RequestContext ctx = RequestContext.getCurrentContext();
//        HttpServletRequest request = ctx.getRequest();
//       
//        try {
//        	
//        	
//        } catch (Exception e) {
//           
//        }
//        return null;
//    }
//
//     
//}
