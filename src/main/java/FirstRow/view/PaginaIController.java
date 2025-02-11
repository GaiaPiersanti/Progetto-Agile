package FirstRow.view;

import java.io.File;
import java.io.IOException;
import FirstRow.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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


    public void setStage(Stage primaryStage) {
        this.stage = primaryStage;
    }
 
    @FXML
    public void initialize(){
        File bannerFile = new File("src/main/resources/Immagini/Banner.png");
        Image banner = new Image(bannerFile.toURI().toString());
        bannerImageRegistrazione.setImage(banner);
        
    }
   
    
    @FXML
	public void loginBox(MouseEvent event) throws IOException{
        	
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FirstRow/view/loginMignon.fxml"));
        Parent rootLogin = loader.load();
        LoginMignonController loginController = loader.getController();
        loginController.setStage(stage);
		Scene scenaLogin = new Scene(rootLogin, 700, 500);
        stage.setScene(scenaLogin);
	
  	  }

        @FXML
		public void registrazione(ActionEvent event){
			System.out.println("ok");
			try {
					Database.connection(pInput.getText(), emailInput.getText(),uInput.getText(), null);
	
				}
	
	
			catch (Exception e) {
	
				}
		}
    
}
