<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>广告添加</title>
	<link rel="stylesheet" type="text/css" href="/css/fwz/add_gg.css">
	<%@include file="../common/share_static.jsp" %>
</head>
<body>
<!--外围容器-->
<div class="k_contaniner">
	<%@include file="../common/header.jsp" %>
	<%@include file="../common/leftNav.jsp" %>
	<!--右边开始-->
	<div class="k_right">
		<div class="content">
			<div class="add_gg">
					<form id="formId">
					<div class="a-d">
						<i class="star icon-asterisk tx_red"></i>
						<span class="s-p">广告名称 :</span>
						<input type="text" id="name" class="validate[required]">
					</div>
					<p class="int">广告名称只是作为辨别多个广告条目之用，并不显示在广告中</p>
					<div class="a-d">
						<i class="star icon-asterisk tx_red"></i>
						<span class="s-p">平台 :</span>
						<select id="platformType" class="validate[required]">
						</select>
					</div>
					<div class="a-d">
						<i class="star icon-asterisk tx_red"></i>
						<span class="s-p">推荐广告位 :</span>
						<select id="adLocation" class="validate[required]">
						</select>
						<a href="#" class="explain">区域说明</a>
					</div>
					<div class="a-d">
						<i class="star icon-asterisk tx_red"></i>
						<span class="s-p">广告类型 :</span>
						<select id="adType" class="validate[required]">
						</select>
					</div>
					<div class="a-d" id="recommendedIdDiv">
						<i class="star icon-asterisk tx_red"></i>
						<span class="s-p">ID :</span>
						<span id="recommendedIdSpan"><input type="text" id="recommendedId" placeholder="请输入对应ID号" ></span>
						<a href="#" title="商品id请到商品列表查看，供应商id请到供应商列表查看，品牌id请到品牌列表查看" alt="" class="question icon-question-sign"></a>
					</div>
					<div class="a-d">
						<i class="tx_red"></i>
						<span class="s-p">URL链接 :</span>
						<input type="text" id="url" >
					</div>
					<p class="int">
						*A区图片尺寸大小为：750*360px<br>
						*B区图片尺寸大小为：228*186px<br>
						*C区供应商店铺头像图片尺寸大小为：88*88px（圆）<br>
						*D区商品图片尺寸大小为：88*88px
					</p>
					<div class="a-d">
						<i class="star icon-asterisk tx_red" style="position:relative;top:60px"></i>
						<span class="s-p" style="position:relative;top:60px">图片略缩图 :</span>
						<h5>
								<div class="dis_inb j_main_pic_box">
									<label class="w_filebox">
										<img src="<%=contextPath %>/images/share/sctp.png">
										<input type="file" name="file" id="j_main_pic">
									</label>
									<input type="hidden" name="keyID" value="${adNo}">
									<input type="hidden" name="bucketName" value="ad">
									<label class="w_view_pic" data-file_key="AD00000003_20160818114358622.png">
										<img src="${photoList0 }"><b class="del">X</b>
									</label>
								</div>
						</h5>
					</div>
					<div class="a-d">
						<i class="star icon-asterisk tx_red"></i>
						<span class="s-p">开始时间 :</span>
						<input type="text" placeholder="开始时间" id="startTime" class="validate[required]">
					</div>
					<div class="a-d">
						<i class="star icon-asterisk tx_red"></i>
						<span class="s-p">结束时间 :</span>
						<input type="text" placeholder="结束时间" id="endTime" class="validate[required]">
					</div>
					<h5 class="int">活动添加成功后不允许再编辑，请您仔细核对</h5>
					<div class="a-d">
						<i class="tx_red"></i>
						<span class="s-p">审核设置 :</span>
						<input type="checkbox" id="adStatus" style="position:relative;top:2p">
						立即提审
					</div>
					</form>
					<div class="a-d sub">
						<button type="button" id="submit">提交</button>
						<button class="qx">返回</button>
					</div>
			</div>
		</div>
	</div>
	<!--右边结束-->
	<!--面包屑导航-->
	<div class="k_breadcrumb">
		<ol class="breadcrumb" style="background:#fff">
		  	<li><a href="#">服务站</a></li>
		  	<li><a href="#">广告管理</a></li>
		  	<li><a href="#">修改广告</a></li>
		</ol>
		<a href="/fwz/ssAd/findInit.do" class="add_menu">广告列表</a>
	</div>
	<!--面包屑导航结束-->
	<%@include file="../common/footer.jsp" %>
