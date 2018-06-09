package com.didispace;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.string.widget.util.ValueWidget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/***
 * @author : whuang
 */
public class AccessFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(AccessFilter.class);

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
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();

        log.info("send {} request to {}", request.getMethod(), request.getRequestURL().toString());

        Object accessToken = request.getParameter("accessToken");
        System.out.println("accessToken :" + accessToken);
        if (ValueWidget.isNullOrEmpty(accessToken)
                || (!"whuang".equals(accessToken))) {
            log.warn("access token is empty");
            ctx.setSendZuulResponse(false);
            /*try {
                WebServletUtil.writeResponse(response,"登录失败");
            } catch (IOException e) {
                e.printStackTrace();
            }*/

            ctx.setResponseStatusCode(401);
            return null;
        }
        List<String> username = new ArrayList();
        username.add("黄昆仑223");
        Map<String, List<String>> queryParams = ctx.getRequestQueryParams();
        queryParams.put("username", username);
        ctx.setRequestQueryParams(queryParams);
        log.info("access token ok");
        return null;
    }

}