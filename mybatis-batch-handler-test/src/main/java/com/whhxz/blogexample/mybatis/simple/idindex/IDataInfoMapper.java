package com.whhxz.blogexample.mybatis.simple.idindex;

import org.apache.ibatis.annotations.Param;

import java.util.Collection;

public interface IDataInfoMapper {
    void insert(@Param("item") DataInfo data);

    void update(@Param("item") DataInfo data);

    void updateList1(@Param("datas") Collection<DataInfo> datas);

    void updateList2(@Param("datas") Collection<DataInfo> datas);
}
