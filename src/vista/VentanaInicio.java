package vista;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import controlador.AlumnoDAO;
import modelo.Alumno;

class ventana extends JFrame implements ActionListener{
	JTextField cajaNoControl, cajaNombre, cajaAP, cajaAM;
	JButton btnAgregar, btnBorrar, btnCancelar;
	JComboBox comboSemestre, comboCarrera;
	JMenuBar menu;
	JMenu menuBDAlumnos;
	
	JInternalFrame IF_AltaAlumnos;
	
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
		txtNombres.setBounds(50,47, 150, 20);
		add(txtNombres);
		
		JLabel txtAP = new JLabel("Apellido Paterno: ");
		txtAP.setBounds(50, 72, 150, 20);
		add(txtAP);
		
		JLabel txtAM = new JLabel("Apellido Materno: ");
		txtAM.setBounds(50, 97, 150, 20);
		add(txtAM);
		
		JLabel txtSemestre = new JLabel("Semestre: ");
		txtSemestre.setBounds(50, 122, 150, 20);
		add(txtSemestre);
		
		JLabel txtCarrera = new JLabel("Carrera: ");
		txtCarrera.setBounds(50, 147, 150, 20);
		add(txtCarrera);
		
		cajaNoControl = new JTextField(5);
		cajaNoControl.setBounds(165, 22, 150, 20);
		add(cajaNoControl);
		cajaNombre = new JTextField(5);
		cajaNombre.setBounds(110, 47, 205, 20);
		add(cajaNombre);
		cajaAP = new JTextField(5);
		cajaAP.setBounds(155, 72, 160, 20);
		add(cajaAP);
		cajaAM = new JTextField(5);
		cajaAM.setBounds(155, 97, 160, 20);
		add(cajaAM);
		
		String semestre[] = {"Elige Semestre...", "Primer", "Segundo", "Tercero", "Cuarto", "Quinto", "Sexto", "Septimo", "Octavo", "Noveno"};
		comboSemestre = new JComboBox<String>(semestre);
		comboSemestre.setBounds(155, 122, 160, 20);
		add(comboSemestre);
		
		String carreras[] = {"Eige carrera..." , "ISC", "IM", "IA", "LA", "LC"};
		comboCarrera = new JComboBox<String>(carreras);
		comboCarrera.setBounds(155, 147, 160, 20);
		add(comboCarrera);
		
		btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(this);
		btnAgregar.setBounds(320, 30, 100, 30);
		add(btnAgregar);
		
		btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(this);
		btnBorrar.setBounds(320, 70, 100, 30);
		add(btnBorrar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this);
		btnCancelar.setBounds(320, 110, 100, 30);
		add(btnCancelar);
		
		JTable tablaAlumnos = new JTable(6,6);
		JScrollPane scrollPane = new JScrollPane(tablaAlumnos);
		tablaAlumnos.setBounds(10, 180, 500, 100);
		add(tablaAlumnos);
		
		String controlador = "com.mysql.cj.jdbc.Driver";
		String URL = "jdbc:mysql://localhost:3306/Esccuela_Topicos";
		String consulta = "select * from alumnos";
		
		ResultSetTableModel modeloDatos = null;
		try {
			modeloDatos = new ResultSetTableModel(controlador, URL, consulta);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		tablaAlumnos.setModel(modeloDatos);
		
		setVisible(true);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAgregar) {
			String carrera = (String) comboCarrera.getSelectedItem();
			Alumno a = new Alumno();
			AlumnoDAO aDAD = new AlumnoDAO();
			a.setNumControl(cajaNoControl.getText());
			a.setNombre(cajaNombre.getText());
			a.setPrimerAp(cajaAP.getText());
			a.setSegundoAp(cajaAM.getText());
			a.setSemestre(comboSemestre.getSelectedIndex());
			a.setCarrera(carrera);
			
			System.out.println(aDAD.insertarRegistro(a) ? "EXITO": "Me cambio de carrera");
		}
		else if(e.getSource() == btnBorrar) {
			cajaNoControl.setText("");
			cajaNombre.setText("");
			cajaAP.setText("");
			cajaAM.setText("");
		}
		else if(e.getSource() == btnCancelar) {
			setVisible(false);
		}
		
	}
}

public class VentanaInicio {

	public static void main(String[] args) {
		//Suponiendo que los datos vienen desde la interfaz grafica
		
		//Alumno a = new Alumno("01", "Felix", "Lee", "-", (byte)20, (byte)10, "ISC");
		
		//AlumnoDAD aDAD = new AlumnoDAD();
		
		//System.out.println(aDAD.insertarRegistro(a) ? "EXITO": "Me cambio de carrera");
		
		
		//System.out.println(aDAD.eliminarRegistro("01") ? "EXITO": "No funciono");
		
		//Alumno a2 = new Alumno("01", "Felix", "Lee", "-", (byte)20, (byte)6, "ISC");
		//System.out.println(aDAD.modificarRegistro(a2) ? "EXITO" : "No funciono");
		
		
		//System.out.println(aDAD.buscarAlumnos(null) ? "EXITO BUSCAR" : "No funciono :C");
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new ventana();
				
			}
		});
	}
}

