package com.isechome.ecommerce.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
public class ParameterRequestUtils extends HttpServletRequestWrapper {
    private Map<String, String[]> params = new HashMap<>();

    /**
     * 必须要实现的构造方法
     * @param request
     */
    public ParameterRequestUtils(HttpServletRequest request) {
        super(request);
        //将参数表，赋予给当前的Map以便于持有request中的参数
        this.params.putAll(request.getParameterMap());
    }

    /**
     *    使用方法demo，通过构造方法直接传入参数  
     *    Map<String,Object> addMap=new HashMap<>();
     *    addMap.put("自定义参数1","值1");addMap.put("自定义参数2","值2");
     *    ParameterRequestUtils req=new ParameterRequestUtils(request,addMap);
     * 重载构造方法
     * @param request
     * @param extendParams
     */
    public ParameterRequestUtils(HttpServletRequest request, Map<String, Object> extendParams) {
        this(request);
        //这里将扩展参数写入参数表
        addAllParameters(extendParams);
    }
    /*
    * 重载构造方法
    * @param request
    * @param extendParams
    */
    public ParameterRequestUtils(HttpServletRequest request, String key,Object value) {
        this(request);
        //这里修改参数
        updataParameter(key,value);
    }

    /**
     * 在获取所有的参数名,必须重写此方法，否则对象中参数值映射不上
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Enumeration<String> getParameterNames() {
        return new Vector(params.keySet()).elements();
    }

    /**
     * 重写getParameter方法
     * @param name 参数名
     * @return 返回参数值
     */
    @Override
    public String getParameter(String name) {
        String[] values = params.get(name);
        if (values == null || values.length == 0) {
            return null;
        }
        return values[0];
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = params.get(name);
        if (values == null || values.length == 0) {
            return null;
        }
        return values;
    }

    /*
    修改参数值
     */

     public void updataParameter(String key,Object value){
        params.put(key, (String[]) value);
     }
    /**
     * 增加多个参数
     * @param otherParams 增加的多个参数
     */
    public void addAllParameters(Map<String, Object> otherParams) {
        for (Map.Entry<String, Object> entry : otherParams.entrySet()) {
            addParameter(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 增加参数
     * getParameterMap()中的类型是<String,String[]>类型的，所以这里要将其value转为String[]类型
     * @param name 参数名
     * @param value 参数值
     */
    public void addParameter(String name, Object value) {
        if (value != null) {
            if (value instanceof String[]) {
                params.put(name, (String[]) value);
            } else if (value instanceof String) {
                params.put(name, new String[]{(String) value});
            } else {
                params.put(name, new String[]{String.valueOf(value)});
            }
        }
    }

    /**
     * 移除参数
     * @param name 参数名
     */
    public void removeParameter(String name) {
        if (params.get(name) != null) {
            params.remove(name);
        }
    }

    public Map<String,String[]> getParameterMap() {
        return params;
    }
}

