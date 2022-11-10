package aed.Util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import aed.main.App;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ConexionMySQL {
	

	
	public static ResultSet getHoteles() {
		Connection conn;
		try {
			conn = getProperties();
			Statement stmt = conn.createStatement();
			return stmt.executeQuery("Select distinct codHotel from habitaciones");
		} catch (IOException | SQLException e) {
		}
		return null;
	}
	
	public static void InsertarEstacias(
			String nombre,
			String fechaIni,String fechaFin,
			String numHabitacion,
			String codHotel) {
		try {
			Connection conn = getProperties();
			PreparedStatement stmtPrepared = 
					conn.prepareStatement(
							"INSERT INTO `estancias`(`nombre`, `fechaInicio`, `fechaFin`, `numHabitacion`, `codHotel`) VALUES (?,?,?,?,?)",
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			stmtPrepared.setString(1, nombre);
			stmtPrepared.setString(2, fechaIni);
			stmtPrepared.setString(3, fechaFin);
			stmtPrepared.setString(4, numHabitacion);
			stmtPrepared.setString(5, codHotel);
			stmtPrepared.execute();
			
			stmtPrepared.close();
			conn.close();
		} catch (IOException | SQLException e) {
			MyAlert.createAlert(AlertType.ERROR, "Error en la Base De Datos", e.getMessage()).showAndWait();
		}
	}
	
	public static void updateEstancia(
			String id,
			String nombre,
			String fechaIni,
			String fechaFin,
			String numHabitacion,
			String codHotel) {
		try {
			Connection conn = getProperties();
			PreparedStatement stmtPrepared = 
					conn.prepareStatement(
							"UPDATE `estancias` "
							+ "SET `nombre`= ?,"
							+ "`fechaInicio`= ?,"
							+ "`fechaFin`= ?,"
							+ "`numHabitacion`= ?,"
							+ "`codHotel`= ?"
							+ " WHERE estancias.id = ?",
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			stmtPrepared.setString(1, nombre);
			stmtPrepared.setString(2, fechaIni);
			stmtPrepared.setString(3, fechaFin);
			stmtPrepared.setString(4, numHabitacion);
			stmtPrepared.setString(5, codHotel);
			stmtPrepared.setString(6, id);
			stmtPrepared.execute();
			
			stmtPrepared.close();
			conn.close();
		} catch (IOException | SQLException e) {
			MyAlert.createAlert(AlertType.ERROR, "Error en la Base De Datos", e.getMessage()).showAndWait();
			
		}
	}

	public static void deletedStancias(String id) {
		try {
			Connection conn = getProperties();
			PreparedStatement stmtPrepared = 
					conn.prepareStatement(
							"delete from estancias where id = ?",
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			stmtPrepared.setString(1, id);
			stmtPrepared.execute();
			
			stmtPrepared.close();
			conn.close();
		} catch (IOException | SQLException e) {
			MyAlert.createAlert(AlertType.ERROR, "Error en la Base De Datos", e.getMessage()).showAndWait();
			
		}
	}
	
	public static ResultSet getEstanciasByHotel(String hotel) {
		Connection conn;
		try {
			conn = getProperties();
			PreparedStatement stmtPrepared = 
					conn.prepareStatement(
							"select distinct * from estancias where codHotel = ? order by nombre",
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			stmtPrepared.setString(1, hotel);
//			System.out.println(stmtPrepared.getResultSetType());
			return stmtPrepared.executeQuery();
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static Connection getProperties() throws IOException, SQLException {
		Properties conexionProperty = new Properties();
		conexionProperty.load(ConexionMySQL.class.getResourceAsStream("/MySQL/conexionDB.properties"));
		
		//base de datos
		Connection conn = DriverManager.getConnection(
				conexionProperty.getProperty("url"),
				conexionProperty.getProperty("usuario"),
				conexionProperty.getProperty("password")
		);
		return conn;
	}
	
}
