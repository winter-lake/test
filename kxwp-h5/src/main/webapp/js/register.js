;//document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);  
var Register = (function () {
    var register = {
    	
    	//初始化级联
    	select:function(){
    		var url='/h5/public/ajax/queryRegion.do';
    		/*初始化省级*/
    		province={"pager":{
    					    "pageSize": 10,
    					    "totoalResults": 0,
    					    "currentPage": 1,
    					    "totalPages": 0
    					  },
    					  "parentRegionID": 0
    			  }
    		common.submit_success(url,JSON.stringify(province),getprovince);
    		function getprovince(data){
    			var province=data.data;
    			var html="<option>请选择</option>";
    			$.each(province,function(i,val){
    				html+='<option value="'+val.id+'">'+val.name+'</option>'
    			})
    			$('#province').html(html);
    		}
    		/*初始化市级*/
    		$("#province").change(function(){
    			var cityid=$("#province").val();
    			var city={"pager":{
				    "pageSize": 10,
				    "totoalResults": 0,
				    "currentPage": 1,
				    "totalPages": 0
				 	},
				 	"parentRegionID": cityid
    			}
    			common.submit_success(url,JSON.stringify(city),getcity);
    		})
    		function getcity(data){
    			var city=data.data;
    			var html="<option>请选择</option>";
    			$.each(city,function(i,val){
    				html+='<option value="'+val.id+'">'+val.name+'</option>'
    			})
    			$('#city').html(html);
    		}
    		/*初始化区级*/
    		$("#city").change(function(){
    			var areaid=$("#city").val();
    			console.log(areaid)
    			var area={"pager":{
				    "pageSize": 10,
				    "totoalResults": 0,
				    "currentPage": 1,
				    "totalPages": 0
				 	},
				 	"parentRegionID": areaid
    			}
    			common.submit_success(url,JSON.stringify(area),getarea);
    		})
    		function getarea(data){
    			var area=data.data;
    			var html="<option>请选择</option>";
    			$.each(area,function(i,val){
    				html+='<option value="'+val.id+'">'+val.name+'</option>'
    			})
    			$('#area').html(html);
    		}
    		/*初始化街道*/
    		$("#area").change(function(){
    			var street=$("#area").val();
    			var street={"pager":{
				    "pageSize": 10,
				    "totoalResults": 0,
				    "currentPage": 1,
				    "totalPages": 0
				 	},
				 	"parentRegionID": street
    			}
    			common.submit_success(url,JSON.stringify(street),getstreet);
    		})
    		function getstreet(data){
    			var street=data.data;
    			var html="<option>请选择</option>";
    			$.each(street,function(i,val){
    				html+='<option value="'+val.id+'">'+val.name+'</option>'
    			})
    			$('#street').html(html);
    		}
    		
    	},
    	//查看密码
    	open_pwd:function(){
    		$("#open-pwd").click(function(){
    			if($(this).hasClass('close')){
			      $(this).removeClass('close').addClass('open');
			      $('#y-password').attr('type','text');
			   }else{
			   	  $(this).removeClass('open').addClass('close');
			   	  $('#y-password').attr('type','password');
			   }
    		})
    	},
    	//发送验证码
    	sendyzm:function(){
			$("#sendyzm").on('click',checkCode);
	         function checkCode(){
	           var phone=$.trim($('#user').val());
	           if(!common.checkPhone(phone)){
				  return;
			   }
			   var url='/h5/public/ajax/sendRegisterSMSCode.do';
			   // ajax
			   common.submit_success(url,JSON.stringify({"mobile":phone,"template":"SMS_CODE"}),getSuccess);
			   // 发送验证码成功
			    var time=60,timer=null,codeBtn=$('#sendyzm');
				function getSuccess(data){
				  if(data.callStatus=="FAIL"){
					  common.msg(data.message);
				  }else if(data.callStatus=="SUCCESS"){
					    common.msg('发送验证码成功！');
					    codeBtn.off('click',checkCode);
					    codeBtn.css({"background":"#ccc","color":"#000"});
					    // 倒计时
					    timer=setInterval(function(){
					    	time--;
					        if(time<=0){
					          clearInterval(timer);
					          codeBtn.text('获取验证码');
					          codeBtn.css({"background":"#ffa200","color":"#fff"});
					          times=60;
					          codeBtn.on('click',checkCode);
					        }else{
					          codeBtn.text(time+'秒后重试');
					        }
					    },1000)
				  }
				}
	        }
    	},
    	//验证注册
    	check:function(){
			$("#register").click(function(){
				var user=$.trim($("#user").val());
    			var yzm=$.trim($("#y-zm").val());
    			var password=$.trim($('#y-password').val());
    			var supermarketName=$.trim($("#supermarketName").val());
    			var man=$.trim($("#man").val());
    			//收货人手机号,与主持手机号一致
    			var phone=user;
    			var province=$.trim($("#province").val());
    			var city=$.trim($("#city").val());
    			var area=$.trim($("#area").val());
    			var town=$.trim($("#street").val());
    			var address=$.trim($("#addre").val());
    			if(!common.checkPhone(user)){
			       return;
			    }
    			if(!common.checknull("验证码",yzm)){
 			       return;
 			    }
    			if(!common.checkPwd(password)){
 			       return;
 			    }
    			if(!common.checknull("超市名称",supermarketName)){
  			       return;
  			    }
    			if(!common.checknull("收货人",man)){
   			       return;
   			    }
    			if(!common.checkPhone(phone)){
 			       return;
 			    }
    			if(!common.checknull("市",city)){
			       return;
			    }
    			if(!common.checknull("区",area)){
			       return;
			    }
    			if(!common.checknull("街道",town)){
 			       return;
 			    }
    			if(!common.checknull("详细地址",addre)){
			       return;
			    }
				
				// 请求ajax
			    var url='/h5/user/ajax/registerSupermarketAccount.do';
			    var data={
						accountMobile : user,
						mobile : user,
						password : password,
						receptionName : man,
						smsCode:yzm,
						province : province,
						city : city,
						country : area,
						town     : town,
						supermarketName : supermarketName,
						address:address
				}
			    common.submit_success(url,JSON.stringify(data),success);
			    function success(data){
			     	if(data.callStatus=="SUCCESS"){
			     		common.msg("注册成功!");
			     		window.location.href="/h5/user/home.do";
			     	}else if(data.callStatus=="FAIL"){
			     		common.msg(data.message);
			     	}	
			    }
			})
    	},
    	
    	
    	//初始化函数
	     init:function(){
	     	var self=this;
	     	self.check();
	     	self.select();
	     	self.open_pwd();
	     	self.sendyzm();
	     }
    }
    return register;
})();

$(document).ready(function() {
	Register.init();
});