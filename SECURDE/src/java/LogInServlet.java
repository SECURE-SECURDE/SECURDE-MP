/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import web.Account;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import web.Cart;
import web.model.AccountModel;

/**
 *
 * @author Miko Garcia
 */
public class LogInServlet extends MySQLDbcpServlet {

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
        
        String user = request.getParameter("user");
        String pwd = request.getParameter("pwd");

        try {            
            this.invalidateSession();
            this.newSession(request.getSession(true));
            this.clearCookies();
            
            if(AccountModel.getInstance().validateAccount(user, pwd)) {
                Account account = AccountModel.getInstance().getAccountByUsernameOrEmail(user);
                
                this.addToSession(Account.TABLE_NAME, account);
                this.addToSession("login_error", false);
                this.addToSession(Cart.ATTRIBUTE_NAME, new Cart());
                
                if(AccountModel.getInstance().isAdmin(account.getID())) {
                    response.sendRedirect("AdminPage.jsp");
                } else {
                    response.sendRedirect("HomePage.jsp");
                }
               
            } else {
                if(AccountModel.getInstance().addLoginAttempt(user) < Account.MAX_LOGIN_ATTEMPT) {
                    this.addToSession("login_error", true);
                } else {
                    AccountModel.getInstance().setLockDate(user);
                    this.addToSession("login_error", true);
                    this.addToSession("lock_account", true);
                    
                    AccountModel.getInstance().resetAttempts(user);
                }
                
                response.sendRedirect("login.jsp");
            }
            
            } catch (SQLException ex) {
                Logger.getLogger(LogInServlet.class.getName()).log(Level.SEVERE, null, ex);
                this.addToSession("login_error", true);
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
        super.doPost(request, response);
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
