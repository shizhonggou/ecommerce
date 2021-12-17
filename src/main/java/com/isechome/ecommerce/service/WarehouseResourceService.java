package com.isechome.ecommerce.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.isechome.ecommerce.common.AjaxResult;
import com.isechome.ecommerce.common.Common;
import com.isechome.ecommerce.common.SecurityUserUtil;
import com.isechome.ecommerce.constant.CommonConstant;
import com.isechome.ecommerce.entity.*;
import com.isechome.ecommerce.mapper.ecommerce.*;
import com.isechome.ecommerce.mapper.isechome.SysAdminuserMapper;
import com.isechome.ecommerce.util.ArithmeticUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
@Transactional(transactionManager = "ecommerceMybatisPlatformTransactionManager")
public class WarehouseResourceService {
    @Autowired
    WarehouseResourceMapper warehouseResourceMapper;

    @Autowired
    WarehouseResourceActionMapper warehouseResourceActionMapper;

    @Autowired
    ShelfResourceMapper shelfResourceMapper;

    @Autowired
    LogisticsPurchaseMapper logisticsPurchaseMapper;

    @Autowired
    ScOrderListDetailMapper scOrderListDetailMapper;

    @Autowired
    ScWorkTimeMapper scWorkTimeMapper;

    @Autowired
    ScplatformsidesettingMapper scplatformsidesettingMapper;

    @Autowired
    SysAdminuserMapper sysAdminuserMapper;

    @Autowired
    ScPurchasBaseMapper scPurchasBaseMapper;

    @Autowired
    ScPurchasDetailMapper scPurchasDetailMapper;

    @Autowired
    ScShoppingCartMapper scShoppingCartMapper;

    @Autowired
    CalcPriceService calcPriceService;

    @Autowired
    WarehouseResourceService warehouseResourceService;

    /**
     * @return int
     * @Description 导入仓库资源保存到数据库中 写入日志
     * @Author zhaofy
     * @Date 2021/8/20
     * @Param [warehouseResourceList]
     **/
    public int importWarehouseResource(List<WarehouseResource> warehouseResourceList) {
        int retcode = 0;
        if (warehouseResourceList == null || warehouseResourceList.size() == 0) {
            retcode = -1;
        }

        //仓库资源id
        int resourceId = 0;
        for (WarehouseResource warehouseResource : warehouseResourceList) {
            WarehouseResource wResource = warehouseResourceMapper.getIdBySth(warehouseResource);
            warehouseResource = combinedResource(warehouseResource);
            if (wResource != null) {
                resourceId = wResource.getId();
                warehouseResource.setId(wResource.getId());
                warehouseResource.setReviewStatus(CommonConstant.WAREHOUSE_UN_REVIEW);
                //更新
                warehouseResourceMapper.updateByPrimaryKey(warehouseResource);
            } else {
                //插入
                resourceId = warehouseResourceMapper.insert(warehouseResource);
            }
            //写入仓库操作日志
            WarehouseResourceAction warehouseResourceAction = new WarehouseResourceAction();
            warehouseResourceAction.setWId(resourceId);
            warehouseResourceAction.setNum(warehouseResource.getNum());
            warehouseResourceAction.setReservedNum((double) 0);
            warehouseResourceAction.setType(CommonConstant.ACTION_RUKU);
            warehouseResourceAction.setAuditUserid(SecurityUserUtil.getCurrentUserId());
            warehouseResourceAction.setPmid(SecurityUserUtil.getPmid());
            insertWarehouseResourceLog(warehouseResourceAction);
        }
        return retcode;
    }

