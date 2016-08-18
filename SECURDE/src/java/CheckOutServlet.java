/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import web.Account;
import web.Cart;
import web.LineItem;
import web.Order;
import web.model.OrderModel;

/**
 *
 * @author user
 */
public class CheckOutServlet extends MySQLDbcpServlet {

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
        
        try {
            if(this.validateSessionId(request, request.getParameter("SESSION_ID"))) {
                Account account = (Account) request.getSession().getAttribute(Account.TABLE_NAME);
                Cart cart = (Cart) request.getSession().getAttribute(Cart.ATTRIBUTE_NAME);
                List<LineItem> items = cart.getItems();

                Order newOrder = new Order.OrderBuilder()
                        .userId(account.getID())
                        .build();

                for(LineItem item: items) {
                    newOrder.addLineItem(item);
                }

                OrderModel.getInstance().addOrder(newOrder);
                
                this.addToSession(Cart.ATTRIBUTE_NAME, new Cart());
                response.sendRedirect("HomePage.jsp");
            } else {
                response.sendRedirect("CSRF.html");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CheckOutServlet.class.getName()).log(Level.SEVERE, null, ex);
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
