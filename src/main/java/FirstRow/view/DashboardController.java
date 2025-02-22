package FirstRow.view;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class DashboardController {
    @FXML
    private ImageView bannerImageIC;
    @FXML
    private ImageView bannerImageIP;
    @FXML
    private ImageView bannerImageIE;
    @FXML
    private ImageView bannerImageICalendario;
    @FXML
    private PasswordField pInput;
    @FXML
    private TextField emailInput;
    @FXML
    private TextField uInput;
    private Stage stage;

    public void setStage(Stage primaryStage){
        this.stage = primaryStage;
    }

    @FXML
    public void initialize(){
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
        
    }

    public void elencoAttivita(MouseEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ElencoAttivita.fxml"));
        Parent root = loader.load();
        
        ElencoAttivitaController controller = loader.getController();
        controller.setDialogStage(stage);
        
        Stage stage = (Stage) bannerImageIC.getScene().getWindow(); // Prendi lo Stage corrente
        stage.setTitle("Elenco Attvità");
        stage.setScene(new Scene(root));
        stage.show();
    }
    
    public void Calendario(MouseEvent event) throws IOException{
    	FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Calendar.fxml"));
        Parent root = loader.load();
        
        CalendarController controller = loader.getController();
        controller.setStage(stage);
        stage.setTitle("Calendar");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static Connection collegamento(){
		try {
			String url = System.getProperty("DATABASE_URL");  // Prende la variabile d'ambiente
			String user = System.getProperty("DATABASE_USERNAME");
			String password = System.getProperty("DATABASE_PASSWORD");

			if (url == null) {
				// Usa il database di produzione SOLO se non è in esecuzione un test
				url = "jdbc:mysql://127.0.0.1:3306/AgileDB";
				user = "root";
				password = "MaicholZed01.";
			}
            System.out.println(url);
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}


}
