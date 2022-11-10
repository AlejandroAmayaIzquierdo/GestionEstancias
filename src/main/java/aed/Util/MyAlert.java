package aed.Util;

import aed.main.App;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MyAlert {
	
	public static Alert createAlert(AlertType at, String header, String text) {
		Alert alert = new Alert(at);
		alert.setHeaderText(header);
		alert.setContentText(text);
		alert.initOwner(App.mainStage);
		return alert;

	}

}
