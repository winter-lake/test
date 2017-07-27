/**
 *此处定义级联等控件的初始化
 *必须先引用select2的相关文件
 */
(function (global) {


	var JiLian = {
		// 地址级联菜单 --开始
		addressSelect: function (province, city, district,street) {
			var province_dom = $(province);
			if (city) {
				var city_dom = $(city);
			}
			if (district) {
				var district_dom = $(district);
			}
			if (street) {
				var street_dom = $(street);
			}
			// 省autocomplete
			KXWP.KXWPSelect2({
				url: "/common/ajax/region/queryRegion.do",
				element: province_dom,
				queryField: "regionName", //*input的参数
				displayField: "name", //*显示在input中的返回值字段名
				valueField: "id",// *程序中要用的值
				minLength: 0,//输入几个字符就触发
				afterSelected: function () {
					if (city) {
						city_dom.val(null).trigger("change");
						if (district) {
							district_dom.val(null).trigger("change");
						}
						var this_value = province_dom.val();
						if (this_value && this_value !== "") {
							city_dom.removeAttr('disabled');
						} else {
							city_dom.attr("disabled", "disbaled");
							if (district) {
								district_dom.val(null).trigger("change");
								district_dom.attr("disabled", "disbaled");
							}

						}
					}
					province_dom.trigger('selected', province_dom.val());
				}
			});

			if (city) {
				//城市 autocomplete
				KXWP.KXWPSelect2({
					url: "/common/ajax/region/queryRegion.do",
					element: city_dom,
					parentDom: province, //根据父元素的值来查询
					parentDomField: "parentRegionID", //查询时所带的父元素参数名称，和上一行一起传入
					minLength: 0,//输入几个字符就触发,
					queryField: "regionName", //*input的参数
					valueField: "id",// *程序中要用的值
					displayField: "name", //*显示在input中的返回值字段名
					afterSelected: function () {
						if (district) {
							district_dom.select2('val', '');
							var this_value = city_dom.val();
							if (this_value && this_value !== "") {
								district_dom.removeAttr('disabled');
							} else {
								district_dom.attr("disabled", "disbaled");
							}
						}
						city_dom.trigger('selected', city_dom.val());
					}
				});
			}

			if (district) {
				// 镇/街道autocomplete
				KXWP.KXWPSelect2({
					url: "/common/ajax/region/queryRegion.do",
					element: district_dom,
					parentDom: city, //根据父元素的值来查询
					parentDomField: "parentRegionID", //查询时所带的父元素参数名称，和上一行一起传入
					minLength: 0,//输入几个字符就触发,
					queryField: "regionName", //*input的参数
					valueField: "id",// *程序中要用的值
					displayField: "name", //*显示在input中的返回值字段名
					afterSelected: function () {
						if (street) {
							street_dom.select2('val', '');
							var this_value = district_dom.val();
							if (this_value && this_value !== "") {
								street_dom.removeAttr('disabled');
							} else {
								street_dom.attr("disabled", "disbaled");
							}
						}
					}
				});
			}
			
			if (street) {
				// 区/县 autocomplete
				KXWP.KXWPSelect2({
					url: "/common/ajax/region/queryRegion.do",
					element: street_dom,
					parentDom: district, //根据父元素的值来查询
					parentDomField: "parentRegionID", //查询时所带的父元素参数名称，和上一行一起传入
					minLength: 0,//输入几个字符就触发,
					queryField: "regionName", //*input的参数
					valueField: "id",// *程序中要用的值
					displayField: "name", //*显示在input中的返回值字段名
					afterSelected: function () {
						street_dom.trigger('selected', street_dom.val());
					}
				});
			}
		},// 地址级联菜单 --结束
		
		
		// 供应商autocomplete --开始
		gysSelect: function (dom, callback) {
			var element = $(dom);
			KXWP.KXWPSelect2({
				url: "/common/ajax/gys/querySupplier.do",
				element: element,
				queryField: "supplier_name", //*input的参数
				displayField: "supplierName", //*显示在input中的返回值字段名
				valueField: "id",// *程序中要用的值
				minLength: 0,//输入几个字符就触发
				afterSelected: function () {
					if (callback) {
						callback();
					}
				}
			});
		},// 供应商autocomplete --结束

		// 服务站autocomplete --开始
		fwzSelect: function (dom, callback) {
			var element = $(dom);
			KXWP.KXWPSelect2({
				url: "/common/ajax/fwz/queryServiceStationName.do",
				element: element,
				queryField: "serviceStationName", //*input的参数
				displayField: "serviceStationName", //*显示在input中的返回值字段名
				valueField: "id",// *程序中要用的值
				minLength: 0,//输入几个字符就触发
				afterSelected: function () {
					if (callback) {
						callback();
					}
				}
			});
		},// 服务站autocomplete --结束
		
		// 服务站超市autocomplete --开始
		supermarketRepoSelectByName: function (dom, callback) {
			var element = $(dom);
			KXWP.KXWPSelect2({
				url: "/common/ajax/supermarket/querySupermarketRepo.do",
				element: element,
				hasPages: false,
				ajaxType: 'post',
				queryField: "supermarketName", //*input的参数
				displayField: "supermarketName", //*显示在input中的返回值字段名
				valueField: "id",// *程序中要用的值
				minLength: 1,//输入几个字符就触发
				hasInputValue: true, // 是否可以直接输入
				afterSelected: function () {
					if (callback) {
						callback();
					}
				}
			});
		},// 服务站超市autocomplete --结束
		
		// 商品库条码autocomplete --开始
		goodsRepoSelectByBarcode: function (dom, callback) {
			var element = $(dom);
			KXWP.KXWPSelect2({
				url: "/common/ajax/goods/queryGoodsRepo.do",
				element: element,
				hasPages: false,
				ajaxType: 'get',
				queryField: "barcode", //*input的参数
				displayField: "barcode", //*显示在input中的返回值字段名
				valueField: "barcode",// *程序中要用的值
				minLength: 1,//输入几个字符就触发
				hasInputValue: true, // 是否可以直接输入
				afterSelected: function () {
					if (callback) {
						callback();
					}
				}
			});
		},// 商品库名称autocomplete --结束
		
		// 商品库名称autocomplete --开始
		goodsRepoSelectByName: function (dom, callback) {
			var element = $(dom);
			KXWP.KXWPSelect2({
				url: "/common/ajax/goods/queryGoodsRepo.do",
				element: element,
				hasPages: false,
				ajaxType: 'get',
				queryField: "goods_name", //*input的参数
				displayField: "goodsName", //*显示在input中的返回值字段名
				valueField: "id",// *程序中要用的值
				minLength: 1,//输入几个字符就触发
				hasInputValue: true, // 是否可以直接输入
				afterSelected: function () {
					if (callback) {
						callback();
					}
				}
			});
		},// 商品库名称autocomplete --结束
		
		// 商品库名称autocomplete --开始
		goodsRepoSelectByCondition: function (dom, callback) {
			var element = $(dom);
			KXWP.KXWPSelect2({
				url: "/common/ajax/goods/queryGoodsRepoByCondition.do",
				element: element,
				hasPages: false,
				ajaxType: 'get',
				queryField: "goods_name", //*input的参数
				displayField: "goodsName", //*显示在input中的返回值字段名
				valueField: "id",// *程序中要用的值
				minLength: 1,//输入几个字符就触发
				hasInputValue: false, // 是否可以直接输入
				afterSelected: function () {
					if (callback) {
						callback();
					}
				}
			});
		},// 商品库名称autocomplete --结束
		
		// 品牌库autocomplete --开始
		sysBrandRepoSelect: function (dom, callback) {
			var element = $(dom);
			KXWP.KXWPSelect2({
				url: "/common/ajax/brand/queryBrandRepo.do",
				element: element,
				queryField: "brand_name", //*input的参数
				displayField: "brandName", //*显示在input中的返回值字段名
				valueField: "id",// *程序中要用的值
				minLength: 0,//输入几个字符就触发
				afterSelected: function () {
					if (callback) {
						callback();
					}
				}
			});
		},// 品牌autocomplete --结束
		
		// 省市站点快递员级联 --开始
		ssxStopKDYSelect: function (province, city, stop,kdy) {
			var province_dom = $(province);
			if (city) {
				var city_dom = $(city);
			}
			if (stop) {
				var stop_dom = $(stop);
			}
			if(kdy) {
				var kdy_dom = $(kdy);
			}


			// 省autocomplete
			KXWP.KXWPSelect2({
				url: "/system/ajax/getSSX.do",
				element: province_dom,
				queryField: "ssxName", //*input的参数
				displayField: "ssxName", //*显示在input中的返回值字段名
				valueField: "ssxId",// *程序中要用的值
				minLength: 0,//输入几个字符就触发
				afterSelected: function () {
					if (city) {
						city_dom.select2('val', '');
						var this_value = province_dom.val();
						if (this_value && this_value !== "") {
							city_dom.removeAttr('disabled');
						} else {
							city_dom.attr("disabled", "disbaled");
						}
					}
				}
			});

			if (city) {
				//城市 autocomplete
				Higo.higoSelect2({
					url: "/system/ajax/getSSX.do",
					element: city_dom,
					parentDom: province, //根据父元素的值来查询
					parentDomField: "parentAreaID", //查询时所带的父元素参数名称，和上一行一起传入
					minLength: 0,//输入几个字符就触发,
					queryField: "ssxName", //*input的参数
					valueField: "ssxId",// *程序中要用的值
					displayField: "ssxName", //*显示在input中的返回值字段名
					afterSelected: function () {
						if (stop) {
							stop_dom.select2('val', '');
							var this_value = city_dom.val();
							if (this_value && this_value !== "") {
								stop_dom.removeAttr('disabled');
							} else {
								stop_dom.attr("disabled", "disbaled");
							}
						}
					}
				});
			}
			
			if(stop){
				KXWP.KXWPSelect2({
					url: "/tms/jilian/ajax/queryDistributionCenter.do",
					element: stop_dom,
					parentDom: city, //根据父元素的值来查询
					parentDomField: "city_id", //查询时所带的父元素参数名称，和上一行一起传入
					minLength: 0,//输入几个字符就触发
					queryField: "distributionCenterName", //*input的参数
					valueField: "distributioncenterid",// *程序中要用的值
					displayField: "distributioncentername", //*显示在input中的返回值字段名
					afterSelected: function () {
						if (kdy) {
							kdy_dom.select2('val', '');
							var this_value = stop_dom.val();
							if (this_value && this_value !== "") {
								kdy_dom.removeAttr('disabled');
							} else {
								kdy_dom.attr("disabled", "disbaled");
							}
						}
					}
				});
			}
			
			if(kdy){
				// 快递员
				KXWP.KXWPSelect2({
					url: "/system/sysUser/ajax/querySysUser.do",
					element: kdy_dom,
					parentDom: stop, //根据父元素的值来查询
					parentDomField: "distributioncenterid", //查询时所带的父元素参数名称，和上一行一起传入
					minLength: 0,//输入几个字符就触发,
					queryField: "username", //*input的参数
					valueField: "userno",// *程序中要用的值
					displayField: "userno", //*显示在input中的返回值字段名
					displayField2: "username", //*显示在input中的返回值字段名
					afterSelected: function () {
					}
				});
			}


		},// 省市站点快递员级联 --结束

		// 仓库级联 --开始
		systemWarehouseSelect: function (ome, warehouse) {
			var omeDom = $(ome);
			var warehouseDom = $(warehouse);

			// 网点单位autocomplete
			Higo.higoSelect2({
				url: "/wms/oeminfo/ajax/queryOEMInfo.do",
				element: omeDom,
				queryField: "oemname", //*input的参数
				displayField: "oemname", //*显示在input中的返回值字段名
				valueField: "oemid",// *程序中要用的值
				minLength: 0,//输入几个字符就触发
				afterSelected: function () {
					warehouseDom.select2('val', '');
					var this_value = omeDom.val();
					if (this_value && this_value !== "") {
						warehouseDom.removeAttr('disabled');
					} else {
						warehouseDom.attr("disabled", "disbaled");
					}
				}
			});

			//仓库 autocomplete
			Higo.higoSelect2({
				url: "/wms/warehouse/ajax/queryWMSWarehouse.do",
				element: warehouseDom,
				parentDom: ome, //根据父元素的值来查询
				parentDomField: "oemid", //查询时所带的父元素参数名称，和上一行一起传入
				minLength: 0,//输入几个字符就触发,
				queryField: "warehouseName", //*input的参数
				valueField: "warehouseid",// *程序中要用的值
				displayField: "warehousename", //*显示在input中的返回值字段名
				afterSelected: function () {
				}
			});

		},// 地址级联菜单 --结束
		
		// 属性/字典级联 --开始
		systemPropertyCascade: function (firstClass, secondClass, thirdClass, propertyType) {
			var firstClass_dom = $(firstClass);
			if (secondClass) {
				var secondClass_dom = $(secondClass);
			}
			if (thirdClass) {
				var thirdClass_dom = $(thirdClass);
			}


			// 一级分类autocomplete
			Higo.higoSelect2({
				url: "/system/ajax/getSYSProperty.do",
				element: firstClass_dom,
				ajax_data: {
					property_type: propertyType,
					parentID: "0",
				},
				queryField: "property_name", //*input的参数
				displayField: "name", //*显示在input中的返回值字段名
				valueField: "id",// *程序中要用的值
				minLength: 0,//输入几个字符就触发
				afterSelected: function () {
					if (secondClass) {
						secondClass_dom.select2('val', '');
						if (thirdClass) {
							thirdClass_dom.select2('val', '');
						}
						var this_value = firstClass_dom.val();
						if (this_value && this_value !== "") {
							secondClass_dom.removeAttr('disabled');
						} else {
							secondClass_dom.attr("disabled", "disbaled");
							if (thirdClass) {
								thirdClass_dom.attr("disabled", "disbaled");
							}

						}
					}
				}
			});

			if (secondClass) {
				//二级分类 autocomplete
				Higo.higoSelect2({
					url: "/system/ajax/getSYSProperty.do",
					element: secondClass_dom,
					parentDom: firstClass, //根据父元素的值来查询
					parentDomField: "parentID", //查询时所带的父元素参数名称，和上一行一起传入
					minLength: 0,//输入几个字符就触发,
					queryField: "property_name", //*input的参数
					valueField: "id",// *程序中要用的值
					displayField: "name", //*显示在input中的返回值字段名
					afterSelected: function () {
						if (thirdClass) {
							thirdClass_dom.select2('val', '');
							var this_value = secondClass_dom.val();
							if (this_value && this_value !== "") {
								thirdClass_dom.removeAttr('disabled');
							} else {
								thirdClass_dom.attr("disabled", "disbaled");
							}
						}
					}
				});
			}

			if (thirdClass) {
				// 区/县 autocomplete
				Higo.higoSelect2({
					url: "/system/ajax/getSYSProperty.do",
					element: thirdClass_dom,
					parentDom: secondClass, //根据父元素的值来查询
					parentDomField: "parentID", //查询时所带的父元素参数名称，和上一行一起传入
					minLength: 0,//输入几个字符就触发,
					queryField: "property_name", //*input的参数
					valueField: "id",// *程序中要用的值
					displayField: "name", //*显示在input中的返回值字段名
					afterSelected: function () {
					}
				});
			}
		},// 工单分类级联 --结束

		// 员工autocomplete
		systemUserSelect: function (dom, roleCode, stopId, callback) {
			var element = $(dom);
			var options = {
				url: "/system/sysUser/ajax/querySysUser.do",
				element: element,
				queryField: "username", //*input的参数
				displayField: "userno", //*显示在input中的返回值字段名
				displayField2: "username", //*显示在input中的返回值字段名
				valueField: "userno",// *程序中要用的值
				minLength: 0,//输入几个字符就触发
				afterSelected: function () {
					if (callback) {
						callback();
					}
				}
			}
			if (roleCode) {
				options.ajax_data = {
					rolecode: roleCode
				}
			}
			if (stopId) {
				options.ajax_data = {
					distributioncenterid: stopId
				}
			}
			Higo.higoSelect2(options);
		},


		

		// 查询站点下的交接单 --开始
		getJJDsByStopId: function (domId, stopId, isZero) {
			var requestURL = "/system/ajax/queryDeliveryReceiptsByStopId.do";
			$.ajax({
				url: requestURL,
				type: 'POST',
				dataType: 'json',
				data: {'stopId':stopId},
				//contentType: "application/json",
				success: function (data) {
					if (data.callStatus == "success") {
						var html = "";
						//如果传入true 则显示placeholder值
						if (isZero) {
							var placeholder = $(domId).attr("placeholder");
							html = "<option value=''>" + placeholder + "</option>" + html;
						}
						if (data.data && data.data.length > 0) {
							var list = data.data;
							for (var i = 0; i < list.length; i++) {
								html += "<option value=" + list[i].id + ">" + list[i].receiptno + "</option>";
							}
						}
						$(domId).html(html);
					}
				}
			});
		},// 查询站点下的交接单 --结束
		
		// 货主产品编号级联查询--开始
		systemOwnerGoodsNoSelect: function (owner, goodsNo) {
			// 货主autocomplete
			var owner_dom = $(owner);
			var goodsNo_dom = $(goodsNo);

			Higo.higoSelect2({
				url: "/wms/owner/ajax/queryOwner.do",
				element:owner_dom,
				queryField: "ownerName", //*input的参数
				displayField: "owername", //*显示在input中的返回值字段名
				valueField:"owerid",// *程序中要用的值
				minLength:0,//输入几个字符就触发
				afterSelected:function(){
					goodsNo_dom.select2('val', '');
					var this_value = owner_dom.val();
					if(this_value && this_value !== ""){
						goodsNo_dom.removeAttr('disabled');
					}else{
						goodsNo_dom.attr("disabled","disbaled");
					}
				}
			});

			// 产品编号autocomplete
			Higo.higoSelect2({
				url: "/wms/goods/ajax/queryGoodsNo.do",
				element:goodsNo_dom,
				parentDom: owner, //根据父元素的值来查询
				parentDomField: "ownerID", //查询时所带的父元素参数名称，和上一行一起传入
				resultList:"goodsNoList",// ajax返回的结果列表的字段名
				ItemType:"string",//结果的子元素类型
				minLength:0,//输入几个字符就触发,
				queryField: "goodsNo", //*input的参数
			});
		},//货主产品编号级联查询 --结束
		
		// 菜单autocomplete --开始
		systemModuleSelect: function (dom) {
			var element = $(dom);
			Higo.higoSelect2({
				url: "/system/module/ajax/queryPModule.do",
				element: element,
				queryField: "modulename", //*input的参数
				displayField: "modulename", //*显示在input中的返回值字段名
				valueField: "moduleid",// *程序中要用的值
				minLength: 0,//输入几个字符就触发
				ajaxType: "post",
				hasPages: false,
				afterSelected: function () {
				}
			});
		},// 菜单autocomplete --结束








		/*
		* 新的autocomplete 2016.08.13
		*/ 
		goodsCategoryAuto: function (firstClass, secondClass, thirdClass, propertyType) {
			var firstClass_dom = $(firstClass);
			if (secondClass) {
				var secondClass_dom = $(secondClass);
			}
			if (thirdClass) {
				var thirdClass_dom = $(thirdClass);
			}


			// 一级分类autocomplete
			KXWP.KXWPSelect2({
				url: "/common/ajax/goods/queryGoodsCategory.do",
				element: firstClass_dom,
				ajax_data: {
					property_type: propertyType,
					parentClassficationID: "0",
				},
				queryField: "category_name", //*input的参数
				displayField: "name", //*显示在input中的返回值字段名
				valueField: "id",// *程序中要用的值
				minLength: 0,//输入几个字符就触发
				afterSelected: function () {
					if (secondClass) {
						secondClass_dom.select2('val', '');
						if (thirdClass) {
							thirdClass_dom.select2('val', '');
						}
						var this_value = firstClass_dom.val();
						if (this_value && this_value !== "") {
							secondClass_dom.removeAttr('disabled');
						} else {
							secondClass_dom.attr("disabled", "disbaled");
							if (thirdClass) {
								thirdClass_dom.attr("disabled", "disbaled");
							}

						}
					}
				}
			});

			if (secondClass) {
				//二级分类 autocomplete
				KXWP.KXWPSelect2({
					url: "/common/ajax/goods/queryGoodsCategory.do",
					element: secondClass_dom,
					parentDom: firstClass, //根据父元素的值来查询
					parentDomField: "parentClassficationID", //查询时所带的父元素参数名称，和上一行一起传入
					minLength: 0,//输入几个字符就触发,
					queryField: "category_name", //*input的参数
					valueField: "id",// *程序中要用的值
					displayField: "name", //*显示在input中的返回值字段名
					afterSelected: function () {
						if (thirdClass) {
							thirdClass_dom.select2('val', '');
							var this_value = secondClass_dom.val();
							if (this_value && this_value !== "") {
								thirdClass_dom.removeAttr('disabled');
							} else {
								thirdClass_dom.attr("disabled", "disbaled");
							}
						}
					}
				});
			}

			if (thirdClass) {
				// 区/县 autocomplete
				KXWP.KXWPSelect2({
					url: "/common/ajax/goods/queryGoodsCategory.do",
					element: thirdClass_dom,
					parentDom: secondClass, //根据父元素的值来查询
					parentDomField: "parentClassficationID", //查询时所带的父元素参数名称，和上一行一起传入
					minLength: 0,//输入几个字符就触发,
					queryField: "category_name", //*input的参数
					valueField: "id",// *程序中要用的值
					displayField: "name", //*显示在input中的返回值字段名
					afterSelected: function () {
					}
				});
			}
		},// 工单分类级联 --结束
	}
	
	global.fwzSelect = JiLian.fwzSelect;//服务站搜索
	global.addressSelect = JiLian.addressSelect;//地址库级联
	global.goodsCategoryAuto = JiLian.goodsCategoryAuto; // 商品分类
	global.sysBrandRepoSelect = JiLian.sysBrandRepoSelect; //品牌库搜索
	global.goodsRepoSelectByBarcode = JiLian.goodsRepoSelectByBarcode; // 商品条码
	global.goodsRepoSelectByName = JiLian.goodsRepoSelectByName; // 商品名称
	global.gysSelect = JiLian.gysSelect; //供应商搜索
	global.supermarketRepoSelectByName = JiLian.supermarketRepoSelectByName;//超市名称
	global.goodsRepoSelectByCondition = JiLian.goodsRepoSelectByCondition;//超市名称
})(KXWP);

