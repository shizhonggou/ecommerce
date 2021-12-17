package com.isechome.ecommerce.mapper.ecommerce;

import com.isechome.ecommerce.entity.LogisticsPurchase;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Repository
public interface LogisticsPurchaseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LogisticsPurchase record);

    LogisticsPurchase selectByPrimaryKey(Integer id);

    List<LogisticsPurchase> selectAll();

    int updateByPrimaryKey(LogisticsPurchase record);

    int insertLogistics(LogisticsPurchase record);

    int updateLogisticsById(Integer id, Byte type, Byte kind, Double num);

    List<LogisticsPurchase> getPurchaseList(Byte type);

    int updatePurchaseById(LogisticsPurchase record);

    List getAllExtractId(String[] extract_id);

    int deletePurchaseByIds(HashMap<String, Object> map);

    Integer getPurchaseIdBymoney(Integer pmid, Integer supplier_mid, Double purchase_amount);

    int purchaseMoneyMach(Integer id, Byte type);

    /**
     * @Description 获取采购资源未分配采购单信息
     * @Author zhaofy
     * @Date 2021/8/27
     * @return java.util.List<com.isechome.ecommerce.entity.LogisticsPurchase>
     **/
    List<LogisticsPurchase> selectUnassignedList(Integer id);

    /**
     * @Description 匹配未分配采购单
     * @Author zhaofy
     * @Date 2021/8/27
     * @Param [record]
     * @return int
     **/
    int updateByMatchResource(LogisticsPurchase record);

    LogisticsPurchase selectByExtractId(String extract_id);

    List<LogisticsPurchase> selectByOrderDetailId(Integer orderdetailid);

    BigDecimal selectSumByOrderDetailId(Integer orderdetailid);

    int updateByExtractId(LogisticsPurchase record);

    int updateByExtractIdAndType(LogisticsPurchase record);

    List<LogisticsPurchase> selectByOrderId(String orderid);

    BigDecimal getSumNumByResid(Integer resource_id);

    int updatePurchaseNumById(LogisticsPurchase record);

    List<LogisticsPurchase> selectPurchaseByDetailId(Integer orderdetailid, Byte type);

    Integer getPurchaseCountByDId(Integer id);

    Integer updatePurchaseByResourceId(Integer resource_id);
}