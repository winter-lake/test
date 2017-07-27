package com.kxwp.admin.service.serviceStation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxwp.admin.entity.serviceStation.SsRoleResourceRelation;
import com.kxwp.admin.mapper.serviceStation.SsRoleResourceRelationMapper;

/**
 * 角色资源关系对应Service
 * date: 2016年8月2日 下午3:11:44 
 * @author Liuzibing
 */
@Service("ssRoReRelationService")
public class SsRoReRelationService {
      @Autowired
      private SsRoleResourceRelationMapper ssRoleResourceRelationMapper;
      
  
      /**
       * listForRelation:(根据角色ID获取其权限).
       * 2016年8月2日 下午4:19:21
       * @param roleId
       * @return
       */
      public List<SsRoleResourceRelation> listForRelation(Long roleId)
      {
        List<SsRoleResourceRelation> relationlist=new ArrayList<SsRoleResourceRelation>();
        relationlist=ssRoleResourceRelationMapper.selectByroleid(roleId);
        return relationlist;
      }
      
      /**
       * 
       * insertSelectiveBatch:(批量插入角色资源关系).
       *
       * 2016年8月6日 上午11:44:30
       * @author wangjun
       */
      public void insertSelectiveBatch(List<SsRoleResourceRelation> ssRoleResourceRelations){
        ssRoleResourceRelationMapper.insertSelectiveBatch(ssRoleResourceRelations);
      }

      public void remove(SsRoleResourceRelation ssRoleResourceRelation) {
        ssRoleResourceRelationMapper.deleteByCondition(ssRoleResourceRelation);
      }
}
