<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.io.*,java.util.*,java.sql.*, web.*, web.model.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<html>
<head>
    <meta charset="utf-8">
    <title>SECURDE</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
    <script type = "text/javascript" src="javascript/jquery-1.11.1.min.js"></script>
    <script type = "text/javascript" src="javascript/bootstrap.min.js"></script>
    <script type="text/javascript" src="javascript/w3data.js"></script>
    <link rel="stylesheet" type="text/css" href="css/main.css"/>
</head>

<%
    Account account = null;
    
    try {
        account = (Account)request.getSession().getAttribute(Account.TABLE_NAME);
        if(account == null) {
            response.sendRedirect("login.jsp");
        }
    } catch(NullPointerException noCookies) {
        response.sendRedirect("login.jsp");
    } 
%>

<body>
    <div w3-include-html="navbar.jsp"></div>
    <script>w3IncludeHTML();</script>
    
    <div class="container">
        <div class="row">        
            <div class="product-list-container">
                <sql:setDataSource var="ds" dataSource="jdbc/securde"/>
                <sql:query var="rs" dataSource="${ds}">
                    SELECT * FROM <%=Product.TABLE_NAME%>;
                </sql:query>
                <c:forEach var="row" items="${rs.rows}">
                    <div class="col-md-3 col-sm-3 col-lg-3">
                        <div class="thumbnail">
                            <form id="product${row.product_id}" role="form" action="product.jsp" method="post">
                                <input type="hidden" name=<%=Product.PRODUCT_ID%> value="${row.product_id}"/>      
                                <div class="caption">
                                    <img class="product-img" src="${row.product_img}"/>
                                    <h4 class="product-price pull-right"> $ ${row.price} </h4>
                                    <h4>
                                        <a href="#" onclick="document.getElementById('product${row.product_id}').submit();">
                                            ${row.product_name}
                                        </a>
                                    </h4>
                                    <p>${row.product_description}</p>
                                </div>
                            </form>
                        </div>
                    </div>
                </c:forEach>     
            </div>
        </div>
    </div>
</body>

</html>