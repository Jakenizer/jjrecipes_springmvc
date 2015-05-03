<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="<c:url value="/resources/js/main.js" />"></script>

<div id="topContainer">
	<nav id="listCont">
		<ul>
			<li class="topMenuItem"><a href="#">Min sida</a></li>
			<li class="topMenuItem"><a href="#">Recept</a>
				<ul>
					<li><a href="list_and_search">Lista och sök recept</a></li>
					<li><a href="create_modify_recipe">Skapa nytt recept</a></li>
					<li class="topMenuItem"><a href="#">Favoriter</a></li>
				</ul>
			</li>
			<li class="topMenuItem"><a href="tags"> Hantera taggar</a></li>
			<li class="topMenuItem"><a href="" onclick="logout(); return false;"> Logga ut</a></li>
		</ul>
	</nav>
</div>

<c:url var="logoutUrl" value="/logout" />
<form name="logoutForm" action="${logoutUrl}" method="post" hidden="hidden">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>