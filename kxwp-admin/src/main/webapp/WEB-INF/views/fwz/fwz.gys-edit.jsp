<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<title>添加供应商</title>
<%@ include file="../common/share_static.jsp"%>
</head>
<body>
	<!--外围容器-->
	<div class="k_contaniner">
		<%@ include file="../common/header.jsp"%>
		<%@ include file="../common/leftNav.jsp"%>

		<!--右边开始-->
		<div class="k_right">
			<div class="p_form_page">
				<div class="panel panel-default">
				  	<div class="panel-body" style="padding-top:0;">
				  		<form class="form-horizontal tx_14" id="add_gys_form">
				  			<input type="hidden" id="supplierID" value="${gys_detail.id }"/>
				  			<!-- 账户信息 start -->
					    	<div class="row w_div">
					    		<div class="col-xs-12 w_div_title">账户信息</div>
					    		<div class="col-xs-12 w_div_body">
					    			<div class="form-group hide">
									    <label class="col-xs-3 control-label"><i class="icon-asterisk tx_red"></i> 供应商名称</label>
									    <div class="col-xs-4">
									      <input type="text" class="form-control validate[required,maxSize[32]]" placeholder="供应商" />
									    </div>
									</div>
									<div class="form-group">
									    <label class="col-xs-3 control-label"><i class="icon-asterisk tx_red"></i> 负责人</label>
									    <div class="col-xs-4">
									      <input type="text" disabled="disabled" value="${gys_detail.supplierAdmin }" class="form-control validate[required,maxSize[32],custom[chinese]]" placeholder="请输入姓名" id="contact_person"/>
									    </div>
									</div>
									<div class="form-group">
									    <label class="col-xs-3 control-label"><i class="icon-asterisk tx_red"></i> 负责人手机号</label>
									    <div class="col-xs-4">
									      <input type="text" disabled="disabled" value="${gys_detail.supplierAdminMobile }" class="form-control validate[required,custom[mobile]]" placeholder="13261992222" id="account_mobile"/>
									    </div>
									</div>
									<div class="form-group">
									    <div class="col-xs-offset-3 col-xs-4">
									      	<div class="checkbox pl20">
									        	<label>
									          		<input type="checkbox" disabled="disabled" id="sendPassword" name="sendPassword" checked="checked" value="Y"> 选中后生成的密码将以短信的形式发送手机号中
									        	</label>
									      	</div>
									    </div>
								  	</div>
					    		</div>
					    	</div>
					    	<!-- 账户信息 end -->

					    	<!-- 商品信息 start -->
					    	<div class="row w_div">
					    		<div class="col-xs-12 w_div_title">供应商信息</div>
					    		<div class="col-xs-12 w_div_body">
					    			<div class="form-group">
									    <label class="col-xs-3 control-label"><i class="icon-asterisk tx_red"></i> 供应商名称</label>
									    <div class="col-xs-4">
									      <input type="text" disabled="disabled" value="${gys_detail.supplierName }" class="form-control validate[required,maxSize[32],custom[chinese]]" placeholder="请输入供应商名称" id="supplier_name"/>
									    </div>
									</div>
									<div class="form-group">
									    <label class="col-xs-3 control-label"><i class="icon-asterisk tx_red"></i> 企业法人</label>
									    <div class="col-xs-4">
									      <input type="text" disabled="disabled" value="${gys_detail.legalPerson }" class="form-control validate[required,maxSize[32],custom[chinese]]" placeholder="请输入企业法人姓名" id="legalPerson" />
									    </div>
									</div>
									<div class="form-group">
									    <label class="col-xs-3 control-label">业务员</label>
									    <div class="col-xs-4">
									      <input type="text" disabled="disabled" class="form-control" placeholder="请输入业务员姓名">
									    </div>
									</div>
									<div class="form-group">
									    <label class="col-xs-3 control-label"><i class="icon-asterisk tx_red"></i> 客服电话</label>
									    <div class="col-xs-4">
									      <input type="text" disabled="disabled" value="${gys_detail.service_phone }" class="form-control" placeholder="请输入客服电话" id="service_phone" class="validate[required,custome[phone]]">
									    </div>
									</div>
									<div class="form-group">
									    <label class="col-xs-3 control-label"><i class="icon-asterisk tx_red"></i> 地址</label>
									    <div class="col-xs-4">
									      <input type="text" disabled="disabled" value="${gys_detail.full_address }" class="form-control validate[required]" placeholder="请输入详细地址" id="street">
									    </div>
									</div>
					    		</div>
					    	</div>
					    	<!-- 商品信息 end -->

					    	<!-- 服务信息 start -->
					    	<div class="row w_div">
					    		<div class="col-xs-12 w_div_title">服务信息</div>
					    		<div class="col-xs-12 w_div_body">
					    			<div class="form-group">
									    <label class="col-xs-3 control-label"><i class="icon-asterisk tx_red"></i> 配送范围</label>
									    <div class="col-xs-4">
									      	<p class="form-control-static mt0">${shippingData.province.name },${shippingData.city.name } <i class="icon-question-sign e_hover_i" title="说明
