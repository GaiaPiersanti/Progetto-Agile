package FirstRow.view;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import FirstRow.Database;
import FirstRow.Model.Attivita;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AggiungiAttivitaController {
    
    @FXML
    private TextField nomeAttivita;
    @FXML
    private ChoiceBox<String> categoriaAttivita;
    @FXML
    private DatePicker scadenzaAttivita;
    @FXML
    private ChoiceBox<String> prioritaAttivita;
    private Stage stage;
    private ElencoAttivitaController elencoController;


    public void setStage(Stage stage) {
        this.stage = stage;
    }


    public void setElencoController(ElencoAttivitaController elencoController) {
        this.elencoController = elencoController;
    }

    

    public void initialize(){
        categoriaAttivita.getItems().addAll("Lavoro", "Studio", "Casa");
        prioritaAttivita.getItems().addAll("Alta", "Media", "Bassa");

    }

    public void aggiungi(ActionEvent event) {

        String nome = nomeAttivita.getText();
        String categoria = categoriaAttivita.getValue();
        Date scadenza = (scadenzaAttivita.getValue() != null) ? Date.valueOf(scadenzaAttivita.getValue()) : Date.valueOf(LocalDate.now());
        String priorita = prioritaAttivita.getValue();

        if (nome.isEmpty() || categoria == null || scadenza == null|| priorita == null) {
            System.out.println("Errore: tutti i campi devono essere compilati!");
            return;
        }

        // Crea un oggetto Attivita
        Attivita nuovaAttivita = new Attivita(nome, categoria, scadenza, priorita);

        // Salva l'attività nel database
        if (salvaAttivitaSuDatabase(nuovaAttivita)) {
            // Aggiorna la tabella nella finestra principale
            if (elencoController != null) {
                elencoController.aggiungiAttivitaAllaTabella(nuovaAttivita);
            }
            if (stage != null) {
                stage.close();
            }
        }
    }

    private boolean salvaAttivitaSuDatabase(Attivita attivita) {
        String query = "INSERT INTO attivita (nome, categoria, scadenza, priorita) VALUES (?, ?, ?, ?)";
        try (Connection con = Database.collegamento();
            PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, attivita.getNome());
            stmt.setString(2, attivita.getCategoria());
            stmt.setDate(3, attivita.getScadenza());
            stmt.setString(4, attivita.getPriorita());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
            }
        
        }
    }

