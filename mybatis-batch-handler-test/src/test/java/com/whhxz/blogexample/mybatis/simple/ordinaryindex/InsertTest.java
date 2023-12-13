package com.whhxz.blogexample.mybatis.simple.ordinaryindex;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.InputStream;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class InsertTest {
    static SqlSessionFactory sqlSessionFactory;

    @BeforeClass
    public static void init() throws Exception {
        String resource = "simple/ordinaryindex/mybatis.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        InsertTest.sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void testInsert() throws Exception {
        Random random = new Random();
        int threadNum = 10;
        CountDownLatch latch = new CountDownLatch(threadNum);
        for (int j = 0; j < threadNum; j++) {
            final int t = j;
            new Thread(() -> {
                try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, true)) {
                    IDataInfoMapper mapper = sqlSession.getMapper(IDataInfoMapper.class);
                    int num = 10000;
                    for (int i = 1; i <= num; i++) {
                        if (i != 0 && i % 1000 == 0) {
                            sqlSession.flushStatements();
                        }
                        String name1 = String.valueOf(random.nextInt(99999999));
                        String name2 = String.valueOf(random.nextInt(99999999));
                        int id = t * num + i;
                        DataInfo data = new DataInfo(id, String.valueOf(id), String.valueOf(id), name1, name2);
                        mapper.insert(data);
                    }
                    sqlSession.flushStatements();
                } finally {
                    latch.countDown();
                }
            }).start();
        }
        latch.await();
    }
}