package servlets;


import com.sun.istack.internal.logging.Logger;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.Account;
import web.model.AccountModel;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends MySQLDbcpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);	
    }

    /**
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);

        if(RegisterServlet.sameOrigin(request)) {                
            String username = request.getParameter("user");
            String firstName = request.getParameter("fName");
            String middleInitial = request.getParameter("mName");
            String lastName = request.getParameter("lName");
            String password = request.getParameter("pwd");
            String confPwd = request.getParameter("confpwd");
            String email = request.getParameter("email");

            Connection con = null;
            firstName = firstName.toLowerCase();
            lastName = lastName.toLowerCase();
            middleInitial = middleInitial.toUpperCase();

            try {
                if(password.equals(confPwd)) {
                    Account toAdd = new Account.AccountBuilder()
                                        .username(username)
                                        .email(email)
                                        .password(password)
                                        .firstName(firstName)
                                        .lastName(lastName)
                                        .middleInitial(middleInitial)
                                        .build();
                    AccountModel.getInstance().addAccount(toAdd);

                    Account account = AccountModel.getInstance().getAccountByUsernameOrEmail(toAdd.getUsername());

                    this.invalidateSession();
                    this.newSession(request.getSession(true));
                    this.addToSession(Account.TABLE_NAME, account);

                    response.sendRedirect("HomePage.jsp");
                } else response.sendRedirect("register.html");
            } catch(SQLException ex) {
                Logger.getLogger(RegisterServlet.class).log(Level.SEVERE, null, ex);
            } 
        } else {
            response.sendRedirect(ACCESS_DENIED_URL);
        }
    }
}