    /**
     * @return java.util.List<com.isechome.ecommerce.entity.WarehouseResource>
     * @Description 仓库资源列表
     * @Author zhaofy
     * @Date 2021/8/20
     * @Param [warehouseResource]
     **/
    public List<WarehouseResource> selectListByResource(WarehouseResource warehouseResource) {
        warehouseResource.setPmid(SecurityUserUtil.getPmid());
        List<WarehouseResource> warehouseResourceList = warehouseResourceMapper.selectListByResource(warehouseResource);

        //系统时间
        ScWorkTime scWorkTime = getWorkTime();
        if (ObjectUtil.isNotEmpty(scWorkTime)) {
            for (WarehouseResource warehouseResourceReturn : warehouseResourceList) {
                if (ObjectUtil.isNotNull(warehouseResourceReturn.getAuditTime()) && ObjectUtil.isNotNull(scWorkTime.getStartTime())) {
                    //审核时间显示 大于前一天开盘后的日期
                    if (warehouseResourceReturn.getAuditTime().before(scWorkTime.getStartTime())) {
                        warehouseResourceReturn.setAuditTime(null);
                    }
                }
            }
        }
        return warehouseResourceList;
    }

    /**
     * @return void
     * @Description 更新资源的预留量数据 写入日志
     * @Author zhaofy
     * @Date 2021/8/21
     * @Param [warehouseResource]
     **/
    public void updateWarehouseResource(WarehouseResource warehouseResource) {
        warehouseResourceMapper.updateByResource(warehouseResource);
        WarehouseResourceAction warehouseResourceAction = new WarehouseResourceAction();
        warehouseResourceAction.setWId(warehouseResource.getId());
        warehouseResourceAction.setReservedNum(warehouseResource.getReservedNum());
        warehouseResourceAction.setReservedDate(warehouseResource.getReservedDate());
        warehouseResourceAction.setType(CommonConstant.ACTION_YULIU);
        warehouseResourceAction.setAuditUserid(SecurityUserUtil.getCurrentUserId());
        warehouseResourceAction.setPmid(SecurityUserUtil.getPmid());
        insertWarehouseResourceLog(warehouseResourceAction);
    }

    /**
     * @return com.isechome.ecommerce.entity.WarehouseResource
     * @Description 根据id获取单条仓库资源
     * @Author zhaofy
     * @Date 2021/9/2
     * @Param [id]
     **/
    public WarehouseResource selectByPrimaryKey(int id) {
        return warehouseResourceMapper.selectByPrimaryKey(id);
    }

    /**
     * @return void
     * @Description 审核仓库资源
     * @Author zhaofy
     * @Date 2021/8/21
     * @Param [warehouseResource]
     **/
    public void auditWarehouseResource() {
        WarehouseResource warehouseResource = new WarehouseResource();
        warehouseResource.setAuditUserid(SecurityUserUtil.getCurrentUserId());
        warehouseResource.setReviewStatus(CommonConstant.WAREHOUSE_REVIEW);
        warehouseResource.setPmid(SecurityUserUtil.getPmid());
        warehouseResourceMapper.auditResource(warehouseResource);
        //获取审核的资源列表
        List<WarehouseResource> warehouseResourceList = warehouseResourceMapper.getAuditResourceId(warehouseResource);
        if (ArrayUtil.isNotEmpty(warehouseResourceList)) {
            for (WarehouseResource resource : warehouseResourceList) {
                warehouseResource.setId(resource.getId());
                //组合仓库审核操作日志
                warehouseResourceActionMapper.insert(mixWarehouseResourceAction(warehouseResource,
                        CommonConstant.ACTION_AUDIT_RESOURCE));
            }
        }
        //获取开盘时间
//        ScWorkTime scWorkTime = scWorkTimeMapper.selectByPmid(SecurityUserUtil.getPmid());
        //开盘时间
//        Date startTime = DateUtil.parseDateTime(DateUtil.today() + " " + Common.getHis(scWorkTime.getStartTime()));
//        Date now = DateTime.now();
        //未开盘审核资源直接上架
//        if (startTime.after(now)) {
//            //上架资源
//            warehouseResourceService.shelfResource();
//        }
        //审核后直接上架
        warehouseResourceService.shelfResource(warehouseResource.getPmid());
    }

