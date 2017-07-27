package com.kxwp.h5.service.channel;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxwp.common.constants.OSSImgStyleEnum;
import com.kxwp.common.constants.channel.ChannelItemStatusEnum;
import com.kxwp.common.entity.channel.Channel;
import com.kxwp.common.entity.channel.ChannelItem;
import com.kxwp.common.entity.supplier.Goods;
import com.kxwp.common.entity.supplier.Supplier;
import com.kxwp.common.model.exception.KXWPNotFoundException;
import com.kxwp.common.query.channel.ChannelItemQuery;
import com.kxwp.common.query.channel.ChannelQuery;
import com.kxwp.common.service.channel.ChannelItemService;
import com.kxwp.common.service.channel.ChannelService;
import com.kxwp.common.service.goods.GoodsRepoService;
import com.kxwp.common.service.supplier.SupplierRepoService;
import com.kxwp.h5.model.channel.ChannelResponse;

/**
 * 频道API Service date: 2016年9月22日 下午5:12:48
 *
 * @author wangjun
 */
@Service
public class ChannelH5Service {
  @Autowired
  private ChannelService channelService;

  @Autowired
  private ChannelItemService channelItemService;

  @Autowired
  private GoodsRepoService goodsRepoService;

  @Autowired
  private SupplierRepoService supplierRepoService;

  /**
   * 
   * listChannelMixture:(查询频道综合信息).
   *
   * 2016年9月22日 下午5:16:20
   * 
   * @author wangjun
   * @param channelQuery
   * @throws KXWPNotFoundException
   */
  public List<ChannelResponse> listChannelMixture(ChannelQuery channelQuery)
      throws KXWPNotFoundException {
    // TODO缓存

    // 获取频道
    List<Channel> channels = channelService.listByCondition(channelQuery);

    if (channels == null || channels.size() == 0) {
      return null;
    }

    // 获取频道item
    if (channels.get(0) != null) {
      List<ChannelResponse> channelResponses = new ArrayList<ChannelResponse>();

      ChannelItemQuery channelItemQuery = new ChannelItemQuery();

      channelItemQuery.setPager(channelQuery.getPager());
      channelItemQuery.setChannelId(channels.get(0).getId());
      channelItemQuery.setItemStatus(ChannelItemStatusEnum.VALID);

      List<ChannelItem> channelItems = channelItemService.listByExamplePaging(channelItemQuery);

      for (ChannelItem channelItem : channelItems) {
        ChannelResponse channelResponse = new ChannelResponse();

        channelResponse.setPromotionPrice(channelItem.getPromotionPrice());

        // 获取商品信息
        Goods goods = goodsRepoService.getGoodsWithThumbnail(channelItem.getItemId(),
            OSSImgStyleEnum.goods_300_300);

        channelResponse.setPhotoList(goods.getPhotoList());
        channelResponse.setGoodsName(goods.getGoodsName());
        channelResponse.setPackageSpecific(goods.getPackageSpecific());
        channelResponse.setSalePrice(goods.getSalePrice());
        channelResponse.setGoodsId(channelItem.getItemId());

        // 后去供应商信息
        Supplier supplier = supplierRepoService.getSupplierByID(goods.getSupplierId());

        channelResponse.setSupplierName(supplier.getSupplierName());
        channelResponse.setSupplierId(supplier.getId());

        channelResponses.add(channelResponse);
      }

      // TODO缓存

      return channelResponses;
    }

    return null;
  }

  /**
   * 
   * countChannelItemTotalResult:(这里用一句话描述这个方法的作用).
   *
   * 2016年9月22日 下午6:24:29
   * 
   * @author wangjun
   * @param channelQuery
   * @return
   */
  public int countChannelItemTotalResults(ChannelQuery channelQuery) {
    // 获取频道
    List<Channel> channels = channelService.listByCondition(channelQuery);

    if (channels == null || channels.size() == 0) {
      return 0;
    }

    // 获取频道item
    if (channels.get(0) != null) {

      ChannelItemQuery channelItemQuery = new ChannelItemQuery();

      channelItemQuery.setPager(channelQuery.getPager());
      channelItemQuery.setChannelId(channels.get(0).getId());
      channelItemQuery.setItemStatus(ChannelItemStatusEnum.VALID);

      return channelItemService.countTotalResultsByExample(channelItemQuery);
    }

    return 0;
  }

}
