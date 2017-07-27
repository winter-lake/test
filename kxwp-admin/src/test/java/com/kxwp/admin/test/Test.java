package com.kxwp.admin.test;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import com.kxwp.common.model.exception.SYSException;
import com.kxwp.common.utils.KXWPEncryptUtils;
import com.kxwp.common.utils.KXWPLogUtils;

/**
 * @author   lou jian wen 
 */
public class Test {

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {}

  @AfterClass
  public static void tearDownAfterClass() throws Exception {}

  @Before
  public void setUp() throws Exception {}

  @After
  public void tearDown() throws Exception {}

  @org.junit.Test
  public void testMiMa() throws SYSException {
    KXWPLogUtils.logInfo(this.getClass(), KXWPEncryptUtils.encryptPassword("kxwp2016"));
  }
  
  @org.junit.Test
  public void testJavaUnicode() throws SYSException{
    //KXWPLogUtils.logInfo(this.getClass(), StringEscapeUtils.escapeJava("abc中文Layer's*/* abc"));
   // KXWPEncryptUtils.encryptPassword("a12345678");
    //System.out.println(KXWPEncryptUtils.encryptPassword("a12345678"));
    String str = "test";
    String[] arr = str.split(",");
    System.out.println(arr[0]);
    KXWPLogUtils.logInfo(this.getClass(), RandomStringUtils.randomAlphanumeric(6));
  }

}

