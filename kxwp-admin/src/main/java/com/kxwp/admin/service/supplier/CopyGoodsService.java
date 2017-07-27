package com.kxwp.admin.service.supplier;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.kxwp.admin.entity.serviceStation.ServiceStation;
import com.kxwp.admin.mapper.serviceStation.ServiceStationMapper;
import com.kxwp.common.constants.KXWPNumberRuleEnum;
import com.kxwp.common.constants.YNEnum;
import com.kxwp.common.constants.config.ProjectConfig;
import com.kxwp.common.entity.SYSRegion;
import com.kxwp.common.entity.supplier.Goods;
import com.kxwp.common.entity.supplier.GoodsCategory;
import com.kxwp.common.entity.supplier.GoodsExample;
import com.kxwp.common.entity.supplier.GoodsLotPrice;
import com.kxwp.common.entity.supplier.GoodsShippingArea;
import com.kxwp.common.entity.supplier.Supplier;
import com.kxwp.common.form.file.FileListSummary;
import com.kxwp.common.form.goods.CopyGoodsForm;
import com.kxwp.common.mapper.supplier.GoodsCategoryMapper;
import com.kxwp.common.mapper.supplier.GoodsLotPriceMapper;
import com.kxwp.common.mapper.supplier.GoodsMapper;
import com.kxwp.common.mapper.supplier.GoodsShippingAreaMapper;
import com.kxwp.common.model.exception.KXWPNotFoundException;
import com.kxwp.common.model.exception.SYSException;
import com.kxwp.common.model.oss.BucketNameEnum;
import com.kxwp.common.query.file.OSSFileQuery;
import com.kxwp.common.query.goods.SearchGoodsRepoQuery;
import com.kxwp.common.query.sys.OddNumberQuery;
import com.kxwp.common.query.sys.RegionQuery;
import com.kxwp.common.service.core.KXWPFileService;
import com.kxwp.common.service.core.OddNumberService;
import com.kxwp.common.service.core.RegionService;
import com.kxwp.common.service.supplier.SupplierRepoService;
import com.kxwp.common.utils.KXWPDatetimeUtils;
import com.kxwp.common.utils.KXWPEncryptUtils;
import com.kxwp.common.utils.KXWPLogUtils;


/**
 * 商品库的相关搜索 date: 2016年9月13日 上午12:20
 *
 * @author zhaojn
 */
@Service("CopyGoodsService")
public class CopyGoodsService {

  @Autowired
  private SupplierRepoService supplierRepoService;

  @Autowired
  private GoodsShippingAreaMapper goodsShippingAreaMapper;

  @Autowired
  private GoodsMapper goodsMapper;

  @Autowired
  private GoodsCategoryMapper goodsCategoryMapper;

  @Autowired
  private OddNumberService oddNumberService;

  @Autowired
  private KXWPFileService kxwpFileService;

  @Autowired
  private ServiceStationMapper serviceStationMapper;

  @Autowired
  private RegionService regionService;

  @Autowired
  private GoodsLotPriceMapper goodsLotPriceMapper;


  // 添加商品
  public void addGoods(Goods goods) {
    goodsMapper.insert(goods);
  }

  // 添加商品分类搜索信息
  public void addGoodsCategory(GoodsCategory goodsCategory) {
    goodsCategoryMapper.insert(goodsCategory);
  }

  // 添加商品的配送范围
  public void addGoodsShippingArea(GoodsShippingArea goodsShippingArea) {
    goodsShippingAreaMapper.insert(goodsShippingArea);
  }

  //生成新的file_key
  public String createObjectKey(String goodsNo, String file_key) throws SYSException, IOException {
    String suffix = file_key.substring(file_key.indexOf("."));
    String nowTimestamp = KXWPDatetimeUtils.getNowTimestamp(KXWPDatetimeUtils.YYYYMMDDHHMMssSSS)
        + "_" + RandomStringUtils.randomAlphanumeric(2);
    String copy_file_key = ProjectConfig.app_env + "/" + BucketNameEnum.goodsmain.name() + "/"
        + goodsNo + "_" + KXWPEncryptUtils.getMD5String(nowTimestamp) + suffix;
    return copy_file_key;
  }


