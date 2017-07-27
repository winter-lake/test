<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>账号管理</title>
	
	<link rel="stylesheet" href="<%=basePath %>css/zz/number.css">
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
			<form class="k_change" method="post" action="<%=basePath%>zz/msAccount/dimission.do" hidden="true" >
				<input type="hidden" class="pagecount" name="currentPage" value="${exchangeData.pager.currentPage }">
				<input type="hidden" class="pagenumber" name="pageSize" value="${exchangeData.pager.pageSize }">
				<b class="k-b">
					<i>角色 :</i>
					<input type="hidden" class="pagecount">
					<input type="hidden" class="pagenumber">
					<select name="" id="x_t">
						<option value="">所有角色</option>
						<option value="">商品管理员</option>
						<option value="">财务管理员</option>
					</select>
				</b>
				<b class="k-b">
					<i>状态 :</i>
					<select name="accountStatus" id="z_t">
					</select>
				</b>
				<input type="submit" id="Inquire" value="查询">
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
						<td>系统</td>
						<td>状态</td>
						<td>操作</td>
					</tr>
				</thead>
				<tbody>
				    <c:forEach items="${masterStationAccounts }" var="item" varStatus="status">
						<tr>
							<!-- <td><input type="checkbox" name="a"></td> -->
							<td>${status.index+1}</td>
							<td>${item.name }</td>
							<td>${item.mobile }</td>
							<td>角色</td>
							<td>总站</td>
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
							<td><a href="#" id="${item.id }"  class="start">重新任用</a></td>
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
		  	<li><a href="#">总站</a></li>
		  	<li><a href="#">账号管理</a></li>
		  	<li><a href="#">离职账号</a></li>
		</ol>
		<a href="<%=basePath%>zz/msAccount/list.do" class="add_menu"><i class="icon-plus"></i>账号列表</a>
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
            renderx_t(data);
        }
    })
    /*系统*/
    function renderx_t(data){
    	var html = '<option value="">请选择</option>';
    	
        $.each(data.data,function(i,val){
            html+='<option value="'+val.name+'">'+val.desc+'</option>'
        })
        
        $('#z_t').html(html);
        
        $('#z_t').val("${accountQuery.accountStatus}");
    }
	
	Common.table();
	
	$(".stop").click(function(){
		 var mStationAccounts = [];
		 var masterStationAccount = {};
		 
		 masterStationAccount.id = $(this).attr("id");
		 masterStationAccount.accountStatus = 'INVALID';
		 
		 mStationAccounts.push(masterStationAccount);
		 
		 modifyStatus(mStationAccounts);
	});
	
	$(".dengji").click(function(){
		 var mStationAccounts = [];
		 var masterStationAccount = {};
		 
		 masterStationAccount.id = $(this).attr("id");
		 masterStationAccount.accountStatus = 'DIMISSION';
		 
		 mStationAccounts.push(masterStationAccount);
		 
		 modifyStatus(mStationAccounts);
	});
	
	$(".start").click(function(){
		 var mStationAccounts = [];
		 var masterStationAccount = {};
		 
		 masterStationAccount.id = $(this).attr("id");
		 masterStationAccount.accountStatus = 'VALID';
		 
		 mStationAccounts.push(masterStationAccount);
		 
		 modifyStatus(mStationAccounts);
	});
	
	$(".t_view").click(function(){
		var id = $(this).attr("id");
		
		$.ajax({
	        url:"<%=basePath%>zz/msAccount/resetPassword.do?id="+id,
	        contentType:"application/json",
	        type:'post',
	        dataType:'json',
	        success:function(data){
	        	if(data.callStatus == 'SUCCESS'){
	                layer.alert('操作成功！', {
	                    skin: 'layui-layer-molv' //样式类名
	                    ,closeBtn: 0
	                  }, function(){
	           	   		window.location.href = "<%=basePath %>zz/msAccount/list.do";
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
    
})


function modifyStatus(param){
	$.ajax({
        url:"<%=basePath%>zz/msAccount/modifyStatusBatch.do",
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
           	   		window.location.href = "<%=basePath %>zz/msAccount/list.do";
                  });
        	}
        }
    })
}
</script>
</body>
</html>