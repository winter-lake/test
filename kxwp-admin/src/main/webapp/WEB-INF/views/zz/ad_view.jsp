<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>广告查看</title>
	<link rel="stylesheet" type="text/css" href="/css/zz/add_gg.css">
	<link rel="stylesheet" type="text/css" href="/css/zz/ad_detail.css">
	<%@include file="../common/share_static.jsp" %>
		<style type="text/css">
		.row {
		    margin-right: -15px;
		    margin-left: 300px;
		}
	</style>
</head>
<body>
<!--外围容器-->
<div class="k_contaniner">
	<%@include file="../common/header.jsp" %>
	<%@include file="../common/leftNav.jsp" %>
	<!--右边开始-->
	<div class="k_right">
		<div class="content">
			<h2 class="title">账户信息</h2>
			<div class="row">
			  <div class="col-md-2">广告名称 :</div>
			  <div class="col-md-4" id="name"></div>
			</div>
			<div class="row">
			  <div class="col-md-2">平台 :</div>
			  <div class="col-md-4" id="platformType"></div>
			</div>
			<div class="row">
			  <div class="col-md-2">推荐广告位 :</div>
			  <div class="col-md-4" id="adLocation"><a href="#" class="explain">区域说明</a></div>
			</div>
			<div class="row">
			  <div class="col-md-2">广告类型 :</div>
			  <div class="col-md-4" id="adType"></div>
			</div>
			<div class="row">
			  <div class="col-md-2">ID :</div>
			  <div class="col-md-4" id="recommendedId"></div>
			</div>
			<div class="row">
			  <div class="col-md-2">URL链接 :</div>
			  <div class="col-md-4" id="url"></div>
			</div>
			<div class="row">
			  <div class="col-md-2">图片略缩图 :</div>
			  <div class="col-md-4" id="adLocation"><img src="${photoList0 }"/></div>
			</div>
			<div class="row">
			  <div class="col-md-2">开始时间 :</div>
			  <div class="col-md-4" id="startTime"></div>
			</div>
			<div class="row">
			  <div class="col-md-2">结束时间 :</div>
			  <div class="col-md-4" id="endTime"></div>
			</div>
			<div class="row" >
				<div class="a-d sub" style="margin-left: 58px;">
					<span id="changeStatusDiv">
					<button type="button" name="changeStatus" adId="" adStatus="NOTSTARTED">通过审核</button>
					<button type="button" name="changeStatus" adId="" adStatus="NOTPASS">拒绝审核</button>
					</span>
					<button type="button" id="back">返回</button>
				</div>
			</div>
		</div>
	</div>
	<!--右边结束-->
	<!--面包屑导航-->
	<div class="k_breadcrumb">
		<ol class="breadcrumb" style="background:#fff">
		  	<li><a href="#">总站</a></li>
		  	<li><a href="#">广告管理</a></li>
		  	<li><a href="#">查看广告</a></li>
		</ol>
		<a href="/zz/msAd/findInit.do" class="add_menu">广告列表</a>
	</div>
	<!--面包屑导航结束-->
	<%@include file="../common/footer.jsp" %>
</div>	
<!--end-->
</body>
<script type="text/javascript">
	$(function(){
		$(".explain").click(function(){
			layer.open({
				  type: 1,
				  title: false,
				  closeBtn: 0,
				  area: '430px',
				  skin: 'layui-layer-nobg',
				  shadeClose: true,
				  skin: 'yourclass',
				  content: '<img width="400px" src="/images/fwz/adLocationSpecification.png">'
				});
		});
		
		$("#back").click(function(){
			window.location.href="/zz/msAd/findInit.do";
		});
		
		var adId = ${id};
		
		$.ajax({
			url : '/zz/msAd/get.do',
			type : 'POST',
			data : {id:adId},
			success : function(data) 
			{
				if(data.callStatus == 'SUCCESS'){
					$("#name").html(data.data.name);
					
					$.ajax({
					    url:"<%=basePath%>common/ajax/getEnumListByName.do?enumname=PlatformTypeEnum",
					    type:'post',
					    dataType:'json',
					    success:function(response){
							var result = '';
							
						    $.each(response.data,function(i,val){
						    	if(data.data.platformType == val.name){
							    	result=val.desc;
						    	}
						    })
						    
						    $('#platformType').text(result);
					    }
					})
					
					$("#adType").val(data.data.adType);
					
					$.ajax({
					    url:"<%=basePath%>common/ajax/getEnumListByName.do?enumname=AdTypeEnum",
					    type:'post',
					    dataType:'json',
					    success:function(response){
							var result = '';
							
						    $.each(response.data,function(i,val){
						    	if(data.data.adType == val.name){
							    	result=val.desc;
						    	}
						    })
						    
						    $('#adType').text(result);
					    }
					})
					
					$("#recommendedId").text(data.data.recommendedId);
					
					$.ajax({
					    url:"<%=basePath%>common/ajax/getEnumListByName.do?enumname=AdLocationEnum",
					    type:'post',
					    dataType:'json',
					    success:function(response){
							var result = '';
							
						    $.each(response.data,function(i,val){
						    	if(data.data.adLocation== val.name){
							    	result=val.desc;
						    	}
						    })
						    
						    $('#adLocation').prepend(result+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					    }
					})
					
					$("#url").text(data.data.url);
					$("#startTime").text(data.data.startTime);
					$("#endTime").text(data.data.endTime);
					
					var temp = data.data.adStatus == "WAITAUDIT" ? true : false;
					$("#adStatus").attr("checked", temp);
					$(".w_filebox").hide();
					
					$("button[adStatus=NOTSTARTED]").attr("adId",data.data.id);
					$("button[adStatus=NOTPASS]").attr("adId",data.data.id);
					
					if(data.data.adStatus != "WAITAUDIT"){
						$("#changeStatusDiv").hide();
					}
				}
			}
		}); 
		
		$("div").on('click','button[name=changeStatus]', function(){
	 		var adConfig = {};
	 		adConfig.id=$(this).attr('adId');
	 		adConfig.adStatus=$(this).attr('adStatus');
	 		adConfig.adLocation=$(this).attr('location');
	 		adConfig.describtion = "通过审核";
	 		
	 		if(adConfig.adStatus == 'NOTSTARTED'){
	 			layer.confirm('确认广告通过审核，进行排期？', {
	 				  btn: ['取消','确认'],title:'广告审核' //按钮
	 				}, function(){
	 				  layer.msg('取消成功');
	 				}, function(){
		 				$.ajax({
		 					url : '/zz/msAd/audit.do',
		 					type : 'POST',
		 					dataType : 'json',
		 					data : JSON.stringify(adConfig),
		 					contentType : "application/json",
		 					success : function(data) {
		 						if(data.callStatus == 'SUCCESS'){
		 							layer.msg('操作成功');
		 							window.location.href="/zz/msAd/findInit.do";
		 						}
		 					}
		 				});
	 				});
	 		}else{
	 			layer.prompt({
	 				title: '输入审核信息，并确认',
	 				formType: 2//prompt风格，支持0-2
	 			}, function(pass){
	 				adConfig.describtion = pass;
	 				
	 				$.ajax({
	 					url : '/zz/msAd/audit.do',
	 					type : 'POST',
	 					dataType : 'json',
	 					data : JSON.stringify(adConfig),
	 					contentType : "application/json",
	 					success : function(data) {
	 						if(data.callStatus == 'SUCCESS'){
	 							layer.msg('操作成功');
	 							window.location.href="/zz/msAd/findInit.do";
	 						}
	 					}
	 				}); 
	 			});
	 		}
	 		
		});
	});
</script>
</html>