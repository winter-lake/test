package com.kxwp.admin.mapper.supermarket;

import com.kxwp.admin.entity.supermarket.SupermarketAccount;
import com.kxwp.admin.entity.supermarket.SupermarketAccountExample;

public interface SupermarketAccountMapper {
    void updateByCondition(SupermarketAccount supermarketAccount);
  
    SupermarketAccount selectBySupermarketId(Long supermarketId);
    
    int countByExample(SupermarketAccountExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SupermarketAccount record);

    int insertSelective(SupermarketAccount record);
    
    SupermarketAccount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SupermarketAccount record);

    int updateByPrimaryKey(SupermarketAccount record);
}