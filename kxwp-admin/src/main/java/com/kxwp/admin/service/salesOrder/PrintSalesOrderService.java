package com.kxwp.admin.service.salesOrder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxwp.admin.entity.serviceStation.ServiceStation;
import com.kxwp.admin.service.serviceStation.FWZCommonService;
import com.kxwp.common.entity.SYSRegion;
import com.kxwp.common.entity.supplier.OrderItem;
import com.kxwp.common.entity.supplier.SalesOrder;
import com.kxwp.common.entity.supplier.Supplier;
import com.kxwp.common.model.exception.KXWPNotFoundException;
import com.kxwp.common.model.exception.KXWPValidException;
import com.kxwp.common.model.order.FWZPrintSalesOrder;
import com.kxwp.common.model.order.PrintSalesOrder;
import com.kxwp.common.query.orderitem.SelectOrderItemQuery;
import com.kxwp.common.query.sys.RegionQuery;
import com.kxwp.common.service.core.RegionService;
import com.kxwp.common.service.orderitems.OrderItemsService;
import com.kxwp.common.service.salesorder.SalesOrderService;
import com.kxwp.common.service.supplier.SupplierRepoService;
import com.kxwp.common.utils.PhoneNumberFormat;

/**
 * 打印订单接口 date: 2016年9月19日 下午2:55:46
 *
 * @author zhaojn
 */
@Service("PrintSalesOrderService")
public class PrintSalesOrderService {

  @Autowired
  private SupplierRepoService supplierRepoService;

  @Autowired
  private SalesOrderService salesOrderService;

  @Autowired
  private FWZCommonService fwzCommonService;

  @Autowired
  private RegionService regionService;

  @Autowired
  private OrderItemsService orderItemsService;

  /**
   * getPrintSalesOrder:(获取供应商打印发货单订单详情).
   *
   * 2016年9月3日 下午6:38:21
   * 
   * @author zhaojn
   * @param orderId
   * @return
   * @throws KXWPNotFoundException
   * @throws KXWPValidException
   */
  public PrintSalesOrder getPrintSalesOrder(Long orderId)
      throws KXWPNotFoundException, KXWPValidException {
    PrintSalesOrder printSalesOrder = new PrintSalesOrder();
    SalesOrder salesorder = salesOrderService.getSalesOrderById(orderId);
    Supplier supplier = supplierRepoService.getSupplierByID(salesorder.getSupplierId());
    formatPrintAddress(supplier);
    ServiceStation serviceStation =
        fwzCommonService.getServiceStationByID(supplier.getServiceStationId());
    printSalesOrder.setSalesOrder(salesorder);
    printSalesOrder.setSupplier(supplier);
    printSalesOrder.setPrintDate(new Date());
    printSalesOrder
        .setServiceStation_phone(PhoneNumberFormat.formatPhone(serviceStation.getPhone()));
    return printSalesOrder;
  }

  /**
   * 
   * formatFullAddress:(返回供应商打印订单显示地址).
   *
   * 2016年8月23日 上午11:53:55
   * 
   * @author lou jian wen
   * @param supplier
   * @return
   */
  Supplier formatPrintAddress(Supplier supplier) {
    RegionQuery regionQuery = new RegionQuery();
    StringBuilder full_address = new StringBuilder();
    regionQuery.setCurrentRegionID(supplier.getProvince().longValue());
    regionQuery.setCurrentRegionID(supplier.getCity().longValue());
    regionQuery.setCurrentRegionID(supplier.getCounty().longValue());
    SYSRegion county = regionService.queryCurrentRegion(regionQuery);
    full_address.append(county.getName());
    full_address.append(supplier.getAddress());
    supplier.setFull_address(full_address.toString());
    return supplier;
  }


  /**
   * fwzPrintSalesOrder:(服务站打印发货单).
   *
   * 2016年9月23日 下午3:39:33
   * 
   * @author zhaojn
   * @param bigOrderNoList
   * @return
   * @throws KXWPNotFoundException
   * @throws KXWPValidException
   */
  public List<FWZPrintSalesOrder> fwzPrintSalesOrder(List<String> bigOrderNoList)
      throws KXWPNotFoundException, KXWPValidException {

    if (bigOrderNoList == null || bigOrderNoList.size() == 0) {
      throw new KXWPNotFoundException("服务站批量打印订单所需参数bigOrderNoList");
    }

    List<FWZPrintSalesOrder> fwzPrintSalesOrder_list = new ArrayList<FWZPrintSalesOrder>();

    for (String bigOrderNo : bigOrderNoList) {

      // 查询订单明细列表
      SelectOrderItemQuery query = new SelectOrderItemQuery();
      query.setBigOrderNo(bigOrderNo);
      List<OrderItem> orderItemList = orderItemsService.queryOrderItemByBigOrderNo(query);
      
      if (orderItemList == null || orderItemList.size() == 0) {
        continue;
      }
      
      FWZPrintSalesOrder fwzPrintSalesOrder = new FWZPrintSalesOrder();
      // 拼装商品清单list 信息
      List<OrderItem> orderItemDetail_list = new ArrayList<>();

      for (OrderItem item : orderItemList) {
        // 查询服务站电话
        if (StringUtils.isEmpty(fwzPrintSalesOrder.getServiceStation_phone())) {
          ServiceStation serviceStation =
              fwzCommonService.getServiceStationByID(item.getServiceStationId());
          fwzPrintSalesOrder
              .setServiceStation_phone(PhoneNumberFormat.formatPhone(serviceStation.getPhone()));
        }

        // 组装商品信息
        OrderItem itemDetail = orderItemsService.getOrderItemByID(item.getId());
        Supplier supplier = supplierRepoService.getSupplierByID(itemDetail.getSupplierId());

        itemDetail.setSupplierName(supplier.getSupplierName());
        orderItemDetail_list.add(itemDetail);
      }
      fwzPrintSalesOrder.setOrderItemList(orderItemDetail_list);

      fwzPrintSalesOrder_list.add(fwzPrintSalesOrder);
    }

    return fwzPrintSalesOrder_list;
  }
}
