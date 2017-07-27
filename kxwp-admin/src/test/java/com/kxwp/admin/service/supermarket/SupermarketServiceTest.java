package com.kxwp.admin.service.supermarket;

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

import com.kxwp.common.data.exchange.ExchangeData;
import com.kxwp.common.entity.supermarket.Supermarket;
import com.kxwp.common.model.exception.KXWPNotFoundException;
import com.kxwp.common.query.supermarket.SupermarketQuery;
import com.kxwp.common.utils.JacksonUtils;
import com.kxwp.common.utils.KXWPLogUtils;

/**
 * @author   lou jian wen 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations={"/WEB-INF/spring/root-context.xml" })
public class SupermarketServiceTest {
  
  @Autowired
  private SupermarketService supermarketService;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {}

  @AfterClass
  public static void tearDownAfterClass() throws Exception {}

  @Before
  public void setUp() throws Exception {}

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testQueryCascadeClassificaion() throws KXWPNotFoundException {
    SupermarketQuery supe = new SupermarketQuery();
    supe.setSupermarketName("小主超市");
    ExchangeData<List<Supermarket>> ed = new ExchangeData<>();
    List<Supermarket> list = supermarketService.searchByQuery(supe);
    ed.setData(list);
    
    KXWPLogUtils.logInfo(this.getClass(), JacksonUtils.writeValue(ed));
  }


}

