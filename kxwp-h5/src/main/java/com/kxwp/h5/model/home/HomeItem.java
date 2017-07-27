package com.kxwp.h5.model.home;

import java.io.Serializable;

/**
 * Date:     2016年8月27日 下午1:53:11 
 * @author   lou jian wen 
 * 每个条码的属性
 */
public class HomeItem implements Serializable {

  
  /**
   * serialVersionUID:TODO(用一句话描述这个变量表示什么).
   */
  private static final long serialVersionUID = -5376455368827042349L;

  private Long id;
  
  //标题
  private String title;
  //副标题
  private String sub_title;
  

  private String photo_url;
  
  private String open_url;
  
  public String getSub_title() {
    return sub_title;
  }

  public void setSub_title(String sub_title) {
    this.sub_title = sub_title;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getPhoto_url() {
    return photo_url;
  }

  public void setPhoto_url(String photo_url) {
    this.photo_url = photo_url;
  }

  public String getOpen_url() {
    return open_url;
  }

  public void setOpen_url(String open_url) {
    this.open_url = open_url;
  }

}

