<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Sign in</title>

    <!-- Bootstrap -->
    <link href="<c:url value="inc/css/bootstrap.min.css"/>" rel="stylesheet"/>
    <link href="<c:url value="inc/css/bootstrap-theme.min.css"/>" rel="stylesheet"/>
    <script type="text/javascript" src="<c:url value="inc/js/jquery-1.11.3.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="inc/js/bootstrap.min.js"/>"></script>
    <link href="<c:url value="inc/css/style.css"/>" rel="stylesheet"/>
  </head>
<body>
  <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="<c:url value="/Home"/>">JWeb</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
            <li><a href="<c:url value="/Home" />">Home</a></li>
            <li><a href="<c:url value="/About" />">About the product</a></li>
        <c:choose>
        <c:when test="${!empty sessionScope.Utilisateur}">
           <li><a href="<c:url value="/Deconnexion" />">Deconnexion of <c:out value="${sessionScope.Utilisateur.email}"/></a></li>
        </c:when>
        <c:otherwise>
            <li class="active"><a href="<c:url value="/Connexion" />">Log in</a></li>
        </c:otherwise>
        </c:choose>
        <c:if test="${sessionScope.Utilisateur.isAdm()}">
           <li><a href="<c:url value="/Restreint" />">Post a news</a></li>
        </c:if>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
    <div class="container">

      <form class="form-signin" method="post">
        <h2 class="form-signin-heading">Please sign in</h2>
        <label for="inputEmail" class="sr-only">Email address</label>
        <input type="email" id="email" name="email" class="form-control" placeholder="Email address" value="<c:out value="${user.email}"/>" required="" autofocus="">
        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" id="password" name="password" class="form-control" placeholder="Password" required="">
        <div class="checkbox">
        </div>
        <p class="form-group">
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
        </p>
        <p class="form-group">
        <a href="<c:url value="/Form" />"><button class="btn btn-lg btn-success btn-block" type="button">Sign up</button></a>
        </p>
        <c:if test="${!empty error}">
           <p class="erreur">${error}</p>
        </c:if>        
        <c:if test="${!empty sessionScope.Utilisateur}">
           <p class="succes">Vous êtes connecté(e) avec l'adresse : ${sessionScope.Utilisateur.email}</p>
        </c:if>
      </form>

    </div> <!-- /container -->
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
	</body>
</html>