package com.kxwp.h5.actions.classificaion;

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
import com.kxwp.common.entity.MSClassificaion;
import com.kxwp.common.query.sys.ClassificaionQuery;
import com.kxwp.common.service.core.ClassificaionService;

@Controller
@RequestMapping("/h5/classificaion")
public class ClassificaionAction {
  @Autowired
  private ClassificaionService classificaionService;

  /**
   * queryClassificaion:(查询商品分类).
   *
   * 2016年8月5日 下午8:04:38
   * 
   * @author zhaojn
   * @param classificaionQuery
   * @param request
   * @param response
   * @return
   * 
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  @RequestMapping(value = "/ajax/queryClassificaion.do",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData queryClassificaion(
      @Valid @RequestBody ClassificaionQuery classificaionQuery, HttpServletRequest request,
      HttpServletResponse response) {
    ExchangeData edb = new ExchangeData<>();
    try {
      List<MSClassificaion> ClassificaionList =
          classificaionService.queryCascadeClassificaion(classificaionQuery);
      edb.setData(ClassificaionList);
    } catch (Exception e) {
      edb.markException(e.getMessage(), e);
    }
    return edb;
  }

  @RequestMapping(value = "/gotoCategory.do")
  public String gotoCategory(HttpServletRequest request,
      HttpServletResponse response) {
    return "goods/category";
  }

}
