package com.kxwp.h5.service.shoppingcar;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxwp.common.constants.CheckEnum;
import com.kxwp.common.constants.OSSImgStyleEnum;
import com.kxwp.common.constants.ShoppingStatusEnum;
import com.kxwp.common.constants.YNEnum;
import com.kxwp.common.entity.shoppingcar.AppShoppingCar;
import com.kxwp.common.entity.shoppingcar.ShoppingCar;
import com.kxwp.common.entity.shoppingcar.ShoppingCarExample;
import com.kxwp.common.entity.supermarket.Supermarket;
import com.kxwp.common.entity.supplier.Goods;
import com.kxwp.common.entity.supplier.GoodsLotPrice;
import com.kxwp.common.entity.supplier.Supplier;
import com.kxwp.common.form.shoppingCar.UpdateShoppingCarForm;
import com.kxwp.common.mapper.shoppingcar.ShoppingCarMapper;
import com.kxwp.common.mapper.supplier.GoodsLotPriceMapper;
import com.kxwp.common.mapper.supplier.GoodsMapper;
import com.kxwp.common.model.app.supermarket.APPUserModel;
import com.kxwp.common.model.exception.KXWPNotFoundException;
import com.kxwp.common.model.exception.KXWPValidException;
import com.kxwp.common.model.exception.LoginException;
import com.kxwp.common.model.exception.SYSException;
import com.kxwp.common.model.shopcart.ShopcartItem;
import com.kxwp.common.model.shopcart.UpdateShopcartData;
import com.kxwp.common.model.shopcart.UpdateShopcartResult;
import com.kxwp.common.query.shoppingcar.SelectShoppingCarQuery;
import com.kxwp.common.query.shoppingcar.ShoppingCarCountQuery;
import com.kxwp.common.query.shoppingcar.ShoppingCarQuery;
import com.kxwp.common.query.shoppingcar.SupplierShoppingCarResult;
import com.kxwp.common.query.supplier.GoodsShippingAreaQuery;
import com.kxwp.common.service.core.GoodsFileService;
import com.kxwp.common.service.core.SumAmoutService;
import com.kxwp.common.service.goods.GoodsRepoService;
import com.kxwp.common.service.shoppingcar.CallShoppipngCarListTaskService;
import com.kxwp.common.service.shoppingcar.ShoppingCarService;
import com.kxwp.common.service.supermarket.SuperMarketUserService;
import com.kxwp.common.service.supplier.GoodsShippingAreaService;
import com.kxwp.common.service.supplier.SupplierRepoService;
import com.kxwp.h5.model.shoppingcar.AppShoppingCarConfirmResult;
import com.kxwp.h5.model.shoppingcar.AppShoppingCarResult;
import com.kxwp.h5.model.shoppingcar.AppSupplierShoppingCarResult;

@Service("AppShoppingCarService")
public class AppShoppingCarService {

  @Autowired
  private ShoppingCarService shoppingCarService;

  @Autowired
  private GoodsRepoService goodsRepoService;
  
  @Autowired
  private GoodsShippingAreaService goodsShippingAreaService;  // 判断配送范围

  @Autowired
  private SumAmoutService sumAmoutService;

  @Autowired
  private SuperMarketUserService superMarketUserService;

  @Autowired
  private SupplierRepoService supplierRepoService;

  @Autowired
  private ShoppingCarMapper shoppingCarMapper;

  @Autowired
  private GoodsMapper goodsMapper;

  @Autowired
  private GoodsLotPriceMapper goodsLotPriceMapper;
  
  @Autowired
  private GoodsFileService goodsFileService;
  
  @Autowired
  private CallShoppipngCarListTaskService callShoppipngCarListTaskService;

