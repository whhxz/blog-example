package com.whhxz.blogexample.log4j2.trace.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

    @GetMapping("log")
    public void log() {
        logger.trace("trace");
        logger.debug("debug");
        logger.info("info");
        logger.warn("warn");
        logger.error("error");
    }

    @GetMapping("threadlog")
    public void threadLog() throws Exception {
        logger.trace("trace");
        logger.debug("debug");
        logger.info("info");
        logger.warn("warn");
        logger.error("error");

        Thread thread = new Thread(() -> {
            logger.trace("thread-trace");
            logger.debug("thread-debug");
            logger.info("thread-info");
            logger.warn("thread-warn");
            logger.error("thread-error");

        });
        thread.start();
        thread.join();
    }
}
