package com.isechome.ecommerce.mapper.ecommerce;

import com.isechome.ecommerce.entity.ScTodayBasePriceInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ScTodayBasePriceInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ScTodayBasePriceInfo record);

    ScTodayBasePriceInfo selectByPrimaryKey(Integer id);

    List<ScTodayBasePriceInfo> selectAll();

    int updateByPrimaryKey(ScTodayBasePriceInfo record);

    void review();

    List<ScTodayBasePriceInfo> getTodayBasePrice(@Param("date") Date date, @Param("pmid")int pmid );

    void setTodayBasePriceInvalid( @Param("date")Date date, @Param("pmid")int pmid, @Param("reviewStatus")int reviewStatus );

    void insertListData(List<ScTodayBasePriceInfo> scTodayBasePriceInfoList);

    List<ScTodayBasePriceInfo> getTodayBasePriceLog(@Param("pmid")int pmid);

    List<ScTodayBasePriceInfo> getTodayBasePriceReview(@Param("date") Date date, @Param("pmid")int pmid);
}