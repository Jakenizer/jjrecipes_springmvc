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

<link href="<c:url value="/resources/css/create_modify_recipe.css" />" rel="stylesheet">


<title>JJRecipes create modify recipe</title>
<script type="text/javascript">
$(document).ready(function(){
    $('#addIngredient').click(function() {		
       var name = $('#newIngredient').val();
       var amount = $('#amount').val();
       var type = $('#selectMeasureType').val();
       var text = name; 
       var val = name;
       if (amount) {
    	   text += " " + amount;
    	   val += ";;" + amount;
    	   if (type)
    		   text += " " + type;
    	   	val += ";;" + type;
       }
    	 //var numItems =  $("#ingredientList li").length;
    	 
       $('#ingredientList').append('<li><input type="checkbox" name="ingredients" value="' + val + '" checked>' + text + '</li>');
    });
    
    $('#selectTagBtn').click(function() {
    	var text = $('#selectTag option:selected').html();
    	var val = $('#selectTag').val();
    	$('#selectedTags').append('<li><input type="checkbox" name="tags" value="' + val + '" checked>' + text + '</li>');
    })
});
</script>
</head>
<body>
<div class="container">

	<jsp:include page="topPanel.jsp" flush="true"/>
	
	<c:if test="${empty recipe.id}">
	</c:if>


	<div id="bigBox">
	<!-- ?${_csrf.parameterName}=${_csrf.token} -->
		<form action="createRecipe" method="post" enctype="multipart/form-data">
			Namn:<br><input name="name" required="required" maxlength="100" value="${recipe.name}"/><br><br>
			Beskrivning:<br><textarea rows="25" cols="108" name="content" maxlength="1000"></textarea><br><br>
			<div class="ingredientGroup" style="width:180px">Ny ingrediens:<br><input id="newIngredient"/></div>
			<div class="ingredientGroup" style="width:180px">Mängd:<br><input type="number" name="amount" value="0" step="1" min="0" id="amount"></div>
			<div class="ingredientGroup">Mått:<br>
			<select id="selectMeasureType" name="selectMeasureType">
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
		  
		  <select id="selectTag">
		  		<c:forEach var="tag" items="${tags}"> 
  			      	<option value="${tag.id}">${tag.name}</option>
 			   </c:forEach>
		  </select>
		  <button type='button' id="selectTagBtn">Lägg till</button><br>
		 
		  <div>
		  		<ul id="selectedTags"></ul>
		  		
		  </div>
		  
		  <br>
		  <input type="file" name="file" size="50"><br><br>
		  <input type="submit" value="Spara">
		<!--<sec:csrfMetaTags /> -->
		 <input type="hidden"
			name="${_csrf.parameterName}"
			value="${_csrf.token}"/>
		</form>
	</div>
</div>
	<jsp:include page="bottomPanel.jsp" flush="true"/>
</body>
</html>