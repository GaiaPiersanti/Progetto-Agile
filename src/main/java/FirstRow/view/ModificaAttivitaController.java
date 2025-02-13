package FirstRow.view;

import FirstRow.Model.Attivita;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModificaAttivitaController {

    @FXML
    private TextField nomeAttivita;
    @FXML
    private ChoiceBox<String> categoriaAttivita;
    @FXML
    private DatePicker scadenzaAttivita;
    @FXML
    private ChoiceBox<String> prioritaAttivita;

    private Attivita attivitaSelezionata;
    private ElencoAttivitaController elencoController;
    private Stage stage;

    public void setAttivita(Attivita selectedAttivita) {
        this.attivitaSelezionata = selectedAttivita;
        nomeAttivita.setText(selectedAttivita.getNome());
        categoriaAttivita.setValue(selectedAttivita.getCategoria());
        scadenzaAttivita.setValue(java.time.LocalDate.parse(selectedAttivita.getScadenza()));
        prioritaAttivita.setValue(selectedAttivita.getPriorita());
    }

    public void setElencoController(ElencoAttivitaController elencoAttivitaController) {

        this.elencoController = elencoAttivitaController;
        
    }

      public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initialize(){
        categoriaAttivita.getItems().addAll("Lavoro", "Studio", "Casa");
        prioritaAttivita.getItems().addAll("Alta", "Media", "Bassa");

    }


    @FXML
    private void handleSalvaModifiche(ActionEvent event) {
        if (attivitaSelezionata != null) {
            Attivita nuovaAttivita = new Attivita(
                nomeAttivita.getText(),
                categoriaAttivita.getValue(),
                scadenzaAttivita.getValue().toString(),
                prioritaAttivita.getValue()
            );

            elencoController.aggiornaAttivita(attivitaSelezionata, nuovaAttivita);

            if (stage != null) {
                stage.close();
            }
        }
    }

}
