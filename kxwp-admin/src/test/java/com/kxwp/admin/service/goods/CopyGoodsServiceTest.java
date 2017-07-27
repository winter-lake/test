package com.kxwp.admin.service.goods;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kxwp.admin.service.supplier.CopyGoodsService;
import com.kxwp.common.entity.supplier.OrderItem;
import com.kxwp.common.entity.supplier.SalesOrder;
import com.kxwp.common.form.goods.CopyGoodsForm;
import com.kxwp.common.mapper.supplier.OrderItemMapper;
import com.kxwp.common.mapper.supplier.SalesOrderMapper;
import com.kxwp.common.model.exception.KXWPNotFoundException;
import com.kxwp.common.model.exception.SYSException;

/**
 * Date:     2016年8月31日 上午9:16:05 
 * @author   zhao jia nan 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/root-context.xml" })
public class CopyGoodsServiceTest {
  
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {}

  @AfterClass
  public static void tearDownAfterClass() throws Exception {}

  @Before
  public void setUp() throws Exception {}

  @After
  public void tearDown() throws Exception {}

  @Autowired
  private CopyGoodsService  copyGoodsService;
  @Autowired 
  private SalesOrderMapper salesOrderMapper;
  @Autowired
  private OrderItemMapper  orderItemMapper;
  @Test
  public void testCopyGoods() throws SYSException, KXWPNotFoundException, IOException {
	  CopyGoodsForm form = new CopyGoodsForm();
	  form.setCopyed_supplierId(1L);
	  form.setSupplierId(42L);
	  copyGoodsService.copyGoods(form);
  }
  
  
  @Test
  public void testbigordernumber() throws SYSException, KXWPNotFoundException, IOException {
     List<SalesOrder> list = salesOrderMapper.selectAll();
     for(SalesOrder sa:list){
       sa.setBigOrderNo(sa.getOrderNo());
       List<OrderItem> item_list = orderItemMapper.selectByOrderId(sa.getId());
       if(item_list==null||item_list.size()==0){
         continue;
       }
       for(OrderItem item:item_list){
         item.setBigOrderNo(sa.getBigOrderNo());
         orderItemMapper.updateByPrimaryKeySelective(item);
       }
     }
  }
  
}

