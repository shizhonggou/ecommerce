package com.isechome.ecommerce.mapper.isechome;

import com.isechome.ecommerce.entity.SysCompany;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysCompanyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysCompany record);

    SysCompany selectByPrimaryKey(Integer id);

    List<SysCompany> selectAll();

    int updateByPrimaryKey(SysCompany record);

    // 查询公司信息
    SysCompany companyInfoDetai(Integer id);

    int updateCompanyInfoDetail(Integer id, String com_name, String com_name_short, String com_desc,
            String address_state, String address_city, String address, String strPath);

    // void updateCompanyInfoZhuLXR(Integer id,Integer link_user_id);
    // 根据公司名字 模糊查询
    SysCompany selectCompanyInfoForName(String companyName);

    List<SysCompany> seletSerch(String searchName, String comids, Integer pagesize);

    List<SysCompany> seletIDs(String ids);

    int companyInfoinsertbyname(SysCompany sysCompany);

    List<SysCompany> selectCompanyByName(String companyName);

}