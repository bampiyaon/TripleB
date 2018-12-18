<%-- 
    Document   : Header
    Created on : Nov 13, 2018, 10:07:46 AM
    Author     : piyao
--%>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Agency - Start Bootstrap Theme</title>

    <!-- Bootstrap core CSS -->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom fonts for this template -->
    <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">
    <link href='https://fonts.googleapis.com/css?family=Kaushan+Script' rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Droid+Serif:400,700,400italic,700italic' rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700' rel='stylesheet' type='text/css'>

    <!-- Custom styles for this template -->
    <link href="css/agency.min.css" rel="stylesheet">

  </head>

  <body id="page-top">

    <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-dark fixed-top" id="mainNav" style="background-color: #696969">
      <div class="container">
        <a class="navbar-brand js-scroll-trigger" href="#page-top">TripleBB</a>
        <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
          Menu
          <i class="fas fa-bars"></i>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
            
            
          
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
            <li class="nav-item">
              <a class="nav-link js-scroll-trigger" href="#team">Team</a>
            </li>
             <c:if test="${cart != null}">
                <a class="navbar-brand" href="ShowCart" title="Cart"><img src="basket.png" width="25">${cart.totalQuantity}</a>
                </c:if>
           
            <c:choose>
                <c:when test="${sessionScope.customer != null}">
                    Hello <a href="Logout">${sessionScope.customer.firstname},</a>
                </c:when>
                <c:otherwise>
                    <a href="Login?returnUrl=${requestScope['javax.servlet.forward.request_uri']}?${requestScope['javax.servlet.forward.query_string']}">Hello Guest</a>
                </c:otherwise>
            </c:choose>
           
            
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
            
                            

            
          </ul>
        </div>
      </div>
    </nav>
           
            <c:if test="${cart != null}">
                <a class="navbar-brand" href="ShowCart" title="Cart"><img src="basket.png" width="25">${cart.totalQuantity}</a>
                </c:if>
           
            <c:choose>
                <c:when test="${sessionScope.customer != null}">
                    Hello <a href="Logout">${sessionScope.customer.firstname},</a>
                </c:when>
                <c:otherwise>
                    <a href="Login?returnUrl=${requestScope['javax.servlet.forward.request_uri']}?${requestScope['javax.servlet.forward.query_string']}">Hello Guest</a>
                </c:otherwise>
            </c:choose>
          
        </ul>
        <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Plugin JavaScript -->
    <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

    <!-- Contact form JavaScript -->
    <script src="js/jqBootstrapValidation.js"></script>
    <script src="js/contact_me.js"></script>

    <!-- Custom scripts for this template -->
    <script src="js/agency.min.js"></script>

    </div>
</nav>