</div>	
<!--end-->
</body>
<script type="text/javascript">
	$(function(){
	//初始化资源状态
		$.ajax({
		    url:"<%=basePath%>common/ajax/getEnumListByName.do?enumname=PlatformTypeEnum",
		    type:'post',
		    dataType:'json',
		    success:function(data){
				var html = '';
				
			    $.each(data.data,function(i,val){
			        html+='<option value="'+val.name+'">'+val.desc+'</option>';
			    })
			    
			    $('#platformType').append(html);
		    }
		})
		$.ajax({
		    url:"<%=basePath%>common/ajax/getEnumListByName.do?enumname=AdTypeEnum",
		    type:'post',
		    dataType:'json',
		    success:function(data){
				var html = '';
				
			    $.each(data.data,function(i,val){
			        html+='<option value="'+val.name+'">'+val.desc+'</option>';
			    })
			    
			    $('#adType').append(html);
			    $('#adType option:first').remove();
			    $('#adType option:eq(3)').remove();
		    }
		})
		$.ajax({
		    url:"<%=basePath%>common/ajax/getEnumListByName.do?enumname=AdLocationEnum",
		    type:'post',
		    dataType:'json',
		    success:function(data){
				var html = '';
				
			    $.each(data.data,function(i,val){
			        html+='<option value="'+val.name+'">'+val.desc+'</option>';
			    })
			    
			    $('#adLocation').append(html);
		    }
		})
		
		KXWP.setStartEndTime($("#startTime"),$("#endTime"),{
                endMaxToday:false,
                preDays:365
            });
		
		$("#adLocation").change(function(){
			$('#adType').empty();
			$("#recommendedIdSpan").empty();
			$("#recommendedIdSpan").append('<select id="recommendedId" name="recommendedId" ></select>');
			
			if($(this).val() == "A"){
				$.ajax({
				    url:"<%=basePath%>common/ajax/getEnumListByName.do?enumname=AdTypeEnum",
				    type:'post',
				    dataType:'json',
				    success:function(data){
						var html = '';
						
					    $.each(data.data,function(i,val){
					        html+='<option value="'+val.name+'">'+val.desc+'</option>';
					    })
					    
					    $('#adType').append(html);
					    $('#adType option:first').remove();
					    $('#adType option:eq(3)').remove();
				    }
				})
				
				KXWP.goodsRepoSelectByCondition("#recommendedId");
			}else if($(this).val() == "B"){
				$.ajax({
				    url:"<%=basePath%>common/ajax/getEnumListByName.do?enumname=AdTypeEnum",
				    type:'post',
				    dataType:'json',
				    success:function(data){
						var html = '';
						
					    $.each(data.data,function(i,val){
					        html+='<option value="'+val.name+'">'+val.desc+'</option>';
					    })
					    
					    $('#adType').append(html);
					    $('#adType option:lt(4)').remove();
					    $('#adType option:gt(0)').remove()
				    }
				})
				
				KXWP.goodsCategoryAuto("#recommendedId");
			}else if($(this).val() == "C"){
				$.ajax({
				    url:"<%=basePath%>common/ajax/getEnumListByName.do?enumname=AdTypeEnum",
				    type:'post',
				    dataType:'json',
				    success:function(data){
						var html = '';
						
					    $.each(data.data,function(i,val){
					        html+='<option value="'+val.name+'">'+val.desc+'</option>';
					    })
					    
					    $('#adType').append(html);
					    $('#adType option:lt(2)').remove();
					    $('#adType option:gt(0)').remove();
				    }
				})
				
				KXWP.sysBrandRepoSelect("#recommendedId");
			}else if($(this).val() == "D"){
				$.ajax({
				    url:"<%=basePath%>common/ajax/getEnumListByName.do?enumname=AdTypeEnum",
				    type:'post',
				    dataType:'json',
				    success:function(data){
						var html = '';
						
					    $.each(data.data,function(i,val){
					        html+='<option value="'+val.name+'">'+val.desc+'</option>';
					    })
					    
					    $('#adType').append(html);
					    $('#adType option:lt(3)').remove();
					    $('#adType option:gt(0)').remove();
				    }
				})
				
				KXWP.gysSelect("#recommendedId");
			}else{
				$.ajax({
				    url:"<%=basePath%>common/ajax/getEnumListByName.do?enumname=AdTypeEnum",
				    type:'post',
				    dataType:'json',
				    success:function(data){
						var html = '';
						
					    $.each(data.data,function(i,val){
					        html+='<option value="'+val.name+'">'+val.desc+'</option>';
					    })
					    
					    $('#adType').append(html);
					    $('#adType option:first').remove();
					    $('#adType option:gt(0)').remove();
				    }
				})
				
				KXWP.goodsRepoSelectByCondition("#recommendedId");
			}
		});
	
		$("#adType").change(function(){
			$("#recommendedIdSpan").empty();
			$("#recommendedIdSpan").append('<select id="recommendedId" name="recommendedId" ></select>');
			if($(this).val() == 'SUPPLIER'){
				KXWP.gysSelect("#recommendedId");
			}
			else if($(this).val() == 'BRAND'){
				KXWP.sysBrandRepoSelect("#recommendedId");
			}
			else if($(this).val() == 'CLASSIFICATION'){
				KXWP.goodsCategoryAuto("#recommendedId");
			}
			else if($(this).val() == 'GOODS'){
				KXWP.goodsRepoSelectByCondition("#recommendedId");
			}
		});
	
		Common.validator($("#formId"));
		
		// 初始化上传图片 -- 主图
		Common.imageUpload(".j_main_pic_box");
		
		$("#submit").click(function(){
		    if(!$('#formId').validationEngine('validate'))
				return false;
		    
		    if($("#adType").val() != 'PICTURE' && $("#adType").val() != 'URL' && !$("#recommendedId").val()){
		    	layer.alert('请选择推荐的ID');
		    }else{
				var ADConfig = {};
				
				ADConfig.id = adId;
				ADConfig.name = $("#name").val();
				ADConfig.platformType = $("#platformType").val();
				ADConfig.adType = $("#adType").val();
				ADConfig.recommendedId = $("#recommendedId").val();
				ADConfig.adLocation = $("#adLocation").val();
				ADConfig.url = $("#url").val();
				ADConfig.startTime = $("#startTime").val();
				ADConfig.endTime = $("#endTime").val();
				ADConfig.adStatus = $("#adStatus").is(":checked") == true ? "WAITAUDIT" : "NOTCOMMIT";
				ADConfig.adNo = "${adNo}";
				
				$.ajax({
				    url:"/fwz/ssAd/modify.do",
				    type:'post',
				    contentType:'application/json',
				    dataType:'json',
				    data:JSON.stringify(ADConfig),
				    success:function(data){
				    	if(data.callStatus == 'SUCCESS'){
				    		window.location.href="/fwz/ssAd/findInit.do";
				    	}
				    }
				})
		    }
		});
		
		$(".qx").click(function(){
			window.location.href="/fwz/ssAd/findInit.do";
		});
		
		var adId = ${id};
		
		$.ajax({
			url : '/fwz/ssAd/get.do',
			type : 'POST',
			data : {id:adId},
			success : function(data) 
			{
				if(data.callStatus == 'SUCCESS'){
					$("#name").val(data.data.name);
					$("#platformType").val(data.data.platformType);
					$("#adType").val(data.data.adType);
					$("#recommendedId").val(data.data.recommendedId);
					$("#adLocation").val(data.data.adLocation);
					$("#url").val(data.data.url);
					$("#startTime").val(data.data.startTime);
					$("#endTime").val(data.data.endTime);
					
					var temp = data.data.adStatus == "WAITAUDIT" ? true : false;
					$("#adStatus").attr("checked", temp);
					$(".w_filebox").hide();
				}
			}
		}); 
		
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
	});
</script>
</html>