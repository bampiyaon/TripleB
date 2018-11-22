<%-- 
    Document   : Header
    Created on : Nov 13, 2018, 10:07:46 AM
    Author     : piyao
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<link href="//netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>

<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <a class="navbar-brand" href="ProductList" title="Our Product"><img src="logo_big.png" width="50"></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarColor01" aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <h2>${param.title}</h2>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <div class="collapse navbar-collapse" id="navbarColor01">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="ProductList">Product</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="OrderHistory">Check My Order</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="Register">Signup</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="Login">Login</a>
            </li>
            &nbsp;&nbsp;&nbsp;
            <c:if test="${cart != null}">
                <a class="navbar-brand" href="ShowCart" title="Cart"><img src="basket.png" width="25">${cart.totalQuantity}</a>
                </c:if>
            &nbsp;&nbsp;&nbsp;
            <c:choose>
                <c:when test="${sessionScope.customer != null}">
                    Hello ${sessionScope.customer.firstname}
                </c:when>
                <c:otherwise>
                    <button type="button" class="btn btn-primary">Hello Guest</button>
                </c:otherwise>
            </c:choose>
        </ul>

<!--        <div class="container">
            <div class="row">    
                <div class="col-xs-8 col-xs-offset-2">
                    <div class="input-group">
                        <div class="btn-group" role="group" aria-label="Button group with nested dropdown">
                            <button type="button" class="btn btn-primary">Shop</button>
                            <div class="btn-group" role="group">
                                <button id="btnGroupDrop1" type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"></button>
                                <div class="dropdown-menu" aria-labelledby="btnGroupDrop1">
                                    <a class="dropdown-item" href="#">Dropdown link</a>
                                    <a class="dropdown-item" href="#">Dropdown link</a>
                                </div>
                            </div>
                        </div>
                        <input type="hidden" name="search_param" value="all" id="search_param">         
                        <input type="text" class="form-control" name="x" placeholder="Search term...">
                        <span class="input-group-btn">
                            <button class="btn btn-default" type="button"><span class="glyphicon glyphicon-search"></span></button>
                        </span>
                    </div>
                </div>
            </div>
        </div>-->


        <!--                <form class="form-inline my-2 my-lg-0">
                            <input class="form-control mr-sm-2" type="text" placeholder="Search">
                            <button class="btn btn-secondary my-2 my-sm-0" type="submit">Search</button>
                        </form>-->

    </div>
</nav>

