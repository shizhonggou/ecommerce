package com.isechome.ecommerce.mapper.ecommerce;

import com.isechome.ecommerce.entity.ScOrderListBase;
import com.isechome.ecommerce.entity.ScOrderListDetail;
import com.isechome.ecommerce.entity.ScShoppingCart;
import com.isechome.ecommerce.entity.ScShoppingCartResoueces;

import java.util.Date;
import java.util.List;

public interface ScShoppingCartMapper {
    void insertScShoppingCart(ScShoppingCart obj);
    List<ScShoppingCartResoueces> getScShoppingCartAll(Integer userid,Integer pmid,Integer status,Date create_date);
    void updateScShoppingCartById(Integer id,Double weight,Double weight2,Date update_time);
    void deleteScShoppingCartById(List<Integer> list);
    void deleteScShoppingCartAll(Date create_date);
    void delByDatePmid(Integer pmid, Date beginOfDay, Date endOfDay);
    Integer insertScOrderListBae(ScOrderListBase obj);
    void insertScOrderListDetail(List<ScOrderListDetail> obj,Integer baseid);

    //根据资源id清空购物车
    void delByResourceId(int pmid, int resources_id);
}
