package com.isechome.ecommerce.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.isechome.ecommerce.common.Common;
import com.isechome.ecommerce.common.SecurityUserUtil;
import com.isechome.ecommerce.constant.CommonConstant;
import com.isechome.ecommerce.entity.*;
import com.isechome.ecommerce.mapper.ecommerce.*;
import com.isechome.ecommerce.security.SecuritySysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Description: 计算价格service
 * @Author: shizg
 * @Date: 2021/8/30 9:01
 * @param :
 * @return: null
 **/

@Service
@Slf4j
@Transactional( transactionManager = "ecommerceMybatisPlatformTransactionManager")
public class CalcPriceService {
    @Autowired
    ScTodayBasePriceInfoMapper scTodayBasePriceInfoMapper;

    @Autowired
    ScSettingRangeMapper scSettingRangeMapper;

    @Autowired
    ScShelfResourceMapper scShelfResourceMapper ;

    @Autowired
    ScMaintainPriceMapper scMaintainPriceMapper ;

    @Autowired
    ScModifyPriceMapper scModifyPriceMapper ;

    /**
     * @Description: 外部调用入口
     * @Author: shizg
     * @Date: 2021/9/7 17:44
     * @param pmid:
     * @return: void
     **/
    public void CalcTodayPrice ( int pmid  ){
        //获取基价
        List<ScTodayBasePriceInfo> scTodayBasePriceInfoList = GetTodayBasePrice( pmid );
        if ( scTodayBasePriceInfoList != null ) {
            //获取最新极差设置
            HashMap<Byte,List<ScSettingRange>> rangeMap = GetSettingRange( pmid );
            if ( rangeMap != null ) {
                //对今日上架资源计算价格
                Date today = Common.getNowDateShort();
                List<ScShelfResource> scShelfResourceList = scShelfResourceMapper.getScTodayResourceByPmid( today, pmid );
                if ( scShelfResourceList != null ) {
                    CalcResourcePrice( scTodayBasePriceInfoList, rangeMap, scShelfResourceList, today, pmid );
                }
            }
        }
    }

    /**
     * @Description: 正直处理计算价格的总函数
     * @Author: shizg
     * @Date: 2021/9/7 17:45
     * @param scTodayBasePriceInfoList:
     * @param rangeMap:
     * @param scShelfResourceList:
     * @return: void
     **/
    public void CalcResourcePrice( List<ScTodayBasePriceInfo> scTodayBasePriceInfoList, HashMap<Byte,List<ScSettingRange>> rangeMap, List<ScShelfResource> scShelfResourceList, Date today, int pmid ) {
        Double calResultPrice = 0.0;
        Double rangePrice = -10000.0;
        List<ScShelfResource> scShelfResourcePriceList = new ArrayList<>();
        //处理调价
        List<ScModifyPrice> scModifyPriceList = scModifyPriceMapper.getTodayModifyPriceforCal( pmid, (byte)1, today );
        for ( int i = 0; i < scShelfResourceList.size(); i++ ) {
            if( scShelfResourceList.get(i).getPrice() > 0.0 ) {
                if ( scModifyPriceList != null ) {
                    for (int j = 0; j < scModifyPriceList.size(); j++) {
                        if ( (scShelfResourceList.get(i).getVid() == 1 && scModifyPriceList.get(j).getType().intValue() == 1) || (scShelfResourceList.get(i).getVid() == 2 && scModifyPriceList.get(j).getType().intValue() == 3) || (scShelfResourceList.get(i).getVid() == 3 && scModifyPriceList.get(j).getType().intValue() == 2)  ) {
                            Double newprice = scShelfResourceList.get(i).getPrice() + scModifyPriceList.get(j).getPrice() ;
                            scShelfResourceList.get(i).setPrice( newprice );
                            break;
                        }
                    }
                }
                scShelfResourcePriceList.add( scShelfResourceList.get(i) );
            }else{
                Double resourceBasePrice = GetResourceBasePrice( scShelfResourceList.get(i), scTodayBasePriceInfoList );
                if ( resourceBasePrice > 1.0 ) {
                    rangePrice = GetResourceRangePrice( scShelfResourceList.get(i), rangeMap );
                    if ( rangePrice > -10000.0 ) {
                        calResultPrice = resourceBasePrice + rangePrice;
                        scShelfResourceList.get(i).setPrice( calResultPrice );
                        scShelfResourcePriceList.add( scShelfResourceList.get(i) );
                    }
                }
            }

        }
        //处理调价更新之后，将调价记录的更新时间、更新人添加到记录中，下次再计算就不用再考虑此调价了
        Date nowtime = Common.getNowDate();
        for (int k = 0; k < scModifyPriceList.size(); k++) {
            scModifyPriceList.get(k).setUpdateTime( nowtime );
            scModifyPriceList.get(k).setUpdateUserId( 1 );
            scModifyPriceMapper.updateByPrimaryKey( scModifyPriceList.get(k));
        }

        //更新上架资源价格以及记录价格到maintainprice
        if ( scShelfResourcePriceList.size() > 0 ) {
            UpdateScShelfResourcePrice( scShelfResourcePriceList );
        }

    }

