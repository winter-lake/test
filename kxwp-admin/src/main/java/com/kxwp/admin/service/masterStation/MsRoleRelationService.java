package com.kxwp.admin.service.masterStation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxwp.admin.entity.masterStation.MasterStationAccount;
import com.kxwp.admin.entity.masterStation.MsRoleRelation;
import com.kxwp.admin.mapper.masterStation.MsRoleRelationMapper;

/**
 * 账号角色关系service
 * date: 2016年8月3日 下午4:05:35 
 *
 * @author wangjun
 */
@Service
public class MsRoleRelationService {
  @Autowired
  private MsRoleRelationMapper msRoleRelationMapper;
  
  /**
   * 
   * addBatch:(批量添加账号角色关系).
   *
   * 2016年8月3日 下午4:07:50
   * @author wangjun
   * @param msRoleRelations
   * @return
   */
  public int addBatch(List<MsRoleRelation> msRoleRelations){
    return msRoleRelationMapper.insertSelectiveBatch(msRoleRelations);
  }
  
  public List<MsRoleRelation> list(MsRoleRelation msRoleRelation){
    return msRoleRelationMapper.selectByCondition(msRoleRelation);
  }

  public void remove(MasterStationAccount masterStationAccount) {
    msRoleRelationMapper.deleteByCondition(masterStationAccount.getId());
    
  }
}
