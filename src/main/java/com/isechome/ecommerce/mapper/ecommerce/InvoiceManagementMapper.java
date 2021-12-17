package com.isechome.ecommerce.mapper.ecommerce;

import com.isechome.ecommerce.entity.InvoiceManagement;
import java.util.List;
import java.util.Map;

public interface InvoiceManagementMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(InvoiceManagement record);

    InvoiceManagement selectByPrimaryKey(Integer id);

    List<InvoiceManagement> selectAll();

    int updateByPrimaryKey(InvoiceManagement record);

    List<InvoiceManagement> getInvoiceList(Map<String,Object> map);

    int updatedrawer(InvoiceManagement record);
}