    /**
     * @Description: 更新上架资源的价格
     * @Author: shizg
     * @Date: 2021/9/7 17:45
     * @param scShelfResourcePriceList:
     * @return: void
     **/
    private void UpdateScShelfResourcePrice(List<ScShelfResource> scShelfResourcePriceList) {
        List<ScMaintainPrice> scMaintainPriceList = new ArrayList<>();
        Date date = Common.getNowDateShort();
        Date createdate = Common.getNowDate();
        int pmid = 0;
        byte kind = 1;
        byte status = 1;
        for ( int i = 0; i < scShelfResourcePriceList.size(); i++ ) {
            //保存价格到资源表
            scShelfResourceMapper.updateByPrimaryKey( scShelfResourcePriceList.get(i) );
            //处理maintainprice
            ScMaintainPrice scMaintainPrice = new ScMaintainPrice();
            scMaintainPrice.setPrice( scShelfResourcePriceList.get(i).getPrice() );
            scMaintainPrice.setDate( date );
            scMaintainPrice.setCreateTime( createdate );
            scMaintainPrice.setVarietyName( scShelfResourcePriceList.get(i).getVarietyName() );
            scMaintainPrice.setMaterial( scShelfResourcePriceList.get(i).getMaterial() );
            scMaintainPrice.setSpecification( scShelfResourcePriceList.get(i).getSpecification() );
            scMaintainPrice.setFactory( scShelfResourcePriceList.get(i).getFactory() );
            scMaintainPrice.setKind( kind );
            scMaintainPrice.setStorehouse( scShelfResourcePriceList.get(i).getWarehouse() );
            scMaintainPrice.setStatus( status );
            pmid = scShelfResourcePriceList.get(i).getPmid();
            scMaintainPrice.setPmid( pmid );
            scMaintainPriceList.add( scMaintainPrice );
        }
        if ( scMaintainPriceList.size() > 0 ) {
            //把数据库中今天的价格设置无效
            scMaintainPriceMapper.updateTodayPriceInvalid( pmid, date, kind );
            //插入价格数据
            scMaintainPriceMapper.insertPriceList( scMaintainPriceList );
        }
    }

