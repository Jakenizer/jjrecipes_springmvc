<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="_csrf" content="${_csrf.token}"/>
<!-- default header name is X-CSRF-TOKEN-->
<meta name="_csrf_header" content="${_csrf.headerName}"/>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">

<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet">

<link href="<c:url value="/resources/css/create_modify_recipe.css" />" rel="stylesheet">
<script type="text/javascript">
$(document).ready(function(){
	$('#errorWindow').css('display', 'none');

	$('#showErrorButton').click(function() {		
		$('#errorWindow').css('display', 'inline');
		$('#showErrorButton').hide();
	});
});
</script>
<title>JJRecipes</title>
</head>

<body>
<div class="container">

	<jsp:include page="topPanel.jsp" flush="true"/>

	<div id="bigBox">
		<span>Felmeddelande: <font color="red"><c:out value="${message}"></c:out></font></span><br><br>
		<span><a href="${returnpage}">Gå tillbaka</a></span><br><br>
		<button id="showErrorButton">Visa fel</button>
		<textarea rows="47" cols="108" id="errorWindow">${exception}</textarea>
	</div>

	<jsp:include page="bottomPanel.jsp" flush="true"/>
</div>
</body>
</html>