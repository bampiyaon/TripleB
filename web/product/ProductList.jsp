<%-- 
    Document   : ProductList
    Created on : Nov 13, 2018, 3:27:54 PM
    Author     : piyao
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Our Products</title>
        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom fonts for this template -->
        <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">
        <link href='https://fonts.googleapis.com/css?family=Kaushan+Script' rel='stylesheet' type='text/css'>
        <link href='https://fonts.googleapis.com/css?family=Droid+Serif:400,700,400italic,700italic' rel='stylesheet' type='text/css'>
        <link href='https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700' rel='stylesheet' type='text/css'>

        <!-- Custom styles for this template -->
        <link href="css/agency.min.css" rel="stylesheet">

        <!-- Custom styles for this template -->
        <link href="css/agency.min.css" rel="stylesheet">
    </head>
    <body>

        <c:choose>
            <c:when test="${sessionScope.customer != null}">
                Hello ${sessionScope.customer.firstname}
                <a href="OrderHistory">Recent Orders</a>
                <a href="Logout">Logout</a>
            </c:when>
            <c:otherwise>
                <nav class="navbar navbar-expand-lg navbar-dark fixed-top" id="mainNav" style="background-color: #696969">
                    <div class="container">
                        <a class="navbar-brand js-scroll-trigger" href="index.html">TripleB</a>

                        <div class="collapse navbar-collapse" id="navbarResponsive" style="background-color: #696969">
                            <ul class="navbar-nav text-uppercase ml-auto">
                                <li class="nav-item">
                                    <a class="nav-link js-scroll-trigger" href="index.html">home</a>
                                <li class="nav-item">
                                    <a class="nav-link js-scroll-trigger" href="Register">Sign up</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link js-scroll-trigger" href="Login?returnUrl=${requestScope['javax.servlet.forward.request_uri']}?${requestScope['javax.servlet.forward.query_string']}">Login</a>
                                </li>
                            </ul>

                            <p>Hello Guest</p>
                        </c:otherwise>
                    </c:choose>
                            &nbsp;&nbsp;&nbsp;
                    <div>
                        <a href="ShowCart" title="Cart">
                            <img src="images/payment/basket.png" width="25">${cart.totalQuantity != null ? cart.totalQuantity : 0} 
                        </a>
                    </div> 

                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <form action = "Search" class="form-inline my-2 my-lg-0">
                        <select name = "searchOption" required class="form-control">
                            <option value = "all">All</option>
                            <option value = "101">Uncle Num Square</option>
                            <option value = "102">Jaidee Shop</option>
                            <option value = "103">Mana Shop</option>
                            <option value = "104">Islamic Food Shop</option>
                            <option value = "105">KFC Shop no.1</option>
                            <option value = "106">KFC Shop no.2</option>
                            <option value = "107">KFC Shop no.3</option>
                            <option value = "108">KFC Shop no.4</option>
                            <option value = "109">KFC Shop no.5</option>
                            <option value = "110">KFC Shop no.6</option>
                        </select>
                        <input class="form-control mr-sm-2" type = "text" name = "keyword" placeholder="Search">
                        <input class="btn btn-secondary my-2 my-sm-0" type = "submit" value = "Search">
                    </form>
                </div>
            </div>
        </nav>




        <table class="table table-hover" style="margin-top:100px ">
            <thead>
            <th scope="col">Image</th>
            <th scope="col">No.</th>
            <th scope="col">Shop</th>
            <th scope="col">Product Name</th>
            <th scope="col">Price(baht)</th>
            <th scope="col">Calories</th>
            <th scope="col">Add</th>
        </thead>

        <c:forEach items="${products}" var="p" varStatus="vs">
            <tr class="table-info">
                <td><img src="images/food/${p.productid}.jpg" width="120"></td>
                <td>${vs.count}</td>
                <td>${p.shopid.shopname}</td>
                <td>${p.productname}</td>
                <td>${p.price}</td>                
                <td>${p.description}</td>
                <td>
                    <form action="AddItemToCart" method = "post">
                        <input type = "hidden" name = "productid" value="${p.productid}">
                        <input type = "image" src="images/payment/basket.png" width="25" title = "Add">
                    </form>
                </td>

            </tr>
        </c:forEach>       
    </table>
</body>
</html>