    /**
     * @Description: 获取极差设置的资源差价
     * @Author: shizg
     * @Date: 2021/8/30 17:58
     * @param scShelfResource:
     * @param rangeMap:
     * @return: java.lang.Double
     **/
    private Double GetResourceRangePrice( ScShelfResource scShelfResource, HashMap<Byte, List<ScSettingRange>> rangeMap ) {
        Double rangePrice = -10000.0;
        //处理钢厂升贴水
        Double factoryRange = HandlerFactoryRange( scShelfResource.getFactory(), rangeMap.get( CommonConstant.RANGE_KIND_ARRAY[0] ) );
        //处理材质极差
        Double materialRange = HandlerFactoryRange( scShelfResource.getMaterial(), rangeMap.get( CommonConstant.RANGE_KIND_ARRAY[1] ) );
        //处理仓库极差
        Double warehouseRange = HandlerFactoryRange( scShelfResource.getWarehouse(), rangeMap.get( CommonConstant.RANGE_KIND_ARRAY[4] ) );
        String specificationStr = scShelfResource.getSpecification();
        String[] specificationArr = specificationStr.split("\\*");
        Double specRange = -10000.0;
        Double lengthRange = -10000.0;
        if ( specificationArr.length == 2 ) {
            //处理规格极差
            specRange = HandlerSpecRange( scShelfResource.getVarietyName(), specificationArr[0], rangeMap.get( CommonConstant.RANGE_KIND_ARRAY[2] ) );
            //处理长度极差
            lengthRange = HandlerFactoryRange( specificationArr[1], rangeMap.get( CommonConstant.RANGE_KIND_ARRAY[3] ) );

        }else {
            //处理规格极差
            specRange = HandlerSpecRange( scShelfResource.getVarietyName(), specificationArr[0], rangeMap.get( CommonConstant.RANGE_KIND_ARRAY[2] ) );
            lengthRange = 0.0;
        }
        if ( factoryRange > -10000.0 && materialRange > -10000.0 && warehouseRange> -10000.0 && lengthRange> -10000.0 && specRange > -10000.0 ) {
            rangePrice = factoryRange + materialRange +warehouseRange + lengthRange + specRange;
        }
        return rangePrice;
    }

    /**
     * @Description: 处理规格极差
     * @Author: shizg
     * @Date: 2021/8/30 19:16
     * @param varietyName:
     * @param spec:
     * @param scSettingRanges:
     * @return: java.lang.Double
     **/
    private Double HandlerSpecRange(String varietyName, String spec, List<ScSettingRange> scSettingRanges) {
        Double specRange = -10000.0;
        spec = spec.replaceAll("F","");
        spec = spec.replaceAll("Φ","");
        for ( int i = 0; i < scSettingRanges.size(); i++ ) {
            if ( varietyName.equals( scSettingRanges.get(i).getVarietyName() ) ) {
                String sbRangespec = scSettingRanges.get(i).getDescription() ;
                sbRangespec = sbRangespec.replaceAll("F","");
                sbRangespec = sbRangespec.replaceAll("Φ","");
                String[] rangeSpecArr = sbRangespec.split("-");
                if ( rangeSpecArr.length == 2 ) {
                    if ( Integer.parseInt( spec ) >= Integer.parseInt( rangeSpecArr[0] ) && Integer.parseInt( spec ) <= Integer.parseInt( rangeSpecArr[1] ) ) {
                        specRange = scSettingRanges.get(i).getJiajianjia();
                        break;
                    }
                }else if ( Integer.parseInt( spec ) == Integer.parseInt( rangeSpecArr[0] ) ){
                    specRange = scSettingRanges.get(i).getJiajianjia();
                    break;
                }
            }
        }
        return specRange;
    }


    /**
     * @Description:处理钢厂升贴水
     * @Author: shizg
     * @Date: 2021/8/30 18:06
     * @param factory:
     * @param scSettingRanges:
     * @return: java.lang.Double
     **/
    private Double HandlerFactoryRange(String factory, List<ScSettingRange> scSettingRanges) {
        Double factoryRange = -10000.0;
        for ( int i = 0; i < scSettingRanges.size(); i++ ) {
            if ( factory.equals( scSettingRanges.get(i).getDescription() ) ) {
                factoryRange = scSettingRanges.get(i).getJiajianjia();
                break;
            }
        }
        return factoryRange;
    }

