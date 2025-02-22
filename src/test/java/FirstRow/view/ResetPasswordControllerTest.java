package FirstRow.view;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobotException;
import org.testfx.util.WaitForAsyncUtils;
import FirstRow.Database;
import FirstRow.TestFXBase;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;



public class ResetPasswordControllerTest extends TestFXBase {

    //final String  NOME_CAMPO_ID = "#FXID";
    final String MAIL_FIELD_ID = "#Email";
    final String OLD_PASSWORD_FIELD_ID = "#OldPassword"; 
    final String NEW_PASSWORD_FIELD_ID = "#NewPassword";
    final String NEW_PASSWORD_2_FIELD_ID = "#NewPassword2";
    final String RESET_PW_BUTTON_ID = "#ResetPwButton";
    final String RETURN_LOGIN_BUTTON_ID ="#buttonReturnLogin"; //bottone per tornare al login dalla schermata ResetPassword.fxml
    final String RESET_SUCCESS_BUTTON_ID = "#ResetSuccessButton"; //bottone per tornare al log in in ResetConSuccesso.fxml
    final String ERROR_MEX_LABEL_ID= "#MessaggioErrore"; //label messaggio di errore

    ResetPasswordController resetPwController;



    //carica la pagina di reset
   /* @Override
    public void start(Stage stage) throws Exception {
        System.out.println("Entrato nel metodo start()");
          
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ResetPassword.fxml"));
        Parent rootReset = loader.load();
    
        resetPwController = loader.getController();
        resetPwController.setStage(stage);
        Scene scenaReset = new Scene(rootReset);
        stage.setScene(scenaReset);
        stage.show();
    }*/
    
    @Override
    public void start (Stage stage) throws Exception{
    	System.out.println("entrato finalemnte");
    	FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ResetPassword.fxml"));
        Parent rootReset = loader.load();
    
        resetPwController = loader.getController();
        resetPwController.setStage(stage);
        Scene scenaReset = new Scene(rootReset);
        stage.setScene(scenaReset);
        stage.show();
    }
    
