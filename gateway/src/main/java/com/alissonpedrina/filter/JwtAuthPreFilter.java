package com.alissonpedrina.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class JwtAuthPreFilter extends ZuulFilter {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    JwtUtil jwtUtil;

    /**
     * pre: Before Routing
     * routing: When Routing
     * post:  After Routing
     * error: Send an incorrect call
     *
     * @return
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * filterOrder: The sequence number configuration for filtering can be referred to https://blog.csdn.net/u010963948/article/details/100146656
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 2;
    }

    /**
     * shouldFilter: Is Logic to Filter
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        //If the path matches the configuration, filter is performed
        RequestContext ctx = RequestContext.getCurrentContext();
        return (ctx.getRequest().getRequestURI() == null ||
                !ctx.getRequest().getRequestURI().contains("/auth/"));
    }

    /**
     * Execute filter logic, verify token
     *
     * @return
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String token = request.getHeader("token");
        Claims claims;
        try {
            //Parsing with no exceptions means token validation passes and, if necessary, adds validation logic to suit your needs
            claims = jwtUtil.parseJWT(token);
            log.info("token : {} Verification Passed", token);
            //Routing requests
            ctx.setSendZuulResponse(true);
            //Request header joins userId, passed to business service
            ctx.addZuulRequestHeader("userId", claims.get("userId").toString());
        } catch (ExpiredJwtException expiredJwtEx) {
            log.error("token : {} Be overdue", token);
            //Do not route requests
            ctx.setSendZuulResponse(false);
            responseError(ctx, -402, "token expired");
        } catch (Exception ex) {
            log.error("token : {} Validation failed", token);
            //Do not route requests
            ctx.setSendZuulResponse(false);
            responseError(ctx, -401, "invalid token");
        }
        return null;
    }

    /**
     * Response Exception Information to Front End
     */
    private void responseError(RequestContext ctx, int code, String message) {
        HttpServletResponse response = ctx.getResponse();
        ctx.setResponseBody(toJsonString(String.format("{'code':'%s','message':'%s'}", code, message)));
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("application/json;charset=utf-8");
    }

    private String toJsonString(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            log.error("json Serialization failed", e);
            return null;
        }
    }
}