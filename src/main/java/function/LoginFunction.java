package function;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import model.User;
/*
 * This class is meant to check if the user exists in our database. The class has a private 
 * user variable. If the account exists under that username, the information 
 * will be saved into the user variable and it will check against the inputted password to see 
 * if it is correct. 
 * 
 */

public class LoginFunction {
	private User user;
	
	public LoginFunction(){
		this.user = null;
	}
	
	public int checkSystemAccount(String user, String password) throws SQLException 
	{
		/* This method checks the input information with the one in the database.
		 * The method returns one of the following:
		 * 		0 -- User doesn't exist in the database
		 * 		1 -- User exists but incorrect password
		 * 		2 -- User exists, password is correct, user information is saved
		 * 				into the LoginFunction class variable
		 */
		
		
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
			
			String search_user = "select * from user where username = ?";
            PreparedStatement pstmt = c.prepareStatement( search_user );
            pstmt.setString( 1, user );
            ResultSet rs = pstmt.executeQuery();
            if(rs.next())
            {
            	if(rs.getString("pass").equals(org.apache.commons.codec.digest.DigestUtils.sha256Hex(password)))
            	{
            		int id = rs.getInt("id");
            		String firstName = rs.getString("firstname");
            		String lastName = rs.getString("lastname");
            		int position = rs.getInt("position");
            		int unitId = rs.getInt("unit_id");
            		
            		
            		this.user = new User(id,
            				firstName,
            				lastName,
            				user,
            				position,
            				unitId);
            		
            		c.close();
            		return 2;
            	}
            	else
            	{
            		c.close();
            		return 1;	// Incorrect password
            		
            	}
            }
            else{
            	c.close();
            	return 0;	 // User does not exist in the system
            }
            
		}
		finally{
			if(c != null){
				c.close();
			}
		}
	}
	
	public User getSystemAccount(){
		return user;
	}
}
