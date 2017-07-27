$(document).on('touchmove',function(e){e.preventDefault();})
var Stepone = (function () {
    var stepone = {
    	   //是否激活按钮
	       Check:function(){
	       	     $('#phone').on('propertychange input',check);//检测手机号
			     $('#y-zm').on('propertychange input',check);//检测验证码
			     //检测是否为空
		       	 function check(){
				    var phone=$.trim($('#phone').val());
				    var yzm=$.trim($('#y-zm').val());
				    if(phone && yzm){
				      $('#next').addClass('activ');
				    }else{
				      $('#next').removeClass('activ');
				    }
				 }
	       },
	       //发送验证码
	       sendyzm:function(){
	    	   $('#hqyzm').on('click',checkCode);//发送验证码
		         function checkCode(){
	         	   var phone=$.trim($('#phone').val());
				   var url='/h5/public/ajax/sendForgetPasswdSMSCode.do';
				   if(!common.checkPhone(phone)){
				      return;
				   }
				   common.submit_success(url,JSON.stringify({"mobile":phone,"template":"SMS_CODE"}),getSuccess);
		         }
		        // 发送验证码成功
		       	function getSuccess(data){
				  var time=60,timer=null,codeBtn=$('#hqyzm');
				  if(data.callStatus=="FAIL"){
					  common.msg(data.message);
				  }else if(data.callStatus=="SUCCESS"){
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
				  }
				}
	       },
	       isnext:function(){
	    	    $("#next").on('click',checknext);//进入下一步
	    	    //判断是否可以进入下一步
				function checknext(){
					var yzm=$("#y-zm").val();
					var phone=$("#phone").val();
					if(!common.checkPhone(phone)){
					   return;
					}
					if(!common.checknull("验证码",yzm) || !common.checkhan(yzm)){
	  			       return;
	  			    }
					var data={
						smsCode : yzm,
						mobile  : phone
					}
					url="/h5/user/ajax/forgetPasswordAndverifySMSCode.do";
					common.submit_success(url,JSON.stringify(data),getSuccess);
					function getSuccess(data){
						if(data.callStatus=="SUCCESS"){
							window.location.href="/h5/user/gotoForgotPasswd_next.do?passKey="+data.data.passKey;
						}
						if(data.callStatus=="FAIL"){
							common.msg(data.message);
						}
					}
				}
	       },
	       //初始化页面
	       init:function(){
		       	var self=this;
		       	self.sendyzm();
		       	self.isnext();
		       	self.Check();
	       }
    }
    return stepone;
})();

$(document).ready(function() {
	Stepone.init();
});