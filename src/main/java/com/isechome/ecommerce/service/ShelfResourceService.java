package com.isechome.ecommerce.service;

import cn.hutool.core.util.ObjectUtil;
import com.isechome.ecommerce.common.AjaxResult;
import com.isechome.ecommerce.common.SecurityUserUtil;
import com.isechome.ecommerce.common.StringUtils;
import com.isechome.ecommerce.constant.CommonConstant;
import com.isechome.ecommerce.entity.*;
import com.isechome.ecommerce.mapper.ecommerce.ScSteelMillMapper;
import com.isechome.ecommerce.mapper.ecommerce.ScWorkTimeMapper;
import com.isechome.ecommerce.mapper.ecommerce.ScplatformsidesettingMapper;
import com.isechome.ecommerce.mapper.ecommerce.ShelfResourceMapper;
import com.isechome.ecommerce.util.ArithmeticUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Log4j2
@Service
@Transactional(transactionManager = "ecommerceMybatisPlatformTransactionManager")
public class ShelfResourceService {
    @Autowired
    ShelfResourceMapper shelfResourceMapper;

    @Autowired
    ScSteelMillMapper scSteelMillMapper;

    @Autowired
    ScWorkTimeMapper scWorkTimeMapper;

    @Autowired
    ScplatformsidesettingMapper scplatformsidesettingMapper;

    @Autowired
    HttpServletRequest request;

    public List<ShelfResource> selectListByResource(ShelfResource shelfResource, String searchWhere, String searchVid) {
        List<ShelfResource> shelfResourceList;
        shelfResource.setPmid(SecurityUserUtil.getPmid());
        if (StringUtils.isNotEmpty(searchVid) && !searchVid.equals("请选择")) {
            shelfResource.setVId((int) CommonConstant.VARIETY_NAME_TYPE.get(searchVid));
        }
        if (StringUtils.isNotEmpty(searchWhere)) {
            if (searchVid.equals("请选择")) shelfResource.setVId(null);
            shelfResource.setVarietyName(searchWhere);
            shelfResourceList = shelfResourceMapper.selectListByMixResource(shelfResource);
        } else {
            shelfResourceList = shelfResourceMapper.selectListByResource(shelfResource);
        }
        return dealResourceWeight(shelfResourceList);
    }

    /**
     * @return java.util.List<com.isechome.ecommerce.entity.ShelfResource>
     * @Description 处理螺纹上架资源件重
     * @Author zhaofy
     * @Date 2021/9/10
     * @Param [shelfResourceList]
     **/
    public List<ShelfResource> dealResourceWeight(List<ShelfResource> shelfResourceList) {
        if (null == SecurityUserUtil.getPmid()) return shelfResourceList;
        //处理件重
        List<ScSteelMill> steelMillList = scSteelMillMapper.getSteelMillAndWeight(SecurityUserUtil.getPmid());
        for (ShelfResource shelfResourceReturn : shelfResourceList) {
            //只有螺纹有件重
            if (shelfResourceReturn.getVId() != (int) CommonConstant.LUOWEN_VID) continue;
            //处理件重
            for (ScSteelMill steelMill : steelMillList) {
                if (shelfResourceReturn.getFactory().equals(steelMill.getSteelNameShort())) {
                    for (PieceWeight pieceWeight : steelMill.getPieceWeightList()) {
                        if (shelfResourceReturn.getSpecification().equals(pieceWeight.getSpec())) {
                            shelfResourceReturn.setWeight(ArithmeticUtils.round(pieceWeight.getWeight(), 3));
                        }
                    }
                }
            }
        }
        return shelfResourceList;
    }

    /**
     * @return com.isechome.ecommerce.common.AjaxResult
     * @Description 修改上架资源价格
     * @Author zhaofy
     * @Date 2021/11/4
     * @Param [price, id]
     **/
    public AjaxResult updateResourcePrice(double price, int id) {
        ShelfResource shelfResource = new ShelfResource();
        shelfResource.setId(id);
        shelfResource.setPrice(price);
        shelfResourceMapper.updateByPrimaryKey(shelfResource);
        return AjaxResult.success();
    }

    /**
     * @return com.isechome.ecommerce.entity.ScWorkTime
     * @Description 获取时间设置
     * @Author zhaofy
     * @Date 2021/11/25
     * @Param []
     **/
    public ScWorkTime selectWorkTime() {
        int pmid = ObjectUtil.isNotNull(SecurityUserUtil.getPmid()) ? SecurityUserUtil.getPmid() : getPmId();
        return scWorkTimeMapper.selectByPmid(pmid);
    }

    public int getPmId() {
        HttpSession session = request.getSession();
        String token = (String) session.getAttribute("token");
        Integer pmid = 0;
        Scplatformsidesetting pmids = scplatformsidesettingMapper.selectnewpmid();
        Integer pmid1 = pmids.getPmid();

        if (token == null) {
            pmid = pmid1;
        } else {
            Scplatformsidesetting tk = scplatformsidesettingMapper.selectbytoken(token);
            if (tk == null) {
                pmid = pmid1;
            } else {
                pmid = tk.getPmid();
            }
        }
        return pmid;
    }
}
