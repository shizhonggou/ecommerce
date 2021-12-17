package com.isechome.ecommerce.service;

import com.isechome.ecommerce.constant.CommonConstant;
import com.isechome.ecommerce.entity.LogisticsPurchase;
import com.isechome.ecommerce.entity.ScOrderListBase;
import com.isechome.ecommerce.entity.ScOrderListDetail;
import com.isechome.ecommerce.mapper.ecommerce.LogisticsPurchaseMapper;
import com.isechome.ecommerce.mapper.ecommerce.ScCompanyBalanceMapper;
import com.isechome.ecommerce.mapper.ecommerce.ScOrderListBaseMapper;
import com.isechome.ecommerce.mapper.ecommerce.ScOrderListDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional( transactionManager = "ecommerceMybatisPlatformTransactionManager")
public class ShipmentsImportService {
    @Autowired
    LogisticsPurchaseMapper logisticsPurchaseMapper;

    @Autowired
    ScOrderListDetailMapper scOrderListDetailMapper;

    @Autowired
    ScOrderListBaseMapper scOrderListBaseMapper;

    @Autowired
    ScCompanyBalanceMapper scCompanyBalanceMapper;

    @Autowired
    WarehouseResourceService warehouseResourceService;
    
    // 提单发货量量导入
    public String importExcel(List<LogisticsPurchase> shipmentsList) {
        int retcode = 0;
        String err_extract_id = ""; //错误的提单号
        String err_extract_id2 = ""; //已提单的提单号
        String err_extract_id3 = ""; //大于数量的提单号
        if (shipmentsList == null || shipmentsList.size() == 0) {
            retcode = -1;
            return "-";
        }

        /* //取出不符合要求的提货单ID（已经提货）*/

        List<String> extract_id_tmp=new ArrayList<String>();  
        
        for (LogisticsPurchase exceldata : shipmentsList) {
            extract_id_tmp.add(exceldata.getExtractId());
        }
        String[] arr = extract_id_tmp.toArray(new String[extract_id_tmp.size()]);
        //所有发货数量>0的提货单ID
        List<LogisticsPurchase> extractids = logisticsPurchaseMapper.getAllExtractId(arr);

        String extract_id = "";
        for1:
        for (LogisticsPurchase exceldata : shipmentsList) {
            extract_id = exceldata.getExtractId();
            for2:
            for (LogisticsPurchase logisticsPurchase : extractids) {
                if(logisticsPurchase.getExtractId().equals(extract_id)){
                    //说明这个提货单已经提货，直接跳过
                    err_extract_id2 = extract_id + "," + err_extract_id2;
                    continue for1;
                }
            }

            LogisticsPurchase logistics = logisticsPurchaseMapper.selectByExtractId(extract_id);
            if (logistics == null) {
                retcode = 0;
                err_extract_id = extract_id + "," + err_extract_id;
                continue;
            }else{
                /*if(logistics.getActualNum()>0){ //说明这个提货单已经提货，直接跳过
                    err_extract_id2 = extract_id + "," + err_extract_id2;
                    // System.out.println(extract_id+"已经提货");
                    continue;
                }*/
                // 1. 修改提货单表的发货数量
                if(logistics.getNum()>=exceldata.getActualNum() && exceldata.getActualNum()>0){
                    retcode = this.updateActualNum(logistics, exceldata);
                } else {
                    err_extract_id3 = extract_id + "," + err_extract_id3;
                    continue;
                }
            }
            // 2. 根据详细表id查出所有的提货单，当所有的提货单的发货数量>0时，更改详细表的结算结算重量和结算金额
            if(this.isUpdateOrderListDetail(logistics.getOrderDetailid())==0){
                System.out.println("更新详细表");
                retcode = this.updateOrderListDetail(logistics);
                if(retcode == 0){
                    System.out.println("详细表更新失败");
                    continue;
                }
                // 3. 根据订单id查出所有的提货单，当所有的提货单的发货数量>0时，更改订单表的结算结算重量和结算金额，并且状态改为已结算
                if(this.isUpdateOrderListBase(logistics.getOrderId())==0){
                    retcode = this.updateOrderListBase(logistics);
                    if(retcode == 0){  //更新失败
                        System.out.println("更新失败");
                        continue;
                    }
                    System.out.println("更新基础表");
                    // 更新完结算余额、资源入库
                    retcode = this.updateBalanceAndResource(logistics.getOrderId());
                    if(retcode == 0){  //更新失败
                        System.out.println("余额更新失败");
                    }
                } else {
                    System.out.println("不更新基础表");
                    continue;
                }
            } else {
                System.out.println("不更新详细表");
                continue;
            }
        }
        if(err_extract_id.isEmpty()){
            err_extract_id = "-";
        }
        if(err_extract_id2.isEmpty()){
            err_extract_id2 = "-";
        }
        if(err_extract_id3.isEmpty()){
            err_extract_id3 = "-";
        }

        return err_extract_id + "|" + err_extract_id2+ "|" + err_extract_id3;
    }

