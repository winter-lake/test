package com.kxwp.h5.model.announcement;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kxwp.common.utils.KXWPDatetimeUtils;

/**
 * 公告响应bean
 * date: 2016年9月14日 上午10:33:25 
 *
 * @author wangjun
 */
public class AnnouncementResponse {
  private Long id;

  private String announcementName;
  
  private String serviceStationName;

  @JsonFormat(pattern = KXWPDatetimeUtils.YYYY_MM_DD_HH_MM_SS_DASH, timezone = "Asia/Shanghai")
  private Date pushTime;

  private String content;
  
  private String pictureUrl;
  
  @JsonIgnore
  private String announcementNo;

  /**
   * @return  id
  
   */
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return  announcementName
  
   */
  public String getAnnouncementName() {
    return announcementName;
  }

  public void setAnnouncementName(String announcementName) {
    this.announcementName = announcementName;
  }

  /**
   * @return  pushTime
  
   */
  public Date getPushTime() {
    return pushTime;
  }

  public void setPushTime(Date pushTime) {
    this.pushTime = pushTime;
  }

  /**
   * @return  content
  
   */
  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  /**
   * @return  pictureUrl
  
   */
  public String getPictureUrl() {
    return pictureUrl;
  }

  public void setPictureUrl(String pictureUrl) {
    this.pictureUrl = pictureUrl;
  }

  /**
   * @return  announcementNo
  
   */
  public String getAnnouncementNo() {
    return announcementNo;
  }

  public void setAnnouncementNo(String announcementNo) {
    this.announcementNo = announcementNo;
  }

  /**
   * @return  serviceStationName
  
   */
  public String getServiceStationName() {
    return serviceStationName;
  }

  public void setServiceStationName(String serviceStationName) {
    this.serviceStationName = serviceStationName;
  }
}
