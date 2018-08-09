package com.alissonpedrina.filter;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BasicAuthRequestInterceptor implements RequestInterceptor {

    @Value("${security.basic.user}")
    private String user;

    @Value("${security.basic.password}")
    private String password;

    @Override
    public void apply(RequestTemplate template) {
        String encoding64 = Base64.getEncoder().encodeToString(String.format("%s:%s", user, password).getBytes());
        template.header("Authorization", String.format("Basic %s", encoding64));
    }

}
