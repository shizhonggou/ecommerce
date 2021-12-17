package com.isechome.ecommerce.security;

import cn.hutool.core.util.ObjectUtil;
import com.isechome.ecommerce.entity.BalanceInfo;
import com.isechome.ecommerce.entity.ScWorkTime;
import com.isechome.ecommerce.entity.Scplatformsidesetting;
import com.isechome.ecommerce.entity.Smownmember;
import com.isechome.ecommerce.mapper.ecommerce.FinancialMapper;
import com.isechome.ecommerce.mapper.ecommerce.ScWorkTimeMapper;
import com.isechome.ecommerce.mapper.ecommerce.ScplatformsidesettingMapper;
import com.isechome.ecommerce.mapper.isechome.SmownmemberMapper;
import com.isechome.ecommerce.security.entity.SysAdminuser;
import com.isechome.ecommerce.security.entity.SysCompany;
import com.isechome.ecommerce.security.mapper.isechome.SecurityUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalTime;
import java.util.*;

/**
 * @Author: libing
 * @Date: 2021/4/13
 * @Description: spring-security权限管理框架
 **/
@Service
@Transactional
public class UserSecurityService implements UserDetailsService {
    @Autowired
    private SecurityUserMapper securityUserMapper;
    @Autowired
    private ScplatformsidesettingMapper ScplatformsidesettingMapper;
    @Autowired
    private SmownmemberMapper SmownmemberMapper;
    @Autowired
    HttpServletRequest request;
    @Autowired
    FinancialMapper financialMapper;
    @Autowired
    ScWorkTimeMapper scWorkTimeMapper;
    /**
     * @Description 实现了UserDetailsService接口中的loadUserByUsername方法
     * 如果用户不存在，则抛出UsernameNotFoundException异常
     * @param username
     * @return org.springframework.security.core.userdetails.UserDetails
     * @throws UsernameNotFoundException
     */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SecuritySysUser sysuser = new SecuritySysUser();
        SysAdminuser user = securityUserMapper.getUserInfoByUsername(username);
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        Map<Integer, String> level_map = new HashMap<Integer, String>();
        if (user == null) {
            throw new UsernameNotFoundException("用户名不对");
        }
        Date create_date = new Date();

        Byte kong3 = 0;
        String kong = "0";

        Integer kong2 = 0;
        Boolean kong4 = true;
        SysCompany member = securityUserMapper.getMemberInfoById(user.getComid());
        HttpSession session = request.getSession();

        String token = (String) session.getAttribute("token");
        Integer pmid = 0;
        Scplatformsidesetting  pmids = ScplatformsidesettingMapper.selectnewpmid();
        Integer pmid1 =pmids.getPmid();
        if (token == null) {
            pmid = pmid1;
            sysuser.setPmid(pmid1);
        } else {
            Scplatformsidesetting tk = ScplatformsidesettingMapper.selectbytoken(token);
            if (tk == null) {
                pmid = pmid1;
            } else {
                pmid = tk.getPmid();
            }
            sysuser.setPmid(pmid);
        }

        Scplatformsidesetting logo = ScplatformsidesettingMapper.selectLogo(pmid); 
        
        String logourl= logo.getLogoUrl();

        sysuser.setLogoUrl(logourl);

        Integer comid = user.getComid();
        BalanceInfo balanceInfo = new BalanceInfo();
       
        balanceInfo = financialMapper.selectCompaneyBalance(comid,pmid);
        if(balanceInfo == null){
            BalanceInfo balanceInfo2 = new BalanceInfo();
    
            balanceInfo2.setBalance(0.0);
            balanceInfo =balanceInfo2;
        }
        Smownmember smownmember = new Smownmember();
        smownmember.setApplymid(user.getComid());
        smownmember.setReviewmid(pmid);
        Smownmember status = SmownmemberMapper.selectbyapplymid(smownmember);
        if (status == null) {
            smownmember.setApplyuid(user.getId());
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
        } else {
            if (status.getStatus() == 1) {
                list.add(new SimpleGrantedAuthority("ROLE_GYLQX_YSH"));
            }

        }

        //显示开盘时间
        ScWorkTime scWorkTime = scWorkTimeMapper.selectByPmid(pmid);
        if (ObjectUtil.isNotNull(scWorkTime)) {
            LocalTime s_time = LocalTime.of(scWorkTime.getStartTime().getHours(), scWorkTime.getStartTime().getMinutes(), scWorkTime.getStartTime().getSeconds());
            LocalTime e_time = LocalTime.of(scWorkTime.getEndTime().getHours(), scWorkTime.getEndTime().getMinutes(), scWorkTime.getEndTime().getSeconds());
            sysuser.setWorkTime(s_time + " - " + e_time);
        }else{
            sysuser.setWorkTime("");
        }
//        sysuser.setScWorkTime();
        sysuser.setId(user.getId());
        sysuser.setUsername(username);
        sysuser.setPassword(user.getApasswd());

        sysuser.setSysAdminUserInfo(user);
        sysuser.setSysCompanyInfo(member);
        sysuser.setBalanceInfo(balanceInfo);
        // System.out.println("111"+user.getPassword()+"登录成功2!");

        if (user.getComid().intValue() == pmid.intValue()) { // 平台方
            list.add(new SimpleGrantedAuthority("ROLE_GYLQX_PTF"));
            list.add(new SimpleGrantedAuthority("ROLE_GYLQX_YSH"));
        }
        else {

            list.add(new SimpleGrantedAuthority("ROLE_KH"));   
        }
        if (user.getIsmain() == 1) { // 主账号
            list.add(new SimpleGrantedAuthority("ROLE_GYLQX_MASTER"));
        }
        level_map.put(4, "MASTERJG");
        level_map.put(8, "MASTERXS");
        level_map.put(16, "MASTERCG");
        level_map.put(32, "MASTERKC");
        level_map.put(64, "MASTERCW");
        level_map.put(128,"MASTERCGSH");
        if (user.getRights() != null && user.getRights() > 0 ) {
            long qx = user.getRights();
            if ((qx & 4) == 4) {
                list.add(new SimpleGrantedAuthority("ROLE_GYLQX_" + level_map.get(((int) 4))));
            }
            if ((qx & 8) == 8) {
                list.add(new SimpleGrantedAuthority("ROLE_GYLQX_" + level_map.get(((int) 8))));
            }
            if ((qx & 16) == 16) {
                list.add(new SimpleGrantedAuthority("ROLE_GYLQX_" + level_map.get(((int) 16))));
            }
            if ((qx & 32) == 32) {
                list.add(new SimpleGrantedAuthority("ROLE_GYLQX_" + level_map.get(((int) 32))));
            }
            if ((qx & 64) == 64) {
                list.add(new SimpleGrantedAuthority("ROLE_GYLQX_" + level_map.get(((int) 64))));
            }
            if ((qx & 128) == 128) {
                list.add(new SimpleGrantedAuthority("ROLE_GYLQX_" + level_map.get(((int) 128))));
            }
        }


        /*
         * if(){ list.add(new SimpleGrantedAuthority("GYLQX_MAIN")); }
         */
        sysuser.setAuthorities(list);
        return sysuser;
    }

}
