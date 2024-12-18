package FirstRow.view;

import java.io.IOException;

import FirstRow.MainFx;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



public class BoxIniziale {

    public static VBox setupBox(Stage stage){

        VBox vBox = new VBox(30);
        vBox.setAlignment(Pos.CENTER);

        Button registrationB = new Button("Registrazione");
        Button loginB = new Button("Login");
        Button recuperoB = new Button("Recupero Password");

        vBox.getChildren().addAll(registrationB, loginB, recuperoB);



        Scene scene2 = new Scene(RegistrationBox.boxRegistrazione(stage), 700, 500);


        registrationB.setOnAction(e -> {
            stage.setTitle("Registrazione");
            stage.setScene(scene2);});
        loginB.setOnAction(e -> {
            stage.setTitle("Registrazione");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFx.class.getResource("view/loginMignon.fxml"));
    			AnchorPane rootLayout;
				try {
					rootLayout = (AnchorPane) loader.load();
				
    			Scene sceneLI = new Scene(rootLayout);
               stage.setScene(sceneLI);
               LoginMignonController controller = loader.getController();
               controller.setDialogStage(stage);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
           /* stage.setScene(scene2);*/});

        return vBox;

    }

}
