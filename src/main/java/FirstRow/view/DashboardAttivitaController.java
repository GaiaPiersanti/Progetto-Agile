package FirstRow.view;

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
import java.time.LocalDate;
import FirstRow.Database;
import FirstRow.Model.Attivita;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class DashboardAttivitaController implements Initializable {
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
	private TableView<Attivita> TabellaAttivitaUrgenti;
	@FXML
	private TableColumn<Attivita,String> ColNome1;
	@FXML
	private TableColumn<Attivita,String> ColCategoria1;
	@FXML
	private TableColumn<Attivita,Date> ColScadenza1;
	@FXML
	private TableColumn<Attivita,String> ColPriorita1;

	//icone sul bordo
    @FXML
	private ImageView bannerImageIC;
	@FXML
	private ImageView bannerImageIP;
	@FXML
	private ImageView bannerImageIE;
	@FXML
	private ImageView bannerImageICalendario;

	//grafici
    @FXML
	private PieChart GraficoTortaCompletate;

    private Stage StageCompl;


    @Override
    public void initialize ( URL location, ResourceBundle resources ) {
		//immagini laterali
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

		//colonne tabella completate
        ColNome.setCellValueFactory(new PropertyValueFactory<Attivita,String>("Nome"));
        ColCategoria.setCellValueFactory(new PropertyValueFactory<Attivita,String>("Categoria"));
        ColScadenza.setCellValueFactory(new PropertyValueFactory<Attivita,Date>("Scadenza"));
        ColPriorita.setCellValueFactory(new PropertyValueFactory<Attivita,String>("Priorita"));

		//colonne tabella urgenti
		ColNome1.setCellValueFactory(new PropertyValueFactory<Attivita,String>("Nome"));
        ColCategoria1.setCellValueFactory(new PropertyValueFactory<Attivita,String>("Categoria"));
        ColScadenza1.setCellValueFactory(new PropertyValueFactory<Attivita,Date>("Scadenza"));
        ColPriorita1.setCellValueFactory(new PropertyValueFactory<Attivita,String>("Priorita"));

		//lista da riempire con i dati del grafico a torta
		ObservableList<PieChart.Data> listPie = FXCollections.observableArrayList();
		



		//riempimento della tabella completate e urgenti
        Connection con = Database.collegamento();
		ObservableList<Attivita> list = FXCollections.observableArrayList(); //list Completate
		ObservableList<Attivita> list1 = FXCollections.observableArrayList(); // list1 urgenti
		if (con == null) {
			System.out.println("errore nel caricamento in collegamento");
		}
		try {
		Statement stmt = con.createStatement();
		ResultSet rs;
			//attivita completate
			rs = stmt.executeQuery("SELECT nome,categoria,scadenza,priorita FROM attivita WHERE completato = 1");
			while(rs.next()) {
				list.add(new Attivita(rs.getString("nome"),rs.getString("categoria"),rs.getDate("scadenza"),rs.getString("priorita")));
			}
			//attivita non completate che scadono entro 7 giorni dal giorno corrente(urgenti)
			PreparedStatement stmt1 = con.prepareStatement("SELECT nome,categoria,scadenza,priorita FROM attivita WHERE completato = 0 AND scadenza BETWEEN ? AND ?");
			stmt1.setDate(1, Date.valueOf(LocalDate.now()));
			stmt1.setDate(2, Date.valueOf(LocalDate.now().plusWeeks(1)));
			rs = stmt1.executeQuery();
			while(rs.next()) {
				list1.add(new Attivita(rs.getString("nome"),rs.getString("categoria"),rs.getDate("scadenza"),rs.getString("priorita")));
			}

			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        TabellaAttivita.setItems(list); // il list dentro il while prende tutti gli elementi prese dalla select e poi lo uso per riempire la tabella
		TabellaAttivitaUrgenti.setItems(list1); //idem per urgenti

		//riempimento grafico a torta
		Connection con1 = Database.collegamento();

		//3 fette
		PieChart.Data AltaPriorita = new PieChart.Data("Alta priorità", 0);
		PieChart.Data MediaPriorita =new PieChart.Data("Media priorità", 0);
		PieChart.Data BassaPriorita =new PieChart.Data("Bassa priorità", 0);

		if (con1 == null) {
			System.out.println("errore nel caricamento in collegamento");
		}
		try {
			Statement stmt1 = con1.createStatement();
			ResultSet rs;

			rs = stmt1.executeQuery("SELECT COUNT(*) FROM attivita WHERE priorita = 'Alta' AND completato=1");
			rs.next();
			AltaPriorita.setPieValue(rs.getInt("COUNT(*)"));

			rs = stmt1.executeQuery("SELECT COUNT(*) FROM attivita WHERE priorita = 'Media' AND completato=1");
			rs.next();
			MediaPriorita.setPieValue(rs.getInt("COUNT(*)"));

			rs = stmt1.executeQuery("SELECT COUNT(*) FROM attivita WHERE priorita = 'Bassa' AND completato=1");
			rs.next();
			BassaPriorita.setPieValue(rs.getInt("COUNT(*)"));
			
			rs.close();
			stmt1.close();
			con1.close();
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	}
		listPie.addAll(AltaPriorita, MediaPriorita, BassaPriorita); //aggiungi alla lista
		GraficoTortaCompletate.setData(listPie); //riempi il grafo
		GraficoTortaCompletate.setTitle("Grafico a torta delle attività completate divise per categoria");

    }

	
	
	//passaggio dello stage dalla pagina precedente a questa
	public void setDialogStage(Stage dialogStage) {
        this.StageCompl = dialogStage;
    }

	//metodi per far funzionare i bottoni e passare alle altre pagine 
	public void elencoAttivita(MouseEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ElencoAttivita.fxml"));
        Parent root = loader.load();
        
        ElencoAttivitaController controller = loader.getController();
        controller.setDialogStage(StageCompl);
        
        StageCompl.setTitle("Elenco Attività");
        StageCompl.setScene(new Scene(root));
        StageCompl.show();
    }
    
    public void Calendario(MouseEvent event) throws IOException{
    	FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Calendar.fxml"));
        Parent root = loader.load();
        
        CalendarController controller = loader.getController();
        controller.setStage(StageCompl);
        StageCompl.setTitle("Calendar");
        StageCompl.setScene(new Scene(root));
		StageCompl.show();
    }
}

