package com.kxwp.admin.service.supplier;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.kxwp.admin.entity.serviceStation.ServiceStation;
import com.kxwp.admin.form.gys.CreateGoodsForm;
import com.kxwp.admin.model.ms.UserModel;
import com.kxwp.common.constants.CachekeyPrefix;
import com.kxwp.common.constants.GoodsStatusEnum;
import com.kxwp.common.constants.KXWPNumberRuleEnum;
import com.kxwp.common.constants.YNEnum;
import com.kxwp.common.constants.goods.PackageSpeciEnum;
import com.kxwp.common.constants.goods.ReturnPolicyEnum;
import com.kxwp.common.entity.MSBrand;
import com.kxwp.common.entity.MSClassificaion;
import com.kxwp.common.entity.supplier.Goods;
import com.kxwp.common.entity.supplier.GoodsCategory;
import com.kxwp.common.entity.supplier.GoodsLotPrice;
import com.kxwp.common.entity.supplier.GoodsShippingArea;
import com.kxwp.common.mapper.MSBrandMapper;
import com.kxwp.common.mapper.MSClassificaionMapper;
import com.kxwp.common.mapper.supplier.GoodsCategoryMapper;
import com.kxwp.common.mapper.supplier.GoodsLotPriceMapper;
import com.kxwp.common.mapper.supplier.GoodsMapper;
import com.kxwp.common.mapper.supplier.GoodsShippingAreaMapper;
import com.kxwp.common.model.exception.ForbiddenException;
import com.kxwp.common.model.exception.KXWPNotFoundException;
import com.kxwp.common.model.exception.KXWPValidException;
import com.kxwp.common.model.exception.SYSException;
import com.kxwp.common.query.goods.SearchGoodsRepoQuery;
import com.kxwp.common.query.sys.OddNumberQuery;
import com.kxwp.common.service.core.AbstractCacheService;
import com.kxwp.common.service.core.OddNumberService;
import com.kxwp.common.service.goods.GoodsRepoService;
import com.kxwp.common.utils.JacksonUtils;
import com.kxwp.common.utils.KXWPDatetimeUtils;
import com.kxwp.common.utils.KXWPLogUtils;

/**
 * 供应商商品维护模块 date: 2016年8月14日 下午9:51:32
 *
 * @author janwen
 */
@Service("SupplierManageGoodsService")
public class SupplierManageGoodsService {


  @Autowired
  private GoodsMapper goodsMapper;

  @Autowired
  private GoodsCategoryMapper goodsCategoryMapper;
  
  @Autowired
  private GoodsShippingAreaMapper goodsShippingAreaMapper;
  
  @Autowired
  private GoodsRepoService goodsRepoService;

  @Autowired
  private GoodsLotPriceMapper goodsLotPriceMapper;

  @Autowired
  private MSBrandMapper msBrandMapper;

  @Autowired
  private OddNumberService oddNumberService;

  @Autowired
  private MSClassificaionMapper msClassificaionMapper;
  
  @Autowired
  private AbstractCacheService cacheService;


  // 模拟生成商品
  @Async
  public void mockAddGoods() throws SYSException {
    for (int i = 0; i < 100; i++) {
      CreateGoodsForm form = new CreateGoodsForm();
      Goods goods = new Goods();
      goods.setBarcode(RandomStringUtils.randomNumeric(8));
      MSBrand brand = msBrandMapper.selectByPrimaryKey(RandomUtils.nextLong(1, 30));
      UserModel user = new UserModel();
      // 供应商登录账号Id
      user.setId(1L);
      // 供应商id
      user.setOrganizationId(1L);
      ServiceStation ss = new ServiceStation();
      // 服务站id
      ss.setId(10L);
      user.setServiceStation(ss);
      if (brand == null) {
        {
          brand = msBrandMapper.selectByPrimaryKey(RandomUtils.nextLong(1, 100));
        }
      }
      goods.setBrandId(brand.getId());
      goods
          .setGoodsName("商品名称_" + KXWPDatetimeUtils.getNowTimestamp(KXWPDatetimeUtils.MMDDHHMMssSSS)
              + RandomStringUtils.randomNumeric(2));
      OddNumberQuery oddNumberQuery = new OddNumberQuery();
      oddNumberQuery.setNumber_type(KXWPNumberRuleEnum.GOODS_NO);
      goods.setGoodsNo(oddNumberService.newOddNumberViaProcedure(oddNumberQuery));
      goods.setGoodsStatus(GoodsStatusEnum.ONSALE);
      goods.setListOrder(RandomUtils.nextLong(1, 100));
      goods.setLotsPrice(YNEnum.N);
      goods.setServiceStationId(user.getServiceStation().getId());
      goods.setMinPurchased(RandomUtils.nextLong(1, 20));
      goods.setPackageSpecific("250ml*10*" + PackageSpeciEnum.BA);
      goods.setProductDate("2016-07-12");
      goods.setReturnPolicy(ReturnPolicyEnum.EXCHANGE);
      goods.setSalePrice(new BigDecimal(RandomUtils.nextDouble(1, 100)));
      goods.setShelfLife("保质期30天");
      goods.setSuggestRetailPrice(new BigDecimal(RandomUtils.nextDouble(1, 100)));
      form.setGoodsInfo(goods);
      List<MSClassificaion> yijifenleiList = msClassificaionMapper.selectByParentID(0L);
      MSClassificaion yijifenlei =
          yijifenleiList.get(RandomUtils.nextInt(0, yijifenleiList.size() - 1));
      GoodsCategory _yijifenlei = new GoodsCategory();
      _yijifenlei.setCategoryId(yijifenlei.getId());
      List<MSClassificaion> zijifenleiList =
          msClassificaionMapper.selectByParentID(yijifenlei.getId());
      MSClassificaion erjifenlei = msClassificaionMapper.selectByPrimaryKey(
          zijifenleiList.get(RandomUtils.nextInt(0, zijifenleiList.size())).getId());
      GoodsCategory _erjifenlei = new GoodsCategory();
      _erjifenlei.setCategoryId(erjifenlei.getId());
      List<MSClassificaion> _zijifenleiList =
          msClassificaionMapper.selectByParentID(erjifenlei.getId());
      MSClassificaion sanjifenlei = msClassificaionMapper.selectByPrimaryKey(
          _zijifenleiList.get(RandomUtils.nextInt(0, _zijifenleiList.size() - 1)).getId());
      GoodsCategory _sanjifenlei = new GoodsCategory();
      _sanjifenlei.setCategoryId(sanjifenlei.getId());
      List<GoodsCategory> fenleiList = new ArrayList<>();
      fenleiList.add(_yijifenlei);
      fenleiList.add(_erjifenlei);
      fenleiList.add(_sanjifenlei);
      form.setGoodsCategoryList(fenleiList);
      addGoods(form, user);
      KXWPLogUtils.logInfo(this.getClass(), "Mock Goods Data " + JacksonUtils.writeValue(form));
    }

  }

