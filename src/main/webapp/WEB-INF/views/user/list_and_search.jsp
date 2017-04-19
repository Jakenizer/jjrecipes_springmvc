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
<link href="<c:url value="/resources/css/list_and_search.css" />" rel="stylesheet">

<script src="<c:url value="/resources/js/main.js" />"></script>

<script type="text/javascript">
         $(document).ready(function() {
        	 setActiveNav("#nav_listrecipes");
        	 $('.listAnchor').click(function(event){
					$("#idVal").val(event.target.id);
            	$("#submitRec").submit();
        	 });
         });
</script>
<title>JJRecipes list and search</title>
</head>
<body>
	<jsp:include page="topPanel.jsp" flush="true"/>

<div class="container">

	<form action="/JJRecipes/user/searchRecipe" method="post">
		<div class="row">
	        <div class="col-sm-4">
	            <div class="input-group stylish-input-group">
	                   <input name="inputText" type="text" class="form-control" placeholder="Search" >
	                   <span class="input-group-addon">
	                       <button type="submit">
	                           <span class="glyphicon glyphicon-search"></span>
	                       </button>  
	                   </span>
	             </div>
	        </div>
		</div>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	</form>

	<div class="row">
		<div class="col-lg-6">
			<ul class="list-group">
					<c:forEach var="item" items="${row1}"> 
						<li class="list-group-item list-group-item-info"><a class="listAnchor" href="/JJRecipes/recipe?id=${item.id}">${item.name}</a></li>
	 			   </c:forEach>	   
				</ul>
		</div>

		<div class="col-lg-6">
			<ul class="list-group">
					<c:forEach var="item" items="${row2}"> 
						<li class="list-group-item list-group-item-info"><a class="listAnchor" href="/JJRecipes/recipe?id=${item.id}">${item.name}</a></li>
	 			   </c:forEach>
				</ul>
		</div>
	</div>
	
	<ul class="pagination">
		<c:forEach var="i" begin="1" end="${paginationSize}">
	  		<li><a href="/JJRecipes/user/list_and_search/${i}">${i}</a></li>
	  </c:forEach>
	</ul>
	

	<div id="bigBox">
		<!-- <form action="searchRecipe" method="post">
			<div class="searchCont">
				<input class="lefter" type="text" name="inputText">
				<input class="lefter" type="submit" value="Sök">
			</div>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		</form>
			<div>
				<ul class="list-group">
					<c:forEach var="item" items="${recipes}"> 
						<li class="list-group-item list-group-item-info"><a class="listAnchor" href="/JJRecipes/recipe/${item.id}">${item.name}</a></li>
	 			   </c:forEach>
				</ul>
			</div>			  -->
			
	</div>
</div>
	
</body>
</html>