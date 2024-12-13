package com.whhxz.blogexample.log4j2.trace.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@SuppressWarnings({"UastIncorrectHttpHeaderInspection", "NullableProblems"})
public class LogTraceInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String traceId = request.getHeader("x-trace-id");
        if (traceId == null || traceId.isEmpty()) {
            traceId = UUID.randomUUID().toString().replace("-", "").substring(0, 12);
        }

        MDC.put("traceId", traceId);
        //此处只能设置在这里，不能设置在postHandler和afterCompletion里
        //因为那时候response可能已经返回了，再设置无效
        response.addHeader("x-trace-id", traceId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.remove("traceId");
    }
}
