<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet">
<script src="<c:url value="/resources/js/jquery-1.11.2.js" />"></script>
<script type="text/javascript">
$(document).ready(function(){
	$('#errorWindow').css('display', 'none');

	$('#showErrorButton').click(function() {		
		$('#errorWindow').css('display', 'inline');
	});
});
</script>
<title>JJRecipes</title>
</head>
<body>

</body>


<body>
	<jsp:include page="topPanel.jsp" flush="true"/>

	<div id="bigBox">
		<span>Felmeddelande: <font color="red"><c:out value="${message}"></c:out></font></span><br><br>
		<span><a href="${returnpage}">Gå tillbaka</a></span><br><br>
		<button id="showErrorButton">Visa fel</button>
		<textarea rows="47" cols="108" id="errorWindow">${exception}</textarea>
	</div>

	<jsp:include page="bottomPanel.jsp" flush="true"/>
</body>
</html>