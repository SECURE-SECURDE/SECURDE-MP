<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.io.*,java.util.*,java.sql.*, web.*, web.model.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*, servlets.*" %>

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
    int productID = 0;
    
    Account account = null;
    Product product = null;
    boolean bought = false;
    String redirect = "";

    try {
        if(!MySQLDbcpServlet.sameOrigin(request)) {
            redirect = MySQLDbcpServlet.ACCESS_DENIED_URL;
        }
    } catch(NullPointerException ex) {
        redirect = MySQLDbcpServlet.ACCESS_DENIED_URL;
    } 
    
    try {
        productID = Integer.parseInt((String)request.getParameter(Product.PRODUCT_ID));
        product = ProductModel.getInstance().getProductById(productID);
        
        account = (Account)request.getSession().getAttribute(Account.TABLE_NAME);
        
        bought = ProductModel.getInstance().boughtByUser(productID, account.getID());
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
                        <%=Review.USER_ID%>: "<%=account.getID()%>",
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
            
            $("#review").val("");
        });
    });
    
</script>

<body>
    <div w3-include-html="navbar.jsp"></div>
    <script>w3IncludeHTML();</script>
    
    <div class="container">
        <div class="row">
            <div class="order-info">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <b><%=product.getProductName()%></b>
                        <p><%=product.getDescription()%></p>
                    </div>
                    <div class="panel-body">
                        <form class="form-horizontal" action="AddToCartServlet" method="post">
                            <input type="hidden" name="<%=LineItem.PRODUCT_ID%>" value="<%=productID%>"/>
                            <div class="form-group">
                                <label for="<%=LineItem.QTY%>" class="col-md-3 control-label">
                                    Quantity:
                                </label>
                                <div class="col-md-9">
                                    <input id="<%=LineItem.QTY%>" type="number" name="<%=LineItem.QTY%>" min="1" max="1000" value="1" onchange="updateTotal();"/>
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
                                    <b><c:out value="${row.username}"/></b>
                                    <p class="pull-right"><c:out value="${row.review_date}"/></p>
                                    <p><c:out value="${row.review}"/></p>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>

<%
    } catch(NullPointerException noValue) {
        redirect = "login.jsp";
    } catch(NumberFormatException ex) {
        redirect = "HomePage.jsp";
    }
    
    if(!redirect.isEmpty()) {
        response.sendRedirect(redirect);
    }
%>
</html>