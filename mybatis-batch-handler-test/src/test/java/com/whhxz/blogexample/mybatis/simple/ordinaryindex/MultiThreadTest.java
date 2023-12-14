package com.whhxz.blogexample.mybatis.simple.ordinaryindex;

import com.whhxz.blogexample.mybatis.IDbUpdateHandler;
import com.whhxz.blogexample.mybatis.util.TimeRecord;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.InputStream;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

public class MultiThreadTest {
    static SqlSessionFactory sqlSessionFactory;
    static Thread[] threads;
    static CountDownLatch latch;

    @BeforeClass
    public static void init() throws Exception {
        String resource = "simple/ordinaryindex/mybatis.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        threads = new Thread[10];
        latch = new CountDownLatch(threads.length);
    }

    @Test
    public void update() throws Exception {
        //1w/1k 87.178000
        //1w/100 71.128000
        TimeRecord timeRecord = new TimeRecord();
        newThread((d) -> d.update(10000, 100));
        startThread();
        latch.await();
        timeRecord.stop();
    }

    @Test
    public void updateBatch() throws Exception {
        //1w/1k 58.657000
        //1w/100 61.239000
        TimeRecord timeRecord = new TimeRecord();
        newThread((d) -> d.updateBatch(10000, 100));
        startThread();
        latch.await();
        timeRecord.stop();
    }

    @Test
    public void updateMultiSql() throws Exception{
        //1w/1k 47.542000
        //1w/100 46.966000
        TimeRecord timeRecord = new TimeRecord();
        newThread((d) -> d.updateMultiSql(10000, 100));
        startThread();
        latch.await();
        timeRecord.stop();
    }

    @Test
    public void updateTempTable()throws Exception {
        //1w/1k
        //1w/10  9.674000
        TimeRecord timeRecord = new TimeRecord();
        newThread((d) -> d.updateTempTable(10000, 10));
        startThread();
        latch.await();
        timeRecord.stop();
    }

    public static void newThread(Consumer<IDbUpdateHandler> consumer) {
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                try {
                    IDbUpdateHandler handler = new DbUpdateHandler(sqlSessionFactory);
                    consumer.accept(handler);
                } finally {
                    latch.countDown();
                }
            });
        }
    }

    public static void startThread() {
        for (Thread thread : threads) {
            thread.start();
        }
    }
}