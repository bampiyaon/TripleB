<%-- 
    Document   : Register
    Created on : Nov 14, 2018, 12:25:49 AM
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

        <title>Sign up</title>
    </head>
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
        
        <div class="container" style="margin-top:120px">
            <h1>Register</h1>
            <form action="Register" method="post">
                <fieldset>
                    <div class="form-group">
                        <label for="first">Your Firstname</label>
                        <input type="text" class="form-control" aria-describedby="emailHelp" placeholder="Enter your firstname"  name="firstname" required>
                        <small id="emailHelp" class="form-text text-muted">Please Enter in English</small>
                    </div>
                    <div class="form-group">
                        <label for="lastname">Your Lastname</label>
                        <input type="text" class="form-control" placeholder="Enter your lastname" name="lastname" required>
                        <small id="emailHelp" class="form-text text-muted">Please Enter in English</small>
                    </div>
                    <div class="form-group">
                        <label for="username">Username</label> &nbsp <span style = "color:red">${usernameNotice}</span><br>
                        <input type="number" class="form-control" placeholder="Enter student ID ex.60xxxxxxxxx" name="username" required>
                        <small id="emailHelp" class="form-text text-muted">Please use your student id</small>
                        
                    </div>
                    <div class="form-group">
                        <label for="InputPassword">Password</label>
                        <input type="password" class="form-control" placeholder="Password" name="password" required>
                    </div>
                    <div class="form-group">
                        <button type="submit" class="btn btn-primary">Submit</button>
                    </div>
                    <p style="color: red">${message}</p>
                </fieldset>
            </form>  
        </div>
    </body>
</html>

