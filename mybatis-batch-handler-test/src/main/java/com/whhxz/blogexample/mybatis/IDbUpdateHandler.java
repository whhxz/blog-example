package com.whhxz.blogexample.mybatis;

public interface IDbUpdateHandler {
    void update(int max, int maxOneTime);

    void updateBatch(int max, int maxOneTime);

    void updateMultiSql(int max, int maxOneTime);

    void updateTempTable(int max, int maxOneTime);
}
