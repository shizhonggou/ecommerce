package com.isechome.ecommerce.mapper.isechome;

import com.isechome.ecommerce.entity.LogisticsInformation;
import java.util.List;

public interface LogisticsInformationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LogisticsInformation record);

    LogisticsInformation selectByPrimaryKey(Integer id);

    List<LogisticsInformation> selectAll();

    int updateByPrimaryKey(LogisticsInformation record);

    List<LogisticsInformation> selectByOrderId(Integer order_id);
}