    /**
     * @return void
     * @Description 仓库操作日志插入
     * @Author zhaofy
     * @Date 2021/8/20
     * @Param [warehouseResourceAction]
     **/
    public void insertWarehouseResourceLog(WarehouseResourceAction warehouseResourceAction) {
        warehouseResourceActionMapper.insert(warehouseResourceAction);
    }

    /**
     * @return void
     * @Description 资源定时上架
     * @Author zhaofy
     * @Date 2021/8/26
     * @Param []
     **/
    public void shelfResource(int pmid) {
        //前一天日期
        Date yesterday = DateUtil.yesterday().toSqlDate();
        //平台列表
//        List<Scplatformsidesetting> scplatformsidesettingList = scplatformsidesettingMapper.selectPmidList();
        //多个平台循环处理
//        for (Scplatformsidesetting scplatformsidesetting : scplatformsidesettingList) {
        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("beginDate", DateUtil.beginOfDay(yesterday));
//            queryMap.put("endDate", DateUtil.endOfDay(yesterday));
        queryMap.put("pmid", pmid);
        List<WarehouseResource> warehouseResourceList =
                warehouseResourceMapper.selectAuditListByResource(queryMap);

        for (WarehouseResource warehouseResource : warehouseResourceList) {
            //上架资源量
            double wResourceNum = warehouseResource.getNum();
            //如果预留资源到期，就加上预留资源
            double ReservedNum = 0.0;
            if (ObjectUtil.isNotNull(warehouseResource.getReservedDate())) {
                if (warehouseResource.getReservedDate().before(yesterday) || warehouseResource.getReservedDate().equals(yesterday)) {
                    ReservedNum = warehouseResource.getReservedNum();
//                    wResourceNum += ReservedNum;
                    wResourceNum = ArithmeticUtils.add(wResourceNum, ReservedNum);
                }
            }
            //加上 未售出的资源数量 （修改需求 改为 收盘将未售出资源返回仓库，上架不处理了）
//            double UnsoldNum = getUnSoldNum(warehouseResource.getId(), pmid, yesterday);
//            wResourceNum += UnsoldNum;

            //匹配今待发已匹配
            wResourceNum = matchPurchase(warehouseResource, wResourceNum);
            //如果上架的资源量不为0，就上架
            if (wResourceNum > 0) {
                //组合上架资源
                shelfResourceInsertAndUpdate(warehouseResource, wResourceNum);

                //组合仓库操作日志
                warehouseResourceActionMapper.insert(mixWarehouseResourceAction(warehouseResource,
                        CommonConstant.ACTION_SHANGJIA));
                //上架后 减掉仓库资源（仓库资源需要减去未售出的数量和预留资源到期的数量） 和预留资源 数量
                warehouseResource.setNum(wResourceNum - ReservedNum);
//                warehouseResource.setNum(wResourceNum - UnsoldNum - ReservedNum);
                warehouseResource.setReservedNum(ReservedNum);
                warehouseResourceMapper.updateShangJiaByPrimaryKey(warehouseResource);
            }
            //预留资源上架的话 写入操作日志
            if (ReservedNum > 0) {
                WarehouseResource rewarehouseResource = new WarehouseResource();
                rewarehouseResource.setReservedNum(ArithmeticUtils.sub(0.0, ReservedNum));
                rewarehouseResource.setPmid(warehouseResource.getPmid());
                rewarehouseResource.setReservedDate(warehouseResource.getReservedDate());
                rewarehouseResource.setId(warehouseResource.getId());
                warehouseResourceActionMapper.insert(mixWarehouseResourceAction(rewarehouseResource,
                        CommonConstant.ACTION_YULIU));
                //将仓库资源预留资源和日期清空
                warehouseResourceMapper.updateReserved(warehouseResource.getId());
            }
            //将仓库资源设置为未审核
            WarehouseResource reviewResource = new WarehouseResource();
            reviewResource.setReviewStatus(CommonConstant.WAREHOUSE_UN_REVIEW);
            reviewResource.setAuditTime(null);
            reviewResource.setId(warehouseResource.getId());
//                warehouseResourceMapper.unReviewByPrimaryKey(reviewResource);
        }
        //执行计算资源价格
        calcPriceService.CalcTodayPrice(pmid);
//        }
    }

