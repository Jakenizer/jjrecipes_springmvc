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
<script type="text/javascript">
$(document).ready(function(){
    $('#addIngredient').click(function() {		
       var val = $('#newIngredient').val();
    	 var numItems =  $("#ingredientList li").length;
       $('#ingredientList').append('<li><input type="checkbox" name="ingredient" value="ingredient'+ ++numItems +'">' + val + '</li>');
    });
});
</script>
</head>
<body>
	<jsp:include page="topPanel.jsp" flush="true"/>

	<div id="bigBox">
		<form action="recipes.jsp" method="get">
			Namn:<br><input /><br><br>
			Beskrivning:<br><textarea rows="15" cols="50"></textarea><br><br>
			Lägg till ingredienser:<br><input id="newIngredient"/> <button type='button' id="addIngredient">Lägg till</button><br>
			<div>
				<ul id="ingredientList">
				</ul>
		  </div>
		  
		  <input type="submit" value="Spara">
		</form>
	</div>

	<jsp:include page="bottomPanel.jsp" flush="true"/>
</body>
</html>