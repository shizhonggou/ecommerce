/*
 * @Author: xiangbin
 * @Date: 2021-05-13 11:36:19
 * @LastEditTime: 2021-05-13 11:43:28
 * @FilePath: \ess\src\main\java\cn\steelhome\ess\service\SysmobilecheckcodeService.java
 * @Description: 
 * @Copyright: © 2021, SteelHome. All rights reserved.
 */
package com.isechome.ecommerce.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.isechome.ecommerce.entity.Sysmobilecheckcode;
import com.isechome.ecommerce.mapper.isechome.SysmobilecheckcodeMapper;

@Service
@Transactional(transactionManager = "isechomeMybatisPlatformTransactionManager")
public class SysmobilecheckcodeService {
    @Autowired
    SysmobilecheckcodeMapper sysmobilecheckcodeMapper;
    public Sysmobilecheckcode selectSysmobilecheckcodeByMobileNumber(String MobileNumber){
        return sysmobilecheckcodeMapper.selectSysmobilecheckcodeByMobileNumber(MobileNumber);
    }

   
    

    
    

     
    

    
    

}
