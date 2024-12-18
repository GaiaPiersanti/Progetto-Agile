package FirstRow;

import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

import FirstRow.view.BoxIniziale;
import FirstRow.view.ConfirmBox;
import FirstRow.view.LoginMignonController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class MainFx extends Application {
	 private Stage primaryStage;
	   Stage window;
	   Scene scene1;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
       this.primaryStage.setTitle("Log-In");
		try {
			 window = primaryStage;
		        window.setOnCloseRequest(e -> {
		            e.consume();
		            closeProgamme();}
		        );
		        window.setTitle("Benvenuto");

		        scene1 = new Scene(BoxIniziale.setupBox(window), 700, 500);

		        window.setScene(scene1);

		        window.show();
		/* FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainFx.class.getResource("view/loginMignon.fxml"));
			AnchorPane rootLayout = (AnchorPane) loader.load();
			Scene scene = new Scene(rootLayout);
           primaryStage.setScene(scene);
           LoginMignonController controller = loader.getController();
           controller.setDialogStage(primaryStage);
           primaryStage.show();*/
		} catch (Exception e) {
			System.out.println("errore di I/O");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	public void closeProgamme(){
        Boolean answer = ConfirmBox.display("Exit", "Vuoi annulare l'operazione?");
        if(answer){
            window.close();
        }
    }
	
}
