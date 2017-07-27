$(document).on('touchmove',function(e){e.preventDefault();})
var Cz = (function () {
    var cz = {
	       czCheck:function(){
       	     $('#phone').on('propertychange input',check);//检测手机号
		     $('#y-zm').on('propertychange input',check);//检测验证码
		     $('#hqyzm').on('click',checkCode);//发送验证码
		     $("#next").on('click',checknext);//进入下一步
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
	       	 //发送验证码
	         function checkCode(){
         	   var phone=$.trim($('#phone').val());
			   var code=$.trim($('#code').val());
			   var url='register.php';
			   if(!common.checkPhone(phone)){
			      return;
			   }
			   // post
			   common.submit_success(url,{"phone":phone},getSuccess,getError);
	         }
	          // 发送验证码成功
			function getSuccess(data){
			  var time=60,timer=null,codeBtn=$('#hqyzm');
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
			//判断是否可以进入下一步
			function checknext(){
				var code=$("#y-zm").val();
				if($(this).hasClass('activ')){
					if(code!=testCode){
						common.msg('验证码输入有误！')
					}
					window.location.href="cz_password_n.html";
				}
			}
	       },
	       init:function(){
	       	var self=this;
	       	self.czCheck();
	       }
    }
    return cz;
})();

$(document).ready(function() {
	Cz.init();
});