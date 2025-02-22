package FirstRow.view;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import FirstRow.Model.Attivita;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import FirstRow.Database;
import FirstRow.TestFXBase;

class AggiungiAttivitaControllerTest extends TestFXBase {

    private ElencoAttivitaController mainController;

    //Metodo che configura il database di test e crea le tabelle necessarie.
    
    @BeforeAll
    public static void setUpDatabase() {

        System.setProperty("DATABASE_URL", "jdbc:mysql://localhost:3306/?user=root");
        System.setProperty("DATABASE_USERNAME", "root");
        System.setProperty("DATABASE_PASSWORD", "password");

        try {
            Connection con = Database.collegamento();
            Statement stmt = con.createStatement();
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS testdb;");
            stmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        System.setProperty("DATABASE_URL", "jdbc:mysql://localhost:3306/testdb");
        System.setProperty("DATABASE_USERNAME", "root");
        System.setProperty("DATABASE_PASSWORD", "password");

        try {
            Connection con = Database.collegamento();
            Statement stmt = con.createStatement();
            String createAttivita = "CREATE TABLE IF NOT EXISTS attivita ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "nome VARCHAR(255) NOT NULL, "
                    + "categoria VARCHAR(255), "
                    + "scadenza DATE, "
                    + "priorita VARCHAR(50), "
                    + "completato BOOL NOT NULL DEFAULT FALSE"
                    + ");";
            stmt.executeUpdate(createAttivita);

            String createCategorie = "CREATE TABLE IF NOT EXISTS categorie ("
                    + "nome VARCHAR(255) PRIMARY KEY"
                    + ");";
            stmt.executeUpdate(createCategorie);

            stmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Metodo che limina il database di test e pulisce le proprietà impostate.
     
    @AfterAll
    public static void tearDownDatabase() {
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

    
    //Metodo che carica l'FXML principale apre lo stage contente la pagina che contiente l'elecno delle attività.
    

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ElencoAttivita.fxml"));

        Parent root = loader.load();

        mainController = loader.getController();
        Scene scene = new Scene(root, 1000, 700);
        stage.setScene(scene);
        stage.show();

    }

    /**
        Test che simula l'aggiunta di una attività andandop a clicca sul pulsante "Aggiungi Attività" nel main window
        nel form di aggiunta inserisce i dati dell'attività,
        clicca sul pulsante per salvare l'attività,
        e verifica che l'attività sia presente nella TableView del main window
     */

    @Test
    public void testAggiungiAttivita() {

        clickOn("#buttonAggiunta");
        
        sleep(500);

        
        clickOn("#nomeAttivita").write("Test Attività");
        
        clickOn("#categoriaAttivita");
        clickOn("Lavoro");
        
        DatePicker dp = lookup("#scadenzaAttivita").query();
        Platform.runLater(() -> dp.setValue(LocalDate.of(2025, 12, 31)));
        sleep(500);
        
        clickOn("#prioritaAttivita");
        clickOn("Alta");
        
        clickOn("#buttonAggiungi");
        
        sleep(1000);
        
        ObservableList<Attivita> list = mainController.TabellaAttivita.getItems();
        boolean found = list.stream().anyMatch(a -> "Test Attività".equals(a.getNome()) &&
                                                     "Lavoro".equals(a.getCategoria()) &&
                                                     "Alta".equals(a.getPriorita()));
        assertTrue(found, "L'attività inserita non è stata trovata nella TableView del main window.");
    }


}
