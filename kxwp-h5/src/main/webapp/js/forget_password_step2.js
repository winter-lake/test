$(document).on('touchmove',function(e){e.preventDefault();})
var Newpwd = (function () {
    var newpwd = {
	       newpwdCheck:function(){
       		 //检测是否为空
       		 $('#n_pwd').on('propertychange input',check);
			 $('#sure_pwd').on('propertychange input',check);
	       	 function check(){
			    var npwd=$.trim($('#n_pwd').val());
			    var surepwd=$.trim($('#sure_pwd').val());
			    if(npwd && surepwd){
			      $('#sure').addClass('activ');
			    }else{
			      $('#sure').removeClass('activ');
			    }
			 } 
	         $("#sure").on("click",function(){
	         	var npwd=$.trim($("#n_pwd").val()),
	         		surepwd=$.trim($("#sure_pwd").val()),
	         		passkey=$("#passkey").val();
			     if(!common.checkPwd(npwd)){
			       return;
			     }
			     if(npwd!=surepwd){
			     	common.msg('两次密码不一致！');
			     	return;
			     }
			     //post
			      // 请求ajax
			     var url='/h5/user/ajax/forgetAndModifyPassWord.do';
			     var data={"modify_password":surepwd,"passKey":passkey};
			     common.submit_success(url,JSON.stringify(data),success);
			     function success(data){
			    	 common.msg('修改密码成功');
			    	 window.location.href="/h5/user/index.do";
			     }
	         }) 
	       },
	       init:function(){
		       	var self=this;
		       	self.newpwdCheck();
	       }
    }
    return newpwd;
})();

$(document).ready(function() {
	Newpwd.init();
});