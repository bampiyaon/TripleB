<%-- 
    Document   : CompleteMyOrder
    Created on : Nov 28, 2018, 4:33:46 PM
    Author     : piyao
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My Order</title>
        <link rel="stylesheet" href="https://bootswatch.com/4/journal/bootstrap.min.css">
        <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">
        <link href='https://fonts.googleapis.com/css?family=Kaushan+Script' rel='stylesheet' type='text/css'>
        <link href='https://fonts.googleapis.com/css?family=Droid+Serif:400,700,400italic,700italic' rel='stylesheet' type='text/css'>
        <link href='https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700' rel='stylesheet' type='text/css'>

        <!-- Custom styles for this template -->
        <link href="css/agency.min.css" rel="stylesheet">
    </head>


    <nav class="navbar navbar-expand-lg navbar-dark fixed-top" id="mainNav" style="background-color: #696969">
        <div class="container">
            <a class="navbar-brand js-scroll-trigger" href="index.html">TripleB</a>
            <div class="collapse navbar-collapse" id="navbarResponsive" style="background-color: #696969">
                <ul class="navbar-nav text-uppercase ml-auto">
                    <li class="nav-item">
                        <a class="nav-link js-scroll-trigger" href="Register">Sign up</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link js-scroll-trigger" href="ProductList">Product</a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link js-scroll-trigger" href="#team">Team</a>
                    </li>

                </ul>                       
            </div>
        </div>
    </nav> 
    <body>
        <div class="container">
            <h4>Processing</h4>
            <fieldset>
                <div class="progress" style="margin-top:100px">
                    <div class="progress-bar progress-bar-striped progress-bar-animated" role="progressbar" aria-valuenow="75" aria-valuemin="0" aria-valuemax="100" style="width: 100%"></div>
                </div>
            </fieldset>

            <h2>Order confirmation</h2>
            <table>
                <tr>
                    <th>Order Number</th>
                    <td>${order.orderid}</td>
                </tr>
                <tr>
                    <th>Order Date</th>
                    <td>${order.ordereddate}</td>
                </tr>
                <tr>
                    <th>Order Total</th>
                    <td>${order.payment.totalprice}</td>
                </tr>
                <tr>
                    <th>Payment method</th>
                    <td>${order.payment.methods}</td>
                </tr>
                <tr>
                    <th>Ship To</th>
                    <td>${order.username.firstname} ${order.username.lastname}<br>
                        ${order.shipping.addressid.location}
                    </td>
                </tr>
            </table>
            <table>
                <tr>
                    <th>Item</th>
                    <th>Quantity</th>
                    <th>Price</th>
                    <th>Total</th>
                </tr>
                <c:forEach items = "${order.lineitemList}" var = "item">
                    <tr>
                        <td>${item.product.productname}</td>
                        <td style = "text-align: right">${item.quantity}</td>
                        <td>${item.unitprice}</td>
                        <td>${item.totalLinePrice}</td>
                    </tr>
                </c:forEach>
                <tr>
                    <th colspan = "2">Total</th>
                    <th colspan = "1"></th>
                    <td>${order.orderTotal}</td>

            </table>
            <style>
                table, h2{
                    margin: 20px;
                }
                th, td{
                    padding-right: 40px;
                }
            </style>
        </div>
    </body>
</html>
