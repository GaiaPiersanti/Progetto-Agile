package FirstRow.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import FirstRow.Database;
import FirstRow.Model.Attivita;

public class ElencoAttivitaController implements Initializable {
	
	@FXML
	private TableView<Attivita> TabellaAttivita;
	@FXML
	private TableColumn<Attivita,String> ColNome;
	@FXML
	private TableColumn<Attivita,String> ColCategoria;
	@FXML
	private TableColumn<Attivita,Date> ColScadenza;
	@FXML
	private TableColumn<Attivita,String> ColPriorita;
	@FXML
	private ImageView bannerImageIC;
	@FXML
	private ImageView bannerImageIP;
	@FXML
	private ImageView bannerImageIE;
	@FXML
	private ImageView bannerImageICalendario;
	@FXML
	private TextField SearchField;
	
	private Stage StageIn;
	
	@Override
    public void initialize ( URL location, ResourceBundle resources ) {
		File bannerFile = new File("src/main/resources/Immagini/CasaIcona.png");
        Image iconaC = new Image(bannerFile.toURI().toString());
        bannerImageIC.setImage(iconaC);
        
        File bannerFileCalenadrio = new File("src/main/resources/Immagini/IconaCalendario.png");
        Image iconaCalenadrio = new Image(bannerFileCalenadrio.toURI().toString());
        bannerImageICalendario.setImage(iconaCalenadrio);

        File bannerFileLista = new File("src/main/resources/Immagini/IconaLista.png");
        Image iconaE = new Image(bannerFileLista.toURI().toString());
        bannerImageIE.setImage(iconaE);

        File bannerFileProfilo = new File("src/main/resources/Immagini/IconaProfilo.png");
        Image iconaP = new Image(bannerFileProfilo.toURI().toString());
        bannerImageIP.setImage(iconaP);
        
        
        ColNome.setCellValueFactory(new PropertyValueFactory<Attivita,String>("Nome"));
        ColCategoria.setCellValueFactory(new PropertyValueFactory<Attivita,String>("Categoria"));
        ColScadenza.setCellValueFactory(new PropertyValueFactory<Attivita,Date>("Scadenza"));
        ColPriorita.setCellValueFactory(new PropertyValueFactory<Attivita,String>("Priorita"));
        
		Connection con = Database.collegamento();
		ObservableList<Attivita> list = FXCollections.observableArrayList();
		if (con == null) {
			System.out.println("errore nel caricamento in collegamento");
		}
		try {
		Statement stmt = con.createStatement();
		ResultSet rs;
		
			rs = stmt.executeQuery("SELECT nome,categoria,scadenza,priorita FROM attivita WHERE completato = 0");
			while(rs.next()) {
				list.add(new Attivita(rs.getString("nome"),rs.getString("categoria"),rs.getDate("scadenza"),rs.getString("priorita")));
			}
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		TabellaAttivita.setItems(list);
    }
	
	public void setDialogStage(Stage dialogStage) {
        this.StageIn = dialogStage;
    }
	

	
	@FXML
	private void handleAggiunta(ActionEvent event) throws IOException {

		FXMLLoader loaderAttivita = new FXMLLoader(getClass().getResource("/FirstRow/view/Attivita.fxml"));
        Parent rootAggiungiA = loaderAttivita.load();
		AggiungiAttivitaController controller = loaderAttivita.getController();
		controller.setElencoController(this);
		Stage stage = new Stage();
		controller.setStage(stage);
		Scene aggiungiAttivita = new Scene(rootAggiungiA, 534, 483);
		stage.setScene(aggiungiAttivita);
		stage.show();
	}
	
	
	@FXML
	private void handleModifica() {

		Attivita selectedAttivita = TabellaAttivita.getSelectionModel().getSelectedItem();
    
		if (selectedAttivita != null) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/FirstRow/view/ModificaAttivita.fxml"));
				Parent root = loader.load();
				ModificaAttivitaController controller = loader.getController();
				controller.setAttivita(selectedAttivita);
				controller.setElencoController(this);
				
				Stage stage = new Stage();
				stage.setScene(new Scene(root, 534, 483));
				stage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Errore: Nessuna attività selezionata!");
		}
	}

