package com.kxwp.admin.mapper.serviceStation;

import java.util.List;

import com.kxwp.admin.entity.masterStation.MsResource;
import com.kxwp.admin.entity.serviceStation.SsResource;
import com.kxwp.admin.entity.serviceStation.SsResourceExample;
import com.kxwp.admin.query.serviceStation.ServiceStationQuery;

public interface SsResourceMapper {
    int countByExample(SsResourceExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MsResource record);

    int insertSelective(MsResource record);
    
    int insertSelectiveBatch(List<SsResource> records);

    SsResource selectByPrimaryKey(Long id);
    
    List<SsResource> selectByCondition(ServiceStationQuery serviceStationQuery);

    int updateByPrimaryKeySelective(MsResource record);
    
    int updateByPrimaryKeySelectiveBatch(List<MsResource> records);

    int updateByPrimaryKey(MsResource record);
}