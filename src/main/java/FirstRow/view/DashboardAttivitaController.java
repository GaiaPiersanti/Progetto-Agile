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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
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

	@FXML
	private BarChart<String, Integer> GraficoIstogrammaUrgenti;

	@FXML
    private LineChart<String, Integer> Grafo30giorni;



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
		

		//3 fette
		PieChart.Data AltaPriorita = new PieChart.Data("Alta priorità", 0);
		PieChart.Data MediaPriorita =new PieChart.Data("Media priorità", 0);
		PieChart.Data BassaPriorita =new PieChart.Data("Bassa priorità", 0);

		
		try {
			Connection con1 = Database.collegamento();
			if (con1 == null) {
				System.out.println("errore nel caricamento in collegamento");
			}
			Statement stmt1 = con1.createStatement();
			ResultSet rs2;

			rs2 = stmt1.executeQuery("SELECT COUNT(*) FROM attivita WHERE priorita = 'Alta' AND completato=1");
			rs2.next();
			AltaPriorita.setPieValue(rs2.getInt("COUNT(*)"));

			rs2 = stmt1.executeQuery("SELECT COUNT(*) FROM attivita WHERE priorita = 'Media' AND completato=1");
			rs2.next();
			MediaPriorita.setPieValue(rs2.getInt("COUNT(*)"));

			rs2 = stmt1.executeQuery("SELECT COUNT(*) FROM attivita WHERE priorita = 'Bassa' AND completato=1");
			rs2.next();
			BassaPriorita.setPieValue(rs2.getInt("COUNT(*)"));
			
			rs2.close();
			stmt1.close();
			con1.close();
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	}
		listPie.addAll(AltaPriorita, MediaPriorita, BassaPriorita); //aggiungi alla lista
		GraficoTortaCompletate.setData(listPie); //riempi il grafo
		GraficoTortaCompletate.setTitle("Grafico a torta delle attività completate divise per priorità");


		//creazione serie per il grafico istogramma
		XYChart.Series alta = new XYChart.Series();
		XYChart.Series media = new XYChart.Series();
		XYChart.Series bassa = new XYChart.Series();
		alta.setName("Alta Priorità");
		media.setName("Media Priorità");
		bassa.setName("Bassa Priorità");

		//creazione serie per istogramma 30 gg
		XYChart.Series linea30 = new XYChart.Series();
		linea30.setName("Attività completate negli ultimi 30 giorni");
		
		//riempimento istogramma urgenti
		try {
			Connection con2 = Database.collegamento();
			PreparedStatement stmt1 = con2.prepareStatement("SELECT COUNT(*) FROM attivita WHERE priorita = ? AND completato = 0 AND scadenza = ?");
			ResultSet rs1 = null;


				for(int x = 0; x<=6; x++){
					stmt1.setString(1,"Alta");
					stmt1.setDate(2, Date.valueOf(LocalDate.now().plusDays(x)));	
					rs1 = stmt1.executeQuery();
					rs1.next();
					alta.getData().add(new XYChart.Data(Date.valueOf(LocalDate.now().plusDays(x)).toString(), rs1.getInt("COUNT(*)")));
				}


				for(int x = 0; x<=6; x++){
					stmt1.setString(1,"Media");
					stmt1.setDate(2, Date.valueOf(LocalDate.now().plusDays(x)));	
					rs1 = stmt1.executeQuery();
					rs1.next();
					media.getData().add(new XYChart.Data(Date.valueOf(LocalDate.now().plusDays(x)).toString(), rs1.getInt("COUNT(*)")));
				}


				for(int x = 0; x<=6; x++){
					stmt1.setString(1,"Bassa");
					stmt1.setDate(2, Date.valueOf(LocalDate.now().plusDays(x)));	
					rs1 = stmt1.executeQuery();
					rs1.next();
					bassa.getData().add(new XYChart.Data(Date.valueOf(LocalDate.now().plusDays(x)).toString(), rs1.getInt("COUNT(*)")));
				}
			

				//riempimento grafo 30

				
				

				rs1.close();
				stmt1.close();
				con2.close();
			
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	}
		//urgenti
	    GraficoIstogrammaUrgenti.getData().addAll(alta, media, bassa);
		GraficoIstogrammaUrgenti.setTitle("Istogramma delle attività in scadenza nei prossimi 7 giorni");

		try {
			Connection con2 = Database.collegamento();
			ResultSet rs1 = null;
			PreparedStatement stmt2 = con2.prepareStatement("SELECT COUNT(*) FROM attivita WHERE completato = 1 AND scadenza = ?");
				

				for(int x = 30; x>=0; x--){
					
					stmt2.setDate(1, Date.valueOf(LocalDate.now().minusDays(x)));	
					rs1 = stmt2.executeQuery();
					rs1.next();
					linea30.getData().add(new XYChart.Data(Date.valueOf(LocalDate.now().minusDays(x)).toString(), rs1.getInt("COUNT(*)")));
				}
				rs1.close();
				stmt2.close();
				con2.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		//30giorni
		Grafo30giorni.getData().addAll(linea30);
		Grafo30giorni.setTitle("Grafo della produttività degli ultimi 30 giorni");





		

    }//fine  initialiaze





    













	
	
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


