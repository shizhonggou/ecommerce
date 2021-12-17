package com.isechome.ecommerce.service;

import com.isechome.ecommerce.common.MD5Util;
import com.isechome.ecommerce.common.SecurityUserUtil;
import com.isechome.ecommerce.common.Common;
import com.isechome.ecommerce.entity.ScSettingRange;
import com.isechome.ecommerce.entity.ScSettingRangeLog;
import com.isechome.ecommerce.entity.ScSteelMill;
import com.isechome.ecommerce.mapper.ecommerce.ScSettingRangeLogMapper;
import com.isechome.ecommerce.mapper.ecommerce.ScSettingRangeMapper;
import com.isechome.ecommerce.mapper.ecommerce.ScSteelMillMapper;
import com.isechome.ecommerce.security.SecuritySysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description: 极差设置service
 * @Author: shizg
 * @Date: 2021/8/21 16:40
 * @return: null
 **/
@Service
@Slf4j
@Transactional( transactionManager = "ecommerceMybatisPlatformTransactionManager")
public class ScSettingRangeService {
    @Autowired
    ScSettingRangeLogMapper scSettingRangeLogMapper;
    @Autowired
    ScSettingRangeMapper scSettingRangeMapper;

    @Autowired
    ScSteelMillMapper scSteelMillMapper;
    /**
     * @Description: 极差设置页面显示
     * @Author: shizg
     * @Date: 2021/8/27 14:36
     * @param request:
     * @param model:
     * @return: void
     **/
    public void showSettingsRange(HttpServletRequest request, Model model) {
        int kind = 1;
        boolean settingsStatus = true;
        Date date = Common.getNowDateShort();
        Date savedate = Common.getNowDateShort();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        if( request.getParameter("date" ) != null ) {
            date = sdf.parse(request.getParameter("date"),pos);
        }
        if( request.getParameter("savedate" ) != null ) {
            savedate = sdf.parse(request.getParameter("savedate"),pos);
        }
        if( request.getParameter("kind" ) != null ) {
            kind = Integer.parseInt(request.getParameter("kind"));
        }
        if ( kind < 1 || kind > 10 ) {
            kind = 1;
        }
        int pmid = SecurityUserUtil.getPmid();

        //获取钢厂名称列表
        if ( 1 == kind ) {
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("pmid",pmid);
            map.put("name","");
            map.put("state","");
            map.put("city","");
            List<ScSteelMill> scSteelMillList = new ArrayList<>();
            scSteelMillList = scSteelMillMapper.getScSteelMilList(map);
            model.addAttribute("scSteelMillList", scSteelMillList);
        }

        model.addAttribute("kind", kind);
        model.addAttribute("date", sdf.format( date ) );
        model.addAttribute("savedate", sdf.format( savedate ) );
        model.addAttribute("mark", "settings");
        model.addAttribute("title","级差设置");
        Date today = date;

        ScSettingRange scSettingRangeMaxDate = scSettingRangeMapper.getSettingRangeDateByKind( pmid, kind, date );
        byte reviewType = 0;
        if ( scSettingRangeMaxDate != null ) {
            today = scSettingRangeMaxDate.getDate();
            reviewType = scSettingRangeMaxDate.getReviewType();
            if ( reviewType == 1 ) {
                settingsStatus = false;
            }
        }
        List<ScSettingRange> scSettingRangeInfoList = scSettingRangeMapper.getSettingRangeInfoByKind( pmid, kind, today, reviewType );
        String statusMessage = "";
        if ( !settingsStatus ) {
            statusMessage = "已审核（"+sdf.format(today)+"）";
        }else {
            statusMessage = "未审核（"+sdf.format(today)+"）";
        }
        model.addAttribute("settingsStatus", settingsStatus);
        model.addAttribute("statusMessage", statusMessage);
        model.addAttribute("scSettingRangeInfoList",scSettingRangeInfoList);
    }

    /**
     * @Description: 极差页面提交的数据保存
     * @Author: shizg
     * @Date: 2021/8/28 10:43
     * @param request:
     * @return: int
     **/
    public int saveRange(HttpServletRequest request) {
        int returnStatus = 0;
        returnStatus = checkRangeData( request );
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);

