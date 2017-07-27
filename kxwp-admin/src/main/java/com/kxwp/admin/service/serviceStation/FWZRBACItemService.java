package com.kxwp.admin.service.serviceStation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxwp.admin.entity.serviceStation.ServiceStationAccount;
import com.kxwp.admin.entity.serviceStation.SsResource;
import com.kxwp.admin.model.ms.UserModel;
import com.kxwp.common.constants.CachekeyPrefix;
import com.kxwp.common.model.rbac.MenuItem;
import com.kxwp.common.service.core.AbstractCacheService;

/**
 * Date: 2016年8月29日 下午5:31:17
 * 
 * @author lou jian wen 总站权限菜单
 */
@Service("FWZRBACItemService")
public class FWZRBACItemService {


  @Autowired
  private SsAccountService accountService;
  
  @Autowired
  private AbstractCacheService cacheService;

  /**
   * 
   * getAccessItem:(返回当前用户的权限菜单).
   *
   * 2016年8月29日 下午5:32:25
   * 
   * @author lou jian wen
   * @param user
   * @return
   */
  public List<MenuItem> getAssignedItem(UserModel user) {
    List<MenuItem> menu_list =  cacheService.getValue(CachekeyPrefix.ADMIN_COMMON_MENU + "fwz" + user.getId());
    if(menu_list != null && menu_list.size() > 0){
      return menu_list;
    }
    menu_list = new ArrayList<>();
    ServiceStationAccount account = accountService.getAccountInfo(user.getId());
    Set<SsResource> resource_set = account.getSsResourcesFinal();
    for (SsResource parent_Resource : resource_set) {
      MenuItem parent_menu = new MenuItem();
      parent_menu.setItem_name(parent_Resource.getName());
      parent_menu.setItem_url(parent_Resource.getUrl());
      if (parent_Resource.getSsResources() != null) {
        List<MenuItem> children_menu = new ArrayList<>();
        for (int i = 0; i < parent_Resource.getSsResources().size(); i++) {
          SsResource child = parent_Resource.getSsResources().get(i);
          MenuItem menuItem = new MenuItem();
          menuItem.setItem_url(child.getUrl());
          menuItem.setItem_name(child.getName());
          // 子菜单自动高亮,把菜单关联的URL都绑定上
          StringBuilder item_data_path = new StringBuilder();
          item_data_path.append(menuItem.getItem_url()).append(",");
          for (int j = 0; j < child.getModuleUrls().size(); j++) {
            if (j != child.getModuleUrls().size() - 1) {
              item_data_path.append(child.getModuleUrls().get(j)).append(",");
            }
          }
          menuItem.setData_path(item_data_path.toString());
          children_menu.add(menuItem);
        }
        parent_menu.setData_path(parent_menu.getItem_url());
        parent_menu.setChildren(children_menu);
      }
      menu_list.add(parent_menu);
    }
    cacheService.putKey(CachekeyPrefix.ADMIN_COMMON_MENU + "fwz" + user.getId(), menu_list);
    return menu_list;
  }
}

