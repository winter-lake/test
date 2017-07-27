/**
 *此处定义级联等控件的初始化
 *必须先引用select2的相关文件
 */
(function (global) {


	var JiLian = {
		// 地址级联菜单 --开始
		addressSelect: function (province, city, district) {
			var province_dom = $(province);
			if (city) {
				var city_dom = $(city);
			}
			if (district) {
				var district_dom = $(district);
			}
			// 省autocomplete
			Higo.higoSelect2({
				url: "/system/ajax/getSSX.do",
				element: province_dom,
				queryField: "ssxName", //*input的参数
				displayField: "ssxName", //*显示在input中的返回值字段名
				valueField: "ssxId",// *程序中要用的值
				minLength: 0,//输入几个字符就触发
				afterSelected: function () {
					if (city) {
						city_dom.select2('val', '');
						if (district) {
							district_dom.select2('val', '');
						}
						var this_value = province_dom.val();
						if (this_value && this_value !== "") {
							city_dom.removeAttr('disabled');
						} else {
							city_dom.attr("disabled", "disbaled");
							if (district) {
								district_dom.attr("disabled", "disbaled");
							}

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
						if (district) {
							district_dom.select2('val', '');
							var this_value = city_dom.val();
							if (this_value && this_value !== "") {
								district_dom.removeAttr('disabled');
							} else {
								district_dom.attr("disabled", "disbaled");
							}
						}
					}
				});
			}

			if (district) {
				// 区/县 autocomplete
				Higo.higoSelect2({
					url: "/system/ajax/getSSX.do",
					element: district_dom,
					parentDom: city, //根据父元素的值来查询
					parentDomField: "parentAreaID", //查询时所带的父元素参数名称，和上一行一起传入
					minLength: 0,//输入几个字符就触发,
					queryField: "ssxName", //*input的参数
					valueField: "ssxId",// *程序中要用的值
					displayField: "ssxName", //*显示在input中的返回值字段名
					afterSelected: function () {
					}
				});
			}
		},// 地址级联菜单 --结束
		
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
			Higo.higoSelect2({
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
				Higo.higoSelect2({
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
				Higo.higoSelect2({
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
		
		// 客户门店级联查询--开始
		systemTransportSelect: function (owner, transport) {
			// 货主autocomplete
			var owner_dom = $(owner);
			var stop_dom = $(transport);

			Higo.higoSelect2({
				url: "/wms/owner/ajax/queryOwner.do",
				element:owner_dom,
				queryField: "ownerName", //*input的参数
				displayField: "owername", //*显示在input中的返回值字段名
				valueField:"owerid",// *程序中要用的值
				minLength:0,//输入几个字符就触发
				afterSelected:function(){
					stop_dom.select2('val', '');
					var this_value = owner_dom.val();
					if(this_value && this_value !== ""){
						stop_dom.removeAttr('disabled');
					}else{
						stop_dom.attr("disabled","disbaled");
					}
				}
			});

			// 店铺 autocomplete
			Higo.higoSelect2({
				url: "/wms/owner/ajax/queryOwnerShop.do",
				element:stop_dom,
				parentDom: owner, //根据父元素的值来查询
				parentDomField: "ownerID", //查询时所带的父元素参数名称，和上一行一起传入
				resultList:"shopNameList",// ajax返回的结果列表的字段名
				ItemType:"string",//结果的子元素类型
				minLength:0,//输入几个字符就触发,
				queryField: "shopName", //*input的参数
			});
		},//客户门店级联查询 --结束
		
		// 获取站点的函数 --开始
		getSystemStop: function (selectDom) {
			var branchDom = $(selectDom);
			// 返回分部的select
			$.ajax({
				url: '/tms/ajax/queryDistributionCenter.do',
				type: 'POST',
				dataType: 'json',
				contentType: "application/json",
				data: {},
				success: function (data) {
					if (data && data.callStatus == "success") {
						if (data.data && data.data.length > 0) {
							var options = data.data;
							for (var i = 0; i < options.length; i++) {
								var html = '<option value=' + options[i].distributioncenterid + '>' + options[i].distributioncentername + '</option>';
								$(branchDom).append(html);
							}
							;
						} else {
							Higo.showMessage("获取点部、分部失败");
						}
					}
				},
				fail: function () {
				}
			});
		},// 仓库级联 --结束

		// 站点autocomplete --开始
		systemStopSelect: function (dom) {
			Higo.higoSelect2({
				url: "/tms/ajax/queryDistributionCenter.do",
				element: dom,
				queryField: "distributionCenterName", //*input的参数
				displayField: "distributioncentername", //*显示在input中的返回值字段名
				valueField: "distributioncenterid",// *程序中要用的值
				minLength: 0,//输入几个字符就触发
				ajaxType: "get",
				hasPages: false,
				afterSelected: function () {
				}
			});
		},// 站点autocomplete --结束
		
		// 产品信息autocomplete --开始
		systemGoodsNoSelect: function (dom) {
			Higo.higoSelect2({
				url: "/wms/goods/ajax/queryGoods.do",
				element: dom,
				queryField: "autoCompleteGoodsNo", //*input的参数
				displayField: "goodsno", //*显示在input中的返回值字段名
				valueField: "goodsid",// *程序中要用的值
				minLength: 4,//输入几个字符就触发
				ajaxType: "post",
				hasPages: false,
				afterSelected: function () {
				}
			});
		},// 产品信息autocomplete --结束

		// 点部autocomplete --开始
		systemPointSelect: function (dom) {
			Higo.higoSelect2({
				url: "/tms/ajax/queryDistributionCenter.do",
				element: dom,
				queryField: "distributionCenterName", //*input的参数
				displayField: "distributioncentername", //*显示在input中的返回值字段名
				valueField: "distributioncenterid",// *程序中要用的值
				minLength: 0,//输入几个字符就触发
				ajaxType: "get",
				hasPages: false,
				afterSelected: function () {
				}
			});
		},// 点部autocomplete --结束
		
		//仓库 autocomplete --开始
		warehouseSelect: function (dom) {
			Higo.higoSelect2({
				url: "/wms/warehouse/ajax/queryWMSWarehouse.do",
				element: dom,
				queryField: "warehouseName", //*input的参数
				valueField: "warehouseno",// *程序中要用的值
				displayField: "warehousename", //*显示在input中的返回值字段名
				minLength: 0,//输入几个字符就触发,
				ajaxType: "post",
				hasPages: false,
				afterSelected: function () {
				}
			});
		},// 仓库 autocomplete --结束
		
		//区位 autocomplete --开始
		sectionSelect: function (dom) {
			Higo.higoSelect2({
				url: "/wms/section/ajax/querySection.do",
				element: dom,
				queryField: "sectionNo", //*input的参数
				valueField:"sectionid",// *程序中要用的值
				displayField: "sectionno", //*显示在input中的返回值字段名
				minLength: 0,//输入几个字符就触发,
				ajaxType: "post",
				hasPages: false,
				afterSelected: function () {
				}
			});
		},// 区位autocomplete --结束

		// 货主autocomplete --开始
		systemOwnerSelect: function (dom, callback) {
			var element = $(dom);
			Higo.higoSelect2({
				url: "/wms/owner/ajax/queryOwner.do",
				element: element,
				queryField: "ownerName", //*input的参数
				displayField: "owername", //*显示在input中的返回值字段名
				valueField: "owerid",// *程序中要用的值
				minLength: 0,//输入几个字符就触发
				afterSelected: function () {
					if (callback) {
						callback();
					}
				}
			});
		},// 货主autocomplete --结束

		// 预付货主autocomplete --开始
		systemYuFuOwnerSelect: function (dom, callback) {
			var element = $(dom);
			Higo.higoSelect2({
				url: "/crm/owner/ajax/queryYuFuOwner.do",
				element: element,
				queryField: "ownerName", //*input的参数
				displayField: "owername", //*显示在input中的返回值字段名
				valueField: "owerid",// *程序中要用的值
				minLength: 0,//输入几个字符就触发
				afterSelected: function () {
					if (callback) {
						callback();
					}
				}
			});
		},// 货主autocomplete --结束
		
		//货位autocomplete
		locationSelect:function (dom, callback) {
			var element = $(dom);
			Higo.higoSelect2({
				url: "/wms/location/ajax/queryLoaction.do",
				element: element,
				queryField: "locationNo", //*input的参数
				displayField: "locationno", //*显示在input中的返回值字段名
				valueField:"locationid",
				minLength: 0,//输入几个字符就触发
				afterSelected: function () {
					if (callback) {
						callback();
					}
				}
			});
		},// 货位autocomplete --结束

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

		// 获取承运商 --开始
		systemCarriers: function (domId, carrierType, isZero) {
			var requestURL = "/express/ajax/queryCarriers.do";
			if (carrierType) {
				requestURL = requestURL + "?carrierType=" + carrierType;
			}
			$.ajax({
				url: requestURL,
				type: 'POST',
				dataType: 'json',
				data: {},
				contentType: "application/json",
				success: function (data) {
					if (data.callStatus == "success") {
						if (data.data && data.data.length > 0) {
							var html = "";
							//如果传入true 则显示placeholder值
							if (isZero) {
								var placeholder = $(domId).attr("placeholder");
								html = "<option value=''>" + placeholder + "</option>" + html;
							}
							var list = data.data;
							for (var i = 0; i < list.length; i++) {
								html += "<option value=" + list[i].carriersno + ">" + list[i].carriersname + "</option>";
							}
							$(domId).html(html);
						}
					}
				}
			});
		},// 获取承运商 --结束

		// 获取派车单 --开始
		luRuDispatchCarReceipts: function (domId, isZero) {
			var requestURL = "/express/ajax/getSanHuoDispatchCarReceipts.do";
			$.ajax({
				url: requestURL,
				type: 'POST',
				dataType: 'json',
				data: {},
				contentType: "application/json",
				success: function (data) {
					if (data.callStatus == "success") {
						if (data.data && data.data.length > 0) {
							var html = "";
							//如果传入true 则显示placeholder值
							if (isZero) {
								var placeholder = $(domId).attr("placeholder");
								html = "<option value=''>" + placeholder + "</option>" + html;
							}
							var list = data.data;
							for (var i = 0; i < list.length; i++) {
								html += "<option value=" + list[i].id + ">" + list[i].receiptNo + "</option>";
							}
							$(domId).html(html);
						}
					}
				}
			});
		},// 获取派车单 --结束

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
	}

	global.addressSelect = JiLian.addressSelect;
	global.systemStopSelect = JiLian.systemStopSelect;
	global.systemPointSelect = JiLian.systemPointSelect;
	global.systemOwnerSelect = JiLian.systemOwnerSelect;
	global.systemYuFuOwnerSelect = JiLian.systemYuFuOwnerSelect;
	global.systemWarehouseSelect = JiLian.systemWarehouseSelect;
	global.systemPropertyCascade = JiLian.systemPropertyCascade;
	global.systemUserSelect = JiLian.systemUserSelect;
	global.systemCarriers = JiLian.systemCarriers;
	global.luRuDispatchCarReceipts = JiLian.luRuDispatchCarReceipts;
	global.getJJDsByStopId = JiLian.getJJDsByStopId;
	global.systemTransportSelect = JiLian.systemTransportSelect;
	global.ssxStopKDYSelect = JiLian.ssxStopKDYSelect;
	global.locationSelect = JiLian.locationSelect;
	global.systemOwnerGoodsNoSelect = JiLian.systemOwnerGoodsNoSelect;
	global.systemModuleSelect = JiLian.systemModuleSelect;
	global.warehouseSelect = JiLian.warehouseSelect;
	global.sectionSelect = JiLian.sectionSelect;
	global.systemGoodsNoSelect = JiLian.systemGoodsNoSelect;
})(Higo);

