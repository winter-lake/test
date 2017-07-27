/**
 *此处定义my97的定义控件
 切记必须先引入/My97DatePicker/WdatePicker.js！！！
 */
/**
 * 设置页面Pickup/Delivery的初始时间
 */
(function(global){

	//兼容chrome 以外 的时间构造函数
	function newDate(reference_time) {
		var dateArray = reference_time.split('-');
		var this_year = dateArray[0];
		var this_month = dateArray[1];
		var day_and_hours_minutes = dateArray[2].split(' ');
		var this_day = day_and_hours_minutes[0];
		var hours_minutes = day_and_hours_minutes[1].split(':');
		var this_hours = hours_minutes[0];
		var this_minutes = hours_minutes[1];
		var this_seconds = hours_minutes[2];

		var date = new Date(this_year, this_month - 1, this_day, this_hours, this_minutes, this_seconds);
		return date;
	}

	// 格式化时间的函数

	function formatDate(date){
		if(!date){
			date = new Date();
		}

		var seperator1 = "-";
		var seperator2 = ":";
		var month = date.getMonth() + 1;
		var strDate = date.getDate();
		var hours = date.getHours();
		var minutes = date.getMinutes();
		var seconds = date.getSeconds();
		if (month >= 1 && month <= 9) {
			month = "0" + month;
		}
		if (strDate >= 0 && strDate <= 9) {
			strDate = "0" + strDate;
		}
		if (hours >= 0 && hours <= 9) {
			hours = "0" + hours;
		}
		if (minutes >= 0 && minutes <= 9) {
			minutes = "0" + minutes;
		}
		if (seconds >= 0 && seconds <= 9) {
			seconds = "0" + seconds;
		}
		var currentdate = date.getYear()+1900 + seperator1 + month + seperator1 + strDate + " " + hours + seperator2 + minutes + seperator2 + seconds;
		return currentdate;
	}
	
	
	/*
	 * 设置开始时间结束时间的函数
	 * futureMode:true:最小时间为当前时间;false:最大时间为当前时间
	 */
	function set_start_end_time(startDom,endDom,options,futureMode){
		// options 相关配置，为了兼容以前的版本
		var config = {
			endMaxToday:true,
			//preDays:90, //开始时间选择的最早的时间是基于现在时间的多少天
			dateFmt:"yyyy-MM-dd HH:mm:ss",
			isShowClear:true
		};
		$.extend(true, config, options);
		//开始时间
		startDom.on('focus', function(event) {
			event.preventDefault();
			// 开始时间为今天的前三个月 
		    var today = new Date();
			var today_day = today.getDate();
			var min_date = new Date();
			var start_config = {
	                dateFmt:config.dateFmt,
	                isShowClear:config.isShowClear,
	                onpicked:function(dp){
	                },
	                errDealMode:1
	        };
			if(futureMode){
			  start_config.minDate = new Date();				
		    }else{
		      start_config.maxDate = new Date();
		    }
			WdatePicker(start_config);
			
		});

		// 为日历图标添加点击事件
		startDom.siblings('.input-group-addon').on('click', function(event) {
			event.preventDefault();
			startDom.focus();
		});

		if(endDom){
			//结束时间
			endDom.on('focus', function(event) {
				event.preventDefault();
				// 开始时间为起始时间
				var min_date_format = startDom.val();
				if(min_date_format == ""){
					var min_today = new Date();
					var today_day = min_today.getDate();
					var min_date = new Date();
					min_date.setDate(today_day - 90);
				    min_date_format = formatDate(min_date);
				}
				// 截止时间为今天 
				var end_config = {
					dateFmt: config.dateFmt,
					minDate: min_date_format,
					isShowClear:config.isShowClear,
					onpicked: function(dp) {
					},
					errDealMode: 1
				};

				if(config.endMaxToday){
					var today = new Date();
					var max_max_format = formatDate(today);
					end_config.maxDate = max_max_format;
				}
				
				WdatePicker(end_config);
			});

			// 为日历图标添加点击事件
			endDom.siblings('.input-group-addon').on('click', function(event) {
				event.preventDefault();
				endDom.focus();
			});
		}
	};

	//设置开始时间结束时间的函数
	function setStartEndTime(startDom,EndDom,options){
		// options 相关配置，为了兼容以前的版本
		var config = {
			endMaxToday:true,
			//preDays:90, //开始时间选择的最早的时间是基于现在时间的多少天
			dateFmt:"yyyy-MM-dd HH:mm:ss",
			isShowClear:true
		};
		$.extend(true, config, options);
		//开始时间
		startDom.on('focus', function(event) {
			event.preventDefault();
			// 开始时间为今天的前三个月 
		    var today = new Date();
			var today_day = today.getDate();
			var min_date = new Date();
			//min_date.setDate(today_day - config.preDays);
			//var min_date_format = formatDate(min_date);
			WdatePicker({
                dateFmt:config.dateFmt,
                maxDate:new Date(),
              //  minDate:min_date_format,
                isShowClear:config.isShowClear,
                onpicked:function(dp){
                    
                     
                },
                errDealMode:1
            });
		});

		// 为日历图标添加点击事件
		startDom.siblings('.input-group-addon').on('click', function(event) {
			event.preventDefault();
			startDom.focus();
		});

		if(EndDom){
			//结束时间
			EndDom.on('focus', function(event) {
				event.preventDefault();
				// 开始时间为起始时间
				var min_date_format = startDom.val();
				if(min_date_format == ""){
					var min_today = new Date();
					var today_day = min_today.getDate();
					var min_date = new Date();
					min_date.setDate(today_day - 90);
				    min_date_format = formatDate(min_date);
				}
				// 截止时间为今天 
				var end_config = {
					dateFmt: config.dateFmt,
					minDate: min_date_format,
					isShowClear:config.isShowClear,
					onpicked: function(dp) {
					},
					errDealMode: 1
				};

				if(config.endMaxToday){
					var today = new Date();
					var max_max_format = formatDate(today);
					end_config.maxDate = max_max_format;
				}
				
				WdatePicker(end_config);
			});

			// 为日历图标添加点击事件
			EndDom.siblings('.input-group-addon').on('click', function(event) {
				event.preventDefault();
				EndDom.focus();
			});
		}
	};

	// 设置起始时间为今天的时间控件
	function initMy97Date(options){
		var min_temp = new Date();
		var min_date_format = formatDate(min_temp);
		var max_temp = new Date();
		max_temp.setDate(max_temp.getDate() + 90);
		var max_date = new Date(max_temp);
		var max_date_format = formatDate(max_date);

		var defaults = {
			minDate:min_date_format,
			maxDate:max_date_format,
			dateFmt:"yyyy-MM-dd HH:mm:ss",
		}
		var sets = $.extend({}, defaults, options);
		var dom = $(sets.dom);
		//开始时间
		dom.on('focus', function(event) {
			event.preventDefault();
			WdatePicker({
                dateFmt:sets.dateFmt,
                minDate:sets.minDate,
                maxDate:sets.maxDate,
                onpicked:function(dp){
                },
                errDealMode:1
            });
		});

		// 为日历图标添加点击事件
		dom.siblings('.input-group-addon').on('click', function(event) {
			event.preventDefault();
			dom.focus();
		});
	};

	
	global.setStartEndTime=setStartEndTime;
	global.set_start_end_time = set_start_end_time;
	global.formatDate=formatDate;
	global.initMy97Date=initMy97Date;
})(KXWP);

