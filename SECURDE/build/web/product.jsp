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
    int accountID = 0;
    int productID = 0;
    Product product = null;
    boolean bought = false;
    
    try {
        productID = Integer.parseInt((String)request.getParameter(Product.PRODUCT_ID));
        product = ProductModel.getInstance().getProductById(productID);
        
        for(Cookie cookie: request.getCookies()) {
            if(cookie.getName().equals(Account.ACCOUNT_ID)) {
                accountID = Integer.parseInt(cookie.getValue());
            }
        }
        
        bought = ProductModel.getInstance().boughtByUser(productID, accountID);
    } catch(NullPointerException noValue) {
        response.sendRedirect("login.html");
    }
    
    if(accountID == 0) {
        response.sendRedirect("login.html");
    }
%>

<script>
    function updateTotal() {
        var price = <%=product.getPrice()%>;
        var qty = document.getElementById('<%=LineItem.QTY%>').value;
        var total = Math.round(price*qty*100)/100;
        document.getElementById('<%=LineItem.TOTAL_PRICE%>').innerHTML = '$' + total;
        document.getElementById('price').value = total;
    };
    
    function reloadReviews() {
        $.ajax({
            url: "reviews.jsp",
            data: {
                <%=Product.PRODUCT_ID%>: "<%=productID%>"
            },
            type: "post",
            success: function(result) {
                        $("#review-container").html(result);
                    }
        });
    }
    
    $(document).ready(function() {
        $("#review-button").click(function() {
            $.ajax({
                url: "PostServlet",
                data: {
                        <%=Review.USER_ID%>: "<%=accountID%>",
                        <%=Review.PRODUCT_ID%>: "<%=productID%>",
                        <%=Review.REVIEW%>: $("#review").val()
                     },
                type: "post",
                cache: false,
                success: reloadReviews(), 
                error: function() {
                    alert("An error has occured.");
                }
            });
        });
    });
    
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
                    <input type="hidden" name="<%=Product.PRODUCT_ID%>" value="<%=productID%>"/>
                    <div class="form-group">
                        <label for="<%=LineItem.QTY%>" class="col-md-3 control-label">
                            Quantity:
                        </label>
                        <div class="col-md-9">
                            <input id="<%=LineItem.QTY%>" type="number" name="<%=LineItem.QTY%>" min="1" value="1" onchange="updateTotal();"/>
                        </div> 
                    </div>
                    <div class="form-group">
                        <label for="<%=LineItem.TOTAL_PRICE%>" class="col-md-3 control-label">
                            Total: 
                        </label>
                        <div class="col-md-9" id="<%=LineItem.TOTAL_PRICE%>">
                            $<%=product.getPrice()%>
                        </div>
                        <input id="price" type="hidden" name="<%=LineItem.TOTAL_PRICE%>" value="<%=product.getPrice()%>">
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
                    
    <div class="panel panel-default">
        <div class="panel-heading">
            Reviews
        </div>
        
        <div class="panel-body">
            <% if(bought) { %>
                <div class="review-area">
                    <textarea id="review" class="form-control counted review-area" name="<%=Review.REVIEW%>" placeholder="Leave a review..." maxlength="320" rows="5"></textarea>
                    <button id="review-button" class="btn btn-info" type="submit">Post</button>
                </div>
            <%} %>
            
            <div id="review-container">
                <sql:setDataSource var="ds" dataSource="jdbc/securde"/>
                <sql:query var="rs" dataSource="${ds}">
                    SELECT * FROM <%=Review.TABLE_NAME%>, <%=Account.TABLE_NAME%> WHERE <%=Review.USER_ID%>=<%=Account.ACCOUNT_ID%> AND <%=Review.PRODUCT_ID%>=<%=product.getProductId()%>;
                </sql:query>
                <ul class="list-group">
                    <c:forEach var="row" items="${rs.rows}">
                        <li class="list-group-item">
                            <b>${row.username}</b>
                            <p>${row.review}</p>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
</body>

</html>