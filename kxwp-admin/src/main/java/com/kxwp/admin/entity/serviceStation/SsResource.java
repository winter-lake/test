package com.kxwp.admin.entity.serviceStation;

import java.io.Serializable;
import java.util.List;

import com.kxwp.admin.entity.masterStation.MsResource;

public class SsResource extends MsResource implements Serializable{
  private static final long serialVersionUID = -183488997900061284L;
  
  private List<SsResource> ssResources;

  /**
   * @return  ssResources
  
   */
  public List<SsResource> getSsResources() {
    return ssResources;
  }

  public void setSsResources(List<SsResource> ssResources) {
    this.ssResources = ssResources;
  }
  
}