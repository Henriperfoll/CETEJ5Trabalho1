package com.example.cetej5trabalho.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class Interceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object controller)
			throws Exception {

		String uri = request.getRequestURI();

		if (uri.equals("/") ) {
			return true;
		}

		response.sendRedirect("/");
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, //
			Object handler, ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, //
			Object handler, Exception ex) throws Exception {

	}

}
//https://o7planning.org/en/11689/spring-boot-interceptors-tutorial