<%@page import="com.kxwp.common.constants.CommonRequestAttr"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script>
  var sys_url_prefix = '${sys_url_prefix}';
</script>
	<!--头部开始-->
	<div class="k_top">
		<div class="k_logo">
			<span class="k_logo1">
				<a href="<%=contextPath %>/${sys_url_prefix}/user/gotoDashboard.do"><img src="<%=contextPath %>/images/share/kxwp_logo.png" alt=""></a>
			</span>
			<span>
				<img src="<%=contextPath %>/images/share/title_${sys_url_prefix }.png" alt="">
			</span>
			<div class="k_Options">
				<span class="mr95 yh">
					<i>用户 :</i>
					<em id="user">${login_user.name }(${login_user.userNo })</em>
				</span>
			    <span class="js">
					<i>角色 :</i>
					<em id="yygl">${login_user.role_name }</em>
				</span>
				<div class="dropdown safe_center" id="j_save_center" data-sysprefix="${sys_url_prefix}">
				  <button class="btn btn-default dropdown-toggle" type="button" id="j_safe_center" data-toggle="dropdown">
				    安全中心
				    <span class="caret"></span>
				  </button>
				  <ul class="dropdown-menu dropdown-menu-right" aria-labelledby="j_safe_center">
				    <li><a href="/${sys_url_prefix}/userManage/goReset.do" class="j_reset_pwd"><i class="icon-unlock-alt"></i> 重置密码</a></li>
				    <li><a href="#" class="j_signout"><i class="icon-signout"></i> 退出系统</a></li>
				  </ul>
				</div>
				<span class="fwz-number">
					<em class="fwz-name">${login_user.serviceStation.serviceStationName } :</em>
					<em class="fwz-num">${login_user.serviceStation.phone }</em>
				</span>
			</div>                         
		</div>
	</div>
	<!--头部结束-->