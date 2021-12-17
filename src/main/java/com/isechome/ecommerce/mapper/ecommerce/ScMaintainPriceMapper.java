package com.isechome.ecommerce.mapper.ecommerce;

import com.isechome.ecommerce.entity.ScMaintainPrice;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ScMaintainPriceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ScMaintainPrice record);

    ScMaintainPrice selectByPrimaryKey(Integer id);

    List<ScMaintainPrice> selectAll();

    int updateByPrimaryKey(ScMaintainPrice record);

    void updateTodayPriceInvalid(@Param("pmid")int pmid, @Param("date")Date date,  @Param("kind")Byte kind);

    void insertPriceList(List<ScMaintainPrice> scMaintainPriceList);
}