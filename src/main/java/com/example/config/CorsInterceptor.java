package com.example.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//用于打印请求信息
@Component
public class CorsInterceptor extends HandlerInterceptorAdapter {
//    LoggerFactory.getLoggery用于IDE控制台打印日志
    private static final Logger logger = LoggerFactory.getLogger(CorsInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        定义打印格式，并输出http请求的IP地址
        String logPattern = "[%s][%s]:%s";
        logger.info(String.format(logPattern, request.getMethod(), request.getRemoteAddr(), request.getRemoteAddr()));
        return true;
    }
}
