package com.kxwp.admin.mapper.supplier;

import java.util.List;

import com.kxwp.admin.entity.supplier.SuRoleRelation;
import com.kxwp.admin.entity.supplier.SuRoleRelationExample;

public interface SuRoleRelationMapper {
    int countByExample(SuRoleRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SuRoleRelation record);

    int insertSelective(SuRoleRelation record);
    
    int insertSelectiveBatch(List<SuRoleRelation> records);

    SuRoleRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SuRoleRelation record);

    int updateByPrimaryKey(SuRoleRelation record);

    List<SuRoleRelation> selectByCondition(SuRoleRelation suRoleRelation);

    void deleteByCondition(Long id);
}