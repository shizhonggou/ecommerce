/*
 * @Author: lina
 * @Date: 2021-08-19 09:13:59
 * @LastEditTime: 2021-08-20 10:33:40
 * @FilePath: \e-commerce\src\main\java\com\isechome\ecommerce\mapper\ecommerce\ScSteelMillMapper.java
 * @Description: 
 * @Copyright: © 2021, SteelHome. All rights reserved.
 */
package com.isechome.ecommerce.mapper.ecommerce;

import com.isechome.ecommerce.entity.ScSteelMill;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ScSteelMillMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ScSteelMill record);

    ScSteelMill selectByPrimaryKey(Integer id);

    List<ScSteelMill> selectAll();

    int updateByPrimaryKey(ScSteelMill record);

    List<ScSteelMill> getScSteelMilList(Map<String,Object> map);

    //获取钢厂 件重信息
    List<ScSteelMill> getSteelMillAndWeight(Integer pmid);

    int updateStatusById(Integer id, Byte status);
}