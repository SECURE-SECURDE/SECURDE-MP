<%@page import="web.model.ProductModel"%>
<%@page import="web.Product"%>
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
    int productID = Integer.parseInt(request.getParameter(Product.PRODUCT_ID));
    int qty = Integer.parseInt(request.getParameter("qty"));
    double price = Double.parseDouble(request.getParameter("totalPrice"));
    
    Product product = ProductModel.getInstance().getProductById(productID);
%>
<body>
	<div w3-include-html="navbar.html"></div>
	<script>w3IncludeHTML();</script>

	<table class="checkout-items table table-striped table-bordered table-list">
            <thead>
		<tr>
			<th class="qty-col">Qty</th>
			<th class="name-col">Item Name</th>
			<th class="price-col">Price</th>
		</tr>
            </thead>
            <tbody>
		<tr>
			<td><%=qty%></td>
			<td><%=product.getProductName()%></td>
			<td><%=price%></td>
		</tr>
            </tbody>
	</table>
	<h4>Total Price:</h4> <%=price%>
	<form action="">
		<input type="submit" value="Order">
		<input type="hidden" value="0.00" name="total-price">
	</form>
</body>

</html>