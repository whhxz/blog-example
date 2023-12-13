package com.whhxz.blogexample.mybatis.simple;

import com.whhxz.blogexample.mybatis.IDbUpdateHandler;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.InputStream;
import java.util.Random;

public class InsertTest {
    static SqlSessionFactory sqlSessionFactory;
    static IDbUpdateHandler handler;

    @BeforeClass
    public static void init() throws Exception {
        String resource = "simple/mybatis.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        handler = new DbUpdateHandler(sqlSessionFactory);
        InsertTest.sqlSessionFactory = sqlSessionFactory;
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
}