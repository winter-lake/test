package com.kxwp.admin.query.masterStation;

import com.kxwp.admin.constants.RoleStatusEnum;
import com.kxwp.common.constants.SystemTypeEnum;

/**
 * 总站根据角色id查询对应资源id
 * date: 2016年8月2日 下午3:29:43 
 * @author Liuzibing
 */
public class MsRoleResourceRelationQuery implements Cloneable{
     private RoleStatusEnum roleResourceStatus;
     
     private Long createUserId;
     
     private SystemTypeEnum systemType;
     
     // 默认页面条数
     private int pageSize = 10;

     // 当前页,从1开始
     private int currentPage = 1;
     
     private int offset;

     
     
    public RoleStatusEnum getRoleResourceStatus() {
      return roleResourceStatus;
    }

    public void setRoleResourceStatus(RoleStatusEnum roleResourceStatus) {
      this.roleResourceStatus = roleResourceStatus;
    }

    public Long getCreateUserId() {
      return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
      this.createUserId = createUserId;
    }

    public SystemTypeEnum getSystemType() {
      return systemType;
    }

    public void setSystemType(SystemTypeEnum systemType) {
      this.systemType = systemType;
    }

    public int getPageSize() {
      return pageSize;
    }

    public void setPageSize(int pageSize) {
      this.pageSize = pageSize;
    }

    public int getCurrentPage() {
      return currentPage;
    }

    public void setCurrentPage(int currentPage) {
      this.currentPage = currentPage;
    }

    public int getOffset() {
      return offset;
    }

    public void setOffset(int offset) {
      this.offset = offset;
    }
     
     
}