  static final Long GOODS_TYPE = 1L;
  static final Long SUPPLIER_TYPE = 2L;
  static final Long SUPERMARKET_TYPE = 3L;
  static final String STANDARD_MESSAGE = "不在配送范围内";
  static final String SUCCESS_MESSAGE ="";
  /**
   * addShoppingCar:(app端添加购物车清单 并返回购物车中商品总量).
   *
   * 2016年8月17日 上午10:28:36
   * 
   * @author zhaojn
   * @param query
   * @param model
   * @return
   * @throws LoginException
   * @throws KXWPNotFoundException
   * @throws SYSException 
   * @throws KXWPValidException 
   * 
   */
  public ShoppingCarCountQuery addShoppingCar(ShoppingCarQuery query, APPUserModel model)
      throws KXWPNotFoundException, SYSException, KXWPValidException {

    if (model == null) {
      throw new KXWPNotFoundException("没有找到登录的超市信息");
    }
    
    query.setSupermarketId(model.getSupermarket_id());
    ShoppingCarExample example = new ShoppingCarExample();
    example.createCriteria().andSupermarketIdEqualTo(model.getSupermarket_id())
        .andGoodsIdEqualTo(query.getGoodsId());
    int count = shoppingCarMapper.countByExample(example);
    if (count == 0) {
      shoppingCarService.addShoppingCar(query);
    } else {
      shoppingCarService.updateShoppingCar(query);
    }
    SelectShoppingCarQuery selectShoppingCarQuery = new SelectShoppingCarQuery();
    selectShoppingCarQuery.setSupermarketId(model.getSupermarket_id());
    Long goodsCount = shoppingCarService.shoppingCarCountBySupermarket(selectShoppingCarQuery);
    ShoppingCarCountQuery countQuery = new ShoppingCarCountQuery();
    countQuery.setGoodsCount(goodsCount);
    return countQuery;
  }

  



  /**
    * 
    * addShoppingCar:(加入购物车操作).
    *
    * 2016年8月31日 下午11:03:08
    * 
    * @author lou jian wen
    * @param data
    * @param user
    * @return
    * @throws KXWPNotFoundException
    * @throws SYSException
   * @throws KXWPValidException 
    */
   public UpdateShopcartResult addShoppingCar(UpdateShopcartData data, APPUserModel user)
       throws KXWPNotFoundException, SYSException, KXWPValidException {

     UpdateShopcartResult updateResult = new UpdateShopcartResult();
     Long goodsCount = 0L;
     for (ShopcartItem item : data.getItems()) {
       ShoppingCarQuery query = new ShoppingCarQuery();
       query.setGoodsId(item.getGoodsId());
       query.setSupermarketId(user.getSupermarket_id());
       Goods goods = goodsRepoService.getGoodsByID(item.getGoodsId());
       query.setSupplierId(goods.getSupplierId());
       query.setGoodQit(item.getGoodQit());
       ShoppingCarExample example = new ShoppingCarExample();
       example.createCriteria().andSupermarketIdEqualTo(user.getSupermarket_id())
           .andGoodsIdEqualTo(query.getGoodsId());
       int count = shoppingCarMapper.countByExample(example);
       if (count == 0) {
         shoppingCarService.addShoppingCar(query);
       } else {
         shoppingCarService.updateShoppingCar(query);
       }
     }
     SelectShoppingCarQuery selectShoppingCarQuery = new SelectShoppingCarQuery();
     selectShoppingCarQuery.setSupermarketId(user.getSupermarket_id());
     Long _goodsCount = shoppingCarService.shoppingCarCountBySupermarket(selectShoppingCarQuery);
     goodsCount = goodsCount + _goodsCount;
     updateResult.setGoodsCount(goodsCount);
     return updateResult;
   }



