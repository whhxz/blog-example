package com.whhxz.blogexample.mybatis.simple.ordinaryindex;

import com.whhxz.blogexample.mybatis.IDbUpdateHandler;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DbUpdateHandler implements IDbUpdateHandler {
    private SqlSessionFactory sqlSessionFactory;

    public DbUpdateHandler(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public void update(int max, int maxOneTime) {
        Random random = new Random();
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            IDataInfoMapper mapper = sqlSession.getMapper(IDataInfoMapper.class);
            int maxId = mapper.maxId();
            for (int i = 0; i < max; i++) {
                String code1 = String.valueOf(random.nextInt(maxId) + 1);
                String name1 = String.valueOf(random.nextInt(99999999));
                DataInfo data = new DataInfo(null, code1, null, name1, null);
                mapper.update(data);
            }
        }
    }

    @Override
    public void updateBatch(int max, int maxOneTime) {
        Random random = new Random();
        try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, true)) {
            IDataInfoMapper mapper = sqlSession.getMapper(IDataInfoMapper.class);
            int maxId = mapper.maxId();
            for (int i = 0; i < max; i++) {
                if (i != 0 && i % maxOneTime == 0) {
                    sqlSession.flushStatements();
                }
                String code1 = String.valueOf(random.nextInt(maxId) + 1);
                String name1 = String.valueOf(random.nextInt(99999999));
                DataInfo data = new DataInfo(null, code1, null, name1, null);
                mapper.update(data);
            }
            sqlSession.flushStatements();
        }
    }

    @Override
    public void updateMultiSql(int max, int maxOneTime) {
        Random random = new Random();
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            IDataInfoMapper mapper = sqlSession.getMapper(IDataInfoMapper.class);
            int maxId = mapper.maxId();
            List<DataInfo> datas = new ArrayList<>(1000);
            for (int i = 0; i < max; i++) {
                if (i != 0 && i % maxOneTime == 0) {
                    mapper.updateList1(datas);
                    datas = new ArrayList<>(1000);
                }
                String code1 = String.valueOf(random.nextInt(maxId) + 1);
                String name1 = String.valueOf(random.nextInt(99999999));
                DataInfo data = new DataInfo(null, code1, null, name1, null);
                datas.add(data);
            }
            if (!datas.isEmpty()) {
                mapper.updateList1(datas);
            }
        }
    }

    @Override
    public void updateTempTable(int max, int maxOneTime) {
        Random random = new Random();
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            IDataInfoMapper mapper = sqlSession.getMapper(IDataInfoMapper.class);
            int maxId = mapper.maxId();
            List<DataInfo> datas = new ArrayList<>(maxOneTime);
            for (int i = 0; i < max; i++) {
                if (i != 0 && i % maxOneTime == 0) {
                    mapper.updateList2(datas);
                    datas = new ArrayList<>(maxOneTime);
                }
                String code1 = String.valueOf(random.nextInt(maxId) + 1);
                String name1 = String.valueOf(random.nextInt(99999999));
                DataInfo data = new DataInfo(null, code1, null, name1, null);
                datas.add(data);
            }
            if (!datas.isEmpty()) {
                mapper.updateList2(datas);
            }
        }
    }
}
