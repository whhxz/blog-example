package com.whhxz.blogexample.ratelimiter;

import lombok.Getter;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 固定窗口计数器
 */
public class FixedWindowCounter extends TimerTask {
    private final AtomicInteger count = new AtomicInteger(0);
    private final int max;
    private final Timer timer;
    private final AtomicInteger total = new AtomicInteger(0);

    public FixedWindowCounter(int max) {
        this.max = max;
        timer = new Timer();
    }

    public void start() {
        timer.scheduleAtFixedRate(this, 0, 1000L);
    }

    /**
     * 判断是否能执行
     */
    public boolean take() {
        if (count.get() >= max) {
            return false;
        }
        if (count.incrementAndGet() <= max) {
            total.incrementAndGet();
            return true;
        }
        return false;
    }

    public int getTotal() {
        return total.get();
    }

    @Override
    public void run() {
        count.set(0);
    }
}
