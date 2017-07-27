<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!--左边开始-->
<div class="k_left">
	<!--菜单导航-->
	<div class="w_side_menu tx_14">
        <ul>
        
            <c:forEach items="${menu_list }" var="menu">
	               <li class="j_haschild module active">
	                <a href="#">
	                    <i class="icon-sitemap tx_16 mr10"></i><span class="name">${menu.item_name }</span>
	                    <i class="icon-angle-right f_right"></i>
	                    <i class="icon-angle-down f_right"></i>
	                </a>
	                <ul>
	                   <c:forEach items="${menu.children }" var="item">
	                   	  <li class="end-li"><a href="${item.item_url }" data-path="${item.data_path }"><span class="icon-star-f mr5">${item.item_name }</span></a></li>
	                   </c:forEach>
	                </ul>
	           	 </li>
            </c:forEach>
            
            <li class="j_haschild module hide">
                <a href="#">
                    <i class="icon-sitemap tx_16 mr10"></i><span class="name">总站</span>
                    <i class="icon-angle-right f_right"></i>
                    <i class="icon-angle-down f_right"></i>
                </a>
                <ul>
                	<li class="end-li"><a href="<%=contextPath%>/zz/msResourceManage/list.do" data-path="/zz/msResourceManage/list.do,/zz/msResourceManage/addInit.do,/zz/msResourceManage/get.do,/zz/msResourceManage/get.do"><span class="icon-star-f mr5">菜单管理</span></a></li>
					<li class="end-li"><a href="<%=contextPath%>/zz/msRoleManage/list.do" data-path="/zz/msRoleManage/list.do,/zz/msRoleManage/addInit.do,/zz/msRoleManage/get.do,/zz/msRoleManage/view.do"><span class="icon-star-f mr5">角色管理</span></a></li>
					<li class="end-li"><a href="<%=contextPath%>/zz/msAccount/list.do" data-path="/zz/msAccount/list.do,/zz/msAccount/dimission.do,/zz/msAccount/addInit.do,/zz/msAccount/get.do"><span class="icon-star-f mr5">账号管理</span></a></li>
					<li class="end-li"><a href="<%=contextPath%>/zz/manager/fwz/listFWZ.do" data-path="/zz/manager/fwz/listFWZ.do,/zz/manager/fwz/gotoAddFWZ.do,/zz/manager/fwz/queryFWZDetail.do"><span class="icon-star-f mr5">服务站列表</span></a></li>
					<li class="end-li"><a href="<%=contextPath%>/zz/supermarket/supermarketManager.do" data-path="/zz/supermarket/supermarketManager.do,/zz/supermarket/getSupermarketId.do"><span class="icon-star-f mr5">超市会员管理</span></a></li>
					<li class="end-li"><a href="<%=contextPath%>/zz/msAd/findInit.do" data-path="/zz/msAd/findInit.do"><span class="icon-star-f mr5">广告管理</span></a></li>
                	<li class="end-li"><a href="<%=contextPath%>/zz/ssBrand/queryBrandInit.do" data-path="/zz/ssBrand/queryBrandInit.do,/zz/ssBrand/goaddbrand.do"><span class="icon-star-f mr5">品牌管理</span></a></li>
                	<li class="end-li"><a href="<%=contextPath%>/zz/goods/gotoListGoods.do" data-path="/zz/goods/gotoListGoods.do,/zz/goods/listGoods.do,/zz/goods/gotoGoodsDetail.do"><span class="icon-star-f mr5">商品管理</span></a></li>
                	<li class="end-li"><a href="<%=contextPath%>/zz/manager/gys/gysList.do" data-path="/zz/manager/gys/gysList.do,/zz/manager/gys/gotoAddGys.do,/zz/manager/gys/getGysById.do,/zz/manager/gys/ajax/querygys.do,/zz/manager/gys/gotoGYSDetail.do"><span class="icon-star-f mr5">供应商管理</span></a></li>
                	<li class="end-li"><a href="<%=contextPath%>/zz/manager/salesOrder/gotolistSalesOrder.do" data-path="/zz/manager/salesOrder/gotolistSalesOrder.do"><span class="icon-star-f mr5">订单管理</span></a></li>
                	<li class="end-li"><a href="<%=contextPath%>/zz/fwzAccount/list.do" data-path="/zz/fwzAccount/list.do"><span class="icon-star-f mr5">服务站账号管理</span></a></li>
                	<li class="end-li"><a href="<%=contextPath%>/zz/suAccount/list.do" data-path="/zz/suAccount/list.do"><span class="icon-star-f mr5">供应商账号管理</span></a></li>
                </ul>
            </li>
            <li class="j_haschild module  hide">
                <a href="#">
                    <i class="icon-flag tx_16 mr10"></i><span class="name">服务站</span>
                    <i class="icon-angle-right f_right"></i>
                    <i class="icon-angle-down f_right"></i>
                </a>
                <ul>
                    <li class="j_haschild">
                        <a href="#">
                            <span class="icon-folder-close-alt"></span>
                            <span class="icon-folder-open-alt"></span>二级菜单
                            <i class="icon-angle-right f_right"></i>
                            <i class="icon-angle-down f_right"></i>
                        </a>
                        <ul>
                            <li class="end-li">
                                <a href="#">
                                    <span class="icon-star-f mr5"></span>三级菜单
                                </a>
                            </li>
                            <li class="end-li">
                                <a href="#">
                                    <span class="icon-star-f mr5"></span>三级菜单
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li class="end-li"><a href="<%=contextPath%>/fwz/goods/gotoListGoods.do" data-path="/fwz/goods/gotoListGoods.do,/fwz/goods/gotoGoodsDetail.do,/fwz/goods/gotoGoodsReview.do"><span class="icon-star-f mr5">商品审核</span></a></li>
                    <li class="end-li"><a href="<%=contextPath%>/fwz/supermarket/FWZManagerSupermarket.do" data-path="/fwz/supermarket/FWZManagerSupermarket.do,/fwz/supermarket/getSupermarketId.do,/fwz/supermarket/gotoAddSupermarket.do"><span class="icon-star-f mr5">超市会员管理</span></a></li>
					<li class="end-li"><a href="<%=contextPath%>/fwz/manager/gys/gysList.do" data-path="/fwz/manager/gys/gysList.do,//fwz/manager/gys/gotoAddGys.do,/fwz/manager/gys/getGysById.do,/fwz/manager/gys/ajax/querygys.do,/fwz/manager/gys/gotoGYSDetail.do"><span class="icon-star-f mr5">供应商管理</span></a></li>
					<li class="end-li"><a href="<%=contextPath%>/fwz/ssRoleManage/list.do" data-path="/fwz/ssRoleManage/list.do,/fwz/ssRoleManage/addInit.do,/fwz/ssRoleManage/view.do,/fwz/ssRoleManage/get.do"><span class="icon-star-f mr5">角色管理</span></a></li>
					<li class="end-li"><a href="<%=contextPath%>/fwz/ssAccount/list.do" data-path="/fwz/ssAccount/list.do,/fwz/ssAccount/dimission.do,/fwz/ssAccount/addInit.do,/fwz/ssAccount/get.do"><span class="icon-star-f mr5">账号管理</span></a></li>
					<li class="end-li"><a href="<%=contextPath%>/fwz/ssAd/findInit.do" data-path="/fwz/ssAd/findInit.do,/fwz/ssAd/addInit.do"><span class="icon-star-f mr5">广告管理</span></a></li>
					<li class="end-li"><a href="<%=contextPath%>/fwz/manager/salesOrder/gotolistSalesOrder.do" data-path="/fwz/manager/salesOrder/gotolistSalesOrder.do,/fwz/manager/salesOrder/gotolistSalesOrder.do"><span class="icon-star-f mr5">订单管理</span></a></li>
                </ul>
            </li>
            <li class="j_haschild module  hide">
                <a href="#">
                    <i class="icon-suitcase tx_16 mr10"></i><span class="name">供应商</span>
                    <i class="icon-angle-right f_right"></i>
                    <i class="icon-angle-down f_right"></i>
                </a>
                <ul>
                	<li class="end-li"><a href="<%=contextPath%>/gys/suRoleManage/list.do" data-path="/gys/suRoleManage/list.do,/gys/suRoleManage/addInit.do,/gys/suRoleManage/get.do,/gys/suRoleManage/view.do"><span class="icon-star-f mr5">角色管理</span></a></li>
					<li class="end-li"><a href="<%=contextPath%>/gys/suAccount/list.do" data-path="/gys/suAccount/list.do,/gys/suAccount/dimission.do,/gys/suAccount/addInit.do,/gys/suAccount/get.do"><span class="icon-star-f mr5">账号管理</span></a></li>
					<li class="end-li"><a href="<%=contextPath%>/gys/goods/gotoListGoods.do" data-path="/gys/goods/gotoListGoods.do,/gys/goods/gotoAddGoods.do,/gys/goods/gotoGoodsDetail.do"><span class="icon-star-f mr5">商品列表</span></a></li>
					<li class="end-li"><a href="<%=contextPath%>/gys/manager/salesOrder/gotolistSalesOrder.do" data-path="/gys/manager/salesOrder/gotolistSalesOrder.do,/gys/manager/salesOrder/gotolistSalesOrder.do"><span class="icon-star-f mr5">订单管理</span></a></li>
                </ul>
            </li>
        </ul>
        <div class="switch">
        	<i class="icon-caret-left before"></i>
            <i class="icon-caret-right after"></i>
        </div>
    </div>
	<!--end-->
</div>
<!--左边结束-->