package servlets;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import web.Review;
import web.model.ProductModel;
import web.model.ReviewModel;

/**
 *
 * @author user
 */
public class PostServlet extends MySQLDbcpServlet {

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
        
        if(PostServlet.sameOrigin(request)) {
            try {
                int userId = Integer.parseInt(request.getParameter(Review.USER_ID));
                int productId = Integer.parseInt(request.getParameter(Review.PRODUCT_ID));
                String review = request.getParameter(Review.REVIEW);
                
                if(ProductModel.getInstance().boughtByUser(productId, userId)) {
                    try {
                        Review rev = new Review.ReviewBuilder()
                                .userId(userId)
                                .review(review)
                                .productId(productId)
                                .build();
                        
                        ReviewModel.getInstance().addReview(rev);
                        
                    } catch (SQLException ex) {
                        Logger.getLogger(PostServlet.class.getName()).log(Level.SEVERE, null, ex);
                        response.sendError(0);
                    }
                } else {
                    //do nothing for now...
                }
            } catch (SQLException ex) {
                Logger.getLogger(PostServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
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
