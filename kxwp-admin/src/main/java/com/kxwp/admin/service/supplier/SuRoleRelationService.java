package com.kxwp.admin.service.supplier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxwp.admin.entity.supplier.SuRoleRelation;
import com.kxwp.admin.entity.supplier.SupplierAccount;
import com.kxwp.admin.mapper.supplier.SuRoleRelationMapper;

/**
 * 账号角色关系service
 * date: 2016年8月3日 下午4:05:35 
 *
 * @author wangjun
 */
@Service
public class SuRoleRelationService {
  @Autowired
  private SuRoleRelationMapper suRoleRelationMapper;
  
  /**
   * 
   * addBatch:(批量添加账号角色关系).
   *
   * 2016年8月3日 下午4:07:50
   * @author wangjun
   * @param msRoleRelations
   * @return
   */
  public int addBatch(List<SuRoleRelation> suRoleRelations){
    return suRoleRelationMapper.insertSelectiveBatch(suRoleRelations);
  }

  public List<SuRoleRelation> list(SuRoleRelation suRoleRelation) {
    return suRoleRelationMapper.selectByCondition(suRoleRelation);
  }

  public void remove(SupplierAccount supplierAccount) {
    suRoleRelationMapper.deleteByCondition(supplierAccount.getId());
    
  }
}