  /**
   * selectShoppingCarGroupBySupplied:(根据超市ID查询购物车清单).
   *
   * 2016年8月17日 下午3:19:24
   * 
   * @author zhaojn
   * @param query
   * @param model
   * @return
   * @throws KXWPNotFoundException
   * @throws KXWPValidException 
   * 
   */
  public AppShoppingCarResult selectShoppingCarGroupBySupplied(APPUserModel model)
      throws KXWPNotFoundException, KXWPValidException {
    SelectShoppingCarQuery query = new SelectShoppingCarQuery();
    if (model == null) {
      throw new KXWPNotFoundException("API selectShoppingCarGroupBySupplied() :查询条件不可以为空");
    }
    AppShoppingCarResult appShoppingCarResult = new AppShoppingCarResult();
    query.setSupermarketId(model.getSupermarket_id());
    List<ShoppingCar> shoppingcarlist = shoppingCarService.selectByQuery(query);
    if (shoppingcarlist == null || shoppingcarlist.size() == 0) {
      return appShoppingCarResult;
    }
    BigDecimal totalAmount = new BigDecimal(0);
    Long totalGoodsCount = 0L;
    Long totalGoodsQit =
        shoppingCarService.shoppingCarCheckCountBySupermarket(model.getSupermarket_id());
    Long allcheck =
        shoppingCarService.valiateShoppingCarStatusBySupermarket(model.getSupermarket_id());
    List<Long> supplierIdList = shoppingCarService.groupBySupplierId(query);

    if (supplierIdList == null || supplierIdList.size() == 0) {
      return appShoppingCarResult;
    }

    Supermarket supermarket = superMarketUserService.getSuperMarketById(model.getSupermarket_id());
    String full_adress = superMarketUserService.searchFullAddress(supermarket).getFull_address();
    appShoppingCarResult.setFull_address(full_adress);
    appShoppingCarResult.setTotalGoodsQit(totalGoodsQit);
    appShoppingCarResult.setAllCheck(allcheck);
    List<SupplierShoppingCarResult> shoppingcarResultList =
        new ArrayList<SupplierShoppingCarResult>();

    // 根据供应商ID 将查询到的数据拼接到对应的返回json中
    for (Long supplierId : supplierIdList) {
      ShoppingCarExample example = new ShoppingCarExample();
      example.createCriteria().andSupermarketIdEqualTo(model.getSupermarket_id())
          .andSupplierIdEqualTo(supplierId).andCheckStatusEqualTo(ShoppingStatusEnum.CHECK);
      // 供应商下的商品种类
      int goodsCount = shoppingCarMapper.countByExample(example);
      Supplier supplier = supplierRepoService.getSupplierByID(supplierId);
      SelectShoppingCarQuery queryCheckStatus = new SelectShoppingCarQuery();
      // 供应商的商品是否被全选 1:全选 0 ：未全选
      Long supplierFlag = shoppingCarService
          .valiateShoppingCarStatusBySupermarketAndSupplier(model.getSupermarket_id(), supplierId);
      queryCheckStatus.setSupplierId(supplierId);
      queryCheckStatus.setSupermarketId(model.getSupermarket_id());

      List<ShoppingCar> Shoppingcar_list = shoppingCarService.selectByQuery(queryCheckStatus);
      //addGoodsMessageIntoShoppingCar(Shoppingcar_list);
      //开启多线程  校验商品条件并将商品信息拼接到shoppingcar中
      /*******/
      Shoppingcar_list = callShoppipngCarListTaskService.run(Shoppingcar_list);
      /*******/
      SupplierShoppingCarResult shoppingcarResult = new SupplierShoppingCarResult();
      Long supplierGoodsQit =
          shoppingCarService.shoppingCarCheckCountBySupplier(model.getSupermarket_id(), supplierId);

      shoppingcarResult.setShoppingCarList(Shoppingcar_list);
      shoppingcarResult.setGoodsCount(goodsCount);
      shoppingcarResult.setMinShippingAmount(supplier.getMinShippingAmount());
      shoppingcarResult.setSupplierAllCheck(supplierFlag);
      shoppingcarResult.setSupplierGoodsQit(supplierGoodsQit);

      BigDecimal suAmount =
          sumAmoutService.SumAmoutBySuperMarketAndSupplier(supplierId, model.getSupermarket_id());
      if (suAmount.compareTo(supplier.getMinShippingAmount()) == -1) {
        shoppingcarResult.setSupplierStandard(CheckEnum.N);
        shoppingcarResult
            .setSupplierMessage("最低起订价为" + supplier.getMinShippingAmount());
      } else {
        shoppingcarResult.setSupplierStandard(CheckEnum.Y);
        shoppingcarResult.setSupplierMessage("");
      }
      shoppingcarResult.setSupplierAmount(suAmount);
      shoppingcarResult.setSupplierId(supplierId);
      shoppingcarResult.setSupplierName(supplier.getSupplierName());
      shoppingcarResultList.add(shoppingcarResult);
      totalAmount = totalAmount.add(suAmount);
      totalGoodsCount = totalGoodsCount + goodsCount;
    }
    appShoppingCarResult.setShoppingcarResult(shoppingcarResultList);
    appShoppingCarResult =
        validatePlatFormStandardAndAddMessageInfo(appShoppingCarResult, totalAmount);
    appShoppingCarResult.setSupermarketName(supermarket.getSupermarketName());
    appShoppingCarResult.setTotalAmount(totalAmount);
    appShoppingCarResult.setTotalGoodsCount(totalGoodsCount);

    return appShoppingCarResult;
  }


