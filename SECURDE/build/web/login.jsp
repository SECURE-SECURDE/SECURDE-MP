<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.io.*,java.util.*,java.sql.*, web.*, web.model.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

<head>
    <meta charset="utf-8">
    <title>SECURDE</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
    <script type="text/javascript" src="javascript/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="javascript/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="css/main.css"/>
    
    <%  
        boolean login_error = false;
        boolean lock_out = false;
        
        try {
           login_error = (boolean)request.getSession(true).getAttribute("login_error");
           lock_out = (boolean) request.getSession(true).getAttribute("lock_account");
        } catch(NullPointerException ex) {}
        
        if(lock_out) {
            request.getSession().invalidate();
            response.sendRedirect("lockAccount.html");
        }
            
    %>
    
</head>

<body>
    <div class="container">
    <div class="row">
        <div class="col-md-4 col-md-offset-7 center">
            <div class="panel panel-default">
                <div class="panel-heading">
                    Log In
                </div>
                <div class="panel-body">
                    <form class="form-horizontal" role="form" action="LogInServlet" method="post">
                    <div class="form-group">
                        <label for="inputEmail3" class="col-sm-3 control-label">
                            Username
                        </label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="inputEmail3" placeholder="Username" name="user" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="inputPassword3" class="col-sm-3 control-label">
                            Password
                        </label>
                        <div class="col-sm-9">
                            <input type="password" class="form-control" id="inputPassword3" placeholder="Password" name="pwd" required>
                            <%if(login_error) {%>
                                <span class='help-inline'>No Email or Password mismatch</span>
                            <%}%>
                        </div>
                    </div>
                    
                    <div class="form-group last">
                        <div class="col-sm-offset-3 col-sm-9">
                            <button type="submit" class="btn btn-success btn-sm">
                                Sign in</button>
                                 <button type="reset" class="btn btn-default btn-sm">
                                Reset</button>
                        </div>
                    </div>
                    </form>
                </div>
                <div class="panel-footer">
                    Not Registered? <a href="register.html">Register here</a></div>
            </div>
        </div>
    </div>
</div>

</body>
</html>