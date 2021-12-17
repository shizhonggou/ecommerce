package com.isechome.ecommerce.mapper.ecommerce;

import com.isechome.ecommerce.entity.Scfahuolibs;
import java.util.List;

public interface ScfahuolibsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Scfahuolibs record);

    Scfahuolibs selectByPrimaryKey(Integer id);

    List<Scfahuolibs> selectAll(Integer id);

    List<Scfahuolibs> selectAll2(Integer pmid,Integer mid);

    int updateByPrimaryKey(Scfahuolibs record);

    void insertfhinfo(Integer mid, String cangkuid, String cangkuname, String yunfei, String remark,
            String daozhanCangkuName, String daozhanma, Integer pmid);

    void updatefhinfo(Integer id, Integer mid, String cangkuid, String cangkuname,String yunfei, String remark,
            String daozhanCangkuName, String daozhanma);
}