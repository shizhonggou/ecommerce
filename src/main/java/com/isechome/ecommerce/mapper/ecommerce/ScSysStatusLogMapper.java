/*
 * @Author: lina
 * @Date: 2021-08-24 15:25:57
 * @LastEditTime: 2021-08-25 09:28:21
 * @FilePath: \e-commerce\src\main\java\com\isechome\ecommerce\mapper\ecommerce\ScSysStatusLogMapper.java
 * @Description: 
 * @Copyright: © 2021, SteelHome. All rights reserved.
 */
package com.isechome.ecommerce.mapper.ecommerce;

import com.isechome.ecommerce.entity.ScSysStatusLog;
import java.util.List;

public interface ScSysStatusLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ScSysStatusLog record);

    ScSysStatusLog selectByPrimaryKey(Integer id);

    List<ScSysStatusLog> selectAll();

    List<ScSysStatusLog> getStatusLogList(Integer pmid);

    int updateByPrimaryKey(ScSysStatusLog record);
}