供应商筛选配送范围是基于服务站的，所以配送范围需要在服务站的经营范围内进行选择，默认全部区域，添加供应商时进行减少即可。如其业务延伸至其他区域，需要到相关区域服务站注册账号。"></i></p>
									    </div>
									</div>
									<div class="form-group">
									    <div class="col-xs-offset-3 col-sm-4">
									    	<div class="w_label_box tree_box">
									    		<ul class="root">
									    		    <c:forEach items="${shippingData.county_list }" var="county">
									    		      <li class="child_li active">
									    		      <c:set var="iscontain" value="false" /> 
													  <c:forEach var="itemx" items="${shipping_area_list}" >   
														<c:if test="${itemx.regionId eq county.id}">     
															<c:set var="iscontain" value="true" />  
														</c:if> 
													  </c:forEach>
													  <c:if test="${iscontain == true}">
									    				<label class="first_label"><input type="checkbox" disabled="disabled" checked="checked" value="${county.id }"> ${county.name } <i class="icon-angle-down"></i><i class="icon-angle-up"></i></label>
									    			  </c:if>
													  <c:if test="${iscontain == false}">
									    				<label class="first_label"><input type="checkbox" disabled="disabled" value="${county.id }"> ${county.name } <i class="icon-angle-down"></i><i class="icon-angle-up"></i></label>
									    			  </c:if>
									    				<ul class="clearfix second_box">
									    					<c:forEach items="${county.childrenRegionList }" var="street">
									    					<c:set var="iscontain2" value="false" /> 
															  <c:forEach var="itemx2" items="${shipping_area_list}" >   
																<c:if test="${itemx2.regionId eq street.id}">     
																	<c:set var="iscontain2" value="true" />  
																</c:if> 
															  </c:forEach>
															  <c:if test="${iscontain2 == true}">
									    					<li><label><input type="checkbox" disabled="disabled" checked="checked" value="${street.id }" > ${street.name }</label></li>
									    					</c:if>
															  <c:if test="${iscontain2 == false}">
									    					<li><label><input type="checkbox" disabled="disabled" value="${street.id }" >${street.name }</label></li>
									    					</c:if>
									    					</c:forEach>
									    				</ul>
									    			</li>
									    		    </c:forEach>
									    		</ul>   	
									      	</div>
									    </div>
								  	</div>
									<div class="form-group">
									    <label class="col-xs-3 control-label"><i class="icon-asterisk tx_red"></i> 经营范围</label>
									    <div class="col-xs-4">
									    	<div class="w_label_box j_category">
									    	    <c:forEach items="${fir_category_list }" var="category">
									    	    <c:set var="iscontain3" value="false" /> 
															  <c:forEach var="itemx3" items="${gys_detail.businessScopeList}" >   
																<c:if test="${itemx3.categoryId eq category.id}">     
																	<c:set var="iscontain3" value="true" />  
																</c:if> 
															  </c:forEach>
															  <c:if test="${iscontain3 == true}">
									    	    <label><input name="fir_category" type="checkbox" checked="checked" value="${category.id }" class="validate[minCheckbox[1]]"> ${category.name }</label>
									    	    </c:if>
															  <c:if test="${iscontain3 == false}">
									    	    <label><input name="fir_category" type="checkbox" value="${category.id }" class="validate[minCheckbox[1]]"> ${category.name }</label>
									    	    </c:if>
									    	    </c:forEach>
									      	</div>
									    </div>
									</div>
									<div class="form-group">
									    <label class="col-xs-3 control-label"><i class="icon-asterisk tx_red"></i> 最低起送金额</label>
									    <div class="col-xs-4">
									      <input type="text" value="${gys_detail.minShippingAmount}" class="form-control validate[required,custom[number]]" placeholder="请输入最低起送金额" id="minShippingAmount">
									    </div>
									</div>
									<div class="form-group hide">
									    <label class="col-xs-3 control-label">优惠信息</label>
									    <div class="col-xs-4">
									      <input type="text" class="form-control" placeholder="请输入优惠信息">
									    </div>
									</div>
									<div class="form-group">
									    <label class="col-xs-3 control-label"><i class=" "></i> 代理品牌</label>
									    <div class="col-xs-4">
									    	<select class="form-control" id="search_brand">
									    		<option value="">请选择代理品牌</option>
									    	</select>
									    </div>
									</div>
									<div class="form-group">
									    <label class="col-xs-3 control-label"><i class="icon-asterisk tx_red"></i> 已选品牌</label>
									    <div class="col-xs-4">
									    	<div class="w_flag_box j_brand_selected">
									    		<c:forEach items="${gys_detail.agentBrandList}" var="item">
									    			<span class="label label-info" data-brandid="${item.brandId}">${item.brand_name}<i title="移除" class="icon-remove"></i></span>
									    		</c:forEach>
									      	</div>
									    </div>
									</div>
					    		</div>
					    	</div>
					    	<!-- 服务信息 end -->

					    	<!-- 认证信息 start -->
					    	<div class="row w_div">
					    		<div class="col-xs-12 w_div_title">认证信息</div>
					    		<div class="col-xs-12 w_div_body">
					    			<div class="form-group">
									    <label class="col-xs-3 control-label"> 社会统一信用代码</label>
									    <div class="col-xs-4">
									      <input type="text" disabled="disabled" value="${gys_detail.licenseNum }" placeholder="18位信用代码"  class="form-control validate[custom[creditCode]]" id="license_num">
									    </div>
									</div>
									<div class="form-group" >
									    <label class="col-xs-3 control-label"> 营业执照扫描件</label>
									    <div class="col-xs-4">
									    	<div class="dis_inb">
												<label class="w_filebox">
													<img src="<%=contextPath %>/images/share/sctp.png" class="add_img">
													<input type="file" name="file" id="j_main_pic" disabled="disabled">
												</label>
												<input type="hidden" name="keyID" value="1000000000495">
												<input type="hidden" name="bucketName" value="goodsmain">
											</div>
									    </div>
									</div>
									<div class="form-group">
									    <label class="col-xs-3 control-label"> 身份证号</label>
									    <div class="col-xs-4">
									      <input type="text" disabled="disabled" value="${gys_detail.identityCardNum }" placeholder="18位身份证号码" class="form-control validate[custom[chinaId]]" id="identityCardNum">
									    </div>
									</div>
									<div class="form-group">
									    <label class="col-xs-3 control-label"> 身份证扫描件</label>
									    <div class="col-xs-4">
									    	<div class="dis_inb">
												<label class="w_filebox">
													<img src="<%=contextPath %>/images/share/sctp.png" class="add_img">
													<input type="file" name="file" id="j_main_pic" disabled="disabled">
												</label>
												<input type="hidden" name="keyID" value="1000000000495">
												<input type="hidden" name="bucketName" value="goodsmain">
											</div>
									    </div>
									</div>
									<div class="form-group">
									    <label class="col-xs-3 control-label">备注</label>
									    <div class="col-xs-4">
									      <textarea disabled="disabled" class="form-control" rows="4" placeholder="请输入备注" id="remark"></textarea>
									    </div>
									</div>
									<div class="form-group">
								    	<div class="col-xs-offset-3 col-xs-1">
								      		<button type="button" class="btn btn-primary btn-block j_submit">保存</button>
								    	</div>
								    	<div class="col-xs-offset-1 col-xs-1">
								      		<button type="button" class="btn btn-default btn-block j_back">返回</button>
								    	</div>
								  	</div>
					    		</div>
					    	</div>
					    	<!-- 认证信息 end -->
				    	</form>
				  	</div>
				</div>
			</div>
		</div>
		<!--右边结束-->
		<!--右边结束-->
		<!--面包屑导航-->
		<div class="k_breadcrumb">
			<ol class="breadcrumb" style="background: #fff">
				<li><a href="#">服务站</a></li>
				<li><a href="#">供应商管理</a></li>
				<li><a href="#">添加供应商会员</a></li>
			</ol>
			 <a href="<%=contextPath %>/fwz/manager/gys/gysList.do" class="add_menu"><i class="icon-plus"></i>供应商列表</a>
		</div>
		<!--面包屑导航结束-->
		<!--footer开始-->
		<%@include file="../common/footer.jsp"%>
		<!--footer结束-->
	</div>
	<!--end-->
	<script type="text/javascript">
		seajs.use("fwz/gys/edit", function(view) {
			view.init();
		});
	</script>
</body>
</html>