package FirstRow;

import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import FirstRow.View.LoginMignonController;

public class MainFx extends Application {
	 private Stage primaryStage;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
       this.primaryStage.setTitle("Log-In");
		try {
		 FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainFx.class.getResource("view/loginMignon.fxml"));
			AnchorPane rootLayout = (AnchorPane) loader.load();
			Scene scene = new Scene(rootLayout);
           primaryStage.setScene(scene);
           LoginMignonController controller = loader.getController();
           controller.setDialogStage(primaryStage);
           primaryStage.show();
		} catch (IOException e) {
			System.out.println("errore di I/O");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
