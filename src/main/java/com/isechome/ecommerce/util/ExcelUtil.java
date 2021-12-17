package com.isechome.ecommerce.util;

import com.alibaba.excel.EasyExcel;

import java.io.InputStream;
import java.util.List;

public class ExcelUtil<T> {
    /**
     * 实体对象
     */
    public Class<T> clazz;

    public ExcelUtil(Class<T> clazz)
    {
        this.clazz = clazz;
    }
    /**
     * 对excel表单默认第一个索引名转换成list（EasyExcel）
     *
     * @param is 输入流
     * @return 转换后集合
     */
    public List<T> importEasyExcel(InputStream is) throws Exception
    {
        return EasyExcel.read(is).head(clazz).sheet().doReadSync();
    }
}
