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
import javafx.scene.input.MouseEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
	
	@FXML TableView<Attivita> TabellaAttivita;
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
	@FXML
	private Button buttonAggiunta;
	
	
	private Stage StageIn;

	// Metodo che carica le immagini del banner imposta le caselle della TableView e carica le attività dal database.
	 
	@Override
    public void initialize ( URL location, ResourceBundle resources ) {
		// Carica e imposta l'immagine per l'icona "Casa"
		File bannerFile = new File("src/main/resources/Immagini/CasaIcona.png");
        Image iconaC = new Image(bannerFile.toURI().toString());
        bannerImageIC.setImage(iconaC);
         // Carica e imposta l'immagine per il calendario
        File bannerFileCalenadrio = new File("src/main/resources/Immagini/IconaCalendario.png");
        Image iconaCalenadrio = new Image(bannerFileCalenadrio.toURI().toString());
        bannerImageICalendario.setImage(iconaCalenadrio);
 		// Carica e imposta l'immagine per la lista
        File bannerFileLista = new File("src/main/resources/Immagini/IconaLista.png");
        Image iconaE = new Image(bannerFileLista.toURI().toString());
        bannerImageIE.setImage(iconaE);
		// Carica e imposta l'immagine per il profilo
        File bannerFileProfilo = new File("src/main/resources/Immagini/IconaProfilo.png");
        Image iconaP = new Image(bannerFileProfilo.toURI().toString());
        bannerImageIP.setImage(iconaP);
        
        // Configura le colonne della TableView con i rispettivi campi dell'oggetto Attivita
        ColNome.setCellValueFactory(new PropertyValueFactory<Attivita,String>("Nome"));
        ColCategoria.setCellValueFactory(new PropertyValueFactory<Attivita,String>("Categoria"));
        ColScadenza.setCellValueFactory(new PropertyValueFactory<Attivita,Date>("Scadenza"));
        ColPriorita.setCellValueFactory(new PropertyValueFactory<Attivita,String>("Priorita"));

        // Ottiene una connessione al database e crea una lista di attività
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

	//Metodo per impostare lo Stage corrente
	

	public void setDialogStage(Stage dialogStage) {
        this.StageIn = dialogStage;
    }

	//Metodo per l'aggiunta di una attvità che mi apre quando viene cliccato il tasto Aggiungi Attività uno stage che contiente i campi per le iformazioni dell'attività
	 
	@FXML
	private void handleAggiunta(ActionEvent event) throws IOException {
		// Carica il file FXML della interfaccia per aggiungere un'attività.
		FXMLLoader loaderAttivita = new FXMLLoader(getClass().getClassLoader().getResource("Attivita.fxml"));
        Parent rootAggiungiA = loaderAttivita.load();
		// Chiama il controller della classe AggiungiAttivitaController
		AggiungiAttivitaController controller = loaderAttivita.getController();
		// Passa un riferimento al controller dell'elenco in modo che dopo l'aggiunta la TableView si aggiorna
		controller.setElencoController(this);
		Stage stage = new Stage();
		controller.setStage(stage);
		Scene aggiungiAttivita = new Scene(rootAggiungiA, 534, 483);
		stage.setScene(aggiungiAttivita);
		stage.show();
	}
	
	//Metodo per la modifica di un'attività selezionata che apre una nuova finestra con le informazioni da compilare per la modifica

	@FXML
	private void handleModifica() {
		// Recupera l'attività selezionata nella TableView
		Attivita selectedAttivita = TabellaAttivita.getSelectionModel().getSelectedItem();
    
		if (selectedAttivita != null) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ModificaAttivita.fxml"));
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

	//Metodo per aggiornare un'attività modifica i dati nell'oggetto e aggiorna la TableView e il database.
	
	public void aggiornaAttivita(Attivita attivitaVecchia, Attivita attivitaNuova) {
		attivitaVecchia.setNome(attivitaNuova.getNome());
		attivitaVecchia.setCategoria(attivitaNuova.getCategoria());
		attivitaVecchia.setScadenza(attivitaNuova.getScadenza());
		attivitaVecchia.setPriorita(attivitaNuova.getPriorita());

		TabellaAttivita.refresh();

		aggiornaAttivitaNelDatabase(attivitaVecchia);
	}

	//Metodo per aggiornare un'attività nel database.

	private void aggiornaAttivitaNelDatabase(Attivita attivita) {
		String query = "UPDATE attivita SET nome = ?, categoria = ?, scadenza = ?, priorita = ? WHERE nome = ?";

		try (Connection con = Database.collegamento();
			 PreparedStatement stmt = con.prepareStatement(query)) {
	
			stmt.setString(1, attivita.getNome());
			stmt.setString(2, attivita.getCategoria());
			stmt.setDate(3, attivita.getScadenza());
			stmt.setString(4, attivita.getPriorita());
			stmt.setString(5, attivita.getNome()); // Usa il vecchio nome per aggiornare
	
			stmt.executeUpdate();
			System.out.println("Attività modificata con successo!");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore nella modifica dell'attività!");
		}
	}
	
	//Metodo per l'eliminazione di un'attività e rimuove l'attività dalla TableView e dal database.

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
	// Metodo per segnare un'attività come completata

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
	
	//Metodo per gestire la ricerca avanzata filtra le attività in base al testo inserito
	
	@FXML
	private void handleRicercaAvanzata() {
		String filtro = SearchField.getText().trim();

		// Se il campo di ricerca è vuoto, ricarica tutte le attività
		if (filtro.isEmpty()) {
			caricaTutteLeAttivita();
						return;
					}
				
					ObservableList<Attivita> risultatiRicerca = FXCollections.observableArrayList();
					
					String query = "SELECT nome, categoria, scadenza, priorita FROM attivita " +
								   "WHERE LOWER(nome) LIKE ? OR LOWER(categoria) LIKE ? OR LOWER(priorita) LIKE ?";
				
					try (Connection con = Database.collegamento();
						 PreparedStatement stmt = con.prepareStatement(query)) {
						
						// Imposta i parametri della query con il filtro in minuscolo per una ricerca case-insensitive
						String filtroLike = "%" + filtro.toLowerCase() + "%";
						stmt.setString(1, filtroLike);
						stmt.setString(2, filtroLike);
						stmt.setString(3, filtroLike);
						
						ResultSet rs = stmt.executeQuery();
				
						while (rs.next()) {
							risultatiRicerca.add(new Attivita(
								rs.getString("nome"),
								rs.getString("categoria"),
								rs.getDate("scadenza"),
								rs.getString("priorita")
							));
						}
				
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
						System.out.println("Errore nella ricerca delle attività!");
					}
				
					// Aggiorna la TableView con i risultati della ricerca
					TabellaAttivita.setItems(risultatiRicerca);
				}
	//Metodo per ricaricare tutte le attività dal database e aggiornare la TableView

	private void caricaTutteLeAttivita() {
		ObservableList<Attivita> list = FXCollections.observableArrayList();
    
		String query = "SELECT nome, categoria, scadenza, priorita FROM attivita";

		try (Connection con = Database.collegamento();
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery(query)) {

					while (rs.next()) {
							list.add(new Attivita(
							rs.getString("nome"),
							rs.getString("categoria"),
							rs.getDate("scadenza"),
							rs.getString("priorita")
						));
					}
		} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Errore nel caricamento delle attività!");
			}

					// Aggiorna la TableView con tutti i dati
					TabellaAttivita.setItems(list);
				}

	public void aggiungiAttivitaAllaTabella(Attivita nuovaAttivita) {
		
		TabellaAttivita.getItems().add(nuovaAttivita);
		
	}
	// Metodo per passare alla dashboard
	public void dashboard(MouseEvent event) throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("DashboardAttivita.fxml"));
		Parent root = loader.load();
		DashboardAttivitaController controller = loader.getController();
		controller.setDialogStage(StageIn);
		Stage stage = (Stage) TabellaAttivita.getScene().getWindow(); 
		stage.setTitle("Dashboard");
		stage.setScene(new Scene(root));
		stage.show();
	}
	// Metodo per aprire il calendario 
	public void Calendario(MouseEvent event) throws IOException{
    	FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Calendar.fxml"));
        Parent root = loader.load();
        
        CalendarController controller = loader.getController();
        controller.setStage(StageIn);
        StageIn.setTitle("Calendar");
        StageIn.setScene(new Scene(root));
        StageIn.show();
    }
}
