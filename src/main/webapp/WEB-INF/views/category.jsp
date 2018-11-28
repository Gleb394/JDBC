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
    <h1>Category <c:out value="${category.name}"/></h1>
</div>

<div>
    <c:forEach var="c" items="${category.product}">
        <h3>Category name:
            <a href="<c:url value="/servlet/product?p_id=${c.id}"/>">
                <c:out value="${c.name}"/>
            </a>
        </h3>
    </c:forEach>
</div>


</body>
</html>
