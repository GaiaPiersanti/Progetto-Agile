package FirstRow.controller;

import com.codingds.view.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;




public class Main extends Application{
    Stage window;
    Scene scene1, scene3, scene4;
    public static void main(String[] args) throws Exception {
        Application.launch(args);
    }
    @Override
    public void start(Stage primaryStage)throws Exception{
        window = primaryStage;
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgamme();}
        );
        window.setTitle("Benvenuto");

        scene1 = new Scene(BoxIniziale.setupBox(window), 700, 500);

        window.setScene(scene1);

        window.show();


    }



    public void closeProgamme(){
        Boolean answer = ConfirmBox.display("Exit", "Vuoi annulare l'operazione?");
        if(answer){
            window.close();
        }
    }
}