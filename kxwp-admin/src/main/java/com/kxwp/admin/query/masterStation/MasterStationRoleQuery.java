package com.kxwp.admin.query.masterStation;

import com.kxwp.admin.constants.RoleStatusEnum;
import com.kxwp.common.constants.SystemTypeEnum;

/**
 * 总站查询角色实体
 * date: 2016年8月1日 下午4:03:04 
 *
 * @author Liuzibing
 */
/**
 * date: 2016年8月2日 上午11:58:51 
 *
 * @author Liuzibing
 */
public class MasterStationRoleQuery implements Cloneable{
    private RoleStatusEnum roleStatus;
    
    private Long createUserId;
    
    private SystemTypeEnum systemType;
    
    // 默认页面条数
    private int pageSize = 10;

    // 当前页,从1开始
    private int currentPage = 1;
    
    private int offset;

    
    
    /**
     * @return RoleStatus
    
     */
    public RoleStatusEnum getRoleStatus() {
      return roleStatus;
    }

    public void setRoleStatus(RoleStatusEnum roleStatus) {
      this.roleStatus = roleStatus;
    }

    
    
    public Long getCreateUserId() {
      return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
      this.createUserId = createUserId;
    }

    /**
     * @return  systemType
    
     */
    public SystemTypeEnum getSystemType() {
      return systemType;
    }

    public void setSystemType(SystemTypeEnum systemType) {
      this.systemType = systemType;
    }

    @Override  
    public Object clone() throws CloneNotSupportedException  
    {  
        return super.clone();  
    }
    
    /**
     * @return  pageSize
    
     */
    public int getPageSize() {
      return pageSize;
    }

    public void setPageSize(int pageSize) {
      this.pageSize = pageSize;
    }
    /**
     * @return  currentPage
    
     */
    public int getCurrentPage() {
      return currentPage;
    }

    public void setCurrentPage(int currentPage) {
      this.currentPage = currentPage;
    }

    
    /**
     * @return  offset
    
     */
    public int getOffset() {
      return offset;
    }

    public void setOffset(int offset) {
      this.offset = offset;
    } 
    
    
    
}
