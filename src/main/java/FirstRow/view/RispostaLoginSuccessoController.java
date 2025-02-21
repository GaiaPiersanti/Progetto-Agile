package FirstRow.view;


import java.io.IOException;

import FirstRow.MainFx;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
		dialogStageIn.close();
	}
}
