package com.isechome.ecommerce.mapper.ecommerce;

import com.isechome.ecommerce.entity.InvoiceInformationManagement;
import java.util.List;

public interface InvoiceInformationManagementMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(InvoiceInformationManagement record);

    InvoiceInformationManagement selectByPrimaryKey(Integer id);

    List<InvoiceInformationManagement> selectAll(Integer id);

    int updateByPrimaryKey(InvoiceInformationManagement record);

    void updatefpinfo(Integer id,String rise, String taxnum, String addressMail, String addressCompany,String openBank, String account,
     String phone, Integer isdefault, String invoiceCode, String remark);
    
     void updatefpinfodf(Integer mid,Integer pmid, Integer dfm);
    void insertfpinfo(Integer mid,String rise, String taxnum, String addressMail,String addressCompany, String openBank, String account,String phone,Integer isdefault, String invoiceCode, String remark,Integer pmid);

    List<InvoiceInformationManagement> selectBymid(Integer mid,Integer pmid);

    List<InvoiceInformationManagement> selectByInId(String ids);
    

}