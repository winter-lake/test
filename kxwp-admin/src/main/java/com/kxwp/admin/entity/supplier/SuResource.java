package com.kxwp.admin.entity.supplier;

import java.io.Serializable;
import java.util.List;

import com.kxwp.admin.entity.serviceStation.SsResource;

public class SuResource extends SsResource implements Serializable{
  private static final long serialVersionUID = -183488997900061284L;
  
  private List<SuResource> suResources;

  /**
   * @return  suResources
  
   */
  public List<SuResource> getSuResources() {
    return suResources;
  }

  public void setSuResources(List<SuResource> suResources) {
    this.suResources = suResources;
  }

}