<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/create_modify_recipe.css" />" rel="stylesheet">

<script src="<c:url value="/resources/js/main.js" />"></script>

<script src="<c:url value="/resources/js/jquery-1.11.2.js" />"></script>
<script src="<c:url value="/resources/js/jquery-ui-1.11.4/jquery-ui.min.js" />"></script>


<title>JJRecipes</title>
<script type="text/javascript">
$(document).ready(function(){
    $('#addIngredient').click(function() {		
       var val = $('#newIngredient').val();
    	 var numItems =  $("#ingredientList li").length;
       $('#ingredientList').append('<li><input type="checkbox" name="ingredient" value="' + val + '">' + val + '</li>');
    });
});
</script>
</head>
<body>
	<jsp:include page="topPanel.jsp" flush="true"/>
	
	<c:if test="${empty recipe.id}">
	</c:if>


	<div id="bigBox">
		<form action="upload" method="post" enctype="multipart/form-data">
			Namn:<br><input name="name" required="required" value="${recipe.name}"/><br><br>
			Beskrivning:<br><textarea rows="15" cols="50" name="content"></textarea><br><br>
			<div class="ingredientHeader">Ny ingrediens:</div><div class="ingredientHeader">Mängd:</div><div class="ingredientHeader">Mått:</div><br>
			<input id="newIngredient"/>  <input type="number" step="1" min="0" id="amount"> 
			<select name="selectMeasureType">
 			   <c:forEach var="item" items="${measuretypes}">
  			      <option value="${item}">${item}</option>
 			   </c:forEach>
			</select>
			<button type='button' id="addIngredient">Lägg till</button><br>
			<div>
				<ul id="ingredientList">
				</ul>
		  </div>
		  <input type="file" name="fileUpload" size="50" /><br><br>
		  <input type="submit" value="Spara">
		</form>
	</div>

	<jsp:include page="bottomPanel.jsp" flush="true"/>
</body>
</html>