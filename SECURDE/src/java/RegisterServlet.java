
import java.io.IOException;
import java.sql.*;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                super.doPost(request, response);
                                
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
				
				int accountID = AccountModel.getInstance().getAccountByUsernameOrEmail(toAdd.getUsername()).getID();

				addCookieToList(Account.ACCOUNT_ID, String.valueOf(accountID), expiry);
				addCookiesToResponse(response);
                                
				response.sendRedirect("https://localhost:8443/SECURDE/HomePage.jsp");
			} else response.sendRedirect("https://localhost:8443/SECURDE/Register.html");
		} catch(SQLException ex) {
			
			ex.printStackTrace();
		} finally {
			try{
				if(con != null)
					con.close();
			} catch(SQLException ex) {

			}
		}
	}
}
