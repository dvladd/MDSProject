

import java.sql.*;

/**
 *
 * @author Vlad
 */
public class DataBase {
   static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";  
   static final String DB_URL = "jdbc:oracle:thin:@5.12.135.96:1521:XE";
   static final String USER = "system";
   static final String PASS = "bd12345";
   
    int isUser(String username, String password) throws ClassNotFoundException, SQLException{ 
	   int number = -1;
	   Class.forName("oracle.jdbc.driver.OracleDriver");
	   System.out.println("Connecting to database...");
	   Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
	   Statement stmt = conn.createStatement();
	   String sql = "Select count(*) from useri where username='"+username+"' and password='"+password+"' ";
	   ResultSet rs = stmt.executeQuery(sql);
	   while(rs.next())
		   number = rs.getInt("count(*)");
	   if(number == 0){
		   sql = "Select count(*) from useri where username='"+username+"'";
		   rs = stmt.executeQuery(sql);
		   while(rs.next())
			   number = rs.getInt("count(*)");
		   if(number>0) System.out.println("Parola gresita");
		   else System.out.println("Username si parola gresita");
		   }
           if(number == 1)
               { sql = "update useri set connected = 1 where username='"+username+"' and password='"+password+"'";
                 stmt.executeUpdate(sql);
                 System.out.println("Connected!");
               }
      rs.close();
	   stmt.close();
	   conn.close();
	   return number;
   }
   
    void addUser(String username, String password, String nickname) throws ClassNotFoundException, SQLException{  
	   Class.forName("oracle.jdbc.driver.OracleDriver");
	   System.out.println("Connecting to database...");
	   Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
	   Statement stmt = conn.createStatement();
	   String sql = "insert into useri values('"+username+"','"+password+"','"+nickname+"','"+0+"')";
	   stmt.executeUpdate(sql);
           System.out.println("User added!");
           stmt.close();
           conn.close();
   }
}