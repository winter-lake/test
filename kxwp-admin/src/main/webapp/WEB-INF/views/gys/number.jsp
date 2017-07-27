<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>账号管理</title>
	
	<link rel="stylesheet" href="<%=basePath %>css/gys/number.css">
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
			<form class="k_change" method="post" action="<%=basePath%>gys/suAccount/list.do">
				<input type="hidden" class="pagecount" name="currentPage" value="${exchangeData.pager.currentPage }">
				<input type="hidden" class="pagenumber" name="pageSize" value="${exchangeData.pager.pageSize }">
				<b class="k-b">
					<i>角色 :</i>
					<input type="hidden" class="pagecount">
					<input type="hidden" class="pagenumber">
					<select name="roleId" id="x_t">
					<option value="">请选择</option>
					 <c:forEach items="${suroles }" var="item">
					 	<option value="${item.id }">${item.name }</option>
					 </c:forEach>
					</select>
				</b>
				<b class="k-b">
					<i>状态 :</i>
					<select name="accountStatus" id="z_t">
					</select>
				</b>
				<b class="k-b">
					<i>姓名:</i>
					<input type="text" name="name" value="${accountQuery.name }">
				</b>
				<b class="k-b">
					<i>手机号:</i>
					<input type="text" name="mobile" value="${accountQuery.mobile }">
				</b>
				<input type="button" id="Inquire" value="查询">
			</form>
			<!--table-->
			<table class="table table-bordered" style="text-align:center">
			  	<thead style="background:#eee">
					<tr>
						<!-- <td><input type="checkbox" name="b"></td> -->
						<td>编号</td>
						<td>姓名</td>
						<td>手机号</td>
						<td>角色</td>
						<td>状态</td>
						<td>操作</td>
					</tr>
				</thead>
				<tbody>
					<c:set var="adminStatus" value="0"></c:set>
				    <c:forEach items="${supplierAccounts }" var="item" varStatus="status">
						<tr>
							<!-- <td><input type="checkbox" name="a"></td> -->
							<td>${status.index+1}</td>
							<td>${item.name }</td>
							<td>${item.mobile }</td>
							<td>
								<c:forEach items="${item.suRoles }" var="item2" varStatus="status">
								<c:if test="${status.last}">
									<span>${item2.name }</span>
								</c:if>
								<c:if test="${!status.last}">
									<span>${item2.name }/</span>
								</c:if>
								</c:forEach>
								<c:forEach items="${item.suRoles }" var="item2">
								<c:if test="${item2.name == 'admin'}">
									<c:set var="adminStatus" value="${adminStatus + 1 }"></c:set>
								</c:if>
								</c:forEach>
							</td>
							<c:if test="${item.accountStatus == 'VALID' }">
								<td>合法</td>
							</c:if>
							<c:if test="${item.accountStatus == 'INVALID' }">
								<td>无效</td>
							</c:if>
							<c:if test="${item.accountStatus == 'NEEDRESET' }">
								<td>需要重置</td>
							</c:if>
							<c:if test="${item.accountStatus == 'DIMISSION' }">
								<td>已离职</td>
							</c:if>
							<td>
								<c:if test="${item.accountStatus == 'INVALID' || item.accountStatus == 'DIMISSION'}">
									<a href="#" id="${item.id }"  class="start">启用</a>
								</c:if>
								<c:if test="${(item.accountStatus == 'VALID' || item.accountStatus == 'NEEDRESET') && adminStatus > 1}">
									<a href="#" id="${item.id }"  class="stop">停用</a>
								</c:if>
								<c:if test="${adminStatus > 1}">
								<a href="<%=basePath %>gys/suAccount/get.do?id=${item.id }" class="t_edit">编辑</a><br>
								</c:if>
								<a href="#" class="t_view" id="${item.id }" mobile="${item.mobile }">重置密码</a>
								<c:if test="${adminStatus > 1}">
								<a href="#" class="dengji" id="${item.id }">登记离职</a>
								</c:if>
								</td>
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
		  	<li><a href="#">供应商</a></li>
		  	<li><a href="#">账号管理</a></li>
		  	<li><a href="#">账号列表</a></li>
		</ol>
		<a href="<%=basePath%>gys/suAccount/dimission.do" class="Quit_number">离职账户</a>
		<a href="<%=basePath%>gys/suAccount/addInit.do" class="add_menu"><i class="icon-plus"></i>添加账号</a>
	</div>
	<!--面包屑导航结束-->
	<%@include file="../common/footer.jsp" %>
</div>	
<!--end-->
<script>
$(document).ready(function() {
	//初始化状态
	$.ajax({
        url:"<%=basePath%>common/ajax/getEnumListByName.do?enumname=AccountStatusEnum",
        type:'post',
        dataType:'json',
        success:function(data){
            renderz_t(data);
        }
    })
    /*系统*/
    function renderz_t(data){
    	var html = '<option value="">请选择</option>';
    	
        $.each(data.data,function(i,val){
            html+='<option value="'+val.name+'">'+val.desc+'</option>'
        })
        
        $('#z_t').html(html);
        $('#z_t option:last').remove();
        
        $('#z_t').val("${accountQuery.accountStatus}");
    }
	
	$("#x_t").val("${accountQuery.roleId}");
	
	Common.table();
	
	$(".stop").click(function(){
		 var supplierAccounts = [];
		 var supplierAccount = {};
		 
		 supplierAccount.id = $(this).attr("id");
		 supplierAccount.accountStatus = 'INVALID';
		 
		 supplierAccounts.push(supplierAccount);
		 
		 modifyStatus(supplierAccounts);
	});
	
	$(".dengji").click(function(){
		 var supplierAccounts = [];
		 var supplierAccount = {};
		 
		 supplierAccount.id = $(this).attr("id");
		 supplierAccount.accountStatus = 'DIMISSION';
		 
		 supplierAccounts.push(supplierAccount);
		 
		 modifyStatus(supplierAccounts);
	});
	
	$(".start").click(function(){
		 var supplierAccounts = [];
		 var supplierAccount = {};
		 
		 supplierAccount.id = $(this).attr("id");
		 supplierAccount.accountStatus = 'VALID';
		 
		 supplierAccounts.push(supplierAccount);
		 
		 modifyStatus(supplierAccounts);
	});
	
	$(".t_view").click(function(){
		var id = $(this).attr("id");
		var mobile = $(this).attr("mobile");
		
		$.ajax({
	        url:"<%=basePath%>gys/suAccount/resetPassword.do?id="+id+"&mobile="+mobile,
	        contentType:"application/json",
	        type:'post',
	        dataType:'json',
	        success:function(data){
	        	if(data.callStatus == 'SUCCESS'){
	                layer.alert(data.message, {
	                    skin: 'layui-layer-molv' //样式类名
	                    ,closeBtn: 0
	                  }, function(){
	           	   		window.location.href = "<%=basePath %>gys/suAccount/list.do";
	                  });
	        	}
	        }
	    })
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
        url:"<%=basePath%>gys/suAccount/modifyStatusBatch.do",
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
           	   		window.location.href = "<%=basePath %>gys/suAccount/list.do";
                  });
        	}
        }
    })
}
</script>
</body>
</html>