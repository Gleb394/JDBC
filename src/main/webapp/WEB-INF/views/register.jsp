<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: akhambir
  Date: 10/8/18
  Time: 8:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Mate</title>

    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
          crossorigin="anonymous">

    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
            integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
            crossorigin="anonymous"></script>

</head>
<body>

<%@include file="header.jsp" %>

<div>
    <form class="form-signin" action="<c:url value="/servlet/register"/>" method="post">

        <h1 class="h3 mb-3 font-weight-normal">Please sign up</h1>

        <label for="inputEmail" class="sr-only">Email address</label>
        <input name="email" type="email" id="inputEmail" class="form-control" placeholder="Email address" required autofocus>

        <label for="inputPassword" class="sr-only">Password</label>
        <input name="password" type="password" id="inputPassword" class="form-control" placeholder="Password" required>

        <button class="btn btn-lg btn-primary btn-block" type="submit">Register</button>

        <p class="mt-5 mb-3 text-muted">&copy; 2017-2018</p>
    </form>
</div>

</body>
</html>

