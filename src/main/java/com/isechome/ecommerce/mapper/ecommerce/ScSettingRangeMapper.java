package com.isechome.ecommerce.mapper.ecommerce;

import com.isechome.ecommerce.entity.ScSettingRange;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ScSettingRangeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ScSettingRange record);

    ScSettingRange selectByPrimaryKey(Integer id);

    List<ScSettingRange> selectAll();

    int updateByPrimaryKey(ScSettingRange record);

    ScSettingRange getSettingRangeDateByKind(@Param("pmid")int pmid, @Param("kind")int kind, @Param("date") Date date);

    List<ScSettingRange> getSettingRangeInfoByKind(@Param("pmid") int pmid, @Param("kind") int kind, @Param("date") Date date, @Param("reviewType")Byte reviewType);

    int insertList(List<ScSettingRange> scSettingRangeList);

    void deleteByDateAndStatus(@Param("date")Date today, @Param("kind")Byte kind, @Param("pmid")int pmid, @Param("reviewType")Byte reviewType);

    ScSettingRange getSettingRangeReviewDateByKind(@Param("pmid")int pmid, @Param("kind")int kind, @Param("date") Date date);

    List<ScSettingRange> getckname(Integer pmid,Byte kind);

}