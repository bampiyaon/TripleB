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
        <form>
            <fieldset>
                <div class="form-group">
                    <label for="username">Your Firstname</label>
                    <input type="number" class="form-control" id="InputStudentId" aria-describedby="emailHelp" placeholder="Enter your firstname" required>
                </div>
                                <div class="form-group">
                    <label for="username">Your Lasttname</label>
                    <input type="number" class="form-control" id="InputStudentId" aria-describedby="emailHelp" placeholder="Enter your lastname" required>
                </div>
                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="number" class="form-control" id="InputStudentId" aria-describedby="emailHelp" placeholder="Enter student ID ex.60xxxxxxxxx" required>
                    <small id="emailHelp" class="form-text text-muted">Please use your student id</small>
                </div>
                <div class="form-group">
                    <label for="InputPassword">Password</label>
                    <input type="password" class="form-control" id="InputPassword" placeholder="Password" required>
                </div>
                <div class="form-group">
                    
                </div>
                <button type="submit" class="btn btn-primary">Submit</button>
            </fieldset>
        </form>
        </div>
    </body>
</html>

