package vista;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import controlador.AlumnoDAD;
import modelo.Alumno;

class ventana extends JFrame implements ActionListener{
	JTextField cajaNoControl, cajaNombre, cajaAP, cajaAM;
	JButton btnAgregar, btnBorrar, btnCancelar;
	JComboBox comboSemestre, comboCarrera;
	
	public ventana() {
		getContentPane().setLayout(new FlowLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(550, 350);
		setTitle("Menú BD Alumnos");
		setLocationRelativeTo(null);
		setLayout(null);
		
		JLabel txtTexto1 = new JLabel("Altas Alumnos");
		txtTexto1.setBounds(200, 0, 150, 20);
		txtTexto1.setFont(new Font("Serif", Font.BOLD, 22));
		add(txtTexto1);
		
		JLabel txtNoControl = new JLabel("Número de Control: ");
		txtNoControl.setBounds(50, 22, 150, 20);
		add(txtNoControl);
		
		JLabel txtNombres = new JLabel("Nombres: ");
		txtNombres.setBounds(50,42, 150, 20);
		add(txtNombres);
		
		JLabel txtAP = new JLabel("Apellido Paterno: ");
		txtAP.setBounds(50, 62, 150, 20);
		add(txtAP);
		
		JLabel txtAM = new JLabel("Apellido Materno: ");
		txtAM.setBounds(50, 82, 150, 20);
		add(txtAM);
		
		JLabel txtSemestre = new JLabel("Semestre: ");
		txtSemestre.setBounds(50, 102, 150, 20);
		add(txtSemestre);
		
		JLabel txtCarrera = new JLabel("Carrera: ");
		txtCarrera.setBounds(50, 122, 150, 20);
		add(txtCarrera);
		
		cajaNoControl = new JTextField(5);
		cajaNoControl.setBounds(165, 22, 150, 20);
		add(cajaNoControl);
		cajaNombre = new JTextField(5);
		cajaNombre.setBounds(110, 42, 205, 20);
		add(cajaNombre);
		cajaAP = new JTextField(5);
		cajaAP.setBounds(155, 62, 160, 20);
		add(cajaAP);
		cajaAM = new JTextField(5);
		cajaAM.setBounds(155, 82, 160, 20);
		add(cajaAM);
		
		String semestre[] = {"Elige Semestre...", "Primer", "Segundo", "Tercero", "Cuarto", "Quinto", "Sexto", "Septimo", "Octavo", "Noveno"};
		comboSemestre = new JComboBox<String>(semestre);
		comboSemestre.setBounds(155, 102, 160, 20);
		add(comboSemestre);
		
		String carreras[] = {"Eige carrera..." , "ISC", "IM", "IA", "LA", "LC"};
		comboCarrera = new JComboBox<String>(carreras);
		comboCarrera.setBounds(155, 122, 160, 20);
		add(comboCarrera);
		
		btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(this);
		btnAgregar.setBounds(320, 30, 100, 30);
		add(btnAgregar);
		
		btnBorrar = new JButton("Borrar");
		btnBorrar.setBounds(320, 70, 100, 30);
		add(btnBorrar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(320, 110, 100, 30);
		add(btnCancelar);
		
		JTable tablaAlumnos = new JTable(6,6);
		tablaAlumnos.setBounds(10, 150, 500, 100);
		add(tablaAlumnos);
		
		setVisible(true);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAgregar) {
			String carrera = (String) comboCarrera.getSelectedItem();
			Alumno a = new Alumno();
			AlumnoDAD aDAD = new AlumnoDAD();
			a.setNumControl(cajaNoControl.getText());
			a.setNombre(cajaNombre.getText());
			a.setPrimerAp(cajaAP.getText());
			a.setSegundoAp(cajaAM.getText());
			a.setSemestre(comboSemestre.getSelectedIndex());
			a.setCarrera(carrera);
			
			System.out.println(aDAD.insertarRegistro(a) ? "EXITO": "Me cambio de carrera");
		}
		
	}
}

public class VentanaInicio {

	public static void main(String[] args) {
		//Suponiendo que los datos vienen desde la interfaz grafica
		
		/*Alumno a = new Alumno("01", "Han", "Jisung", "-", (byte)20, (byte)4, "ISC");
		
		AlumnoDAD aDAD = new AlumnoDAD();
		
		System.out.println(aDAD.insertarRegistro(a) ? "EXITO": "Me cambio de carrera");
		*/
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new ventana();
				
			}
		});
	}

}
