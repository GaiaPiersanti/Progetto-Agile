package FirstRow.view;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import FirstRow.Database;
import FirstRow.MainFx;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class ResetPasswordController {

    @FXML
    private TextField Email;

    @FXML
    private PasswordField OldPassword;

    @FXML
    private PasswordField NewPassword;

    @FXML
    private PasswordField NewPassword2;

    @FXML
    private Label MessaggioErrore;

    @FXML
	private ImageView bannerImageLogin;

    Stage ResetStage;

    public void setStage(Stage primaryStage) {
        this.ResetStage = primaryStage;
    }
 
    //prende l'immagine verde a dx e la carica (metodo lanciato sempre prima che compare lo stage)
    @FXML
    private void initialize() {
		File bannerFile = new File("src/main/resources/Immagini/Banner.png");
		Image banner = new Image(bannerFile.toURI().toString());
		bannerImageLogin.setImage(banner);
			
    }


    @FXML
	public void loginBox(MouseEvent event) throws IOException{
        	
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FirstRow/view/loginMignon.fxml"));
        Parent rootLogin = loader.load();
        LoginMignonController loginController = loader.getController();
        loginController.setStage(ResetStage);
		Scene scenaLogin = new Scene(rootLogin, 700, 500);
        ResetStage.setScene(scenaLogin);
	
  	  }

    @FXML
    public void resetPassword () {
        System.out.println("sono arrivato qui");
        String email = Email.getText();
        String oldPassword = OldPassword.getText();
        String newPassword = NewPassword.getText();
        String newPassword2 = NewPassword2.getText();

         //costruisco un oggetto di LMC e ci chiamo il metodo e salvo l'esito del controllo in utenteEsiste
        boolean utenteEsiste = (new LoginMignonController()).utenteEsistente(email, oldPassword);

        //setta il messaggio di errore se l'utente non esiste o la password vecchia non è quella dell'utente esistente
        if (!utenteEsiste){
            MessaggioErrore.setText( "Utente non esistente o vecchia password errata");
            return;
        }

        //setta il messaggio di errore se due nuove password non coincidono
        if(!(newPassword.equals(newPassword2))){
            MessaggioErrore.setText( "La nuova password non è ripetuta correttamente");
            return;
         }

        //se è tutto corretto resetta la password nel db
        String query = "UPDATE utenti SET pass = ? WHERE email = ?";

        try (Connection con = Database.collegamento(); 
             PreparedStatement stmt = con.prepareStatement(query)) {

                stmt.setString(1, newPassword);
                stmt.setString(2, email);

        } catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore nel reset del password");
		}
                    //passaggio dal reset pw alla pagina reset con successo
                    try{
                    // Load the fxml file and create a new stage for the popup dialog.
		        	FXMLLoader loader = new FXMLLoader();
		        	loader.setLocation(MainFx.class.getResource("view/ResetConSuccesso.fxml"));
		        	AnchorPane page = (AnchorPane) loader.load();

		        	// Create the dialog Stage
		        	ResetStage.setTitle("Reset effettuato!");
		        	Scene scene = new Scene(page);
		        	ResetStage.setScene(scene);

		        	// Set the person into the controller.
		        	ResetConSuccessoController controller = loader.getController();
		        	controller.setDialogStage(ResetStage);


                    } catch(IOException e){
                        e.printStackTrace();
                        System.out.println("Errore nell'apertura della pagina");

                    }

    }



}
