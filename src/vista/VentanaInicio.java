package vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

import controlador.AlumnoDAO;
import modelo.Alumno;

class ventana extends JFrame implements ActionListener{
	JTextField cajaNoControl, cajaNombre, cajaAP, cajaAM;
	JButton btnInteraccion, btnBorrar, btnCancelar, btnBuscar;
	JComboBox comboEdad, comboSemestre, comboCarrera;
	JMenuBar menu;
	JMenu altasAl, bajasAl, cambiosAl, consultasAl;
	JMenuItem menuItemAltas, menuItemBajas, menuItemCambios, menuItemConsultas;
	JInternalFrame IF_AltaAlumnos, IF_BajaAlumnos, IF_CambiosAlumnos, IF_ConsultasAlumnos, tablaAl;
	
	JLabel txtNumeroDeControl,txtNombres,txtApellidoPaterno,txtApellidoMaterno,txtEdad,txtSemestre,txtCarrera;
	JCheckBox cbTodos,cbNoControl,cbNombres,cbApellidoP,cbApellidoM,cbEdad,cbSemestre,cbCarrera;
	Icon iconoBusqueda = new ImageIcon("./imagenes/buscar.PNG");//imagen
	
	JTable tablaAlumnos;
	JScrollPane scrollPane = new JScrollPane();
	
