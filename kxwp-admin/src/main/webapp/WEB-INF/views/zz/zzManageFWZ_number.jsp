<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>账号管理</title>
	
	<link rel="stylesheet" href="<%=basePath %>css/fwz/number.css">
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
			<!--查询等功能-->
			<form class="k_change" method="post" action="<%=basePath%>zz/fwzAccount/list.do">
				<input type="hidden" class="pagecount" name="currentPage" value="${exchangeData.pager.currentPage }">
				<input type="hidden" class="pagenumber" name="pageSize" value="${exchangeData.pager.pageSize }">
				<b class="k-b">
					<i>负责人:</i>
					<input type="text" name="name" value="${accountQuery.name }">
				</b>
				<input type="button" id="Inquire" value="查询">
			</form>
			<!--table-->
			<table class="table table-bordered" style="text-align:center">
			  	<thead style="background:#eee">
					<tr>
						<!-- <td><input type="checkbox" name="b"></td> -->
						<td>编号</td>
						<td>服务站名称</td>
						<td>负责人</td>
						<td>手机号</td>
						<td>账号类型</td>
						<td>是否初始化</td>
						<td>操作</td>
					</tr>
				</thead>
				<tbody>
				    <c:forEach items="${serviceStationAccounts }" var="item" varStatus="status">
						<tr>
							<!-- <td><input type="checkbox" name="a"></td> -->
							<td>${status.index+1}</td>
							<td>${item.serviceStationName }</td>
							<td>${item.name }</td>
							<td>${item.mobile }</td>
							<td>服务站</td>
							<c:if test="${item.isInit=='Y'}">
								<td>已初始化</td>
							</c:if>
							<c:if test="${item.isInit=='N'}">
								<td>未初始化</td>
							</c:if>
							<c:if test="${item.isInit=='Y'}">
								<td>
								<a href="#" class="t_view" id="${item.id }" mobile="${item.mobile }">再次初始化角色</a>
							</td>
							</c:if>
							<c:if test="${item.isInit=='N'}">
								<td>
								<a href="#" class="t_view" id="${item.id }" mobile="${item.mobile }">初始化角色</a>
							</td>
							</c:if>
						</tr>
				    </c:forEach>
				</tbody>
			</table>
			<!--分页-->
				<div class="paging">
					<ul class="wave-sync-pagination pagination pagination-sm" id="top_wavePagenation">
					 	<li class="first" value="1"><a href="#">首页</a></li>
					 	<c:if test="${exchangeData.pager.currentPage-1 == 0 }">
					 	<li class="prev disabled"  value="${exchangeData.pager.currentPage-1 }"><a href="#">上一页</a></li>
					 	</c:if>
					 	<c:if test="${exchangeData.pager.currentPage-1 != 0 }">
					 	<li class="prev" value="${exchangeData.pager.currentPage-1 }"><a href="#">上一页</a></li>
					 	</c:if>
					 	<c:if test="${exchangeData.pager.currentPage-2 >= 1 }">
					 	<li class="page " value="${exchangeData.pager.currentPage-2 }"><a href="#">${exchangeData.pager.currentPage-2 }</a></li>
					 	</c:if>
					 	<c:if test="${exchangeData.pager.currentPage-1 >= 1 }">
					 	<li class="page " value="${exchangeData.pager.currentPage-1 }"><a href="#">${exchangeData.pager.currentPage-1 }</a></li>
					 	</c:if>
					 	<li class="page active" value="${exchangeData.pager.currentPage }"><a href="#">${exchangeData.pager.currentPage }</a></li>
					 	<c:if test="${exchangeData.pager.currentPage+1 <= exchangeData.pager.totalPages }">
					 	<li class="page " value="${exchangeData.pager.currentPage+1 }"><a href="#">${exchangeData.pager.currentPage+1 }</a></li>
					 	</c:if>
					 	<c:if test="${exchangeData.pager.currentPage+2 <= exchangeData.pager.totalPages }">
					 	<li class="page " value="${exchangeData.pager.currentPage+2 }"><a href="#">${exchangeData.pager.currentPage+2 }</a></li>
					 	</c:if>
					 	<c:if test="${exchangeData.pager.currentPage+1 == exchangeData.pager.totalPages  }">
					 	<li class="next" value="${exchangeData.pager.currentPage+1 }"><a href="#">下一页</a></li>
					 	</c:if>
					 	<c:if test="${exchangeData.pager.currentPage+1 > exchangeData.pager.totalPages  }">
					 	<li class="next disabled" value="${exchangeData.pager.currentPage+1 }"><a href="#">下一页</a></li>
					 	</c:if>
					 	<li class="last" value="${exchangeData.pager.totalPages }"><a href="#">末页</a></li>
					 </ul>
					 <span class="pull-right higo-title-right">
			            <span class="text-primary">显示
			                <select id="wave_showlength">
			                    <option value="10">10</option>
			                    <option value="20">20</option>
			                    <option value="50">50</option>
			                </select>
			            </span>
			            <span class="text-primary higo-maginL-10" id="wave_totalpages">共 ${exchangeData.pager.totalPages } 页</span>
			            <span class="text-primary higo-maginL-10" id="wave_totals">共${exchangeData.pager.totoalResults } 条记录</span>
			        </span>
				</div>
		</div>
	</div>
	<!--右边结束-->
	<!--面包屑导航-->
	<div class="k_breadcrumb">
		<ol class="breadcrumb" style="background:#fff">
		  	<li><a href="#">总服务站</a></li>
			<li><a href="#">服务站账号管理</a></li>
			<li><a href="#">账号列表</a></li>
		</ol>
	</div>
	<!--面包屑导航结束-->
	<%@include file="../common/footer.jsp" %>
