package com.isechome.ecommerce.mapper.ecommerce;

import com.isechome.ecommerce.entity.BalanceInfo;
import com.isechome.ecommerce.entity.FinancialInfo;

import java.util.Date;
import java.util.List;

public interface FinancialMapper {
    // 录入财务信息
    int financialInsert(FinancialInfo financialInfo);
    // 出入账 列表
    List<FinancialInfo> selectAllList(Integer pmid);
    // 删除 财务信息
    int deleteFinancialForID(Integer id);
    // 查询单条 财务信息
    FinancialInfo selecteFinancialInfoForID(Integer id);
    // 更新财务信息
    int updateFinancialInfoForID(Integer id,Integer bill, Integer bill_type, String accept_bank, Integer accept_date, Double money, Integer company_id, String company_name, Integer user_id, String input_user, Date input_date, String remark);
    // 查询余额列表
    List<BalanceInfo> selectBalanceAllList(Integer pmid);
    // 删除 余额信息
    int deleteBalanceForID(Integer id);
    // 查询单条 余额信息
    BalanceInfo selecteBalanceInfoForID(Integer id);
    // 更新 余额信息
    int updateBalanceInfoForID(Integer id, Double balance, Integer user_id, String input_user, Date input_date);
    // 插入新的余额信息
    int insertCompanyBalance(BalanceInfo balanceInfo);
    // 外部接口 根据公司ID  平台方pmid 查询公司余额
    BalanceInfo selectCompaneyBalance(Integer company_id, Integer pmid);
    // 外部接口 根据公司company_id  平台方pmid    更新公司余额
    int updateCompaneyBalance(Integer company_id, Integer pmid, Double balance, Integer user_id, String input_user, Date input_date);


}
