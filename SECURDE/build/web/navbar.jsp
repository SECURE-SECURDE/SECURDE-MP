<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.io.*,java.util.*,java.sql.*, web.*, web.model.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    int cart_count = 0;
    String username = "No Name";
    String checkOutLink = "#";
    
    try {
        Cart cart = (Cart) request.getSession().getAttribute(Cart.ATTRIBUTE_NAME);
        cart_count = cart.getCartCount();
        
        username = ((Account) request.getSession().getAttribute(Account.TABLE_NAME)).getUsername();
    } catch(NullPointerException ex){
        
    }
    
    if(cart_count > 0) {
        checkOutLink = "checkout.jsp";
    }
%>

<nav class = "navbar navbar-default">
    <div class = "collapse navbar-collapse">
        <ul class = "nav navbar-nav">
            <li><a href="HomePage.jsp">Home</a></li>
            <li><a href="#"><%=username%></a></li>
            <li class="pull-right"><a href="LogOutServlet">Log Out</a></li>
            <li><a href="<%=checkOutLink%>">Check Out (<%=cart_count%>)</a></li>
        </ul>	
    </div>
</nav>