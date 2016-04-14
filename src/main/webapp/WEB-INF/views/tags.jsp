<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<link href="<c:url value="/resources/css/tags.css" />" rel="stylesheet">

<title>JJRecipes tags</title>
</head>
<body>
<div class="container">
	<jsp:include page="topPanel.jsp" flush="true"/>
			
	<div class="bigBox">
	
	<div id="centerdude">
	
		<form action="nyTagg">
		Skapa ny tag:<br>
		<input name="newTag" style="width:250px"> <input type="submit" value="Skapa tag"><span>${error}</span>
	</form>
			<form action="searchTag" method="get" name="tagForm">
				Välj de taggar du vill söka med<br>
				
				<div class="list-group" id="tagList">
					<c:forEach var="tag" items="${tags}"> 
					  <button type="button" class="list-group-item" tagid="${tag.id}">
					  	${tag.name} 
					  	<span class="pull-right">
					  		 <span class="glyphicon glyphicon-remove" id="deleteBtn_${tag.id}">
					  	</span></span> </button>
	 			   </c:forEach>
				</div>
				<!-- 
				<div>
					<ul id="tagList">
					 <c:forEach var="item" items="${tags}"> 
					 	
	  			      <li><input type="checkbox" name="tagItem" value="${item.id}">${item.name}</li>
	 			   </c:forEach>
					</ul>
			  </div> -->
			  <button type="button" onclick="searchByTags();">Sök på markerade</button>
			  <input type="hidden" value="" name="selectedValues">
			</form>	
	</div>
	
	<script type="text/javascript">
		$('.list-group-item').click(function () {
			$(this).toggleClass('active');
		});
		
		$('.glyphicon-remove').click(function (){
			var id = $(this).prop('id').split('_')[1];
			removeTag(id);
		});
	</script>

	<form action="removeTag" id="removeTag" method="post">
		<input type="hidden" name="tagId" id="tagId" >
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	</form>
	<jsp:include page="bottomPanel.jsp" flush="true"/>
</div></div>
	
</body>
</html>