package com.whhxz.blogexample.mybatis.simple;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IDataInfoMapperTest {
    static SqlSessionFactory sqlSessionFactory;

    @BeforeClass
    public static void init() throws Exception {
        String resource = "simple/mybatis.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    @Ignore
    public void testInsert() {
        Random random = new Random();
        try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
            IDataInfoMapper mapper = sqlSession.getMapper(IDataInfoMapper.class);
            for (int i = 0; i < 100000; i++) {
                if (i != 0 && i % 1000 == 0) {
                    sqlSession.flushStatements();
                }
                String tmp = String.valueOf(random.nextInt(99999999));
                DataInfo data = new DataInfo(null, tmp, tmp);
                mapper.insert(data);
            }
            sqlSession.flushStatements();
            sqlSession.commit();
        }
    }

    @Test
    public void testUpdate() {
        long start = System.currentTimeMillis();
        Random random = new Random();
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            IDataInfoMapper mapper = sqlSession.getMapper(IDataInfoMapper.class);
            int id = random.nextInt(100000);
            for (int i = 0; i < 100000; i++) {
                if (i != 0 && i % 1000 == 0) {
                    sqlSession.flushStatements();
                }
                String tmp = String.valueOf(random.nextInt(99999999));
                DataInfo data = new DataInfo(id, tmp, null);
                mapper.update(data);
            }
            sqlSession.flushStatements();
            sqlSession.rollback();
        }
        //90.959000
        System.out.printf("update耗时%f\n", (System.currentTimeMillis() - start) / 1000.0);
    }
    @Test
    public void testUpdate2() {
        long start = System.currentTimeMillis();
        Random random = new Random();
        try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
            IDataInfoMapper mapper = sqlSession.getMapper(IDataInfoMapper.class);
            int id = random.nextInt(100000);
            for (int i = 0; i < 100000; i++) {
                if (i != 0 && i % 1000 == 0) {
                    sqlSession.flushStatements();
                }
                String tmp = String.valueOf(random.nextInt(99999999));
                DataInfo data = new DataInfo(id, tmp, null);
                mapper.update(data);
            }
            sqlSession.flushStatements();
            sqlSession.rollback();
        }
        //48.242000
        System.out.printf("update耗时%f\n", (System.currentTimeMillis() - start) / 1000.0);
    }

    @Test
    public void testUpdateList1() {
        long start = System.currentTimeMillis();
        Random random = new Random();
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            IDataInfoMapper mapper = sqlSession.getMapper(IDataInfoMapper.class);
            int id = random.nextInt(100000);
            List<DataInfo> datas = new ArrayList<>(1000);
            for (int i = 0; i < 100000; i++) {
                if (i != 0 && i % 1000 == 0) {
                    mapper.updateList1(datas);
                    datas = new ArrayList<>(1000);
                }
                String tmp = String.valueOf(random.nextInt(99999999));
                DataInfo data = new DataInfo(id, tmp, null);
                datas.add(data);
            }
            if (!datas.isEmpty()) {
                mapper.updateList1(datas);
            }
            sqlSession.rollback();
        }
        //9.479000
        System.out.printf("UpdateList1耗时%f\n", (System.currentTimeMillis() - start) / 1000.0);
    }

    @Test
    public void testUpdateList2() {
        long start = System.currentTimeMillis();
        Random random = new Random();
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            IDataInfoMapper mapper = sqlSession.getMapper(IDataInfoMapper.class);
            int id = random.nextInt(100000);
            List<DataInfo> datas = new ArrayList<>(1000);
            for (int i = 0; i < 100000; i++) {
                if (i != 0 && i % 1000 == 0) {
                    mapper.updateList2(datas);
                    datas = new ArrayList<>(1000);
                }
                String tmp = String.valueOf(random.nextInt(99999999));
                DataInfo data = new DataInfo(id, tmp, null);
                datas.add(data);
            }
            if (!datas.isEmpty()) {
                mapper.updateList2(datas);
            }
            sqlSession.rollback();
        }
        //2.092000
        System.out.printf("UpdateList2耗时%f\n", (System.currentTimeMillis() - start) / 1000.0);

    }
}