<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.io.*,java.util.*,java.sql.*, web.*, web.model.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    int productID = Integer.parseInt(request.getParameter(Product.PRODUCT_ID));
    
    Product product = ProductModel.getInstance().getProductById(productID);
%>

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
