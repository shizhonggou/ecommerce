package com.isechome.ecommerce.mapper.ecommerce;

import com.isechome.ecommerce.entity.ScOrderList;
import java.util.List;
import java.util.Date;
import java.util.Map;

public interface ScOrderListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ScOrderList record);

    ScOrderList selectByPrimaryKey(Integer id);

    List<ScOrderList> selectAll();

    int updateByPrimaryKey(ScOrderList record);


    List<ScOrderList> getOrderList(Map<String,Object> map);

    ScOrderList getOrderByid(Integer id);

}