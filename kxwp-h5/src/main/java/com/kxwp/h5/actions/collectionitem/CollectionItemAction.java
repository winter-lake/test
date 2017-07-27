package com.kxwp.h5.actions.collectionitem;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kxwp.common.data.exchange.ExchangeData;
import com.kxwp.common.entity.supermarket.APICollectionItem;
import com.kxwp.common.form.supermarket.collectionitem.CreateManyCollectionItemForm;
import com.kxwp.common.form.supermarket.collectionitem.DeleteCollectionItem;
import com.kxwp.common.model.app.supermarket.APPUserModel;
import com.kxwp.common.model.exception.KXWPNotFoundException;
import com.kxwp.common.model.exception.SYSException;
import com.kxwp.common.query.supermarket.CollectionItemQuery;
import com.kxwp.common.service.supermarket.collectionitem.CollectionItemService;
import com.kxwp.common.utils.KXWPLogUtils;
import com.kxwp.h5.utils.H5UserSessionUtils;

/**
 * date: 2016年9月10日 下午2:16:28 超市收藏夹
 * 
 * @author zhaojn
 * 
 */
@Controller
@RequestMapping("/h5/v2/collectionItem")
public class CollectionItemAction {
  @Autowired
  private CollectionItemService  collectionItemService;
  
  /**
   * addCollectionItem:(超市添加收藏夹功能).
   *
   * 2016年9月10日 下午2:25:46
   * @author zhaojn
   * @param form
   * @param request
   * @param response
   * @return
  
   */
  @SuppressWarnings("rawtypes")
  @RequestMapping(value = "/addCollectionItem.do",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData addCollectionItem(
      @Valid @RequestBody CreateManyCollectionItemForm  form, HttpServletRequest request,
      HttpServletResponse response) {
    ExchangeData edb = new ExchangeData<>();
    try {
    APPUserModel um = H5UserSessionUtils.getUserSession(request);
    form.setSupermarketId(um.getSupermarket_id());
      collectionItemService.addManyCollectionItem(form);
    } catch (KXWPNotFoundException | SYSException e) {
      edb.markException(e.getMessage(), e);
    }catch (Exception e) {
      edb.markException("系统出错",e);
      KXWPLogUtils.logException(CollectionItemAction.class, "/addCollectionItem.do :", e);
    }
    return edb;
  }
  
  
  /**
   * searchCollectionItem:(查询收藏夹列表).
   *
   * 2016年9月12日 下午2:11:13
   * @author zhaojn
   * @param query
   * @param request
   * @param response
   * @return
  
   */
  @RequestMapping(value = "/searchCollectionItem.do", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData<List<APICollectionItem>> searchCollectionItem(
      @RequestBody CollectionItemQuery query, HttpServletRequest request,
      HttpServletResponse response) {
    ExchangeData<List<APICollectionItem>> data = new ExchangeData<>();
    try {
      query.setSupermarketId(H5UserSessionUtils.getUserSession(request).getSupermarket_id());
      List<APICollectionItem> collectionItem_list =  collectionItemService.selectByQuery(query);
      data.setData(collectionItem_list);
    } catch (KXWPNotFoundException e) {
      data.markException(e.getMessage(), e);
    } catch (Exception e) {
      data.markException(e);
      KXWPLogUtils.logException(CollectionItemAction.class, "/searchCollectionItem.do :", e);
    }
    return data;
  }
  
  /**
   * addCollectionItem:(超市删除收藏夹物品).
   *
   * 2016年9月10日 下午2:25:46
   * @author zhaojn
   * @param form
   * @param request
   * @param response
   * @return
  
   */
  @SuppressWarnings("rawtypes")
  @RequestMapping(value = "/deleteCollectionItem.do",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData deleteCollectionItem(
      @Valid @RequestBody DeleteCollectionItem  form, HttpServletRequest request,
      HttpServletResponse response) {
    ExchangeData edb = new ExchangeData<>();
    try {
    APPUserModel um = H5UserSessionUtils.getUserSession(request);
      form.setSupermarketId(um.getSupermarket_id());
      collectionItemService.deleteCollectionItem(form);
    } catch (KXWPNotFoundException | SYSException e) {
      edb.markException(e.getMessage(), e);
    }catch (Exception e) {
      edb.markException("系统出错",e);
      KXWPLogUtils.logException(CollectionItemAction.class, "/deleteCollectionItem.do :", e);
    }
    return edb;
  }

  /**
   * gotoOrders:(跳到商品清单页面).
   *
   * 2016年9月21日 上午11:06:30
   * @author zhaojn
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/gotoMyCollectionItem.do")
  public String gotoOrders(HttpServletRequest request, HttpServletResponse response) {
    return "user/favorites";
  }
}
