package com.kxwp.admin.mapper.masterStation;

import java.util.List;

import com.kxwp.admin.entity.masterStation.MsResource;
import com.kxwp.admin.entity.masterStation.MsResourceExample;
import com.kxwp.admin.query.masterStation.MasterStationQuery;

public interface MsResourceMapper {
    int countByExample(MsResourceExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MsResource record);

    int insertSelective(MsResource record);
    
    int insertSelectiveBatch(List<MsResource> records);

    MsResource selectByPrimaryKey(Long id);
    
    List<MsResource> selectByCondition(MasterStationQuery masterStationQuery);

    int updateByPrimaryKeySelective(MsResource record);
    
    int updateByPrimaryKeySelectiveBatch(List<MsResource> records);

    int updateByPrimaryKey(MsResource record);
}