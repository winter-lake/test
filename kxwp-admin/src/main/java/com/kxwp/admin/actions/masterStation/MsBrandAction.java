package com.kxwp.admin.actions.masterStation;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kxwp.admin.constants.BrandStatusEnum;
import com.kxwp.admin.entity.masterStation.MsBrand;
import com.kxwp.admin.query.masterStation.MsBrandQuery;
import com.kxwp.admin.service.masterStation.MsBrandService;
import com.kxwp.common.constants.CallStatusEnum;
import com.kxwp.common.constants.KXWPNumberRuleEnum;
import com.kxwp.common.data.exchange.ExchangeData;
import com.kxwp.common.query.sys.OddNumberQuery;
import com.kxwp.common.service.core.OddNumberService;

@Controller
@RequestMapping(value="/zz/ssBrand")
public class MsBrandAction {
  @Autowired
  private MsBrandService msBrandService;
  
  @Autowired
  private OddNumberService oddNumberService;
  /**
   * findInit:(初始化品牌管理查询页面).
   * 2016年8月19日 上午10:50:17
   * @author Liuzibing
   * @return
   */
  @RequestMapping(value="/queryBrandInit.do",method = RequestMethod.GET)
  public ModelAndView findInit(){
    ModelAndView mav=new ModelAndView();
    try{
      mav.setViewName("zz/brandlist");
    }catch(Exception ex){
      mav.addObject("callStatus", CallStatusEnum.FAIL);
      mav.addObject("message", "系统错误，请联系管理员！");
      mav.setViewName("500");// TODO
    }
    return mav;
  }
  
