/*
 * @Author: lina
 * @Date: 2021-08-23 18:01:35
 * @LastEditTime: 2021-08-24 09:24:00
 * @FilePath: \e-commerce\src\main\java\com\isechome\ecommerce\mapper\ecommerce\ScModifyPriceMapper.java
 * @Description: 
 * @Copyright: © 2021, SteelHome. All rights reserved.
 */
package com.isechome.ecommerce.mapper.ecommerce;

import com.isechome.ecommerce.entity.ScModifyPrice;

import java.util.Date;
import java.util.List;

public interface ScModifyPriceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ScModifyPrice record);

    ScModifyPrice selectByPrimaryKey(Integer id);

    List<ScModifyPrice> selectAll();

    List<ScModifyPrice> selectListByPmid(Integer pmid, Date date);

    int updateByPrimaryKey(ScModifyPrice record);

    int statusDel(Integer pmid, Date date);

    List<ScModifyPrice> getTodayModifyPrice(int pmid, Byte status, Date date);

    List<ScModifyPrice> getTodayModifyPriceforCal(int pmid, Byte status, Date date);
}