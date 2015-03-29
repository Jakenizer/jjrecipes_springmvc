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


<title>JJRecipes create modify recipe</title>
<script type="text/javascript">
$(document).ready(function(){
    $('#addIngredient').click(function() {		
       var name = $('#newIngredient').val();
       var amount = $('#amount').val();
       var type = $('#selectMeasureType').val();
       var val = name;
       if (amount) {
    	   val += " " + amount;
    	   if (type)
    		   val += " " + type;;
       }
    	 //var numItems =  $("#ingredientList li").length;
    	 
       $('#ingredientList').append('<li><input type="checkbox" name="ingredient" value="' + val + '" checked>' + val + '</li>');
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
			Namn:<br><input name="name" required="required" maxlength="100" value="${recipe.name}"/><br><br>
			Beskrivning:<br><textarea rows="25" cols="108" name="content" maxlength="1000"></textarea><br><br>
			<div class="ingredientGroup" style="width:180px">Ny ingrediens:<br><input id="newIngredient"/></div>
			<div class="ingredientGroup" style="width:180px">Mängd:<br><input type="number" value="0" step="1" min="0" id="amount"></div>
			<div class="ingredientGroup">Mått:<br>
			<select id="selectMeasureType">
 			   <c:forEach var="item" items="${measuretypes}"> 
  			      <option value="${item}">${item}</option>
 			   </c:forEach>
			</select>
			</div>
			<br>
			
			<button type='button' id="addIngredient">Lägg till</button><br>
			<div>
				<ul id="ingredientList"></ul>
		  </div>
		  <input type="file" name="fileUpload" size="50"><br><br>
		  <input type="submit" value="Spara">
		</form>
	</div>

	<jsp:include page="bottomPanel.jsp" flush="true"/>
</body>
</html>