package com.isechome.ecommerce.controller;

import com.isechome.ecommerce.common.AjaxResult;
import com.isechome.ecommerce.service.ScOrderListService;
import com.isechome.ecommerce.service.ShelfResourceService;
import com.isechome.ecommerce.service.WarehouseResourceService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("shelf_resource")
@Log4j2
public class ShelfResourceController {

    @Autowired
    private ShelfResourceService shelfResourceService;

    @Autowired
    private ScOrderListService scOrderListService;

    @Autowired
    private WarehouseResourceService warehouseResourceService;

    /**
     * @return com.isechome.ecommerce.common.AjaxResult
     * @Description 修改上架资源价格
     * @Author zhaofy
     * @Date 2021/11/14
     * @Param [reservedNum, reservedDate, id]
     **/
    @PostMapping("update_price")
    public AjaxResult update_price(@RequestParam(value = "price", defaultValue = "0.0") Double price,
                                   @RequestParam(value = "id") int id) {
        return shelfResourceService.updateResourcePrice(price, id);
    }

    /**
     * @return com.isechome.ecommerce.common.AjaxResult
     * @Description 下架资源
     * @Author zhaofy
     * @Date 2021/12/1
     * @Param [id]
     **/
    @PostMapping("off_resource")
    public AjaxResult off_resource(@RequestParam(value = "id") int id) {
        //是否存在未付款 or 未确认的订单
        if (scOrderListService.haveOrder(id)) {
            return AjaxResult.error();
        } else {
            //下架资源
            warehouseResourceService.offResourceById(id);
            return AjaxResult.success();
        }
    }

    /**
     * @return com.isechome.ecommerce.common.AjaxResult
     * @Description 下架存在订单的资源
     * @Author zhaofy
     * @Date 2021/12/1
     * @Param [id]
     **/
    @PostMapping("off_have_order_resource")
    public AjaxResult off_have_order_resource(@RequestParam(value = "id") int id) {
        //根据资源id取消订单
        if (scOrderListService.dealHaveOrder(id)) {
            //下架资源
            warehouseResourceService.offResourceById(id);
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }
}
