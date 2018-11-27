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
        <link rel="stylesheet" href="https://bootswatch.com/4/journal/bootstrap.min.css">

    </head>
    <body>        
        <jsp:include page="include/Header.jsp?title=Your Cart::"/>
        <table id="example" class="table">
            <thead>
            <th>Image</th>
            <th>#</th>
            <th>Product ID</th>
            <th>Product Name</th>
            <th>Quantity</th>
            <th>Price</th>
            <th>Total Price</th>
        </thead>

        <c:set var="items" value="${sessionScope.cart.lineItems}"/>
        <c:set var="bgColorX" value="lightgray" />
        <c:set var="bgColorY" value="white" />

        <c:forEach items="${cart.lineItems}" var="line" varStatus="vs">
            <tr style="background-color: ${vs.count%2==1 ? bgColorX:colrY}">
                <td><img src="picture/${line.product.productid}.jpg" width="120"></td>
                <td>${vs.count}</td>
                <td>${line.product.productid}</td>
                <td>${line.product.productname}</td>
                <td>${line.quantity}</td>
                <td>${line.product.price}</td>
                <td>${line.totalPrice}</td>
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
        <input type="submit" class="btn btn-primary" value="Check out">
    </form>
</body>
</html>
