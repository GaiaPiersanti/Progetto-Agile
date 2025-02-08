package FirstRow.view;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobotException;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import FirstRow.Database;
import FirstRow.MainFx;
import FirstRow.TestFXBase;
import FirstRow.view.LoginMignonController;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

class LoginMignonControllerTest extends TestFXBase{
	int exist1=0, exist2=0;
	final String MAIL_FIELD_ID = "#MailField";
	final String PASS_FIELD_ID = "#PassField";
	final String LOG_IN_BUTTON_ID = "#LogInButton";
	final String RETURN_BUTTON_ID = "#buttonI";
	final String ENTER1_BUTTON_ID = "#buttonLogin";
	
	
	@BeforeEach
	void setUp() {
		try {
			Connection con = Database.collegamento();
			Statement stmt = con.createStatement();
			exist1 = stmt.executeUpdate("INSERT INTO utenti (username,email,pass) values ('Mino','Dedalo@gmail.com','Tauro');");
			exist2 = stmt.executeUpdate("DELETE FROM utenti WHERE username='gino' AND email='giorgino2@gmail.com' AND pass='ringolino'");
			stmt.close();
			con.close();	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@AfterEach
	void tearDown() {
		try {
			Connection con = Database.collegamento();
			Statement stmt = con.createStatement();
			if(exist1>0) {
				stmt.executeUpdate("DELETE FROM utenti WHERE username='Mino' AND email='Dedalo@gmail.com' AND pass='Tauro'");
			}
			if(exist2>0) {
				stmt.executeUpdate("INSERT INTO utenti (username,email,pass) values ('gino','giorgino2@gmail.com','ringolino');");
			}
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	
	/*@Test
	public void clickOnBogusElement() {
		assertThrows(FxRobotException.class,()->{clickOn("#slkdjfkasd");},"mi ha cliccato un pulsante inesistente");
	}*/
	
	
	@Test
	public void LogInErrato_utenteEsistente() {
		clickOn(ENTER1_BUTTON_ID);
		LoginMignonController m=new LoginMignonController();
		clearOutImputFields();
		clickOn(MAIL_FIELD_ID).write("Mino");
		clickOn(PASS_FIELD_ID).write("");
		sleep(1000);
		assertFalse(m.ok,"controllo su passwoard inesistente");
		clearOutImputFields();
		clickOn(MAIL_FIELD_ID).write("gino");
		clickOn(PASS_FIELD_ID).write("ringolino");
		sleep(1000);
		assertFalse(m.ok,"utente inesistente passato con username");
		clearOutImputFields();
		clickOn(MAIL_FIELD_ID).write("giorgino2@gmail.com");
		clickOn(PASS_FIELD_ID).write("ringolino");
		sleep(1000);
		assertFalse(m.ok,"utente inesistente passato con mail");
	}
	
	
	@Test
	void controlloDiLogin_utenteEsistente() {
		LoginMignonController m=new LoginMignonController();
		assertTrue(m.utenteEsistente("Mino","Tauro"));
		assertTrue(m.utenteEsistente("Dedalo@gmail.com","Tauro"));
		
	}
	
	private void clearOutImputFields() {
		clickOn(MAIL_FIELD_ID).eraseText(100);
		clickOn(PASS_FIELD_ID).eraseText(25);
	}

}
