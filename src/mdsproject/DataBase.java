package mdsproject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

public class DataBase {

    static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    static final String DB_URL = "jdbc:oracle:thin:@188.25.173.11:1521:XE";
    static final String USER = "system";
    static final String PASS = "bd12345";

    public int isUser(String username, String password) throws ClassNotFoundException, SQLException {
        int number = -1;
        Class.forName("oracle.jdbc.driver.OracleDriver");
        System.out.println("Connecting to database...");
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

        Statement stmt = conn.createStatement();
        String sql = "Select count(*) from useri where username='" + username + "' and password='" + password + "' ";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            number = rs.getInt("count(*)");
        }
        if (number == 0) {
            sql = "Select count(*) from useri where username='" + username + "'";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                number = rs.getInt("count(*)");
            }
            if (number > 0) {
                System.out.println("Parola gresita");
            } else {
                System.out.println("Username si parola gresita");
            }
        }
        if (number == 1) {
            sql = "update useri set connected = 1 where username='" + username + "' and password='" + password + "'";
            stmt.executeUpdate(sql);
            System.out.println("Connected!");
        }
        rs.close();
        stmt.close();
        conn.close();
        return number;
    }

    public int addUser(String username, String password, String nickname) throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        System.out.println("Connecting to database...");
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement stmt = conn.createStatement();
        String sql = "insert into useri values('" + username + "','" + password + "','" + nickname + "','" + 0 + "')";
        int rez = stmt.executeUpdate(sql);
        System.out.println("User added!");
        stmt.close();
        conn.close();
        return rez;
    }

    public ArrayList<String> getUsers(String current_user) throws ClassNotFoundException, SQLException {
        ArrayList<String> lista = new ArrayList<String>();
        Class.forName("oracle.jdbc.driver.OracleDriver");
        System.out.println("Connecting to database...");
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement stmt = conn.createStatement();
        String sql = "select username from useri where username != '" + current_user + "'";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            String user = rs.getString("username");
            lista.add(user);
        }
        rs.close();
        stmt.close();
        conn.close();
        return lista;
    }

    public int sendMessage(String sender, String receiver, String text) throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        System.out.println("Connecting to database...");
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement stmt = conn.createStatement();
        String sql = "insert into mesaje values((select count(*) from mesaje)+1,'" + sender + "','" + receiver + "',to_char(sysdate,'DD/MM/YYYY HH24:MI:SS'),'" + text + "')";
        int rez = stmt.executeUpdate(sql);
        stmt.close();
        conn.close();
        return rez;
    }

    public String getMessages(String sender, String receiver) throws ClassNotFoundException, SQLException {
        String conversation = new String();
        Class.forName("oracle.jdbc.driver.OracleDriver");
        System.out.println("Connecting to database...");
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement stmt = conn.createStatement();
        String sql = "select sender, text_date, text from mesaje where (sender='" + sender + "' and receiver='" + receiver + "') or (sender='" + receiver + "' and receiver='" + sender + "')";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            String current_sender = rs.getString("sender");
            String text_date = rs.getString("text_date");
            String text = rs.getString("text");
            conversation = conversation + current_sender + "(" + text_date + "):  " + text + "\n";
        }
        rs.close();
        stmt.close();
        conn.close();
        return conversation;
    }

    public void creaza_txt(String sender, String receiver) throws ClassNotFoundException, SQLException
   {
       try{
    PrintWriter writer = new PrintWriter("FisierCreat"+sender+receiver+".txt", "UTF-8");
    Class.forName("oracle.jdbc.driver.OracleDriver");
	   System.out.println("Connecting to database...");
	   Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
	   Statement stmt = conn.createStatement();
	   String sql = "select sender, text_date, text from mesaje where (sender='"+sender+"' and receiver='"+receiver+"') or (sender='"+receiver+"' and receiver='"+sender+"')";
	   ResultSet rs = stmt.executeQuery(sql);
	   while(rs.next()){
		   String current_sender = rs.getString("sender");
		   String text_date = rs.getString("text_date");
		   String text = rs.getString("text");
		    writer.println(current_sender + "(" + text_date +"):  " + text + "\n"); 
	   }
           
           
           writer.close();
	   rs.close();
	   stmt.close();
	   conn.close();
    
} catch (IOException e) {
   // do something
}
       
   }
}
