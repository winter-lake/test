package com.kxwp.admin.mapper.serviceStation;

import java.util.List;

import com.kxwp.admin.entity.serviceStation.SsRole;
import com.kxwp.admin.entity.serviceStation.SsRoleExample;
import com.kxwp.admin.query.serviceStation.ServiceStationRoleQuery;

public interface SsRoleMapper {
    int countByExample(SsRoleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SsRole record);

    int insertSelective(SsRole record);

    SsRole selectByPrimaryKey(Long id);

    List<SsRole> selectByCondition(ServiceStationRoleQuery serviceStationRoleQuery);
    
    int updateByPrimaryKeySelective(SsRole record);

    int updateByPrimaryKey(SsRole record);
}