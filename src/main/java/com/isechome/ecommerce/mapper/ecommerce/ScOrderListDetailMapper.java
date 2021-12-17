package com.isechome.ecommerce.mapper.ecommerce;

import com.isechome.ecommerce.entity.ScOrderListDetail;
import org.springframework.stereotype.Repository;
import java.util.Map;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface ScOrderListDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ScOrderListDetail record);

    ScOrderListDetail selectByPrimaryKey(Integer id);

    List<ScOrderListDetail> selectAll();

    int updateByPrimaryKey(ScOrderListDetail record);

    ScOrderListDetail getDetailByid(Integer id);

    int updateDetailById(Integer id, Double price, Double piece, Double num, String extractPiece, String extractNum);

    int updateMoneyAndWeightByid(Integer id, double money, double weight);

    ScOrderListDetail getDetaiLogisticsByid(Map<String, Object> map);

    List<ScOrderListDetail> selectAllByOrderId(String OrderId);

    int updateSaveActionByid(Integer id, Byte exist_save_action);

    int updateSaveActionByrsid(Integer resource_id, Byte exist_save_action, Byte action, Integer pmid);

    List<ScOrderListDetail> getExcessResource(Integer pmid);

    Integer getDetailCountByorderId(String order_id);

    BigDecimal selectSumbyresourceId(Integer resource_id, Integer status, Integer pmid);

    List<ScOrderListDetail> selectDetailByresourceId(Integer resource_id, Integer pmid);

    List<ScOrderListDetail> getAllDetailByBaseid(String order_baseids);

    BigDecimal selectSumbyresourceIdAndPmid(Integer resource_id, Integer pmid, Integer status, Date create_date);

    Integer deleteByOrderId(String orderId);

    Integer getDetailCountByAction(String orderId);

    Integer updateSaveActionByPmid(Integer pmid);

    BigDecimal getSumbyResourceId(Integer resource_id, Integer pmid);

    //通过资源id查看是否有未确认+未付款订单
    Integer getCountOrderByResourceId(Integer resource_id, Integer pmid);

    //通过资源id查看未确认+未付款订单详情
    List<ScOrderListDetail> getListOrderByResourceId(Integer resource_id, Integer pmid);

    //将订单detail表的状态改为删除
    Integer updateOrderDetailDelete(int id);

}