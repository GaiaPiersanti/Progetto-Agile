package FirstRow.view;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import FirstRow.Database;
import FirstRow.MainFx;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class LoginMignonController {
	
	@FXML
	private TextField MailField;
	@FXML
	private PasswordField PassField;
	@FXML
	private ImageView bannerImageLogin;
	private Stage lStage;

	
	private Stage dialogStageIn;
	private boolean ok;
	private Scene rettiffica;

	

    public void setStage(Stage primaryStage) {
        this.lStage = primaryStage;
		this.dialogStageIn = primaryStage;
    }
 
	
	@FXML
    private void initialize() {
		File bannerFile = new File("src/main/resources/Immagini/Banner.png");
		Image banner = new Image(bannerFile.toURI().toString());
		bannerImageLogin.setImage(banner);
			
    }
	

    
    
	@FXML
	private void handleImput() {
		System.out.println("io funziono");
		String x = MailField.getText();
		String y = PassField.getText();
		if(utenteEsistente(x,y)){
			try {
					// Load the fxml file and create a new stage for the popup dialog.
			        FXMLLoader loader = new FXMLLoader();
			        loader.setLocation(MainFx.class.getResource("view/RispostaLoginSuccesso.fxml"));
			        AnchorPane page = (AnchorPane) loader.load();

			        // Create the dialog Stage.
			        Stage dialogStage = new Stage();
			        dialogStage.setTitle("Welcome");
			        dialogStage.initOwner(dialogStageIn);
			        Scene scene = new Scene(page);
			        dialogStage.setScene(scene);

		        	// Set the person into the controller.
		        	RispostaLoginSuccessoController controller = loader.getController();
		        	controller.setDialogStage(dialogStage);

		        	// Show the dialog and wait until the user closes it
		        	dialogStage.showAndWait();
		        	ok = true;
		    	} catch (IOException e) {
		    		e.printStackTrace();
		    	}
		}else{
			try {
		        	// Load the fxml file and create a new stage for the popup dialog.
		        	FXMLLoader loader = new FXMLLoader();
		        	loader.setLocation(MainFx.class.getResource("view/RispostaLoginInsuccesso.fxml"));
		        	AnchorPane page = (AnchorPane) loader.load();

		        	// Create the dialog Stage.
		        	Stage dialogStage = new Stage();
		        	dialogStage.setTitle("Errore");
		        	dialogStage.initOwner(dialogStageIn);
		        	Scene scene = new Scene(page);
		        	dialogStage.setScene(scene);

		        	// Set the person into the controller.
		        	RispostaLoginInsucessoController controller = loader.getController();
		        	controller.setDialogStage(dialogStage);

		        	// Show the dialog and wait until the user closes it
		        	dialogStage.showAndWait();
		        	PassField.setText("");
		        	ok = false;
		        	

		    	} catch (IOException e) {
		    		e.printStackTrace();
		    	}
		}

		if(ok) {
			dialogStageIn.close();
		}
	}
	
	
	//metodo che si collega al db è controlla che ci sia l'utente inserito x: username o mail, y: password
	 boolean utenteEsistente(String x, String y) {
		boolean res=false;
		try {
			Connection con = Database.collegamento();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM utenti WHERE email='"+x+"'AND pass='"+y+"' or username='"+x+"' AND pass='"+y+"'");
			
			res = rs.next();
			rs.close();
			stmt.close();
			con.close();
			return res;	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public void tornaIndietro(MouseEvent event) throws IOException{

		FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("view/PaginaIniziale.fxml"));
    	Parent paginaIniziale = loader.load();
		PaginaIController paginaIController = loader.getController();
    	paginaIController.setStage(lStage); 
		Scene sBack = new Scene(paginaIniziale);
		lStage.setScene(sBack);
		
	}
	
	
	@FXML
	private void chiudi() {
		try {
			dialogStageIn.setScene(rettiffica);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
