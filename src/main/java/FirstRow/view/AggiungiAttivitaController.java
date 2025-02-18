package FirstRow.view;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Statement;  // Import corretto
import FirstRow.Database;
import FirstRow.Model.Attivita;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    @FXML
    private TextField nuovaCategoria; // Campo per inserire nuova categoria
    private Stage stage;
    private ElencoAttivitaController elencoController;
    private ObservableList<String> categorieList = FXCollections.observableArrayList();



    public void setStage(Stage stage) {
        this.stage = stage;
    }


    public void setElencoController(ElencoAttivitaController elencoController) {
        this.elencoController = elencoController;
    }

    

    public void initialize(){
        caricaCategorieDalDatabase();
        
                categoriaAttivita.getItems().addAll("Lavoro", "Studio", "Casa", "Altro");
                prioritaAttivita.getItems().addAll("Alta", "Media", "Bassa");
        
                categoriaAttivita.setOnAction(event -> {
                    if ("Altro".equals(categoriaAttivita.getValue())) {
                        nuovaCategoria.setDisable(false);  // Abilita il TextField per la nuova categoria
                        nuovaCategoria.requestFocus();    // Focus sul campo di input
                    } else {
                        nuovaCategoria.setDisable(true);  // Disabilita se non è selezionato "Altro"
                        nuovaCategoria.clear();
                    }
                });
        
                // Disabilita il campo della nuova categoria all'inizio
                nuovaCategoria.setDisable(true);
        
            }
                
        
    public void aggiungi(ActionEvent event) {

        String nome = nomeAttivita.getText();
        String categoria = categoriaAttivita.getValue();
        String nuovaCategoriaTesto = nuovaCategoria.getText();
        Date scadenza = (scadenzaAttivita.getValue() != null) ? Date.valueOf(scadenzaAttivita.getValue()) : Date.valueOf(LocalDate.now());
        String priorita = prioritaAttivita.getValue();

        if (nome.isEmpty() || (categoria == null && nuovaCategoriaTesto.isEmpty()) || scadenza == null|| priorita == null) {
            System.out.println("Errore: tutti i campi devono essere compilati!");
            return;
        }

        // Se l'utente ha inserito una nuova categoria, la salviamo e usiamo quella
        if (!nuovaCategoriaTesto.isEmpty()) {
            categoria = nuovaCategoriaTesto;
            salvaCategoriaNelDatabase(nuovaCategoriaTesto);
            categoriaAttivita.getItems().add(nuovaCategoriaTesto); // Aggiunge la nuova categoria alla ChoiceBox
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

        private void salvaCategoriaNelDatabase(String categoria) {
            String query = "INSERT INTO categorie (nome) VALUES (?) ON DUPLICATE KEY UPDATE nome = nome";
            try (Connection con = Database.collegamento();
                    PreparedStatement stmt = con.prepareStatement(query)) {
                    stmt.setString(1, categoria);
                    stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private void caricaCategorieDalDatabase() {
            String query = "SELECT nome FROM categorie";
            try (Connection con = Database.collegamento();
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query)) {
                    while (rs.next()) {
                        categorieList.add(rs.getString("nome"));
                    }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            categoriaAttivita.setItems(categorieList);
        }


    }

