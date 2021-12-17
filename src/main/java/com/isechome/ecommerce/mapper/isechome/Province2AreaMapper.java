/*
 * @Author: shitaodi
 * @Date: 2021-09-08 18:01:18
 * @LastEditTime: 2021-09-09 08:29:06
 * @FilePath: \e-commerce\src\main\java\com\isechome\ecommerce\mapper\isechome\Province2AreaMapper.java
 * @Description: 
 * @Copyright: © 2021, SteelHome. All rights reserved.
 */
package com.isechome.ecommerce.mapper.isechome;

import com.isechome.ecommerce.entity.Province2Area;
import java.util.List;

public interface Province2AreaMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Province2Area record);

    Province2Area selectByPrimaryKey(Integer id);

    List<Province2Area> selectAll();
    
    List<Province2Area> selectallsf();

    int updateByPrimaryKey(Province2Area record);

    Province2Area selectByProviceId(Integer id);
    Province2Area selectByProviceName(String proviceName);
}