package FirstRow.view;

import java.io.File;
import java.io.IOException;
import FirstRow.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import javafx.stage.Stage;

public class PaginaIController {
    @FXML
    private ImageView bannerImageRegistrazione;
    @FXML
    private PasswordField pInput;
    @FXML
    private TextField emailInput;
    @FXML
    private TextField uInput;
    private Stage stage;

    //  Metodo per impostare lo stage principale viene chiamato dal main per passare il riferimento allo stage corrente.

    public void setStage(Stage primaryStage) {
        this.stage = primaryStage;
    }

    // Metodo di inizializzazione serve per configurare l'immagine  
    

    @FXML
    public void initialize(){
        File bannerFile = new File("src/main/resources/Immagini/Banner.png");
        Image banner = new Image(bannerFile.toURI().toString());
        bannerImageRegistrazione.setImage(banner);
        
    }
   
    //Metodo  che apre la schermata di login quando si clicca sul tasto login nella schermata di registrazione.

    @FXML
	public void loginBox(MouseEvent event) throws IOException{
        	
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("loginMignon.fxml"));
        Parent rootLogin = loader.load();
        LoginMignonController loginController = loader.getController();
        loginController.setStage(stage);
		Scene scenaLogin = new Scene(rootLogin);
        stage.setScene(scenaLogin);
        stage.setMinWidth(700);
        stage.setMinHeight(500);
	
  	  }
        //Metodo che geistisce la registrazione in cui dopo la registrazione si apre la dashboard.

        @FXML
		public void registrazione(ActionEvent event) throws Exception{
			System.out.println("ok");
			// Effettua una chiamata al metodo di connessione per registrare l'utente
            // Passa i valori dei campi password, email e username
				Boolean registrazioneOk = Database.connection(pInput.getText(), emailInput.getText(),uInput.getText(), null);
            // Se la registrazione va a buon fine, carica la Dashboard
                if(registrazioneOk){
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("DashboardAttivita.fxml"));
                    Parent dashboardRoot = loader.load();
                    Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                    DashboardAttivitaController dashController = loader.getController();
                    dashController.setDialogStage(this.stage);
                    Scene dashboardScene = new Scene(dashboardRoot, 1000, 700); 
                    stage.setScene(dashboardScene);
                    stage.setMinWidth(1000);
                    stage.setMinHeight(800);
                    stage.show();
                }
		}
    }

