package com.whhxz.blogexample.mybatis.simple;

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
        String resource = "simple/mybatis.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        threads = new Thread[10];
        latch = new CountDownLatch(threads.length);
    }

    @Test
    public void update() throws Exception {
        //68.440000
        TimeRecord timeRecord = new TimeRecord();
        newThread((d) -> d.update(10000, 1000));
        startThread();
        latch.await();
        timeRecord.stop();
    }

    @Test
    public void updateBatch() throws Exception {
        //57.372000
        TimeRecord timeRecord = new TimeRecord();
        newThread((d) -> d.updateBatch(10000, 1000));
        startThread();
        latch.await();
        timeRecord.stop();
    }

    @Test
    public void updateMultiSql() throws Exception{
        //45.233000
        TimeRecord timeRecord = new TimeRecord();
        newThread((d) -> d.updateMultiSql(10000, 1000));
        startThread();
        latch.await();
        timeRecord.stop();
    }

    @Test
    public void updateTempTable()throws Exception {
        TimeRecord timeRecord = new TimeRecord();
        newThread((d) -> d.updateTempTable(10000, 1000));
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