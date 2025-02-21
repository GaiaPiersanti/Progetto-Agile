package FirstRow.view;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;
import FirstRow.Database;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

class PaginaIControllerTest extends ApplicationTest {
    final String USERNAME_FIELD_ID = "#uInput";
    final String EMAIL_FIELD_ID = "#emailInput";
    final String PASSWORD_FIELD_ID = "#pInput";
    final String REGISTER_BUTTON_ID = "#bRegistrazione";

    PaginaIController paginaIController;

    @BeforeAll
    static void setUp() {     //jdbc:mysql://127.0.0.1:3307/?user=testuser    jdbc:mysql://localhost:3306/?user=root  jdbc:mysql://127.0.0.1:3306/testdb
      
		System.setProperty("DATABASE_URL", "jdbc:mysql://localhost:3306/?user=root");
    	System.setProperty("DATABASE_USERNAME", "root");
    	System.setProperty("DATABASE_PASSWORD", "password"); //password , MaicholZed01.
		try {
			//Thread.sleep(10000);
			Connection con = Database.collegamento();
			Statement stmt = con.createStatement();
			stmt.executeUpdate("create database if not exists testdb;\n" );
								
			stmt.close();
			con.close();	
		} catch (SQLException e) {
			e.printStackTrace();
		} //catch (InterruptedException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				//}
				System.setProperty("DATABASE_URL", "jdbc:mysql://localhost:3306/testdb");
				System.setProperty("DATABASE_USERNAME", "root");
				System.setProperty("DATABASE_PASSWORD", "password");
	
				try {
					
					Connection con = Database.collegamento();
					Statement stmt = con.createStatement();
					stmt.executeUpdate(
								"create table if not exists utenti (\n" + //
								"\tID integer unsigned auto_increment primary key,\n" + //
								"\tusername char(25) not null,\n" + //
								"    email char(100) not null unique,\n" + //
								"    pass char(25) not null\n" + //
								");");
          stmt.executeUpdate(
                "create table if not exists attivita (\n" + //
                "\tid INT AUTO_INCREMENT PRIMARY KEY,\n" + //
                "\tnome VARCHAR(255) NOT NULL,\n" + //
                "categoria VARCHAR(255),\n"+ //                                      
                " scadenza DATE\n," + //
                " priorita VARCHAR(50),\n" + //
                " completato BOOL NOT NULL DEFAULT FALSE\n" + //
                ");");
					stmt.executeUpdate("INSERT INTO utenti (username,email,pass) values ('Mino','Dedalo@gmail.com','Tauro');");

			}catch (SQLException e) {
				e.printStackTrace();
			}

		}

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("PaginaIniziale.fxml"));
        Parent root = loader.load();
        paginaIController = loader.getController();
        paginaIController.setStage(stage);
        Scene scene = new Scene(root, 700, 500);
        stage.setScene(scene);
        stage.setMinWidth(700);
        stage.setMinHeight(500);
        stage.show();
    }

    @Test
    void registrazioneCorretta() {
        clearInputFields(0, 0, 0);
        clickOn(USERNAME_FIELD_ID).write("NuovoUtente");
        clickOn(EMAIL_FIELD_ID).write("nuovoemail@gmail.com");
        clickOn(PASSWORD_FIELD_ID).write("password123");
        clickOn(REGISTER_BUTTON_ID);
        WaitForAsyncUtils.waitForFxEvents();

        // Verifica che l'utente sia stato correttamente registrato nel database
        boolean utenteRegistrato = false;
        String email = "nuovoemail@gmail.com";

        try (Connection con = Database.collegamento();
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM utenti WHERE email = ?")) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                utenteRegistrato = rs.next(); // Se trova un record, l'utente è stato registrato
            }
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Errore di connessione al database durante il controllo della registrazione");
    }

    // Assicurati che l'utente sia stato registrato nel database
    assertTrue(utenteRegistrato, "L'utente non è stato registrato correttamente nel database");
    }

    @Test
    void registrazioneFallita_EmailEsistente() {

        clearInputFields(0, 0, 0);
        clickOn(USERNAME_FIELD_ID).write("Mino");
        clickOn(EMAIL_FIELD_ID).write("Dedalo@gmail.com"); // Email già esistente
        clickOn(PASSWORD_FIELD_ID).write("Tauro");
        clickOn(REGISTER_BUTTON_ID);
		sleep(100);

        // Verifica direttamente nel database
        boolean utenteEsistente = false;
        try (Connection con = Database.collegamento();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM utenti WHERE email = 'Dedalo@gmail.com'")) {
            utenteEsistente = rs.next(); // Se trova un record, l'utente esiste già
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assertTrue(utenteEsistente, "L'email è già registrata");
    }


    @AfterAll
    static void tearDown() {
        try {
            Connection con = Database.collegamento();
            Statement stmt = con.createStatement();
            stmt.executeUpdate("DROP DATABASE testdb;");
            stmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.clearProperty("DATABASE_URL");
        System.clearProperty("DATABASE_USERNAME");
        System.clearProperty("DATABASE_PASSWORD");
    }

    

    private void clearInputFields(int userLength, int emailLength, int passLength) {
        clickOn(USERNAME_FIELD_ID).eraseText(userLength);
        clickOn(EMAIL_FIELD_ID).eraseText(emailLength);
        clickOn(PASSWORD_FIELD_ID).eraseText(passLength);
    }
}
