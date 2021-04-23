package controlador;

import conexionBD.ConexionBD;
import modelo.Alumno;

//DAD - Data Access Object

public class AlumnoDAD {
	ConexionBD conexion;
	
	public AlumnoDAD() {
		conexion = new ConexionBD();
	}
	
	//Metodos para ALTAS, BAJAS, CAMBIOS y CONSULTAS
	
	public boolean insertarRegistro(Alumno a) {
		boolean resultado = false;
		
		//INSERT INTO alumnos VALUES('1','1','1',1,1,'1');
		
		String sql = "INSERT INTO alumnos VALUES('"+a.getNumControl() + "','"+a.getNombre()+"','"+a.getPrimerAp()+"',"+a.getEdad()+","+a.getSemestre()+",'"+a.getCarrera()+"');";
		resultado = conexion.ejecutarInstruccion(sql);
		
		
		
		return resultado;
	}

}
