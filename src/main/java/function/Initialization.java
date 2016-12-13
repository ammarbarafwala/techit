package function;

import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.naming.InitialContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class Initialization implements ServletContextListener {
	private Logger log = Logger.getLogger(this.getClass());
	
	public void contextInitialized(ServletContextEvent sce){
		// ------------- Email SMTP Setup -------------------
		final String user = "techit.csula@gmail.com";
		final String pass = ".Im0nx.W";
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.user", user);
		props.put("mail.smtp.pass", pass);
		
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, pass);
			}
		});
		
		sce.getServletContext().setAttribute("properties", props);
		sce.getServletContext().setAttribute("session", session);
		sce.getServletContext().setAttribute("email", user);
		// ------------- DataSource setup -------------------
		
		Boolean onServer = Boolean.valueOf(sce.getServletContext().getInitParameter("onServer"));
		if(onServer){
			try{
				InitialContext enc = new InitialContext();
				DataSource dataSource  = (DataSource) enc.lookup("java:comp/env/jdbc/mysqldb");
				
				if(dataSource == null){
					log.error("Datasource object lookup is null in Initialization!");
				}
				sce.getServletContext().setAttribute("dbSource", dataSource);
				
			}catch(Exception e){
				log.error("Initialization failed.", e);
				throw new RuntimeException(e);
			}
			log.debug("Initialization succeeded.");
		}
		
		sce.getServletContext().setAttribute("onServer", onServer);
		System.out.println("System Initialized!");
	}
	
	public void contextDestroyed(ServletContextEvent sce){
		
	}
}
