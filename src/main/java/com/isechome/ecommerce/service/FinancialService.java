package com.isechome.ecommerce.service;

import com.isechome.ecommerce.common.SecurityUserUtil;
import com.isechome.ecommerce.entity.BalanceInfo;
import com.isechome.ecommerce.entity.FinancialInfo;
import com.isechome.ecommerce.entity.Smownmember;
import com.isechome.ecommerce.entity.SysCompany;
import com.isechome.ecommerce.mapper.ecommerce.FinancialMapper;
import com.isechome.ecommerce.mapper.isechome.SmownmemberMapper;
import com.isechome.ecommerce.mapper.isechome.SysAdminuserMapper;
import com.isechome.ecommerce.mapper.isechome.SysCompanyMapper;
import com.isechome.ecommerce.security.SecuritySysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional( transactionManager = "isechomeMybatisPlatformTransactionManager")
public class FinancialService {
    @Autowired
    FinancialMapper financialMapper;
    @Autowired
    SysAdminuserMapper sysAdminuserMapper;
    @Autowired
    SysCompanyMapper sysCompanyMapper;
    @Autowired
    ScOrderListService scOrderListService;
    @Autowired
    SmownmemberMapper smownmemberMapper;
    // 财务出入账信息 录入
    public int financialInputSave(HttpServletRequest request) throws ParseException {
        if (request.getParameter("companyid").equals("")) {
            return 4;
        }
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        Integer id= session.getSysAdminUserInfo().getId();
        FinancialInfo financialInfo = new FinancialInfo();
//        SysCompany sysCompany = sysCompanyMapper.selectCompanyInfoForName(request.getParameter("company_name"));
        SysCompany sysCompany = sysCompanyMapper.companyInfoDetai(Integer.valueOf(request.getParameter("companyid")));
        financialInfo.setCompany_id(sysCompany.getId());
        financialInfo.setCompany_name(sysCompany.getComname());
        financialInfo.setMoney(Double.parseDouble(request.getParameter("money").replace(" ","")));
        String bill = request.getParameter("bill");
        financialInfo.setBill(Integer.parseInt(bill));
        String bill_type = request.getParameter("bill_type");
        financialInfo.setBill_type(Integer.parseInt(bill_type));
        if (bill_type.equals("2")) {
            // 承兑
            financialInfo.setAccept_bank(request.getParameter("accept_bank"));
            financialInfo.setAccept_date(Integer.parseInt(request.getParameter("accept_date").replace(" ","")));
        }
        financialInfo.setUser_id(id);
        financialInfo.setInput_user(session.getSysAdminUserInfo().getArealname());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String dateStr = df.format(new Date());
        financialInfo.setInput_date(df.parse(dateStr));
        financialInfo.setRemark(request.getParameter("remark"));
        financialInfo.setPmid(SecurityUserUtil.getPmid());
        int res = 0;
//        int res = financialMapper.financialInsert(financialInfo);
        BalanceInfo blInfo = financialMapper.selectCompaneyBalance(sysCompany.getId(),SecurityUserUtil.getPmid());
        if (bill.equals("1")) {
            // 1=入账  2=出账    3=退款
            if (blInfo != null){
                // 余额表里有这家公司 直接更新余额
                Double balanceAdd = Double.parseDouble(request.getParameter("money")) + blInfo.getBalance();
                financialMapper.updateCompaneyBalance(sysCompany.getId(),SecurityUserUtil.getPmid(),
                        balanceAdd, session.getSysAdminUserInfo().getId(),
                        session.getSysAdminUserInfo().getArealname(),df.parse(dateStr));
            } else {
                // 余额表 没有这家公司的记录  直接录入
                BalanceInfo balanceInfo = new BalanceInfo();
                balanceInfo.setBalance(Double.parseDouble(request.getParameter("money")));
                balanceInfo.setCompany_id(sysCompany.getId());
                balanceInfo.setCompany_name(sysCompany.getComname());
                balanceInfo.setUser_id(session.getSysAdminUserInfo().getId());
                balanceInfo.setInput_user(session.getSysAdminUserInfo().getArealname());
                balanceInfo.setInput_date(df.parse(dateStr));
                balanceInfo.setPmid(SecurityUserUtil.getPmid());
                financialMapper.insertCompanyBalance(balanceInfo);
            }
            res = financialMapper.financialInsert(financialInfo);
            scOrderListService.orderMoneyMach(SecurityUserUtil.getPmid(),sysCompany.getId(),Double.parseDouble(request.getParameter("money")));
        } else if (bill.equals("2")) {
            //  2=出账
            res = financialMapper.financialInsert(financialInfo);
            scOrderListService.purchaseMoneyMach(SecurityUserUtil.getPmid(), sysCompany.getId(), Double.parseDouble(request.getParameter("money")));
        } else if (bill.equals("3")) {
            // 3=退款
            if (blInfo != null) {
                if (blInfo.getBalance() >= Double.parseDouble(request.getParameter("money"))) {
                    Double bl = blInfo.getBalance() - Double.parseDouble(request.getParameter("money"));
                    int i = financialMapper.updateCompaneyBalance(blInfo.getCompany_id(),SecurityUserUtil.getPmid(),bl,session.getSysAdminUserInfo().getId(),session.getSysAdminUserInfo().getArealname(),df.parse(dateStr));
                    if (i == 0) {
                        // 更新失败
                        res = 0;
                    } else {
                        res = financialMapper.financialInsert(financialInfo);
                    }
                } else {
                    res = 3;// 余额不足
                }
            } else {
                res = 3;// 余额不足
            }
        }
        return res;
    }
    // 财务出入账 列表
    public List<FinancialInfo> financialAccountList(HttpServletRequest request) {
        List<FinancialInfo> listFinancial =  financialMapper.selectAllList(SecurityUserUtil.getPmid());
        return listFinancial;
    }
    // 删除 财务信息
    public int deleteFinancialForID(HttpServletRequest request) {
        int res = financialMapper.deleteFinancialForID(Integer.valueOf(request.getParameter("id")));
        return res;
    }
    // 根据ID 查询财务信息
    public FinancialInfo selecteFinancialInfoForID(HttpServletRequest request) {
        FinancialInfo financialInfo = new FinancialInfo();
        financialInfo = financialMapper.selecteFinancialInfoForID(Integer.valueOf(request.getParameter("id")));
        return financialInfo;
    }
    // 根据ID 查询 录入的财务信息  更新保存
    public int updateFinancialInfoForID(HttpServletRequest request) throws ParseException {
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String dateStr = df.format(new Date());
        SysCompany sysCompany = sysCompanyMapper.selectCompanyInfoForName(request.getParameter("company_name"));
        Integer accept_date = 0;
        if (request.getParameter("accept_date").equals("")) {
            accept_date = 0;
        } else {
            accept_date = Integer.valueOf(request.getParameter("accept_date").replace(" ",""));
        }
        int res = financialMapper.updateFinancialInfoForID(Integer.valueOf(request.getParameter("id")), Integer.valueOf(request.getParameter("bill")),
                Integer.valueOf(request.getParameter("bill_type")), request.getParameter("accept_bank"),
                accept_date, Double.valueOf(request.getParameter("money").replace(" ","")), sysCompany.getId(),
                sysCompany.getComname(), session.getSysAdminUserInfo().getId(), session.getSysAdminUserInfo().getArealname(),
                df.parse(dateStr), request.getParameter("remark") );
        return res;
    }
    // 余额管理 列表
    public List<BalanceInfo> selectBalanceAllList(HttpServletRequest request) {
        List<BalanceInfo> balanceInfoList =  financialMapper.selectBalanceAllList(SecurityUserUtil.getPmid());
        return balanceInfoList;
    }
    // 删除 余额信息
    public int deleteBalanceForID(HttpServletRequest request) {
        int res = financialMapper.deleteBalanceForID(Integer.valueOf(request.getParameter("id")));
        return res;
    }
    // 根据ID 查询余额信息
    public BalanceInfo selecteBalanceInfoForID(HttpServletRequest request) {
        BalanceInfo balanceInfo = new BalanceInfo();
        balanceInfo = financialMapper.selecteBalanceInfoForID(Integer.valueOf(request.getParameter("id")));
        return balanceInfo;
    }
    // 根据ID 查询 录入的余额信息  更新保存
    public int updateBalanceInfoForID(HttpServletRequest request) throws ParseException {
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String dateStr = df.format(new Date());
        Double balance = Double.valueOf(request.getParameter("balance").replace(" ",""));
        if (balance == null) {
            balance = 0.0;
        }
        int res = financialMapper.updateBalanceInfoForID(Integer.valueOf(request.getParameter("id")), balance, session.getSysAdminUserInfo().getId(), session.getSysAdminUserInfo().getArealname(), df.parse(dateStr));
        return res;
    }
    public BalanceInfo selectCompaneyBalanceByCommID(Integer company_id) {
        BalanceInfo balanceInfo = new BalanceInfo();
        balanceInfo = financialMapper.selectCompaneyBalance(company_id,Integer.valueOf(SecurityUserUtil.getPmid()));
        return balanceInfo;
    }
    // 外部接口  根据公司 mid  平台方pmid 查询公司余额  信用额度
    public Boolean selectCompaneyBalanceAndCredit(Integer pmid, Integer mid, Double money) {
        Boolean res = false;
        Smownmember smownmember = smownmemberMapper.selectByPmidAndMid(pmid,mid);
        BalanceInfo balanceInfo = financialMapper.selectCompaneyBalance(mid, pmid);
        Double balanceAndCredit = 0.0;
        if (smownmember != null && balanceInfo != null) {
            if (smownmember.getMember_type().equals(2)) {
                balanceAndCredit = balanceInfo.getBalance() + smownmember.getCredit_amount();
            } else {
                balanceAndCredit = balanceInfo.getBalance();
            }
        }
        if (balanceAndCredit >= money) {
            res = true;
        } else {
            res = false;
        }
        return res;
    }

    // 外部接口 根据公司company_id  平台方pmid 查询公司余额
    public BalanceInfo selectCompaneyBalance(HttpServletRequest request) {
        BalanceInfo balanceInfo = new BalanceInfo();
        balanceInfo = financialMapper.selectCompaneyBalance(Integer.valueOf(request.getParameter("company_id")),Integer.valueOf(request.getParameter("pmid")));
        return balanceInfo;
    }
    // 外部接口 根据公司company_id  平台方pmid    更新公司余额
    public String updateCompaneyBalance(HttpServletRequest request) throws ParseException {
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String dateStr = df.format(new Date());
        int row =  financialMapper.updateCompaneyBalance(Integer.valueOf(request.getParameter("company_id")),Integer.valueOf(request.getParameter("pmid")), Double.valueOf(request.getParameter("balance")), session.getSysAdminUserInfo().getId(), session.getSysAdminUserInfo().getArealname(), df.parse(dateStr));
        String str = "更新失败";
        if (row == 1){
            str = "更新成功";
        } else if (row == 0){
            str = "更新失败";
        }
        return str;
    }



}
