




var Login = (function () {
    var login = {
        // 登录函数
        login:function(){
            var self = this;
            // 按钮正在登录
            $(".login").html('<i class="fa fa-spinner fa-spin"></i> 正在登录..');
            var user_name = $.trim($("#user_name").val());
            var user_pass = $.trim($("#pwd").val());
            var ajax_data = {
                loginUserNO:user_name,
                loginPassword:user_pass
            }
            $.ajax({
                url: '',
                type: 'post',
                dataType: 'json',
                contentType:"application/json",
                data: JSON.stringify(ajax_data),
                global:false,
                success:function(data){
                    if(data.callStatus == "success"){
                        $(".login").html('<i class="fa fa-check"></i> 登录成功');
                        self.loginSuccess(data);
                    }else{
                        $(".login").html('登 录');
                        if(data.message && data.message){
                            $("#loginError").html(data.message);
                            $(".higo-login-error").show();
                        }
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
                url: '/system/autologin.do',
                type: 'post',
                dataType: 'json',
                data: {},
                global:false,
                success:function(data){
                    if(data.callStatus == "success"){
                        self.loginSuccess(data);
                    }
                },
                error:function(){
                    $("#loginBtn").html('登录失败,请重试');
                }
            });
        },

        // 登录成功
        loginSuccess:function(data){
            if(data.sysUser.initialPassword){
            	// 是初始密码跳转密码修改页面
            	window.location.href='/system/toResetPassword.do';
            }else{
                // 根据是否是快递员工跳到对应的主页
                var userRole = data.sysUser;
                var userJob = userRole.userJob;
            	 if(userRole && userJob && userJob.rolecode){
                     if(userJob.rolecode == "FBYG"){
                         window.location.href = "/express/gotoQueryDeliveringExpresses.do";
                     }else{
                         window.location.href='/system/toMain.do';
                     }
                 }else{
                     window.location.href='/system/toMain.do';
                 }
            }        
        },

        // 验证函数
        validLogin:function(){
            var user_name = $.trim($("#user_name").val());
            var user_pass = $.trim($("#pwd").val());
            if(user_name !== "" && user_pass !== ""){
                return true;
            }else{
                layer.alert('用户名或密码不能为空',{
				  skin: 'layui-layer-molv' //样式类名
				  ,closeBtn: 0
				});
            }
        },

        // 初始化页面的函数
        initPage:function(){
            var self = this;
            // 先自动登录
            self.autoLogin();
            // 绑定快捷键
            $("#user").on('keyup', function(event) {
                if(event.keyCode == 13){
                    var user_name = $.trim($("#user").val());
                    var user_pass = $.trim($("#password").val());
                    if(user_name !== "" && user_pass !== ""){
                        $("#loginBtn").trigger('click');
                    }else{
                        if(user_name !== ""){
                            $("#password").focus();
                        }
                    }
                }
            });
            $("#password").on('keyup', function(event) {
                if(event.keyCode == 13){
                    var user_name = $.trim($("#user").val());
                    var user_pass = $.trim($("#password").val());
                    if(user_name !== "" && user_pass !== ""){
                        $("#loginBtn").trigger('click');
                    }
                }
            });
            // 绑定登录事件
            $(".higo-login-body").on('click', '#loginBtn', function(event) {
                event.preventDefault();
                if(self.validLogin()){
                    self.login();
                }
            });
            
        }
    }
    return login;
})();