  /**
   * appAddGoodsMessageIntoShoppingCar:(私有方法 ，将插入的商品相关信息封装一样).
   *
   * 2016年8月22日 下午3:31:54
   * @author zhaojn
   * @param list
   * @return
   * @throws KXWPNotFoundException
   * @throws KXWPValidException 
  
   */
  private List<AppShoppingCar> appAddGoodsMessageIntoShoppingCar(List<AppShoppingCar> list) throws KXWPNotFoundException, KXWPValidException{
    if (list == null || list.size() == 0) {
      throw new KXWPNotFoundException("appAddGoodsMessageIntoShoppingCar()  购物车list为空");
    }
    for(AppShoppingCar appShoppingCar:list){
      Goods good = goodsRepoService.getGoodsWithThumbnail(appShoppingCar.getGoodsId(), OSSImgStyleEnum.goods_200_200);
      appShoppingCar.setGoodsName(good.getGoodsName());
      appShoppingCar.setPackageSpecific(good.getPackageSpecific());
      appShoppingCar.setPhotoList(good.getPhotoList());
      BigDecimal sum = sumAmoutService.SumAmoutByGoods(appShoppingCar.getGoodsId(), appShoppingCar.getGoodQit());
      appShoppingCar.setGoodsAmount(sum);
      ShoppingCar sc = shoppingCarMapper.selectByPrimaryKey(appShoppingCar.getId());
      salePriceToLotPrice(sc);
      appShoppingCar.setSalePrice(sc.getSalePrice());
    }
    return list;
  }
  /**
   * addGoodsMessageIntoShoppingCar:(私有方法,将商品信息放入shoppingcar中的逻辑封装一下 不然看着难受).
   *
   * 2016年8月20日 下午5:50:53
   * @author zhaojn
   * @param list
   * @return
   * @throws KXWPNotFoundException
   * @throws KXWPValidException 
  
   */
  private List<ShoppingCar> addGoodsMessageIntoShoppingCar(List<ShoppingCar> list)
      throws KXWPNotFoundException, KXWPValidException {
    if (list == null || list.size() == 0) {
      throw new KXWPNotFoundException("addGoodsMessageIntoShoppingCar()  购物车list为空");
    }
    for (ShoppingCar sc : list) {
      Goods good = goodsRepoService.getGoodsByID(sc.getGoodsId());
      sc.setPackageSpecific(good.getPackageSpecific());
      sc.setGoodsName(good.getGoodsName());
      sc.setSalePrice(good.getSalePrice());
      sc.setMinPurchased(good.getMinPurchased());
      sc.setGoodsName(good.getGoodsName());
      List<String> photoList = goodsFileService.getGoodsPhotos(good);
      sc.setPhotoList(photoList);
      
      GoodsShippingAreaQuery goodsShippingAreaQuery = new GoodsShippingAreaQuery();
      goodsShippingAreaQuery.setGoods_id(good.getId());
      Supermarket supermarket =
          superMarketUserService.getSuperMarketById(sc.getSupermarketId());
      goodsShippingAreaQuery.setRegion_id(Long.valueOf(supermarket.getTown()));
      if (goodsShippingAreaService.isInShippingArea(goodsShippingAreaQuery) == false){
        sc.setGoodsMessage(good.getGoodsName()+STANDARD_MESSAGE);
        sc.setGoodsStandard(CheckEnum.N);
      }
      BigDecimal sum = sumAmoutService.SumAmoutByGoods(sc.getGoodsId(), sc.getGoodQit());
      sc.setGoodsAmount(sum);
      salePriceToLotPrice(sc);
      // 判断配送范围
      if (goodsShippingAreaService.isInShippingArea(goodsShippingAreaQuery) == false) {
        sc.setGoodsMessage(good.getGoodsName() + STANDARD_MESSAGE);
        sc.setGoodsStandard(CheckEnum.N);
      } else if (sc.getGoodQit() < good.getMinPurchased()) {
        sc.setGoodsStandard(CheckEnum.N);
        if (StringUtils.isEmpty(sc.getGoodsMessage())) {
          sc.setGoodsMessage("最低起送量为" + good.getMinPurchased());
        }
      } else {
        sc.setGoodsStandard(CheckEnum.Y);
        sc.setGoodsMessage("");
      }
    }
    return list;
  }


