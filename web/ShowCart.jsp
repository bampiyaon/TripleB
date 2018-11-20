<%-- 
    Document   : ShowCart
    Created on : Aug 9, 2018, 4:16:07 PM
    Author     : piyo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap.min.css">
        <script src="https://code.jquery.com/jquery-3.3.1.js"></script>
        <script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap.min.js"></script>
    </head>
    <body>
        <jsp:include page="include/Header.jsp?title=Your Cart::"/>
        <table id="example" class="table">
            <thead>
            <th>Image</th>
            <th>#</th>
            <th>Product ID</th>
            <th>Product Name</th>
        </thead>

        <c:set var="items" value="${sessionScope.cart.lineItems}"/>
        <c:set var="bgColorX" value="lightgray" />
        <c:set var="bgColorY" value="white" />

        <c:forEach items="${cart.lineItems}" var="line" varStatus="vs">
            <tr style="background-color: ${vs.count%2==1 ? bgColorX:colrY}">
                <td><img src="logo_big.png" width="120"></td>
                <td>${vs.count}</td>
                <td>${line.product.productid}</td>
                <td>${line.product.productname}</td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="4"></td>

            <td>${cart.totalQuantity}</td>
            <td></td>
            <td>${cart.totalPrice}</td>
        </tr> 
    </table>
    <form action="Login" method="post">
        <td><button type="button" class="btn btn-primary">Check out</button></td>
    </form>
</body>
</html>