    /**
     * @return void
     * @Description 收盘时上架资源返回仓库
     * @Author zhaofy
     * @Date 2021/9/28
     * @Param [pmid] 平台方id
     **/
    public void returnResource(int pmid) {
        ShelfResource shelfResourceSelect = new ShelfResource();
        shelfResourceSelect.setPmid(pmid);
//        shelfResourceSelect.setDate(DateUtil.beginOfDay(DateUtil.yesterday()).toSqlDate());//test
        shelfResourceSelect.setDate(DateUtil.parseDate(DateUtil.today()).toSqlDate());
        shelfResourceSelect.setStatus(CommonConstant.SHELF_STATUS_SHANGJIA);
        List<ShelfResource> shelfResourceList = shelfResourceMapper.selectListByResource(shelfResourceSelect);
        for (ShelfResource shelfResource : shelfResourceList) {
            dealUnSoldNum(shelfResource, pmid, CommonConstant.ACTION_RETURN);
        }

    }

    /**
     * @return void
     * @Description 根据资源id下架资源
     * @Author zhaofy
     * @Date 2021/12/1
     * @Param [id]
     **/
    public void offResourceById(int id) {
        dealUnSoldNum(shelfResourceMapper.selectByPrimaryKey(id), SecurityUserUtil.getPmid(),
                CommonConstant.ACTION_OFF_RESOURCE);
        //清空购物车
        scShoppingCartMapper.delByResourceId(SecurityUserUtil.getPmid(), id);
    }

    /**
     * @return com.isechome.ecommerce.common.AjaxResult
     * @Description 结算资源时 多余资源 返回仓库
     * @Author zhaofy
     * @Date 2021/9/1
     * @Param [base_id]
     **/
    public AjaxResult returnResourceByOrderId(String base_id) {
        List<ScOrderListDetail> orderListDetailList = scOrderListDetailMapper.selectAllByOrderId(base_id);
        for (ScOrderListDetail scOrderListDetail : orderListDetailList) {
            double retNum = ArithmeticUtils.sub(ObjectUtil.isNotNull(scOrderListDetail.getNum()) ?
                    scOrderListDetail.getNum() : 0.0, ObjectUtil.isNotNull(scOrderListDetail.getSettlementWeight()) ?
                    scOrderListDetail.getSettlementWeight() : 0.0);
            //结算数量小于下单数量，资源返回仓库
            if (retNum > 0.0) {
                WarehouseResource warehouseResource = new WarehouseResource();
                warehouseResource.setNum(retNum);
                warehouseResource.setId(shelfResourceMapper.getResourceIdById(scOrderListDetail.getResourceId()));
                //资源返回仓库
                warehouseResourceMapper.updateByReturnResource(warehouseResource);
                //组合仓库操作日志
                warehouseResourceActionMapper.insert(mixWarehouseResourceAction(warehouseResource,
                        CommonConstant.ACTION_CANCLE_ORDER));
            }
        }
        return AjaxResult.success();
    }

    /**
     * @return java.util.List<com.isechome.ecommerce.entity.WarehouseResourceAction>
     * @Description 仓库资源操作日志列表
     * @Author zhaofy
     * @Date 2021/9/23
     * @Param [warehouseResourceAction]
     **/
    public List<WarehouseResourceAction> selectByResource(WarehouseResourceAction warehouseResourceAction) {
        List<WarehouseResourceAction> warehouseResourceActionList =
                warehouseResourceActionMapper.selectByResource(warehouseResourceAction);
        //用户信息map
        Map<Integer, SysAdminuser> adminList = sysAdminuserMapper.selectIdAndName();

        //平台方信息map
        Map<Integer, Scplatformsidesetting> scplatformsidesettingMap =
                scplatformsidesettingMapper.selectPmIdAndDescription();

        for (WarehouseResourceAction warehouseResourceAction1 : warehouseResourceActionList) {
            warehouseResourceAction1.setTypeName(CommonConstant.WAREHOUSE_ACTION_TYPE.get(warehouseResourceAction1.getType()));
            if (ObjectUtil.isNotNull(warehouseResourceAction1.getAuditUserid()))
                warehouseResourceAction1.setUserName(adminList.get(warehouseResourceAction1.getAuditUserid()).getArealname());
            if (ObjectUtil.isNotNull(warehouseResourceAction1.getPmid()))
                warehouseResourceAction1.setPmName(scplatformsidesettingMap.get(warehouseResourceAction1.getPmid()).getDescription());
        }
        return warehouseResourceActionList;
    }

