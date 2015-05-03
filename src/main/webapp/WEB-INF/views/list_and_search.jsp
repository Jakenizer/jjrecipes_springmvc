<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

	<meta name="_csrf" content="${_csrf.token}"/>
	<!-- default header name is X-CSRF-TOKEN -->
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
	


<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/list_and_search.css" />" rel="stylesheet">

<script src="<c:url value="/resources/js/main.js" />"></script>

<script src="<c:url value="/resources/js/jquery-1.11.2.js" />"></script>
<script src="<c:url value="/resources/js/jquery-ui-1.11.4/jquery-ui.min.js" />"></script>
<script type="text/javascript">
         $(document).ready(function() {
            $('.listAnchor').click(function(event){
               var token = $("meta[name='_csrf']").attr("content");
					var header = $("meta[name='_csrf_header']").attr("content");
            	var id = event.target.id;
              /* $('#rightDiv').load('/JJRecipes/test', {redId:55}, function(response, status, xhr ) {
            		xhr.setRequestHeader(header, token);

               });*/
               

               var headers = {};
   				headers[header] = token;
					
                $.ajax({
                    type : 'POST',
                    url : '/JJRecipes/test',
                    data: {redId : id},
                    headers: headers,
                    crossDomain : true,
                }).done(function( data ) {
                		$('#rightDiv').html("");
                		$('#rightDiv').append("<h2>" + data.name + "</h2>");
                		$('#rightDiv').append("<img id='recipeImage' src='data:image/png;base64," + data.image + "'/>");
                 });;
            });
         });
      </script>
<title>JJRecipes list and search</title>
</head>
<body>
	<jsp:include page="topPanel.jsp" flush="true"/>
	<div id="bigBox">
		<form action="searchRecipe" method="get">
			 <input class="lefter" type="text" name="inputText">
			 <input class="lefter" type="submit" value="Sök">
			 <br/><br/>
			<div id="leftDiv" class="cont">
				<ul>
					<c:forEach var="item" items="${recipes}"> 
						<li class="listElement"><div class="listAnchor" id="${item.id}">${item.name}</div></li>
	 			   </c:forEach>
				</ul>
			</div>
			<div id="rightDiv" class="cont">
			</div>
		</form>
		
	</div>
	
	
	
	<jsp:include page="bottomPanel.jsp" flush="true"/>
</body>
</html>