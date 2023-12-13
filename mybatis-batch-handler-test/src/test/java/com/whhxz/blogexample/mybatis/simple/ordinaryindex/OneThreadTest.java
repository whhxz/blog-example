package com.whhxz.blogexample.mybatis.simple.ordinaryindex;

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
        String resource = "simple/ordinaryindex/mybatis.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        handler = new DbUpdateHandler(sqlSessionFactory);
    }

    @Test
    public void update() {
        //10w/1k 445.710000
        //1w/1k 47.678000
        //1w/100 46.739000
        TimeRecord timeRecord = new TimeRecord();
        handler.update(10000, 100);
        timeRecord.stop();
    }

    @Test
    public void updateBatch() {
        //10w/1k 1178.840000
        //1w/1k 42.363000
        //1w/100 41.520000
        TimeRecord record = new TimeRecord();
        handler.updateBatch(10000, 1000);
        record.stop();
    }

    @Test
    public void updateMultiSql() {
        //10w/1k 343.241000
        //1w/1k 35.251000
        //1w/100 100.464000
        TimeRecord record = new TimeRecord();
        handler.updateMultiSql(10000, 1000);
        record.stop();
    }

    @Test
    public void updateTempTable() {
        //10w/1k 4.318000
        //1w/1k 1.137000
        //1w/100 1.636000
        TimeRecord record = new TimeRecord();
        handler.updateTempTable(10000, 100);
        record.stop();
    }
}