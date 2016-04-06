<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link href="<c:url value="/resources/bootstrap-3.3.6-dist/css/signin.css" />" rel="stylesheet">


<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</head>
<body>
<!-- 
   <h1>Login</h1>
   <form name='f' action="login" method='POST'>
      <table>
         <tr>
            <td>User:</td>
            <td><input type='text' name='username' value=''></td>
         </tr>
         <tr>
            <td>Password:</td>
            <td><input type='password' name='password' /></td>
         </tr>
         <tr>
            <td><input name="submit" type="submit" value="submit" /></td>
         </tr>
      </table>
      <div style="color:red;">${error}</div>
      <input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
  </form>
</body>
</html>
 -->

<div class="container">

      <form name='f' action="login" method='POST' class="form-signin">
        <h2 class="form-signin-heading">Logga in</h2>
        <div class="form-group">
          <label for="username">Användarnamn</label>
       	 <input id="username" name="username" type="text" class="form-control" placeholder="Användarnamn" required autofocus>
        </div>
        <div class="form-group">
          <label for="password">Lösenord</label>
       	 <input id="password" name="password" type="password" class="form-control" placeholder="lösenord" required>
        </div>
   	   <div style="color:red;">${error}</div>
        <button class="btn btn-lg btn-primary btn-block" name="submit" type="submit">Logga in</button>
      	<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
      </form>
</div> <!-- /container -->