  /**
   * 
   * getRecommendGoods:(复制商品).
   *
   * 2016年9月13日 上午12:20
   * 
   * @author zhao jia nan
   * @param
   * @return
   * @throws KXWPNotFoundException
   * @throws SYSException
   * @throws IOException
   */
  @Async
  public void copyGoods(CopyGoodsForm form)
      throws KXWPNotFoundException, SYSException, IOException {
    if(form.getCopyed_supplierId().longValue() == form.getSupplierId().longValue()){
      throw new SYSException("被复制商品的供应商和复制商品的供应商id不能相同");
    }
    GoodsExample example = new GoodsExample();
    example.createCriteria().andSupplierIdEqualTo(form.getCopyed_supplierId());
    int count = goodsMapper.countByExample(example);

    SearchGoodsRepoQuery query = new SearchGoodsRepoQuery();
    query.setSupplierID(form.getCopyed_supplierId());
    query.getPager().setPageSize(count);
    List<Goods> copy_goods_list = goodsMapper.searchGoodsRepo(query);

    Supplier supplier = supplierRepoService.getSupplierByID(form.getSupplierId());

    for (Goods copy_goods : copy_goods_list) {
      Goods goods = new Goods();
      goods.setGoodsName(copy_goods.getGoodsName());

      // 生成图片编号
      // 放入商品编号
      OddNumberQuery numberQuery = new OddNumberQuery();
      numberQuery.setNumber_type(KXWPNumberRuleEnum.GOODS_NO);
      String goodsNo = oddNumberService.newOddNumberViaProcedure(numberQuery);
      goods.setGoodsNo(goodsNo);
      goods.setSupplierId(supplier.getId());
      goods.setServiceStationId(supplier.getServiceStationId());
      goods.setBrandId(copy_goods.getBrandId());
      goods.setPackageSpecific(copy_goods.getPackageSpecific());
      goods.setMinPurchased(copy_goods.getMinPurchased());
      goods.setSalePrice(copy_goods.getSalePrice());
      goods.setSuggestRetailPrice(copy_goods.getSuggestRetailPrice());
      goods.setShelfLife(copy_goods.getShelfLife());
      goods.setProductDate(copy_goods.getProductDate());
      goods.setReturnPolicy(copy_goods.getReturnPolicy());
      goods.setBarcode(copy_goods.getBarcode());
      goods.setListOrder(copy_goods.getListOrder());
      goods.setGoodsStatus(copy_goods.getGoodsStatus());
      goods.setServiceRemark(copy_goods.getServiceRemark());
      goods.setLotsPrice(copy_goods.getLotsPrice());
      goods.setVersionNO(copy_goods.getVersionNO());
      goods.setCreateUserId(supplier.getId());
      goods.setCreateTime(new Date());
      goods.setUpdateTime(new Date());

      // 添加商品
      addGoods(goods);
      KXWPLogUtils.logInfo(this.getClass(), goods.toString());
      ServiceStation serviceStation =
          serviceStationMapper.selectByPrimaryKey(supplier.getServiceStationId());

      // 复制商品分类信息
      List<GoodsCategory> goodsCategory_list =
          goodsCategoryMapper.selectByGoodsID(copy_goods.getId());
      for (GoodsCategory copyGoodsCategory : goodsCategory_list) {
        GoodsCategory goodsCategory = new GoodsCategory();
        goodsCategory.setGoodsId(goods.getId());
        goodsCategory.setSupplierId(goods.getSupplierId());
        goodsCategory.setServiceStationId(supplier.getServiceStationId());
        goodsCategory.setSalePrice(copyGoodsCategory.getSalePrice());
        goodsCategory.setIsLots(copyGoodsCategory.getIsLots());
        goodsCategory.setCategoryId(copyGoodsCategory.getCategoryId());
        goodsCategory.setBrandId(copyGoodsCategory.getBrandId());
        goodsCategory.setGoodsName(copyGoodsCategory.getGoodsName());
        goodsCategory.setGoodsStatus(copyGoodsCategory.getGoodsStatus());
        goodsCategory.setListOrder(copyGoodsCategory.getListOrder());
        goodsCategory.setCreateTime(new Date());
        goodsCategory.setUpdateTime(new Date());
        goodsCategory.setVersionno(copyGoodsCategory.getVersionno());
        addGoodsCategory(goodsCategory);
        KXWPLogUtils.logInfo(this.getClass(), goodsCategory.toString());
      }

      // 配置商品配送范围
      // 默认为供应商的配送范围
      RegionQuery regionQuery = new RegionQuery();
      regionQuery.setParentRegionID(serviceStation.getCounty().longValue());
      List<SYSRegion> list = regionService.queryCascadeRegion(regionQuery);
      for (SYSRegion sys : list) {
        GoodsShippingArea goodsShippingArea = new GoodsShippingArea();
        goodsShippingArea.setCreateTime(new Date());
        goodsShippingArea.setFwzId(supplier.getServiceStationId());
        goodsShippingArea.setRegionId(sys.getId());
        goodsShippingArea.setSupplierId(supplier.getId());
        goodsShippingArea.setUpdateTime(new Date());
        goodsShippingArea.setZzId(serviceStation.getMasterStationId());
        goodsShippingArea.setGoodsId(goods.getId());
        goodsShippingAreaMapper.insertSelective(goodsShippingArea);
        KXWPLogUtils.logInfo(this.getClass(), goodsShippingArea.toString());
      }

      // 复制商品最低起订价
      if (goods.getLotsPrice() == YNEnum.Y) {
        List<GoodsLotPrice> copy_goodsLotPriceList =
            goodsLotPriceMapper.selectByGoodsId(copy_goods.getId());
        for (GoodsLotPrice copy_goodsLotPrice : copy_goodsLotPriceList) {
          GoodsLotPrice goodsLotPrice = new GoodsLotPrice();
          goodsLotPrice.setGoodsId(goods.getId());
          goodsLotPrice.setSupplierId(goods.getSupplierId());
          goodsLotPrice.setServiceStationId(supplier.getServiceStationId());
          goodsLotPrice.setLotPrice(copy_goodsLotPrice.getLotPrice());
          goodsLotPrice.setMinQit(copy_goodsLotPrice.getMinQit());
          goodsLotPrice.setMaxQit(copy_goodsLotPrice.getMaxQit());
          goodsLotPrice.setCreateTime(new Date());
          goodsLotPrice.setUpdateTime(new Date());
          goodsLotPrice.setVersionno(copy_goodsLotPrice.getVersionno());

          goodsLotPriceMapper.insert(goodsLotPrice);
          KXWPLogUtils.logInfo(this.getClass(), goodsLotPrice.toString());
        }
      }


      /*************** 复制图片 *******************/
      OSSFileQuery ossFileQuery = new OSSFileQuery();
      ossFileQuery.setBucketName(BucketNameEnum.goodsmain);
      ossFileQuery.setKey_id(copy_goods.getGoodsNo());
      FileListSummary result = kxwpFileService.readFileListSummary(ossFileQuery);
      if (result.getFileKeyList() != null && result.getFileKeyList().size() != 0) {
        for (String fileKey : result.getFileKeyList()) {
          OSSFileQuery copy_ossFileQuery = new OSSFileQuery();
          copy_ossFileQuery.setFile_key(fileKey);
          copy_ossFileQuery.setBucketName(BucketNameEnum.goodsmain);
          String new_file_key = createObjectKey(goods.getGoodsNo(), fileKey);
          copy_ossFileQuery.setCopy_file_key(new_file_key);
          kxwpFileService.copyObject(copy_ossFileQuery);
        }
      }
      /**********************************/

    }
    KXWPLogUtils.logInfo(this.getClass(), "************商品复制结束**************");
  }
}
