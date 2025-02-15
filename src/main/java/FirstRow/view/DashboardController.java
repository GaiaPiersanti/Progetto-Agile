package FirstRow.view;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.CalendarView;

import FirstRow.Model.Attivita;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.ImageView;
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FirstRow/view/ElencoAttivita.fxml"));
        Parent root = loader.load();
        
        ElencoAttivitaController controller = loader.getController();
        controller.setDialogStage(stage);
        
        Stage stage = (Stage) bannerImageIC.getScene().getWindow(); // Prendi lo Stage corrente
        stage.setTitle("Elenco Attvità");
        stage.setScene(new Scene(root));
        stage.show();
    }
    
    public void Calendario(MouseEvent event) throws IOException{
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/FirstRow/view/Calendar.fxml"));
        Parent root = loader.load();
        
        CalendarController controller = loader.getController();
        controller.setStage(stage);
        stage.setTitle("Calendar");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
