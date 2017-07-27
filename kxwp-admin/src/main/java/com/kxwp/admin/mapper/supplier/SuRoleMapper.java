package com.kxwp.admin.mapper.supplier;

import java.util.List;

import com.kxwp.admin.entity.supplier.SuRole;
import com.kxwp.admin.entity.supplier.SuRoleExample;
import com.kxwp.admin.query.supplier.SupplierRoleQuery;

public interface SuRoleMapper {
    int countByExample(SuRoleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SuRole record);

    int insertSelective(SuRole record);

    SuRole selectByPrimaryKey(Long id);
    
    List<SuRole> selectByCondition(SupplierRoleQuery supplierRoleQuery);

    int updateByPrimaryKeySelective(SuRole record);

    int updateByPrimaryKey(SuRole record);
}