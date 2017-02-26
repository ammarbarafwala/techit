package techit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;

/**
 * Sample program how to use ActiveDirectory class in Java
 * 
 * @filename SampleUsageActiveDirectory.java
 * @author <a href="mailto:jeeva@myjeeva.com">Jeevanandam Madanagopal</a>
 * @copyright &copy; 2010-2012 www.myjeeva.com
 */
public class SampleUsageActiveDirectory {

	/**
	 * @param args
	 * @throws NamingException 
	 */
	public static void main(String[] args) throws NamingException, IOException {
		System.out.println("\n\nQuerying Active Directory Using Java");
		System.out.println("------------------------------------");
		String domain = "";
		String username = "";
		String password = "";
		String choice = "";
		String searchTerm = "";
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Provide username & password for connecting AD");
		System.out.println("Enter Domain:");			
		domain = br.readLine();
		System.out.println("Enter username:");			
		username = br.readLine();			
		System.out.println("Enter password:");
		password = br.readLine();
		System.out.println("Search by username or email:");
		choice = br.readLine();
		System.out.println("Enter search term:");
		searchTerm = br.readLine();
		
		//Creating instance of ActiveDirectory
        ActiveDirectory activeDirectory = new ActiveDirectory(username, password, domain);
        
        //Searching
        NamingEnumeration<SearchResult> result = activeDirectory.searchUser(searchTerm, choice, null);
        
        if(result.hasMore()) {
			SearchResult rs= (SearchResult)result.next();
			Attributes attrs = rs.getAttributes();
			String temp = attrs.get("samaccountname").toString();
			System.out.println("Username	: " + temp.substring(temp.indexOf(":")+1));
			temp = attrs.get("givenname").toString();
			System.out.println("Name         : " + temp.substring(temp.indexOf(":")+1));
			temp = attrs.get("mail").toString();
			System.out.println("Email ID	: " + temp.substring(temp.indexOf(":")+1));
			temp = attrs.get("cn").toString();
			System.out.println("Display Name : " + temp.substring(temp.indexOf(":")+1) + "\n\n"); 
		} else  {
			System.out.println("No search result found!");
		}

		//Closing LDAP Connection
        activeDirectory.closeLdapConnection();
	}
}
