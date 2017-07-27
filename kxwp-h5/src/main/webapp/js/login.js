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
			     common.submit_success(url,JSON.stringify(data),success);
			     function success(data){
			    	 if(data.callStatus == "SUCCESS"){
	                    common.msg("登录成功 !");
	                    window.location.href="/h5/user/home.do";
                     }else if(data.callStatus=="FAIL"){
                        common.msg(data.message);
                     }
			     }
	         }) 
	       },
	       autoLogin:function(){
            var self = this;
             // 请求ajax
			     var url='/h5/user/ajax/autoLogin.do';
			     var data={};
			     common.submit_success(url,data,autosuccess);
			     function autosuccess(data){
			    	 if(data.callStatus == "SUCCESS"){
	                    window.location.href="/h5/user/home.do";
	                    common.msg('自动登录成功')
                     }else if(data.callStatus == "FAIL"){
                    	 //common.msg('自动登录失败，请登录');
                     }else{
                    	 common.msg('自动登录中...');
                     }
			     }
        	},
        	/*滚动*/
	    	 /*滚动条*/
	    	  scroll:function(){
	    		  function loaded () {
	    			  var myScroll = new IScroll('.content', { scrollX: true, freeScroll: true });
	    		  }
	    		  loaded();
	    	  },
	       init:function(){
	       	var self=this;
	       	//self.scroll();
	       	self.autoLogin();
	       	self.loginCheck();
	       }
    }
    return login;
})();

$(document).ready(function() {
	Login.init();
});