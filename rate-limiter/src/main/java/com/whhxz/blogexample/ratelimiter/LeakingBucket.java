package com.whhxz.blogexample.ratelimiter;

import lombok.Getter;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 漏桶算法
 */
public class LeakingBucket {
    private final LinkedBlockingQueue<Runnable> queue;
    private final Handler handler;

    public LeakingBucket(int size, int fillSpeed) {
        queue = new LinkedBlockingQueue<>(size);
        handler = new Handler(fillSpeed);
    }

    public void start() {
        handler.start();
    }

    public int getTotal() {
        return handler.total;
    }

    /**
     * 添加任务
     */
    public boolean add(Runnable runnable) {
        if (queue.remainingCapacity() == 0) {
            return false;
        }
        try {
            queue.add(runnable);
        } catch (IllegalStateException e) {
            return false;
        }
        return true;
    }

    /**
     * 处理任务
     */
    private Runnable task() {
        return queue.poll();
    }


    /**
     * 任务处理器
     */
    private class Handler extends TimerTask {
        private final int speed;
        private final Timer timer;

        private int total = 0;

        public Handler(int speed) {
            this.speed = speed;
            timer = new Timer();
        }

        public void start() {
            timer.scheduleAtFixedRate(this, 0, 1000L);
        }

        @Override
        public void run() {
            for (int i = 0; i < speed; i++) {
                Runnable task = LeakingBucket.this.task();
                if (task == null) {
                    break;
                }
                task.run();
                total += 1;
            }
        }
    }
}
