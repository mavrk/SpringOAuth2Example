package com.mavrk.springmvc.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

/**
 * Created by Sanatt on 17-05-2017.
 * TODO play with this thing to experiment
 */

@Configuration
@EnableResourceServer
public class ResourceServerServerConfiguration extends ResourceServerConfigurerAdapter {

	private static final String RESOURCE_ID = "my_rest_api";

	@Override
	public void configure(ResourceServerSecurityConfigurer configurer) {
		configurer.resourceId(RESOURCE_ID).stateless(false);
	}

	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.anonymous()
				.disable()
				.requestMatchers()
				.antMatchers("/user/**")
				.and().authorizeRequests()
				.antMatchers("/user/**").access("hasRole('ADMIN')")
				.and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}
}
