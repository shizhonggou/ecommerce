package com.isechome.ecommerce.mapper.isechome;

import com.isechome.ecommerce.entity.Sysmobilecheckcode;
import java.util.List;

public interface SysmobilecheckcodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Sysmobilecheckcode record);

    Sysmobilecheckcode selectByPrimaryKey(Integer id);

    Sysmobilecheckcode selectSysmobilecheckcodeByMobileNumber(String MobileNumber);

    List<Sysmobilecheckcode> selectAll();

    int updateByPrimaryKey(Sysmobilecheckcode record);
}