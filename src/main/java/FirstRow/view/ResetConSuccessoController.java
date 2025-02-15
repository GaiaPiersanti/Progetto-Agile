package FirstRow.view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ResetConSuccessoController {

	
private Stage StageIn;
	
	@FXML
    private void initialize() {
    }
	
    public void setDialogStage(Stage dialogStage) {
        this.StageIn = dialogStage;
    }
	
	@FXML
	private void handleImput() {
		System.out.println("tutto ok");

        try{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FirstRow/view/loginMignon.fxml"));
        Parent rootLogin = loader.load();
        LoginMignonController loginController = loader.getController();
        StageIn.setTitle("Pianificator");
        loginController.setStage(StageIn);
		Scene scenaLogin = new Scene(rootLogin, 700, 500);
        StageIn.setScene(scenaLogin);
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("Errore nell'apertura della pagina");
        }
		
	}
}

