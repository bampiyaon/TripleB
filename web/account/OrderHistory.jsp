<%-- 
    Document   : OrderHistory
    Created on : Dec 18, 2018, 12:39:31 PM
    Author     : piyao
--%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://bootswatch.com/4/journal/bootstrap.min.css">
        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom fonts for this template -->
        <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">
        <link href='https://fonts.googleapis.com/css?family=Kaushan+Script' rel='stylesheet' type='text/css'>
        <link href='https://fonts.googleapis.com/css?family=Droid+Serif:400,700,400italic,700italic' rel='stylesheet' type='text/css'>
        <link href='https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700' rel='stylesheet' type='text/css'>

        <!-- Custom styles for this template -->
        <link href="css/agency.min.css" rel="stylesheet">

        <title>Recent Orders</title>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-dark fixed-top" id="mainNav" style="background-color: #696969">
            <div class="container">
                <a class="navbar-brand js-scroll-trigger" href="index.html">TripleB</a>
                <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                    Menu
                    <i class="fas fa-bars"></i>
                </button>
                <div class="collapse navbar-collapse" id="navbarResponsive" style="background-color: #696969">
                    <ul class="navbar-nav text-uppercase ml-auto">
                        <li class="nav-item">
                            <a class="nav-link js-scroll-trigger" href="index.html">home</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link js-scroll-trigger" href="Register">Sign up</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link js-scroll-trigger" href="ProductList">Product</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>

                        <div class="container" style="margin-top:200px">
                            <h1>Recent Orders</h1>

                            <c:choose>
                                <c:when test = "${!empty orders}">
                                    <table>
                                        <tr>
                                            <th>Order no.</th>
                                            <th>Order date</th>
                                            <th>Status</th>
                                            <th>Total</th>
                                        </tr>
                                        <c:forEach items = "${orders}" var = "order">
                                            <tr>
                                                <td>${order.orderid}</td>
                                                <td>${fn:substring(order.ordereddate, 0, 9)}</td>
                                                <td>${order.ordersatus}</td>
                                                <td>${order.payment.totalprice}</td>
                                                <th><a href = "OrderDetails?orderid=${order.orderid}"> View Detail</a></th>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </c:when>
                                <c:otherwise>
                                    <h2>You haven't ordered anything yet.</h2>
                                    <a href = "ProductList">Continue Shopping</a>
                                </c:otherwise>
                            </c:choose>
                        </div>
    </body>
</html>
