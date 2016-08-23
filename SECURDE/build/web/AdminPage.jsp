<%@page import="servlets.MySQLDbcpServlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.io.*,java.util.*,java.sql.*, web.*, web.model.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%
    Account account = null;
    
    try {
        account = (Account) request.getSession().getAttribute(Account.TABLE_NAME);
        
        if(!AccountModel.getInstance().isAdmin(account.getID())) {
            response.sendRedirect(MySQLDbcpServlet.ACCESS_DENIED_URL);
        }
    } catch (NullPointerException ex) {
        response.sendRedirect("login.jsp");
    }
%>

<html>
<head>
    <meta charset="utf-8">
    <title>SECURDE</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
    <script type="text/javascript" src="javascript/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="javascript/bootstrap.min.js"></script>
    <script type="text/javascript" src="javascript/w3data.js"></script>		
    <link rel="stylesheet" type="text/css" href="css/main.css"/>
</head>

<body>
    <div w3-include-html="navbar.html"></div>
    <script>w3IncludeHTML();</script>
    
    <div class="panel panel-default">
        <div class="panel-heading">
            Add Product
        </div>
        <form action="AddProductServlet" method="post">
            <div class="panel-body">
                <div class="form-group">
                    <label for="<%=Product.PRODUCT_NAME%>" class="col-md-3 control-label">
                        Product Name:
                    </label>
                    <div class="col-md-9">
                        <input type="text" name="<%=Product.PRODUCT_NAME%>" required=""/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="<%=Product.PRODUCT_DESCRIPTION%>" class="col-md-3 control-label">
                        Description:
                    </label>
                    <div class="col-md-9">
                        <textarea class="form-control counted" name="<%=Product.PRODUCT_DESCRIPTION%>" placeholder="Product Description here..."></textarea>
                    </div>
                </div>
                <div class="form-group">
                    <label for="<%=Product.PRODUCT_PRICE%>" class="col-md-3 control-label">
                        Standard Price:
                    </label>
                    <div class="col-md-9"> 
                        <input type="number" name="<%=Product.PRODUCT_PRICE%>" value="0.00" min="0.00" step="0.01" required=""/>
                    </div>
                </div>
            </div>
            <div class="panel-footer">
                <button type="submit" class="btn btn-success">Add Product</button>
            </div>
        </form>
    </div>
                    
    <div class="panel">
        
    </div>
</body>

</html>
