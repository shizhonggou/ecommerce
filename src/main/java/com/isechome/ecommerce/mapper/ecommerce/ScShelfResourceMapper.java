package com.isechome.ecommerce.mapper.ecommerce;

import com.isechome.ecommerce.entity.*;
import com.isechome.ecommerce.entity.ScShelfResource;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ScShelfResourceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ScShelfResource record);

    ScShelfResource selectByPrimaryKey(Integer id);

    List<ScShelfResource> selectAll();

    int updateByPrimaryKey(ScShelfResource record);

    List<ScShelfResource> getScTodayResourceByPmid(@Param("today")Date today, @Param("pmid")int pmid);

    int updateSoldNumById(Integer id,Double sold_num);

    List<ScShelfResource> getExcessResource(Map<String,Object> map);

    int updateSoldNumByRid(Integer id,Double sold_num);

    List<ScShelfResource> selectAllByPmid(Integer pmid);
    
}