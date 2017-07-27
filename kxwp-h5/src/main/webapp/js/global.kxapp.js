// 这里定义一些全局函数，便于调用
var KxApp = {
	// 格式化金钱
	formatMoney: function(value) {
	    // var intNum = Math.floor(value).toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,');
	    var intNum = Math.floor(value);
	    var decimalNum = Math.floor(value * 100) % 100;
	    if (decimalNum.toString().length == 1) {
	        decimalNum = '0' + decimalNum;
	    }
	    formattedValue = intNum + '.' + decimalNum;
	    return formattedValue;
  	},
  	// 扩展ajax的函数
    ajax: function(request) {
    	var app = this;
    	var oldAjax = $.ajax;

    	request.toStringify = request.toStringify || true;
        request.dataType = request.dataType || 'json';
        request.contentType = request.contentType || 'application/json';
        request.cache = false;
        if(request.dataType == 'json') {
            request.xhrFields = {
                withCredentials: true
            }
        }

       	if(request.data) {
       		var formatData = {};
       		for(var key in request.data) {
       			formatData[key] = $.trim(request.data[key]);
       		}
       		request.data = formatData;
       	}

        if(request.data && request.toStringify) {
            request.data = JSON.stringify(request.data);
        }

        var oldSuccess = request.success;
        var oldError = request.error;

        request.success = request.error = null;
        var ajax = oldAjax.apply(app, arguments);
        var def = $.Deferred();
        ajax.done(function(data) {
            if(data.callStatus !== 'SUCCESS') {
                def.reject(ajax, data);
            } else {
                def.resolve(data);
            }
        });

        ajax.fail(function(ajax, textStatus, errorThrown) {
            def.reject(ajax, {});
        });

        def.done(function(data) {
            oldSuccess && oldSuccess(data);
            return data;
        });

        var reject = def.reject;
        def.reject = function(ajax, info) {
            var hasErrorTips = true;

            if (def.state() !== 'rejected') {
                def.fail(function(ajax, data) {
                    var msg = '网络错误，请重试！';

                    // if(data.code == 10100) { // 未登录
                    //     app.error('出错了', data.msg).then(function() {
                    //         location.reload();
                    //     });
                    // } else {
                        if(data.msg) {
                            msg = data.msg;
                        }
                        if(ajax.statusText !== 'abort' && hasErrorTips) {
                            app.error(msg);
                        }
                        oldError && oldError(data);
                    // }
                    return data;
                });
            }

            return reject.call(def, ajax, info, function(showError) {
                hasErrorTips = showError;
            });
        };

        var promise = def.promise();
        promise.abort = function() {
            ajax.abort.apply(ajax, arguments);
        };

        return promise;
    },

    // ko工具函数/必须引入underscore
    linkKnockout: function(vm, data, except) {
        var keys = _.keys(vm);

        except || (except = {});
        data || (data = {});

        _.each(keys, function(key) {
            if(!_.isUndefined(data[key]) && !except[key]) {
                vm[key](_.result(data, key));
            }
        });
    },

    // 错误提示
   	error: function(msg) {
   		common.msg(msg);
   	}
}