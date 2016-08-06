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

	<table class="checkout-items">
		<tr>
			<th class="qty-col">Qty</th>
			<th class="name-col">Item Name</th>
			<th class="price-col">Price</th>
		</tr>
		<tr>
			<td>1</td>
			<td>Some Product</td>
			<td>0.00</td>
		</tr>
	</table>
	<h4>Total Price:</h4> 0.00
	<form action="">
		<input type="submit" value="Order">
		<input type="hidden" value="0.00" name="total-price">
	</form>
</body>

</html>