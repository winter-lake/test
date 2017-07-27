package com.kxwp.admin.mapper.serviceStation;

import java.util.List;

import com.kxwp.admin.entity.serviceStation.ServiceStation;
import com.kxwp.admin.entity.serviceStation.ServiceStationExample;

public interface ServiceStationMapper {
  
  ServiceStation selectByFeServiceStationId(String feServiceStationId);
  
  int countByExample(ServiceStationExample example);

  List<ServiceStation> searchLikeserviceStationName(String stationName);

  int deleteByPrimaryKey(Long id);

  int insert(ServiceStation record);

  int insertSelective(ServiceStation record);

  ServiceStation selectByPrimaryKey(Long id);

  int updateByPrimaryKeySelective(ServiceStation record);

  int updateByPrimaryKey(ServiceStation record);
}
