package FirstRow.view;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import FirstRow.Database;
import FirstRow.Model.Attivita;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class AttivitaCompletateController implements Initializable {
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

    private Stage StageCompl;

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
		
			rs = stmt.executeQuery("SELECT nome,categoria,scadenza,priorita FROM attivita WHERE completato = 1");
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
        this.StageCompl = dialogStage;
    }
}

