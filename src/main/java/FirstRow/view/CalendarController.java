package FirstRow.view;

import java.io.File;
import java.time.LocalTime;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.view.CalendarView;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class CalendarController {
	@FXML
    private ImageView bannerImageIC;
    @FXML
    private ImageView bannerImageIP;
    @FXML
    private ImageView bannerImageIE;
    @FXML
    private ImageView bannerImageICalendario;
    @FXML
    private AnchorPane Sfondo;
	Stage Stage;
	
	public void setStage(Stage primaryStage) {
        this.Stage = primaryStage;
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
	    
	    //Creazione del calendario 
	    CalendarView calendarView = new CalendarView();
    	Calendar birthdays = new Calendar("Birthdays");
    	birthdays.setStyle(Style.STYLE1);
    	CalendarSource myCalendarSource = new CalendarSource("My Calendars");
        myCalendarSource.getCalendars().addAll(birthdays);
        calendarView.getCalendarSources().addAll(myCalendarSource);
        calendarView.setRequestedTime(LocalTime.now());
        
        //inserimento del calendario
        Sfondo.setMaxWidth(1300);
        Sfondo.setMinWidth(1300);
        Sfondo.setMaxHeight(1000);
        Sfondo.setMinHeight(1000);
        Sfondo.setClip(calendarView);
	}
}
