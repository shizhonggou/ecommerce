package com.isechome.ecommerce.mapper.isechome;

public interface CompanyInfoDetailMapper {
    // 查询公司信息
   // CompanyInfo selectCompanyInfo(Integer id);
    void updateCompanyInfoDetail(Integer id, String com_name,String com_name_short,String com_desc,String address_state,String address_city,String address,String strPath);
    void updateCompanyInfoZhuLXR(Integer id,Integer link_user_id);

}
