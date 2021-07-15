package com.isechome.ecommerce.common;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.isechome.ecommerce.entity.LoginMessage;
import com.isechome.ecommerce.security.SecuritySysUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class common {
    
//打开新窗口
    public void newWindow( String url,PrintWriter out ){
        String html = "<script>";
         html += "window.open( '"+url+");";
         html += "</script>";
         out.print(html);
         out.flush();
         out.close();
      
        // echo $html;
        // exit;
    }

    public void goURL1( String url,PrintWriter out){
        String html = "<script>";
        html += "window.location.href='"+ url +"'";
        html += "</script>";
        out.print(html);
        out.flush();
        out.close();
    }

    //页面跳转
    public void goURL( String url,PrintWriter out){
        String html = "<script>";
        // if( _blank == true ){
        //     html += "window.open( '$url' );"; 
        // }else{
            html += "window.location.href='"+ url +"'";
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
    public void reload(PrintWriter out){
        String html = "<script>";
        html += "window.location.reload();";
        html += "</script>";
        out.print(html);
        out.flush();
        out.close();
    }
    //后退
    public void goBack(PrintWriter out){
        String html = "<script>";
        html+= "history.back();";
        html += "</script>";
        out.print(html);
        out.flush();
        out.close();
    }
    //弹出框
    public void alert(String str,PrintWriter out ){
        String html = "<script>";
        html += "alert('"+str+"');";
        html += "</script>";
        out.print(html);
  }

  public String gg_str_replace(String str){
    str=str.trim();
    str.replace("Ф", "").replace("ф", "").replace("ф", "").replace("mm", "").replace("MM", "").replace("米", "").replace("厘米", "");    
    str=str.trim();
    
    return str;
    }
    
    public static String Pagebar( String url, Map<String,String> param, Integer limit, Integer page, Integer total) {
            if( total < 0 ) {
                return "";
            }
            if( url == "" ) {
            return "";
            }
            String link = url + "?";
            // if (is_array($param)) {
            //   foreach ($param as $str_key => $str_value) {
            //       $link = $link . "$str_key=" . urlencode($str_value) . "&";
            //   }
            // }

            for (Entry<String , String> entry  : param.entrySet()) {
                try {
                    link = link +entry.getKey()+"=" + URLEncoder.encode(entry.getValue(),"utf-8") + "&";
                } catch (UnsupportedEncodingException e) {
                    
                    //e.printStackTrace();
                }
            }

            int int_pages = (int)Math.ceil((double)total / limit);
            if(total!=0&&limit!=0&&int_pages==0)
            {
                int_pages=1;
            }
            //$int_pages = ceil($total / $limit);
            if (page < 1) {
            page = 1;
            }
            if (page > int_pages) {
            page = int_pages;
            }
            
            String start_url = link + "page=1";
            String end_url = link + "page="+int_pages;
            String pre_url = link + "page=" + ( page - 1 );
            String next_url = link +"page=" + ( page + 1 );
            int start_page=1,end_page=1;
            if( page < 6 ){
            start_page = 1;
            end_page = 7;
            }else{
            start_page = page -5;
            end_page = page +1;
            }
            if( end_page > int_pages ){
            end_page = int_pages;
            }
            //$urls = null;

            java.util.LinkedHashMap<Integer,String> urls=new java.util.LinkedHashMap();
            /**  THE URL */
            for( Integer i = start_page, j = 0; i <= end_page; i++, j++ ){
            String  temp_url = link + "page="+i;
            if( i == page ){
                urls.put(j, "<strong>[" +i +"]</strong>&nbsp;");
                //$urls[$j] = "<strong>[" +i +"]</strong>&nbsp;";
            }else{
                urls.put(j, "<a href=\""+temp_url+"\">[" +i +"]</a>&nbsp;");

                // $urls[$j] = "<a href=\""+temp_url+"\">[" +i +"]</a>&nbsp;";
            }
            }
            String str_html="";
            if( urls!=null ){
                str_html = "共&nbsp;"+total+"&nbsp;条信息&nbsp;&nbsp;共&nbsp;"+int_pages+"&nbsp;页&nbsp;&nbsp;"+str_html + "<a href=\""+start_url+"\" text=\"回第1页\">首页</a>&nbsp;&nbsp;";
                if( page > 1 ){
                    str_html = str_html +"<a href=\""+pre_url+"\">上页</a>&nbsp;&nbsp;";
                }else{
                    str_html = str_html + "上页&nbsp;&nbsp;";
                }

                for (Entry<Integer,String> string : urls.entrySet())
                {
                    str_html = str_html + string.getValue();
                }
                str_html = str_html + "&nbsp;";
                if( page >= int_pages ){
                    str_html = str_html + "下页&nbsp;&nbsp;";
                }else{
                    str_html = str_html +"<a href=\""+next_url+"\">下页</a>&nbsp;&nbsp";
                }
                
                    str_html = str_html +"<a href=\""+end_url+"\" text=\"到第"+int_pages+"\">尾页</a> &nbsp;&nbsp;跳到 <input type=text size=4 onBlur=window.location.href='"+pre_url+"&page='+this.value  /> 页 ";  
                    return str_html; 
            }
            return ""; 
      }
    public String check_login(HttpServletRequest request,HttpServletResponse response) throws IOException
    {
        LoginMessage lm=new LoginMessage();
        lm.setStatus("false");
        lm.setCompabb("");
        SecuritySysUser securitySysUser = SecurityUserUtil.getCurrentUser();
        
        if (securitySysUser!=null) {

            lm.setStatus("true");
            lm.setCompabb(securitySysUser.getCompanyInfo().getCom_name_short() );

            //System.out.println(securitySysUser.getMemberInfo().getCompabb());
            //lm.setCompabb("");
        }
        
        String callback=request.getParameter("callback");
        String writestr="";
        ObjectMapper om = new ObjectMapper();
        if(callback!=null)
        {
            writestr=callback+"("+om.writeValueAsString(lm)+")";
        }
        else
        {
            writestr=om.writeValueAsString(lm);
        }
        return writestr;
     
    }

    public void dayin()
    {
        System.out.println("当前时间为:"+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))+"-----------------------------------");
    }

    public String zhangdie(float data){
        //$data = number_format($data,2);
        if(data>0){
            return "<span class=\"red\">↑"+data+"</span>";
        }else if(data<0){
        		String data1 =String.valueOf(data).replace("-","");
            return "<span class=\"green\">↓"+data1+"</span>";
        }else{
            return "-";
        }
    }
    public String zhangdie(int data){
        //$data = number_format($data,2);
        if(data>0){
            return "<span class=\"red\">↑"+data+"</span>";
        }else if(data<0){
        		String data1 =String.valueOf(data).replace("-","");
            return "<span class=\"green\">↓"+data1+"</span>";
        }else{
            return "-";
        }
    }

    public String  gettoday()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = new Date();
        return sdf.format(date1);
    }

    public  boolean check_is_chinese(String content) {

        String pattern = "/[^x80-xff]./";
 
        boolean isMatch = Pattern.matches(pattern, content);
		return isMatch;
	}

    public Date strToDate(String datestr)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateTime = null;
        try {
            dateTime = simpleDateFormat.parse(datestr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateTime;
    }
}
