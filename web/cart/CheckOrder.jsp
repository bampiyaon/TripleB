<%-- 
    Document   : CheckOrder
    Created on : Nov 28, 2018, 4:05:06 PM
    Author     : piyao
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
                        <a class="nav-link js-scroll-trigger" href="ProductList">Product</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link js-scroll-trigger" href="Register">Sign up</a>
                    </li>

                </ul>                       
            </div>
        </div>
    </nav> 
    <div class="container">
        <h4>Processing</h4>
        <fieldset>
            <div class="progress" style="margin-top:100px">
                <div class="progress-bar progress-bar-striped progress-bar-animated" role="progressbar" aria-valuenow="75" aria-valuemin="0" aria-valuemax="100" style="width: 66%"></div>
            </div>
        </fieldset>
        <c:set value = "${sessionScope.customer.addressList}" var = "customerAddress"></c:set>
            
                    <form action="AddAddress" method="post">
                        <div class="form-group">
                            <h3>Shipping address in KMUTT</h3>
                            <input type="text" class="form-control" id="InputStudentId" placeholder="ex. CB2301" name="location" required> 
                            <small id="emailHelp" class="form-text text-muted">Please Enter in English</small>
                            <button type="submit" class="btn btn-primary">Add Address</button>
                        </div>
                    </form>
                
                    <form action = "Checkout" method = "post">
                        <h6>DEFAULT SHIPPING ADDRESS</h6>
                        <c:forEach items = "${customerAddress}" var = "address">
                            <div>
                                <span class = "addressInfo">
                                    <input type = "radio" name = "addressId" value = "${address.addressid}" required/>
                                    ${address.location}
                                </span>
                            </div>

                        </c:forEach>
                    
                        
            <fieldset class="form-group">
                <h3>Payment Methods</h3>
                <div class="form-check">

                    <span class = "paymentField">
                        <label>Credit Card No:</label>
                        <input type = "number" min = "1" required> &nbsp;
                        <img src = "images/payment/visa.png" alt = "visa" width = "40px" height = "40px" >
                        <img src = "images/payment/mastercard.png" alt = "mastercard" width = "40px" height = "40px">
                    </span>
                    <span class = "paymentField">
                        <label>CCV:</label>
                        <input type = "number" min = "1" required>
                    </span>
                    <span class = "paymentField">
                        <label>EXP:</label>
                        <input class = "expDate" type = "number" min = "1" max = "12" required> / <input class = "expDate" type = "number" min = "2018" max = "2024" required>
                    </span>
                    <input type="submit" class="btn btn-primary" value="Pay Now"/>
                </div>
            </fieldset>
        </form>
    </div>
</div>
<style>
    .addressField{
        display: block;
    }
    .expDate{
        width: 60px;
    }
    .shipping, .payment{
        margin: 50px;
        width: 60%;
        border: black solid medium;
    }
    .addressField, .paymentField, .addressInfo{
        display: block;
        margin: 10px;
    }
</style>

</body>
</html>
