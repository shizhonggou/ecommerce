/*
 * @Author: lina
 * @Date: 2021-08-20 15:56:12
 * @LastEditTime: 2021-09-03 16:35:30
 * @FilePath: \e-commerce\src\main\java\com\isechome\ecommerce\mapper\ecommerce\ScPieceWeightManageMapper.java
 * @Description: 
 * @Copyright: © 2021, SteelHome. All rights reserved.
 */
package com.isechome.ecommerce.mapper.ecommerce;

import com.isechome.ecommerce.entity.ScPieceWeightManage;
import java.util.List;
import java.util.Map;

public interface ScPieceWeightManageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ScPieceWeightManage record);

    ScPieceWeightManage selectByPrimaryKey(Integer id);

    List<ScPieceWeightManage> getInfoByStmid(Integer stmid);

    List<ScPieceWeightManage> selectAll();

    int updateByPrimaryKey(ScPieceWeightManage record);

    List<ScPieceWeightManage> getPieceWeightList(Map<String,Object> map);

    int updateStatusById(Integer id, Byte status);

    ScPieceWeightManage getByGcShortSpec(String factory, String specification,Integer pmid);


}