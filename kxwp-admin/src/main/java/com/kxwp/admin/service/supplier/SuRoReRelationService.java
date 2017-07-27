package com.kxwp.admin.service.supplier;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxwp.admin.entity.supplier.SuRoleResourceRelation;
import com.kxwp.admin.mapper.supplier.SuRoleResourceRelationMapper;

/**
 * 角色资源关系对应Service
 * date: 2016年8月2日 下午3:11:44 
 * @author Liuzibing
 */
@Service("suRoReRelationService")
public class SuRoReRelationService {
      @Autowired
      private SuRoleResourceRelationMapper suRoleResourceRelationMapper;
      
  
      /**
       * listForRelation:(根据角色ID获取其权限).
       * 2016年8月2日 下午4:19:21
       * @param roleId
       * @return
       */
      public List<SuRoleResourceRelation> listForRelation(Long roleId)
      {
        List<SuRoleResourceRelation> relationlist=new ArrayList<SuRoleResourceRelation>();
        relationlist=suRoleResourceRelationMapper.selectByroleid(roleId);
        return relationlist;
      }
      
      /**
       * 
       * insertSelectiveBatch:(批量插入角色资源关系).
       *
       * 2016年8月6日 上午11:44:30
       * @author wangjun
       */
      public void insertSelectiveBatch(List<SuRoleResourceRelation> suRoleResourceRelations){
        suRoleResourceRelationMapper.insertSelectiveBatch(suRoleResourceRelations);
      }

      public void remove(SuRoleResourceRelation suRoleResourceRelation) {
        suRoleResourceRelationMapper.deleteByCondition(suRoleResourceRelation);
      }
}
