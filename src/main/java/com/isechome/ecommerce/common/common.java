package com.isechome.ecommerce.common;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.isechome.ecommerce.constant.CommonConstant;
import com.isechome.ecommerce.entity.LoginMessage;
import com.isechome.ecommerce.security.SecuritySysUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class Common {

    //打开新窗口
    public void newWindow(String url, PrintWriter out) {
        String html = "<script>";
        html += "window.open( '" + url + ");";
        html += "</script>";
        out.print(html);
        out.flush();
        out.close();

        // echo $html;
        // exit;
    }

    public void goURL1(String url, PrintWriter out) {
        String html = "<script>";
        html += "window.location.href='" + url + "'";
        html += "</script>";
        out.print(html);
        out.flush();
        out.close();
    }

    //页面跳转
    public void goURL(String url, PrintWriter out) {
        String html = "<script>";
        // if( _blank == true ){
        //     html += "window.open( '$url' );"; 
        // }else{
        html += "window.location.href='" + url + "'";
        // }
        html += "</script>";
        out.print(html);
        out.flush();
        out.close();

        // echo $html;
        // exit;
    }

    // public void fGoURL( String url ){
    //     String html = "<script>";
    //     html += "window.opener.location.href='"+url +"';";
    //     html += "window.close();";
    //     html += "</script>";
    //     echo $html;
    //     exit;
    // }

    // //页面刷新
    public void reload(PrintWriter out) {
        String html = "<script>";
        html += "window.location.reload();";
        html += "</script>";
        out.print(html);
        out.flush();
        out.close();
    }

    //后退
    public void goBack(PrintWriter out) {
        String html = "<script>";
        html += "history.back();";
        html += "</script>";
        out.print(html);
        out.flush();
        out.close();
    }

    //弹出框
    public void alert(String str, PrintWriter out) {
        String html = "<script>";
        html += "alert('" + str + "');";
        html += "</script>";
        out.print(html);
    }

    public String gg_str_replace(String str) {
        str = str.trim();
        str.replace("Ф", "").replace("ф", "").replace("ф", "").replace("mm", "").replace("MM", "").replace("米", "").replace("厘米", "");
        str = str.trim();

        return str;
    }

    public static String Pagebar(String url, Map<String, String> param, Integer limit, Integer page, Integer total) {
        if (total < 0) {
            return "";
        }
        if (url == "") {
            return "";
        }
        String link = url + "?";
        // if (is_array($param)) {
        //   foreach ($param as $str_key => $str_value) {
        //       $link = $link . "$str_key=" . urlencode($str_value) . "&";
        //   }
        // }

        for (Entry<String, String> entry : param.entrySet()) {
            try {
                link = link + entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "utf-8") + "&";
            } catch (UnsupportedEncodingException e) {

                //e.printStackTrace();
            }
        }

        int int_pages = (int) Math.ceil((double) total / limit);
        if (total != 0 && limit != 0 && int_pages == 0) {
            int_pages = 1;
        }
        //$int_pages = ceil($total / $limit);
        if (page < 1) {
            page = 1;
        }
        if (page > int_pages) {
            page = int_pages;
        }

        String start_url = link + "page=1";
        String end_url = link + "page=" + int_pages;
        String pre_url = link + "page=" + (page - 1);
        String next_url = link + "page=" + (page + 1);
        int start_page = 1, end_page = 1;
        if (page < 6) {
            start_page = 1;
            end_page = 7;
        } else {
            start_page = page - 5;
            end_page = page + 1;
        }
        if (end_page > int_pages) {
            end_page = int_pages;
        }
        //$urls = null;

        java.util.LinkedHashMap<Integer, String> urls = new java.util.LinkedHashMap();
        /**  THE URL */
        for (Integer i = start_page, j = 0; i <= end_page; i++, j++) {
            String temp_url = link + "page=" + i;
            if (i == page) {
                urls.put(j, "<strong>[" + i + "]</strong>&nbsp;");
                //$urls[$j] = "<strong>[" +i +"]</strong>&nbsp;";
            } else {
                urls.put(j, "<a href=\"" + temp_url + "\">[" + i + "]</a>&nbsp;");

                // $urls[$j] = "<a href=\""+temp_url+"\">[" +i +"]</a>&nbsp;";
            }
        }
        String str_html = "";
        if (urls != null) {
            str_html =
                    "共&nbsp;" + total + "&nbsp;条信息&nbsp;&nbsp;共&nbsp;" + int_pages + "&nbsp;页&nbsp;&nbsp;" + str_html + "<a href=\"" + start_url + "\" text=\"回第1页\">首页</a>&nbsp;&nbsp;";
            if (page > 1) {
                str_html = str_html + "<a href=\"" + pre_url + "\">上页</a>&nbsp;&nbsp;";
            } else {
                str_html = str_html + "上页&nbsp;&nbsp;";
            }

            for (Entry<Integer, String> string : urls.entrySet()) {
                str_html = str_html + string.getValue();
            }
            str_html = str_html + "&nbsp;";
            if (page >= int_pages) {
                str_html = str_html + "下页&nbsp;&nbsp;";
            } else {
                str_html = str_html + "<a href=\"" + next_url + "\">下页</a>&nbsp;&nbsp";
            }

            str_html = str_html + "<a href=\"" + end_url + "\" text=\"到第" + int_pages + "\">尾页</a> &nbsp;&nbsp;跳到 " +
                    "<input type=text size=4 onBlur=window.location.href='" + pre_url + "&page='+this.value  /> 页 ";
            return str_html;
        }
        return "";
    }

    public String check_login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LoginMessage lm = new LoginMessage();
        lm.setStatus("false");
        lm.setCompabb("");
        SecuritySysUser securitySysUser = SecurityUserUtil.getCurrentUser();

        if (securitySysUser != null) {

            lm.setStatus("true");
            lm.setCompabb(securitySysUser.getSysCompanyInfo().getComnameshort() );

            //System.out.println(securitySysUser.getMemberInfo().getCompabb());
            //lm.setCompabb("");
        }

        String callback = request.getParameter("callback");
        String writestr = "";
        ObjectMapper om = new ObjectMapper();
        if (callback != null) {
            writestr = callback + "(" + om.writeValueAsString(lm) + ")";
        } else {
            writestr = om.writeValueAsString(lm);
        }
        return writestr;

    }

    public void dayin() {
        System.out.println("当前时间为:" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss" +
                ".SSS")) + "-----------------------------------");
    }

    public String zhangdie(float data) {
        //$data = number_format($data,2);
        if (data > 0) {
            return "<span class=\"red\">↑" + data + "</span>";
        } else if (data < 0) {
            String data1 = String.valueOf(data).replace("-", "");
            return "<span class=\"green\">↓" + data1 + "</span>";
        } else {
            return "-";
        }
    }

    public String zhangdie(int data) {
        //$data = number_format($data,2);
        if (data > 0) {
            return "<span class=\"red\">↑" + data + "</span>";
        } else if (data < 0) {
            String data1 = String.valueOf(data).replace("-", "");
            return "<span class=\"green\">↓" + data1 + "</span>";
        } else {
            return "-";
        }
    }

    public String gettoday() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = new Date();
        return sdf.format(date1);
    }

    public boolean check_is_chinese(String content) {

        String pattern = "/[^x80-xff]./";

        boolean isMatch = Pattern.matches(pattern, content);
        return isMatch;
    }


    public Date strToDate(String datestr, String dateFormat)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
		Date dateTime = null;
        try {
            dateTime = simpleDateFormat.parse(datestr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateTime;
    }

    /**
     * @Author: lina
     * @msg: 日期转换cron表达式
     * @param {Date} date
     * @param {String} dateFormat
     * @return {*}
     */    
    public String dateToStr(Date date, String dateFormat)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);  
        String formatTimeStr = null;  
        if (date != null) {  
            formatTimeStr = sdf.format(date);  
        }  
        return formatTimeStr;
    }

    /**
     * 获取现在时间
     *
     * @return返回长时间格式 yyyy-MM-dd HH:mm:ss
     */
    public static Date getNowDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(0);
        Date currentTime_1 = formatter.parse(dateString, pos);
        return currentTime_1;
    }

    /**
     * 获取现在时间
     *
     * @return返回短时间格式 yyyy-MM-dd
     */
    public static Date getNowDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(0);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }


    //返回小时
    public int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    // 返回分钟
    public int getMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    public static String getHis (Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE)+":00";
    }
	
	/**
     * @return java.util.HashMap<java.lang.String, java.lang.Double>
     * @Description 拆分规格
     * @Author zhaofy
     * @Date 2021/8/20
     * @Param [Spec]
     **/
    public static HashMap<String, Double> getSpecInfo(String Spec) {
        HashMap<String, Double> map = new HashMap<>();
        map.put("hd", 0.0);
        map.put("kd", 0.0);
        map.put("cd", 0.0);
        map.put("xd", 0.0);
        String[] strings = Spec.split("\\*");
        for (int i = 0; i < strings.length; i++) {
            if (i == 0) {
                map.put("hd", numberFormat(strings[0]));
            }
            if (i == 1) {
                map.put("kd", numberFormat(strings[1]));
            }
            if (i == 2) {
                map.put("cd", numberFormat(strings[2]));
            }
            if (i == 3) {
                map.put("xd", numberFormat(strings[3]));
            }
        }
        return map;
    }

    /**
     * @return java.lang.Double
     * @Description 去掉规格中的非数字符号 字母
     * @Author zhaofy
     * @Date 2021/8/20
     * @Param [str]
     **/
    public static Double numberFormat(String str) {
        str = str.replace("Φ", "");
        str = str.replace("m", "");
        str = str.replace("M", "");
        str = str.replace("F", "");
        return Double.valueOf(str);
    }

    // 获取品种id
    public static int getVid(String varietyName){
        int vid = 0;
        if (varietyName.contains("螺纹")) {
            vid = CommonConstant.LUOWEN_VID;
        } else if (varietyName.contains("盘螺")) {
            vid = CommonConstant.PANLUO_VID;
        } else if (varietyName.contains("高线")) {
            vid = CommonConstant.GAOXIAN_VID;
        }
        return vid;
    }

    /**
     * @Description: 判断数组是否有重复元素
     * @Author: shizg
     * @Date: 2021/8/28 10:05
     * @param array:
     * @return: boolean
     **/
    public static boolean cheakIsRepeat(String[] array) {
        HashSet<String> hashSet = new HashSet<String>();
        for (int i = 0; i < array.length; i++) {
            hashSet.add(array[i]);
        }
        if (hashSet.size() == array.length) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @Description: 交易密码验证
     * @Author: shizg
     * @Date: 2021/8/28 10:25
     * @param pwd:
     * @return: boolean
     **/
    public static boolean cheakTredePassWD( String pwd ) {
        //从session中获取登录用户信息
        SecuritySysUser loginMessage = SecurityUserUtil.getCurrentUser();
        String passWordSession = loginMessage.getSysAdminUserInfo().getTradepwd();
        String passWdmd5 = MD5Util.encode(pwd);
        //交易密码检查
        if ( ! passWdmd5.equals( passWordSession.toUpperCase() ) ) {
            return false;
        }else {
            return true;
        }
    }

    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     * @author jqlin
     */
    public static boolean isEffectiveDate( Date startTime, Date endTime) {
        LocalTime now_time = LocalTime.now();
        LocalTime s_time = LocalTime.of(startTime.getHours(),startTime.getMinutes(),startTime.getSeconds());
        LocalTime e_time = LocalTime.of(endTime.getHours(),endTime.getMinutes(),endTime.getSeconds());

        if (s_time.isBefore(now_time) && e_time.isAfter(now_time)){
            return true;
        } else {
            return false;
        }
    }

}
