package FirstRow.view;
import com.codingds.controller.*;
import com.codingds.mysqlClient.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class RegistrationBox {
    static String username ;
    static Scene postRegistrazione;
    public static Scene erroreRegistrazione;
    static StackPane layoutPRegsitarzione, layoutErrore;
    static Stage windowPRegistrazione;
    public static Scene scene3;

    public static GridPane boxRegistrazione(Stage stage) {

        GridPane grid = new GridPane();
        Label nlabel = new Label("Username");

        grid.setPadding(new Insets(200, 100, 100, 150));
        grid.setVgap(10);
        grid.setHgap(10);
        GridPane.setConstraints(nlabel, 4, 0);

        TextField usernameInput = new TextField("root");
        usernameInput.setPromptText("username");
        GridPane.setConstraints(usernameInput, 5, 0);



        Label pLabel = new Label("Password");
        GridPane.setConstraints(pLabel, 4, 2);

        Label emailLabel = new Label("Email");
        GridPane.setConstraints(emailLabel, 4, 1);
        /*
        Label ripetiPLabel = new Label("Ripeti Password");
        GridPane.setConstraints(ripetiPLabel, 4, 3);
        */
        TextField pInput = new TextField("Password");
        pInput.setPromptText("password");
        GridPane.setConstraints(pInput, 5, 2);

        /*
        TextField ripetiPInput = new TextField("Password");
        ripetiPInput.setPromptText("password");
        GridPane.setConstraints(ripetiPInput, 5, 3);

         */

        TextField emailInput = new TextField("Email");
        emailInput.setPromptText("email");
        GridPane.setConstraints(emailInput, 5, 1);

        Button buttonRegistrazione = new Button("Registrazione");
        GridPane.setConstraints(buttonRegistrazione, 5, 3);

        Button buttonIndietro = new Button("Torna Indietro");
        GridPane.setConstraints(buttonIndietro, 5, 4);


        buttonIndietro.setOnAction(_ ->  stage.setScene( scene3 = new Scene(BoxIniziale.setupBox(stage), 700, 500)));


        buttonRegistrazione.setOnAction(_ -> {
            try {
                Database.connection(pInput.getText(), emailInput.getText(),usernameInput.getText(), null);
                layoutPRegsitarzione = new StackPane();
                Label registartionSuccessfull = new Label();
                registartionSuccessfull.setText("Registrazione Avvenuta Con Successo");
                layoutPRegsitarzione.getChildren().add(registartionSuccessfull);
                postRegistrazione = new Scene
                        (layoutPRegsitarzione, 500, 500);
                windowPRegistrazione = new Stage();
                windowPRegistrazione.setTitle("Registrazione Avvenuta");
                windowPRegistrazione.setScene(postRegistrazione);
                windowPRegistrazione.show();

            }


            catch (Exception e) {

            }
        });

        grid.getChildren().addAll(nlabel, usernameInput, pLabel, pInput, buttonRegistrazione, emailInput, emailLabel, buttonIndietro);

        return grid;
    }

}
