package com.whhxz.blogexample.log4j2.trace.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.filter.DynamicThresholdFilter;
import org.apache.logging.log4j.core.util.KeyValuePair;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Locale;

@SuppressWarnings("NullableProblems")
public class DynamicLogLevelInterceptor implements HandlerInterceptor {

    private final Object obj = new Object();
    private volatile boolean init = false;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String logLevel = request.getHeader("x-log-level");
        if (logLevel != null && !logLevel.isEmpty()) {
            initLogFilter();
            MDC.put("x-log-level", logLevel.toLowerCase(Locale.ROOT));
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.remove("x-log-level");
    }

    private void initLogFilter() {
        //验证是否初始化完成
        if (init) {
            return;
        }
        synchronized (obj) {
            if (init) {
                return;
            }
            //需要先获取LoggerContext，如果有对日志做二次开发，需要想办法从其他地方获取
            LoggerContext context = LoggerContext.getContext(false);
            //配置值对应日志
            KeyValuePair tracePair = new KeyValuePair("trace", "TRACE");
            KeyValuePair debugPair = new KeyValuePair("debug", "DEBUG");
            KeyValuePair[] pairs = {tracePair, debugPair};
            DynamicThresholdFilter filter = DynamicThresholdFilter.createFilter("x-log-level", pairs, Level.INFO, Filter.Result.ACCEPT, Filter.Result.NEUTRAL);

            context.addFilter(filter);
            //更新
            context.updateLoggers();
        }
        init = true;
    }
}
