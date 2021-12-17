package com.isechome.ecommerce.service;
import com.isechome.ecommerce.common.MD5Util;
import com.isechome.ecommerce.common.SecurityUserUtil;
import com.isechome.ecommerce.mapper.isechome.SysAdminuserMapper;
import com.isechome.ecommerce.mapper.isechome.SysCompanyMapper;
import com.isechome.ecommerce.security.SecuritySysUser;
import com.isechome.ecommerce.util.FileUtil;
//import com.isechome.ecommerce.security.entity.AdminUserInfo;
//import com.isechome.ecommerce.security.entity.CompanyInfo;
import com.isechome.ecommerce.entity.SysAdminuser;
import com.isechome.ecommerce.entity.SysCompany;
import com.isechome.ecommerce.entity.FinancialInfo;
import com.isechome.ecommerce.entity.Scfahuolibs;
import com.isechome.ecommerce.entity.Scplatformsidesetting;
import com.isechome.ecommerce.entity.Smownmember;
import com.isechome.ecommerce.mapper.ecommerce.InvoiceInformationManagementMapper;
import com.isechome.ecommerce.mapper.ecommerce.ScfahuolibsMapper;
import com.isechome.ecommerce.mapper.ecommerce.ScplatformsidesettingMapper;
import com.isechome.ecommerce.mapper.isechome.CompanyInfoDetailMapper;
import com.isechome.ecommerce.mapper.isechome.SmownmemberMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.print.DocFlavor.STRING;
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
public class RegisterService {
    @Autowired
    SysAdminuserMapper sysAdminuserMapper;
    @Autowired
    SysCompanyMapper sysCompanyMapper;
    @Autowired
    private SmownmemberMapper SmownmemberMapper;
    @Autowired
    InvoiceInformationManagementMapper invoiceInformationManagementMapper;
    @Autowired
    private ScplatformsidesettingMapper ScplatformsidesettingMapper;
    // 修改公司信息
    public void insertCompany(HttpServletRequest request ,@RequestParam(value = "file") MultipartFile file) {
        String strPath = "";
        String filePath = "";
        if (file.isEmpty()){
            // 没选择文件
        } else {
            // 处理上传的文件
            try{
                byte[] filebytes = file.getBytes();
                String str = System.getProperty("user.dir")+"/src/main/resources/upload/";
                strPath =  System.getProperty("user.dir")+"/src/main/resources/upload/"+ file.getOriginalFilename();
                Path path = Paths.get(str + file.getOriginalFilename());
                Files.write(path,filebytes);
                 filePath = FileUtil.executeUpload(file);
                System.out.println(path);
            }catch(Exception e){
                e.printStackTrace();
                //  "上传失败";
            }
        }

        Byte kong3= 0;
        Byte mr= 1;
        String kong = "0";
        Integer  kong2 = 0;
        Boolean kong4 = true;
        Date create_date = new Date();
        Double kong6 = 0.0;
        SysCompany companyInfo = new SysCompany();
        companyInfo.setComname( request.getParameter("com_name"));
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
        companyInfo.setState(request.getParameter("address_state"));
        companyInfo.setCity(request.getParameter("address_city"));
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
        companyInfo.setComnameshort( request.getParameter("com_name_short"));
        companyInfo.setComdesc( request.getParameter("com_desc"));
        companyInfo.setAddressstate( request.getParameter("address_state"));
        companyInfo.setAddresscity( request.getParameter("address_city"));
        companyInfo.setAddress( request.getParameter("address"));
        companyInfo.setMchNo(filePath);
        companyInfo.setCreatedate( create_date ); 
        sysCompanyMapper.insert(companyInfo);
        Integer ID= companyInfo.getId();
        //System.out.println(ID+ "11111111111111111111111111111");
        SysAdminuser userInfo = new SysAdminuser();
        userInfo.setComid(ID);
        userInfo.setDepartid(kong2);
        userInfo.setAdesc(kong);
        userInfo.setApasswdupdate(create_date);
        userInfo.setApowerroles(kong);
        userInfo.setAquerypasswd(kong);
        userInfo.setAwhs(kong);
        userInfo.setIsallwh(kong4);
        userInfo.setIsmain(mr);
        userInfo.setLoginiptype(kong4);
        userInfo.setLoginlockip(kong);
        userInfo.setCxtnum1(kong2);
        userInfo.setCxtnum2(kong2);
        userInfo.setCxtnum3(kong2);
        userInfo.setStatus(kong2);
        userInfo.setCreatedate(create_date);
        userInfo.setCreateuser(kong);
        userInfo.setUpdatedate(create_date);
        userInfo.setUpdateuser(kong);
        userInfo.setLastdate(create_date);
        userInfo.setLastip(kong);
        userInfo.setStype(kong3);
        userInfo.setIslimit(kong4);
        userInfo.setIssteel(kong3);
        userInfo.setPowerlimit(kong3);
        userInfo.setChildroot(kong);
        userInfo.setFirstsign(kong3);
        userInfo.setMobilecheck(kong);
        userInfo.setTelephone(kong);
        userInfo.setCity(request.getParameter("address_city"));
        userInfo.setProvince(request.getParameter("address_state"));
        userInfo.setQqnum(kong);
        userInfo.setFax(kong);
        userInfo.setPostcode(kong);
        userInfo.setYqRegistered(kong3);
        userInfo.setYqIssealmember(kong3);
        userInfo.setYqIsyqsealmember(kong3);
        userInfo.setYqTelnumber(kong);
        userInfo.setYqChangeduserinfo(kong3);
        userInfo.setIdnum(request.getParameter("idcard"));
        userInfo.setArealname(request.getParameter("real_name"));
        userInfo.setAnickname(kong);
        userInfo.setAusername(request.getParameter("user_name"));
        userInfo.setApasswd(MD5Util.encode(request.getParameter("password")).toLowerCase());
        userInfo.setTradepwd(kong);
        userInfo.setMobile(request.getParameter("mobile"));
        userInfo.setEmail(request.getParameter("email"));
        userInfo.setJob(request.getParameter("job"));
        userInfo.setSex(Byte.parseByte(request.getParameter("sex")));
        userInfo.setAddress(kong);
        userInfo.setCreatedate(create_date);
        sysAdminuserMapper.accountAddAction(userInfo);
        Integer usid= userInfo.getId();
        String pid = request.getParameter("pmid");
        Integer pmid = 0;
       if(pid != null && !pid.trim().equals("")){
              pmid =Integer.valueOf(request.getParameter("pmid")); 
              Smownmember smownmember = new Smownmember();
              smownmember.setApplymid(ID);
              smownmember.setReviewmid(pmid);
              smownmember.setApplyuid(usid);
              smownmember.setContactman(kong);
              smownmember.setContacttel(kong);
              smownmember.setMobile(kong);
              smownmember.setApplydesc(kong);
              smownmember.setReviewuid(kong2);
              smownmember.setEmail(kong);
              smownmember.setApplydate(create_date);
              smownmember.setStatus(kong3);
              smownmember.setManageuser(kong);
              smownmember.setManagedate(create_date);
              smownmember.setManagedesc(kong);
              smownmember.setDzstatus(kong4);
              smownmember.setLevel(kong3);
              SmownmemberMapper.insert(smownmember);
       }
       else{
       
        Scplatformsidesetting  pmids = ScplatformsidesettingMapper.selectnewpmid();
         pmid =pmids.getPmid();
       }
       Integer isdefault = Integer.valueOf(request.getParameter("isdefault"));                              
 
         invoiceInformationManagementMapper.insertfpinfo(ID,request.getParameter("rise"),request.getParameter("taxnum"),request.getParameter("addressMail"),request.getParameter("addressCompany"),request.getParameter("openBank"),request.getParameter("account"),request.getParameter("phone"),isdefault,request.getParameter("invoiceCode"),request.getParameter("remark"),pmid);                                              
        
       // Smownmember status = SmownmemberMapper.selectbyapplymid(smownmember);
       
String aa = "1" ;
        
      /*  Integer link_user_id= userInfo.getId();
        SysCompany companyInfo2 = new SysCompany();
        companyInfo2.setId(ID);
        companyInfo2.setLinkUserId(link_user_id);
        sysCompanyMapper.updateusid(companyInfo2);*/
        //updateusid

       //companyInfoMapper.insert( request);
    }


}
