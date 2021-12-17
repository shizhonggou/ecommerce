package com.isechome.ecommerce.mapper.ecommerce;

import com.isechome.ecommerce.entity.ScWorkTime;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScWorkTimeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ScWorkTime record);

    ScWorkTime selectByPrimaryKey(Integer id);

    ScWorkTime selectByPmid(Integer pmid);

    List<ScWorkTime> selectAll();

    int updateByPrimaryKey(ScWorkTime record);
}