  /**
   * findBrandMessage:(条件查询/显示全部).
   * 2016年8月19日 上午10:50:39
   * @author Liuzibing
   * @param msbrandQuery
   * @param request
   * @param response
   * @return
   */
  @SuppressWarnings("rawtypes")
  @RequestMapping(value = "/ajax/queryBrandMessage.do",
  produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},method = {RequestMethod.POST})
  public @ResponseBody ExchangeData queryBrandMessage(@RequestBody MsBrandQuery msbrandQuery,
     HttpServletRequest request,HttpServletResponse response){
    ExchangeData<List<MsBrand>> exchangeData = new ExchangeData<List<MsBrand>>();
    try{
      if(BrandStatusEnum.ALL.equals(msbrandQuery.getBrandStatus()))
      {
        msbrandQuery.setBrandStatus(null);
      }
      List<MsBrand> msBrands = msBrandService.findBycontion(msbrandQuery);
      int total = msBrandService.countByQuery(msbrandQuery);
      exchangeData.getPager().setCurrentPage(msbrandQuery.getPager().getCurrentPage());
      exchangeData.getPager().setTotoalResults(total);
      exchangeData.setData(msBrands);
    }catch(Exception ex){
      exchangeData.setCallStatus(CallStatusEnum.FAIL);
      exchangeData.setMessage("品牌查询异常，请联系管理员！");
    }
    return exchangeData;
  }
  
  
  /**
   * addInit:(初始化添加品牌页面).
   * 2016年8月20日 上午11:04:03
   * @author Liuzibing
   * @return
   */
  @RequestMapping(value = "/goaddbrand.do", method = RequestMethod.GET)
  public ModelAndView addInit()
  {
    OddNumberQuery query = new OddNumberQuery();
    ModelAndView mav=new ModelAndView();
    query.setNumber_type(KXWPNumberRuleEnum.BRAND_NO);
    try{
      mav.addObject("BrandNo",oddNumberService.newOddNumberViaProcedure(query));
      mav.setViewName("zz/brand_add");
    }catch(Exception ex){
      mav.addObject("callStatus", CallStatusEnum.FAIL);
      mav.addObject("message", "系统错误，请联系管理员！");
      mav.setViewName("500");// TODO
    }
    return mav;
  }
  
  
  /**
   * addBrand:(添加品牌).
   * 2016年8月20日 下午2:09:33
   * @author Liuzibing
   * @param msBrand
   * @param request
   * @return
   */
  
  @SuppressWarnings("rawtypes")
  @RequestMapping(value="/addbrand.do",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},method = {RequestMethod.POST})
  public @ResponseBody ExchangeData addBrand(@RequestBody MsBrand msBrand, HttpServletRequest request,HttpServletResponse response)
  {
    ExchangeData<Object> exchangeData=new ExchangeData<Object>();
    try{
      msBrandService.addBrand(msBrand);
    }
    catch(Exception ex)
    {
      exchangeData.markException(ex);
    }
    return exchangeData;
  }
  
  /**
   * getInit:(获取品牌详情).
   * 2016年8月22日 上午10:59:11
   * @author Liuzibing
   * @param id
   * @param request
   * @return
   */
  @RequestMapping(value="/getInit.do")
  public ModelAndView getInit(Long id,HttpServletRequest request)
  {
    ModelAndView mav = new ModelAndView();
    try{
      MsBrand msBrand = msBrandService.get(id);
      mav.addObject("msBrand",msBrand);
      mav.setViewName("zz/brand_detail");
    }catch(Exception ex){
      mav.addObject("callStatus", CallStatusEnum.FAIL);
      mav.addObject("message", "获取信息失败！");
      mav.setViewName("500");
    }
    return mav;
  }
  
  
  /**
   * modifyInit:(初始化编辑品牌界面).
   * 2016年8月22日 下午4:48:19
   * @author Liuzibing
   * @param id
   * @param request
   * @return
   */
  @RequestMapping(value="/modifyInit.do")
  public ModelAndView modifyInit(Long id,HttpServletRequest request)
  {
    ModelAndView mav=new ModelAndView();
    MsBrand msBrand;
    try{
      msBrand = msBrandService.get(id);
      if(msBrand.getPhotourl()!=""){
        String filekey=msBrand.getPhotourl().substring(23);
        mav.addObject("filekey",filekey);
      }
      mav.addObject("msBrand",msBrand);
      mav.setViewName("zz/brand_edit");
    }catch(Exception ex){
      mav.addObject("callStatus", CallStatusEnum.FAIL);
      mav.addObject("message", "获取信息失败！");
      mav.setViewName("500");
    }
    return mav;
  }
  
  
  /**
   * modify:(编辑品牌)
   * 2016年8月22日 下午5:15:43
   * @author Liuzibing
   * @param msBrand
   * @param request
   * @param response
   * @return
   */
  @SuppressWarnings("rawtypes")
  @RequestMapping(value="/modify.do",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},method = {RequestMethod.POST})
  public @ResponseBody ExchangeData modify(@RequestBody MsBrand msBrand,HttpServletRequest request,HttpServletResponse response)
  {
    ExchangeData<Object> exchangeData = new ExchangeData<Object>();
    try{
      msBrand.setPhotourl(msBrandService.getPhoto(msBrand));
      msBrandService.modify(msBrand);
    }catch(Exception ex){
      exchangeData.setCallStatus(CallStatusEnum.FAIL);
      exchangeData.setMessage("品牌修改异常，请联系管理员！");
    }
    return exchangeData;
  }
  
  
  /**
   * change:(改变品牌状态).
   * 2016年8月22日 下午4:00:32
   * @author Liuzibing
   * @param id
   * @param request
   * @param response
   * @return
   */
  @SuppressWarnings("rawtypes")
  @RequestMapping(value="/changeStatus.do",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},method = {RequestMethod.POST})
  public @ResponseBody ExchangeData changeStatus(@RequestBody MsBrand msbrand,HttpServletRequest request,HttpServletResponse response){
    ExchangeData<Object> exchangeData=new ExchangeData<Object>();
    try{
      msBrandService.changeStatus(msbrand);
     }
    catch(Exception ex)
    {
      exchangeData.setCallStatus(CallStatusEnum.FAIL);
      exchangeData.setMessage("发生错误，请联系管理员！");
    }
    return exchangeData;
  }
}
