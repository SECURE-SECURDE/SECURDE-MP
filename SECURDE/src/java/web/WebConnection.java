package web;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.sql.DataSource;	

public class WebConnection {
	private DataSource pool;
	private static WebConnection instance = null;

	private WebConnection() throws ServletException {
		try {
	        // Create a JNDI Initial context to be able to lookup the DataSource
	        InitialContext ctx = new InitialContext();
	        // Lookup the DataSource, which will be backed by a pool
	        //   that the application server provides.
	        pool = (DataSource)ctx.lookup("java:comp/env/jdbc/securde");
	        if (pool == null)
	           throw new ServletException("Unknown DataSource 'jdbc/securde'");
	    } catch (NamingException ex) {
	    }
	}

	public static WebConnection getInstance() throws ServletException {
		if(instance == null) {
			instance = new WebConnection();
		}
		return instance;
	}

	public DataSource getDataSource() {
		return pool;
	}
}