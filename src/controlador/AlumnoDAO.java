package controlador;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import conexionBD.ConexionBD;
import modelo.Alumno;

//DAD - Data Access Object

public class AlumnoDAO {
	ConexionBD conexion;
	
	public AlumnoDAO() {
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
	
	public boolean eliminarRegistro(String nc) {
		//delete from alumnos where NumComtrol = '01';
		boolean resultado = false;
		String sql = "delete from alumnos where NumComtrol = \""+nc+"\"";
		resultado = conexion.ejecutarInstruccion(sql);
		
		return resultado;
	}
	
	public boolean modificarRegistro(Alumno a, boolean flags[]) {
        // UPDATE alumnos SET nombre.. primer ap.. semestre = '10';
        
        boolean resultado = false;
        int primero = 0;
        String sql = "UPDATE alumnos SET ";
        
        if (flags[0]) {
			if (primero!=0) {
				sql+=", ";
			}
			primero+=1;
			sql+=("Nombre='"+a.getNombre()+"'");
		}
		if (flags[1]) {
			if (primero!=0) {
				sql+=", ";
			}
			primero+=1;
			sql+=("PrimerAp='"+a.getPrimerAp()+"'");
		}
		if (flags[3]) {
			if (primero!=0) {
				sql+=", ";
			}
			primero+=1;
			sql+=("Edad="+a.getEdad());
		}
		if (flags[4]) {
			if (primero!=0) {
				sql+=", ";
			}
			primero+=1;
			sql+=("Semestre="+a.getSemestre());
		}
		if (flags[5]) {
			if (primero!=0) {
				sql+=", ";
			}
			primero+=1;
			sql+=("Carrera='"+a.getCarrera()+"'");
		}
		sql+=("WHERE NumComtrol = '"+a.getNumControl()+"'");
		
		resultado = conexion.ejecutarInstruccion(sql);
        return resultado;
    }
	
	//--- Consultas ---
	
	public ArrayList<Alumno> buscarAlumnos(String filtro){
		ArrayList<Alumno> listaAlumnos = new ArrayList<>();
		
		//"select * from alumnos"
		
		String sql = "select * from alumnos";
		conexion.ejecutarConsulta(sql);
		ResultSet rs = conexion.ejecutarConsulta(sql);
		
		
		
		try {
			if(rs.next()) {
				do {
					listaAlumnos.add(new Alumno(rs.getString(1),rs.getString(2), rs.getString(3), rs.getString(4)
							, rs.getByte(5), rs.getByte(6), rs.getString(7)));
				}while(rs.next());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listaAlumnos;
	}

}
