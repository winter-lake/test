$(document).on('pagecreate', function(event){FastClick.attach(document.body);});
// 所有的接口文档
var KxModel = {}

// 基本的model, 可用来存储一些页面的数据
KxModel.BaseModel = function(params, view) {
    params = params || {};
    var model = this;
    // 获取某些属性
    this.get = function(key) {
        return model[key];
    }
    // 设置属性
    this.set = function(key, value) {
        if(key) {
            model[key] = value;
            // 执行view对应的change函数
            if(view) {
                // 转换首字母
                var formatKey = key.replace(/\b(\w)(\w*)/g, function($0, $1, $2) {
                    return $1.toUpperCase() + $2;
                });
                var event_name = 'on' + formatKey + 'Change';
                if(view[event_name]) {
                    view[event_name](value);
                }
            }
        }
    }
    this.init = function() {
        for(var key in params) {
            model.set(key, params[key]);
        }
    };

    this.init();
};

// 购物车相关
KxModel.shoppingCart = {
    // 获取购物车详情函数
    fetch: function() {
        return $.ajax({
            url: '/h5/shoppingCart/ajax/getShoppingCartData.do',
            type: 'post',
            contentType:"application/json",
            dataType: 'json'
        })
    },
	// 添加到购物车
	addShoppingCart: function(infor) {
		var ajax_data = {
			items: infor.items || [] //商品数组
		}
		return $.ajax({
            url: '/h5/shoppingCart/ajax/addShoppingCart.do',
            type: 'post',
            contentType:"application/json",
            dataType: 'json',
            data: JSON.stringify(ajax_data)
        })
	},

    // 更新购物车
    update: function(data) {
        return $.ajax({
            url: '/h5/shoppingCart/ajax/updateShoppingCart.do',
            type: 'post',
            contentType:"application/json",
            dataType: 'json',
            data: JSON.stringify(data)
        })
    },

	// 获取购物车商品数量
	getCount: function(){
		return $.ajax({
            url: '/h5/shoppingCart/ajax/getCount.do',
            type: 'post',
            contentType:"application/json",
            dataType: 'json'
        })
	}
};

// 订单相关
KxModel.order = {
	getOrderList: function(ajax_data) {
		return $.ajax({
            url: '/h5/v2/order/ajax/querySalesOrderList.do',
            type: 'post',
            contentType:"application/json",
            data: JSON.stringify(ajax_data),
            dataType: 'json'
        })
	},
	submitOrder:function(ajax_data){
		return $.ajax({
            url: '/h5/order/ajax/addSalesOrder.do',
            type: 'post',
            contentType:"application/json",
            data: JSON.stringify(ajax_data),
            dataType: 'json'
        })
	},
    // 订单确认收货
    completeOrder:function(ajax_data){
        return $.ajax({
            url: '/h5/order/ajax/completeOrder.do',
            type: 'post',
            contentType:"application/json",
            data: JSON.stringify(ajax_data),
            dataType: 'json'
        })
    }
}

// 商品相关
KxModel.goods = {
    getGoodsList: function(ajax_data) {
        return $.ajax({
            url: '/h5/goods/ajax/searchGoods.do',
            type: 'post',
            contentType:"application/json",
            data: JSON.stringify(ajax_data),
            dataType: 'json'
        })
    }
}

//供应商相关
KxModel.supplier = {
    getsupplierList: function(ajax_data) {
        return $.ajax({
            url: '/h5/supplier/searchSupplier.do',
            type: 'post',
            contentType:"application/json",
            data: JSON.stringify(ajax_data),
            dataType: 'json'
        })
    },
    // 获取供应商推荐商品
    getsupplierGoodsList: function(ajax_data) {
        return $.ajax({
            url: '/h5/supplier/getRecommendGoods.do',
            type: 'post',
            contentType:"application/json",
            data: JSON.stringify(ajax_data),
            dataType: 'json'
        })
    }
}

//首页商品
KxModel.index = {
    getindexList: function(ajax_data) {
        return $.ajax({
            url: '/h5/goods/ajax/homeGoods.do',
            type: 'post',
            contentType:"application/json",
            data: JSON.stringify(ajax_data),
            dataType: 'json'
        })
    }
}

//h5公告列表
KxModel.announcemnet = {
    getannouncemnetList: function(ajax_data) {
        return $.ajax({
            url: '/h5/announcement/ajax/listAnnouncement.do',
            type: 'post',
            contentType:"application/json",
            data: JSON.stringify(ajax_data),
            dataType: 'json'
        })
    }
}

//APP公告列表
KxModel.appannouncemnet = {
    getappannouncemnetList: function(ajax_data) {
        return $.ajax({
            url: '/h5/app/announcement/ajax/listAnnouncement.do',
            type: 'post',
            contentType:"application/json",
            data: JSON.stringify(ajax_data),
            dataType: 'json'
        })
    }
}

//商品清单
KxModel.ordergoodslist = {
    getordergoodslist: function(ajax_data) {
        return $.ajax({
            url: '/h5/v2/order/ajax/queryOrderItemByBigOrderNo.do',
            type: 'post',
            contentType:"application/json",
            data: JSON.stringify(ajax_data),
            dataType: 'json'
        })
    }
}


//天天特价
KxModel.specials = {
    getspecials: function(ajax_data) {
        return $.ajax({
            url: '/h5/channel/ajax/listChannel.do',
            type: 'post',
            contentType:"application/json",
            data: JSON.stringify(ajax_data),
            dataType: 'json'
        })
    }
}


