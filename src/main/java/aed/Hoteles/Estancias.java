package aed.Hoteles;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Estancias {
	
	private StringProperty id = new SimpleStringProperty();
	private StringProperty nombre = new SimpleStringProperty();
	private StringProperty numHabitacion = new SimpleStringProperty();
	private StringProperty fechaInicio = new SimpleStringProperty();
	private StringProperty fechaFin = new SimpleStringProperty();
	private StringProperty codHotel = new SimpleStringProperty();
	
	public Estancias(String id,String nombre,String numH,String ini,String fin,String codHotel) {
		setId(id);
		setNombre(nombre);
		setNumHabitacion(numH);
		setFechaInicio(ini);
		setFechaFin(fin);
		setCodHotel(codHotel);
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getId();
	}
	

	public final StringProperty nombreProperty() {
		return this.nombre;
	}
	

	public final String getNombre() {
		return this.nombreProperty().get();
	}
	

	public final void setNombre(final String nombre) {
		this.nombreProperty().set(nombre);
	}

	public final StringProperty numHabitacionProperty() {
		return this.numHabitacion;
	}
	

	public final String getNumHabitacion() {
		return this.numHabitacionProperty().get();
	}
	

	public final void setNumHabitacion(final String numHabitacion) {
		this.numHabitacionProperty().set(numHabitacion);
	}
	

	public final StringProperty fechaInicioProperty() {
		return this.fechaInicio;
	}
	

	public final String getFechaInicio() {
		return this.fechaInicioProperty().get();
	}
	

	public final void setFechaInicio(final String fechaInicio) {
		this.fechaInicioProperty().set(fechaInicio);
	}
	

	public final StringProperty fechaFinProperty() {
		return this.fechaFin;
	}
	

	public final String getFechaFin() {
		return this.fechaFinProperty().get();
	}
	

	public final void setFechaFin(final String fechaFin) {
		this.fechaFinProperty().set(fechaFin);
	}
	public final StringProperty idProperty() {
		return this.id;
	}
	
	public final String getId() {
		return this.idProperty().get();
	}
	
	public final void setId(final String id) {
		this.idProperty().set(id);
	}
	public final StringProperty codHotelProperty() {
		return this.codHotel;
	}
	
	public final String getCodHotel() {
		return this.codHotelProperty().get();
	}
	
	public final void setCodHotel(final String codHotel) {
		this.codHotelProperty().set(codHotel);
	}
	
	
	
	
	
	
	

}
