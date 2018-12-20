<%-- 
    Document   : OrderDetails
    Created on : Dec 18, 2018, 12:39:19 PM
    Author     : piyao
--%>

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

        <title>Order Detail</title>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-dark fixed-top" id="mainNav" style="background-color: #696969">
            <div class="container">
                <a class="navbar-brand js-scroll-trigger" href="index.html">TripleB</a>
                <div class="collapse navbar-collapse" id="navbarResponsive" style="background-color: #696969">
                    <ul class="navbar-nav text-uppercase ml-auto">
                        <li class="nav-item">
                            <a class="nav-link js-scroll-trigger" href="ProductList">Product</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link js-scroll-trigger" href="Register">Sign up</a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link js-scroll-trigger" href="#team">Team</a>
                        </li>

                    </ul>                       
                </div>
            </div>
        </nav>
        <div class="container" style="margin-top:200px">
            <h2>Order #${order.orderid}</h2>
            <table>
                <tr>
                    <th>Product Name</th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Subtotal</th>
                </tr>
                <c:forEach items = "${order.lineitemList}" var = "line">
                    <tr>
                        <td>${line.product.productname}</td>
                        <td>${line.unitprice}</td>
                        <td>${line.quantity}</td>
                        <td>${line.totalLinePrice}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </body>
</html>
