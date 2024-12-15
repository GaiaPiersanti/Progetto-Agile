package FirstRow.View;


import javafx.fxml.FXML;
import javafx.stage.Stage;

public class RispostaLoginSuccessoController {

	
private Stage dialogStageIn;
private boolean[] ok ;
	
	@FXML
    private void initialize() {
    }
	
    public void setDialogStage(Stage dialogStage,boolean[] ok) {
        this.dialogStageIn = dialogStage;
        this.ok = ok;
    }
	
	@FXML
	private void handleImput() {
		System.out.println("io sono entrato");
		ok[1] = true;
		dialogStageIn.close();
	}
}
