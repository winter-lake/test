<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>首页</title>
	<%@include file="../common/share_static.jsp" %>
	<link rel="stylesheet" type="text/css" href="<%=ProjectConfig.cdn_host %>/css/swiper.min.css">
	<link rel="stylesheet" type="text/css" href="<%=ProjectConfig.cdn_host %>/css/index.css?t=<%=VersionUtil.VERSIONNO %>">
	<script src="<%=ProjectConfig.cdn_host %>/js/swiper.min.js"></script>
</head>
<body>
	<!--开始-->
	<div class="j_container">
		<!--头部-->
		<header class="header">
			<p class="search">
				<i class="icon-search"></i><input type="text" name="" placeholder="搜索商品/供应商" id="search">
			</p>
			<a class="message" href="<%=contextPath %>/h5/news/new_list.do"></a>
		</header>
		<!--头部-->
		<!--主体-->
		<section class="j_content" style="overflow-y:auto">
			<div class="wrapper"> 
				<!--焦点图-->
				<div class="swiper-container">
				    <div class="swiper-wrapper">
				        <c:forEach items="${homeContent.bannerList }" var="banner">
				        	<div class="swiper-slide"><a href="${banner.open_url }"><img src="${banner.photo_url }" onerror="this.src='<%=contextPath %>/images/404.png'"></a></div>
				        </c:forEach>
				    </div>
				    <!-- 如果需要分页器 -->
				    <div class="swiper-pagination"></div>
				</div>
				<!--分类-->
				<div class="fl">
					<div class="fl-menu">
						<dl>
							<a href="<c:if test="${homeContent.categoryList != null && fn:length(homeContent.categoryList) gt 0 }">${(homeContent.categoryList[0]).open_url}</c:if>">
								<dt><img src="<%=contextPath %>/images/icon-1.png"></dt>
								<dd>休闲食品</dd>
							</a>
						</dl>
						<dl>
							<a href="<c:if test="${homeContent.categoryList != null && fn:length(homeContent.categoryList) gt 0 }">${(homeContent.categoryList[1]).open_url}</c:if>">
								<dt><img src="<%=contextPath %>/images/icon-2.png"></dt>
								<dd>酒水饮料</dd>
							</a>
						</dl>
						<dl>
							<a href="<c:if test="${homeContent.categoryList != null && fn:length(homeContent.categoryList) gt 0 }">${(homeContent.categoryList[2]).open_url}</c:if>">
								<dt><img src="<%=contextPath %>/images/icon-3.png"></dt>
								<dd>粮油副食</dd>
							</a>
						</dl>
						<dl>
							<a href="<c:if test="${homeContent.categoryList != null && fn:length(homeContent.categoryList) gt 0 }">${(homeContent.categoryList[3]).open_url}</c:if>">
								<dt><img src="<%=contextPath %>/images/icon-4.png"></dt>
								<dd>进口商品</dd>
							</a>
						</dl>
					</div>
					<div class="fl-menu">
						<dl>
							<a href="<c:if test="${homeContent.categoryList != null && fn:length(homeContent.categoryList) gt 0 }">${(homeContent.categoryList[4]).open_url}</c:if>">
								<dt><img src="<%=contextPath %>/images/icon-5.png"></dt>
								<dd>母婴用品</dd>
							</a>
						</dl>
						<dl>
							<a href="<c:if test="${homeContent.categoryList != null && fn:length(homeContent.categoryList) gt 0 }">${(homeContent.categoryList[5]).open_url}</c:if>">
								<dt><img src="<%=contextPath %>/images/icon-6.png"></dt>
								<dd>日用百货</dd>
							</a>
						</dl>
						<dl>
							<a href="<c:if test="${homeContent.categoryList != null && fn:length(homeContent.categoryList) gt 0 }">${(homeContent.categoryList[6]).open_url}</c:if>">
								<dt><img src="<%=contextPath %>/images/icon-7.png"></dt>
								<dd>水果生鲜</dd>
							</a>
						</dl>
						<dl>
							<a href="<c:if test="${homeContent.categoryList != null && fn:length(homeContent.categoryList) gt 0 }">${(homeContent.categoryList[7]).open_url}</c:if>">
								<dt><img src="<%=contextPath %>/images/icon-8.png"></dt>
								<dd>文体办公</dd>
							</a>
						</dl>
					</div>
				</div>
				<!-- 天天特价 -->
				<div class="daily_specials">
					<h4 class="d_title">天天特价<a href="<%=contextPath %>/h5/channel/listChannelInit.do"><span class="more"><label>更多</label></span></a></h4>
					<div class="overflow">
						<div class="goods_scroll" id="goods_scroll">
							
						</div>
					</div>
				</div>
				<!--品牌-->
				<div class="pp">
					<h4 class="p-title">推荐品牌</h4>
					<c:forEach items="${homeContent.brandList}" var="brand" varStatus="brand_status">
					  <c:if test="${brand_status.index mod 3  eq 0}">
					      <div class="pp-menu">
					  </c:if>
						<p class="img"><a href="${brand.open_url}"><img src="${brand.photo_url}" alt="" onerror="this.src='../../../images/404.png'"/></a></p>
					  <c:if test="${(brand_status.index+1) mod 3  eq 0 and brand_status.index gt 0 }">
					      </div>
					  </c:if>
					</c:forEach>
				</div>
				<!--供应商-->
				<div class="gys">
					<h4 class="gys-title">优质供应商</h4>
					<c:forEach items="${homeContent.supplierList }" var="supplier" varStatus="supplier_status">
						<dl class="gys_dl_1">
							<dt>
								<a href="${supplier.supplier_detail.open_url }">
									<img src="<%=contextPath %>/images/sjmy.png" onerror="this.src='../../../images/404.png'">
								</a>
							</dt>
							<dd>
								<a href="${supplier.supplier_detail.open_url }">
									<h4 class="name">${supplier.supplier_detail.title }</h4>
									<p class="main">${supplier.supplier_detail.sub_title }</p>
								</a>
							</dd>
						</dl>
						
						<c:forEach items="${supplier.recommendGoodsList }" var="goods" varStatus="goods_status">
						   <c:if test="${goods_status.index + 1 eq 1 }">
						       <div class="show" style="overflow-x:scroll">
						       <div class="show_scroll">
						   </c:if>
						   <div class="s-box">
							<a href="${goods.open_url }">
								<dl>
									<dt><img src="${goods.photo_url }" alt="" onerror="this.src='../../../images/404.png'"/></dt>
									<dd>￥${goods.title }</dd>
								</dl>
							</a>
						</div>
						   <c:if test="${goods_status.index + 1 eq fn:length(supplier.recommendGoodsList) }">
						   </div>
						   </div>
						   </c:if>
						</c:forEach>
					</c:forEach>
				</div>
				<!--商品展示-->
				<div class="sp">
					<h4 class="sp-title">商品展示</h4>
					<div class="sp-box" id="sp-box"></div>
				</div>
			</div> 
		</section>
		<!--主体-->
		<!--footer-->
		<%@ include file="../common/footer.jsp" %>
		<!--footer-->
	</div>
	<!--结束-->
	<!-- 引入模板 -->
	<%@include file="../../../tpl/index_daily_specials.tpl" %>
	<%@include file="../../../tpl/index_list.tpl" %>
	<script src="<%=ProjectConfig.cdn_host %>/js/index.js?t=<%=VersionUtil.VERSIONNO %>"></script>
</body>
</html>