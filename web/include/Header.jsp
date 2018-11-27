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
                    Hello <a href="Logout">${sessionScope.customer.firstname},</a>
                </c:when>
                <c:otherwise>
                    <a href="Login">Hello Guest</a>
                </c:otherwise>
            </c:choose>
            <form action = "Search" class="form-inline my-2 my-lg-0">
                <select name = "searchOption" required class="form-control">
                    <option value = "all">All</option>
                    <option value = "uncleNumSquare">Uncle Num Square</option>
                    <option value = "jaideeShop">Jaidee Shop</option>
                    <option value = "manaShop">Mana Shop</option>
                    <option value = "islamicFoodShop">Islamic Food Shop</option>
                    <option value = "KFCShopNo1">KFC Shop no.1</option>
                    <option value = "KFCShopNo2">KFC Shop no.2</option>
                    <option value = "KFCShopNo3">KFC Shop no.3</option>
                    <option value = "KFCShopNo4">KFC Shop no.4</option>
                    <option value = "KFCShopNo5">KFC Shop no.5</option>
                    <option value = "KFCShopNo6">KFC Shop no.6</option>
                </select>
                <input class="form-control mr-sm-2" type = "text" name = "search" placeholder="Search">
                <input class="btn btn-secondary my-2 my-sm-0" type = "submit" value = "Search">
            </form>
        </ul>
    </div>
</nav>