  /**
   * 
   * listGoods:(供应商商品列表).
   *
   * 2016年8月16日 下午9:19:30
   * 
   * @author lou jian wen
   * @param query
   * @return
   * @throws KXWPNotFoundException 
   * @throws KXWPValidException 
   */
  public List<Goods> listGoods(SearchGoodsRepoQuery query) throws KXWPNotFoundException, KXWPValidException {
    List<Goods> results = goodsMapper.searchGoodsRepo(query);
    List<Goods> _results = new ArrayList<>();
    for(Goods goods:results){
      _results.add(goodsRepoService.getGoodsByID(goods.getId()));
    }
    return _results;
  }
  
  
  /**
   * 
   * countSearchGoods:(统计总数).
   *
   * 2016年8月17日 下午9:25:42
   * @author lou jian wen
   * @param query
   * @return
   * @throws KXWPNotFoundException
   */
  public int countSearchGoods(SearchGoodsRepoQuery query) throws KXWPNotFoundException {
    return goodsMapper.countByQuery(query);
  }
  
  
  /**
   * 
   * editGoods:(编辑商品).
   *
   * 2016年8月26日 下午5:55:12
   * @author lou jian wen
   * @param form
   * @param user
   * @throws ForbiddenException 
   */
  public void editGoods(CreateGoodsForm form, UserModel user) throws ForbiddenException {
    Date now = new Date();
    
    //商品归属判断
    Goods _goods = goodsMapper.selectByPrimaryKey(form.getGoodsInfo().getId());
    if(_goods.getServiceStationId().longValue() != user.getServiceStation().getId()){
      throw new ForbiddenException(_goods.getId() + "");
    }
    
    // 添加商品基本信息
    Goods goods = form.getGoodsInfo();
    goods.setUpdateTime(now);
    goods.setCreateUserId(user.getId());
    //如果是阶梯价,最小价格为平台价
    if (goods.getLotsPrice() == YNEnum.Y){
      List<GoodsLotPrice> goodsLotPriceList = form.getGoodsLotPriceList();
      BigDecimal sale_price = goodsLotPriceList.get(0).getLotPrice();
      goods.setSalePrice(sale_price);
    }
    goodsMapper.updateByPrimaryKeySelective(goods);
    
    // 添加分类信息
    List<GoodsCategory> goodsCategories = form.getGoodsCategoryList();
    //删除旧的分类信息
    goodsCategoryMapper.deleteByGoodsID(goods.getId());
    for (GoodsCategory goods_category : goodsCategories) {
      goods_category.setBrandId(goods.getBrandId());
      goods_category.setCreateTime(now);
      goods_category.setUpdateTime(now);
      goods_category.setGoodsId(goods.getId());
      goods_category.setGoodsName(goods.getGoodsName());
      goods_category.setSalePrice(goods.getSalePrice());
      goods_category.setGoodsStatus(goods.getGoodsStatus());
      goods_category.setIsLots(goods.getLotsPrice());
      goods_category.setServiceStationId(user.getServiceStation().getId());
      goods_category.setSold(_goods.getSold());
      goods_category.setSupplierId(user.getOrganizationId());
      goods_category.setVersionno(_goods.getVersionNO() + 1);
      goods_category.setListOrder(goods.getListOrder());
      goodsCategoryMapper.insertSelective(goods_category);
    }

    // 配送范围
    List<Long> goodsShippingAreaIDList = form.getGoodsShippingAreaIDList();
    // 删除旧的配送范围信息
    goodsShippingAreaMapper.deleteByGoodsID(goods.getId());
    for(Long area_id:goodsShippingAreaIDList){
      
      GoodsShippingArea goodsShippingArea = new GoodsShippingArea();
      goodsShippingArea.setGoodsId(goods.getId());
      goodsShippingArea.setCreateTime(now);
      goodsShippingArea.setUpdateTime(now);
      goodsShippingArea.setFwzId(user.getServiceStation().getId());
      goodsShippingArea.setSupplierId(user.getOrganizationId());
      goodsShippingArea.setRegionId(area_id);
      goodsShippingArea.setZzId(user.getServiceStation().getMasterStationId());
      goodsShippingAreaMapper.insertSelective(goodsShippingArea);
    }

    // 区域价格
    if (goods.getLotsPrice() == YNEnum.Y) {
      List<GoodsLotPrice> goodsLotPriceList = form.getGoodsLotPriceList();
      //删除旧的配送价格
      goodsLotPriceMapper.deleteByGoodsID(_goods.getId());
      for (GoodsLotPrice lots_price : goodsLotPriceList) {
        lots_price.setCreateTime(now);
        lots_price.setGoodsId(goods.getId());
        lots_price.setServiceStationId(user.getServiceStation().getId());
        lots_price.setSupplierId(user.getOrganizationId());
        lots_price.setUpdateTime(now);
        lots_price.setVersionno(_goods.getVersionNO());
        goodsLotPriceMapper.insert(lots_price);
      }
    }
 // 更新商品缓存信息
    cacheService.removeCache(CachekeyPrefix.ADMIN_GYS_GOODS_INFO + goods.getId());
  }

