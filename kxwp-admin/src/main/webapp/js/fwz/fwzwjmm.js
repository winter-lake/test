$(document).ready(function() {
	Common.validator($('#wjmmform'));
})
		//发送验证码的点击事件
	   $(".hqyz").click(function(){
	    var mobile=$("#usermobile").val();
	    if(mobile!="" && mobile.length==11 && Common.checkPhone(mobile)){
	    	$.ajax({
				url:"/fwz/userManage/selectFwzMobileAlive.do?mobile="+mobile,
				type:'GET',
				dataType:'json',
				success:function(data)
				{
					if(data.callStatus == 'SUCCESS'){
						Common.hqyzm($("#fsyzm"));
						$.ajax({
							url:"/fwz/userManage/fwzgetSMSCode.do?mobile="+mobile,
					        type:'GET',
					        dataType:'json',
					        success:function(data){
					        	if(data.callStatus == 'SUCCESS'){
					        		Common.kxpw_tishi('验证码发送成功，请查收')
					        	}else
					        	{Common.kxpw_tishi(data.message)
					        		return false;}
					        }
					    });
					}
					else
					{
						Common.kxpw_tishi('发送验证码失败，该账号不存在')
						return false;
					}
				}
			})
			return false;
		}
	    else
		{
	    	if(mobile=="" || mobile==null){
	    		Common.kxpw_tishi('请输入绑定的手机号');
	    		return false;
	    	}
	    	if(!Common.checkPhone(mobile)){
	    		Common.kxpw_tishi('输入的手机号非法');
	    		return false;
	    	}
		}
	   })
	    
	    //整个表单的点击事件
	    $(".qdxg").click(function(){
	    	 $('#wjmmform').validationEngine('validate');
			var mobile = $('#usermobile').val();
			var newpwd = $('#newpwd').val();
			var repwd= $('#repwd').val();
			var smscode= $('#smscode').val();
			var pattern = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$/;
			if(Common.checkPhone(mobile) && smscode!="" && pattern.test(newpwd) && pattern.test(repwd)){
				$.ajax({
					url:"/fwz/userManage/fwzcheckSMSCode.do?mobile="+mobile+"&SMScode="+smscode,
			        type:'GET',
			        dataType:'json',
			        success:function(data){
			        	if(data.callStatus == 'SUCCESS'){
			        		Common.kxpw_tishi("设置新密码成功");
			        		 $("#wjmmform").submit();
			        	}
			        	else
			        	{
			        		Common.kxpw_tishi(data.message);
			        		return false;
			        	}
			        }
			    })
			}
			else{
				if(mobile=="" || mobile==null){
		    		Common.kxpw_tishi('请输入绑定的手机号');
		    		return false;
		    	}
		    	if(!Common.checkPhone(mobile)){
		    		Common.kxpw_tishi('输入的手机号非法');
		    		return false;
		    	}
			}
})
