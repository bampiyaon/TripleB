<%-- 
    Document   : CheckOrder
    Created on : Nov 28, 2018, 4:05:06 PM
    Author     : piyao
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My Order</title>
        <link rel="stylesheet" href="https://bootswatch.com/4/journal/bootstrap.min.css">
         <jsp:include page="include/Header.jsp?title=Order Confirmation"/>
        <table id="example" class="table">
            <thead>
            <th>Image</th>
            <th>#</th>
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
                <td>${line.product.productname}</td>
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
              <fieldset class="form-group">
      <div class="form-check">
        <label class="form-check-label">
            <p>Payment Methods</p>
            <input type="radio" class="form-check-input" name="optionsRadios" id="optionsRadios1" value="option1" required checked><li>
                <img src="visa-logo-new.jpg" width="50">
        </label>
      </div>
    </fieldset>
                    <fieldset>
                    <div class="form-group">
                        <label for="first">Your location in KMUTT</label>
                        <input type="text" class="form-control" id="InputStudentId" aria-describedby="emailHelp" placeholder="ex. CB2301"  name="" required> 
                        <small id="emailHelp" class="form-text text-muted">Please Enter in English</small>
                    </div>
                    <div class="form-group">
                        <button type="submit" class="btn btn-primary">Submit</button>
                    </div>
                    <div colspan="2"><p style="color: red">${message}</p></div>
                </fieldset>
    <form action="Payment" method="post">
        <input type="submit" class="btn btn-primary" value="Pay Now"/>
    </form>
    </head>
    
        
    </body>
</html>