  public void addGoods(CreateGoodsForm form, UserModel user) {
    Date now = new Date();
    // 添加商品基本信息
    Goods goods = form.getGoodsInfo();
    goods.setCreateTime(now);
    goods.setUpdateTime(now);
    goods.setCreateUserId(user.getId());
    goods.setSupplierId(user.getOrganizationId());
    goods.setServiceStationId(user.getServiceStation().getId());
    //如果是阶梯价,最小价格为平台价
    if (goods.getLotsPrice() == YNEnum.Y){
      List<GoodsLotPrice> goodsLotPriceList = form.getGoodsLotPriceList();
      BigDecimal sale_price = goodsLotPriceList.get(0).getLotPrice();
      goods.setSalePrice(sale_price);
    }
    goodsMapper.insertSelective(goods);
    
    // 添加分类信息
    List<GoodsCategory> goodsCategories = form.getGoodsCategoryList();
    for (GoodsCategory goods_category : goodsCategories) {
      goods_category.setBrandId(goods.getBrandId());
      goods_category.setCreateTime(now);
      goods_category.setUpdateTime(now);
      goods_category.setGoodsId(goods.getId());
      goods_category.setGoodsName(goods.getGoodsName());
      goods_category.setSalePrice(goods.getSalePrice());
      goods_category.setGoodsStatus(goods.getGoodsStatus());
      goods_category.setIsLots(goods.getLotsPrice());
      goods_category.setServiceStationId(user.getServiceStation().getId());
      goods_category.setSold(0L);
      goods_category.setSupplierId(user.getOrganizationId());
      goods_category.setVersionno(0L);
      goods_category.setListOrder(goods.getListOrder());
      goodsCategoryMapper.insertSelective(goods_category);
    }

    // 配送范围
    List<Long> goodsShippingAreaIDList = form.getGoodsShippingAreaIDList();
    for(Long area_id:goodsShippingAreaIDList){
      GoodsShippingArea goodsShippingArea = new GoodsShippingArea();
      goodsShippingArea.setGoodsId(goods.getId());
      goodsShippingArea.setCreateTime(now);
      goodsShippingArea.setUpdateTime(now);
      goodsShippingArea.setFwzId(user.getServiceStation().getId());
      goodsShippingArea.setSupplierId(user.getOrganizationId());
      goodsShippingArea.setRegionId(area_id);
      goodsShippingArea.setZzId(user.getServiceStation().getMasterStationId());
      goodsShippingAreaMapper.insertSelective(goodsShippingArea);
    }

    // 区域价格
    if (goods.getLotsPrice() == YNEnum.Y) {
      List<GoodsLotPrice> goodsLotPriceList = form.getGoodsLotPriceList();
      for (GoodsLotPrice lots_price : goodsLotPriceList) {
        lots_price.setCreateTime(now);
        lots_price.setGoodsId(goods.getId());
        lots_price.setServiceStationId(user.getServiceStation().getId());
        lots_price.setSupplierId(user.getOrganizationId());
        lots_price.setUpdateTime(now);
        lots_price.setVersionno(0L);
        goodsLotPriceMapper.insert(lots_price);
      }
    }
  }
}
