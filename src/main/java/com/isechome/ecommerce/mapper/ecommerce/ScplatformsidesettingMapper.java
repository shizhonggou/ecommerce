package com.isechome.ecommerce.mapper.ecommerce;

import com.isechome.ecommerce.entity.Scplatformsidesetting;
import org.apache.ibatis.annotations.MapKey;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface ScplatformsidesettingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Scplatformsidesetting record);

    Scplatformsidesetting selectByPrimaryKey(Integer id);

    Scplatformsidesetting selectbytoken(String token);

    int updateByPrimaryKey(Scplatformsidesetting record);
    
    Scplatformsidesetting selectnewpmid();

    Scplatformsidesetting selectLogo(Integer pmid);
    @MapKey("pmid")
    Map<Integer,Scplatformsidesetting> selectPmIdAndDescription();

}