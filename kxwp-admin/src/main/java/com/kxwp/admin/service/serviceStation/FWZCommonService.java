package com.kxwp.admin.service.serviceStation;

import java.util.List;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxwp.admin.entity.serviceStation.ServiceStation;
import com.kxwp.admin.mapper.serviceStation.ServiceStationMapper;
import com.kxwp.common.constants.CachekeyPrefix;
import com.kxwp.common.model.exception.KXWPNotFoundException;
import com.kxwp.common.service.core.AbstractCacheService;

/**
 * Date:     2016年8月9日 下午2:53:56 
 * @author   lou jian wen 
 * 服务站常用接口
 */
@Service("FWZCommonService")
public class FWZCommonService {

  
  @Autowired
  private ServiceStationMapper serviceStationMapper;
  
  @Autowired
  private AbstractCacheService cacheService;
  
  
  /**
   * 
   * getServiceStationByID:(获取服务站信息).
   *
   * 2016年8月12日 下午7:33:30
   * @author lou jian wen
   * @param id
   * @return
   * @throws KXWPNotFoundException
   */
  public ServiceStation getServiceStationByID(Long id) throws KXWPNotFoundException{
    ServiceStation fwzInfo = cacheService.getValue(CachekeyPrefix.ADMIN_FWZ_INFO + id);
    if(fwzInfo != null){
      return fwzInfo;
    }
    fwzInfo =  serviceStationMapper.selectByPrimaryKey(id);
    if(fwzInfo ==null){
      throw new KXWPNotFoundException("服务站不存在[" + id + "]");
    }
    
    cacheService.putKey(CachekeyPrefix.ADMIN_FWZ_INFO + id, fwzInfo, AbstractCacheService.HALFHOUR);
    return fwzInfo;
  }
  
  
  /**
   * searchLikeServiceStationName:(根据服务站名称模糊查询).
   *
   * 2016年8月9日 下午4:23:44
   * 
   * @author zhaojn
   * @param serviceStationName
   * @return
   * @throws LoginException
   * 
   */
  public List<ServiceStation> searchLikeServiceStationName(String serviceStationName)
      throws LoginException {
    List<ServiceStation> serviceStation_list =
        serviceStationMapper.searchLikeserviceStationName(serviceStationName);
    return serviceStation_list;
  }

}

