package FirstRow.view;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.view.CalendarView;

import FirstRow.Database;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    private BorderPane Finestra;
	Stage Stage;
	
	public void setStage(Stage primaryStage) {
        this.Stage = primaryStage;
    }
	
	/*
	 *sito per capire meglio CalendarFX: https://dlsc-software-consulting-gmbh.github.io/CalendarFX/
	 */
	
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
    	Calendar Bassa = new Calendar("Bassa");
    	Calendar Normale = new Calendar("Normale");
    	Calendar Urgente = new Calendar("Urgente");
    	//style: 5:rosso, 3:giallo, 1:verde
    	Bassa.setStyle(Style.STYLE1);
    	Normale.setStyle(Style.STYLE3);
    	Urgente.setStyle(Style.STYLE5);
    	//aggiunta degli elementi db nei calendari
    	//aggiunta attività a bassa priorità
    	try(Connection con = Database.collegamento(); Statement stmt = con.createStatement();ResultSet rs = stmt.executeQuery("SELECT * FROM attivita WHERE priorita = 'Bassa' ")){
    		while(rs.next()) {
    			Entry x = new Entry();
    			x.setId(String.valueOf(rs.getInt("ID")));
    			x.setTitle(rs.getString("nome"));
    			x.setFullDay(true);
    			x.changeStartDate(LocalDate.parse(rs.getDate("scadenza").toString()));
    			x.changeEndDate(LocalDate.parse(rs.getDate("scadenza").toString()));
    			x.setCalendar(Bassa);
    		}
    	}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore nel estrazione delle attività a bassa priorita");
		}
    	//aggiunta attività a media priorità
    	try(Connection con = Database.collegamento(); Statement stmt = con.createStatement();ResultSet rs = stmt.executeQuery("SELECT * FROM attivita WHERE priorita = 'Media' ")){
    		while(rs.next()) {
    			Entry x = new Entry();
    			x.setId(String.valueOf(rs.getInt("ID")));
    			x.setTitle(rs.getString("nome"));
    			x.setFullDay(true);
    			x.changeStartDate(LocalDate.parse(rs.getDate("scadenza").toString()));
    			x.changeEndDate(LocalDate.parse(rs.getDate("scadenza").toString()));
    			x.setCalendar(Normale);
    		}
    	}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore nel estrazione delle attività a bassa priorita");
		}
    	//aggiunta attività a alta priorità
    	try(Connection con = Database.collegamento(); Statement stmt = con.createStatement();ResultSet rs = stmt.executeQuery("SELECT * FROM attivita WHERE priorita = 'Alta' ")){
    		while(rs.next()) {
    			Entry x = new Entry();
    			x.setId(String.valueOf(rs.getInt("ID")));
    			x.setTitle(rs.getString("nome"));
    			x.setFullDay(true);
    			x.changeStartDate(LocalDate.parse(rs.getDate("scadenza").toString()));
    			x.changeEndDate(LocalDate.parse(rs.getDate("scadenza").toString()));
    			x.setCalendar(Urgente);
    		}
    	}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore nel estrazione delle attività a bassa priorita");
		}
    	
    	CalendarSource myCalendarSource = new CalendarSource("My Calendars");
        myCalendarSource.getCalendars().addAll(Bassa,Normale,Urgente);
        calendarView.getCalendarSources().addAll(myCalendarSource);
        calendarView.setRequestedTime(LocalTime.now());
        
        //inserimento del calendario
              
        Finestra.setCenter(calendarView);
	}
	
	
	public void elencoAttivita(MouseEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FirstRow/view/ElencoAttivita.fxml"));
        Parent root = loader.load();
        
        ElencoAttivitaController controller = loader.getController();
        controller.setDialogStage(Stage);
        Stage.setTitle("Elenco Attvità");
        Stage.setScene(new Scene(root));
        Stage.show();
    }
	
	public void dashboard(MouseEvent event) throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/FirstRow/view/Dashboard.fxml"));
		Parent root = loader.load();
		DashboardController controller = loader.getController();
		controller.setStage(Stage);
		Stage.setTitle("Dashboard");
		Stage.setScene(new Scene(root));
		Stage.show();
	}
}
