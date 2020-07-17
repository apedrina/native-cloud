package com.alissonpedrina.filter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

@Component
@Slf4j
public class LoginAndJwtFilter extends ZuulFilter {

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
     * filterOrder: Order of filtering
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return FilterConstants.SEND_RESPONSE_FILTER_ORDER - 2;
    }

    /**
     * shouldFilter: Here you can write a logical judgment about whether to filter
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        //If the path matches the configuration, filter is performed
        RequestContext ctx = RequestContext.getCurrentContext();
        return (ctx.getRequest().getRequestURI() == null ||
                ctx.getRequest().getRequestURI().contains("/auth/"));
    }

    /**
     * Execute filter logic to add token to response content upon successful login
     *
     * @return
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        try {
            InputStream stream = ctx.getResponseDataStream();
            String body = StreamUtils.copyToString(stream, StandardCharsets.UTF_8);
            Result<HashMap<String, Object>> result = objectMapper.readValue(body, new TypeReference<Result<HashMap<String, Object>>>() {
            });
            //result.getCode() == 0 indicates successful login
            if (result.getCode() == 0) {
                HashMap<String, Object> jwtClaims = new HashMap<String, Object>() {{
                    put("userId", result.getData().get("userId"));
                }};
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, +7);

                Date expDate = cal.getTime(); //7 days overdue
                String token = jwtUtil.createJWT(expDate, jwtClaims);
                //body json adds token
                result.getData().put("token", token);
                //Serialize body json, set to response body
                body = objectMapper.writeValueAsString(result);
                ctx.setResponseBody(body);

                //Response header setting token
                ctx.addZuulResponseHeader("token", token);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}