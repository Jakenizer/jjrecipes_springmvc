<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="<c:url value="/resources/js/main.js" />"></script>

<!-- 
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
			<li class="topMenuItem"><a href="javascript:void(0);" onclick="logout(); return false;"> Logga ut</a></li>
		</ul>
	</nav>
</div>
 -->



<nav class="navbar navbar-default">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand">JJRecipes</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
      	<li><a href="/JJRecipes/list_and_search">Recept</a></li>
      	
        <li class="active"><a href="/JJRecipes/tags">Taggar</a></li>
        <li><a href="javascript:logout();">Logga ut</a></li>
        <!-- <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Dropdown <span class="caret"></span></a>
        	 <ul class="dropdown-menu">
            <li><a href="#">Action</a></li>
            <li><a href="#">Another action</a></li>
            <li><a href="#">Something else here</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="#">Separated link</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="#">One more separated link</a></li>
          </ul>
        </li> -->
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>

<c:url var="logoutUrl" value="/logout" />
<form name="logoutForm" action="${logoutUrl}" method="post" hidden="hidden">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>