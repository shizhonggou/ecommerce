package com.isechome.ecommerce.mapper.isechome;

import com.isechome.ecommerce.entity.CompanyInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CompanyInfo record);

    CompanyInfo selectByPrimaryKey(Integer id);

    List<CompanyInfo> selectAll();

    int updateByPrimaryKey(CompanyInfo record);

    void insertres(CompanyInfo companyInfo);
    int updateusid (CompanyInfo record);

    List<CompanyInfo> seletSerch(String searchName);

    
}