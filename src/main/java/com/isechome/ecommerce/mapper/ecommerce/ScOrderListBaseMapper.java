package com.isechome.ecommerce.mapper.ecommerce;

import com.isechome.ecommerce.entity.ScOrderListBase;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ScOrderListBaseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ScOrderListBase record);

    ScOrderListBase selectByPrimaryKey(Integer id);

    ScOrderListBase getSumNum(Integer pmid, Date beginOfDay, Date endOfDay);

    List<ScOrderListBase> selectAll();

    int updateByPrimaryKey(ScOrderListBase record);

    List<ScOrderListBase> getOrderList(Map<String, Object> map);

    ScOrderListBase getOrderById(Integer id);

    List<ScOrderListBase> getOrderByIds(Map<String, Object> map);

    List<ScOrderListBase> getBaseByIds(String ids);

    int updatepurchase(ScOrderListBase record);

    int updateOrderStatus(Integer id, byte status);

    int orderMoneyMach(Integer pmid, Integer purchase_mid, Double total_price);

    Integer getOrderMoneyMachId(Integer pmid, Integer purchase_mid, Double total_price);

    int updateBaseByOrderId(ScOrderListBase record);

    ScOrderListBase getOrderInfoByOrderId(String orderId);

    ScOrderListBase getInfoByOrderId(String orderId);

    List<ScOrderListBase> getOrderListByStatus(Integer pmid, Integer purchase_mid, byte status);

    List<ScOrderListBase> getDetaiLogisticsList(Map<String, Object> map);

    ScOrderListBase getLogisticsById(Integer id, Integer mid);

    ScOrderListBase getDetailById(Integer id);

    int updateTotalPriceById(Double total_price, Integer id);

    List<ScOrderListBase> getYjsOrderList(Map<String, Object> map);

    int updatecleanOrder(Integer pmid, Date beginOfDay, Date endOfDay);

    ScOrderListBase getOrderByorderId(String orderId);

    List<ScOrderListBase> getBaseOrderIds(String orderids);

    ScOrderListBase getLogisticsByOrderId(Integer mid, String orderId);

    List<ScOrderListBase> getOrderListByIds(String ids);

    ScOrderListBase getOrderByorderId2(String orderId);

    Integer getDQCountByResourceId(Integer pmid, Integer resource_id);

    ScOrderListBase getOrderDetailById(Integer id);

    //根据订单id取消订单
    int cancelOrder(String orderId);

    //修改订单的件数 数量 总金额
    int subOrderNum(ScOrderListBase scOrderListBase);
}