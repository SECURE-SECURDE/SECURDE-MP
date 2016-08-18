package servlets;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import web.Cart;
import web.LineItem;

/**
 *
 * @author user
 */
public class AddToCartServlet extends MySQLDbcpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        super.doPost(request, response);
        
        if(this.sameOrigin(request)) {
            Cart cart = (Cart)request.getSession().getAttribute(Cart.ATTRIBUTE_NAME);

            String sessionId = request.getParameter("SESSION_ID");
            int qty = Integer.parseInt(request.getParameter(LineItem.QTY));
            int productId = Integer.parseInt(request.getParameter(LineItem.PRODUCT_ID));
            double price = Double.parseDouble(request.getParameter(LineItem.TOTAL_PRICE));

            if(this.validateSessionId(request, sessionId)) {
                LineItem item = new LineItem.LineItemBuilder()
                                    .productId(productId)
                                    .qty(qty)
                                    .totalPrice(price)
                                    .build();

                cart.addItem(item);

                response.sendRedirect("HomePage.jsp");
            } else {
                response.sendRedirect(ACCESS_DENIED_URL);
            }
        } else {
            response.sendRedirect(ACCESS_DENIED_URL);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
