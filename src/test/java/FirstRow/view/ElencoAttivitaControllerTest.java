package FirstRow.view;

import FirstRow.TestFXBase;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ElencoAttivitaControllerTest extends TestFXBase{

	final String SEARCH_FIELD_ID = "#SearchField";
	final String TABLE_ID = "#TabellaAttivita";
	
	@Override
	public void start(Stage stage) throws Exception{
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("ElencoAttivita.fxml"));
        Parent page = loader.load();


        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Elenco Attività");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

    	// Set the person into the controller.
        ElencoAttivitaController controller = loader.getController();
    	controller.setDialogStage(dialogStage);
	     dialogStage.show();

	}	
}
