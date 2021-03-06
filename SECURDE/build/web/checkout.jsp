<%@page import="servlets.MySQLDbcpServlet"%>
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
    <script type="text/javascript" src="javascript/w3data.js"></script>		
    <link rel="stylesheet" type="text/css" href="css/main.css"/>
</head>

<%  
    Cart cart = new Cart();
    List<LineItem> items = new ArrayList<>();
    
    try {

        if(MySQLDbcpServlet.sameOrigin(request)) {
            try {
                cart = (Cart) request.getSession().getAttribute(Cart.ATTRIBUTE_NAME);
                items = cart.getItems();
            } catch (NullPointerException ex) {

            }
        } else {
            response.sendRedirect(MySQLDbcpServlet.ACCESS_DENIED_URL);
        } 
    } catch(NullPointerException ex) {
        response.sendRedirect(MySQLDbcpServlet.TIMEOUT_URL);
    }
%>
<body>
    <div w3-include-html="navbar.jsp"></div>
    <script>w3IncludeHTML();</script>
    
    <div class="container">
        <div class="checkout-items">
            <table class="checkout-items table table-striped table-bordered table-list">
                <thead>
                    <tr>
                        <th class="qty-col">Qty</th>
                        <th class="name-col">Item Name</th>
                        <th class="price-col">Price</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        for(LineItem item: items) {
                            Product product = ProductModel.getInstance().getProductById(item.getProduct().getProductId());
                    %>
                    <tr>
                        <td><%=item.getQty()%></td>
                        <td><%=product.getProductName()%></td>
                        <td>$<%=item.getTotalPrice()%></td>
                    </tr>
                    <%
                        }
                    %>
                    <tr>
                        <td></td>
                        <td align="right"><b>Total Price</b></td>
                        <td>$<%=cart.getTotalPrice()%></td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div class="purchase-container">
            <form action="CheckOutServlet" method="post">
                <div class="form-group">
                    <label class="control-label">Card Holder's Name</label>
                    <input type="text" class="form-control" autocomplete="off" required=""/>
                </div>
                <div class="form-group">
                    <label class="control-label">Card Number</label>
                    <div class="controls">
                        <div class="row">
                            <div class="col-sm-3 col-md-3">
                                <input type="text" class="form-control" autocomplete="off" maxlength="4" pattern="\d{4}" title="First four digits" required="">
                            </div>
                            <div class="col-sm-3 col-md-3">
                                <input type="text" class="form-control" autocomplete="off" maxlength="4" pattern="\d{4}" title="Second four digits" required="">
                            </div>
                            <div class="col-sm-3 col-md-3">
                                <input type="text" class="form-control" autocomplete="off" maxlength="4" pattern="\d{4}" title="Third four digits" required="">
                            </div>
                            <div class="col-sm-3 col-md-3">
                                <input type="text" class="form-control" autocomplete="off" maxlength="4" pattern="\d{4}" title="Fourth four digits" required="">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label">Expiration Date</label>
                    <input type="date" class="form-control" required=""/>
                </div>
                <div class="form-group">
                    <label class="control-label">Card CCV</label>
                    <input type="text" class="form-control" maxlength="4" pattern="\d{3,4}" required=""/>
                </div>
                <button class="btn btn-success" align="right" type="submit">
                    Purchase
                </button>
            </form>
        </div>
    </div>
</body>

</html>