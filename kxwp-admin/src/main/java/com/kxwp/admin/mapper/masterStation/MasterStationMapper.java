package com.kxwp.admin.mapper.masterStation;

import com.kxwp.admin.entity.masterStation.MasterStation;
import com.kxwp.admin.entity.masterStation.MasterStationExample;

public interface MasterStationMapper {
    int countByExample(MasterStationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MasterStation record);

    int insertSelective(MasterStation record);

    MasterStation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MasterStation record);

    int updateByPrimaryKey(MasterStation record);
}