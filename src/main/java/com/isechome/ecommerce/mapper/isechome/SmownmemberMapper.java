package com.isechome.ecommerce.mapper.isechome;

import com.isechome.ecommerce.entity.Smownmember;
import java.util.List;

public interface SmownmemberMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Smownmember smownmember);

    Smownmember selectByPrimaryKey(Integer id);

    Smownmember selectbyapplymid(Smownmember smownmember);

    List<Smownmember> selectAll2(Integer id);

    List<Smownmember> selectAll(Integer id);

    List<Smownmember> selectAll4(Integer id);

    List<Smownmember> selectbycom(Integer id, Integer comid);

    int updateByPrimaryKey(Smownmember record);

    void updatekhinfo(Integer id, Integer reviewuid, Integer reviewmid, Integer status, String preferentialAmount,
            Integer member_type, String credit_amount, String filePath);

    Smownmember selectByPmidAndMid(Integer pmid, Integer mid);

    Smownmember selectByPmidAndUid(Integer pmid, Integer uid);

}