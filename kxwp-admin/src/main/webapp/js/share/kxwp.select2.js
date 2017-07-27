/**
 * 使用本插件必须先引入以下文件
    #1. select2.min.css
    #2. select2-bootstrap.min.css
    #3. select2.min.js
 */
(function(global) { 

	/**
     * 插件的默认值 带*为必传的参数
     */
    var defaults = {
        url:"",//*查询的url
        element:"",//*绑定的元素 eg:"#demo"
        ajax_data:{},//发送参数的ajax参数
        ajaxType:"post",
        placeholder:"请选择一个值",
        parentDom:"",//根据父元素的值来查询
        parentDomField:"id",//查询时所带的父元素参数名称，和上一行一起传入
        queryField:"query",//*input的参数
        valueField:"",// *程序中要用的值字段名，指$(element).val()取得的值
        displayField:"",//*显示在input中的返回值字段名,如果不传，则表示返回的是一个字符串数组
        minLength:0,//至少输入几个字符触发
        resultList:"",// ajax返回的结果列表的字段名,如果没传，则默认为data.data
        ItemType:"obj",//ajax返回的结果，可能是obj,也可能是string
        showInput:true,//是否显示输入框
        hasPages:true,//传参数的时候是否带pager
        afterSelected:function(){},//选择完一个值之后触发
        hasInputValue: false // 是否能自定义输入，即将查询的参数作为值填充
    };

    var KXWPSelect2 = function(options){
    	var self = this;
		//将插件的默认参数及用户定义的参数合并到一个新的obj里
		this.settings = $.extend(true, {}, defaults, options);
        // 格式化部分参数，以适应初始化时的参数
        if(this.settings.showInput == false){
            this.settings.showInput = Infinity;
        }
		//将dom jquery对象赋值给插件，方便后续调用
		var settings = this.settings;
		var element = $(settings.element);
		// 定义取值做参数的父元素
		var parent_Dom = $(settings.parentDom);
        // 假如父元素存在，则绑定和父元素的联动事件
        if(parent_Dom.length > 0 && parent_Dom.val() !== "" ){
            element.attr('disabled', 'disabled');
        }


        element.select2({
            placeholder: element.attr("placeholder") || "请选择一个值",
            allowClear: true,
            theme: "bootstrap",
            ajax: {
                url: settings.url,
                dataType: 'json',
                delay: 250,
                global: false,
                contentType: "application/json",
                type: settings.ajaxType,
                data: function(params) {
                    //组合查询的参数
                    var query_parameter = settings.ajax_data;
                    query_parameter[settings.queryField] = params.term || "";
                    // 当要取值的父元素存在时
                    if (parent_Dom && parent_Dom.length > 0) {
                        // 如果有取父元素的函数存在
                        if (settings.getParentDomFieldValue) {
                            query_parameter[settings.parentDomField] = settings.getParentDomFieldValue();
                        } else {
                            query_parameter[settings.parentDomField] = parent_Dom.val();
                        }
                    }
                    if(settings.hasPages){
                        query_parameter.pager = {
                            currentPage:1,
                            pageSize:20,
                        }
                    }
                    if(settings.ajaxType == "post"){
                        return JSON.stringify(query_parameter);
                    }else{
                        return query_parameter;
                    }
                    
                },
                processResults: function(data, page) {
                    var results = [];
                    if (data.callStatus = "SUCCESS" && data.data && data.data.length > 0) {
                        // 此处有可能显示多级的结果，暂且保留，以后再来改
                        var returnData = [];
                        if (settings.resultList && settings.resultList !== "") {
                            returnData = data.data[0][settings.resultList];
                        } else {
                            returnData = data.data;
                        }
                        $.each(returnData, function(i, v) {
                            var o = {};
                            // 根据返回的每个对象的类型做不同处理
                            // 假如返回的每个对象为字符串
                            if(settings.ItemType == "string"){
                                o.id = v;
                                o.value = v;
                                o.name = v;
                            }else{
                                o.id = v[settings.valueField];
                                o.value = v[settings.valueField];
                                var display_name = "";

                                if (settings.displayField !== "") {
                                    // 如果有多个显示字段，则返回相加后的字段
                                    display_name = v[settings.displayField];
                                    if (settings.displayField2) {
                                        display_name = v[settings.displayField] + " " + v[settings.displayField2];
                                    }
                                }
                                o.name = display_name;
                            }
                            results.push(o);
                            
                        });
                    }else{
                    	//console.log("ajax查询无数据返回");
                    }
                    return {
                        results: results
                    };
                    
                },
                // cache: true
            },
            escapeMarkup: function(markup) {
                return markup;
            }, // let our custom formatter work
            minimumInputLength: settings.minLength,
            minimumResultsForSearch: settings.showInput,
            templateResult: function(repo) {
                if (repo.loading) return repo.text;
                var markup = '<div value="' + repo.id + '">' + repo.name;
                markup += '</div>';
                return markup;
            },
            templateSelection: function(repo) {
                return repo.name || repo.text;
            }
        });
        // 保存初始化的参数,主要让设置默认值的时候用
        element.data("select2-options",settings);
        
        element.on('change', function(event) {
            event.preventDefault();
            if(settings.afterSelected){
                settings.afterSelected();
            }
        });

        if(settings.hasInputValue) {
            element.on('select2:close', function(event) {
                if(!element.val()) {
                    var defauls = element.data('select2').results.lastParams.term;
                    if(defauls) {
                        KXWPSelect2SetValue({
                            Ddom: settings.element, //设置的dom
                            Dvalue:defauls, // 设置的值
                            Ddisplay:defauls, // 要显示的名字
                        })
                    }
                }
                
            });
        }
    }

    // 将所有的select初始化一次
    $("select.js-search-select").each(function(index, el) {
        var this_dom = $(this);
        var this_place = this_dom.attr("placeholder");
        this_dom.select2({
            theme:"bootstrap",
            placeholder:this_place || "请选择一个值",
            allowClear: true,
        })
    });

    // 设置默认值,必须初始化过

    var KXWPSelect2SetValue = function(options){
        /*
            options:{
                Ddom:"", //设置的dom
                Dvalue:"", // 设置的值
                Ddisplay:"", // 要显示的名字
            }
        */ 
        try{
            var dom = $(options.Ddom);
            var select2_op = dom.data("select2-options");
            dom.select2("destroy");
            var option_dom = '<option value="' + options.Dvalue + '" selected="selected">' + options.Ddisplay + '</option>'
            dom.html(option_dom);
            KXWP.KXWPSelect2(select2_op);
        }catch(err){
            console.error(err);
        }
    };




    
    global.KXWPSelect2 = KXWPSelect2;
    global.KXWPSelect2SetValue = KXWPSelect2SetValue;
     
    
    
})(KXWP);