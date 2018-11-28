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

    <style>
        .container2 {
            margin-top: 100px;
            margin-left: 40px;
        }
    </style>

</head>
<body>

<%@include file="header.jsp" %>

<div class="container2">

    <div class="mt-5">
        <c:forEach var="c" items="${categories}">
            <div class="row">
                <div class="col-md-2">
                    <h3><c:out value="${c.name}"/></h3>
                </div>
                <div class="col-md-4">
                    <a href="<c:url value="/servlet/admin/edit-category?c_id=${c.id}"/>" class="btn btn-outline-warning my-2 my-sm-0 ml-2">Edit</a>
                    <a href="<c:url value="/servlet/admin/delete-category?c_id=${c.id}"/>" class="btn btn-outline-danger my-2 my-sm-0 ml-2">Delete</a>
                </div>
            </div>
        </c:forEach>

    </div>

    <a href="<c:url value="/servlet/admin/add-category"/>" class="btn btn-outline-success my-2 my-sm-0">Add Category</a>

</div>

</body>
</html>