    /**
     * @return com.isechome.ecommerce.entity.ScWorkTime
     * @Description 获取昨日的停开盘时间
     * @Author zhaofy
     * @Date 2021/9/10
     * @Param []
     **/
    public ScWorkTime getWorkTime() {
        //获取开盘时间
        ScWorkTime scWorkTime = scWorkTimeMapper.selectByPmid(SecurityUserUtil.getPmid());
        if (ObjectUtil.isNotEmpty(scWorkTime)) {
            scWorkTime.setStartTime(DateUtil.parseDateTime(DateUtil.yesterday() + " " + Common.getHis(scWorkTime.getStartTime())));
            scWorkTime.setEndTime(DateUtil.parseDateTime(DateUtil.yesterday() + " " + Common.getHis(scWorkTime.getEndTime())));
        }
        return scWorkTime;
    }

    /**
     * @Description 处理未售出的资源返回仓库
     * @Author zhaofy
     * @Date 2021/8/26
     * @Param [wid, today]
     **/
    private void dealUnSoldNum(ShelfResource shelfResourceInfo, int pmid, int action) {
        double unSoldNum = 0.0;
        if (ObjectUtil.isNotNull(shelfResourceInfo)) {
//            ret = shelfResourceInfo.getNum() - shelfResourceInfo.getSoldNum();
            unSoldNum = ArithmeticUtils.sub(shelfResourceInfo.getNum(), shelfResourceInfo.getSoldNum());
            if (unSoldNum > 0) {
                ShelfResource updateShelfResourceInfo = new ShelfResource();
                updateShelfResourceInfo.setStatus(CommonConstant.SHELF_STATUS_RETURN_WAREHOUSE);
                updateShelfResourceInfo.setId(shelfResourceInfo.getId());
                shelfResourceMapper.updateByPrimaryKey(updateShelfResourceInfo);
                //返回仓库
//            warehouseResourceMapper.returnResource(shelfResourceInfo);
                WarehouseResource warehouseResource = new WarehouseResource();
                warehouseResource.setNum(unSoldNum);
                warehouseResource.setId(shelfResourceInfo.getWId());
                warehouseResource.setPmid(pmid);
                //资源返回仓库
                warehouseResourceMapper.updateByReturnResource(warehouseResource);
                //组合仓库操作日志 返回仓库
                warehouseResourceActionMapper.insert(mixWarehouseResourceAction(warehouseResource, action));
            }
        }
    }

    /**
     * @return void
     * @Description 上架资源，插入shelf上架表，根据资源存在与否判断是插入还是更新
     * @Author zhaofy
     * @Date 2021/9/9
     * @Param [warehouseResource, wResourceNum]
     **/
    private void shelfResourceInsertAndUpdate(WarehouseResource warehouseResource, double wResourceNum) {
        Date today = DateUtil.parseDate(DateUtil.today()).toSqlDate();
        Map<String, Object> selectMap = new HashMap<>();
        selectMap.put("date", today);
        selectMap.put("wId", warehouseResource.getId());
        selectMap.put("status", CommonConstant.SHELF_STATUS_SHANGJIA);
        //已经存在就更新 不存在插入
        ShelfResource shelfResource = shelfResourceMapper.getTodayResourceID(selectMap);
        if (ObjectUtil.isNotNull(shelfResource)) {
            shelfResourceMapper.updateResourceNumByPrimaryKey(mixShelfResource(warehouseResource, wResourceNum,
                    shelfResource));
        } else {
            shelfResourceMapper.insert(mixShelfResource(warehouseResource, wResourceNum, null));
        }
    }

