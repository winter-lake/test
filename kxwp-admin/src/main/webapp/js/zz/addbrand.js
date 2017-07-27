$(function(){	
		// 初始化上传图片 -- 主图
		Common.imageUpload(".j_main_pic_box");
		
		$("#bansub").click(function(){
			var MsBrand = {};
			MsBrand.brandName = $("#brandName").val();
			MsBrand.describtion = $("#describtion").val();
			MsBrand.brandNo =$("#brandno").val();
			if( $("#brandName").val()!="" && $("#describtion").val()!=""){
		    		$.ajax({
					    url:"/zz/ssBrand/addbrand.do",
					    type:'post',
					    contentType:'application/json',
					    dataType:'json',
					    data:JSON.stringify(MsBrand),
					    success:function(data){
					    	if(data.callStatus == 'SUCCESS'){
					    		Common.kxpw_tishi("添加品牌成功");
					    		window.location.href="/zz/ssBrand/queryBrandInit.do";
					    	}
					    	else{
					    		Common.kxpw_tishi("系统错误，品牌添加失败");
					    		return false;
					    	}
					    }
					})
		}else{
				Common.kxpw_tishi("请输入完整的表单");
				return false;
			}
		});
		$("#comeback").click(function(){
			window.location.href="/zz/ssBrand/queryBrandInit.do";
		});
	});