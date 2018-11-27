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
            </form> 
        </div>
    </body>
</html>
