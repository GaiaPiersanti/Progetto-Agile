package FirstRow;

import java.util.concurrent.TimeoutException;

import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

public class TestFXBase extends ApplicationTest {

	
	@Before
	public void setUpClass() throws Exception{
		ApplicationTest.launch(MainFx.class);
	}
	
	/*@Override
	public void start(Stage stage) throws Exception{
		stage.show();
	}*/
	
	@AfterEach
	public void afterEachTest() throws TimeoutException{
		FxToolkit.hideStage();
		release(new KeyCode[] {});
		release(new MouseButton[] {});
	}
	
	public<T extends Node> T find(final String query) {
		return (T) lookup(query).queryAll().iterator().next();
	}
}
