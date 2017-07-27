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
        	//结束加载动画
        	self.end_loading();
           callback(rs);
        },
        error:function(){
        	self.end_loading();
        	self.msg("网络繁忙...");
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
        	self.end_loading();
           callback(rs);
        },
        error:function(){
           if(typeof errorfn=='function'){
              errorfn();
           }else{
        	   self.end_loading();
           	   self.msg("网络繁忙...");
           }     
        }
     })
  }
	//开始加载层
	 this.start_loading=function(){
	 	layer.open({
	 		style:'background:url(../../images/loading.gif) no-repeat center center;background-size:30px;border:none;box-shadow:none',
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
  //iscroll滚动
  this.scroll=function(id){
    myScroll = new IScroll('id');
  }
}
var common=new Common();
// 解析地址栏中的参数，返回形式为对象
function getParams(urlstr){
   var obj={},arr,i;
   urlstr=urlstr.substr(1);
   if(urlstr){
     arr=urlstr.split('&');
     for(i=0;i<arr.length;i++){
        var tempArr=arr[i].split('=');
        obj[tempArr[0]]=tempArr[1];
     }
     return obj;
   }else{
     return false;
   }
}

// 计算要显示的日期
function get_date(i,option){
   var now=option?new Date(option.year,option.month,option.day):new Date(); //现在的日期时间
   i=i?86400*1000*i:0;   // i天的毫秒
   var futrueDay=new Date();  // 未来的日期时间
   var futrueTime=now.getTime()+i;
   futrueDay.setTime(futrueTime);
   var month=futrueDay.getMonth()+1;
   var day=futrueDay.getDate();
   if(month<10){
     month='0'+month;
   }
   if(day<10){
     day='0'+day;
   }
   return futrueDay.getFullYear()+'-'+month+'-'+day;
}

// 显示日历插件
function showCalendar(obj,beginDate,maxDate,action){
   obj.calendar({
      minDate:beginDate,
      maxDate:maxDate,
      swipeable:true,
      hide:function(){
         changeDateout(action)
      }
   }).calendar('show');
   $('.shadow').remove();
   $('.ui-slideup-wrap').addClass('calenderbox');
   var shadow=$('<span class="shadow"></span>');
   $('.calenderbox').append(shadow);
   $('.ui-slideup').addClass('calender');
}

// 日期的转换(将字符串的日期转换为数值型的日期)
function changeType(datestr){
   datestr=datestr.split('-');
   return datestr[0]+datestr[1]+datestr[2];
}

// 列表页和内容页的显示日期
function renderDate(dIn,dOut){
   var inMonDay=removeYear(dIn),
       outMonDay=removeYear(dOut);
   $('#inText').text(inMonDay);
   $('#outText').text(outMonDay);
   // 给入住和离店的隐藏域设置值
   $('#date-in').val(dIn);
   $('#date-out').val(dOut);
   // 点击修改按钮-弹出日历组件
   $('#modify').on('tap',function(){
      var type=$(this).attr('type'),
          now=new Date(),
          beginDate=new Date(now.getFullYear(),now.getMonth(),now.getDate()),
          maxDate=new Date(now.getFullYear(),now.getMonth(),now.getDate()+90),
          ele=$('#date-in');
          showCalendar(ele,beginDate,maxDate,type);
   })
}

// 日历组件隐藏时执行的函数
function changeDateout(action){
  console.log(action);
  // 修改显示的入住日期
  $('#inText') && ($('#inText').text(removeYear($('#date-in').val())));
  // 转换为数字的入住日期和离店日期 
  var inDate=$('#date-in').val();
  var outDate=$('#date-out').val();
  var inNum=changeType(inDate);
  var outNum=changeType(outDate);
  var newOut;
  if(inNum>=outNum){
     newOut=get_date(1,{year:getArr(inDate)[0],month:getArr(inDate)[1]-1,day:getArr(inDate)[2]})
  }else{
     newOut=outDate;
  }
   // 修改离店的隐藏域的值
  $('#date-out').val(newOut);
   // 修改显示的离店日期
  $('#outText') && ($('#outText').text(removeYear(newOut)));
  if(!action){
    return false;
  }
   post_data.date_in=inDate;
   post_data.date_out=$('#date-out').val();
   // 如果是列表页
   if(action=='list'){
      showHotelList();
   }/*else if(action=='detail'){
      // showHotelDetail();
   }*/
}

// 将年月日转换为数组
function getArr(str){
  return str.split('-');
}

function removeYear(dateArg){
   // dateArg='2015-01-13'    01月13日
   var arr=dateArg.split('-'),
       mon=arr[1].charAt(0)=='0'?arr[1].charAt(1):arr[1],
       day=arr[2].charAt(0)=='0'?arr[2].charAt(1):arr[2];
   return mon+'月'+day+'日';
}

// 获取地址栏的信息
function getUrl(){
  return window.location.search;
}

// 判断用户是否登录
function if_logined(callback){
   var url='checkusers.php';
   var data={};
   common.access_server(url,data,checkLogin);
   function checkLogin(rs){
     // 如果没有登录
     if(rs.data==0){
        sessionStorage.setItem('target',getUrl());
        location.href='login.html';  
     }else{
        callback();
     }
   }
}