    private int updateActualNum(LogisticsPurchase logistics, LogisticsPurchase exceldata) {
        int res;
        try {
            logistics.setActualNum(exceldata.getActualNum());
            logistics.setType(CommonConstant.LOGISTICS_PURCHASE_TYPE_TIHUODAN);
            res = logisticsPurchaseMapper.updateByExtractIdAndType(logistics);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            res = 0;
        }
        return res;
    }

    private int updateOrderListDetail(LogisticsPurchase logistics) {
        int res = 0;
        ScOrderListDetail orderListDetail = scOrderListDetailMapper.selectByPrimaryKey(logistics.getOrderDetailid());
        String baseid = orderListDetail.getOrderBaseid();
        // 查询是否是一票制，一票制需要加上运费
        ScOrderListBase scOrderListBase = scOrderListBaseMapper.getInfoByOrderId(baseid);
        double freight;
        if(scOrderListBase.getTicketType() == 1){
            freight = scOrderListBase.getFreight();
        } else {
            freight = 0.0;
        }
        BigDecimal actual_num_sum =  logisticsPurchaseMapper.selectSumByOrderDetailId(logistics.getOrderDetailid());
        BigDecimal settlement_weight = actual_num_sum;
        BigDecimal n_freight = BigDecimal.valueOf(freight);
        BigDecimal tmp_price = BigDecimal.valueOf(orderListDetail.getPrice());
        BigDecimal price = n_freight.add(tmp_price);
        double settlement_money = settlement_weight.multiply(price).doubleValue();
        try {
            scOrderListDetailMapper.updateMoneyAndWeightByid(orderListDetail.getId(), settlement_money, settlement_weight.doubleValue());
            res = 1;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            res = 0;
        }
        return res;
    }

    // 判断是否需要更新订单详细表，返回发货数量为0的条数
    private int isUpdateOrderListDetail(Integer orderdetailid){
        List<LogisticsPurchase> all_logistics = logisticsPurchaseMapper.selectByOrderDetailId(orderdetailid);
        int geshu = 0;
        for (LogisticsPurchase logistics : all_logistics) {
            if(logistics.getActualNum()==0)
            geshu++;
        }
        System.out.println("geshu = " + geshu);
        return geshu;
    }

    // 判断是否需要更新订单表，返回发货数量为0的条数
    private int isUpdateOrderListBase(String orderid){
        List<LogisticsPurchase> all_logistics = logisticsPurchaseMapper.selectByOrderId(orderid);
        int geshu = 0;
        for (LogisticsPurchase logistics : all_logistics) {
            if(logistics.getActualNum()==0)
            geshu++;
        }
        return geshu;
    }

    private int updateOrderListBase(LogisticsPurchase logistics) {
        int res = 1;
        List<ScOrderListDetail> orderListDetail = scOrderListDetailMapper.selectAllByOrderId(logistics.getOrderId());
        
        if(orderListDetail.size()==0 || orderListDetail == null){
            res = 0;
        } else {
            double money = 0;
            double weight = 0;
            for (ScOrderListDetail scOrderListDetail : orderListDetail) {
                money += scOrderListDetail.getSettlementMoney();
                weight += scOrderListDetail.getSettlementWeight();
            }
            ScOrderListBase scOrderListBase = new ScOrderListBase();
            if(money !=0 && weight!= 0){
                scOrderListBase.setSettlementMoney(money);
                scOrderListBase.setSettlementWeight(weight);
                scOrderListBase.setOrderId(logistics.getOrderId());
                res = scOrderListBaseMapper.updateBaseByOrderId(scOrderListBase);
            }
        }

        return res;
    }

    private int updateBalanceAndResource(String orderId) {
        int res;
        ScOrderListBase orderinfo = scOrderListBaseMapper.getOrderInfoByOrderId(orderId);
        if(orderinfo == null){
            return 0;
        }
        int purchase_mid = orderinfo.getPurchaseMid();
        int pmid = orderinfo.getPmid();

        double discrepancy_money = Double.parseDouble(new BigDecimal(orderinfo.getTotalPrice().toString()).subtract(new BigDecimal(orderinfo.getSettlementMoney().toString())).toString());
        double discrepancy_weight = Double.parseDouble(new BigDecimal(orderinfo.getNum().toString()).subtract(new BigDecimal(orderinfo.getSettlementWeight().toString())).toString());
        System.out.println("------------显示差额------------------");
        System.out.println("pmid：" + pmid);
        System.out.println("purchase_mid：" + purchase_mid);
        System.out.println("discrepancy_money：" + discrepancy_money);
        System.out.println("-------------------------------------");
        // 更新余额
        try {
            res = scCompanyBalanceMapper.updateBalanceByComidForSettlement(pmid, purchase_mid, discrepancy_money);
            warehouseResourceService.returnResourceByOrderId(orderinfo.getOrderId());
            res = 1;
            System.out.println("余额和资源更新成功");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            res = 0;
        }
        return res;
    }

}
