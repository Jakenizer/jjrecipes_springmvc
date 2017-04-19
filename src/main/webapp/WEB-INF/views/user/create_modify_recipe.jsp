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
   setActiveNav("#nav_createrecipe");
	
    $('#addIngredient').click(function() {		
       var content = $('#newIngredient').val();
       var amount = $('#amount').val();
       var type = $('#selectMeasureType option:selected').text();
       var typeId = $('#selectMeasureType').val();
       var json = {};
       json['content'] = content;
       var stringed = JSON.stringify(json);
    	 
       $('#ingredientList').append("<li><input type='checkbox' name='ingredients' value='" + stringed + "' checked>" + content + "</li>");
    });
    
    $('#selectTagBtn').click(function() {
    	var text = $('#selectTag option:selected').html();
    	var val = $('#selectTag').val();
    	$('#selectedTags').append('<li><input type="checkbox" name="tags" value="' + val + '" checked>' + text + '</li>');
    })
});
/*
function fixAndSave() {
	var json = [];	
	$.each($('[name="ingredients"]'), function() {
		json.push($(this).val());
	});
	$('[name="ingredientjson"]').val(JSON.stringify(json));
	$('#createRecipe').submit();
}*/
</script>
</head>
<body>
<div class="container">

	<jsp:include page="topPanel.jsp" flush="true"/>


	<div id="bigBox">
		<form id="createRecipe" action="/JJRecipes/user/createModifyRecipe" method="post" enctype="multipart/form-data">
			Namn:<br><input name="name" required="required" maxlength="50" value="${recipeData.name}"/><span class="fieldError">${name_error }</span><br><br>
			Beskrivning:<br><textarea rows="25" cols="108" name="content" maxlength="1001">${recipeData.content}</textarea><br><span class="fieldError">${content_error }</span><br><br>
			<div class="ingredientGroup" style="width:180px">Ny ingrediens:<br><input id="newIngredient"/></div>
			<br>
			<button type='button' id="addIngredient">Lägg till</button><br>
			<div>
				<ul id="ingredientList">
					<c:forEach items="${recipeData.ingredients}" var="ingredient">
						<li><input type="checkbox" name="ingredients" value="${ingredient.id}" checked>${ingredient.content}</li>
					</c:forEach>
				</ul>
		  </div>
		  <span class="fieldError">${ingredient_error }</span>
		  
		  <select id="selectTag">
		  		<c:forEach var="tag" items="${tags}"> 
  			      	<option value="${tag.id}">${tag.name}</option>
 			   </c:forEach>
		  </select>
		  <button type='button' id="selectTagBtn">Lägg till</button><br>
		 
		  <div>
		  		<ul id="selectedTags">
		  			<c:forEach items="${recipeData.tags}" var="tag">
						<li><input type="checkbox" name="tags" value="${tag.id}" checked>${tag.name}</li>
					</c:forEach>
		  		</ul>
		  		
		  </div>
		  
		  <br>
		  <input type="file" name="file" size="50"><br><br>
<!-- 		  <button type="button" onclick="fixAndSave();">Spara</button> -->
 		  <input type="submit" value="Spara">
		  
		  <input type="hidden" name="ingredientjson" />
		  <c:if test="${not empty recipeData}">
			  	<input type="hidden" name="id" value="${recipeData.id}">
		  </c:if>
	  	  
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