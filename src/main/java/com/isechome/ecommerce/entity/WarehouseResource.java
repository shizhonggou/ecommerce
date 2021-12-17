package com.isechome.ecommerce.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class WarehouseResource implements Serializable {
    private Integer id;

    private Integer vId;

    @ExcelProperty("品名")
    private String varietyName;

    @ExcelProperty("材质")
    private String material;

    @ExcelProperty("规格")
    private String specification;

    private Double hd;

    private Double kd;

    private Double cd;

    private Double xd;

    @ExcelProperty("钢厂")
    private String factory;

    @ExcelProperty("仓库")
    private String warehouse;

    @ExcelProperty("资源量")
    private Double num;

    private Double reservedNum;

    @DateTimeFormat("yyyy-MM-dd")
    private Date reservedDate;

    private Integer status;

    private Integer reviewStatus;

    private Integer systemType;

    private Integer pmid;

    private Date createTime;

    private Date auditTime;

    private Integer auditUserid;
}