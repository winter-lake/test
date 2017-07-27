package com.kxwp.admin.service.serviceStation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxwp.admin.entity.serviceStation.ServiceStationAccount;
import com.kxwp.admin.entity.serviceStation.SsRoleRelation;
import com.kxwp.admin.mapper.serviceStation.SsRoleRelationMapper;

/**
 * 账号角色关系service
 * date: 2016年8月3日 下午4:05:35 
 *
 * @author wangjun
 */
@Service
public class SsRoleRelationService {
  @Autowired
  private SsRoleRelationMapper ssRoleRelationMapper;
  
  /**
   * 
   * addBatch:(批量添加账号角色关系).
   *
   * 2016年8月3日 下午4:07:50
   * @author wangjun
   * @param msRoleRelations
   * @return
   */
  public int addBatch(List<SsRoleRelation> ssRoleRelations){
    return ssRoleRelationMapper.insertSelectiveBatch(ssRoleRelations);
  }

  public List<SsRoleRelation> list(SsRoleRelation ssRoleRelation) {
    return ssRoleRelationMapper.selectByCondition(ssRoleRelation);
  }

  public void remove(ServiceStationAccount serviceStationAccount) {
    ssRoleRelationMapper.deleteByCondition(serviceStationAccount.getId());
    
  }
}