  /**
   * salePriceToLotPrice:(将购物车清单中的销售价格变成阶梯价).
   *
   * 2016年8月18日 下午1:48:58
   * 
   * @author zhaojn
   * @param shoppingCar
   * @return
   * 
   */
  private ShoppingCar salePriceToLotPrice(ShoppingCar shoppingCar) {
    Goods good = goodsMapper.selectByPrimaryKey(shoppingCar.getGoodsId());
    // 判断该商品是否存在阶梯价或批发价
    if (good.getLotsPrice() == YNEnum.Y) {
      List<GoodsLotPrice> goodsLotPriceList =
          goodsLotPriceMapper.selectByGoodsId(shoppingCar.getGoodsId());
      if (goodsLotPriceList == null || goodsLotPriceList.size() == 0) {
        shoppingCar.setSalePrice(good.getSalePrice());
        return shoppingCar;
      }
      
      Long MaxQit = 0L;
      GoodsLotPrice maxGlp = new GoodsLotPrice();
      for (GoodsLotPrice glp : goodsLotPriceList) {
        if (glp.getMaxQit() > MaxQit) {
          MaxQit = glp.getMaxQit();
          maxGlp = glp;
        }
        if (shoppingCar.getGoodQit() >= glp.getMinQit() && shoppingCar.getGoodQit() <= glp.getMaxQit()) {
          shoppingCar.setSalePrice(glp.getLotPrice());
          return shoppingCar;
        }
      }

      if (MaxQit < shoppingCar.getGoodQit()) {
        shoppingCar.setSalePrice(maxGlp.getLotPrice());
        return shoppingCar;
      }
    } else {
      shoppingCar.setSalePrice(good.getSalePrice());
    }
    return shoppingCar;
  }


  /**
   * shoppingCarCountBySupermarket:(查询超市购物车清单中商品的总件数).
   *
   * 2016年8月18日 上午11:38:12
   * 
   * @author zhaojn
   * @param model
   * @return
   * @throws
   */
  public ShoppingCarCountQuery shoppingCarCountBySupermarket(APPUserModel model)
      throws KXWPNotFoundException {
    if (model == null) {
      throw new KXWPNotFoundException("没有获取到登录信息");
    }
    SelectShoppingCarQuery query = new SelectShoppingCarQuery();
    query.setSupermarketId(model.getSupermarket_id());
    Long goodsCount = shoppingCarService.shoppingCarCountBySupermarket(query);
    ShoppingCarCountQuery countQuery = new ShoppingCarCountQuery();
    countQuery.setGoodsCount(goodsCount);
    return countQuery;
  }


