package FirstRow;

import javafx.application.Application;
import javafx.stage.Stage;
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

		java.net.URL fxmlUrl = getClass().getClassLoader().getResource("loginMignon.fxml");
    	System.out.println("🔍 Percorso FXML trovato: " + fxmlUrl);

    	if (fxmlUrl == null) 
        throw new RuntimeException("ERRORE: File FXML non trovato! Controlla il percorso.");
			
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("PaginaIniziale.fxml"));
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
