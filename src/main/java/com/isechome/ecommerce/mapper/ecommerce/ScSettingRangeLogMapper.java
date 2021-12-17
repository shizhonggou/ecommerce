package com.isechome.ecommerce.mapper.ecommerce;

import com.isechome.ecommerce.entity.ScSettingRange;
import com.isechome.ecommerce.entity.ScSettingRangeLog;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ScSettingRangeLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ScSettingRangeLog record);

    ScSettingRangeLog selectByPrimaryKey(Integer id);

    List<ScSettingRangeLog> selectAll();

    int updateByPrimaryKey(ScSettingRangeLog record);


    void insertLogData(List<ScSettingRangeLog> scSettingRangeLogList);
}