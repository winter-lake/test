package com.kxwp.admin.actions.masterStation;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kxwp.admin.constants.RoleStatusEnum;
import com.kxwp.admin.entity.masterStation.MsResource;
import com.kxwp.admin.entity.masterStation.MsRole;
import com.kxwp.admin.model.ms.UserModel;
import com.kxwp.admin.query.masterStation.MasterStationRoleQuery;
import com.kxwp.admin.service.masterStation.MsRoleManageService;
import com.kxwp.admin.utils.session.ZZUserSessionUtils;
import com.kxwp.common.constants.CallStatusEnum;
import com.kxwp.common.data.exchange.ExchangeData;

/**
 * 总站角色管理控制器
 * date: 2016年8月2日 上午9:16:51 
 *
 * @author Liuzibing
 */
@Controller
@RequestMapping(value="/zz/msRoleManage")
public class MsRoleManageAction {
    
    @Autowired
    private MsRoleManageService msRoleManageService;
    
    /**
     * getAddView:(初始化添加界面).
     * 2016年8月3日 下午2:55:30
     * @author Liuzibing
     * @param msRole
     * @return
     */
    @RequestMapping(value="/addInit.do")
    public ModelAndView addInit()
    {
      ModelAndView mav=new ModelAndView();
      
      List<MsResource> newlist = msRoleManageService.getMsResourceList();
      
      try
      {
        mav.setViewName("zz/add_role");
        mav.addObject("newlist",newlist);
      }
      catch(Exception ex)
      {
        mav.addObject("callStatus", CallStatusEnum.FAIL);
        mav.addObject("message", "系统错误，请联系管理员！");
        mav.setViewName("500");
      }
      return mav;
    }
    
    /** 
     * add:(添加角色信息).
     * 2016年7月29日 下午6:55:29
     * @author Liuzibing
     * @param role
     * @return
     */
    @RequestMapping(value="/add.do")
    public ModelAndView add(HttpServletRequest request, MsRole role)
    {
      ModelAndView mav=new ModelAndView();
      try
      {
        UserModel userModel = ZZUserSessionUtils.getUserSession(request);
        
        role.setCreateUserId(userModel.getId());
        
        
        msRoleManageService.addMixture(role);
        
        ExchangeData<Object> exchangeData=new ExchangeData<Object>();
        
        mav.setViewName("zz/add_role");
        mav.addObject("exchangeData", exchangeData);
      }
      catch(Exception ex)
      {
        mav.addObject("callStatus",CallStatusEnum.FAIL);
        mav.addObject("message", "系统错误，请联系管理员！");
        mav.setViewName("500");
      }
      return mav;
    }
    
    /**
     * modify:(修改角色信息)
     * 2016年7月29日 下午7:01:17
     * @author Liuzibing
     * @param role
     * @return
     */
    @RequestMapping(value="/modify.do")
    public ModelAndView modify(HttpServletRequest request, MsRole role)
    {
      ModelAndView mav=new ModelAndView();
      try
      {
        msRoleManageService.modifyMixture(role);
        
        ExchangeData<Object> exchangeData=new ExchangeData<Object>();
        
        mav.setViewName("zz/edit_role");
        mav.addObject("exchangeData", exchangeData);
      }
      catch(Exception ex)
      {
        mav.addObject("callStatus",CallStatusEnum.FAIL);
        mav.addObject("message", "系统错误，请联系管理员！");
        mav.setViewName("500");
      }
      return mav;
    }
    
    /**
     * get:(根据ID获取角色信息).
     * 2016年7月29日 下午7:05:16
     * @author Liuzibing
     * @param id
     * @return
     */
    @RequestMapping(value="/get.do")
    public ModelAndView get(Long id)
    {
      ModelAndView mav=new ModelAndView();
      
      MsRole msRole=null;
      
      try
      { 
        msRole=msRoleManageService.getMixture(id);
        List<MsResource> newlist = msRoleManageService.getMsResourceList();
        
        mav.setViewName("zz/edit_role");
        mav.addObject("msRole",msRole);
        mav.addObject("newlist",newlist);
      }
      catch(Exception ex)
      {
        mav.setViewName("500");
      }
      return mav;
    }
    
    /**
     * get:(根据ID获取角色信息).
     * 2016年7月29日 下午7:05:16
     * @author Liuzibing
     * @param id
     * @return
     */
    @RequestMapping(value="/remove.do")
    @ResponseBody
    public ExchangeData<Object> remove(Long id)
    {
      ExchangeData<Object> exchangeData = new ExchangeData<Object>();
      
      try
      { 
        msRoleManageService.remove(id);
        
      }
      catch(Exception ex)
      {
        exchangeData.setCallStatus(CallStatusEnum.FAIL);
      }
      return exchangeData;
    }
    
    /**
     * get:(根据ID获取角色信息).
     * 2016年7月29日 下午7:05:16
     * @author Liuzibing
     * @param id
     * @return
     */
    @RequestMapping(value="/view.do")
    public ModelAndView view(Long id)
    {
      ModelAndView mav=new ModelAndView();
      
      MsRole msRole=null;
      
      try
      { 
        msRole=msRoleManageService.getMixture(id);
        List<MsResource> newlist = msRoleManageService.getMsResourceList();
        
        mav.setViewName("zz/view_role");
        mav.addObject("msRole",msRole);
        mav.addObject("newlist",newlist);
      }
      catch(Exception ex)
      {
        mav.setViewName("500");
      }
      return mav;
    }
    
    
    /**
     * list:(显示全部).
     * 2016年8月1日 下午5:29:39
     * @author Liuzibing
     * @param masterStationRoleQuery
     * @return
     */
    @RequestMapping(value="/list.do")
    public ModelAndView list(MasterStationRoleQuery masterStationRoleQuery){
      ModelAndView mav=new ModelAndView();
      ExchangeData<Object> exchangeData=new ExchangeData<Object>();
      List<MsRole> msroles;
      try
      { 
        masterStationRoleQuery.setRoleStatus(RoleStatusEnum.VALID);//只查询VALID状态的（没有被删除）的数据
        exchangeData = msRoleManageService.exchangeData(masterStationRoleQuery);
        msroles = msRoleManageService.getAllRole(masterStationRoleQuery);
        
        mav.setViewName("zz/role");
        mav.addObject("exchangeData",exchangeData);
        mav.addObject("msroles",msroles);
        mav.addObject("masterStationRoleQuery",masterStationRoleQuery);
      }
      catch(Exception ex)
      {
        exchangeData.setCallStatus(CallStatusEnum.FAIL);
        exchangeData.setMessage("操作失败，请联系管理员");
        mav.setViewName("500");
        mav.addObject("exchangeData",exchangeData);
        mav.addObject("msroles",null);
        mav.addObject("masterStationRoleQuery",masterStationRoleQuery);
      }
      return mav;
    }
    
}
