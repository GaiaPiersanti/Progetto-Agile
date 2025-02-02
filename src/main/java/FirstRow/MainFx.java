package FirstRow;

import javafx.application.Application;
import javafx.stage.Stage;
import FirstRow.view.DashboardController;
import FirstRow.view.PaginaIController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class MainFx extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
	
		FXMLLoader loader = new FXMLLoader(getClass().getResource("view/PaginaIniziale.fxml"));
		Parent rootPI = loader.load();

		PaginaIController controller = loader.getController();
		controller.setStage(primaryStage);


		Scene scenaI = new Scene(rootPI, 700, 500); 
		primaryStage.setTitle("Pianificator");
		primaryStage.setScene(scenaI);
		primaryStage.show();
				
	}
	 
	/*public void closeProgamme(){
        Boolean answer = ConfirmBox.display("Exit", "Vuoi annulare l'operazione?");
        if(answer){
            this.primaryStage.close();
        }
    }
	*/
}
