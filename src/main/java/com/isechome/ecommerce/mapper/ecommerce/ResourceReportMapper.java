package com.isechome.ecommerce.mapper.ecommerce;

import com.isechome.ecommerce.entity.ResourceReport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceReportMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ResourceReport record);

    ResourceReport selectByPrimaryKey(Integer id);

    List<ResourceReport> selectAll();

    int updateByPrimaryKey(ResourceReport record);
}