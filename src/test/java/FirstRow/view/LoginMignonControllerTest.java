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
import org.testfx.util.WaitForAsyncUtils;

import FirstRow.Database;
import FirstRow.MainFx;
import FirstRow.TestFXBase;
import FirstRow.view.LoginMignonController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

class LoginMignonControllerTest extends TestFXBase{
	int exist1=0, exist2=0;
	final String MAIL_FIELD_ID = "#MailField";
	final String PASS_FIELD_ID = "#PassField";
	final String LOG_IN_BUTTON_ID = "#LogInButton";
	final String RETURN_BUTTON_ID = "#buttonI";
	final String INS_BUTTON_ID = "#Button1";
	final String SUC_BUTTON_ID = "#bottone";

	LoginMignonController loginController;
	
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
	
	@Override
	public void start(Stage stage) throws Exception{
		 FXMLLoader loader = new FXMLLoader(getClass().getResource("/FirstRow/view/loginMignon.fxml"));
	     Parent rootLogin = loader.load();
	     loginController = loader.getController();
	     loginController.setStage(stage);
	     Scene scenaLogin = new Scene(rootLogin, 700, 500);
	     stage.setScene(scenaLogin);
	     stage.show();
	}

	
	@Test
	public void clickOnBogusElement() {
		assertThrows(FxRobotException.class,()->{clickOn("#slkdjfkasd");},"mi ha cliccato un pulsante inesistente");
	}
	
	
	@Test
	public void LogInErrato_utenteEsistente() {
		LoginMignonController m = loginController;
		clearOutImputFields(0,0);
		clickOn(MAIL_FIELD_ID).write("Mino");
		clickOn(PASS_FIELD_ID).write("");
		clickOn(LOG_IN_BUTTON_ID);
		clickOn(INS_BUTTON_ID);
		sleep(100);
		//clickOn(INS_BUTTON_ID);
		assertFalse(m.ok,"controllo su passwoard inesistente");
		clearOutImputFields("Mino".length(),"".length());
		clickOn(MAIL_FIELD_ID).write("gino");
		clickOn(PASS_FIELD_ID).write("ringolino");
		clickOn(LOG_IN_BUTTON_ID);
		sleep(100);
		clickOn(INS_BUTTON_ID);
		assertFalse(m.ok,"utente inesistente passato con username");
		clearOutImputFields("gino".length(),"ringolino".length());
		clickOn(MAIL_FIELD_ID).write("giorgino2@gmail.com");
		clickOn(PASS_FIELD_ID).write("ringolino");
		clickOn(LOG_IN_BUTTON_ID);
		sleep(100);
		clickOn(INS_BUTTON_ID);
		assertFalse(m.ok,"utente inesistente passato con mail");
	}
	
	
	@Test
	void LogInCorretto_utenteEsistente() {
		LoginMignonController m = loginController;
		clearOutImputFields(0,0);
		clickOn(MAIL_FIELD_ID).write("Mino");
		clickOn(PASS_FIELD_ID).write("Tauro");
		clickOn(LOG_IN_BUTTON_ID);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(m.ok,"non è entrato nikname e password giuste");
		clearOutImputFields("Mino".length(),"Tauro".length());
		clickOn(MAIL_FIELD_ID).write("Dedalo@gmail.com");
		clickOn(PASS_FIELD_ID).write("Tauro");
		clickOn(LOG_IN_BUTTON_ID);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(m.ok,"non è entrato email e password giuste");
		
	}
	
	private void clearOutImputFields(int x,int y) {
		clickOn(MAIL_FIELD_ID).eraseText(x);
		clickOn(PASS_FIELD_ID).eraseText(y);
	}

}
