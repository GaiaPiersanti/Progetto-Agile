package FirstRow.view;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class utenteEsistenteTest {
	int exist1=0, exist2=0;
	
	@BeforeEach
	void aggintà() {
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/firstrow","root","Higdrasil1!34");
			Statement stmt = con.createStatement();
			exist1 = stmt.executeUpdate("INSERT INTO firstrow.utente_registrato (username,mail,pass) values ('Mino','Dedalo@gmail.com','Tauro');");
			exist2 = stmt.executeUpdate("DELETE FROM firstrow.utente_registrato WHERE username='gino' AND mail='giorgino2@gmail.com' AND pass='ringolino'");
			stmt.close();
			con.close();	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@AfterEach
	void elimina() {
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/firstrow","root","Higdrasil1!34");
			Statement stmt = con.createStatement();
			if(exist1>0) {
				stmt.executeUpdate("DELETE FROM firstrow.utente_registrato WHERE username='Mino' AND mail='Dedalo@gmail.com' AND pass='Tauro'");
			}
			if(exist2>0) {
				stmt.executeUpdate("INSERT INTO firstrow.utente_registrato (username,mail,pass) values ('gino','giorgino2@gmail.com','ringolino');");
			}
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}

	@Test
	void test() {
		LoginMignonController m=new LoginMignonController();
		assertTrue(m.utenteEsistente("Mino","Tauro"));
		assertTrue(m.utenteEsistente("Dedalo@gmail.com","Tauro"));
		assertFalse(m.utenteEsistente("gino","ringolino"));
		assertFalse(m.utenteEsistente("giorgino2@gmail.com","ringolino"));
	}

}
