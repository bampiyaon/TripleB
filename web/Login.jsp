<%-- 
    Document   : Login
    Created on : Nov 13, 2018, 9:37:44 AM
    Author     : piyao
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <link rel="stylesheet" href="https://bootswatch.com/4/journal/bootstrap.min.css">
    </head>
    <body>
        <div class="container">
            <jsp:include page="include/Header.jsp?title=Welcome to Triple B"/> 
            <form action="Login" method="post">
                <!--<div class="btn-group-vertical" data-toggle="buttons">-->
                    <table class="table">
                        <tr>
                            <td>Username :</td>
                            <td><input type="number" name="userName" required</td>
                        </tr>
                        <tr>
                            <td>Password :</td>
                            <td><input type="password" name="password" required</td>
                        </tr>
                        <tr>
                            <td></td>
                            <td><input type="submit" class="btn btn-primary" value="Login"</td>
                        </tr>
                        <tr>
                            <td colspan="2"><p style="color: red">${loginfailed}</p></td>
                        </tr>
                    </table>
                <!--</div>-->
            </form>
    </body>
</html>
