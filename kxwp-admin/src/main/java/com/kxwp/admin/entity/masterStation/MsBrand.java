package com.kxwp.admin.entity.masterStation;

import java.util.Date;

import com.kxwp.admin.constants.BrandStatusEnum;

public class MsBrand {
    private Long id;

    private String brandName;

    private String brandNameAbbr;

    private BrandStatusEnum brandStatus;

    private Long feBid;

    private Date createTime;

    private Date updateTime;

    private String brandNo;
    
    private String photourl;
    
    private String describtion;
    
    
    public String getPhotourl() {
      return photourl;
    }

    public void setPhotourl(String photourl) {
      this.photourl = photourl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName == null ? null : brandName.trim();
    }

    public String getBrandNameAbbr() {
        return brandNameAbbr;
    }

    public void setBrandNameAbbr(String brandNameAbbr) {
        this.brandNameAbbr = brandNameAbbr == null ? null : brandNameAbbr.trim();
    }

    public BrandStatusEnum getBrandStatus() {
        return brandStatus;
    }

    public void setBrandStatus(BrandStatusEnum brandStatus) {
        this.brandStatus = brandStatus;
    }

    public Long getFeBid() {
        return feBid;
    }

    public void setFeBid(Long feBid) {
        this.feBid = feBid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getBrandNo() {
        return brandNo;
    }

    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo == null ? null : brandNo.trim();
    }

    public String getDescribtion() {
        return describtion;
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion == null ? null : describtion.trim();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        MsBrand other = (MsBrand) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getBrandName() == null ? other.getBrandName() == null : this.getBrandName().equals(other.getBrandName()))
            && (this.getBrandNameAbbr() == null ? other.getBrandNameAbbr() == null : this.getBrandNameAbbr().equals(other.getBrandNameAbbr()))
            && (this.getBrandStatus() == null ? other.getBrandStatus() == null : this.getBrandStatus().equals(other.getBrandStatus()))
            && (this.getFeBid() == null ? other.getFeBid() == null : this.getFeBid().equals(other.getFeBid()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getBrandNo() == null ? other.getBrandNo() == null : this.getBrandNo().equals(other.getBrandNo()))
            && (this.getDescribtion() == null ? other.getDescribtion() == null : this.getDescribtion().equals(other.getDescribtion()))
            && (this.getPhotourl() == null ? other.getPhotourl() == null : this.getPhotourl().equals(other.getPhotourl()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getBrandName() == null) ? 0 : getBrandName().hashCode());
        result = prime * result + ((getBrandNameAbbr() == null) ? 0 : getBrandNameAbbr().hashCode());
        result = prime * result + ((getBrandStatus() == null) ? 0 : getBrandStatus().hashCode());
        result = prime * result + ((getFeBid() == null) ? 0 : getFeBid().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getBrandNo() == null) ? 0 : getBrandNo().hashCode());
        result = prime * result + ((getDescribtion() == null) ? 0 : getDescribtion().hashCode());
        result = prime * result + ((getPhotourl()==null ? 0 : getPhotourl().hashCode()));
        return result;
    }
}