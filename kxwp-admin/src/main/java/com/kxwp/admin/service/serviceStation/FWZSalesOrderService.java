package com.kxwp.admin.service.serviceStation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxwp.admin.model.fwz.FWZSalesOrder;
import com.kxwp.admin.model.fwz.FWZSalesOrderResult;
import com.kxwp.common.constants.goods.OrderStatusEnum;
import com.kxwp.common.entity.supermarket.Supermarket;
import com.kxwp.common.entity.supplier.SalesOrder;
import com.kxwp.common.entity.supplier.Supplier;
import com.kxwp.common.mapper.supplier.SalesOrderMapper;
import com.kxwp.common.model.exception.KXWPNotFoundException;
import com.kxwp.common.model.exception.KXWPValidException;
import com.kxwp.common.query.salesorder.SelectSalesOrderQuery;
import com.kxwp.common.query.supermarket.SupermarketQuery;
import com.kxwp.common.service.salesorder.SalesOrderService;
import com.kxwp.common.service.supermarket.SuperMarketUserService;
import com.kxwp.common.service.supplier.SupplierRepoService;

/**
 * 服务站 订单 相关接口 
 * date: 2016年9月22日 下午12:05:47 
 *
 * @author zhaojn
 */
@Service("FWZSalesOrderService")
public class FWZSalesOrderService {
  
  @Autowired
  private SalesOrderService salesOrderService;
  
  @Autowired
  private SuperMarketUserService superMarketUserService;
  
  @Autowired
  private SalesOrderMapper salesOrderMapper;
  
  @Autowired
  private SupplierRepoService supplierRepoService ;
  
  
  /**
   * queryOrdersList:(服务站查询订单列表(显示大订单编号)).
   *
   * 2016年9月22日 下午12:12:44
   * @author zhaojn
   * @return
   * @throws KXWPValidException 
   * @throws KXWPNotFoundException 
   */
  public  FWZSalesOrderResult queryOrders(SelectSalesOrderQuery query) throws KXWPNotFoundException, KXWPValidException{
    FWZSalesOrderResult result = new FWZSalesOrderResult();
    
    List<FWZSalesOrder> fwzSalesOrder_list = new ArrayList<FWZSalesOrder>();
    
    BigDecimal totalSumAmount = new BigDecimal(0);
    
    // 根据联系方式 查出对应的超市id
    if (query.getPhone() != null) {
      SupermarketQuery supermarketQuery = new SupermarketQuery();
      supermarketQuery.setPhone(query.getPhone());
      List<Supermarket> supermarketList = superMarketUserService.selectByQuery(supermarketQuery);
      if (supermarketList != null && supermarketList.size() != 0) {
        query.setSupermarketId(supermarketList.get(0).getId());
      }
    }
    
    //根据大订单编号分组
    List<String> bigOrderNoList = salesOrderService.groupByBigOrderNo(query);
    
    for(String bigOrderNo:bigOrderNoList){
      query.setBigOrderNo(bigOrderNo);
      
      //查询此大订单下的小订单 不分页
      FWZSalesOrder fwzSalesOrder = querySumSalesOrderInfo(query);
      fwzSalesOrder.setBigOrderNo(bigOrderNo);
      fwzSalesOrder_list.add(fwzSalesOrder);
      totalSumAmount = totalSumAmount.add(fwzSalesOrder.getSumAmount());
      //计算所有大订单的价格
    }
    
    result.setFwz_salesOrderList(fwzSalesOrder_list);
    result.setTotalSumAmount(totalSumAmount);
    return result;
  }
  
  
  /**
   * querySalesOrderAndOrderItem:(按条件查询订单 不进行分页).
   *
   * 2016年8月24日 下午7:24:07
   * 
   * @author zhaojn
   * @param query
   * @return
   * @throws KXWPNotFoundException
   * @throws KXWPValidException
   * 
   */
  public FWZSalesOrder querySumSalesOrderInfo(SelectSalesOrderQuery query)
      throws KXWPNotFoundException, KXWPValidException {
    
    FWZSalesOrder fwzSalesOrder = new FWZSalesOrder();
    //查询订单(不分页)
    List<SalesOrder> salesOrderList = salesOrderMapper.querySubOrders(query);
    BigDecimal sumAmount = new BigDecimal(0);
    if(salesOrderList==null||salesOrderList.size()==0){

      return fwzSalesOrder;
    }
   
    //拼装订单详细信息
    for (SalesOrder salesOrder : salesOrderList) {
      Supermarket supermarket =
          superMarketUserService.getSuperMarketById(salesOrder.getSupermarketId());
      Supplier supplier = supplierRepoService.getSupplierByID(salesOrder.getSupplierId());
      salesOrder.setSupplierName(supplier.getSupplierName());
      salesOrder.setFull_address(supermarket.getFull_address());
      salesOrder.setReceptionName(supermarket.getReceptionName());
      salesOrder.setPhone(supermarket.getPhone());
      salesOrder.setSupermarketName(supermarket.getSupermarketName());
      //计算总实付金额(不包括取消的订单)
     if(salesOrder.getOrderStatus() != OrderStatusEnum.Cancelled){
       sumAmount = sumAmount.add(salesOrder.getPayAmount());
     }
    }
    
    //拼装显示信息
    fwzSalesOrder.setSalesOrderList(salesOrderList);
    fwzSalesOrder.setOrderTime(salesOrderList.get(0).getOrderTime());
    fwzSalesOrder.setSupermarketName(salesOrderList.get(0).getSupermarketName());
    fwzSalesOrder.setSalesOrderList(salesOrderList);
    fwzSalesOrder.setSumAmount(sumAmount);
    return fwzSalesOrder;

  }
  
  /**
   * fwzCountByQuery:(服务站查询符合条件的订单数量).
   *
   * 2016年8月27日 下午3:16:22
   * 
   * @author zhaojn
   * @param query
   * @return
   * 
   */
  public int fwzCountByQuery(SelectSalesOrderQuery query) {
    return salesOrderMapper.fwzCountByQuery(query);
  }
}
