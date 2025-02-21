package FirstRow;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.sql.*;


public class Database {

    public static boolean connection(String pInput, String email, String usernameI, Connection con) throws Exception{

        PreparedStatement st = null;
        PreparedStatement checkEmailStmt = null;
        ResultSet rs = null;
        boolean registrato = true;


        try {
            con = Database.collegamento();

            String checkEmailQuery = "SELECT email FROM utenti WHERE email = ?";
            checkEmailStmt = con.prepareStatement(checkEmailQuery);
            checkEmailStmt.setString(1, email);
            rs = checkEmailStmt.executeQuery();

            if(rs.next()){

                Stage errorWindow = new Stage();
                StackPane errorLayout = new StackPane();
                Label erroreText = new Label();
                erroreText.setText("Email già esistente");
                errorLayout.getChildren().add(erroreText);
                Scene errorScene = new Scene(errorLayout, 300, 250);
                errorWindow.setTitle("Errore");
                errorWindow.setScene(errorScene);
                errorWindow.show();
                registrato = false;

            }

            else{

                st = con.prepareStatement("insert into utenti (email, pass, username) values (?, ?, ?)");
                st.setString(1, email);
                st.setString(2, pInput);
                st.setString(3, usernameI);
                st.executeUpdate();
                
            }

        } catch (SQLException e) {
            e.printStackTrace();
            registrato = false; 

        }



        finally {

            if (rs != null) rs.close();
            if (checkEmailStmt != null) checkEmailStmt.close();
            if (st != null) st.close();
            if (con != null) con.close();

        }
        return registrato;
    }
    /* 
    public static Connection collegamento() {
    	Connection x = null;
    	try { //jdbc:mysql://127.0.0.1:3306/?user=root  
 			x = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/AgileDB", "root", "password"); //michele ("jdbc:mysql://localhost:3306/AgileDB", "root", "MaicholZed01."), armando("jdbc:mysql://127.0.0.1:3306/firstrow", "root", "Higdrasil1!34")
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return x;
    }

    */

    public static Connection collegamento(){
		try {
			String url = System.getProperty("DATABASE_URL");  // Prende la variabile d'ambiente
			String user = System.getProperty("DATABASE_USERNAME");
			String password = System.getProperty("DATABASE_PASSWORD");
	
			if (url == null) {
				// Usa il database di produzione SOLO se non è in esecuzione un test
				url = "jdbc:mysql://127.0.0.1:3306/AgileDB";
				user = "root";
				password = "MaicholZed01.";
			}
            System.out.println(url);
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
    
}



