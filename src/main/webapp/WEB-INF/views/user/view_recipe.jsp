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
<link href="<c:url value="/resources/css/view_recipe.css" />" rel="stylesheet">


<title>JJRecipes</title>
</head>
<body>
		<jsp:include page="topPanel.jsp" flush="true"/>

	<div class="container main-container">
		<div class="bigBox">
			<div id="headerDiv">
				<h1>${responseData.name}</h1>
				<form action="/JJRecipes/recipe/${responseData.id}/edit" method="post">	
				<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}"/>			
					<!-- <input type="hidden" name="recipeID" value="${responseData.id}"> -->
					<!-- <input type="image" src="resources/image/edit-icon.png"> -->
					<button type="button" class="btn btn-default" onclick="javascript:this.parentNode.submit();"><span class="glyphicon glyphicon-edit"></span></button>
					<button class="btn btn-primary btn-xs" data-title="Edit" data-toggle="modal" data-target="#edit">
						<span class="glyphicon glyphicon-pencil"></span>
					</button>
				</form>
			</div>
    		<div class="col-lg-7">
    			<div class="row">
      			<div id="contentCont">${responseData.content}</div>
      		</div>
      		<div class="row">
	      		<div id="ingredientCont">
      			<h2>Ingredienser</h2>
      			<c:if test="${not empty responseData.ingredients}">
		      		<ul class="list-group">
	      			<c:forEach items="${responseData.ingredients}" var="ing">
	      				<li class="list-group-item list-group-item-success">${ing.content}</li>
	      			</c:forEach>
	      			</ul>		
      			</c:if>
      		</div>
      		</div>
      		
    		</div>
    		<div class="col-lg-5">
    			<div class="row">
      			<div id=imageCont><img id='recipeImage' src='data:image/jpg;base64,${responseData.image}'/></div>
      		</div>
      		<div class="row">
      			<div id="tagsCont" class="col-lg-12">
		      		<h2>Taggar</h2>
		      		<c:if test="${not empty responseData.tags}">
		      			<ul class="list-group">
		      			<c:forEach items="${responseData.tags}" var="tag">
		      				<li class="list-group-item list-group-item-warning">${tag.name}</li>
		      			</c:forEach>
		      			</ul>		
		      		</c:if>
	      		</div>
	      	</div>
    		</div>
  		
		<!-- 
		 <input type="hidden"
			name="${_csrf.parameterName}"
			value="${_csrf.token}"/> -->
		</div>
	</div>
</body>
</html>