	public ventana() {
		getContentPane().setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(570, 350);
		setTitle("Menú BD Alumnos");
		setLocationRelativeTo(null);
		
		setVisible(true);
		
		reasignar();
		
		JDesktopPane desktopPane = new JDesktopPane();
		
		IF_AltaAlumnos = new JInternalFrame();
		JPanel panelAltas[]= defPanel(IF_AltaAlumnos, "Altas Alumnos", "ALTAS ALUMNOS", Color.GREEN);
		IF_BajaAlumnos = new JInternalFrame();
		JPanel panelBajas[]= defPanel(IF_BajaAlumnos, "Bajas Alumnos", "BAJAS ALUMNOS", Color.RED);
		IF_CambiosAlumnos = new JInternalFrame();
		JPanel panelCambios[]= defPanel(IF_CambiosAlumnos, "Modificaciones Alumnos", "MODIFICACIONES ALUMNOS", Color.ORANGE);
		IF_ConsultasAlumnos = new JInternalFrame();
		JPanel panelConsultas[]= defPanel(IF_ConsultasAlumnos, "Consultas Alumnos", "CONSULTAS ALUMNOS", Color.BLUE);
		
		tablaAl = new JInternalFrame();
		tablaAl.getContentPane().setLayout(null);
		tablaAl.setDefaultCloseOperation(HIDE_ON_CLOSE);
		tablaAl.setSize(567,137);
		tablaAl.setLocation(0, 290);
		tablaAl.setTitle("Tabla");
		
		menu = new JMenuBar();
		altasAl = new JMenu("Altas");
			menuItemAltas = new JMenuItem("Agregar Alumno");
			altasAl.add(menuItemAltas);
			menuItemAltas.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					actualizarTabla("com.mysql.cj.jdbc.Driver","jdbc:mysql://localhost:3306/Esccuela_Topicos","SELECT * FROM Alumnos");
					IF_AltaAlumnos.setVisible(true);
					IF_BajaAlumnos.setVisible(false);
					IF_CambiosAlumnos.setVisible(false);
					IF_ConsultasAlumnos.setVisible(false);
					setFormularioEnabled(false);
					cajaNoControl.setEditable(true);
					defPosicionamiento(panelAltas[1], false, true, "AGREGAR");
					
				}
			});
			
		bajasAl = new JMenu("Bajas");
			menuItemBajas = new JMenuItem("Eliminar Alumno");
			bajasAl.add(menuItemBajas);
			menuItemBajas.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					actualizarTabla("com.mysql.cj.jdbc.Driver","jdbc:mysql://localhost:3306/Esccuela_Topicos","SELECT * FROM Alumnos");
					IF_AltaAlumnos.setVisible(false);
					IF_BajaAlumnos.setVisible(true);
					IF_CambiosAlumnos.setVisible(false);
					IF_ConsultasAlumnos.setVisible(false);
					metodoRestablecerTodo(cajaNombre, cajaAP, cajaAM, comboEdad, comboSemestre, comboCarrera);
					setFormularioEnabled(false);
					cajaNoControl.setEditable(true);
					defPosicionamiento(panelBajas[1], true, true, "Eliminar");
				}
			});
			
		cambiosAl = new JMenu("Cambios");
			menuItemCambios = new JMenuItem("Modificar Alumno");
			cambiosAl.add(menuItemCambios);
			menuItemCambios.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					actualizarTabla("com.mysql.cj.jdbc.Driver","jdbc:mysql://localhost:3306/Esccuela_Topicos","SELECT * FROM Alumnos");
					IF_AltaAlumnos.setVisible(false);
					IF_BajaAlumnos.setVisible(false);
					IF_CambiosAlumnos.setVisible(true);
					IF_ConsultasAlumnos.setVisible(false);
					setFormularioEnabled(true);
					defPosicionamiento(panelCambios[1], true, true, "Guardar Cambios");
				}
			});
		
		consultasAl = new JMenu("Consultas");
			menuItemConsultas = new JMenuItem("Buscar ALumno");
			consultasAl.add(menuItemConsultas);
			menuItemConsultas.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					actualizarTabla("com.mysql.cj.jdbc.Driver","jdbc:mysql://localhost:3306/Esccuela_Topicos","SELECT * FROM Alumnos");
					IF_AltaAlumnos.setVisible(false);
					IF_BajaAlumnos.setVisible(false);
					IF_CambiosAlumnos.setVisible(false);
					IF_ConsultasAlumnos.setVisible(true);
					metodoRestablecerTodo(cajaNoControl, cajaNombre, cajaAP, cajaAM, comboEdad, comboSemestre, comboCarrera);
					setFormularioEnabled(false);
					cbTodos.setSelected(false);
					setCheckboxSelected(false);
					defPosicionamiento(panelConsultas[1], true, false, "");
					metodoMagico(cbTodos, 70, 17, 20, 20, panelConsultas[1]);
					metodoMagico(cbNoControl, 70, 37, 20, 20, panelConsultas[1]);
					metodoMagico(cbNombres, 70, 60, 20, 20, panelConsultas[1]);
					metodoMagico(cbApellidoP, 70, 83, 20, 20, panelConsultas[1]);
					metodoMagico(cbApellidoM, 70, 106, 20, 20, panelConsultas[1]);
					metodoMagico(cbEdad, 70, 129, 20, 20, panelConsultas[1]);
					metodoMagico(cbSemestre, 70, 159, 20, 20, panelConsultas[1]);
					metodoMagico(cbCarrera, 70, 177, 20, 20, panelConsultas[1]);
				}
			});
		menu.add(altasAl);
		menu.add(bajasAl);
		menu.add(cambiosAl);
		menu.add(consultasAl);
		
		IF_AltaAlumnos.add(panelAltas[0]);
		IF_AltaAlumnos.add(panelAltas[1]);
		IF_BajaAlumnos.add(panelBajas[0]);
		IF_BajaAlumnos.add(panelBajas[1]);
		IF_CambiosAlumnos.add(panelCambios[0]);
		IF_CambiosAlumnos.add(panelCambios[1]);
		IF_ConsultasAlumnos.add(panelConsultas[0]);
		IF_ConsultasAlumnos.add(panelConsultas[1]);
		
		desktopPane.add(tablaAl);
		desktopPane.add(IF_AltaAlumnos);
		desktopPane.add(IF_BajaAlumnos);
		desktopPane.add(IF_CambiosAlumnos);
		desktopPane.add(IF_ConsultasAlumnos);
		desktopPane.setBounds(0, 0, 567, 425);
		add(desktopPane);
		
		
	}
	
	public void reasignar() {
		cajaNoControl = new JTextField();
		cajaNombre = new JTextField();
		cajaAP = new JTextField();
		cajaAM = new JTextField();
		comboEdad = new JComboBox<String>();
		comboSemestre = new JComboBox<String>();
		comboCarrera = new JComboBox<String>();
		
		cajaNoControl.addKeyListener(new KeyAdapter() { //Codigo de validación
			public void keyPressed(KeyEvent evento) {
				int code = evento.getKeyCode();
				if (((cajaNoControl.getText().length()<9)&&(evento.getKeyChar() >= '0' && evento.getKeyChar() <= '9'))||(cajaNoControl.getText().equals("")&&((evento.getKeyChar() >= 'a' && evento.getKeyChar() <= 'z')||(evento.getKeyChar() >= 'A' && evento.getKeyChar() <= 'Z')))||(code==KeyEvent.VK_BACK_SPACE)) {
					cajaNoControl.setEditable(true);
				}else{
					cajaNoControl.setEditable(false);
				}
			}
		});
		
		cajaNombre.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evento) {
				int code = evento.getKeyCode();
				if (((cajaNombre.getText().length()<9)&&(evento.getKeyChar() >= '0' && evento.getKeyChar() <= '9'))||(cajaNombre.getText().equals("")&&((evento.getKeyChar() >= 'a' && evento.getKeyChar() <= 'z')||(evento.getKeyChar() >= 'A' && evento.getKeyChar() <= 'Z')))||(code==KeyEvent.VK_BACK_SPACE)) {
					cajaNombre.setEditable(true);
				}else{
					cajaNombre.setEditable(false);
				}
			}
		});
		
		cajaAP.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evento) {
				int code = evento.getKeyCode();
				if (((cajaAP.getText().length()<9)&&(evento.getKeyChar() >= '0' && evento.getKeyChar() <= '9'))||(cajaAP.getText().equals("")&&((evento.getKeyChar() >= 'a' && evento.getKeyChar() <= 'z')||(evento.getKeyChar() >= 'A' && evento.getKeyChar() <= 'Z')))||(code==KeyEvent.VK_BACK_SPACE)) {
					cajaAP.setEditable(true);
				}else{
					cajaAP.setEditable(false);
				}
			}
		});
		
		cajaAM.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evento) {
				int code = evento.getKeyCode();
				if (((cajaAM.getText().length()<9)&&(evento.getKeyChar() >= '0' && evento.getKeyChar() <= '9'))||(cajaAM.getText().equals("")&&((evento.getKeyChar() >= 'a' && evento.getKeyChar() <= 'z')||(evento.getKeyChar() >= 'A' && evento.getKeyChar() <= 'Z')))||(code==KeyEvent.VK_BACK_SPACE)) {
					cajaAM.setEditable(true);
				}else{
					cajaAM.setEditable(false);
				}
			}
		});
		
		for (int i = 1; i <= 122; i++) {	comboEdad.addItem(""+i);}
		for (int i = 1; i <= 10; i++) {		comboSemestre.addItem(""+i);}
		
		comboCarrera.addItem("ISC");
		comboCarrera.addItem("IM");
		comboCarrera.addItem("ADMON");
		comboCarrera.addItem("IIA");
		comboCarrera.addItem("LEC");
		
		metodoRestablecerTodo(comboEdad, comboSemestre, comboCarrera);
		txtNumeroDeControl = new JLabel("Numero de Control: ");
		txtNombres = new JLabel("Nombre: ");
		txtApellidoPaterno = new JLabel("Apellido Paterno: ");
		txtApellidoMaterno = new JLabel("Apellido Materno: ");
		txtEdad = new JLabel("Edad: ");
		txtSemestre = new JLabel("Semestre: ");
		txtCarrera = new JLabel("Carrera: ");
		
		btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(this);
		btnInteraccion = new JButton("");
		btnInteraccion.addActionListener(this);
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this);
		btnBuscar = new JButton(iconoBusqueda);
		btnBuscar.addActionListener(this);
		
		cbTodos = new JCheckBox();
		cbNoControl = new JCheckBox();
		cbNombres = new JCheckBox();
		cbApellidoP = new JCheckBox();
		cbApellidoM = new JCheckBox();
		cbEdad = new JCheckBox();
		cbSemestre = new JCheckBox();
		cbCarrera = new JCheckBox();
		cbTodos.addItemListener((ItemListener) this);
		cbNoControl.addItemListener((ItemListener) this);
		cbNombres.addItemListener((ItemListener) this);
		cbApellidoP.addItemListener((ItemListener) this);
		cbApellidoM.addItemListener((ItemListener) this);
		cbEdad.addItemListener((ItemListener) this);
		cbSemestre.addItemListener((ItemListener) this);
		cbCarrera.addItemListener((ItemListener) this);
		
	}
	
	public void setCheckboxSelected(boolean b){
		cbNombres.setSelected(b);
		cbApellidoP.setSelected(b);
		cbApellidoM.setSelected(b);
		cbEdad.setSelected(b);
		cbSemestre.setSelected(b);
		cbCarrera.setSelected(b);
	}
	
	public void setCheckboxesEnabled(boolean b){
		cbNoControl.setEnabled(b);
		cbNombres.setEnabled(b);
		cbApellidoP.setEnabled(b);
		cbApellidoM.setEnabled(b);
		cbEdad.setEnabled(b);
		cbSemestre.setEnabled(b);
		cbCarrera.setEnabled(b);
	}
	
	public void setFormularioEnabled(boolean b) {
		cajaNoControl.setEditable(b);
		cajaNombre.setEditable(b);
		cajaAP.setEditable(b);
		cajaAM.setEditable(b);
		comboEdad.setEnabled(b);
		comboSemestre.setEnabled(b);
		comboCarrera.setEnabled(b);
	}
	
	public JPanel[] defPanel(JInternalFrame frame,String encabezado,String titulo,Color color) {
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(HIDE_ON_CLOSE);
		frame.setSize(567,290);
		frame.setTitle(encabezado);
		JPanel panelTitulo = new JPanel();
		panelTitulo.setLayout(null);
		panelTitulo.setBackground(color);
		panelTitulo.setBounds(0, 0, 567, 50);
		JLabel lblTitulo = new JLabel(titulo);
		lblTitulo.setFont(new Font("Calibri", Font.BOLD, 20));
		lblTitulo.setForeground(Color.WHITE);
		metodoMagico(lblTitulo, 30, 20, 290, 20, panelTitulo);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 50, 567, 425);
		JPanel retorno[]=new JPanel[2];
		retorno[0]=panelTitulo;
		retorno[1]=panel;
		return retorno;
	};
	
	public void defPosicionamiento(JPanel panel, boolean busq, boolean interact, String boton) {
		metodoMagico(txtNumeroDeControl, 90, 37, 130, 20, panel);
		metodoMagico(txtNombres, 90, 60, 130, 20, panel);
		metodoMagico(txtApellidoPaterno, 90, 83, 130, 20, panel);
		metodoMagico(txtApellidoMaterno, 90, 106, 130, 20, panel);
		metodoMagico(txtEdad, 90, 129, 130, 20, panel);
		metodoMagico(txtSemestre, 90, 159, 130, 20, panel);
		metodoMagico(txtCarrera, 90, 177, 130, 20, panel);
		metodoMagico(cajaNoControl, 226, 40, 134, 17, panel);
		metodoMagico(cajaNombre, 172, 63, 187, 17, panel);
		metodoMagico(cajaAP, 216, 86, 144, 17, panel);
		metodoMagico(cajaAM, 216, 109, 144, 17, panel);
		metodoMagico(comboEdad, 216, 137, 40, 16, panel);
		metodoMagico(comboSemestre, 216, 165, 144, 16, panel);
		metodoMagico(comboCarrera, 216, 181, 144, 16, panel);
		metodoMagico(btnBorrar, 380, 46, 90, 18, panel);
		metodoMagico(btnCancelar, 375, 153, 100, 18, panel);
		if (interact) {
			btnInteraccion.setText(boton);
			if (boton.contains("GUARDAR CAMBIOS")) {
				metodoMagico(btnInteraccion, 380, 105, 160, 18, panel);
			}else {
				metodoMagico(btnInteraccion, 380, 105, 90, 18, panel);
			}
			
		}
		
	}
	
	public void metodoMagico(Component c, int x, int y,int width, int height, JPanel p) {
		c.setBounds(x, y, width, height);
		p.add(c);
	}
	
	public void metodoRestablecerTodo(Component...componentesGraficos) {
		for (Component c: componentesGraficos) {
			if (c instanceof JComboBox) {
				((JComboBox<?>)c).setSelectedIndex(-1);
			}else if (c instanceof JTextField) {
				((JTextField)c).setText("");
			}
		}
	}
	
	public String consulta() {
		AlumnoDAO aDAO = new AlumnoDAO();
		String sql = "select * from alumnos";
		boolean primero = true;
		
		if (!cajaNoControl.getText().equals("")) {
			if (!primero) {sql+=" AND ";}else {sql+="WHERE ";}
			primero=false;
			sql+=("NumComtrol='"+cajaNoControl.getText()+"'");
		}
		if (!cajaNombre.getText().equals("")) {
			if (!primero) {sql+=" AND ";}else {sql+="WHERE ";}
			primero=false;
			sql+=("Nombre='"+cajaNombre.getText()+"'");
		}
		if (!cajaAP.getText().equals("")) {
			if (!primero) {sql+=" AND ";}else {sql+="WHERE ";}
			primero=false;
			sql+=("PrimerAp='"+cajaAP.getText()+"'");
		}
		if (!cajaAM.getText().equals("")) {
			if (!primero) {sql+=" AND ";}else {sql+="WHERE ";}
			primero=false;
			sql+=("SegundoAp='"+cajaAM.getText()+"'");
		}
		if (comboEdad.getSelectedIndex()!=-1) {
			if (!primero) {sql+=" AND ";}else {sql+="WHERE ";}
			primero=false;
			sql+=("Edad="+comboEdad.getSelectedItem());
		}
		if (comboSemestre.getSelectedIndex()!=-1) {
			if (!primero) {sql+=" AND ";}else {sql+="WHERE ";}
			primero=false;
			sql+=("Semestre="+comboSemestre.getSelectedItem());
		}
		if (comboCarrera.getSelectedIndex()!=-1) {
			if (!primero) {sql+=" AND ";}else {sql+="WHERE ";}
			primero=false;
			sql+=("Carrera='"+comboCarrera.getSelectedItem()+"'");
		}
		
		return sql;
	}
	
	public void actualizarTabla(String driver, String url, String sql) {
		ResultSetTableModel modeloDatos =null;
		try {
			modeloDatos = new ResultSetTableModel(driver,url,sql);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		

		tablaAl.remove(scrollPane);
		tablaAlumnos = new JTable(modeloDatos);
		tablaAlumnos.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		    	obtenerRegistroTabla();
		    }
		});
		scrollPane = new JScrollPane(tablaAlumnos);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(20,0,525,100);
		tablaAl.add(scrollPane);
		tablaAl.setVisible(true);
	}
	
	public void obtenerRegistroTabla() {
		cajaNoControl.setText((String) tablaAlumnos.getValueAt(tablaAlumnos.getSelectedRow(),0));
		cajaNombre.setText((String) tablaAlumnos.getValueAt(tablaAlumnos.getSelectedRow(),1));
		cajaAP.setText((String) tablaAlumnos.getValueAt(tablaAlumnos.getSelectedRow(),2));
		cajaAM.setText((String) tablaAlumnos.getValueAt(tablaAlumnos.getSelectedRow(),3));
		comboEdad.setSelectedIndex((int)(tablaAlumnos.getValueAt(tablaAlumnos.getSelectedRow(),4))-1);
		comboSemestre.setSelectedIndex((int)(tablaAlumnos.getValueAt(tablaAlumnos.getSelectedRow(),5))-1);
		comboCarrera.setSelectedItem(tablaAlumnos.getValueAt(tablaAlumnos.getSelectedRow(),6));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnInteraccion) {
			AlumnoDAO aDAO = new AlumnoDAO();
			if (IF_AltaAlumnos.isVisible()) {
				if (cajaNoControl.getText().equals("")||cajaNombre.getText().equals("")||cajaAP.getText().equals("")||cajaAM.getText().equals("")||comboEdad.getSelectedIndex()==-1||comboSemestre.getSelectedIndex()==-1||comboCarrera.getSelectedIndex()==-1) {
					JOptionPane.showMessageDialog(null, "Error: faltan uno o más campos");
				}else {
					Alumno a = new Alumno(cajaNoControl.getText(), cajaNombre.getText(), cajaAP.getText(), cajaAM.getText(),
							(byte)(comboEdad.getSelectedIndex()+1), (byte)(comboSemestre.getSelectedIndex()+1), comboCarrera.getSelectedItem().toString());
					if (aDAO.insertarRegistro(a)) {
						JOptionPane.showMessageDialog(null, "Alumno agregado exitosamente");
					}else {
						JOptionPane.showMessageDialog(null, "No se pudo agregar al alumno");
					}
				}
			}else if(IF_BajaAlumnos.isVisible()) {
				if (cajaNoControl.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "No se esta especificando el numero de control");
				}else {
					if (aDAO.eliminarRegistro(cajaNoControl.getText())) {
						JOptionPane.showMessageDialog(null, "Alumno eliminado exitosamente");
					}else {
						JOptionPane.showMessageDialog(null, "No se pudo eliminar al alumno");
					}
				}
			}else if (IF_CambiosAlumnos.isVisible()) {
				
				if (cajaNoControl.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "No se esta especificando el numero de control");
				}else {
					Alumno a = new Alumno(cajaNoControl.getText(), cajaNombre.getText(), cajaAP.getText(), cajaAM.getText(),
							(byte)(comboEdad.getSelectedIndex()+1), (byte)(comboSemestre.getSelectedIndex()+1), 
							comboCarrera.getSelectedIndex()!=-1?comboCarrera.getSelectedItem().toString():"");
					boolean flags[]=new boolean[6];
					flags[0]=!cajaNombre.getText().equals("");
					flags[1]=!cajaAP.getText().equals("");
					flags[2]=!cajaAM.getText().equals("");
					flags[3]=comboEdad.getSelectedIndex()!=-1;
					flags[4]=comboSemestre.getSelectedIndex()!=-1;
					flags[5]=comboCarrera.getSelectedIndex()!=-1;
					
					if (aDAO.modificarRegistro(a, flags)) {
						JOptionPane.showMessageDialog(null, "Datos de Alumno modificados exitosamente");
					}else {
						JOptionPane.showMessageDialog(null, "No se pudieron modificar los datos del alumno");
					}
				}
				
			}else if(IF_ConsultasAlumnos.isVisible()) {
				if (cajaNoControl.getText().equals("")&& cajaNombre.getText().equals("") && cajaAP.getText().equals("") && cajaAM.getText().equals("")&&comboEdad.getSelectedIndex()==-1&&comboSemestre.getSelectedIndex()==-1&&comboCarrera.getSelectedIndex()==-1) {
					JOptionPane.showMessageDialog(null, "Error: no se lleno ningun campo");
				}else {
					ArrayList<Alumno> listaAlumnos = aDAO.buscarAlumnos("Parametros");
					if (listaAlumnos.size()!=0) {
						JOptionPane.showMessageDialog(null, "Se encontraron registros que coinciden");
					}else {
						JOptionPane.showMessageDialog(null, "No se encontraron coincidencias");
					}
				}
				
			}
			actualizarTabla("com.mysql.cj.jdbc.Driver","jdbc:mysql://localhost:3306/Esccuela_Topicos","SELECT * FROM alumnos");
		}
		if (e.getSource()== btnBuscar) {
			
			String sql = consulta();
			AlumnoDAO aDAO = new AlumnoDAO();
			
			actualizarTabla("com.mysql.cj.jdbc.Driver","jdbc:mysql://localhost:3306/Esccuela_Topicos",sql);
			ArrayList<Alumno> alumnos = aDAO.buscarAlumnos(sql);
			
		}
		if (e.getSource()== btnBorrar) {
			metodoRestablecerTodo(cajaNoControl, cajaNombre, cajaAP , cajaAM , comboEdad,comboSemestre,comboCarrera);
		}
		if (e.getSource()== btnCancelar) {
			IF_AltaAlumnos.setVisible(false);
			IF_BajaAlumnos.setVisible(false);
			IF_CambiosAlumnos.setVisible(false);
			IF_ConsultasAlumnos.setVisible(false);
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

