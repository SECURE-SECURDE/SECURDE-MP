package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public static final String ACCESS_DENIED_URL = "CSRF.html";
    public static final String TIMEOUT_URL = "login.jsp";
    
    protected DataSource pool;  // Database connection pool
    protected ArrayList<Cookie> cookies;
    protected static HttpSession session;
    protected final int expiry = 24 * 60 * 60;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doFilter(request, response);
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

    public void addToSession(String attributeName, Object attribute) {
        if(session == null)
            Logger.getLogger(MySQLDbcpServlet.class.getName()).log(Level.INFO, "Session is null");
        session.setAttribute(attributeName, attribute);
    }

    public void invalidateSession() {
        try {
            session.invalidate();
        } catch (NullPointerException | IllegalStateException ex) {
            //No session was created
        }
    }
    
    public void newSession(HttpSession session) {
        MySQLDbcpServlet.session = session;
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
        res.addHeader("X-XSS-Protection", "1");
    }
    
    public static boolean sameOrigin(HttpServletRequest request) {
        try {
            String origin = request.getHeader("Origin");
            
            Logger.getLogger(MySQLDbcpServlet.class.getName()).log(Level.INFO, origin);
            
            return origin.equals("https://192.168.1.30:8443") || origin.equals("https://localhost:8443");
        } catch (NullPointerException ex) {
            return false;
        }
    }
}
