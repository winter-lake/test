/*
	此处定义jquery validation 的自定义方法
*/ 
jQuery.validator.addMethod("selectNoZero", function(value, element) {
  if(value == "0" || value == null || value == "" ){
  	return false;
  }else{
  	return true;
  }
}, "请选择一个值");
   
 // 手机号码验证    
 jQuery.validator.addMethod("isMobile", function(value, element) {
     var length = value.length;
     return this.optional(element) || (length == 11 && /^1[2345678]\d{9}$/.test(value));
 }, "请正确填写您的手机号码");

jQuery.validator.addMethod("isNumber", function(value, element) {
    if(value == 0){
      return false;
    }
    return this.optional(element) || /^\d+$/.test(value) || /^[0-9]+([.]\d{1,2})?$/.test(value);
}, "请输入大于0的两位数小数");
jQuery.validator.addMethod("isAllNumber3", function(value, element) {
    if(value == 0){
      return true;
    }
    return this.optional(element) || /^[+-]?\d*\.?\d{1,3}$/.test(value);
}, "请输入三位数的小数");
jQuery.validator.addMethod("isNumberOrNull", function(value, element) {
    if(value == ""){
      return true;
    }
    if(value == 0){
      return false;
    }
    return this.optional(element) || /^\d+$/.test(value) || /^[0-9]+([.]\d{1,2})?$/.test(value);
}, "请输入大于0的两位数小数");

jQuery.validator.addMethod("isNumberZero", function(value, element) {
    if(value == ""){
      return true;
    }
    if(value == 0){
        return true;
      }
    return this.optional(element) || /^\d+$/.test(value) || /^[0-9]+([.]\d{1,2})?$/.test(value);
}, "请输入大于0的两位数小数");

// 保留三位小数
jQuery.validator.addMethod("isFloat3", function(value, element) {
    value = $.trim(value);
    if(value == 0){
      return false;
    }
    return this.optional(element) || /^\d+$/.test(value) || /^[0-9]+([.]\d{1,3})?$/.test(value);
}, "请输入大于0的三位数小数");

jQuery.validator.addMethod("isFloat3OrNull", function(value, element) {
    value = $.trim(value);
    if(value == ""){
      return true;
    }
    if(value == 0){
      return false;
    }
    return this.optional(element) || /^\d+$/.test(value) || /^[0-9]+([.]\d{1,3})?$/.test(value);
}, "请输入大于0的三位数小数");

jQuery.validator.addMethod("isFloat3Zero", function(value, element) {
    value = $.trim(value);
    if(value == ""){
      return true;
    }
    if(value == 0){
      return true;
    }
    return this.optional(element) || /^\d+$/.test(value) || /^[0-9]+([.]\d{1,3})?$/.test(value);
}, "请输入大于0的三位数小数");

// 电话号码验证
jQuery.validator.addMethod("phoneCN", function (phone_number, element) {
    phone_number = phone_number.replace(/\s+/g, "");
    return this.optional(element) || phone_number.length > 9 &&
    (phone_number.match(/^(\+?1-?)?(\([2-9]\d{\d{4}$/)||phone_number.match(/^(\+?86-?)?(\(1[358]\d\)|1[358]\d)-?\d{4}-?\d{4}$/));
}, "请输入正确的电话号码");

jQuery.validator.addMethod("allTelphone", function(value, element) {
  return this.optional(element) || /^((0\d{2,3}-?\d{7,8}){0,1}( {0,1}(13\d{9})|(17\d{9})|(15\d{9})|(18\d{9})){0,1}( {0,1}(4|8)00\d{3}\d{4}){0,1})$/.test(value);
},"请输入正确的电话号码"); 

jQuery.validator.addMethod("isPassword", function(value, element) {
	return this.optional(element) || /^[a-zA-Z]\w{5,17}$/.test(value);
},"必须以字母开头,长度在6~18之间,只能包含字符、数字和下划线"); 

// 邮政编码验证
jQuery.validator.addMethod("isZipCode", function(value, element) {   
    var tel = /^[0-9]{6}$/;
    return this.optional(element) || (tel.test(value));
}, "请正确填写邮政编码");

// 判断一个输入框是否有id
jQuery.validator.addMethod("isHasId", function(value, element) {   
    value = $.trim(value);
    if(value == ""){
      return true;
    }else{
      var this_id = $(element).attr("higo-id");
      if(this_id && this_id !== ""){
        return true;
      }else{
        return false;
      }
    }
}, "没有此条记录");

//IP地址的验证
jQuery.validator.addMethod("isIp", function(value, element) {   
	var ip = /^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
	return this.optional(element) || (ip.test(value));
}, "请正确填写IP地址，如：127.1.1.1");

//英文的验证
jQuery.validator.addMethod("isEnglish", function(value, element) {   
	var ip = /^[a-zA-Z]+$/;
	return this.optional(element) || (ip.test(value));
}, "只能录入英文字符串，如：nihao");

// 车牌号的验证
jQuery.validator.addMethod("isCarNum", function(value, element) {   
	var carNum = /^[\u4e00-\u9fa5]{1}[A-Z0-9]{6}$/;
	return this.optional(element) || (carNum.test(value));
}, "请正确填写车牌号，如：京A88888");

// 车型的验证
jQuery.validator.addMethod("isCarType", function(value, element) {   
	var carType = /^[0-9a-zA-Z]*$/g  ;  //只能输入字母和数字
	return this.optional(element) || (carType.test(value));
}, "请正确填写车型，数字和字母组合");

