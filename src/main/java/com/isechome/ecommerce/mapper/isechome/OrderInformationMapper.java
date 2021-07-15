/*
 * @Author: lina
 * @Date: 2021-06-07 09:25:41
 * @LastEditTime: 2021-06-15 10:54:05
 * @FilePath: \e-commerce\src\main\java\com\isechome\ecommerce\mapper\isechome\OrderInformationMapper.java
 * @Description: 
 * @Copyright: © 2021, SteelHome. All rights reserved.
 */
package com.isechome.ecommerce.mapper.isechome;

import com.isechome.ecommerce.entity.OrderInformation;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderInformationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderInformation record);

    OrderInformation selectByPrimaryKey(Integer id);

    List<OrderInformation> selectAll();

    int updateByPrimaryKey(OrderInformation record);

    int updateActual(Integer id, Double actual_piece, Double actual_num);

    List<OrderInformation> getOrderList(Map<String,Object> map);

    OrderInformation getOrderByid(Integer id);
    
    int updateStatus(Integer id, Byte status);

    int orderShenhe(Integer id, Integer confirm_user_id, Date confirm_time);

}