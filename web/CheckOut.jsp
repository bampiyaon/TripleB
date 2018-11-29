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
    <div class="container">
        <h4>Processing</h4>
        <div class="progress">
            <div class="progress-bar progress-bar-striped progress-bar-animated" role="progressbar" aria-valuenow="75" aria-valuemin="0" aria-valuemax="100" style="width: 66%"></div>
        </div>

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
                <input type="text" class="form-control" id="InputStudentId" placeholder="ex. CB2301" required> 
                <small id="emailHelp" class="form-text text-muted">Please Enter in English</small>
            </div>
            <div colspan="2"><p style="color: red">${message}</p></div>
        </fieldset>
        <form action="Payment" method="post">
            <input type="submit" class="btn btn-primary" value="Pay Now"/>
        </form>
    </head>
</div>

</body>
</html>
