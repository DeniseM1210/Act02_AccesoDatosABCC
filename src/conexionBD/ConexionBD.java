package conexionBD;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConexionBD {
	private Connection conexion;
	private Statement stn; //NO es tan seguro ya que permite SQL injection
	private ResultSet rs;
	
	public ConexionBD() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			String URL = "jdbc:mysql://localhost:3306/Esccuela_Topicos";
			
			conexion = DriverManager.getConnection(URL, "root", "nanami777");
		
			System.out.println("Conexion establecida");
		} catch (ClassNotFoundException e) {
			System.out.println("Error de DRIVER");
		} catch (SQLException e) {
			System.out.println("Error de conexion a MySQL o de la BD");
		}
	}
	
	public void cerrarConexionBD() {
		try {
			stn.close();
			conexion.close();
		} catch (SQLException e) {
			System.out.println("Error al cerrar la conexion");
			e.printStackTrace();
		}
		
	}
	
	//----- METODO PARA OPERACIONES DDL Y DML (ABC- Altas, bajas y cambios) -----
	public boolean ejecutarInstruccion(String sql) {
		try {
			stn = conexion.createStatement();
			int resultado = stn.executeUpdate(sql);
			return resultado == 1 ? true: false;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("No se pudo ejecutar la instruccion");
			return false;
		}
	}
	
	
	//----- METODO PARA OPERACIONES DE CONSULTA (consultas) -----
	public ResultSet ejecutarConsulta(String sql) {
		try {
			stn = conexion.createStatement();
			rs = stn.executeQuery(sql);	
		} catch (SQLException e) {
			System.out.println("NO se pudo ejecutar la instruccion");
			e.printStackTrace();
		}
		return rs;
	}
	
	
	
	public static void main(String[] args) {
		new ConexionBD();
	}

}
