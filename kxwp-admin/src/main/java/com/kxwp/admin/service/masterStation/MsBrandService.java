package com.kxwp.admin.service.masterStation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxwp.admin.constants.BrandStatusEnum;
import com.kxwp.admin.entity.masterStation.MsBrand;
import com.kxwp.admin.mapper.masterStation.MsBrandMapper;
import com.kxwp.admin.query.masterStation.MsBrandQuery;
import com.kxwp.common.model.exception.KXWPValidException;
import com.kxwp.common.model.oss.BucketNameEnum;
import com.kxwp.common.query.file.OSSFileQuery;
import com.kxwp.common.service.core.KXWPFileService;

/**
 * 总站品牌Service date: 2016年8月18日 下午4:37:08
 * 
 * @author Liuzibing
 */
@Service
public class MsBrandService {
  @Autowired
  private MsBrandMapper msBrandMapper;
  @Autowired
  private KXWPFileService fileService;
  /**
   * find:(条件查询/显示全部). 2016年8月18日 下午4:35:32
   * 
   * @author Liuzibing
   * @param msBrandQuery
   * @return
   * @throws KXWPValidException 
   */
  public List<MsBrand> findBycontion(MsBrandQuery msBrandQuery) throws KXWPValidException {
    if (msBrandQuery.getBrandName() == "" || msBrandQuery.getBrandNameAbbr() == "") {
      msBrandQuery.setBrandName(null);
      msBrandQuery.setBrandNameAbbr(null);
    }
    if(msBrandQuery.getBrandNo()==""){
      msBrandQuery.setBrandNo(null);
    }
    List<MsBrand> msBrands = msBrandMapper.selectByCondition(msBrandQuery);
    for(MsBrand msBrand:msBrands)
    {
      msBrand.setPhotourl(this.getPhoto(msBrand));
    }
    return msBrands;
  }

  /**
   * countByQuery:(查询符合条件的数据的数量). 2016年8月19日 上午10:45:04
   * 
   * @author Liuzibing
   * @param msBrandQuery
   * @return
   */
  public int countByQuery(MsBrandQuery msBrandQuery) {
    int total = msBrandMapper.countByQuery(msBrandQuery);
    return total;
  }
  
  
  /**
   * addBrand:(执行添加品牌方法).
   * 2016年8月20日 下午1:49:15
   * @author Liuzibing
   * @param msBrand
   */
  public void addBrand(MsBrand msBrand)throws Exception {
      this.brandNameAlive(msBrand.getBrandName());
      msBrand.setBrandNameAbbr(msBrand.getBrandName().toLowerCase());
      msBrand.setBrandStatus(BrandStatusEnum.OFFSALE);
      int key=msBrandMapper.insert(msBrand);
      if(key<1){
        throw new Exception("品牌添加失败，请联系管理员");
      }
  }
  
    /**
     * getPhoto:(获取图片地址).
     * 2016年8月22日 下午5:56:19
     * @author Liuzibing
     * @param msBrand
     * @return
     * @throws KXWPValidException 
     */
    public String getPhoto(MsBrand msBrand) throws KXWPValidException {
      OSSFileQuery ossFileQuery = new OSSFileQuery();
      ossFileQuery.setBucketName(BucketNameEnum.brand);
      ossFileQuery.setKey_id(msBrand.getBrandNo());
      
      List<String> temp = fileService.searchFileURL(ossFileQuery);
      if(temp.size()==0 ||temp==null){
        return "";
      }
      return temp.get(0);
    }
  
  /**
   * get:(根据ID获取品牌信息).
   * 2016年8月22日 上午10:34:48
   * @author Liuzibing
   * @param id
   * @return
   */
  public MsBrand get(Long id)throws Exception{
      MsBrand msBrand = msBrandMapper.selectByPrimaryKey(id);
      msBrand.setPhotourl(this.getPhoto(msBrand));
      return  msBrand;
  }
  
  /**
   * modify:(修改品牌信息).
   * 2016年8月22日 上午10:38:41
   * @author Liuzibing
   * @param msBrand
   */
  public void modify(MsBrand msBrand) throws Exception{
     int key = msBrandMapper.updateByPrimaryKeySelective(msBrand);
     if(key<1){
       throw new Exception("修改品牌失败");
     }
  }
  
  /**
   * changeStatus:(修改品牌存在状态).
   * 2016年8月23日 下午3:19:12
   * @author Liuzibing
   * @param msBrand
   */
  public void changeStatus(MsBrand msBrand)
  {
      msBrandMapper.updateByPrimaryKeySelective(msBrand);
  }
  
  /**
   * brandNameAlive:(查找是否已存在相关品牌)
   * 2016年8月23日 上午10:52:54
   * @author Liuzibing
   * @param msBrand
   * @return
   * @throws Exception
   */
  public int brandNameAlive(String brandName) throws Exception
  {
    int key=msBrandMapper.selectDuplicate(brandName);
    if(key!=0){
      throw new Exception("已存在相关品牌");
    }
    else{
      return key;
    }
  }
}
