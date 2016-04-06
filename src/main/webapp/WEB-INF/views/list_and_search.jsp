<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

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
<link href="<c:url value="/resources/css/list_and_search.css" />" rel="stylesheet">

<script src="<c:url value="/resources/js/main.js" />"></script>

<script type="text/javascript">
         $(document).ready(function() {
        	 $('.listAnchor').click(function(event){
					$("#idVal").val(event.target.id);
            	$("#submitRec").submit();
        	 });
         });
</script>
<title>JJRecipes list and search</title>
</head>
<body>
<div class="container">

	<jsp:include page="topPanel.jsp" flush="true"/>
	<div id="bigBox">
		<form action="searchRecipe" method="get">
			<div class="searchCont">
				<input class="lefter" type="text" name="inputText">
				<input class="lefter" type="submit" value="Sök">
			</div>

			 
			<div id="leftDiv" class="cont">
				<ul>
					<c:forEach var="item" items="${recipes}"> 
						<li class="listElement"><div class="listAnchor" id="${item.id}">${item.name}</div></li>
	 			   </c:forEach>
				</ul>
			</div>

		</form>
			<jsp:include page="bottomPanel.jsp" flush="true"/>
		<form name="submitRecipe" action="recipe" method="post" id="submitRec">
			<input type="hidden" name="id" id="idVal">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		</form>
	</div>
</div>
	
</body>
</html>