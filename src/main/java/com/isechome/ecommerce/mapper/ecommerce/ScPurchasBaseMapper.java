/*
 * @Author: shitaodi
 * @Date: 2021-09-18 15:29:08
 * @LastEditTime: 2021-10-10 14:56:10
 * @FilePath: \e-commerce\src\main\java\com\isechome\ecommerce\mapper\ecommerce\ScPurchasBaseMapper.java
 * @Description: 
 * @Copyright: © 2021, SteelHome. All rights reserved.
 */
package com.isechome.ecommerce.mapper.ecommerce;

import com.isechome.ecommerce.entity.ScPurchasBase;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ScPurchasBaseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ScPurchasBase record);

    ScPurchasBase selectByPrimaryKey(Integer id);

    List<ScPurchasBase> selectAll();

    int updateByPrimaryKey(ScPurchasBase record);

    int insertPurchasBase(ScPurchasBase record);

    ScPurchasBase selectByResourceId(Integer resource_id);

    int updatePurchaseNumByid(Integer id,Double purchase_num);

    List<ScPurchasBase> getPurchasBaseList(Map<String,Object> map);

    int updatePurchaseById(ScPurchasBase record);


    Integer getPurchaseIdBymoney(Integer pmid,Integer supplier_mid,Double purchase_amount);

    int purchaseMoneyMach(Integer id,Byte type);

    // 获取采购资源未分配采购单信息
    List<ScPurchasBase> selectUnassignedList(Integer id);

    // 匹配未分配采购单
    int updateByMatchResource(ScPurchasBase record);
}