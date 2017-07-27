package com.kxwp.admin.query.masterStation;

import com.kxwp.admin.constants.BrandStatusEnum;
import com.kxwp.common.query.base.BaseQuery;

public class MsBrandQuery extends BaseQuery{
  
    private String brandName;
    
    private String brandNameAbbr;
    
    private Long id;
    
    private BrandStatusEnum brandStatus;
    
    private String brandNo;
    
    public String getBrandName() {
      return brandName;
    }

    public void setBrandName(String brandName) {
      this.brandName = brandName;
    }

    public String getBrandNameAbbr() {
      return brandNameAbbr;
    }

    public void setBrandNameAbbr(String brandNameAbbr) {
      this.brandNameAbbr = brandNameAbbr;
    }

    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }

    public BrandStatusEnum getBrandStatus() {
      return brandStatus;
    }

    public void setBrandStatus(BrandStatusEnum brandStatus) {
      this.brandStatus = brandStatus;
    }

    public String getBrandNo() {
      return brandNo;
    }

    public void setBrandNo(String brandNo) {
      this.brandNo = brandNo;
    }
    
}
