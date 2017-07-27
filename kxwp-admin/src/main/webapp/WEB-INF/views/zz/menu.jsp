<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>菜单管理</title>
	
	<link rel="stylesheet" href="<%=basePath %>css/zz/menu.css">
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
			<form class="k_change" method="post" action="<%=basePath %>zz/msResourceManage/list.do">
				<input type="hidden" class="pagecount" name="currentPage" value="${exchangeData.pager.currentPage }">
				<input type="hidden" class="pagenumber" name="pageSize" value="${exchangeData.pager.pageSize }">
				<b class="k-b">
					<i>系统 :</i>
					<select name="systemType" id="x_t">
					</select>
				</b>
				<b class="k-b">
					<i>状态 :</i>
					<select name="resourceStatus" id="z_t">
					</select>
				</b>
				<input type="button" id="Inquire" value="查询">
			</form>
			<table class="table table-bordered" style="text-align:center">
			  	<thead style="background:#eee">
					<tr>
						<!-- <td><input type="checkbox"></td> -->
						<td>编号</td>
						<td>功能名称</td>
						<td>功能描述</td>
						<td>父节点</td>
						<td>所属系统</td>
						<td>状态</td>
						<td>URL</td>
						<td>操作</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${msResources }" var="item" varStatus="status">
							<tr>
								<!-- <td><input type="checkbox"></td> -->
								<td>${status.index+1}</td>
								<td>${item.name }</td>
								<td>${item.description }</td>
								<td>${item.parentName }</td>
								<c:if test="${item.ownSystem == 'MasterStation'}">
									<td>总站</td>
								</c:if>
								<c:if test="${item.ownSystem == 'ServiceStation'}">
									<td>服务站</td>
								</c:if>
								<c:if test="${item.ownSystem == 'Supplier'}">
									<td>供应商</td>
								</c:if>
								<c:if test="${item.resourceStatus == 'ONLINE'}">
									<td>上线</td>
								</c:if>
								<c:if test="${item.resourceStatus != 'ONLINE'}">
									<td>下线</td>
								</c:if>
								<td>${item.url }</td>
								<c:if test="${item.ownSystem == 'MasterStation'}">
									<td><a href="<%=basePath %>zz/msResourceManage/get.do?id=${item.id }" class="t_edit">编辑</a><a href="<%=basePath %>zz/msResourceManage/get.do?id=${item.id }&flag=view" class="t_view">查看</a></td>
								</c:if>
								<c:if test="${item.ownSystem == 'ServiceStation'}">
									<td><a href="<%=basePath %>zz/ssResourceManage/get.do?id=${item.id }" class="t_edit">编辑</a><a href="<%=basePath %>zz/ssResourceManage/get.do?id=${item.id }&flag=view" class="t_view">查看</a></td>
								</c:if>
								<c:if test="${item.ownSystem == 'Supplier'}">
									<td><a href="<%=basePath %>zz/suResourceManage/get.do?id=${item.id }" class="t_edit">编辑</a><a href="<%=basePath %>zz/suResourceManage/get.do?id=${item.id }&flag=view" class="t_view">查看</a></td>
								</c:if>
							</tr>
					</c:forEach>
				</tbody>
			</table>
			<!--批量操作及分页-->
			<div class="batch">
				<!-- <span class="batch_up">批量上线</span>
				<span class="batch_down">批量下线</span> -->
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
	</div>
	<!--右边结束-->
	<!--面包屑导航-->
	<div class="k_breadcrumb">
		<ol class="breadcrumb" style="background:#fff">
		  	<li><a href="#">总站</a></li>
		  	<li><a href="#">资源管理</a></li>
		  	<li><a href="#">菜单列表</a></li>
		</ol>
		<a href="<%=basePath %>zz/msResourceManage/addInit.do" class="add_menu"><i class="icon-plus"></i>添加菜单</a>
	</div>
	<!--面包屑导航结束-->
	<%@include file="../common/footer.jsp" %>
</div>	
<!--end-->
<script src="/js/zz/menu.js"></script>
<script>
$(document).ready(function() {
	$.ajax({
        url:"<%=basePath%>common/ajax/getEnumListByName.do?enumname=SystemTypeEnum",
        type:'post',
        dataType:'json',
        success:function(data){
            renderx_t(data);
        }
    })
    /*系统*/
    function renderx_t(data){
    	var html = '<option value="">请选择</option>';
    	
        $.each(data.data,function(i,val){
            html+='<option value="'+val.name+'">'+val.desc+'</option>'
        })
        
        $('#x_t').html(html);
        $('#x_t option:last').remove();
        
        $('#x_t').val("${masterStationQuery.systemType}");
    }
    $.ajax({
        url:'<%=basePath%>common/ajax/getEnumListByName.do?enumname=ResourceStatusEnum',
        type:'post',
        dataType:'json',
        success:function(data){
            renderz_t(data);
        }
    })
    
    /*状态*/
    function renderz_t(data){
    	var html = '<option value="">请选择</option>';
    	
        $.each(data.data,function(i,val){
            html+='<option value="'+val.name+'">'+val.desc+'</option>'
        })
        
        $('#z_t').html(html)
        
        $('#z_t').val("${masterStationQuery.resourceStatus}")
    }
    
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
    
  //加载外部js
	Menu.dialog();
    $("#x_t").change(function(){
    	 $('.pagecount').val(1);
    	 $('.pagenumber').val(10);
    	 $("form:first").submit();
    });
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
})
</script>
</body>
</html>