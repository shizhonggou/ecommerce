/*
 * @Author: lina
 * @Date: 2021-05-29 10:47:50
 * @LastEditTime: 2021-06-17 11:55:45
 * @FilePath: \e-commerce\src\main\java\com\isechome\ecommerce\mapper\isechome\ResourceSalesMapper.java
 * @Description: 
 * @Copyright: © 2021, SteelHome. All rights reserved.
 */
package com.isechome.ecommerce.mapper.isechome;

import com.isechome.ecommerce.entity.ResourceSales;

import java.util.List;
import java.util.Map;

public interface ResourceSalesMapper {
    int insert(ResourceSales record);

    List<ResourceSales> selectAll(Map<String,Object> map);

    void insertExcel(List<ResourceSales> resourceSales);

	void resourceUpdate(Map<String,Object> map);

	List<ResourceSales> getSalesResourceList(Byte vid , String varietyname, String material, String spec, String originCode, String cangku );

    int updateStatusById(Integer id, Byte status);

    int updateStatusByEndTime();

    int updateSoldNumById(Integer id, Double SoldNum);

    ResourceSales getSalesResourceById(Integer id);

    List<ResourceSales> getSalesResourceListBySearch(Byte vid, String searchwhere);
}