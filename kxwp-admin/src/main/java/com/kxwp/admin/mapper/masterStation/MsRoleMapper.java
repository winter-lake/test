package com.kxwp.admin.mapper.masterStation;

import java.util.List;

import com.kxwp.admin.entity.masterStation.MsRole;
import com.kxwp.admin.entity.masterStation.MsRoleExample;
import com.kxwp.admin.query.masterStation.MasterStationRoleQuery;

public interface MsRoleMapper {
    int countByExample(MsRoleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MsRole record);

    int insertSelective(MsRole record);

    MsRole selectByPrimaryKey(Long id);
    
    List<MsRole> selectByCondition(MasterStationRoleQuery masterStationRoleQuery);

    int updateByPrimaryKeySelective(MsRole record);

    int updateByPrimaryKey(MsRole record);
}