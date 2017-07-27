package com.kxwp.admin.mapper.supplier;

import java.util.List;

import com.kxwp.admin.entity.supplier.SuRoleResourceRelation;
import com.kxwp.admin.entity.supplier.SuRoleResourceRelationExample;

public interface SuRoleResourceRelationMapper {
    void deleteByCondition(SuRoleResourceRelation suRoleResourceRelation);
    
    void insertSelectiveBatch(List<SuRoleResourceRelation> suRoleResourceRelation);
  
    List<SuRoleResourceRelation> selectByroleid(Long roleId);
  
    int countByExample(SuRoleResourceRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SuRoleResourceRelation record);

    int insertSelective(SuRoleResourceRelation record);

    SuRoleResourceRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SuRoleResourceRelation record);

    int updateByPrimaryKey(SuRoleResourceRelation record);
}