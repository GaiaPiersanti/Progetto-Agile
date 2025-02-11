package FirstRow;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.sql.*;


public class Database {


    public static void removeD(Connection con) throws SQLException{

        Statement st = null;
        ResultSet rs = null;
        String query2 = "delete from utenti";
        String query3 = "SET SQL_SAFE_UPDATES = 0;";

        try {
            st = con.createStatement();
            st.executeUpdate(query3);
            st.executeUpdate(query2);

            if(rs.next()){
                System.out.println(rs.getString("email"));
            }


        } catch (Exception e) {
            // TODO: handle exception
        }
        finally {
            if (rs != null){rs.close();}
            if (st != null){ st.close();};
        }


    }

    public static boolean connection(String pInput, String email, String usernameI, Connection con) throws Exception{

        String url = "jdbc:mysql://localhost:3306/AgileDB";
        String uname = "root";
        String pass = "MaicholZed01.";
        PreparedStatement st = null;
        PreparedStatement checkEmailStmt = null;
        ResultSet rs = null;


        try {
            con = DriverManager.getConnection(url, uname, pass);

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

            }

            else{
                removeD(con);
                st = con.prepareStatement("insert into utenti (email, pass, username) values (?, ?, ?)");
                st.setString(1, email);
                st.setString(2, pInput);
                st.setString(3, usernameI);
                st.executeUpdate();
                
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }



        finally {

            st.close();
            con.close();
        }
        return true;
    }

    public static Connection collegamento() {
    	Connection x = null;
    	try {
			x = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/firstrow", "root", "Higdrasil1!34"); //michele ("jdbc:mysql://localhost:3306/AgileDB", "root", "MaicholZed01."), armando("jdbc:mysql://127.0.0.1:3306/firstrow", "root", "Higdrasil1!34")
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return x;
    }
    
}



