<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.kxwp.common.utils.VersionUtil,com.kxwp.common.constants.config.ProjectConfig" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
	String contextPath = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() 
	+ ":" + request.getServerPort()
	        + contextPath + "/";
%>

