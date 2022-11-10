package aed.main;

import aed.Hoteles.HotelesController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
	
	public static Stage mainStage;
	
	private HotelesController hotelesController;

	@Override
	public void start(Stage stage) throws Exception {
		
		this.mainStage = stage;
		
		hotelesController = new HotelesController();
		
		stage.setTitle("Hoteles");
		stage.setScene(new Scene(hotelesController.getView()));
		stage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