    /**
     * @Description: 获取资源基价
     * @Author: shizg
     * @Date: 2021/8/30 17:26
     * @param scShelfResource:
     * @param scTodayBasePriceInfoList:
     * @return: java.lang.Double
     **/
    private Double GetResourceBasePrice(ScShelfResource scShelfResource, List<ScTodayBasePriceInfo> scTodayBasePriceInfoList) {
        Double basePrice = 0.0;
        byte resourceType = 0;
        if ( scShelfResource.getVarietyName().equals("螺纹钢") || scShelfResource.getVarietyName().equals("螺纹") ) {
            resourceType = 1;
        }else if ( scShelfResource.getVarietyName().equals("高线") ) {
            resourceType = 2;
        }else if ( scShelfResource.getVarietyName().equals("盘螺") ) {
            resourceType = 3;
        }
        for ( int i = 0; i < scTodayBasePriceInfoList.size(); i++ ) {
            if ( resourceType ==  scTodayBasePriceInfoList.get(i).getType() ) {
                basePrice = scTodayBasePriceInfoList.get(i).getReviewPrice();
                break;
            }
        }
        return basePrice;
    }

    /**
     * @Description: 获取平台极差设置
     * @Author: shizg
     * @Date: 2021/8/30 12:07
     * @param pmid:
     * @return: java.util.HashMap<java.lang.Byte, java.util.List < com.isechome.ecommerce.entity.ScSettingRange>>
     **/
    private HashMap<Byte, List<ScSettingRange>> GetSettingRange(int pmid) {
        Byte[] kindArray = CommonConstant.RANGE_KIND_ARRAY;
        Date today = Common.getNowDateShort();
        HashMap<Byte,List<ScSettingRange>> rangeMap = new HashMap<>();
        for ( int i = 0; i < kindArray.length; i++ ) {
            ScSettingRange scSettingRangeMaxDate = scSettingRangeMapper.getSettingRangeReviewDateByKind( pmid, kindArray[i], today );
            if ( scSettingRangeMaxDate != null ) {
                Date reviewDay = scSettingRangeMaxDate.getDate();
                List<ScSettingRange> scSettingRangeInfoList = scSettingRangeMapper.getSettingRangeInfoByKind( pmid, kindArray[i], reviewDay, scSettingRangeMaxDate.getReviewType() );
                rangeMap.put( kindArray[i], scSettingRangeInfoList );
            }else {
                rangeMap = null;
                break;
            }
        }
        return rangeMap;
    }

    /**
     * @Description: 获取今日基价
     * @Author: shizg
     * @Date: 2021/8/30 10:39
     * @param pmid:
     * @return: java.util.List<com.isechome.ecommerce.entity.ScTodayBasePriceInfo>
     **/
    private List<ScTodayBasePriceInfo> GetTodayBasePrice(int pmid) {
        Date today = Common.getNowDateShort();
        List<ScTodayBasePriceInfo> scTodayBasePriceInfoList = scTodayBasePriceInfoMapper.getTodayBasePriceReview(today, pmid);
        if ( scTodayBasePriceInfoList != null ) {
            //处理调价
            List<ScModifyPrice> scModifyPriceList = scModifyPriceMapper.getTodayModifyPriceforCal( pmid, (byte)1, today );
            if ( scModifyPriceList != null ) {
                for (int i = 0; i < scTodayBasePriceInfoList.size(); i++) {
                    for (int j = 0; j < scModifyPriceList.size(); j++) {
                        if ( scTodayBasePriceInfoList.get(i).getType() == scModifyPriceList.get(j).getType().intValue() ) {
                            scTodayBasePriceInfoList.get(i).setReviewPrice( scTodayBasePriceInfoList.get(i).getReviewPrice() + scModifyPriceList.get(j).getPrice() );
                            break;
                        }
                    }
                }
            }
        }
        return  scTodayBasePriceInfoList;
    }

}