	public void aggiornaAttivita(Attivita attivitaVecchia, Attivita attivitaNuova) {
		TabellaAttivita.getItems().remove(attivitaVecchia);
		TabellaAttivita.getItems().add(attivitaNuova);
		
		aggiornaAttivitaNelDatabase(attivitaVecchia, attivitaNuova);
	}
	
	private void aggiornaAttivitaNelDatabase(Attivita attivitaVecchia, Attivita attivitaNuova) {
		String query = "UPDATE attivita SET nome = ?, categoria = ?, scadenza = ?, priorita = ? WHERE nome = ? AND categoria = ? AND scadenza = ? AND priorita = ?";
		
		try (Connection con = Database.collegamento();
			 PreparedStatement stmt = con.prepareStatement(query)) {
			
			stmt.setString(1, attivitaNuova.getNome());
			stmt.setString(2, attivitaNuova.getCategoria());
			stmt.setDate(3, attivitaNuova.getScadenza());
			stmt.setString(4, attivitaNuova.getPriorita());
			stmt.setString(5, attivitaVecchia.getNome());
			stmt.setString(6, attivitaVecchia.getCategoria());
			stmt.setDate(7, attivitaVecchia.getScadenza());
			stmt.setString(8, attivitaVecchia.getPriorita());
			
			stmt.executeUpdate();
			System.out.println("Attività modificata con successo!");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore nella modifica dell'attività!");
		}
	}
	
	
	@FXML
	private void handleElimina() {
		Attivita selectedAttivita = TabellaAttivita.getSelectionModel().getSelectedItem();

		if (selectedAttivita != null) {
			// Rimuovi dalla TableView
			TabellaAttivita.getItems().remove(selectedAttivita);
			
			// Rimuovi dal database
			eliminaAttivitaDalDatabase(selectedAttivita);
		} else {
			System.out.println("Errore: Nessuna attività selezionata!");
		}

		}
	@FXML
	private void handleComleta() {
		Attivita selectedAttivita = TabellaAttivita.getSelectionModel().getSelectedItem();

		if (selectedAttivita != null) {
			// Rimuovi dalla TableView
			TabellaAttivita.getItems().remove(selectedAttivita);
			
			// aggiorna nel database
			String query = "UPDATE attivita SET completato = ? WHERE nome = ? AND categoria = ? AND scadenza = ? AND priorita = ?";
			
			try (Connection con = Database.collegamento();
				 PreparedStatement stmt = con.prepareStatement(query)) {
				
				stmt.setBoolean(1, true);
				stmt.setString(2, selectedAttivita.getNome());
				stmt.setString(3, selectedAttivita.getCategoria());
				stmt.setDate(4, selectedAttivita.getScadenza());
				stmt.setString(5, selectedAttivita.getPriorita());
				
				stmt.executeUpdate();
				System.out.println("Attività segnata come completatà con successo!");
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Errore nella modifica dell'attività!");
			}
		} else {
			System.out.println("Errore: Nessuna attività selezionata!");
		}

		}

		private void eliminaAttivitaDalDatabase(Attivita attivita) {
   	 		String query = "DELETE FROM attivita WHERE nome = ? AND categoria = ? AND scadenza = ? AND priorita = ?";
    
			try (Connection con = Database.collegamento();
				PreparedStatement stmt = con.prepareStatement(query)) {
				
				stmt.setString(1, attivita.getNome());
				stmt.setString(2, attivita.getCategoria());
				stmt.setDate(3, attivita.getScadenza());
				stmt.setString(4, attivita.getPriorita());
				
				stmt.executeUpdate();
				System.out.println("Attività eliminata con successo!");
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Errore nell'eliminazione dell'attività!");
			}

		}


	
	
	
	@FXML
	private void handleRicercaAvanzata() {
		//TO-DO inserire qui i comandi relativi alla ricerca avanzata delle attività
	}

	public void aggiungiAttivitaAllaTabella(Attivita nuovaAttivita) {
		
		TabellaAttivita.getItems().add(nuovaAttivita);
		
	}
}
