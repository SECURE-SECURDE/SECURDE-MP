<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.io.*,java.util.*,java.sql.*, web.*, web.model.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    int cart_count = 0;
    try {
        Cart cart = (Cart) request.getSession().getAttribute(Cart.ATTRIBUTE_NAME);
        cart_count = cart.getCartCount();
    } catch(NullPointerException ex){
        
    }
%>

<nav class = "navbar navbar-default">
    <div class = "collapse navbar-collapse">
        <ul class = "nav navbar-nav">
            <li><a href="HomePage.jsp">Home</a></li>
            <li><a href="LogOutServlet">Log Out</a></li>
            <li><a href="checkout.jsp">Check Out (<%=cart_count%>)</a></li>
        </ul>	
    </div>
</nav>