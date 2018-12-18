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
            <li class="nav-item">
                <a class="nav-link js-scroll-trigger" href="Register">Sign up</a>
            </li>
            <li class="nav-item">
                <a class="nav-link js-scroll-trigger" href="ProductList">Product</a>
            </li>
           
            
<!--            <c:if test="${cart != null}">
                <a class="navbar-brand" href="ShowCart" title="Cart"><img src="basket.png" width="25">${cart.totalQuantity}</a>
                </c:if>-->
           
            <c:choose>
                <c:when test="${sessionScope.customer != null}">
                    <a href="Logout">${sessionScope.customer.firstname}</a>
                </c:when>
                <c:otherwise>
<!--                    <a href="Login?returnUrl=${requestScope['javax.servlet.forward.request_uri']}?${requestScope['javax.servlet.forward.query_string']}">Hello Guest</a>-->
                </c:otherwise>
            </c:choose>
            
          </ul>
        </div>
      </div>
    </nav>

        
        <div class="container" style="margin-top:200px">
            <h1>Register</h1>
            <form action="Register" method="post">
                <fieldset>
                    <div class="form-group">
                        <label for="first">Your Firstname</label>
                        <input type="text" class="form-control" id="InputStudentId" aria-describedby="emailHelp" placeholder="Enter your firstname"  name="firstname" required>
                        <small id="emailHelp" class="form-text text-muted">Please Enter in English</small>
                    </div>
                    <div class="form-group">
                        <label for="lastname">Your Lastname</label>
                        <input type="text" class="form-control" id="InputStudentId" placeholder="Enter your lastname" name="lastname" required>
                        <small id="emailHelp" class="form-text text-muted">Please Enter in English</small>
                    </div>
                    <div class="form-group">
                        <label for="username">Username</label>
                        <input type="number" class="form-control" id="InputStudentId" placeholder="Enter student ID ex.60xxxxxxxxx" name="username" required>
                        <small id="emailHelp" class="form-text text-muted">Please use your student id</small>
                    </div>
                    <div class="form-group">
                        <label for="InputPassword">Password</label>
                        <input type="password" class="form-control" id="InputPassword" placeholder="Password" name="password" required>
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

