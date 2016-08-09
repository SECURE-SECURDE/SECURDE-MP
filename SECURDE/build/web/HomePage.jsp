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

<!--<script>
    $(function() {
        $(".product").on("click", function(e) {
            e.preventDefault();
            $.post(this.href, function(data) {
                $("#someContainer").html(data);
            });
        });
    });
</script>-->

<body>
    <div w3-include-html="navbar.html"></div>
    <script>w3IncludeHTML();</script>

    <div class="product-list-container">
        <sql:setDataSource var="ds" dataSource="jdbc/securde"/>
        <sql:query var="rs" dataSource="${ds}">
            SELECT * FROM <%=Product.TABLE_NAME%>;
        </sql:query>
        <ul class="product-list">
            <c:forEach var="row" items="${rs.rows}">
                <li>
                    <div class="product-container col-md-1">
                        <form id="product${row.product_id}" role="form" action="product.jsp" method="post">
                            <input type="hidden" name=<%=Product.PRODUCT_ID%> value="${row.product_id}"/>                            
                            <a href="#" onclick="document.getElementById('product${row.product_id}').submit();">
                                <img class="product-img" src="${row.product_img}"/>
                                <h4 class="product-name"> ${row.product_name} </h4>
                                <p class="product-price"> $ ${row.price} </p>
                            </a>
                        </form>
                    </div>
                </li>
            </c:forEach>
        </ul>
                   
    </div>
</body>

</html>