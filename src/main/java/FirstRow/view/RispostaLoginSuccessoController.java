package FirstRow.view;


import java.io.IOException;

import FirstRow.MainFx;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RispostaLoginSuccessoController {

	
private Stage dialogStageIn;
	
	@FXML
    private void initialize() {
    }
	
    public void setDialogStage(Stage dialogStage) {
        this.dialogStageIn = dialogStage;
    }
	
	@FXML
	private void handleImput() {
		System.out.println("io sono entrato");
		try {
		// Load the fxml file and create a new stage for the popup dialog.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainFx.class.getResource("view/ElencoAttivita.fxml"));
        AnchorPane page;
		
			page = (AnchorPane) loader.load();


        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Elenco Attività");
        dialogStage.initOwner(dialogStageIn);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

    	// Set the person into the controller.
        ElencoAttivitaController controller = loader.getController();
    	controller.setDialogStage(dialogStage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dialogStageIn.close();
	}
}
