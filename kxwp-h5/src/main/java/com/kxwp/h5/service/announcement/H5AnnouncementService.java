package com.kxwp.h5.service.announcement;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxwp.common.constants.OSSImgStyleEnum;
import com.kxwp.common.entity.fwz.ServiceStation;
import com.kxwp.common.entity.fwz.SsAnnouncement;
import com.kxwp.common.mapper.fwz.FWZMapper;
import com.kxwp.common.model.exception.KXWPException;
import com.kxwp.common.model.exception.KXWPValidException;
import com.kxwp.common.model.oss.BucketNameEnum;
import com.kxwp.common.query.announcement.AnnouncementQuery;
import com.kxwp.common.query.file.OSSFileQuery;
import com.kxwp.common.service.announcement.AnnouncementService;
import com.kxwp.common.service.core.KXWPFileService;
import com.kxwp.h5.model.announcement.AnnouncementResponse;

/**
 * H5Announcement service 
 * date: 2016年9月18日 下午5:18:50 
 *
 * @author wangjun
 */
@Service
public class H5AnnouncementService {
  @Autowired
  private AnnouncementService announcementService;
  
  @Autowired
  private KXWPFileService fileService;
  
  @Autowired
  private FWZMapper fWZMapper;
  
  /**
   * 
   * listAnnouncementResponse:(公告响应列表).
   *
   * 2016年9月18日 下午5:23:03
   * @author wangjun
   * @param announcementQuery
   * @return
   * @throws KXWPException
   * @throws IllegalAccessException
   * @throws InvocationTargetException
   * @throws KXWPValidException
   */
  public List<AnnouncementResponse> listAnnouncementResponse(AnnouncementQuery announcementQuery)
      throws KXWPException, IllegalAccessException, InvocationTargetException,
      KXWPValidException {
    //查询
    List<SsAnnouncement> announcements = announcementService.list(announcementQuery);
    
    //定义response信息
    List<AnnouncementResponse> announcementResponses 
      = new ArrayList<AnnouncementResponse>();
    
    //剪裁返回信息
    for(SsAnnouncement ssAnnouncement2 : announcements){
      AnnouncementResponse announcementResponse = new AnnouncementResponse();
      
      BeanUtilsBean2.getInstance().copyProperties(announcementResponse, ssAnnouncement2);
      
      //查询服务站名称
      ServiceStation serviceStation = fWZMapper.selectByPrimaryKey(ssAnnouncement2.getServiceStationId());
      
      announcementResponse.setServiceStationName(serviceStation.getServiceStationName());
      
      announcementResponses.add(announcementResponse);
    }
    
    List<String> photoList = null;
    
    
    for(AnnouncementResponse announcementResponse : announcementResponses){
      photoList = this.getPhotos(announcementResponse, OSSImgStyleEnum.announcement_694_200);
      
      if(photoList.size() != 0){
        announcementResponse.setPictureUrl(photoList.get(photoList.size()-1));
      }
    }
    return announcementResponses;
  }
  
  /**
   * 
   * getADPhotos:(获取广告图片信息).
   *
   * 2016年8月17日 下午8:09:38
   * @author wangjun
   * @param adConfig
   * @return
   * @throws KXWPValidException 
   */
  private List<String> getPhotos(AnnouncementResponse announcementResponse, OSSImgStyleEnum oSSImgStyleEnum) throws KXWPValidException {
    OSSFileQuery ossFileQuery = new OSSFileQuery();
    ossFileQuery.setBucketName(BucketNameEnum.announcement);
    ossFileQuery.setKey_id(announcementResponse.getAnnouncementNo());
    ossFileQuery.setOssImgStyleEnum(oSSImgStyleEnum);
    
    return fileService.searchFileURL(ossFileQuery);
  }

  /**
   * 
   * getTotalResults:(获得总记录数).
   *
   * 2016年9月18日 下午5:24:13
   * @author wangjun
   * @param announcementQuery
   * @return
   */
  public int getTotalResults(AnnouncementQuery announcementQuery) {
    return announcementService.getTotalResults(announcementQuery);
  }
  
  /**
   * 
   * getAnnouncementResponse:(根据id获取公告信息).
   *
   * 2016年9月18日 下午5:27:16
   * @author wangjun
   * @param id
   * @return
   * @throws IllegalAccessException
   * @throws InvocationTargetException
   * @throws KXWPValidException
   */
  public AnnouncementResponse getAnnouncementResponse(Long id)
      throws IllegalAccessException, InvocationTargetException, KXWPValidException {
    AnnouncementResponse announcementResponse = new AnnouncementResponse();
    
    SsAnnouncement ssAnnouncement = announcementService.get(id);
    
    BeanUtilsBean2.getInstance().copyProperties(announcementResponse, ssAnnouncement);
    
    //查询服务站名称
    ServiceStation serviceStation = fWZMapper.selectByPrimaryKey(ssAnnouncement.getServiceStationId());
    
    announcementResponse.setServiceStationName(serviceStation.getServiceStationName());
    
    //查询图片
    List<String> photoList = this.getPhotos(announcementResponse, OSSImgStyleEnum.announcement_694_300);
    
    if(photoList.size() != 0){
      announcementResponse.setPictureUrl(photoList.get(photoList.size()-1));
    }
    return announcementResponse;
  }
}
