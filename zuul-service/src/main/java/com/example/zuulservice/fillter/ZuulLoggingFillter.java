package com.example.zuulservice.fillter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Request;
import org.springframework.stereotype.Component;


import javax.servlet.http.HttpServletRequest;


@Slf4j
@Component
public class ZuulLoggingFillter extends ZuulFilter {

    @Override
    public Object run() throws ZuulException {
        log.info("************************ printing logs: ");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info("************************ "+request.getRequestURI());
        return null;
    }

    @Override
    public String filterType() {
        return "pre"; // 들어올때 출력하겠다.
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }


}
