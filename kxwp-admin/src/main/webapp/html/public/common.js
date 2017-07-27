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
		        dataType:'json',
		        async:isAsync,
		        success:function(rs){
		           callback(rs);
		        },
		        error:function(){
		           alert('网络繁忙请重试...')
		        }
		     })
		},
		/*post请求*/
		post:function(url,data,callback){
			var isAsync=data.async?data.async:true;
			$.ajax({
		        url:url,
		        type:'post',
		        data:data,
		        dataType:'json',
		        async:isAsync,
		        success:function(rs){
		          	callback(rs);
		        },
		        error:function(){
		            alert('网络繁忙请重试...')
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
			var reg=/^\w{6,12}$/;
		    if(!reg.test(val)){
		      return false;
		    }
		    return true;
		},
		/*分页*/
        paging:function(){
            $('.paging li').click(function(){
                var val=$(this).text();
                $('.pagecount').val(val);
            })
            $('#wave_showlength').change(function(){
              var pagenum=Number($(this).find("option:selected").text());
                $('.pagenumber').val(pagenum);
            })
            $('#top_wavePagenation li').click(function(){
              $(this).addClass('active').siblings().removeClass('active');
            })
            if("${exchangeData.pager.currentPage }==1"){
            	$('.first').attr('disabled');
            	$('.prev').attr('disabled');
            }
            if("${exchangeData.pager.totalPages }==${exchangeData.pager.currentPage }"){
            	$('.last').attr('disabled');
            	$('.next').attr('disabled');
            }
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
        /*注销账户，重置密码，等功能*/
        zx:function(){
        	$('#zx').change(function(){
        		var xzdz=$("#zx option:selected").val();
	        	if(xzdz=="注销账户"){
	        		layer.confirm('确定要退出吗？',{
                          btn: ['确定','取消'] //按钮
                        },function(){
                          window.location.href="/zz/user/logout.do"
                    });
	        	}
	        	if(xzdz=="重置密码"){
	        		window.location.href="/zz/userManage/goReset.do";
	        	}
        	})
        },
        /*获取验证码*/
        hqyzm:function(o){
        	var wait=60;
        	function yzm(o){
			    if (wait == 60) {
			    	/*ajax发送请求*/
			        $.ajax({
			        	url:'',
			        	type:'post',
			        	success:function(){
			        		
			        	}
			        })
			    }
			    if (wait == 0) {
			        o.removeAttr("disabled");         
			        o.html("获取手机校验码");
			        wait = 60;
			    } else {
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
        kxpw_tishi:function(option,color){
        	layer.alert(option, {
              skin: 'layui-layer-molv' //样式类名
              ,closeBtn: 0
            });
        },
        /*询问框*/
        kxwp_xunwen:function(option){
        	/*如果选中提示是否批量操作*/
            layer.confirm(option,{
                  btn: ['确定','取消'] //按钮
                },function(){
                  Common.post(url,data)
            });
        },
        /*分页2*/
        paging2:function(totalPages,totalRecords,pageNo){
        	function getParameter(name) { 
				var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); 
				var r = window.location.search.substr(1).match(reg); 
				if (r!=null) return unescape(r[2]); return null;
			}
			var totalPage = 20;
			var totalRecords = 390;
			var pageNo = getParameter('pno');
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
					// do something
					//手动选中按钮
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
			})
        },
        /*日历组件*/
        date:function(rs){
        	$(rs).focus(function(){
        		WdatePicker();
        	})
        },
        /*快消互联select2*/
        select2:function(rs){ 	
        	
			
        }
	}
	return common;
})();

