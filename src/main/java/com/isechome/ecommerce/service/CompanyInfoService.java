package com.isechome.ecommerce.service;
import com.isechome.ecommerce.common.MD5Util;
import com.isechome.ecommerce.common.SecurityUserUtil;
import com.isechome.ecommerce.mapper.isechome.SysAdminuserMapper;
import com.isechome.ecommerce.mapper.isechome.SysCompanyMapper;
import com.isechome.ecommerce.security.SecuritySysUser;
//import com.isechome.ecommerce.security.entity.AdminUserInfo;
//import com.isechome.ecommerce.security.entity.CompanyInfo;
import com.isechome.ecommerce.entity.SysAdminuser;
import com.isechome.ecommerce.entity.SysCompany;
import com.isechome.ecommerce.entity.FinancialInfo;
import com.isechome.ecommerce.entity.Scfahuolibs;
import com.isechome.ecommerce.mapper.ecommerce.ScfahuolibsMapper;
import com.isechome.ecommerce.mapper.isechome.CompanyInfoDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
@Transactional( transactionManager = "isechomeMybatisPlatformTransactionManager")
public class CompanyInfoService {
    @Autowired
    CompanyInfoDetailMapper companyInfoMapper;
    @Autowired
    SysAdminuserMapper sysAdminuserMapper;
    @Autowired
    SysCompanyMapper sysCompanyMapper;

    // 查询个人信息
    public SysAdminuser userInfoDetai(HttpServletRequest request) {
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        Integer id= session.getSysAdminUserInfo().getId();
        SysAdminuser userDetai = new SysAdminuser();
        userDetai = sysAdminuserMapper.selectUserInfoDetail(id);
        return userDetai;
    }
    // 查询公司信息
    public SysCompany companyInfoDetai() {
        SecuritySysUser session = SecurityUserUtil.getCurrentUser();
        Integer id= session.getSysAdminUserInfo().getComid();
        SysCompany sysCompany = new SysCompany();
        sysCompany = sysCompanyMapper.companyInfoDetai(id);
        return sysCompany;
    }

