/*
 * @Author: lina
 * @Date: 2021-08-24 10:36:29
 * @LastEditTime: 2021-09-18 10:19:16
 * @FilePath: \e-commerce\src\main\java\com\isechome\ecommerce\mapper\ecommerce\ScSysStatusMapper.java
 * @Description: 
 * @Copyright: © 2021, SteelHome. All rights reserved.
 */
package com.isechome.ecommerce.mapper.ecommerce;

import com.isechome.ecommerce.entity.ScSysStatus;

import java.util.Date;
import java.util.List;

public interface ScSysStatusMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ScSysStatus record);

    ScSysStatus selectByPrimaryKey(Integer id);

    List<ScSysStatus> selectAll();

    int updateByPrimaryKey(ScSysStatus record);

    int updateByPmid(ScSysStatus record);

    ScSysStatus selTodyStatus(Integer pmid, Date date);
}