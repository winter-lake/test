package com.kxwp.admin.service.masterStation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxwp.admin.entity.masterStation.MasterStation;
import com.kxwp.admin.entity.masterStation.MasterStationAccount;
import com.kxwp.admin.mapper.masterStation.MasterStationAccountMapper;
import com.kxwp.admin.mapper.masterStation.MasterStationMapper;
import com.kxwp.admin.model.ms.UserModel;

/**
 * Date:     2016年8月8日 下午2:05:36 
 * @author   lou jian wen 
 */
@Service("MasterStationService")
public class MasterStationService {

  
  @Autowired
  private MasterStationAccountMapper masterStationAccountMapper;
  
  @Autowired
  private MasterStationMapper masterStationMapper;
  
  /**
   * 
   * getUserMasterStation:(获取当前登录用户对应的masterStation(总站)).
   *
   * 2016年8月8日 下午2:45:53
   * @author lou jian wen
   * @param user
   * @return
   */
  public MasterStation getUserMasterStation(UserModel user){
    MasterStationAccount account =  masterStationAccountMapper.selectByPrimaryKey(user.getId());
    MasterStation masterStation =  masterStationMapper.selectByPrimaryKey(account.getMasterStationId());
    return masterStation;
  }
}

