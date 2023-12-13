package com.whhxz.blogexample.mybatis.simple;

import com.whhxz.blogexample.mybatis.IDbUpdateHandler;
import com.whhxz.blogexample.mybatis.util.TimeRecord;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.InputStream;

public class OneThreadTest {
    static IDbUpdateHandler handler;

    @BeforeClass
    public static void init() throws Exception {
        String resource = "simple/mybatis.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        handler = new DbUpdateHandler(sqlSessionFactory);
    }

    @Test
    public void update() {
        //122.900000
        TimeRecord timeRecord = new TimeRecord();
        handler.update(2000, 1000);
        timeRecord.stop();
    }

    @Test
    public void updateBatch() {
        //63.992000
        TimeRecord record = new TimeRecord();
        handler.updateBatch(2000, 1000);
        record.stop();
    }

    @Test
    public void updateMultiSql() {
        //13.202000
        TimeRecord record = new TimeRecord();
        handler.updateMultiSql(2000, 1000);
        record.stop();

    }

    @Test
    public void updateTempTable() {
        //3.874000
        TimeRecord record = new TimeRecord();
        handler.updateTempTable(2000, 1000);
        record.stop();
    }
}