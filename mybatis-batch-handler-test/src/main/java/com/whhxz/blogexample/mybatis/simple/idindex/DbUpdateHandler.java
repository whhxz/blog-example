package com.whhxz.blogexample.mybatis.simple.idindex;

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
            for (int i = 0; i < max; i++) {
                int id = random.nextInt(max);
                String tmp = String.valueOf(random.nextInt(99999999));
                DataInfo data = new DataInfo(id, tmp, null);
                mapper.update(data);
            }
        }
    }

    @Override
    public void updateBatch(int max, int maxOneTime) {
        Random random = new Random();
        try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, true)) {
            IDataInfoMapper mapper = sqlSession.getMapper(IDataInfoMapper.class);
            for (int i = 0; i < max; i++) {
                if (i != 0 && i % maxOneTime == 0) {
                    sqlSession.flushStatements();
                    sqlSession.commit();
                }
                int id = random.nextInt(max);
                String tmp = String.valueOf(random.nextInt(99999999));
                DataInfo data = new DataInfo(id, tmp, null);
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

            List<DataInfo> datas = new ArrayList<>(1000);
            for (int i = 0; i < max; i++) {
                if (i != 0 && i % maxOneTime == 0) {
                    mapper.updateList1(datas);
                    datas = new ArrayList<>(1000);
                }
                int id = random.nextInt(max);
                String tmp = String.valueOf(random.nextInt(99999999));
                DataInfo data = new DataInfo(id, tmp, null);
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
            List<DataInfo> datas = new ArrayList<>(maxOneTime);
            for (int i = 0; i < max; i++) {
                if (i != 0 && i % maxOneTime == 0) {
                    mapper.updateList2(datas);
                    datas = new ArrayList<>(maxOneTime);
                }
                int id = random.nextInt(max);
                String tmp = String.valueOf(random.nextInt(99999999));
                DataInfo data = new DataInfo(id, tmp, null);
                datas.add(data);
            }
            if (!datas.isEmpty()) {
                mapper.updateList2(datas);
            }
        }
    }
}
