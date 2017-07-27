<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script id="supplier_list_tpl" type="text/html">
	{{each list as value i}}
	<dl class="list" data_supplier='{{value.id}}'>
	<a href="/h5/supplier/supplierDetail.do?supplierID={{value.id}}">
		<dt><img src="<%=contextPath %>/images/gys_list.png" onerror="this.src='../../../images/404.png'"></dt>
		<dd>
			<h4 class="company">{{value.supplierName}}</h4>
			<p class="z-y">主营业务： {{each value.businessScopeList as business index}}{{business.category_name}}{{if value.businessScopeList.length != index + 1}},{{/if}}{{/each}}</p>
		</dd>
	</a>
	</dl>
	{{/each}}
</script>










