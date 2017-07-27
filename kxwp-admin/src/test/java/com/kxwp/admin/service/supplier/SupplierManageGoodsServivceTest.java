package com.kxwp.admin.service.supplier;

import static org.junit.Assert.fail;

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
import org.springframework.test.context.web.WebAppConfiguration;

import com.kxwp.common.entity.supplier.Goods;
import com.kxwp.common.model.exception.KXWPNotFoundException;
import com.kxwp.common.model.exception.KXWPValidException;
import com.kxwp.common.model.exception.SYSException;
import com.kxwp.common.query.goods.SearchGoodsRepoQuery;
import com.kxwp.common.utils.KXWPLogUtils;

/**
 * Date:     2016年8月15日 下午1:29:14 
 * @author   lou jian wen 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/root-context.xml" })
public class SupplierManageGoodsServivceTest {

  @Autowired
  private SupplierManageGoodsService supplierManageGoodsServivce;
  

  
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {}

  @AfterClass
  public static void tearDownAfterClass() throws Exception {}

  @Before
  public void setUp() throws Exception {}

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testAddGoods() {
    fail("Not yet implemented");
  }
  
  @Test
  public void testListGoods() throws KXWPNotFoundException, KXWPValidException {
    SearchGoodsRepoQuery query = new SearchGoodsRepoQuery();
    query.setSupplierID(1L);
    List<Goods> goodsList = supplierManageGoodsServivce.listGoods(query);
    KXWPLogUtils.logInfo(SupplierManageGoodsServivceTest.class, goodsList.size());
  }
  
  @Test
  public void testMockAddGoods() throws SYSException{
    for(int i=0;i<10;i++){
      try {
        supplierManageGoodsServivce.mockAddGoods();
      } catch (Exception e) {
        
        KXWPLogUtils.logException(this.getClass(), e);
        
      }
    }
  }

}

