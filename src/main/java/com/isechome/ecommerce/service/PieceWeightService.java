package com.isechome.ecommerce.service;

import com.isechome.ecommerce.entity.PieceWeight;
import com.isechome.ecommerce.mapper.ecommerce.PieceWeightMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @Description: 件重管理service
 * @Author: shizg
 * @Date: 2021/5/27 15:42
 * @return: null
 **/
@Service
@Transactional( transactionManager = "isechomeMybatisPlatformTransactionManager")
public class PieceWeightService {
    @Autowired
    PieceWeightMapper pieceWeightMapper;

    /**
     * @Description: 新增件重
     * @Author: shizg
     * @Date: 2021/5/27 15:43
     * @param pieceWeight:
     * @return: void
     **/
    public void insertPieceWeight(PieceWeight pieceWeight) {
        pieceWeightMapper.insert(pieceWeight);
    }

    /**
     * @Description:修改件重
     * @Author: shizg
     * @Date: 2021/5/27 15:44
     * @param pieceWeight:
     * @return: void
     **/
    public void updatePieceWeightById(PieceWeight pieceWeight) {
        pieceWeightMapper.updateByPrimaryKey(pieceWeight);
    }

    public void deletePieceWeight( HttpServletRequest request ) {
        String requestId = request.getParameter("id");
        Integer id = 0;
        if ( requestId != null ) {
            id = Integer.parseInt( requestId );
        }
        pieceWeightMapper.deleteByPrimaryKey(id);
    }

    /**件重列表
     * @Description:
     * @Author: shizg
     * @Date: 2021/5/27 15:44
     * @return:
     **/
    public void getPieceWeightInfo( HttpServletRequest request, Model model){
        model.addAttribute("title", "件重管理列表");
        model.addAttribute("mark", "pieceweight");
        model.addAttribute("pieceWeightInfoList", pieceWeightMapper.selectAll() );
    }

    /**
     * @Description:根据id获取件重信息
     * @Author: shizg
     * @Date: 2021/5/29 11:09
     * @param request:
     * @param model:
     * @return: void
     **/
    public void getPieceWeightInfoByid( HttpServletRequest request, Model model ){
        String requestId = request.getParameter("id");
        Integer id = 0;
        if ( requestId != null ) {
            id = Integer.parseInt( requestId );
        }
        if ( id > 0 ) {
            model.addAttribute("pieceweight",pieceWeightMapper.selectByPrimaryKey(id) );
            model.addAttribute("title", "修改件重");
        }else {
            model.addAttribute("pieceweight",new PieceWeight() );
            model.addAttribute("title", "新增件重");
        }
        model.addAttribute("mark", "pieceweight");
    }

    /**
     * @Description:新增件重或者修改件重
     * @Author: shizg
     * @Date: 2021/5/27 15:47
     * @param request:
     * @return: void
     **/
    public String savePieceWeight(HttpServletRequest request, Model model) {
        String requestId = request.getParameter("id");
        Integer id = 0;
        if ( requestId != null && requestId != "" ){
            id = Integer.parseInt( requestId );
        }
        String spec = request.getParameter("spec");
        Float weight = Float.parseFloat(request.getParameter("weight"));
        PieceWeight pieceWeight = new PieceWeight();
        pieceWeight.setId(id);
        pieceWeight.setSpec(spec);
        pieceWeight.setWeight(weight);
        pieceWeight.setCreateUserId(2);
        byte status = 0;
        pieceWeight.setStatus( status );
        Date dateNow = new Date();
        pieceWeight.setCreatTime( dateNow );
        String saveMessage = "";
        if ( id > 0 ){
            updatePieceWeightById(pieceWeight);
            saveMessage = "修改成功";
        }else {
            insertPieceWeight(pieceWeight);
            saveMessage ="新增成功";
        }
        return saveMessage;
    }

    /**
     * @Description: 执行审核
     * @Author: shizg
     * @Date: 2021/6/1 10:16

     * @return: void
     **/
    public void review() {
        pieceWeightMapper.review();
    }
}