package com.kxwp.admin.mapper.masterStation;

import java.util.List;

import com.kxwp.admin.entity.masterStation.MsRoleRelation;
import com.kxwp.admin.entity.masterStation.MsRoleRelationExample;

public interface MsRoleRelationMapper {
    int countByExample(MsRoleRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MsRoleRelation record);

    int insertSelective(MsRoleRelation record);
    
    int insertSelectiveBatch(List<MsRoleRelation> records);

    MsRoleRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MsRoleRelation record);

    int updateByPrimaryKey(MsRoleRelation record);

    List<MsRoleRelation> selectByCondition(MsRoleRelation msRoleRelation);

    void deleteByCondition(Long id);
}