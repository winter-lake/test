package com.kxwp.admin.service.masterStation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxwp.admin.entity.masterStation.MsRoleResourceRelation;
import com.kxwp.admin.mapper.masterStation.MsRoleResourceRelationMapper;

/**
 * 角色资源关系对应Service
 * date: 2016年8月2日 下午3:11:44 
 * @author Liuzibing
 */
@Service("msRoReRelationService")
public class MsRoReRelationService {
      @Autowired
      private MsRoleResourceRelationMapper msRoleResourceRelationMapper;
      
  
      /**
       * listForRelation:(根据角色ID获取其权限).
       * 2016年8月2日 下午4:19:21
       * @author Liuzibing
       * @param roleId
       * @return
       */
      public List<MsRoleResourceRelation> listForRelation(Long roleId)
      {
        List<MsRoleResourceRelation> relationlist=new ArrayList<MsRoleResourceRelation>();
        relationlist=msRoleResourceRelationMapper.selectByroleid(roleId);
          
        return relationlist;
      }
      
      /**
       * 
       * insertSelectiveBatch:(批量插入角色资源关系).
       *
       * 2016年8月6日 上午11:44:30
       * @author wangjun
       * @param msRoleResourceRelations
       */
      public void insertSelectiveBatch(List<MsRoleResourceRelation> msRoleResourceRelations){
        msRoleResourceRelationMapper.insertSelectiveBatch(msRoleResourceRelations);
      }

      public void remove(MsRoleResourceRelation msRoleResourceRelation) {
        msRoleResourceRelationMapper.deleteByCondition(msRoleResourceRelation);
      }
}
