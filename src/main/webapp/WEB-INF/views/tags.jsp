<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/tags.css" />" rel="stylesheet">
<script src="<c:url value="/resources/js/main.js" />"></script>

<script src="<c:url value="/resources/js/jquery-1.11.2.js" />"></script>
<script src="<c:url value="/resources/js/jquery-ui-1.11.4/jquery-ui.min.js" />"></script>
<title>JJRecipes tags</title>
</head>
<body>
	<jsp:include page="topPanel.jsp" flush="true"/>

	<div id="bigBox">	
		<div class="contentBox">
			<form action="removeTag" method="get">
				Välj de taggar som skall tas bort<br>
				<div>
					<ul id="tagList">
					 <c:forEach var="item" items="${tags}"> 
	  			      <li><input type="checkbox" name="tagItem" value="${item.id}">${item.name}</li>
	 			   </c:forEach>
					</ul>
			  </div>
			  <input type="submit" value="Ta bort markerade">
			</form>
		</div>	
		
		
		<div class="contentBox">
			<form action="nyTagg">
				Skapa ny tag:<br>
				<input name="newTag" style="width:290px"> <input type="submit" value="Skapa tag">
			</form>
		</div>
		
	</div>

	<jsp:include page="bottomPanel.jsp" flush="true"/>
</body>
</html>