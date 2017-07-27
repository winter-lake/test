/*
*此文件定义公用的函数
*/

var Common = (function(){
	var common = {
		/*get请求*/
		get:function(url,option,callback){
			var isAsync=option.async?option.async:true;
			$.ajax({
		        url:url,
		        type:'get',
		        contentType:"application/json",
		        dataType:'json',
		        async:isAsync,
		        success:function(rs){
		           callback(rs);
		        },
		        error:function(){
		           
		        }
		     })
		},
		/*post请求*/
		post:function(url,data,callback,errorfn){
			var isAsync=data.async?data.async:true;
			$.ajax({
		        url:url,
		        type:'post',
		        data:data,
		        contentType:"application/json",
		        dataType:'json',
		        async:isAsync,
		        success:function(rs){
		          	callback(rs);
		        },
		        error:function(){
		            
		        }
		     })
		},
		/*验证手机号*/
		checkPhone:function(val){
			var reg=/^1[358]\d{9}/;
		    if(!reg.test(val)){
		      return false;
		    }
		    return true;
		},
		/*验证邮箱*/
		checkEmail:function(val){
			var reg=/^\w+\.?@[a-z0-9]+\.(com|cn|net|com.cn|org|edu)$/;
		    if(!reg.test(val)){
		      return false;
		    }
		    return true;
		},
		/*验证网址*/
		checkUrl:function(val){
			var reg=/^http(s)?:\/\/(www\.)?[a-z0-9]+\.(com|cn|net|com.cn|org|edu)\/?([a-z0-9]+)*\/?([a-z0-9]+\.[a-z]+)?$/;
		    if(!reg.test(val)){
		      return false;
		    }
		    return true;
		},
		/*验证密码*/
		checkPwd:function(val){
			var reg=/^\w{8,20}$/;
		    if(!reg.test(val)){
		      return false;
		    }
		    return true;
		},
		
        table:function(){
        	/*全选/全不选*/
	        $('input[name="b"]').click(function(){
	           if($(this).is(':checked')){
	              $('input[name="a"]').prop('checked',true);
	           }else{
	              $('input[name="a"]').prop('checked',false);
	           }
	        })
	        /*点击其中一个取消全选*/
	        $('input[name="a"]').click(function(){
	        	$('input[name="b"]').prop('checked',false);
	        })
        },
        /*获取验证码*/
        hqyzm:function(o){
        	var wait=60;
        	function yzm(o){
			    if (wait == 0) {
			        o.removeAttr("disabled");         
			        o.html("获取验证码");
			        wait = 60;
			        $("#fsyzm").css("background","#50b0df");
			    } else {
			    	$("#fsyzm").css("background","#cccccc");
			        o.attr("disabled", true);
			        o.html("重新发送(" + wait + ")");
			        wait--;
			        setTimeout(function() {yzm(o)},1000)
			    }
        	}
        	yzm(o);
        },
        /*快消互联dialog*/
        /*提示框*/
        kxpw_tishi:function(option,title){
        	layer.alert(option, {
        	  title:title,
              skin: 'layui-layer-demo' //样式类名
              ,closeBtn: 0
            });
        },
        /*询问框*/
        kxwp_xunwen:function(option,title){
        	/*如果选中提示是否批量操作*/
            layer.confirm(option,{
            	  title:title,
                  btn: ['确定','取消'] //按钮
                },function(){
                 
            });
        },
        /*自定义弹框*/
        zdy_dialog:function(txt,callback){
        	var self=this;
        	layer.open({
	    		  type: 1,
	    		  title:txt,
	    		  skin: 'layui-layer-demo', //样式类名
	    		  shift: 2,
	    		  shadeClose: true, //开启遮罩关闭
	    		  content: '<textarea class="txt">fdf</textarea>',
	    		  btn:['确定','取消'],
	    		  shift: 0,
	    		  yes:function(index, layero){
	    			   var self=this;
	    			   
	    		  }
    		});    
        },
        /*loading层*/
        /*开始loading*/
    	startloading:function(){
    		ii=layer.msg('加载中', {icon: 16});
        },
        /*结束loading*/
        endloading:function(){
        	layer.close(ii);
        },
        /*表单验证插件
         * id:表单id
         */
        validator:function(ID){
        	$(ID).validationEngine({
                scroll:true//屏幕自动滚动到第一个验证没通过的地方
            });
        },
        /*图片查看器*/
        openimg:function(){
        	layer.photos({
	    	   photos: '#layer-photos-demo'
	    	});
        },
        /*分页*/
        paging:function(result,url,callback){   
        	var self=this;
        	debugger;
        	var pager=result.pager;
        	function getParameter(name) { 
        		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); 
        		var r = window.location.search.substr(1).match(reg); 
        		if (r!=null) return unescape(r[2]); return null;
        	}
        	var totalPage = pager.totalPages;
        	var totalRecords = pager.totoalResults;
        	var pageNo = getParameter(pager.currentPage);
        	if(!pageNo){
        		pageNo = 1;
        	}
        	//生成分页
        	//有些参数是可选的，比如lang，若不传有默认值
        	kkpager.generPageHtml({
        		pno : pageNo,
        		//总页码
        		total : totalPage,
        		//总数据条数
        		totalRecords : totalRecords,
        		mode : 'click',//默认值是link，可选link或者click
        		click : function(n){
        			var pager={
        				currentPage:n,//当前页码
        				pageSize   :2,//每页条数
        				totalPages :null,//总页码
        				totoalResults :null//总条数
        			 }
        			self.post(url,JSON.stringify(pager),callback);
        			this.selectPage(n);
        			return false;
        		}
        		/*
        		,lang				: {
        			firstPageText			: '首页',
        			firstPageTipText		: '首页',
        			lastPageText			: '尾页',
        			lastPageTipText			: '尾页',
        			prePageText				: '上一页',
        			prePageTipText			: '上一页',
        			nextPageText			: '下一页',
        			nextPageTipText			: '下一页',
        			totalPageBeforeText		: '共',
        			totalPageAfterText		: '页',
        			currPageBeforeText		: '当前第',
        			currPageAfterText		: '页',
        			totalInfoSplitStr		: '/',
        			totalRecordsBeforeText	: '共',
        			totalRecordsAfterText	: '条数据',
        			gopageBeforeText		: '&nbsp;转到',
        			gopageButtonOkText		: '确定',
        			gopageAfterText			: '页',
        			buttonTipBeforeText		: '第',
        			buttonTipAfterText		: '页'
        		}*/
        	});
        },
        /*日历组件*/
        date:function(rs){
        	$(rs).focus(function(){
        		WdatePicker();
        	})
        },
        /*模糊查询*/
        select2:function(id,url){
        	$(id).select2({  
        		placeholder: "请输入服务站",   
                ajax:   
                {  
                    url: url,
                    dataType: 'json', 
                    contentType:"application/json",
                    type: 'POST',  
                    delay: 250,  
                    data: function (params)   
                    {  
                        var param = {};  
                        param.stationName = params.term;                         
                        return JSON.stringify(param);  
                    },  
                    processResults: function (resp, page)   
                    {  
                        var array = new Array();  
                        if (resp.products)  
                        {  
                            for (var i = 0; i < resp.products.length; i++)  
                            {  
                                var product = resp.products[i];  
                                array.push({id:product.productId, text: product.productName});  
                            }  
                        }  
                        var ret = new Object();  
                        ret.results = array;                  
                        return ret;  
                    },  
                    cache: true  
                },   
                language: "zh-CN",  
            });
        },
        /*
         * 上传图片组件
         * [description] 参见 '/gys/goods/gotoAddGoods.do' 图片上传的dom结构
        */ 
        imageUpload: function(box) {
            // box: 包裹整个label的容器，里面必须包含type=file 的输入框，用样式隐藏
            // 如果有附加的参数，则直接包裹在box的type="hidden"的input里

            var boxDom = $(box);
            var fileDom = boxDom.find('input[type=file]');
            var fileBox = boxDom.find('.w_filebox');

            var max = boxDom.data('max') || 1; // 最多上传几张
            var count = boxDom.find(".w_view_pic").length || 0; // 上传图片计数

            // 如果已上传的和最大上传的相同，则隐藏上传表单
            if(max == count) {
                fileBox.hide();
            } else {
                fileBox.show();
            }
            
            var params = {}; // 附加的参数

            boxDom.find('input[type=hidden]').each(function(index, el) {
                params[$(el).attr("name")] = $(el).val();
            })

            // 初始化
            fileDom.fileupload({
                url: '/common/file/ajax/uploadFile.do',
                dataType: 'json',
                add: function (e, data) {
                    if(count >= max) {
                        Common.kxpw_tishi('最多上传' + max + '张');
                        return false;
                    }
                    data.formData = params
                    data.submit();
                },
                done: function (e, data) {
                    if(count >= max) {
                        Common.kxpw_tishi('最多上传' + max + '张');
                        return false;
                    }
                    count ++;
                    var result = data.result.data[0];
                    if(result){
                        var url = result.file_url;
                        boxDom.append('<label class="w_view_pic" data-file_key='+ result.file_key +'><img src='+ url +'><b class="del">X</b></label>');
                        if(count >= max) {
                            fileBox.hide();
                        }
                    }
                }
            });

            // 绑定删除事件
            boxDom.on('click', '.del', function(event) {
                event.preventDefault();
                var btn = $(this);
                var box = btn.parent('.w_view_pic'); // 当前图片
                var file_key = box.data("file_key");

                var ajax_data = params;
                ajax_data.file_key = file_key;
                CommonModel.delFile(ajax_data).done(function(data) {
                    box.remove();
                    count --; // 图片计数减1
                    fileBox.show();
                })
            });
        },
        /*鼠标和划过显示详细内容*/
        hovershow:function(obj,box){
        	var timer=null;
        	$(box).css({"background":"#fff","font-size":"12px","min-width":"50px","width":"250px","line-height":"20px","position":"absolute","top":"-200px","left":"0","z-index":"100","border":"1px solid #ccc","padding":"10px","border-radius":"5px"})
        	$(obj).on("mouseover",function(e){
        		clearTimeout(timer);
        		var clientX=e.clientX;
        		var clientY=e.clientY;
        		var txt=$(this).attr('text');
        		timer=setTimeout(function(){
        			$(box).css("left",clientX).css("top",clientY);
        			if(txt==""){
        				$(box).hide();
        			}else{
        				$(box).show();
        				$(box).html(txt);
        			}
        		},100)
        	})
        	$(obj).on("mouseout",function(){
        		$(box).hide();
        	})
        },
        // 显示大图的函数
        initProp: function(dom) {
            var box_h = 450; // 最大高度
            var box_w = 600; // 最大宽度

            $('body').on('click', dom, function(event) {
                var el = this;
                var src = $(el).data("src") || $(el).attr("src");
                var img = $(el)[0];
                // 获取元素位置，显示左右
                var body_width = $('body').width();
                var el_offset = $(el).offset();
                var el_left = el_offset.left;
                var el_right = body_width-el_left;
                // 获取图片尺寸
                var nWidth, nHeight
                if (img.naturalWidth) { // 现代浏览器
                    nWidth = img.naturalWidth
                    nHeight = img.naturalHeight
                } else { // IE6/7/8
                    var imgae = new Image()
                    image.src = img.src
                    image.onload = function() {
                        callback(image.width, image.height)
                    }
                }

                var options = {
                    // container: $(el).parent(),
                    placement: el_left > el_right ? 'left' : 'right',
                    trigger: 'manual',
                    html: true,
                    content: '<img src=' + src + ' style="width:'+ nWidth +'px;height:'+ nHeight +'px; max-height:'+ box_h +'px; max-width:'+ box_w +'px;" >'
                }
                // 显示后的回调
                $(el).on('shown.bs.popover', function () {

                    var propbox = $(this).siblings('.popover');
                    $(el).data('propid', propbox.attr("id"));
                    var prop_title = propbox.find('.popover-title');
                    var title_h = 0;
                    if(prop_title.length > 0) {
                        title_h = 35;
                    }
                    propbox.css({
                        "width": nWidth + 28,
                        "max-width": box_w + 28,
                        'height': nHeight + 18 + title_h,
                        'max-height': box_h + 18 + title_h
                    });

                    // 给body绑定隐藏这个弹出层的事件
                    $('body').on('click.hideprop'+$(el).data('propid'), function(event) {
                        event.preventDefault();
                        $(el).popover('hide');
                    });
                });
                // 隐藏后的回调
                $(el).on('hidden.bs.popover', function() {
                    $('body').off('click.hideprop'+$(el).data('propid'));
                });

                $(el).popover(options);

                $(el).popover('show');
            });
        }
	}
	return common;
})();

