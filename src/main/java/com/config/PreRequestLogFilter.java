package com.config;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: yunxiang.yang
 * @Date: 2019/1/8 16:12
 */

public class PreRequestLogFilter extends ZuulFilter {

    private static final Logger logger = LoggerFactory.getLogger(PreRequestLogFilter.class);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest httpServletRequest = requestContext.getRequest();
        PreRequestLogFilter.logger.info(String.format("send %s request to %s", httpServletRequest.getMethod(), httpServletRequest.getRequestURL().toString()));
        return null;
    }
}