    public Integer insertbycompanyname(String comname) {
        Byte kong3= 0;
        Byte mr= 1;
        String kong = "0";
        Integer  kong2 = 0;
        Boolean kong4 = true;
        Date create_date = new Date();
        Double kong6 = 0.0;
        SysCompany companyInfo = new SysCompany();
        companyInfo.setComname(comname);
        companyInfo.setGuid(kong);
        companyInfo.setMemberid(kong2);
        companyInfo.setGroupid(kong2);
        companyInfo.setMembertype(kong3);
        companyInfo.setSitNo(kong);
        companyInfo.setComtype(kong3);
        companyInfo.setComtranstype(kong3);
        companyInfo.setTradepasswd(kong);
        companyInfo.setTradepasswdupdate(create_date);
        companyInfo.setPysametd(kong3);
        companyInfo.setMobilechecked(kong);
        companyInfo.setLinkman(kong);
        companyInfo.setMobilecheckeddate(create_date);
        companyInfo.setContacttel(kong);
        companyInfo.setFrdb(kong);
        companyInfo.setParentid(kong2);
        companyInfo.setIsnofinance(kong3);
        companyInfo.setAdminuser1(kong2);
        companyInfo.setAdminuser1date(create_date);
        companyInfo.setAdminuser2(kong2);
        companyInfo.setAdminuser2date(create_date);
        companyInfo.setAdminuser3(kong2);
        companyInfo.setAdminuser3date(create_date);
        companyInfo.setLastservicedate(create_date);
        companyInfo.setLastserviceid(kong2);
        companyInfo.setContactfax(kong);
        companyInfo.setBanktype(kong);
        companyInfo.setTaxno(kong);
        companyInfo.setRegip(kong);
        companyInfo.setFirstsigndate(create_date);
        companyInfo.setCountry(kong);
        companyInfo.setState(kong);
        companyInfo.setCity(kong);
        companyInfo.setPostcode(kong);
        companyInfo.setRegisterfrom(kong3);
        companyInfo.setStatus(kong3);
        companyInfo.setRegisterfrom(kong3);
        companyInfo.setCreateuser(kong);
        companyInfo.setUpdatedate(create_date);
        companyInfo.setUpdateuser(kong);
        companyInfo.setIsneedzs(kong4);
        companyInfo.setBankno(kong);
        companyInfo.setInvoicestar(kong);
        companyInfo.setInvoiceno(kong);
        companyInfo.setPaypasswd(kong);
        companyInfo.setIsdowntd(kong3);
        companyInfo.setGctrybegintime(create_date);
        companyInfo.setGctryendtime(create_date);
        companyInfo.setIsgctry(kong3);
        companyInfo.setComurl(kong);
        companyInfo.setBankaccount(kong);
        companyInfo.setMembertypedate(create_date);
        companyInfo.setIstry(kong3);
        companyInfo.setIssteelhq(kong3);
        companyInfo.setDoarea(kong);
        companyInfo.setIcnumber(kong);
        companyInfo.setEnterprisecode(kong);
        companyInfo.setVariety1(kong);
        companyInfo.setVariety2(kong);
        companyInfo.setVariety3(kong);
        companyInfo.setVariety4(kong);
        companyInfo.setVariety5(kong);
        companyInfo.setScSteelcom(kong);
        companyInfo.setDlCom(kong);
        companyInfo.setRzMarket(kong);
        companyInfo.setJyCitys(kong);
        companyInfo.setJyVariety(kong);
        companyInfo.setZyVariety(kong);
        companyInfo.setIsdzgys(kong3);
        companyInfo.setTieluz(kong);
        companyInfo.setTieluz2(kong);
        companyInfo.setTieluz3(kong);
        companyInfo.setTieluz4(kong);
        companyInfo.setTieluz5(kong);
        companyInfo.setMatou(kong);
        companyInfo.setMatou2(kong);
        companyInfo.setMatou3(kong);
        companyInfo.setMatou4(kong);
        companyInfo.setMatou5(kong);
        companyInfo.setIstj(kong3);
        companyInfo.setPx(kong2);
        companyInfo.setDownprice(kong6);
        companyInfo.setPaytype(kong3);
        companyInfo.setMgManagedaozhancode(kong3);
        companyInfo.setMgTihuomode(kong3);
        companyInfo.setMgCankaoremarkprice(kong3);
        companyInfo.setMgCanbuyother(kong3);
        companyInfo.setMgIsxieyi(kong3);
        companyInfo.setMgXieyinum(kong6);
        companyInfo.setMgXieyinum1200(kong6);
        companyInfo.setMgIsfengongsi(kong3);
        companyInfo.setMgLockprice(kong6);
        companyInfo.setMgCanuseshengyukeyres(kong3);
        companyInfo.setMgMonthnum(kong6);
        companyInfo.setMgHetongtotalnum(kong6);
        companyInfo.setMgSubordinatedepartments(kong3);
        companyInfo.setMgGcdaypricetype(kong3);
        companyInfo.setMgJijiaLuowen(kong6);
        companyInfo.setMgJijiaGaoxian(kong6);
        companyInfo.setMgJijiaPanluo(kong6);
        companyInfo.setEnddate(create_date);
        companyInfo.setEmpowertype(kong3);
        companyInfo.setIsshowcj(kong3);
        companyInfo.setMgAtt(kong2);
        companyInfo.setWebsite2(kong);
        companyInfo.setEmail(kong);
        companyInfo.setAreacode(kong);
        companyInfo.setScale(kong3);
        companyInfo.setZxflag(kong3);
        companyInfo.setIsshowweb(kong3);
        companyInfo.setReceivesmstype(kong);
        companyInfo.setSales(kong);
        companyInfo.setTurnover(kong);
        companyInfo.setSource(kong3);
        companyInfo.setGzjadmin1(kong2);
        companyInfo.setGzjadmin2(kong2);
        companyInfo.setKpcom(kong);
        companyInfo.setKpren(kong);
        companyInfo.setKpaddress(kong);
        companyInfo.setKptel(kong);
        companyInfo.setTryOut(kong3);
        companyInfo.setYqCompanyname(kong);
        companyInfo.setSigncompanyname(kong);
        companyInfo.setYqMuban(kong3);
        companyInfo.setYqLiucheng(kong3);
        companyInfo.setYjSalestype(kong);
        companyInfo.setAllowmultifh(kong3);
        companyInfo.setJiaogefinishnum(kong2);
        companyInfo.setPingjiazongfen1(kong6);
        companyInfo.setPingjianum1(kong2);
        companyInfo.setPingjiazongfen2(kong6);
        companyInfo.setPingjianum2(kong2);
        companyInfo.setAllowsendjingjiasms(kong3);
        companyInfo.setAllowdaizhucemember(kong3);
        companyInfo.setMgEndtime(create_date);
        companyInfo.setMgStarttime(create_date);
        companyInfo.setMgCanbuymaterialtype(kong3);
        companyInfo.setComnameshort(kong);
        companyInfo.setComdesc(kong);
        companyInfo.setAddressstate(kong);
        companyInfo.setAddresscity(kong);
        companyInfo.setAddress(kong);
        companyInfo.setMchNo(kong);
        companyInfo.setCreatedate( create_date ); 
        sysCompanyMapper.insert(companyInfo);
        return  companyInfo.getId();
      }

    



}
