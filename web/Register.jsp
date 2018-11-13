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
        <h1>Register</h1>
        <form>
            <fieldset>
                <legend>Legend</legend>
                <div class="form-group row">
                    <label for="staticEmail" class="col-sm-2 col-form-label">Example Student ID</label>
                    <div class="col-sm-10">
                        <input type="text" readonly="" class="form-control-plaintext" id="staticEmail" value="60xxxxxxxxx">
                    </div>
                </div>
                <div class="form-group">
                    <label for="InputStudentId">Your Student ID</label>
                    <input type="number" class="form-control" id="InputStudentId" aria-describedby="emailHelp" placeholder="Enter student ID" required>
                    <small id="emailHelp" class="form-text text-muted">We'll never share your data with anyone else.</small>
                </div>
                <div class="form-group">
                    <label for="InputPassword">Password</label>
                    <input type="password" class="form-control" id="InputPassword" placeholder="Password" required>
                </div>
                <div class="form-group">
                    <label for="studyYear">Year</label>
                    <select class="form-control" id="exampleSelect1">
                        <option>1</option>
                        <option>2</option>
                        <option>3</option>
                        <option>4</option>
                        <option>5</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="exampleInputFile">File input</label>
                    <input type="file" class="form-control-file" id="exampleInputFile" aria-describedby="fileHelp">
                    <small id="fileHelp" class="form-text text-muted">This is some placeholder block-level help text for the above input. It's a bit lighter and easily wraps to a new line.</small>
                </div>
                <button type="submit" class="btn btn-primary">Submit</button>
            </fieldset>
        </form>
    </body>
</html>
