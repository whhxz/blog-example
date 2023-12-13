package com.whhxz.blogexample.mybatis.simple;

import com.whhxz.blogexample.mybatis.IDbHandler;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

public class SimpleDbHandler implements IDbHandler {
    public static void main(String[] args) throws Exception {
        String resource = "simple/mybatis.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        IDataInfoMapper mapper = sqlSession.getMapper(IDataInfoMapper.class);
        Integer demo = mapper.demo();
        System.out.println(demo);
    }
}
