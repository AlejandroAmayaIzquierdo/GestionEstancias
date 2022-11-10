package aed.Hoteles;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import aed.Util.ConexionMySQL;
import aed.editar.EditarController;
import aed.insertar.InsertarController;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HotelesController implements Initializable {
	
	//SQL
	
	private ResultSet estanciasRS;
	

	//Model
	
	private ListProperty<String> hoteles = new SimpleListProperty<>(FXCollections.observableArrayList());
	private StringProperty selectedHotel = new SimpleStringProperty();
	private ArrayList<Estancias> selectedEstancias = new ArrayList<>();
	private IntegerProperty numOfSelectedEstancias = new SimpleIntegerProperty();
	private ListProperty<Estancias> estancias = new SimpleListProperty<>(FXCollections.observableArrayList());

	//View
	
    @FXML
    private BorderPane view;
    @FXML
    private ComboBox<String> hotelesCombo;
    @FXML
    private TableView<Estancias> tablaEstancias;
    @FXML
    private TableColumn<Estancias, String> nombreCollumn,fechaInicioCollumn,fechaFinCollumn,habitacionCollumn;
    @FXML
    private Button insertarButton,editarButton,eliminarButton;
    @FXML
    private CheckBox cheackAll;

	
	public HotelesController() {
		//FXML
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/View.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//SQL
		
		try {
			ResultSet rs = ConexionMySQL.getHoteles();
			while (rs.next()) {
				hoteles.add(rs.getString(1));
			}
			rs.close();
		} catch (SQLException e) {
		}
		
		//Bindings
		
		hotelesCombo.itemsProperty().bind(hoteles);
		
		selectedHotel.bind(hotelesCombo.getSelectionModel().selectedItemProperty());
		
		tablaEstancias.itemsProperty().bind(estancias);
		
		insertarButton.disableProperty().bind(selectedHotel.isEmpty());
		editarButton.disableProperty().bind(numOfSelectedEstancias.greaterThan(1).or(numOfSelectedEstancias.lessThan(1)));
		eliminarButton.disableProperty().bind(numOfSelectedEstancias.lessThan(1));
		
		// cell value factory
		
		tablaEstancias.getSelectionModel().setSelectionMode(
			    SelectionMode.MULTIPLE
			);
		
		nombreCollumn.setCellValueFactory(param -> param.getValue().nombreProperty());
		habitacionCollumn.setCellValueFactory(param -> param.getValue().numHabitacionProperty());
		fechaInicioCollumn.setCellValueFactory(param -> param.getValue().fechaInicioProperty());
		fechaFinCollumn.setCellValueFactory(param -> param.getValue().fechaFinProperty());
		
		// cell factory
		
		
		//listeners
		
		tablaEstancias.getSelectionModel().selectedIndexProperty().addListener(e -> {
			List<Estancias> a = tablaEstancias.getSelectionModel().getSelectedItems();
			selectedEstancias.clear();
			if(!a.isEmpty()) {
				selectedEstancias.addAll(a);
			}
			numOfSelectedEstancias.set(selectedEstancias.size());
		});
		
		
		selectedHotel.addListener((o,ov,nv) -> {
			loadEstacias(nv);
		});
		
		
		
	}
	
	public void loadEstacias(String nv) {
		estanciasRS = ConexionMySQL.getEstanciasByHotel(nv);
		estancias.clear();
		try {
			while(estanciasRS.next()) {
				estancias.add(
						new Estancias(
								estanciasRS.getString(1),
								estanciasRS.getString(2),
								estanciasRS.getString(5),
								estanciasRS.getString(3),
								estanciasRS.getString(4),
								selectedHotel.get())
						);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    
    @FXML
    void onCheackAllAction(ActionEvent event) {
    }
	
    @FXML
    void selectedHotelAction(ActionEvent event) {

    }
    
    @FXML
    void insertarAction(ActionEvent event) {
    	Stage stage = new Stage();
    	stage.setTitle("Nueva Estancia");
    	InsertarController insertarController = new InsertarController(stage,selectedHotel.get(),this);
    	stage.setScene(new Scene(insertarController.getView()));
    	stage.showAndWait();
    }
	
    @FXML
    void editarAction(ActionEvent event) {
    	Stage stage = new Stage();
    	stage.setTitle("Editar Estancia");
    	EditarController editarController = new EditarController(stage,selectedEstancias.get(0),this);
    	stage.setScene(new Scene(editarController.getView()));
    	stage.showAndWait();
    }

    @FXML
    void eliminarAction(ActionEvent event) {
    	List<Estancias> toDelete = tablaEstancias.getSelectionModel().getSelectedItems();
    	for(int i = 0 ; i < toDelete.size(); i++) {
        	ConexionMySQL.deletedStancias(toDelete.get(i).getId());
        	
    	}
    	estancias.removeAll(toDelete);

    }
    
    public BorderPane getView() {
    	return this.view;
    }





}
