/*
 * @Author: xiangbin
 * @Date: 2021-05-13 10:51:27
 * @LastEditTime: 2021-05-13 11:06:16
 * @FilePath: \ess\src\main\java\cn\steelhome\ess\mapper\mapp\SmsCheckCodeMapper.java
 * @Description: 
 * @Copyright: © 2021, SteelHome. All rights reserved.
 */
package com.isechome.ecommerce.mapper.isechome;

import com.isechome.ecommerce.entity.SmsCheckCode;
import java.util.List;

public interface SmsCheckCodeMapper {
    SmsCheckCode selectSmsCheckCodeByMobileNumber(String MobileNumber);
}