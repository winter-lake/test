package com.kxwp.admin.mapper.supplier;

import java.util.List;

import com.kxwp.admin.entity.supplier.SuResource;
import com.kxwp.admin.entity.supplier.SuResourceExample;
import com.kxwp.admin.query.supplier.SupplierQuery;

public interface SuResourceMapper {
    int countByExample(SuResourceExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SuResource record);

    int insertSelective(SuResource record);
    
    int insertSelectiveBatch(List<SuResource> records);

    SuResource selectByPrimaryKey(Long id);
    
    List<SuResource> selectByCondition(SupplierQuery supplierQuery);

    int updateByPrimaryKeySelective(SuResource record);
    
    int updateByPrimaryKeySelectiveBatch(List<SuResource> records);

    int updateByPrimaryKey(SuResource record);
}