package com.kxwp.admin.actions.serviceStation;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kxwp.common.model.report.SalesOrderCSVRecord;
import com.kxwp.common.model.report.SalesOrderReport;
import com.kxwp.common.query.salesorder.SelectSalesOrderQuery;
import com.kxwp.common.service.salesorder.SalesOrderService;
import com.kxwp.common.utils.KXWPLogUtils;
import com.kxwp.common.utils.ReportUtils;

/**
 * Date: 2016年9月20日 下午6:35:10
 * 
 * @author lou jian wen
 */
@Controller
@RequestMapping("/fwz/report")
public class FWZReportAction {

  @Autowired
  private SalesOrderService salesOrderService;
  
  


  @SuppressWarnings("unchecked")
  @RequestMapping("/ajax/exportOrder.do")
  public void exportOrder(SelectSalesOrderQuery query, HttpServletRequest request,
      HttpServletResponse response) {
    PrintWriter printWriter = null;
    try {
      printWriter = response.getWriter();
      SalesOrderReport report = salesOrderService.exportSalesOrder(query);
      ReportUtils.setContentType(response, report);
      List<SalesOrderCSVRecord> record_list = (List<SalesOrderCSVRecord>) report.getRecord_list();
      
      // 写标题
      printWriter.write(report.initalTitle());
      for (SalesOrderCSVRecord record : record_list) {
        printWriter.write(record.formatCSV());
      }
    } catch (Exception e) {
      KXWPLogUtils.logException(this.getClass(),"订单报表导出异常", e);
      printWriter.write(e.getMessage());
    }

  }
}

