package com.whhxz.blogexample.mybatis.simple.ordinaryindex;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Collection;

public interface IDataInfoMapper {
    @Select("select id from simple_ordinary_index order by id desc limit 1")
    int maxId();
    void insert(@Param("item") DataInfo data);

    void update(@Param("item") DataInfo data);

    void updateList1(@Param("datas") Collection<DataInfo> datas);

    void updateList2(@Param("datas") Collection<DataInfo> datas);
}
