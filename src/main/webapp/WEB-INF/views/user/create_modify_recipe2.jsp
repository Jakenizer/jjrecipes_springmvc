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
<script src="http://js.nicedit.com/nicEdit-latest.js" type="text/javascript"></script>



<title>JJRecipes create modify recipe</title>
<script type="text/javascript">
$(document).ready(function(){
   setActiveNav("#nav_createrecipe");
	

});

bkLib.onDomLoaded(function() {
    nicEditors.editors.push(
            new nicEditor({buttonList: ['fontSize','bold', 'ol', 'ul']}).panelInstance(
                document.getElementById('content')
            )
        );
    
    nicEditors.editors.push(
            new nicEditor({buttonList: ['fontSize','bold', 'ol', 'ul']}).panelInstance(
                document.getElementById('ingredients')
            )
        );
});

</script>
</head>
<body>
	<jsp:include page="topPanel.jsp" flush="true"/>
 
<div class="container">


	<form id="createRecipe" action="/JJRecipes/user/createModifyRecipe" method="post" enctype="multipart/form-data">
		<div class="form-group">
			<label for="name">Namn</label> 
			<div>
				<input type="text" class="form-control" name="name" id="name" placeholder="Namn">
			</div>
		</div>

		<div class="form-group">
			<label for="content">Beskrivning</label>
			<textarea name="content" class="form-control" rows="5" id="content"></textarea>
		</div>
		
		<div class="form-group">
			<label for="ingredients">Ingredienser</label>
			<textarea rows="5" class="form-control" id="ingredients"></textarea>
		</div>
		
<label class="btn btn-primary" for="my-file-selector">
    <input id="my-file-selector" type="file" style="display:none" 
    onchange="$('#upload-file-info').html(this.files[0].name)">
    Lägg till en bild
</label>
<span class='label label-info' id="upload-file-info"></span>
<br>
		
		<button type="submit">Skapa</button>
		
				 <input type="hidden"
			name="${_csrf.parameterName}"
			value="${_csrf.token}"/>
</form>

</div>