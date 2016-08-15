import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import web.WebConnection;

/**
 * Servlet implementation class MySQLDbcpServlet
 */
@WebServlet("/MySQLDbcpServlet")
public class MySQLDbcpServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected DataSource pool;  // Database connection pool
    protected ArrayList<Cookie> cookies;
    protected HttpSession session = null;
    protected final int expiry = 24 * 60 * 60;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("X-FRAME-OPTIONS", "DENY");
    }

    @Override
    public void init() throws ServletException {
       pool = WebConnection.getInstance().getDataSource();
       cookies = new ArrayList<>();
    }

    public void addCookieToList(String name, String value, int expiry) {	
            Cookie toAdd = new Cookie(name, value);
            toAdd.setMaxAge(expiry);   	
            cookies.add(toAdd);
    }

    public void addCookiesToResponse (HttpServletResponse response) {
         for(Cookie cookie: cookies) {
                response.addCookie(cookie);
         }
    }

    public void addToSession(HttpServletRequest request, String attributeName, Object attribute) {
         if(session == null) {
                 session = request.getSession();
         }
         session.setAttribute(attributeName, attribute);
    }

    public void invalidateSession(HttpServletRequest request) {
         request.getSession().invalidate();
    }
    
    protected void clearCookies() {
        cookies.stream().forEach((cookie) -> {
            cookie.setMaxAge(0);
        });
        
        cookies.removeAll(cookies);
    }
    
    protected boolean validateSessionId(HttpServletRequest request, String sessionId) {
        return sessionId.equals(request.getSession().getId());
    }

    public void doFilter(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        HttpServletResponse res = new HttpServletResponseWrapper(response);
        res.addHeader("X-FRAME-OPTIONS", "SAMEORIGIN");
    }
}
