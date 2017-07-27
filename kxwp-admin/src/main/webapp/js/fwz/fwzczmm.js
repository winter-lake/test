$(document).ready(function() {
	Common.validator($('.czmm'));
})

$("#czmmsub").click(function(){
	 $('.czmm').validationEngine('validate');
	 var new_password = $("#use_pwd").val();
	 var re_password  = $("#re_pwd").val();
	 var pattern=/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$/;
	 if(pattern.test(new_password) && pattern.test(re_password)){
		 Common.kxpw_tishi("重置密码成功");
		$(".czmm").submit();
	 }else{
		 return false;
	 }
	})


