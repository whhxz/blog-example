package com.whhxz.blogexample.ratelimiter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

class TokenBucketTest {
    static TokenBucket tokenBucket;
    static AtomicInteger count = new AtomicInteger(0);

    @BeforeAll
    static void setUpBeforeClass() {
        tokenBucket = new TokenBucket(4, 2);
    }

    @Test
    public void thread() throws Exception {

        int threadNum = 5;
        List<Thread> threads = IntStream.range(0, threadNum).mapToObj(d -> new Thread(() -> {
            while (true) {
                boolean token = tokenBucket.take();
                if (token) {
                    count.incrementAndGet();
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                    System.out.printf("%s: %s success\n", format.format(new Date()), Thread.currentThread().getName());
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (InterruptedException ignored) {
                }
            }
        })).toList();

        tokenBucket.start();
        threads.forEach(Thread::start);
        TimeUnit.SECONDS.sleep(20);
        tokenBucket.stop();
        TimeUnit.SECONDS.sleep(2);

        Assertions.assertEquals(count.get(), tokenBucket.getTotal());
    }
}