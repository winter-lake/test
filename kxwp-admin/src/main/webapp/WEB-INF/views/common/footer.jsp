<%@page import="com.kxwp.common.constants.APPBuildVersion"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<!--footer开始-->
	<div class="k_footer">
		<p class="f-p">版权所有 @ 北京快消互联科技发展有限公司 未经许可 严禁复制</p>
		<p class="f-p">京ICP备16000340 号(<%=APPBuildVersion.APP_BUILD_TIMESTAMP %>)</p>
		<p class="f-p">北京经济技术开发区地盛南街9号1栋 客服电话 : 400-677-0928</p>
	</div>
	<!--footer结束-->
	
	
	<script type="text/javascript">
		seajs.use("app", function(app) {
			app.init();
		})
	</script>