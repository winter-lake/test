$(document).on('touchmove',function(e){e.preventDefault();})
var Register = (function () {
    var register = {
    	//初始化级联
    	cxselect:function(){
    		$('#element_id').cxSelect({
			  url: 'js/cityData.min.json',               // 如果服务器不支持 .json 类型文件，请将文件改为 .js 文件
			  selects: ['province', 'city', 'area'],  // 数组，请注意顺序
			  emptyStyle: 'none'
			});
    	},
    	//查看密码
    	open_pwd:function(){
    		$("#open-pwd").click(function(){
    			if($(this).hasClass('close')){
			      $(this).removeClass('close').addClass('open');
			      $('#password').attr('type','text');
			   }else{
			   	  $(this).removeClass('open').addClass('close');
			   	  $('#password').attr('type','password');
			   }
    		})
    	},
    	//是否激活按钮
    	check_null:function(){
    		function check(){
    			var user=$.trim($("#user").val()),
    				yzm=$.trim($("#y_zm").val()),
    				password=$.trim($("#password").val()),
    				supermarketName=$.trim($("#supermarketName").val()),
    				phone=$.trim($("#phone").val()),
    				province=$.trim($("#province option:selected").val()),
    				city=$.trim($("#city option:selected").val()),
    				area=$.trim($("#area option:selected").val()),
    				addre=$.trim($("#addre").val());
	    		if(user && yzm && password && supermarketName && phone && province && city && area && addre){
	    			$("#register").addClass("activ");
	    		}else{
	    			$("#register").removeClass("activ");
	    		}
    		}
    		$("#user").on('propertychange input',check);
    		$("#yzm").on('propertychange input',check);
    		$("#password").on('propertychange input',check);
    		$("#supermarketName").on('propertychange input',check);
    		$("#phone").on('propertychange input',check);
    		$("#province").on('propertychange input',check);
    		$("#city").on('propertychange input',check);
    		$("#area").on('propertychange input',check);
    		$("#addre").on('propertychange input',check);
    	},
    	//发送验证码
    	sendyzm:function(){
			$("#sendyzm").on('click',checkCode);
	         function checkCode(){
	           var phone=$.trim($('#user').val());
			   var url='register.php';
			   if(!common.checkPhone(phone)){
			      return;
			   }
			   // post
			   common.submit_success(url,{"phone":phone},getSuccess,getError);
			   // 发送验证码成功
				function getSuccess(data){
				  var time=60,timer=null,codeBtn=$('#sendyzm');
				  if(data.error==0){
				    testCode=data.risg;
				    common.msg('发送验证码成功！');
				    codeBtn.off('click',checkCode);
				    // 倒计时
				    timer=setInterval(function(){
				    	time--;
				        if(time<=0){
				          clearInterval(timer);
				          codeBtn.text('获取验证码');
				          times=60;
				          codeBtn.on('click',checkCode);
				        }else{
				          codeBtn.text(time+'秒后重试');
				        }
				    },1000)
				  }else{
				     getError();
				  }
				}
				//发送验证码失败
				function getError(){
					common.msg('验证码发送失败！请重试');
				}
	        }
    	},
    	//验证
    	check:function(){
			$("#register").click(function(){
				var user=$.trim($("#user").val()),
    				yzm=$.trim($("#y_zm").val()),
    				password=$.trim($("#password").val()),
    				supermarketName=$.trim($("#supermarketName").val()),
    				phone=$.trim($("#phone").val()),
    				/*province=$.trim($("#province option:selected").val()),
    				city=$.trim($("#city option:selected").val()),
    				area=$.trim($("#area option:selected").val()),*/
    				addre=$.trim($("#addre").val());
				if(!common.checkPhone(user)){
			      return;
			    }
				if(!common.checkPwd(password)){
			      return;
			    }
				if(!common.checkPhone(phone)){
			      return;
			    }
				/*if(yzm!=testCode){
					common.msg("验证码不正确!");
					return;
				}*/
				if(!common.checknull('超市名称',supermarketName)){
					return;
				}
				if(!common.checknull('详细地址',addre)){
					return;
				}
				 // 请求ajax
			     var url='login.php';
			     var data={"phone":phone,"password":pwd};
			     common.submit_success(url,data,success);
			     function success(data){
			     	//location.href="index.html"
			     }
			})
    	},
    	
    	//初始化函数
	     init:function(){
	     	var self=this;
	     	self.check_null();
	     	self.cxselect();
	     	self.open_pwd();
	     	self.sendyzm();
	     	self.check();
	     }
    }
    return register;
})();

$(document).ready(function() {
	Register.init();
});