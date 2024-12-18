package FirstRow.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class ConfirmBox {

    static boolean answer;

    public static boolean display(String title, String message){
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setMinWidth(250);

        Label lable = new Label();
        lable.setText(message);

        VBox layout1 = new VBox(10);

        Button button = new Button("Si");
        Button button2 = new Button("No");

        button.setOnAction(e -> {
            answer = true;
            stage.close();
        });

        button2.setOnAction(e -> {
            answer = false;
            stage.close();
        });
        layout1.getChildren().addAll(lable, button, button2);
        layout1.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout1);
        stage.setScene(scene);
        stage.showAndWait();

        return answer;
    }
}

