package com.kxwp.admin.mapper.serviceStation;

import java.util.List;

import com.kxwp.admin.entity.serviceStation.SsRoleResourceRelation;
import com.kxwp.admin.entity.serviceStation.SsRoleResourceRelationExample;

public interface SsRoleResourceRelationMapper {
    void insertSelectiveBatch(List<SsRoleResourceRelation> ssRoleResourceRelation);
    
    void deleteByCondition(SsRoleResourceRelation ssRoleResourceRelation);
  
    List<SsRoleResourceRelation> selectByroleid(Long roleId);
  
    int countByExample(SsRoleResourceRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SsRoleResourceRelation record);

    int insertSelective(SsRoleResourceRelation record);

    SsRoleResourceRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SsRoleResourceRelation record);

    int updateByPrimaryKey(SsRoleResourceRelation record);
}