    /**
     * @return double
     * @Description 匹配今待发已匹配
     * @Author zhaofy
     * @Date 2021/8/27
     * @Param [warehouseResource, wResourceNum]
     **/
    private double matchPurchase(WarehouseResource warehouseResource, double wResourceNum) {
        List<ScPurchasBase> purchasBaseList =
                scPurchasBaseMapper.selectUnassignedList(warehouseResource.getId());
        for (ScPurchasBase purchaseBase : purchasBaseList) {
            double matchNum = 0.0;//匹配的数量
            ScPurchasBase setPurchaseBase = new ScPurchasBase();
            if (ObjectUtil.isNotNull(purchaseBase)) {
                double unPurchaseNum = (ArithmeticUtils.sub(purchaseBase.getPurchaseNum(),
                        purchaseBase.getDistributeNum()));
                //全部满足采购单，全部匹配
                if (wResourceNum >= unPurchaseNum) {
                    setPurchaseBase.setDistributeNum(purchaseBase.getPurchaseNum());
                    setPurchaseBase.setId(purchaseBase.getId());
                    setPurchaseBase.setType(CommonConstant.LOGISTICS_PURCHASE_TYPE_TIHUODAN);//改为提货单

                    //根据detail表的ExtractId处理logisticsPurchase为提货单
                    List<ScPurchasDetail> purchaseDetailList =
                            scPurchasDetailMapper.selectByBaseId(purchaseBase.getId());
                    if (ObjectUtil.isNotEmpty(purchaseDetailList)) {
                        for (ScPurchasDetail purchaseDetail : purchaseDetailList) {
                            LogisticsPurchase logisticsPurchase = new LogisticsPurchase();
                            logisticsPurchase.setExtractId(purchaseDetail.getExtractId());
                            logisticsPurchase.setDistributeNum(purchaseDetail.getPurchaseNum());
                            logisticsPurchase.setType(CommonConstant.LOGISTICS_PURCHASE_TYPE_TIHUODAN);//改为提货单
                            logisticsPurchaseMapper.updateByExtractId(logisticsPurchase);
                        }
                    }
                    wResourceNum = ArithmeticUtils.sub(wResourceNum, unPurchaseNum);
                    matchNum = unPurchaseNum;
                    //部分满足采购单，部分匹配，状态还是 采购资源未分配
                } else {
                    setPurchaseBase.setDistributeNum(ArithmeticUtils.add(purchaseBase.getDistributeNum(),
                            wResourceNum));
                    setPurchaseBase.setId(purchaseBase.getId());
                    matchNum = wResourceNum;
                    wResourceNum = 0.0;
                }
                //匹配采购单分配量
                scPurchasBaseMapper.updateByMatchResource(setPurchaseBase);
                //组合仓库操作日志
                warehouseResource.setNum(ArithmeticUtils.sub(0.0, matchNum));
                warehouseResourceActionMapper.insert(mixWarehouseResourceAction(warehouseResource,
                        CommonConstant.ACTION_READY_NOMATCH));

                //匹配了采购单资源后把仓库资源减掉
                WarehouseResource warehouseResource1 = new WarehouseResource();
                warehouseResource1.setNum(matchNum);
                warehouseResource1.setId(warehouseResource.getId());
                warehouseResourceMapper.updateShangJiaByPrimaryKey(warehouseResource1);
            }
        }
        return wResourceNum;
    }