    @BeforeAll
	static void setUp() throws Exception {     //jdbc:mysql://127.0.0.1:3307/?user=testuser    jdbc:mysql://localhost:3306/?user=root  jdbc:mysql://127.0.0.1:3306/testdb
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
								"categoria VARCHAR(255),\n"+ //                                       "\n" + //
								" scadenza DATE\n," + //
								" priorita VARCHAR(50),\n" + //
								" completato BOOL NOT NULL DEFAULT FALSE\n" + //
								");");
					

			}catch (SQLException e) {
				e.printStackTrace();
			}
            
		}

        @BeforeEach
         void user_create(){
            try {
			    //Thread.sleep(10000);
			    Connection con = Database.collegamento();
			    Statement stmt = con.createStatement();
                stmt.executeUpdate("INSERT INTO utenti (username,email,pass) values ('Mino','Dedalo@gmail.com','Tauro');");
								
			    stmt.close();
			    con.close();	
		    } catch (SQLException e) {
			    e.printStackTrace();
		    }
        }

        @AfterEach
        void user_delete(){
            try {
			    //Thread.sleep(10000);
			    Connection con = Database.collegamento();
			    Statement stmt = con.createStatement();
                stmt.executeUpdate("DELETE FROM utenti WHERE email = 'Dedalo@gmail.com';");
								
			    stmt.close();
			    con.close();	
		    } catch (SQLException e) {
			    e.printStackTrace();
		    }
        }

        @AfterAll
	    static void tearDown() {
		try {
			Connection con = Database.collegamento();
			Statement stmt = con.createStatement();
			stmt.executeUpdate("drop database testdb;");
			//stmt.executeUpdate("drop database testdb;");
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.clearProperty("DATABASE_URL");
    	System.clearProperty("DATABASE_USERNAME");
    	System.clearProperty("DATABASE_PASSWORD");

	}

    

    

    @Test
    public void testClickOnNonExistentElement() {
    Exception exception = assertThrows(FxRobotException.class, () -> {
        clickOn("#slkdjfkasd"); // Prova a cliccare su un elemento inesistente
    });

    // Stampa il messaggio effettivo per debug
    System.out.println("?? Messaggio dell'eccezione: " + exception.getMessage());

    // Verifica che il messaggio sia quello corretto
    assertTrue(exception.getMessage().contains("the query \"#slkdjfkasd\" returned no nodes"),
               "Errore inatteso nel messaggio dell'eccezione: " + exception.getMessage());
}


    
    @Test 
	public void ResetErrato_utenteNonEsistente_resetPassword() {
       //se l'email non esiste, l'utente non deve cambiare pagina -> il test passa

        //per accedere alle variabili e ai metodi del controller per verificare i risultati
        ResetPasswordController r = resetPwController;
        
        // Pulizia iniziale dei campi di input
        clearOutImputFields(0,0,0,0);

        //inserimento dei dati per il test
        clickOn(MAIL_FIELD_ID).write("mailNonEsistente"); //mail che non eisste nel db
        clickOn(OLD_PASSWORD_FIELD_ID).write("pwQualunque"); //pw qualunque
        clickOn(NEW_PASSWORD_FIELD_ID).write("newPwQualunque"); //pw qualunque
        clickOn(NEW_PASSWORD_2_FIELD_ID).write("newPwQualunque"); //pw qualunque
        clickOn(RESET_PW_BUTTON_ID);
        sleep(100);

        // Verifica che l'utente NON venga spostato su un'altra schermata
        assertFalse(r.isResetSuccessful(), "L'utente non dovrebbe cambiare scena se la mail non esiste nel db!");
        //assertFalse(condizione, messaggio) ->se la condizione è false, il test passa; se è true, stampa il messaggio

        
        // Verifica che il messaggio di errore sia corretto        
        Label errorLabel = lookup(ERROR_MEX_LABEL_ID).queryAs(Label.class);
        assertEquals("Utente non esistente o vecchia password errata", errorLabel.getText(),
                "riconosce una mail che non è nel database");
     
        //pulisco i campi 
        clearOutImputFields("mailNonEsistente".length(), "pwQualunque".length(), "newPwQualunque".length(), "newPwQualunque".length());
    }
    
    @Test 
	public void ResetErrato_vecchiaPwErrata_resetPassword() {
        //inserisce una mail esistente ma la VECCHIA password è errata 

        ResetPasswordController r = resetPwController;
        clearOutImputFields(0,0,0,0);
        //inserimento dei dati per il test
        clickOn(MAIL_FIELD_ID).write("Dedalo@gmail.com"); //mail esistente del db
        clickOn(OLD_PASSWORD_FIELD_ID).write("=!tauro"); //pw =!tauro
        clickOn(NEW_PASSWORD_FIELD_ID).write("newPwQualunque"); //pw qualunque
        clickOn(NEW_PASSWORD_2_FIELD_ID).write("newPwQualunque"); //pw qualunque
        clickOn(RESET_PW_BUTTON_ID);
        sleep(100);

        // Verifica che l'utente NON venga spostato su un'altra schermata
        assertFalse(r.isResetSuccessful(), "L'utente non dovrebbe cambiare scena se la mail esiste ma la VECCHIA password è errata !");

        // Verifica che il messaggio di errore sia corretto        
        Label errorLabel = lookup(ERROR_MEX_LABEL_ID).queryAs(Label.class);
        assertEquals("Utente non esistente o vecchia password errata", errorLabel.getText(),
                "riconosce una password errata per un email che è nel database");


        clearOutImputFields("Dedalo@gmail.com".length(), "=!tauro".length(), "newPwQualunque".length(), "newPwQualunque".length());
        

    }

    @Test 
	public void ResetErrato_campiNewPwVuoti_resetPassword() {
        //inserisce una mail esistente con la vecchia password corretta, ma i campi new e new2 sono vuoti  
        ResetPasswordController r = resetPwController;
        clearOutImputFields(0,0,0,0);
        //inserimento dei dati per il test
        clickOn(MAIL_FIELD_ID).write("Dedalo@gmail.com"); //mail esistente del db
        clickOn(OLD_PASSWORD_FIELD_ID).write("tauro"); //pw corretta 
        clickOn(NEW_PASSWORD_FIELD_ID).write(""); //campo new pw vuoto
        clickOn(NEW_PASSWORD_2_FIELD_ID).write(""); //campo new2 pw vuoto
        clickOn(RESET_PW_BUTTON_ID);
        sleep(100);

        // Verifica che l'utente NON venga spostato su un'altra schermata
        assertFalse(r.isResetSuccessful(), "L'utente non dovrebbe cambiare scena se i campi new e new2 sono vuoti!");

        // Verifica che il messaggio di errore sia corretto        
        Label errorLabel = lookup(ERROR_MEX_LABEL_ID).queryAs(Label.class);
        assertEquals("Devi scrivere la nuova password!", errorLabel.getText(),
                "resetta la password se i campi new e new2 sono vuoti");

        clearOutImputFields("Dedalo@gmail.com".length(), "tauro".length(), 0, 0);

    }

    @Test 
	public void ResetErrato_campiNewPwDiversi_resetPassword() {
        //inserisce una mail esistente con la vecchia password corretta, ma i campi new e new2 sono diversi 
        ResetPasswordController r = resetPwController;
        clearOutImputFields(0,0,0,0);
        //inserimento dei dati per il test
        clickOn(MAIL_FIELD_ID).write("Dedalo@gmail.com"); //mail esistente del db
        clickOn(OLD_PASSWORD_FIELD_ID).write("tauro"); //pw corretta 
        clickOn(NEW_PASSWORD_FIELD_ID).write("ciao"); //campo new pw 
        clickOn(NEW_PASSWORD_2_FIELD_ID).write("12345"); //campo new2 pw diverso
        clickOn(RESET_PW_BUTTON_ID);
        sleep(100);

        // Verifica che l'utente NON venga spostato su un'altra schermata
        assertFalse(r.isResetSuccessful(), "L'utente non dovrebbe cambiare scena se i campi new e new2 sono diversi!");

        // Verifica che il messaggio di errore sia corretto        
        Label errorLabel = lookup(ERROR_MEX_LABEL_ID).queryAs(Label.class);
        assertEquals("La nuova password non è ripetuta correttamente", errorLabel.getText(),
                "resetta la password se i campi new e new2 sono diversi");

        clearOutImputFields("Dedalo@gmail.com".length(), "tauro".length(), "ciao".length(), "12345".length());

    }

    @Test 
	public void ResetErrato_campoNewPienoNew2Vuoto_resetPassword() {
        //inserisce una mail esistente con la vecchia password corretta, ma il campo new è pieno e new2 è vuoto
        ResetPasswordController r = resetPwController;
        clearOutImputFields(0,0,0,0);
        //inserimento dei dati per il test
        clickOn(MAIL_FIELD_ID).write("Dedalo@gmail.com"); //mail esistente del db
        clickOn(OLD_PASSWORD_FIELD_ID).write("tauro"); //pw corretta 
        clickOn(NEW_PASSWORD_FIELD_ID).write("ciao"); //campo new pw pieno
        clickOn(NEW_PASSWORD_2_FIELD_ID).write(""); //campo new2 vuoto
        clickOn(RESET_PW_BUTTON_ID);
        sleep(100);

        // Verifica che l'utente NON venga spostato su un'altra schermata
        assertFalse(r.isResetSuccessful(), "L'utente non dovrebbe cambiare scena se il campo new è pieno e new2 è vuoto!");

        // Verifica che il messaggio di errore sia corretto        
        Label errorLabel = lookup(ERROR_MEX_LABEL_ID).queryAs(Label.class);
        assertEquals("La nuova password non è ripetuta correttamente", errorLabel.getText(),
                "resetta la password se il campo new è pieno e new2 è vuoto");

        clearOutImputFields("Dedalo@gmail.com".length(), "tauro".length(), "ciao".length(), "".length());

    }

    @Test 
	public void ResetErrato_campoNewVuotoNew2Pieno_resetPassword() {
        //inserisce una mail esistente con la vecchia password corretta, ma il campo new è vuoto e new2 è pieno
        ResetPasswordController r = resetPwController;
        clearOutImputFields(0,0,0,0);
        //inserimento dei dati per il test
        clickOn(MAIL_FIELD_ID).write("Dedalo@gmail.com"); //mail esistente del db
        clickOn(OLD_PASSWORD_FIELD_ID).write("tauro"); //pw corretta 
        clickOn(NEW_PASSWORD_FIELD_ID).write(""); //campo new pw vuoto
        clickOn(NEW_PASSWORD_2_FIELD_ID).write("12345"); //campo new2 pieno
        clickOn(RESET_PW_BUTTON_ID);
        sleep(100);

        // Verifica che l'utente NON venga spostato su un'altra schermata
        assertFalse(r.isResetSuccessful(), "L'utente non dovrebbe cambiare scena se il campo new è vuoto e new2 è pieno!");

        // Verifica che il messaggio di errore sia corretto        
        Label errorLabel = lookup(ERROR_MEX_LABEL_ID).queryAs(Label.class);
        assertEquals("La nuova password non è ripetuta correttamente", errorLabel.getText(),
                "resetta la password se il campo new è vuoto e new2 è pieno");

        clearOutImputFields("Dedalo@gmail.com".length(), "tauro".length(), "".length(), "12345".length());

    }



    @Test 
	public void ResetConSuccesso_resetPassword() {
        //inserisce una mail esistente con la vecchia password corretta, e i campi new e new2 sono uguali
        ResetPasswordController r = resetPwController;
        clearOutImputFields(0,0,0,0);
        //inserimento dei dati per il test
        clickOn(MAIL_FIELD_ID).write("Dedalo@gmail.com"); //mail esistente del db
        clickOn(OLD_PASSWORD_FIELD_ID).write("tauro"); //pw corretta 
        clickOn(NEW_PASSWORD_FIELD_ID).write("miao"); //campo new pw 
        clickOn(NEW_PASSWORD_2_FIELD_ID).write("miao"); //campo new2 uguale
        clickOn(RESET_PW_BUTTON_ID);
        sleep(100);

        clickOn(RESET_SUCCESS_BUTTON_ID);
        sleep(500);

        try {
					
            Connection con = Database.collegamento();
            Statement stmt = con.createStatement();
            ResultSet rs =stmt.executeQuery("SELECT pass FROM utenti WHERE email = 'Dedalo@gmail.com' ");
            rs.next();
            assertEquals(rs.getString("pass"), "miao", "la password non è stata aggiornata nel db dopo il reset" );

            }catch (SQLException e) {
            e.printStackTrace();
            }
    
    }


        
        
    
    

    //metodo per pulire i campi di testo di esattamente la lunghezza del testo scritto
    private void clearOutImputFields(int x,int y, int z, int k) {
		clickOn(MAIL_FIELD_ID).eraseText(x);
		clickOn(OLD_PASSWORD_FIELD_ID).eraseText(y);
        clickOn(NEW_PASSWORD_FIELD_ID).eraseText(z);
		clickOn(NEW_PASSWORD_2_FIELD_ID).eraseText(k);
        
	}
}


