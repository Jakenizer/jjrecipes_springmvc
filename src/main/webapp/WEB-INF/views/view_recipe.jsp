<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet">
<script src="<c:url value="/resources/js/main.js" />"></script>

<script src="<c:url value="/resources/js/jquery-1.11.2.js" />"></script>
<script src="<c:url value="/resources/js/jquery-ui-1.11.4/jquery-ui.min.js" />"></script>
<title>JJRecipes</title>
</head>
<body>
	<jsp:include page="topPanel.jsp" flush="true"/>

	<div id="bigBox">
		<span>name: <c:out value=""></c:out></span>	
	
		<form action="" method="get"></form>
	</div>

	<jsp:include page="bottomPanel.jsp" flush="true"/>
</body>
</html>>