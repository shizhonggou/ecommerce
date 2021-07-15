/*
 * @Author: xiangbin
 * @Date: 2021-05-13 11:36:19
 * @LastEditTime: 2021-05-13 11:43:28
 * @FilePath: \ess\src\main\java\cn\steelhome\ess\service\SmsCheckCodeService.java
 * @Description: 
 * @Copyright: © 2021, SteelHome. All rights reserved.
 */
package com.isechome.ecommerce.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.isechome.ecommerce.entity.SmsCheckCode;
import com.isechome.ecommerce.mapper.isechome.SmsCheckCodeMapper;

@Service
@Transactional(transactionManager = "isechomeMybatisPlatformTransactionManager")
public class SmsCheckCodeService {
    @Autowired
    SmsCheckCodeMapper smsCheckCodeMapper;
    public SmsCheckCode selectSmsCheckCodeByMobileNumber(String MobileNumber){
        return smsCheckCodeMapper.selectSmsCheckCodeByMobileNumber(MobileNumber);
    }

   
    

    
    

     
    

    
    

}
