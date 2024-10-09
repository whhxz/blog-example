package com.whhxz.blogexample.ratelimiter;

import lombok.Getter;

import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 令牌桶
 */
public class TokenBucket {
    private final LinkedBlockingQueue<Boolean> queue;
    private final ReFiller reFiller;
    @Getter
    private int total = 0;

    /**
     * @param size      桶大小
     * @param fillSpeed 填充速度 /s
     */
    public TokenBucket(int size, int fillSpeed) {
        queue = new LinkedBlockingQueue<>(size);
        reFiller = new ReFiller(fillSpeed);
    }

    public void start() {
        reFiller.start();
    }

    public void stop() {
        reFiller.stop();
    }

    /**
     * 添加令牌
     *
     * @param num 令牌数量
     */
    private int add(int num) {
        int total = 0;
        for (int i = 0; i < num; i++) {
            try {
                queue.add(true);
            } catch (IllegalStateException e) {
                break;
            }
            total++;
        }
        this.total += total;
        return total;
    }

    /**
     * 取数据
     *
     * @return 返回令牌
     */
    public boolean take() {
        return Optional.ofNullable(queue.poll()).orElse(false);
    }

    /**
     * 重新填充
     */
    private class ReFiller extends TimerTask {
        private final int speed;
        private final Timer timer;

        public ReFiller(int speed) {
            this.speed = speed;
            timer = new Timer();
        }

        public void start() {
            timer.scheduleAtFixedRate(this, 0, 1000L);
        }

        @Override
        public void run() {
            TokenBucket.this.add(speed);
        }

        public void stop() {
            timer.cancel();
        }
    }
}
