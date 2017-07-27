<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head> 
	<meta charset="UTF-8">
	<title>角色管理</title>
	
	<link rel="stylesheet" href="<%=basePath %>html/css/zz/role/role.css">
	<%@include file="../common/share_static.jsp" %>
</head>
<body>
<!--外围容器-->
<div class="k_contaniner">
	<%@include file="../common/header.jsp" %>
	<%@include file="../common/leftNav.jsp" %>
	<!--右边开始-->
	<div class="k_right">
		<form action="<%=basePath %>fwz/ssRoleManage/list.do" method="post">
		    <input type="hidden" class="pagecount" name="currentPage" value="${exchangeData.pager.currentPage }">
			<input type="hidden" class="pagenumber" name="pageSize" value="${exchangeData.pager.pageSize }">
		</form>
		<div class="content">
			<!--table-->
			<table class="table table-bordered" style="text-align:center">
			  	<thead style="background:#eee">
					<tr>
						<!-- <td><input type="checkbox"></td> -->
						<td>编号</td>
						<td>角色名</td>
						<td>角色描述</td>
						<td>操作</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${ssroles }" var="item" varStatus="status">
						<tr>
							<!-- <td><input type="checkbox"></td> -->
							<td>${status.index+1}</td>
							<td>${item.name }</td>
							<td>${item.roleDescription }</td>
							<td>
							<c:if test="${status.index != 0}">
							<a href="<%=basePath %>fwz/ssRoleManage/get.do?id=${item.id }" class="t_edit">编辑</a>
							<a href="#" class="t_del" id="${item.id }">删除</a>
							</c:if>
							<a href="<%=basePath %>fwz/ssRoleManage/view.do?id=${item.id }" class="t_view">查看</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<!--删除角色-->
			<!-- <div class="del_role">删除角色</div> -->
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
		  	<li><a href="#">服务站</a></li>
		  	<li><a href="#">角色管理</a></li>
		  	<li><a href="#">角色列表</a></li>
		</ol>
		<a href="<%=basePath %>fwz/ssRoleManage/addInit.do" class="add_menu"><i class="icon-plus"></i>添加角色</a>
	</div>
	<!--面包屑导航结束-->
	<!--footer开始-->
	<%@include file="../common/footer.jsp" %>
	<!--footer结束-->
</div>
</body>
<script type="text/javascript">
	$(function(){
		$(".t_del").click(function(){
			var id = $(this).attr("id");
			
			$.ajax({
		    	url:"<%=basePath %>fwz/ssAccount/checkRoleIsUsed.do",
		    	type:"post",
		    	dataType:"json",
		    	data:{id:id},
		    	success:function(response){
		    		if(response == true){
		    	        layer.alert('该角色有用户在使用！', {
		    	            skin: 'layui-layer-molv' //样式类名
		    	            ,closeBtn: 0
		    	          });
		    	        
		    	        return false;
		    		}else{
		    			$.ajax({
		    		    	url:"<%=basePath %>fwz/ssRoleManage/remove.do",
		    		    	type:"post",
		    		    	dataType:"json",
		    		    	data:{id:id},
		    		    	success:function(response){
		    		    		if(response.callStatus == 'SUCCESS'){
		    		    	        layer.alert('删除成功！', {
		    		    	            skin: 'layui-layer-molv' //样式类名
		    		    	            ,closeBtn: 0
		    		    	          }, function(){
		    		    	   	   		window.location.href = "<%=basePath %>fwz/ssRoleManage/list.do";
		    		    	          });
		    		    		}
		    		    	}
		    			});
		    		}
		    	}
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
	        
	        if($(this).attr("class").indexOf("disabled") >= 0)
	        	return false;
	        
	        $("form:first").submit();
	    })
	    
	    $('#top_wavePagenation li').click(function(){
	      $(this).addClass('active').siblings().removeClass('active');
	    })
	});
</script>
</html>