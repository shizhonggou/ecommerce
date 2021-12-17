/*
 * @Author: shitaodi
 * @Date: 2021-08-27 16:47:54
 * @LastEditTime: 2021-08-31 18:32:38
 * @FilePath: \e-commerce\src\main\java\com\isechome\ecommerce\mapper\ecommerce\ScCompanyBalanceMapper.java
 * @Description: 
 * @Copyright: © 2021, SteelHome. All rights reserved.
 */
package com.isechome.ecommerce.mapper.ecommerce;

import com.isechome.ecommerce.entity.ScCompanyBalance;
import java.util.List;

public interface ScCompanyBalanceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ScCompanyBalance record);

    ScCompanyBalance selectByPrimaryKey(Integer id);

    List<ScCompanyBalance> selectAll();

    int updateByPrimaryKey(ScCompanyBalance record);

    ScCompanyBalance getBalanceByComid(Integer pmid,Integer company_id);

    Integer updateBalanceByComid(Integer pmid,Integer company_id,Double balance);

    Integer updateBalanceByComidForSettlement(Integer pmid,Integer company_id,Double balance);
}