</div>	
<!--end-->
<script>
$(document).ready(function() {
	$(".t_view").click(function(){
		var id = $(this).attr("id");
		var mobile = $(this).attr("mobile");
		//询问框
		layer.confirm('确认初始化角色？', {
		  btn: ['取消','确认'] //按钮
		}, function(){
		  layer.msg('取消成功！');
		}, function(){
			$.ajax({
		        url:"<%=basePath%>zz/fwzAccount/initRole.do?id="+id,
		        contentType:"application/json",
		        type:'post',
		        dataType:'json',
		        success:function(data){
		        	if(data.callStatus == 'SUCCESS'){
		        		layer.msg('初始化成功', {icon: 1},function(){
		        			window.location.href = "<%=basePath %>zz/fwzAccount/list.do";
		        		});
		        	}
		        }
		    })
		});
	});
	
	
	//初始化分页信息
    $('#wave_showlength').val("${exchangeData.pager.pageSize }");
    
    //分页点击
    $('.paging li').click(function(){
        $('.pagecount').val($(this).val());
        
        if($(this).attr("class").indexOf("disabled") >= 0)
        	return false;
        
        $("form:first").submit();
    })
    $('#wave_showlength').change(function(){
      	var pagenum=Number($(this).find("option:selected").text());
        $('.pagenumber').val(pagenum);
        
       /*  if($(this).attr("class").indexOf("disabled") >= 0)
        	return false; */
        
        $("form:first").submit();
    })
    
    $('#top_wavePagenation li').click(function(){
      $(this).addClass('active').siblings().removeClass('active');
    })
    
    $("#z_t").change(function(){
   	 $('.pagecount').val(1);
   	 $('.pagenumber').val(10);
   	 $("form:first").submit();
   });
   $("#Inquire").click(function(){
   	 $('.pagecount').val(1);
   	 $('.pagenumber').val(10);
   	 $("form:first").submit();
   });
   $("#x_t").change(function(){
  	 $('.pagecount').val(1);
  	 $('.pagenumber').val(10);
  	 $("form:first").submit();
  });
   $("input[name=name]").change(function(){
  	 $('.pagecount').val(1);
  	 $('.pagenumber').val(10);
  	 $("form:first").submit();
  });
  $("input[name=mobile]").change(function(){
  	 $('.pagecount').val(1);
  	 $('.pagenumber').val(10);
  	 $("form:first").submit();
  });
    
})


function modifyStatus(param){
	$.ajax({
        url:"<%=basePath%>fwz/ssAccount/modifyStatusBatch.do",
        contentType:"application/json",
        type:'post',
        dataType:'json',
        data:JSON.stringify(param),
        success:function(data){
        	if(data.callStatus == 'SUCCESS'){
                layer.alert('操作成功！', {
                    skin: 'layui-layer-molv' //样式类名
                    ,closeBtn: 0
                  }, function(){
           	   		window.location.href = "<%=basePath %>zz/fwzAccount/list.do";
                  });
        	}
        }
    })
}
</script>
</body>
</html>