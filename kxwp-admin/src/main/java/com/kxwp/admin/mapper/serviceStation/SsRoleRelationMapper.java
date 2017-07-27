package com.kxwp.admin.mapper.serviceStation;

import java.util.List;

import com.kxwp.admin.entity.serviceStation.SsRoleRelation;
import com.kxwp.admin.entity.serviceStation.SsRoleRelationExample;

public interface SsRoleRelationMapper {
    int countByExample(SsRoleRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SsRoleRelation record);

    int insertSelective(SsRoleRelation record);
    
    int insertSelectiveBatch(List<SsRoleRelation> records);

    SsRoleRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SsRoleRelation record);

    int updateByPrimaryKey(SsRoleRelation record);

    List<SsRoleRelation> selectByCondition(SsRoleRelation ssRoleRelation);

    void deleteByCondition(Long id);
}