  /**
   * updateShoppingCarByOperate:(app更新购物车数据状态).
   *
   * 2016年8月19日 下午5:41:29
   * 
   * @author zhaojn
   * @param form
   * @param model
   * @return
   * @throws KXWPNotFoundException
   * @throws KXWPValidException 
   * 
   */
  public AppShoppingCarResult updateShoppingCarByOperate(UpdateShoppingCarForm form,
      APPUserModel model) throws KXWPNotFoundException, KXWPValidException {
    if (model == null) {
      throw new KXWPNotFoundException("没有获取到登录信息");
    }
    AppShoppingCarResult result = new AppShoppingCarResult();
    form.setSupermarketId(model.getSupermarket_id());
    
    // 对商品操作
    if (form.getOperate_type() == GOODS_TYPE) {
      shoppingCarService.updateShoppingCarGoods(form);
      result = selectShoppingCarGroupBySupplied(model);
    }

    // 对订单操作(全选/全不选)
    if (form.getOperate_type() == SUPERMARKET_TYPE) {
      shoppingCarService.updateSupermarketAllCheck(form);
      result = selectShoppingCarGroupBySupplied(model);
    }

    // 对供应商操作(全选/全不选)
    if (form.getOperate_type() == SUPPLIER_TYPE) {
      shoppingCarService.updateSupplierAllCheck(form);
      result = selectShoppingCarGroupBySupplied(model);
    }

    return result;
  }
  
  
  /**
   * confirmShoppingCar:(购物车确认页面详细数据).
   *
   * 2016年8月22日 下午3:25:16
   * @author zhaojn
   * @param model
   * @return
   * @throws KXWPNotFoundException
   * @throws KXWPValidException 
  
   */
  public AppShoppingCarConfirmResult confirmShoppingCar(APPUserModel model) throws KXWPNotFoundException, KXWPValidException{
    if (model == null) {
      throw new KXWPNotFoundException("confirmShoppingCar() 没有获取到超市登录信息");
    }
    AppShoppingCarConfirmResult result = new AppShoppingCarConfirmResult();
    //查询超市相关信息 放入返回结果中
    Supermarket supermarket = superMarketUserService.getSuperMarketById(model.getSupermarket_id());
    String full_adress = superMarketUserService.searchFullAddress(supermarket).getFull_address();
    Long phone = supermarket.getPhone();
    SelectShoppingCarQuery selectSupplierIdQuery = new SelectShoppingCarQuery();
    selectSupplierIdQuery.setSupermarketId(model.getSupermarket_id());
    List<Long> supplierIdList = shoppingCarService.groupBySupplierId(selectSupplierIdQuery);
    BigDecimal totalAmount = new BigDecimal(0);
    Long totalGoodsCount = 0L;
    //向超市中放入供应商列表的相关信息
    List<AppSupplierShoppingCarResult> supplierResultList = new ArrayList<AppSupplierShoppingCarResult>();
    for(Long supplierId:supplierIdList){
      AppSupplierShoppingCarResult supplierResult = new AppSupplierShoppingCarResult();
      supplierResult.setSupplierId(supplierId);
      ShoppingCarExample example = new ShoppingCarExample();
      example.createCriteria().andSupermarketIdEqualTo(model.getSupermarket_id())
          .andSupplierIdEqualTo(supplierId).andCheckStatusEqualTo(ShoppingStatusEnum.CHECK);
      
      //供应商下的商品种类
      int goodsCount = shoppingCarMapper.countByExample(example);
      Supplier supplier = supplierRepoService.getSupplierByID(supplierId);
      
      //查询购物车中被选中的该供应商的商品
      SelectShoppingCarQuery query = new SelectShoppingCarQuery();
      query.setSupplierId(supplierId);
      query.setSupermarketId(model.getSupermarket_id());
      query.setCheckStatus(ShoppingStatusEnum.CHECK);
      List<AppShoppingCar> Shoppingcar_list = shoppingCarMapper.appSelectByQuery(query);
      if(Shoppingcar_list == null||Shoppingcar_list.size() == 0){
        continue;
      }
      //将商品相关的详细信息放入list中
      appAddGoodsMessageIntoShoppingCar(Shoppingcar_list);
      //供应商中商品的总计数量
      Long supplierGoodsQit =
          shoppingCarService.shoppingCarCheckCountBySupplier(model.getSupermarket_id(), supplierId);
      supplierResult.setSupplierGoodsQit(supplierGoodsQit);
      
      //供应商中选中商品总计金额
      BigDecimal suAmount =
          sumAmoutService.SumAmoutBySuperMarketAndSupplier(supplierId, model.getSupermarket_id());
      supplierResult.setGoodsCount(goodsCount);
      supplierResult.setSupplierAmount(suAmount);
      supplierResult.setSupplierId(supplierId);
      supplierResult.setShoppingCarList(Shoppingcar_list);
      supplierResult.setSupplierName(supplier.getSupplierName());
      supplierResultList.add(supplierResult);
      totalAmount = totalAmount.add(suAmount);
      totalGoodsCount = totalGoodsCount + goodsCount;
    }
    
    //购物车中总计商品数量
    Long totalGoodsQit = shoppingCarService.shoppingCarCheckCountBySupermarket(model.getSupermarket_id());
    result.setTotalGoodsQit(totalGoodsQit);
    result.setTotalGoodsCount(totalGoodsCount);
    result.setTotalAmount(totalAmount);
    result.setFull_address(full_adress);
    result.setReceptionName(supermarket.getReceptionName());
    result.setSupermarketName(supermarket.getSupermarketName());
    result.setSupplierResultList(supplierResultList);
    result.setPhone(phone);
    //生成防止表单重复标记
    result.setOrderSeq(RandomStringUtils.randomAlphanumeric(10));
    return result;
  }
  
