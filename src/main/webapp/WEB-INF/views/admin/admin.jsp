<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page session="true"%>
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
	$(".admintabcontent").hide();
	$("#listusersdiv").show();
	$(".admintabs").click(function(event) {
		$(".nav-tabs li").removeClass("active");
		$(this).addClass("active");
		$(".admintabcontent").hide();
		var contentDiv = $(this).data("content");
		$("#" + contentDiv).show();
	});
});

</script>

<title>JJRecipes Admin Page</title>
</head>
<body>
	<div class="container">
		<jsp:include page="../topPanel.jsp" flush="true"/>
		<div id="bigBox">
			<h1>Admin Page</h1>
			
			<ul class="nav nav-tabs">
			  <li class="admintabs active" data-content="listusersdiv"><a href="#">List Users</a></li>
			  <li class="admintabs" data-content="createuserdiv"><a href="#">Create User</a></li>
			</ul>
			
			<div id="listusersdiv" class="admintabcontent">
				<h2>Users</h2>	
				<c:if test="${not empty users}">
					<div class="list-group">
						<c:forEach var="user" items="${users}"> 
						  <button type="button" class="list-group-item" data-userid="${user.id}">
						  	${user.username} 
						  	<span class="pull-right">
						  		 <span class="glyphicon glyphicon glyphicon-pencil" id="updateBtn_${user.id}"></span> 
						  		 <span class="glyphicon glyphicon-trash" id="deleteBtn_${user.id}"></span>
						  	</span> 
						  	</button>
		 			   </c:forEach>
					</div>
				</c:if>
			</div>
			
			
			
			<div id="createuserdiv" class="admintabcontent">
			<form action="createUser" method="post">
			
			<h2>New User</h2>
				<div class="form-group">
		      	<label for="firstname">Firstname:</label>
		      	<input type="text" class="form-control" id="firstname" name="firstname">
		   	</div>
		   	<div class="form-group">
		      	<label for="lastname">Lastname:</label>
		      	<input type="text" class="form-control" id="lastname" name="lastname">
		   	</div>
		   	<div class="form-group">
		      	<label for="username">Username:</label>
		      	<input type="text" class="form-control" id="username" name="username">
		   	</div>
		      <div class="form-group">
		     	 <label for="pwd">Password:</label>
		     	 <input type="password" class="form-control" id="pwd" name="password" placeholder="at least 6 long">
		   	</div>
		   	<div class="checkbox">
		   	     <label><input type="checkbox" name="admin">Is admin</label>
	     			 <label><input type="checkbox" name="isenabled" checked>Enabled</label>
	    		</div>
		   	<input type="submit" class="btn btn-default" value="create"/>
		   	
		   	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		   </form> 
		   </div>
	    </div>
	</div>
</body>
</html>