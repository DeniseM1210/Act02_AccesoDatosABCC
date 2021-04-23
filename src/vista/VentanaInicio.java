package vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import controlador.AlumnoDAD;
import modelo.Alumno;

class ventana extends JFrame{
	JTextField cajaNoControl, cajaNombre, cajaAP, cajaAM;
	JButton btnAltas, btnBajas, btn
	public ventana() {
		
	}
}

public class VentanaInicio {

	public static void main(String[] args) {
		//Suponiendo que los datos vienen desde la interfaz grafica
		
		Alumno a = new Alumno("01", "Han", "Jisung", "-", (byte)20, (byte)4, "ISC");
		
		AlumnoDAD aDAD = new AlumnoDAD();
		
		System.out.println(aDAD.insertarRegistro(a) ? "EXITO": "Me cambio de carrera");
	}

}
