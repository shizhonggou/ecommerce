package com.isechome.ecommerce.service;

import com.isechome.ecommerce.entity.CompanyInfo;
import com.isechome.ecommerce.mapper.isechome.CompanyInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @program: ecommerce
 * @description: 公司管理service
 * @author: Mr.shizg
 * @create: 2021-05-26 08:59
 **/
@Service
@Transactional( transactionManager = "isechomeMybatisPlatformTransactionManager")
public class CompanyInfoService {
    @Autowired
    CompanyInfoMapper companyInfoMapper;

    /**
     * 新增公司
     * @param companyInfo
     */
    public void insertCompany(CompanyInfo companyInfo) {
        companyInfoMapper.insert(companyInfo);
    }

    /**
     * 修改公司信息
     * @param companyInfo
     */
    public void updateCompanyById(CompanyInfo companyInfo) {
        companyInfoMapper.updateByPrimaryKey(companyInfo);
    }

    /**
     * 获取公司信息列表
     * @return
     */
    public List<CompanyInfo> getCompanyInfo(){
        return companyInfoMapper.selectAll();
    }

    public void saveCompanyInfo(HttpServletRequest request) {
        CompanyInfo companyInfo = new CompanyInfo();
        companyInfo.setAddress("花山区海外海12A");
        companyInfo.setAddressCity("马鞍山");
        companyInfo.setComNameShort("大公司有限公司");
        companyInfo.setComName("世界大公司有限公司");
        companyInfo.setComDesc("公司就是做生意的");
        companyInfo.setAddressState("安徽省");
        companyInfo.setCreateDate( new Date() );
        companyInfo.setCreateUserId(1);
        companyInfo.setEnclosureUrl("http://www.steelhome.cn");
        companyInfo.setLinkUserId(2);
        Byte where_from = 0;
        companyInfo.setWhereFrom( where_from );
        insertCompany(companyInfo);
    }
}
