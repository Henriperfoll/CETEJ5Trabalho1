package com.example.cetej5trabalho;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.cetej5trabalho.interceptor.Interceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
	 
	   @Override
	   public void addInterceptors(InterceptorRegistry registry) {
	      // LogInterceptor apply to all URLs.
	      registry.addInterceptor(new Interceptor());
	 
	   }
}