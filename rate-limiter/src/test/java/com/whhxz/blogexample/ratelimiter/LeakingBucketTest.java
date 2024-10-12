package com.whhxz.blogexample.ratelimiter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

class LeakingBucketTest {
    static LeakingBucket leakingBucket;
    static AtomicInteger count = new AtomicInteger(0);

    @BeforeAll
    static void setUpBeforeClass() {
        leakingBucket = new LeakingBucket(4, 2);
    }

    @Test
    public void thread() throws Exception {
        int threadNum = 5;
        AtomicBoolean stop = new AtomicBoolean(false);
        List<Thread> threads = IntStream.range(0, threadNum).mapToObj(d -> new Thread(() -> {
            while (!stop.get()) {
                String threadName = Thread.currentThread().getName();
                leakingBucket.add(() -> {
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                    System.out.printf("%s: %s success\n", format.format(new Date()), threadName);
                    count.incrementAndGet();
                });
                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (InterruptedException ignored) {
                }
            }
        })).toList();

        leakingBucket.start();
        threads.forEach(Thread::start);
        TimeUnit.SECONDS.sleep(20);
        stop.set(true);
        TimeUnit.SECONDS.sleep(2);
        Assertions.assertEquals(count.get(), leakingBucket.getTotal());
    }
}