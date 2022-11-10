package aed.insertar;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import aed.Hoteles.Estancias;
import aed.Hoteles.HotelesController;
import aed.Util.ConexionMySQL;
import aed.Util.DateRefactor;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class InsertarController implements Initializable{
	
	private HotelesController hotelesController;
	
	
	private Stage stage;
	
	private String selectedHotel;
	
	//Model
	
	private StringProperty nombre = new SimpleStringProperty();
	private IntegerProperty numeroHabitacion = new SimpleIntegerProperty();
	private StringProperty fechaIni = new SimpleStringProperty();
	private StringProperty fechaF = new SimpleStringProperty();
	
	//View
	
    @FXML
    private DatePicker fechaFin;

    @FXML
    private DatePicker fechaInicio;

    @FXML
    private TextField nombreEstancia;

    @FXML
    private TextField numHabitacion;

    @FXML
    private BorderPane view;
	
	public InsertarController(Stage stage,String hotel,HotelesController h) {
		this.stage = stage;
		this.selectedHotel = hotel;
		this.hotelesController = h;
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/InsertView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//Bindings
		
		nombre.bind(nombreEstancia.textProperty());
		numHabitacion.textProperty().bindBidirectional(numeroHabitacion,new NumberStringConverter());
		fechaIni.bind(fechaInicio.getEditor().textProperty());
		fechaF.bind(fechaFin.getEditor().textProperty());
	}
	

    @FXML
    void aceptarAction(ActionEvent event) {
    	ConexionMySQL.InsertarEstacias(
    			nombre.get(), 
    			DateRefactor.fromString(fechaIni.get()),
    			DateRefactor.fromString(fechaF.get()),
    			numeroHabitacion.get() + "",
    			this.selectedHotel);
    	
    	hotelesController.loadEstacias(selectedHotel);
    	
    	this.stage.close();
    }

    @FXML
    void cancelarAction(ActionEvent event) {
    	this.stage.close();
    }
	
	public BorderPane getView() {
		return this.view;
	}


}
