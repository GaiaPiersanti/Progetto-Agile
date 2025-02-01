package FirstRow;

import FirstRow.*;

import static org.junit.Assert.assertTrue;
import java.sql.Connection;
import java.sql.DriverManager;
import org.junit.Test;
import junit.*;

public class ConnectionTest  {


    static String url = "jdbc:mysql://localhost:3306/AgileDB";
    static String uname = "root";
    static String pass = "MaicholZed01.";

    @Test 
    void testConnesione() throws Exception {

        String email = "pippoforeal@gmail.com";
        String password = "Pippo.";
        String username = "PippoElForte";

        Connection con = DriverManager.getConnection(url, uname, pass);

        try{

            Boolean test = Database.connection(email, password, username, con);
            assertTrue(test);
            

    } catch (Exception e) {

        } finally {

            con.close();
        }

    }
}







