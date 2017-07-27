package com.kxwp.admin.actions.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kxwp.common.data.exchange.ExchangeData;
import com.kxwp.common.query.sys.OddNumberQuery;
import com.kxwp.common.service.core.OddNumberService;
import com.kxwp.common.utils.KXWPLogUtils;

/**
 * 获取编号
 *
 * @author lou jian wen
 */
@Controller
@RequestMapping("/common/no")
public class SYSNumberAction {


  @Autowired
  private OddNumberService oddNumberService;

  /**
   * 
   * getNewNumber:(获取新编号).
   *
   * 2016年8月10日 下午1:28:56
   * 
   * @author lou jian wen
   * @param query
   * @param request
   * @param response
   * @return
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  @RequestMapping(value = "/ajax/getSYSNumber.do",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
  public @ResponseBody ExchangeData getNewNumber(@RequestBody OddNumberQuery query,
      HttpServletRequest request, HttpServletResponse response) {
    ExchangeData exchangeData = new ExchangeData();
    try {
      String new_number = oddNumberService.newOddNumberViaProcedure(query);
      exchangeData.setData(new_number);
    } catch (Exception e) {
      KXWPLogUtils.logException(SYSNumberAction.class, e);
      exchangeData.markException("获取新编号异常" + query.toString(), e);
    }

    return exchangeData;
  }
}