        String varietyNameStr = request.getParameter("varietyName");
        String descriptionStr = request.getParameter("description");
        String jiajianjiaStr = request.getParameter("jiajianjia");
        Date today = sdf.parse( request.getParameter("savedate"), pos );
        Byte kind = Byte.parseByte( request.getParameter("kind") );
        String actiontype = request.getParameter("actiontype");
        Byte reviewType = 0;
        if ( actiontype.equals("review") ) {
            reviewType = 1;
        }
        if ( descriptionStr != null ) {
            String[] descriptionArr = descriptionStr.split(",");
            String[] jiajianjiaArr = jiajianjiaStr.split(",");
            String[] varietyNameArr = new String[descriptionArr.length];
            if ( 3 == kind ) {
                varietyNameArr = varietyNameStr.split(",");
            }
            Date createDate = Common.getNowDateShort();
            List<ScSettingRange> scSettingRangeList = new ArrayList<>();
            //从session中获取登录用户信息
            SecuritySysUser loginMessage = SecurityUserUtil.getCurrentUser();
            int pmid = SecurityUserUtil.getPmid();
            if ( descriptionArr.length > 0 ) {
                //先删除今天已有的数据
                scSettingRangeMapper.deleteByDateAndStatus( today, kind, pmid, reviewType );
                if ( reviewType == 1 ) {
                    scSettingRangeMapper.deleteByDateAndStatus( today, kind, pmid, (byte)0 );
                }
                for ( int i = 0; i < descriptionArr.length; i++ ) {
                    ScSettingRange scSettingRange = new ScSettingRange();
                    if ( reviewType == 0 ) {
                        scSettingRange.setAdminid( loginMessage.getSysAdminUserInfo().getId() );
                        scSettingRange.setReviewId(0);
                    }else {
                        scSettingRange.setAdminid( 0 );
                        scSettingRange.setReviewId( loginMessage.getSysAdminUserInfo().getId() );
                    }
                    scSettingRange.setCreateDate(createDate);
                    scSettingRange.setReviewDate(createDate);
                    scSettingRange.setDescription(descriptionArr[i]);
                    scSettingRange.setJiajianjia(Double.parseDouble( jiajianjiaArr[i] ) );
                    scSettingRange.setKind(kind);
                    if ( 3 == kind ) {
                        scSettingRange.setVarietyName( varietyNameArr[i] );
                    }else {
                        scSettingRange.setVarietyName( null );
                    }
                    scSettingRange.setDate(today);
                    scSettingRange.setPmid(pmid);
                    scSettingRange.setReviewType( reviewType );
                    scSettingRange.setStatus((byte)1);
                    scSettingRangeList.add(scSettingRange);
                }
                int total = scSettingRangeMapper.insertList(scSettingRangeList);
                //记录到log表
                saveDataToRangeLog(scSettingRangeList);
            }

        }else {
            returnStatus = 1;
        }
        return  returnStatus;
    }

    /**
     * @Description: 数据记录到log表
     * @Author: shizg
     * @Date: 2021/8/27 14:38
     * @param scSettingRangeListResult:
     * @return: void
     **/
    private void saveDataToRangeLog(List<ScSettingRange> scSettingRangeListResult) {
        List<ScSettingRangeLog> scSettingRangeLogList = new ArrayList<>();
        for ( int i = 0; i < scSettingRangeListResult.size(); i++ ) {
            ScSettingRangeLog scSettingRangeLog = new ScSettingRangeLog();
            scSettingRangeLog.setAdminid( scSettingRangeListResult.get(i).getAdminid());
            scSettingRangeLog.setDate( scSettingRangeListResult.get(i).getDate());
            scSettingRangeLog.setCreateDate( scSettingRangeListResult.get(i).getCreateDate());
            scSettingRangeLog.setDescription( scSettingRangeListResult.get(i).getDescription().toString());
            scSettingRangeLog.setJiajianjia( scSettingRangeListResult.get(i).getJiajianjia());
            scSettingRangeLog.setKind( scSettingRangeListResult.get(i).getKind());
            scSettingRangeLog.setBaseid( scSettingRangeListResult.get(i).getId());
            scSettingRangeLog.setPmid( scSettingRangeListResult.get(i).getPmid());
            scSettingRangeLog.setReviewDate( scSettingRangeListResult.get(i).getReviewDate());
            scSettingRangeLog.setReviewId( scSettingRangeListResult.get(i).getReviewId());
            scSettingRangeLog.setVarietyName( scSettingRangeListResult.get(i).getVarietyName());
            scSettingRangeLog.setStatus( scSettingRangeListResult.get(i).getStatus());
            scSettingRangeLog.setReviewType( scSettingRangeListResult.get(i).getReviewType());
            scSettingRangeLogList.add(scSettingRangeLog);
        }
        scSettingRangeLogMapper.insertLogData( scSettingRangeLogList);
    }

    /**
     * @Description: 检查前台提交的基价数据
     * @Author: shizg
     * @Date: 2021/8/21 9:35
     * @param request:
     * @return: int
     **/
    private int checkRangeData(HttpServletRequest request) {
        int returnStatus = 0;
        String descriptionStr = request.getParameter("description");
        String actiontype = request.getParameter("actiontype");
        String[] descriptionArr = descriptionStr.split(",");
        Byte kind = Byte.parseByte( request.getParameter("kind") );
        if ( kind != 3 ) {
            boolean repeatFlag = Common.cheakIsRepeat(descriptionArr);
            if ( !repeatFlag ) {
                returnStatus = 1;
            }
        }
        if ( actiontype.equals("review") ) {
            String settingspwd = request.getParameter("settingspwd");
            boolean pwdFlag = Common.cheakTredePassWD(settingspwd);
            if ( ! pwdFlag ) {
                returnStatus = 2;
            }
        }
        return returnStatus;
    }

}