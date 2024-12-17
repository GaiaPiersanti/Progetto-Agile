package FirstRow.View;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import FirstRow.MainFx;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class LoginMignonController {
	
	@FXML
	private TextField MailField;
	@FXML
	private PasswordField PassField;
	
	private Stage dialogStageIn;
	private boolean ok;

	
	
	@FXML
    private void initialize() {
    }
	
	
	
    public void setDialogStage(Stage dialogStage) {
        this.dialogStageIn = dialogStage;
    }
	
    
    
	@FXML
	private void handleImput() {
		System.out.println("io funziono");
		String x = MailField.getText();
		String y = PassField.getText();
		if(){
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
	private boolean utenteEsistente(String x, String y) {
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/firstrow","root","Higdrasil1!34");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
