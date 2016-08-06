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
    <script type="text/javascript" src="javascript/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="javascript/bootstrap.min.js"></script>
    <script type="text/javascript" src="javascript/w3data.js"></script>		
    <link rel="stylesheet" type="text/css" href="css/main.css"/>
</head>

<% 
    int productID = Integer.parseInt((String)request.getParameter(Product.PRODUCT_ID));
    
    Product product = ProductModel.getInstance().getProductById(productID);
%>

<script>
    var updateTotal = function() {
            var price = <%=product.getPrice()%>;
            var qty = document.getElementById('qty').value;
            var total = Math.round(price*qty*100)/100;
            document.getElementById('totalPrice').innerHTML = '$' + total;
            document.getElementById('price').value = total;
        };
</script>

<body>
    <div w3-include-html="navbar.html"></div>
    <script>w3IncludeHTML();</script>

    <div class="order-info">
        <div class="panel panel-default">
            <div class="panel-heading">
                <b><%=product.getProductName()%></b>
                <p><%=product.getDescription()%></p>
            </div>
            <div class="panel-body">
                <form class="form-horizontal" action="checkout.jsp" method="post">
                    <div class="form-group">
                        <label for="inputPrice" class="col-md-3 control-label">
                            Quantity:
                        </label>
                        <div class="col-md-9">
                            <input id="qty" type="number" name="qty" min="1" value="1" onchange="updateTotal();"/>
                        </div> 
                    </div>
                    <div class="form-group">
                        <label for="totalPrice" class="col-md-3 control-label">
                            Total: 
                        </label>
                        <div class="col-md-9" id="totalPrice">
                            $<%=product.getPrice()%>
                        </div>
                        <input id="price" type="hidden" name="totalPrice" value="<%=product.getPrice()%>">
                    </div>
                    <div class="form-group last">
                        <div class="col-sm-offset-3 col-sm-9">
                            <button type="submit" class="btn btn-success btn-sm">
                                Check Out
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="review-container panel panel-default">
        <div class="panel-heading">
            Reviews
        </div>
        <sql:setDataSource var="ds" dataSource="jdbc/securde"/>
        <sql:query var="rs" dataSource="${ds}">
            SELECT * FROM <%=Review.TABLE_NAME%>, <%=Account.TABLE_NAME%> WHERE <%=Review.USER_ID%>=<%=Account.ACCOUNT_ID%> AND <%=Review.PRODUCT_ID%>=<%=product.getProductId()%>;
        </sql:query>
        <ul class="list-group panel-body">
            <c:forEach var="row" items="${rs.rows}">
                <li class="list-group-item">
                    <b>${row.username}</b>
                    <p>${row.review}</p>
                </li>
            </c:forEach>
        </ul>
    </div>
</body>

</html>