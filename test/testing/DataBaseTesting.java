package testing;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import mdsproject.DataBase;

public class DataBaseTesting 
{

    public DataBaseTesting() 
    {

    }

    @Test
    public void testIsUser() throws ClassNotFoundException, SQLException {
        DataBase myBD = new DataBase();
        assertEquals(0, myBD.isUser("abc", "abc"));
    }

    @Test
    public void testGetUsers() throws SQLException, ClassNotFoundException {
        DataBase myBD = new DataBase();
        assertNotNull(myBD.getUsers("Vlad"));
        int size = myBD.getUsers("Vlad").size();
        assertEquals(3, size);
    }

    @Test
    public void testGetMessages() throws ClassNotFoundException, SQLException {
        DataBase myBD = new DataBase();
        assertEquals(0, myBD.getMessages("Cristi", "Vlad").length());
    }

    @Test(expected = SQLException.class)
    public void testAddUser() throws SQLException, ClassNotFoundException {
        DataBase myBD = new DataBase();
        assertEquals(0, myBD.addUser("Cristi", "Test", null));
    }

    @Test(expected = SQLException.class)
    public void testSendMessage() throws SQLException, ClassNotFoundException {
        DataBase myBD = new DataBase();
        assertEquals(0, myBD.sendMessage("Cristi", "Error", "salut"));
    }

}
