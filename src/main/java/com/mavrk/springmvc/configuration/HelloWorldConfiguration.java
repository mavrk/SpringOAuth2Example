package com.mavrk.springmvc.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by Sanatt on 13-05-2017.
 * This Class is for testing only.
 * TODO add more functionality and experiment with this class
 */

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.mavrk.springmvc")
public class HelloWorldConfiguration {

}
