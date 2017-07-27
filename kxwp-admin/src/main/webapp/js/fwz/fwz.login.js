var fwz_index = (function () {
    var index = {
        // 登录函数
        login:function(){
            var self = this;
            // 按钮正在登录
            $(".login").html('<i class="fa fa-spinner fa-spin"></i> 正在登录..');
            var user_name = $.trim($("#login_mobile").val());
            var user_pass = $.trim($("#login_password").val());
            var ajax_data = {
            		login_mobile:user_name,
            		login_password:user_pass
            }
            $.ajax({
                url: '/fwz/user/ajax/login.do',
                type: 'post',
                dataType: 'json',
                contentType:"application/json",
                data: JSON.stringify(ajax_data),
                global:false,
                success:function(data){
                    if(data.callStatus == "SUCCESS"){
                        $(".login").html('<i class="fa fa-check"></i> 登录成功');
                        self.loginSuccess(data);
                    }else{
                    	 Common.kxpw_tishi(data.message);
                    	 $("#login_mobile").focus();
                        $(".login").html('登 录');
                    }
                },
                error:function(){
                    $(".login").html('登录失败,请重试');
                }
            });
        },

        // 自动登录
        autoLogin:function(){
            var self = this;
            $.ajax({
                url: '/fwz/user/ajax/autoLogin.do',
                type: 'post',
                dataType: 'json',
                data: {},
                global:false,
                success:function(data){
                    if(data.callStatus == "SUCCESS"){
                        self.loginSuccess(data);
                    }else{
                    	  $("#login_mobile").focus();
                    }
                },
                error:function(){
                    //$("#loginBtn").html('登录失败,请重试');
                }
            });
        },

        // 登录成功
        loginSuccess:function(data){
        	window.location.href='/fwz/user/gotoDashboard.do';      
        },
        
        validLogin:function(){
        	return $('#fwz_login_form').validationEngine('validate');
        },

        // 初始化页面的函数
        initPage:function(){
            var self = this;
            // 先自动登录
            self.autoLogin();
            //绑定表单验证
            Common.validator($("#fwz_login_form"));
            // 绑定快捷键
            $("#login_mobile").on('keyup', function(event) {
                if(event.keyCode == 13){
                    var user_name = $.trim($("#login_mobile").val());
                    var user_pass = $.trim($("#login_password").val());
                    if(user_name !== "" && user_pass !== ""){
                        $(".login").trigger('click');
                    }else{
                        if(user_name !== ""){
                            $("#login_password").focus();
                        }
                    }
                }
            });
            $("#login_password").on('keyup', function(event) {
                if(event.keyCode == 13){
                    var user_name = $.trim($("#login_mobile").val());
                    var user_pass = $.trim($("#login_password").val());
                    if(user_name !== "" && user_pass !== ""){
                        $(".login").trigger('click');
                    }
                }
            });
            // 绑定登录事件
            $(".form_btn").on('click', '.login', function(event) {
                event.preventDefault();
                if(self.validLogin()){
                    self.login();
                }
            });
            
        }
    }
    return index;
})();

$(document).ready(function() {
	fwz_index.initPage();
    setTimeout(function(){
        $("#login_mobile").focus();
    },300);
});