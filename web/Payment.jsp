<%-- 
    Document   : Payment
    Created on : Nov 22, 2018, 4:45:30 PM
    Author     : piyao
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Payment</title>
        <link rel="stylesheet" href="https://bootswatch.com/4/journal/bootstrap.min.css">
    </head>
    <body>
        <jsp:include page="include/Header.jsp?title=Our Products"/>
        <div class="container">
            <form action="Register" method="post">
                <div class="alert alert-dismissible alert-success">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                    <strong>Successfully</strong> Thank you for your order.
                </div>
            </form> 
        </div>
    </body>
</html>
