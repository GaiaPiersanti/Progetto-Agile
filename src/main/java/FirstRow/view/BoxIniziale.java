package FirstRow.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

        return vBox;

    }

}
