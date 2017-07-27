// 公共库
function Common(){
  var self=this;
  // get异步请求的ajax
  this.access_server=function(url,option,callback){
     var isAsync=option.async?option.async:true
     // 加载载入动画
     self.start_loading();
     $.ajax({
        url:url,
        type:'get',
        dataType:'json',
        contentType:"application/json",
        async:isAsync,
        success:function(rs){
        	if(rs && rs.callStatus == "SUCCESS"){
        		if(rs.authStatus=="Y"){
        			
        		}else if(rs.authStatus=="N"){
        			common.msg(rs.message);
        			window.parent.location.href="/";
        		}
        	}else if(rs && rs.callStatus=="FAIL"){
        		if(rs.message) {
        			common.msg(rs.message);
        		}else{
        			common.msg("系统异常!");
        		}
        	}
        	self.end_loading();
            callback(rs);
        },
        error:function(){
        	self.end_loading();
        	self.msg("网络繁忙，请重试");
        }
     })
  }

  // post请求
  this.submit_success=function(url,data,callback,errorfn){
     var isAsync=data.async?data.async:true
     // 加载载入动画
     self.start_loading();
     $.ajax({
        url:url,
        type:'post',
        data:data,
        dataType:'json',
        contentType:"application/json",
        async:isAsync,
        success:function(rs){
        	if(rs && rs.callStatus == "SUCCESS"){
        		if(rs.authStatus=="Y"){
        			
        		}else if(rs.authStatus=="N"){
        			common.msg(rs.message);
        			window.parent.location.href="/";
        		}
        	}else if(rs && rs.callStatus=="FAIL"){
        		if(rs.message) {
        			common.msg(rs.message);
        		}else{
        			common.msg("系统异常!");
        		}
        	}
        	self.end_loading();
            callback(rs);
        },
        error:function(){
    	   self.end_loading();
       	   self.msg("网络繁忙，请重试");
        }
     })
  }
	//开始加载层
	 this.start_loading=function(){
	 	layer.open({
	 		style:'background:url(../../../images/loading.gif) no-repeat center center;background-size:30px;border:none;box-shadow:none',
			shade:false,
			anim:true,
			content:""
		});
	 }
	//结束加载层
	 this.end_loading=function(){
	 	layer.closeAll('loading');
	 }
	//提示信息
	this.msg=function(msg){
		layer.open({
			style: 'border:none;color:#fff;background:#4f4e4e;',
			shade:false,
			anim:true,
			time:1,
  		    content:msg
		});
	}
  //  验证手机号码
  this.checkPhone=function(val){
    var reg=/^1[358]\d{9}/;
    if(!reg.test(val)){
      this.msg("请输入正确的手机号！");
      return false;
    }
    return true;
  }
  //验证非汉字
  this.checkhan=function(val){
	  var reg=/^[0-9]*$/;
	  if(!reg.test(val)){
		  this.msg("输入的格式有误");
		  return false;
	  }
  }
  // 身份证 
  this.checkCard=function(val){
    var reg=/^\d{17}(X|[0-9])$/;
    if(!reg.test(val)){
      this.msg('请输入有效的证件号码');
      return false;
    }
    return true;
  }
  // 邮箱
  this.checkEmail=function(val){
    var reg=/^\w+\.?@[a-z0-9]+\.(com|cn|net|com.cn|org|edu)$/;
    if(!reg.test(val)){
      this.msg('请输入有效的邮件地址');
      return false;
    }
    return true;
  }
  // 网址
  this.checkUrl=function(val){
    var reg=/^http(s)?:\/\/(www\.)?[a-z0-9]+\.(com|cn|net|com.cn|org|edu)\/?([a-z0-9]+)*\/?([a-z0-9]+\.[a-z]+)?$/;
    if(!reg.test(val)){
      this.msg('请输入有效的地址');
      return false;
    }
    return true;
  }
  // 密码
  this.checkPwd=function(val){
    var reg=/^\w{8,20}$/;
    if(!reg.test(val)){
      this.msg('请输入8-20位的密码');
      return false;
    }
    return true;
  }
  //不能为空
  this.checknull=function(msg,val){
  	var reg = /\S/;
  	if(!reg.test(val)){
  		this.msg(msg+'不能为空！');
  		return false;
  	}
  	return true;
  }
}
var common=new Common();
