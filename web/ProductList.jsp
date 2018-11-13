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
        <link rel="stylesheet" href="https://bootswatch.com/4/journal/bootstrap.min.css">
    </head>
    <body>
        <jsp:include page="include/Header.jsp?title=Our Products"/>
        <table class="table table-hover">
            <thead>
            <th scope="col">Image</th>
            <th scope="col">#</th>
            <th scope="col">Product ID</th>
            <th scope="col">Product Name</th>
            <th scope="col">Shop Name</th>
            <th scope="col">Price(baht)</th>
            <th scope="col">Add</th>
            <th scope="col">Remove</th>
        </thead>

        <c:forEach items="${products}" var="p" varStatus="vs">
            <tr class="table-info">
                <td><img src="model-images/${p.productid}.jpg" width="120"></td>
                <td>${vs.count}</td>
                <td>${p.productid}</td>
                <td>${p.productname}</td>
                <td>${p.shopShopid.shopname}</td>
                <td>${p.productprince}</td>
                <td>
                    <a href="AddItemToCart?productId=${p.productid}">
                        <img src="basket.png" width="25"></a>
                </td>
                <td>
                    <a href="RemoveToCart?productId=${p.productid}">
                        <img src="bin.png" width="25"></a>
                    </a>
                </td>
            </tr>
        </c:forEach>        
    </table>
    <!--        &nbsp;
            <table>
                <tr>
                    <td>
                        <div class="card text-white bg-primary mb-3" style="max-width: 20rem;" >
                            <div class="card-header">น้ำส้มปั่น</div>
                            <div class="card-body">
                                <h4 class="card-title">Primary card title</h4>
                                <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                            </div>
                        </div>
                    </td>
                    <td>
                        <div class="card text-white bg-secondary mb-3" style="max-width: 20rem;">
                            <div class="card-header">Header</div>
                            <div class="card-body">
                                <h4 class="card-title">Secondary card title</h4>
                                <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                            </div>
                        </div>
                    </td>
                    <td>
                        <div class="card text-white bg-success mb-3" style="max-width: 20rem;">
                            <div class="card-header">Header</div>
                            <div class="card-body">
                                <h4 class="card-title">Success card title</h4>
                                <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                            </div>
                        </div> 
                    </td>
                    <td>
                        <div class="card text-white bg-danger mb-3" style="max-width: 20rem;">
                            <div class="card-header">Header</div>
                            <div class="card-body">
                                <h4 class="card-title">Danger card title</h4>
                                <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="card text-white bg-warning mb-3" style="max-width: 20rem;">
                            <div class="card-header">Header</div>
                            <div class="card-body">
                                <h4 class="card-title">Warning card title</h4>
                                <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                            </div>
                        </div>
                    </td>
                    <td>
                        <div class="card text-white bg-info mb-3" style="max-width: 20rem;">
                            <div class="card-header">Header</div>
                            <div class="card-body">
                                <h4 class="card-title">Info card title</h4>
                                <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                            </div>
                        </div>
                    </td>
                    <td>
                        <div class="card bg-light mb-3" style="max-width: 20rem;">
                            <div class="card-header">Header</div>
                            <div class="card-body">
                                <h4 class="card-title">Light card title</h4>
                                <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                            </div>
                        </div>
                    </td>
                    <td>
    
                        <div class="card text-white bg-dark mb-3" style="max-width: 20rem;">
                            <div class="card-header">Header</div>
                            <div class="card-body">
                                <h4 class="card-title">Dark card title</h4>
                                <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                            </div>
                        </div>
                    </td>
                </tr>
            </table>-->
</body>
</html>
