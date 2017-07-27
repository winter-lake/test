package com.kxwp.admin.mapper.masterStation;

import java.util.List;

import com.kxwp.admin.entity.masterStation.MsBrand;
import com.kxwp.admin.entity.masterStation.MsBrandExample;
import com.kxwp.admin.query.masterStation.MsBrandQuery;

public interface MsBrandMapper {
    int countByExample(MsBrandExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MsBrand record);

    int insertSelective(MsBrand record);

    MsBrand selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MsBrand record);

    int updateByPrimaryKeyWithBLOBs(MsBrand record);

    int updateByPrimaryKey(MsBrand record);
    
    int countByQuery(MsBrandQuery msBrandQuery);
    
    int selectDuplicate(String msBrand);
    
    List<MsBrand> selectByCondition(MsBrandQuery msBrandQuery);
}