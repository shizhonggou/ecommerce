/*
 * @Author: lina
 * @Date: 2021-05-27 09:20:12
 * @LastEditTime: 2021-05-31 16:02:24
 * @FilePath: \e-commerce\src\main\java\com\isechome\ecommerce\constant\CommonConstant.java
 * @Description:
 * @Copyright: © 2021, SteelHome. All rights reserved.
 */
package com.isechome.ecommerce.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonConstant {


    public static final Integer GCGZJ = 1; //钢城钢之家会员id
    public static final Integer PAGE_SIZE = 20; //分页的每页数量
    public static final Integer NAVIGATE_PAGES = 5; //连续显示的条数

    public static final Byte LUOWEN_VID = 1; //螺纹品种id
    public static final Byte PANLUO_VID = 2; //盘螺品种id
    public static final Byte GAOXIAN_VID = 3; //高线品种id

    public static final HashMap<Object,Object> VARIETY_NAME_TYPE = new HashMap<>();
    static {
        VARIETY_NAME_TYPE.put("螺纹",1);
        VARIETY_NAME_TYPE.put("盘螺",2);
        VARIETY_NAME_TYPE.put("高线",3);
    }

    public static final Integer CHAKAN = 0; // 查看权限
    public static final Integer JIAOYI = 8; // 交易权限
    public static final Integer ZILIAOXIUGAI = 16; // 资料修改权限

    //仓库资源操作类型
    public static final Integer ACTION_RUKU = 1; // 入库
    public static final Integer ACTION_SHANGJIA = 2; // 上架
    public static final Integer ACTION_YULIU = 3; // 预留
    public static final Integer ACTION_RETURN = 4; // 未售完资源返回仓库
    public static final Integer ACTION_READY_NOMATCH = 5; // 待发已匹配资源
    public static final Integer ACTION_CANCLE_ORDER = 6; // 取消订单返回资源
    public static final Integer ACTION_AUDIT_RESOURCE = 7; // 审核资源
    public static final Integer ACTION_OFF_RESOURCE = 8; // 下架资源
    public static final HashMap<Integer,String> WAREHOUSE_ACTION_TYPE = new HashMap<>();
    static {
        WAREHOUSE_ACTION_TYPE.put(ACTION_RUKU,"入库");
        WAREHOUSE_ACTION_TYPE.put(ACTION_SHANGJIA,"上架");
        WAREHOUSE_ACTION_TYPE.put(ACTION_YULIU,"预留");
        WAREHOUSE_ACTION_TYPE.put(ACTION_RETURN,"返回仓库");
        WAREHOUSE_ACTION_TYPE.put(ACTION_READY_NOMATCH,"待发已匹配");
        WAREHOUSE_ACTION_TYPE.put(ACTION_CANCLE_ORDER,"取消订单");
        WAREHOUSE_ACTION_TYPE.put(ACTION_AUDIT_RESOURCE,"审核资源");
        WAREHOUSE_ACTION_TYPE.put(ACTION_OFF_RESOURCE,"下架资源");
    }
    public static final List<Map<Integer,String>> WAREHOUSE_RESOURCE_ACTION_LIST = new ArrayList<>();
    static {
        WAREHOUSE_RESOURCE_ACTION_LIST.add(WAREHOUSE_ACTION_TYPE);
    }

    //sc_logistics_purchase & sc_purchaseorder_management_base 表的type
    public static final byte LOGISTICS_PURCHASE_TYPE_TIHUODAN = 1; //1:提货单
    public static final byte LOGISTICS_PURCHASE_TYPE_CAIGOUDAISHENHE = 2; //2:采购待审核
    public static final byte LOGISTICS_PURCHASE_TYPE_CAIGOUSHENHEBUTONGGUO = 3; //3采购审核不通过
    public static final byte LOGISTICS_PURCHASE_TYPE_CAIGOUWEIFUKUAN = 4; //4采购未付款
    public static final byte LOGISTICS_PURCHASE_TYPE_CAIGOUWEIFENPEI = 5; //5采购资源未分配

    //极差设置的类型
    public static final Byte[] RANGE_KIND_ARRAY = {1, 2, 3, 4, 5}; //1、钢厂升贴水 2、材质极差 3、规格极差 4、长度极差 5、仓库极差

    //任务调度状态
    public static final byte TASK_STATUS_ON = 1;
    public static final byte TASK_STATUS_OFF = 0;

    //仓库资源预留量更新类型
    public static final int TYPE_UPDATE_NUM = 1; //数量
    public static final int TYPE_UPDATE_DATE = 2; //日期

    //仓库资源审核状态
    public static final int WAREHOUSE_UN_REVIEW = 0; //未审核
    public static final int WAREHOUSE_REVIEW = 1; //已审核

    //上架资源状态
    public static final int SHELF_STATUS_SHANGJIA = 1; //上架资源
    public static final int SHELF_STATUS_RETURN_WAREHOUSE = 2; //返回仓库资源
}