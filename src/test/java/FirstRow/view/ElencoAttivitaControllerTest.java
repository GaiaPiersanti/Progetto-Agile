package FirstRow.view;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.Test;
import FirstRow.TestFXBase;
import FirstRow.Model.Attivita;
import javafx.scene.control.TableView;

public class ElencoAttivitaControllerTest extends TestFXBase{

	final String SEARCH_FIELD_ID = "#SearchField";
	final String TABLE_ID = "#TabellaAttivita";

	
	@Test
	public void laTabellaNonEVuota() {
		TableView<Attivita> tab = (TableView<Attivita>)find(TABLE_ID);
		assertNotEquals(null,tab.getItems(),"non c'è una tabella");
	}
}
