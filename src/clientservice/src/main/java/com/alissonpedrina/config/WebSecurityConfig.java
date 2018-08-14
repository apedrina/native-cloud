package com.alissonpedrina.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Value("${security.basic.user}")
  private String user;

  @Value("${security.basic.password}")
  private String password;

  private static String REALM="AP_REALM";

  private static String URL_PATTERN = "/client/**";

  private static String ROLE = "ADMIN";

  @Autowired
  public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication().withUser(user).password(password).roles(ROLE);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.csrf().disable()
        .authorizeRequests()
        .antMatchers(URL_PATTERN).hasRole(ROLE)
        .and().httpBasic().realmName(REALM)
        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
  }
}