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
        <title>Sign up</title>
    </head>
    <body>
        
        <jsp:include page="include/Header.jsp?title=Register"/>
        <div class="container">
        <h1>Register</h1>
        <form action="Register" method="post">
            <fieldset>
                <div class="form-group">
                    <label for="username">Your Firstname</label>
                    <input type="text" class="form-control" id="InputStudentId" aria-describedby="emailHelp" placeholder="Enter your firstname"  name="firstname" required>
                </div>
                                <div class="form-group">
                    <label for="username">Your Lastname</label>
                    <input type="text" class="form-control" id="InputStudentId" aria-describedby="emailHelp" placeholder="Enter your lastname" name="lastname" required>
                </div>
                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="number" class="form-control" id="InputStudentId" aria-describedby="emailHelp" placeholder="Enter student ID ex.60xxxxxxxxx" name="username" required>
                    <small id="emailHelp" class="form-text text-muted">Please use your student id</small>
                </div>
                <div class="form-group">
                    <label for="InputPassword">Password</label>
                    <input type="password" class="form-control" id="InputPassword" placeholder="Password" name="password" required>
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

