package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

  private ResourceServerTokenServices tokenServices;

  @Autowired
  public ResourceServerConfig(ResourceServerTokenServices tokenServices) {
    this.tokenServices = tokenServices;
  }

  @Value("${security.jwt.resource-ids}")
  private String resourceIds;

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) {
    resources.resourceId(resourceIds).tokenServices(tokenServices);
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http
        .requestMatchers()
        .and()
        .authorizeRequests()
        .antMatchers("/api/**" ).authenticated()
        .antMatchers("/**").permitAll()
        // enable H2 /console
        .and().headers().frameOptions().disable();
  }
}
