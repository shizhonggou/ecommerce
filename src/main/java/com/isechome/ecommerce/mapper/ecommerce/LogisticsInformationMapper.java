package com.isechome.ecommerce.mapper.ecommerce;

import com.isechome.ecommerce.entity.LogisticsInformation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogisticsInformationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LogisticsInformation record);

    LogisticsInformation selectByPrimaryKey(Integer id);

    List<LogisticsInformation> selectAll();

    int updateByPrimaryKey(LogisticsInformation record);

    List<LogisticsInformation> selectByOrderId(Integer order_id);
}