$(document).on('touchmove',function(e){e.preventDefault();})
var Login = (function () {
    var login = {
	       loginCheck:function(){
	       	 function check(){
			    var phone=$.trim($('#phone').val());
			    var pwd=$.trim($('#pwd').val());
			    if(phone && pwd){
			      $('#login').addClass('activ');
			    }else{
			      $('#login').removeClass('activ');
			    }
			 }
	       	 $('#phone').on('propertychange input',check);
			 $('#pwd').on('propertychange input',check);
	         $("#login").on("click",function(){
	         	var phone=$.trim($("#phone").val()),
	         		password=$.trim($("#pwd").val());
	         	 if(!common.checkPhone(phone)){
			       return;
			     }
			     if(!common.checkPwd(password)){
			       return;
			     }
			      // 请求ajax
			     var url='/h5/user/ajax/login.do';
			     var data={"login_mobile":phone,"login_password":password};
			     common.submit_success(url,data,success);
			     function success(data){
			     	//location.href="index.html"
			     	console.log(data)
			     }
	         }) 
	       },
	       autoLogin:function(){
            var self = this;
             // 请求ajax
			     var url='/h5/user/ajax/login.do';
			     var data={};
			     common.submit_success(url,data,autosuccess);
			     function autosuccess(data){
			     	//location.href="index.html"
			     	console.log(data)
			     }
        	},
	       init:function(){
	       	var self=this;
	       	self.autoLogin();
	       	self.loginCheck();
	       }
    }
    return login;
})();

$(document).ready(function() {
	Login.init();
});