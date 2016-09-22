package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.User;


@WebServlet(urlPatterns="/Login", loadOnStartup = 1)
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public Login() {
        super();
        
        // TODO Auto-generated constructor stub
    }
    


 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	 	System.out.println("hello");
		request.getRequestDispatcher("/WEB-INF/Login.jsp").forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Byebye");
		String user = request.getParameter("username");
		String password = request.getParameter("password");


		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Connection c = null;
		try
		{
			String url = "jdbc:mysql://cs3.calstatela.edu/cs4961stu05";
			String db_user = "cs4961stu05";
			String db_pass = ".Im0nx.W";
			
			c = DriverManager.getConnection(url, db_user, db_pass);
			
			String search_user = "select * from users where username = ?";
            PreparedStatement pstmt = c.prepareStatement( search_user );
            pstmt.setString( 1, user );
            ResultSet rs = pstmt.executeQuery();
            
            if(rs.next())
            {
    			if(rs.getString("pass").equals(org.apache.commons.codec.digest.DigestUtils.sha256Hex(password)))
    			{
/*    				User userSession = new User(rs.getInt("id"),
    						rs.getString("firstname"),
    						rs.getString("lastname"),
    						user,
    						org.apache.commons.codec.digest.DigestUtils.sha256Hex(password),
    						rs.getInt("CIN"),
    						rs.getInt("position"),
    						rs.getInt("unit_id"),
    						rs.getInt("superviser_id"),
    						rs.getBoolean("is_supervisor")
    						);*/
    				
    				/* Not sure if I should use a user object as attribute in the session or
    				 * to set multiple attributes one like below
    				 */
    				
    				request.getSession().setAttribute("user", user);
    				request.getSession().setAttribute("firstname", rs.getString("firstname"));
    				request.getSession().setAttribute("lastname", rs.getString("lastname"));
    				request.getSession().setAttribute("CIN", rs.getInt("CIN"));
    				request.getSession().setAttribute("unit_id", rs.getInt("unit_id"));
    				request.getSession().setAttribute("supervisor_id", rs.getInt("supervisor_id"));
    				request.getSession().setAttribute("is_supervisor", rs.getBoolean("is_supervisor"));
    			}
    			else
    			{
        			c.close();
        			request.setAttribute("errorMessage", "Invalid username or password, please try again!");
        			request.getRequestDispatcher("/WEB-INF/Login.jsp").forward(request, response);
    			}
            }
            else
            {
            	/* If the person is not in our database, we need to check 
            	 * the active directory's database for them.
            	 * However, I haven't understand completely what the AD does so
            	 * right now this is just returns an error. 
            	 */
            	c.close();
    			request.setAttribute("errorMessage", "Invalid username or password, please try again!");
    			request.getRequestDispatcher("/WEB-INF/Login.jsp").forward(request, response);
            }
            
		}
        catch( SQLException e )
        {
            throw new ServletException( e );
        }
        finally
        {
            try
            {
            	System.out.println("test");	// Just a test to see if this goes through before redirect
                if( c != null ) c.close();
            }
            catch( SQLException e )
            {
                throw new ServletException( e );
            }
        }

		request.getRequestDispatcher("/WEB-INF/Home.jsp").forward(request, response);


	}

}
