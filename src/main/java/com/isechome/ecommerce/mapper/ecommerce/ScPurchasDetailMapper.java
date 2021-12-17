package com.isechome.ecommerce.mapper.ecommerce;

import com.isechome.ecommerce.entity.ScPurchasDetail;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.math.BigDecimal;

@Repository
public interface ScPurchasDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ScPurchasDetail record);

    ScPurchasDetail selectByPrimaryKey(Integer id);

    List<ScPurchasDetail> selectAll();

    int updateByPrimaryKey(ScPurchasDetail record);

    BigDecimal selectDSumbyorderId(String order_id,Integer resource_id);

    int deleteDbyorderId(String order_id,Integer base_id);

    int deleteBybaseId(Integer base_id);

    // 根据base_id获取信息
    List<ScPurchasDetail> selectByBaseId(Integer base_id);

   
}