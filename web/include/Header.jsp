<%-- 
    Document   : Header
    Created on : Nov 13, 2018, 10:07:46 AM
    Author     : piyao
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <a class="navbar-brand" href="Home.jsp" title="Back to Home"><img src="logo_nobg.png" width="50"></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarColor01" aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarColor01">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="ProductList.jsp"  >Product</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="Register.jsp">Register</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="ShowCart.jsp">Order</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="Profile.jsp">Profile</a>
            </li>
        </ul>
        <form class="form-inline my-2 my-lg-0">
            <input class="form-control mr-sm-2" type="text" placeholder="Search">
            <button class="btn btn-secondary my-2 my-sm-0" type="submit">Search</button>
        </form>
    </div>
</nav>

<!--<div class="container-fluid" style="background-color: ${cookie.bgColor.value}">
    <table class="table">
        <tr>
            <td><a href="ProductList.jsp" title="Back To Home"><img src="logo_nobg.png" width="150"></a></td>
            <td><h1>${param.title}</h1></td>
            <td>
<c:if test="${cart != null}">
    <a href="ShowCartView.jsp">Your Cart: (${cart.totalQuantity})</a>
</c:if>
&nbsp;&nbsp;&nbsp;
<c:choose>
    <c:when test="${sessionScope.user != null}">
        Hello ${sessionScope.user.name}
    </c:when>
    <c:otherwise>
        Hello Guest
    </c:otherwise>
</c:choose>
</td>
</tr>
</table>
</div>-->
<!--<hr>
Session Id : ${cookie.JSESSIONID.value}
<hr>-->
