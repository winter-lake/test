package com.kxwp.admin.mapper.masterStation;

import java.util.List;

import com.kxwp.admin.entity.masterStation.MsRoleResourceRelation;
import com.kxwp.admin.entity.masterStation.MsRoleResourceRelationExample;

public interface MsRoleResourceRelationMapper {
    int countByExample(MsRoleResourceRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MsRoleResourceRelation record);

    int insertSelective(MsRoleResourceRelation record);

    MsRoleResourceRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MsRoleResourceRelation record);

    int updateByPrimaryKey(MsRoleResourceRelation record);
    
    List<MsRoleResourceRelation> selectByroleid(Long roleId);

    void msRoleResourceRelationMapper(List<MsRoleResourceRelation> msRoleResourceRelations);

    void insertSelectiveBatch(List<MsRoleResourceRelation> msRoleResourceRelations);

    void deleteByCondition(MsRoleResourceRelation msRoleResourceRelation);
}