    /**
     * @return com.isechome.ecommerce.entity.ShelfResource
     * @Description 组合上架资源
     * @Author zhaofy
     * @Date 2021/8/26
     * @Param [warehouseResource, pmid]
     **/
    private ShelfResource mixShelfResource(WarehouseResource warehouseResource, double num,
                                           ShelfResource shelfResource_old) {
        ShelfResource shelfResource = new ShelfResource();
        if (ObjectUtil.isNotNull(shelfResource_old)) shelfResource.setId(shelfResource_old.getId());
        shelfResource.setWId(warehouseResource.getId());
        shelfResource.setVId(warehouseResource.getVId());
        shelfResource.setVarietyName(warehouseResource.getVarietyName());
        shelfResource.setMaterial(warehouseResource.getMaterial());
        shelfResource.setSpecification(warehouseResource.getSpecification());
        shelfResource.setHd(warehouseResource.getHd());
        shelfResource.setKd(warehouseResource.getKd());
        shelfResource.setCd(warehouseResource.getCd());
        shelfResource.setXd(warehouseResource.getXd());
        shelfResource.setFactory(warehouseResource.getFactory());
        shelfResource.setWarehouse(warehouseResource.getWarehouse());
        if (ObjectUtil.isNotNull(shelfResource_old)) {
            if (shelfResource_old.getStatus() == CommonConstant.SHELF_STATUS_SHANGJIA) {
                shelfResource.setNum(num + shelfResource_old.getNum());
            } else if (shelfResource_old.getStatus() == CommonConstant.SHELF_STATUS_RETURN_WAREHOUSE) {
                shelfResource.setNum(num);
            }
        } else {
            shelfResource.setNum(num);
        }
        shelfResource.setSoldNum(0.0);
        shelfResource.setStatus(CommonConstant.SHELF_STATUS_SHANGJIA);
        shelfResource.setDate(DateUtil.parseDate(DateUtil.today()));
        shelfResource.setType(1);
        shelfResource.setSystemType(1);
        shelfResource.setPmid(warehouseResource.getPmid());
        shelfResource.setCreateTime(DateUtil.parseDateTime(DateUtil.now()));
        return shelfResource;
    }

    /**
     * @return com.isechome.ecommerce.entity.WarehouseResourceAction
     * @Description 组合仓库操作日志
     * @Author zhaofy
     * @Date 2021/8/26
     * @Param [warehouseResource]
     **/
    private WarehouseResourceAction mixWarehouseResourceAction(WarehouseResource warehouseResource, int type) {
        int pmid = ObjectUtil.isNull(warehouseResource.getPmid()) ? SecurityUserUtil.getPmid() :
                warehouseResource.getPmid();

        WarehouseResourceAction warehouseResourceAction = new WarehouseResourceAction();
        warehouseResourceAction.setWId(warehouseResource.getId());
        warehouseResourceAction.setNum(warehouseResource.getNum());
        warehouseResourceAction.setReservedNum(warehouseResource.getReservedNum());
        warehouseResourceAction.setType(type);
        warehouseResourceAction.setCreateTime(DateUtil.parseDateTime(DateUtil.now()));
        warehouseResourceAction.setAuditUserid(warehouseResource.getAuditUserid() == null ? 0 :
                warehouseResource.getAuditUserid());
        warehouseResourceAction.setPmid(pmid);
        return warehouseResourceAction;
    }

    /**
     * @return com.isechome.ecommerce.entity.WarehouseResource
     * @Description 组合需要插入数据库的entity
     * @Author zhaofy
     * @Date 2021/8/20
     * @Param [warehouseResource]
     **/
    private WarehouseResource combinedResource(WarehouseResource warehouseResource) {
        HashMap<String, Double> specMap = Common.getSpecInfo(warehouseResource.getSpecification());
        warehouseResource.setHd(specMap.get("hd"));
        warehouseResource.setKd(specMap.get("kd"));
        warehouseResource.setCd(specMap.get("cd"));
        warehouseResource.setXd(specMap.get("xd"));
        warehouseResource.setVId(Common.getVid(warehouseResource.getVarietyName()));
        warehouseResource.setStatus(1);
        warehouseResource.setReviewStatus(CommonConstant.WAREHOUSE_UN_REVIEW);
        warehouseResource.setPmid(SecurityUserUtil.getPmid());
        return warehouseResource;
    }


}
