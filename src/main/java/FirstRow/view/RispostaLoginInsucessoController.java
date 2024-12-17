package FirstRow.view;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class RispostaLoginInsucessoController {

	
private Stage dialogStageIn;
	
	@FXML
    private void initialize() {
    }
	
    public void setDialogStage(Stage dialogStage) {
        this.dialogStageIn = dialogStage;
    }
	
	@FXML
	private void handleImput() {
		System.out.println("io non sono entrato");

		dialogStageIn.close();
		
	}
}
