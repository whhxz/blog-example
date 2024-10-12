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

class FixedWindowCounterTest {
    static FixedWindowCounter fixedWindowCounter;

    @BeforeAll
    static void setUpBeforeClass() {
        fixedWindowCounter = new FixedWindowCounter(4);
    }

    @Test
    public void thread() throws Exception {
        AtomicInteger count = new AtomicInteger(0);
        int threadNum = 10;
        AtomicBoolean stop = new AtomicBoolean(false);
        List<Thread> threads = IntStream.range(0, threadNum).mapToObj(d -> new Thread(() -> {
            while (!stop.get()) {
                String threadName = Thread.currentThread().getName();
                if (fixedWindowCounter.take()) {
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                    System.out.printf("%s: %s success\n", format.format(new Date()), threadName);
                    count.incrementAndGet();
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (InterruptedException ignored) {
                }
            }
        })).toList();

        fixedWindowCounter.start();
        threads.forEach(Thread::start);
        TimeUnit.SECONDS.sleep(20);
        stop.set(true);
        TimeUnit.SECONDS.sleep(2);
        Assertions.assertEquals(count.get(), fixedWindowCounter.getTotal());

    }
}