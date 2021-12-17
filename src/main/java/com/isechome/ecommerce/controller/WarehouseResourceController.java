package com.isechome.ecommerce.controller;

import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.ExcelReader;
import com.isechome.ecommerce.common.AjaxResult;
import com.isechome.ecommerce.constant.CommonConstant;
import com.isechome.ecommerce.entity.WarehouseResource;
import com.isechome.ecommerce.service.WarehouseResourceService;
import com.isechome.ecommerce.util.ArithmeticUtils;
import com.isechome.ecommerce.util.ExcelUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("warehouse_resource")
@Log4j2
public class WarehouseResourceController {

    @Autowired
    private WarehouseResourceService warehouseResourceService;

    @PostMapping("import")
    public int warehouseResourceImport(@RequestParam(value = "file") MultipartFile multipartFile) throws IOException {
        ExcelReader excelReader = null;
        InputStream in = null;
        int ret = 0;
        try {
            in = multipartFile.getInputStream();
            ExcelUtil<WarehouseResource> util = new ExcelUtil<WarehouseResource>(WarehouseResource.class);
            List<WarehouseResource> warehouseResourceList = util.importEasyExcel(in);

            //插入 更新仓库资源
            ret = warehouseResourceService.importWarehouseResource(warehouseResourceList);
        } catch (Exception e) {
            log.error("import excel to db fail", e);
        } finally {
            in.close();
            // 这里一定别忘记关闭，读的时候会创建临时文件，到时磁盘会崩
            if (excelReader != null) {
                excelReader.finish();
            }
        }
        return ret;
    }

    /**
     * @return com.isechome.ecommerce.common.AjaxResult
     * @Description 修改预留资源
     * @Author zhaofy
     * @Date 2021/8/21
     * @Param [reservedNum, reservedDate, id]
     **/
    @PostMapping("update_resource")
    public AjaxResult warehouseResourceUpdate(@RequestParam(value = "reservedNum",defaultValue ="0.0" ) Double reservedNum,
                                              @RequestParam(value = "reservedDate") String reservedDate,
                                              @RequestParam(value = "id") int id,
                                              @RequestParam(value = "type") int type) {
        AjaxResult ajaxResult = AjaxResult.success();
        WarehouseResource warehouseResource = warehouseResourceService.selectByPrimaryKey(id);
        switch (type) {
            case CommonConstant.TYPE_UPDATE_NUM:
//                if (reservedNum != 0.0) {
//                    double Num = warehouseResource.getNum() - reservedNum + warehouseResource.getReservedNum();
                    double Num = ArithmeticUtils.add(ArithmeticUtils.sub(warehouseResource.getNum(), reservedNum),
                            warehouseResource.getReservedNum());
                    if (Num >= 0.0) {
                        warehouseResource.setNum(Num);
                        warehouseResource.setReservedNum(reservedNum);
                        warehouseResourceService.updateWarehouseResource(warehouseResource);
                        ajaxResult = AjaxResult.success(Num);
                    } else {
                        ajaxResult = AjaxResult.error("预留量不能大于可用数量！");
                    }
//                }

                break;
            case CommonConstant.TYPE_UPDATE_DATE:
                warehouseResource.setNum(null);
                warehouseResource.setReservedNum(null);
                warehouseResource.setReservedDate(DateUtil.parse(reservedDate));
                warehouseResourceService.updateWarehouseResource(warehouseResource);
                ajaxResult = AjaxResult.success();
                break;
        }
        return ajaxResult;
    }

    /**
     * @return com.isechome.ecommerce.common.AjaxResult
     * @Description 审核仓库资源
     * @Author zhaofy
     * @Date 2021/8/21
     * @Param []
     **/
    @PostMapping("audit_resource")
    public AjaxResult warehouseResourceAudit() {
        warehouseResourceService.auditWarehouseResource();
        return AjaxResult.success();
    }
}