  /**
   * validatePlatFormStandArd:(验证是否满足平台结算标准).
   *
   * 2016年8月31日 下午5:07:24
   * @author zhaojn
   * @param appShoppingCarResult
   * @param totalAmount
   * @return
  
   */
  private AppShoppingCarResult validatePlatFormStandardAndAddMessageInfo(
      AppShoppingCarResult appShoppingCarResult, BigDecimal totalAmount) {
    // 判断是否符合平台最低起订价
    BigDecimal minPlatForm = sumAmoutService.getMInPlatformPrice();
    appShoppingCarResult.setMinPlatformPrice(minPlatForm);
    if (totalAmount.compareTo(minPlatForm) == -1) {
      appShoppingCarResult.setPlatFormstandard(CheckEnum.N);
      appShoppingCarResult.setPlatFormMessage("平台最低起订价为" + String.valueOf(minPlatForm) + "元");
      return appShoppingCarResult;
    }

    for (SupplierShoppingCarResult supplierresult : appShoppingCarResult.getShoppingcarResult()) {
      // 判断供应商全选状态下是否符合供应商最低起订价 不符合不允许结算
      if (supplierresult.getSupplierAllCheck() == 1L
          && supplierresult.getSupplierStandard() == CheckEnum.N) {
        appShoppingCarResult.setPlatFormstandard(CheckEnum.N);
        appShoppingCarResult.setPlatFormMessage("");
        return appShoppingCarResult;
      }

      // 满足平台标准 不满足供应商标准 供应商下面的商品不允许结算
      if (supplierresult.getSupplierStandard() == CheckEnum.N) {
        for (ShoppingCar sc : supplierresult.getShoppingCarList()) {
          if (sc.getCheckStatus() == ShoppingStatusEnum.CHECK) {
            appShoppingCarResult.setPlatFormstandard(CheckEnum.N);
            appShoppingCarResult.setPlatFormMessage("");
            return appShoppingCarResult;
          }
        }
      }
    }

    appShoppingCarResult.setPlatFormstandard(CheckEnum.Y);
    appShoppingCarResult.setPlatFormMessage("");
    return appShoppingCarResult;
  }
}


