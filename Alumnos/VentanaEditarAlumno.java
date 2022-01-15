import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class VentanaEditarAlumno extends JFrame implements ItemListener{
	
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtLegajo;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtDni;
	private JTextField txtDireccion;
	private JTextField txtTelefono;
	private JTextField txtEmail;
	private JTextField txtAño;
	private JTextField txtDia;
	private JTextField txtMes;
	private JTextField txtFechaIngreso;
	private JTextField txtLunes = new JTextField();
	private JTextField txtMartes = new JTextField();
	private JTextField txtMiercoles = new JTextField();
	private JTextField txtJueves = new JTextField();
	private JTextField txtViernes = new JTextField();
	private JTextField txtSabado = new JTextField();
	private JTextField txtLunesDuracion = new JTextField();
	private JTextField txtMartesDuracion = new JTextField();
	private JTextField txtMiercolesDuracion = new JTextField();
	private JTextField txtJuevesDuracion = new JTextField();
	private JTextField txtViernesDuracion = new JTextField();
	private JTextField txtSabadoDuracion = new JTextField();
	private JCheckBox chckbxActivo;
	private JComboBox<String> comboBoxCurso;
	private JButton btnGuardar;
	private JButton btnAsistencia;
	private JButton btnImprimir;
	private JLabel lblMensageError;
	private String estadoAnterior;
	private String respuestaCurso[][];
	private String informacion[];
	
	public VentanaEditarAlumno(String legajo) {

		setIconImage(Toolkit.getDefaultToolkit().getImage(LECSys.rutaImagenes + "LEC.jpg"));
		setTitle("LECSys - Búsqueda de alumnos."+ CheckUsuario.getNombreUsuario());
		setBounds(10, 20, 480, 510);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		informacion = ABMCAlumnos.buscarAlumno("ID", legajo);

		lblMensageError = new JLabel("");
		lblMensageError.setBounds(25, 365, 440, 25);
		lblMensageError.setForeground(Color.RED);
		lblMensageError.setBackground(Color.LIGHT_GRAY);
		contentPanel.add(lblMensageError);
		
		JLabel lblLegajo = new JLabel("Legajo:");
		lblLegajo.setBounds(25, 15, 90, 20);
		contentPanel.add(lblLegajo);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(25, 65, 90, 20);
		contentPanel.add(lblNombre);
		
		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(25, 90, 90, 20);
		contentPanel.add(lblApellido);
		
		JLabel lblDni = new JLabel("DNI:");
		lblDni.setBounds(25, 40, 90, 20);
		contentPanel.add(lblDni);
		
		JLabel lblNacimiento = new JLabel("Cumpleaño:");
		lblNacimiento.setBounds(25, 115, 90, 20);
		contentPanel.add(lblNacimiento);
		
		JLabel lblDireccion = new JLabel("Dirección:");
		lblDireccion.setBounds(25, 140, 90, 20);
		contentPanel.add(lblDireccion);
		
		JLabel lblCurso = new JLabel("Teléfono:");
		lblCurso.setBounds(25, 190, 90, 20);
		contentPanel.add(lblCurso);
		
		JLabel lblNivel = new JLabel("e-mail:");
		lblNivel.setBounds(25, 165, 90, 20);
		contentPanel.add(lblNivel);
		
		txtLegajo = new JTextField();
		txtLegajo.setBounds(115, 15, 140, 20);
		txtLegajo.setEditable(false);
		txtLegajo.setText(informacion[0]);
		contentPanel.add(txtLegajo);
		configurarJTextField(txtLegajo, 20);
		
		txtNombre = new JTextField();
		txtNombre.setEnabled(true);
		txtNombre.setText(informacion[1]);
		contentPanel.add(txtNombre);
		txtNombre.setBounds(115, 65, 240, 20);
		configurarJTextField(txtNombre, 20);
		
		txtApellido = new JTextField();
		txtApellido.setEnabled(true);
		txtApellido.setText(informacion[2]);
		contentPanel.add(txtApellido);
		txtApellido.setBounds(115, 90, 240, 20);
		configurarJTextField(txtApellido, 20);
		
		txtDni = new JTextField();
		txtDni.setEnabled(true);
		txtDni.setText(informacion[3]);
		contentPanel.add(txtDni);
		txtDni.setBounds(115, 40, 140, 20);
		configurarJTextField(txtDni, 10);
		
		txtDireccion = new JTextField();
		txtDireccion.setEnabled(true);
		txtDireccion.setText(informacion[5]);
		contentPanel.add(txtDireccion);
		txtDireccion.setBounds(115, 140, 240, 20);
		configurarJTextField(txtDireccion, 45);
		
		txtTelefono = new JTextField();
		txtTelefono.setEnabled(true);
		txtTelefono.setText(informacion[6]);
		contentPanel.add(txtTelefono);
		txtTelefono.setBounds(115, 190, 140, 20);
		configurarJTextField(txtTelefono, 20);
		
		txtEmail = new JTextField();
		txtEmail.setEnabled(true);
		txtEmail.setText(informacion[7]);
		contentPanel.add(txtEmail);
		txtEmail.setBounds(115, 165, 240, 20);
		configurarJTextField(txtEmail, 40);
		
		String[] parts = informacion[4].split("-");
		
		txtAño = new JTextField();
		txtAño.setEnabled(true);
		txtAño.setText(parts[0]);
		contentPanel.add(txtAño);
		txtAño.setBounds(205, 115, 50, 20);
		txtAño.setHorizontalAlignment(SwingConstants.CENTER);
		configurarJTextField(txtAño, 4);
		
		txtMes = new JTextField();
		txtMes.setEnabled(true);
		txtMes.setText(parts[1]);
		contentPanel.add(txtMes);
		txtMes.setBounds(160, 115, 30, 20);
		txtMes.setHorizontalAlignment(SwingConstants.CENTER);
		configurarJTextField(txtMes, 2);
		
		txtDia = new JTextField();
		txtDia.setEnabled(true);
		txtDia.setText(parts[2]);
		contentPanel.add(txtDia);
		txtDia.setBounds(115, 115, 30, 20);
		txtDia.setHorizontalAlignment(SwingConstants.CENTER);
		configurarJTextField(txtDia, 2);

		txtFechaIngreso = new JTextField();
		txtFechaIngreso.setBounds(115, 215, 90, 20);
		txtFechaIngreso.setEditable(false);
		txtFechaIngreso.setText(informacion[8]);
		contentPanel.add(txtFechaIngreso);
		txtFechaIngreso.setColumns(10);
		
		JLabel label = new JLabel("/");
		label.setBounds(145, 115, 15, 20);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(label);
		
		JLabel label_1 = new JLabel("/");
		label_1.setBounds(190, 115, 15, 20);
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(label_1);
		
		chckbxActivo = new JCheckBox("Activo");
		chckbxActivo.setEnabled(true);
		if(informacion[9].contentEquals("1"))
			chckbxActivo.setSelected(true);
		else
			chckbxActivo.setSelected(false);
		chckbxActivo.setBounds(95, 270, 90, 20);
		contentPanel.add(chckbxActivo);
				
		JLabel lblIngreso = new JLabel("Fecha ingreso:");
		lblIngreso.setBounds(25, 215, 90, 20);
		contentPanel.add(lblIngreso);

		JLabel lblFormatoNacimiento = new JLabel("DD / MM / AAAA");
		lblFormatoNacimiento.setBounds(267, 115, 88, 20);
		contentPanel.add(lblFormatoNacimiento);
		
		JLabel lblCurso_1 = new JLabel("Curso:");
		lblCurso_1.setBounds(25, 240, 90, 20);
		contentPanel.add(lblCurso_1);
		
		JLabel lblLunes = new JLabel("Lunes");
		lblLunes.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblLunes.setBounds(85, 300, 60, 20);
		lblLunes.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(lblLunes);
		
		JLabel lblMartes = new JLabel("Martes");
		lblMartes.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblMartes.setBounds(145, 300, 60, 20);
		lblMartes.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(lblMartes);
		
		JLabel lblMiercoles = new JLabel("Miércoles");
		lblMiercoles.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblMiercoles.setBounds(205, 300, 60, 20);
		lblMiercoles.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(lblMiercoles);
		
		JLabel lblJueves = new JLabel("Jueves");
		lblJueves.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblJueves.setBounds(265, 300, 60, 20);
		lblJueves.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(lblJueves);
		
		JLabel lblViernes = new JLabel("Viernes");
		lblViernes.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblViernes.setBounds(325, 300, 60, 20);
		lblViernes.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(lblViernes);
		
		JLabel lblSbado = new JLabel("Sábado");
		lblSbado.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblSbado.setBounds(385, 300, 60, 20);
		lblSbado.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(lblSbado);
		
		txtLunes = new JTextField();
		txtLunes.setBounds(85, 320, 60, 20);
		txtLunes.setHorizontalAlignment(SwingConstants.CENTER);
		txtLunes.setEditable(false);
		contentPanel.add(txtLunes);
		txtLunes.setColumns(10);
		
		txtMartes = new JTextField();
		txtMartes.setBounds(145, 320, 60, 20);
		txtMartes.setHorizontalAlignment(SwingConstants.CENTER);
		txtMartes.setEditable(false);
		contentPanel.add(txtMartes);
		txtMartes.setColumns(10);
		
		txtMiercoles = new JTextField();
		txtMiercoles.setBounds(205, 320, 60, 20);
		txtMiercoles.setHorizontalAlignment(SwingConstants.CENTER);
		txtMiercoles.setEditable(false);
		contentPanel.add(txtMiercoles);
		txtMiercoles.setColumns(10);
		
		txtJueves = new JTextField();
		txtJueves.setBounds(265, 320, 60, 20);
		txtJueves.setHorizontalAlignment(SwingConstants.CENTER);
		txtJueves.setEditable(false);
		contentPanel.add(txtJueves);
		txtJueves.setColumns(10);
		
		txtViernes = new JTextField();
		txtViernes.setBounds(325, 320, 60, 20);
		txtViernes.setHorizontalAlignment(SwingConstants.CENTER);
		txtViernes.setEditable(false);
		contentPanel.add(txtViernes);
		txtViernes.setColumns(10);
		
		txtSabado = new JTextField();
		txtSabado.setBounds(385, 320, 60, 20);
		txtSabado.setHorizontalAlignment(SwingConstants.CENTER);
		txtSabado.setEditable(false);
		contentPanel.add(txtSabado);
		txtSabado.setColumns(10);
		
		txtLunesDuracion = new JTextField();
		txtLunesDuracion.setBounds(85, 340, 60, 20);
		txtLunesDuracion.setHorizontalAlignment(SwingConstants.CENTER);
		txtLunesDuracion.setEditable(false);
		contentPanel.add(txtLunesDuracion);
		txtLunesDuracion.setColumns(10);
		
		txtMartesDuracion = new JTextField();
		txtMartesDuracion.setBounds(145, 340, 60, 20);
		txtMartesDuracion.setHorizontalAlignment(SwingConstants.CENTER);
		txtMartesDuracion.setEditable(false);
		contentPanel.add(txtMartesDuracion);
		txtMartesDuracion.setColumns(10);
		
		txtMiercolesDuracion = new JTextField();
		txtMiercolesDuracion.setBounds(205, 340, 60, 20);
		txtMiercolesDuracion.setHorizontalAlignment(SwingConstants.CENTER);
		txtMiercolesDuracion.setEditable(false);
		contentPanel.add(txtMiercolesDuracion);
		txtMiercolesDuracion.setColumns(10);
		
		txtJuevesDuracion = new JTextField();
		txtJuevesDuracion.setBounds(265, 340, 60, 20);
		txtJuevesDuracion.setHorizontalAlignment(SwingConstants.CENTER);
		txtJuevesDuracion.setEditable(false);
		contentPanel.add(txtJuevesDuracion);
		txtJuevesDuracion.setColumns(10);
		
		txtViernesDuracion = new JTextField();
		txtViernesDuracion.setBounds(325, 340, 60, 20);
		txtViernesDuracion.setHorizontalAlignment(SwingConstants.CENTER);
		txtViernesDuracion.setEditable(false);
		contentPanel.add(txtViernesDuracion);
		txtViernesDuracion.setColumns(10);
		
		txtSabadoDuracion = new JTextField();
		txtSabadoDuracion.setBounds(385, 340, 60, 20);
		txtSabadoDuracion.setHorizontalAlignment(SwingConstants.CENTER);
		txtSabadoDuracion.setEditable(false);
		contentPanel.add(txtSabadoDuracion);
		txtSabadoDuracion.setColumns(10);
		
		JLabel lblHorario = new JLabel("Horario:");
		lblHorario.setBounds(25, 320, 60, 20);
		contentPanel.add(lblHorario);
		
		JLabel lblDuracion = new JLabel("Duración:");
		lblDuracion.setBounds(25, 340, 60, 20);
		contentPanel.add(lblDuracion);

		respuestaCurso = ABMCCurso.getListaCursos(true);
		String listaCursos[] = new String[respuestaCurso.length]; 
		
		for(int i=0 ; i < respuestaCurso.length ; i++) {
			
			listaCursos[i] = respuestaCurso[i][1] + " " + respuestaCurso[i][2] + " - " + respuestaCurso[i][4];
		}
							
		comboBoxCurso = new JComboBox<String>();
		comboBoxCurso.setEnabled(true);
		comboBoxCurso.setBounds(115, 240, 240, 20);
		comboBoxCurso.addItemListener(this);
		comboBoxCurso.setModel(new DefaultComboBoxModel<String>(listaCursos));
		
		try {
			
			comboBoxCurso.setSelectedIndex(0);	
		} catch (IllegalArgumentException e) {
			lblMensageError.setForeground(Color.RED);
			lblMensageError.setText("No hay cursos cargados.");
		}

		comboBoxCurso.setSelectedIndex(Integer.parseInt(informacion[11])-1); 
		contentPanel.add(comboBoxCurso);
				
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();

				try {
					VentanaAlumnos frame = new VentanaAlumnos();
					frame.setVisible(true);
				} catch (Exception f) {
					f.printStackTrace();
				}
			}
		});
		btnVolver.setBounds(325, 435, 110, 23);
		contentPanel.add(btnVolver);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.setEnabled(true);
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				gurdarCambios();
			}
		});
		btnGuardar.setBounds(155, 435, 110, 23);
		contentPanel.add(btnGuardar);
		
		btnAsistencia = new JButton("Asistencia");
		btnAsistencia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					
					VentanaHistorialAsistencia frame = new VentanaHistorialAsistencia(txtLegajo.getText());
					frame.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error al intentar imprimir.");
				}
			}
		});
		btnAsistencia.setEnabled(false);
		btnAsistencia.setVisible(false);	// Está invisible porque no está correctamente implementado aún. ////////////////////////////////////////////////////////////////
		btnAsistencia.setBounds(180, 400, 110, 23);
		contentPanel.add(btnAsistencia);
		
		btnImprimir = new JButton("Imprimir");
		btnImprimir.setEnabled(true);
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				contentPanel.setBackground(Color.WHITE);
				chckbxActivo.setBackground(Color.WHITE);
				txtFechaIngreso.setBackground(Color.WHITE);
				comboBoxCurso.setBackground(Color.WHITE);
				btnVolver.setVisible(false);
				btnAsistencia.setVisible(false);
				btnGuardar.setVisible(false);
				btnImprimir.setVisible(false);
				
				PrinterJob imprimir = PrinterJob.getPrinterJob();
				PageFormat preformat = imprimir.defaultPage();
				PageFormat postformat = imprimir.pageDialog(preformat);
					
				imprimir.setPrintable(new Printer(contentPanel), postformat);
				
				if (imprimir.printDialog()) {
					
					try {
							imprimir.print();
					} catch (PrinterException e) {
							JOptionPane.showMessageDialog(null, "Error al intentar imprimir.");
					}
				}
			
				contentPanel.setBackground(UIManager.getColor("Button.background"));
				chckbxActivo.setBackground(UIManager.getColor("Button.background"));
				txtFechaIngreso.setBackground(UIManager.getColor("Button.background"));
				comboBoxCurso.setBackground(null);
				btnVolver.setVisible(true);
				btnAsistencia.setVisible(true);
				btnGuardar.setVisible(true);
				btnImprimir.setVisible(true);
			}
		});
		btnImprimir.setBounds(10, 435, 110, 23);
		contentPanel.add(btnImprimir);
		
		estadoAnterior = informacion[9];
		actualizoTablaDias(Integer.parseInt(informacion[11]));
	}
	
	private void gurdarCambios() {
		
		String fechaNacimiento = txtAño.getText() + "-" + txtMes.getText() + "-" + txtDia.getText();
		String estado = chckbxActivo.isSelected()? "1":"0";
		String registroModificado[] = { txtLegajo.getText(),
										txtNombre.getText(),
										txtApellido.getText(),
										txtDni.getText(),
										txtDireccion.getText(),
										fechaNacimiento,
										txtTelefono.getText(),
										txtEmail.getText(),
										respuestaCurso[comboBoxCurso.getSelectedIndex()][0],
										estado};
		if(checkCampos()) {
						
			if(!estado.contentEquals(estadoAnterior)) {
				
				String respuesta[] = ABMCAlumnos.buscarAlumno("ID", txtLegajo.getText());
				String idGrupoFamiliar = respuesta[10];
				if(Integer.parseInt(idGrupoFamiliar) > 0)
					ABMCGrupoFamiliar.modificarIntegrantes(idGrupoFamiliar, estado.contentEquals("1")?"+":"-");
			}

			if(ABMCAlumnos.modificarAlumno(registroModificado)) {
				
				lblMensageError.setForeground(Color.BLUE);
				lblMensageError.setText("Cambios guardados.");
			} else {
				
				lblMensageError.setForeground(Color.RED);
				lblMensageError.setText("Error al intentar guardar los cambios.");
			}
		}
	}
	
	private boolean checkCampos() {
		
		boolean i = true;
		lblMensageError.setForeground(Color.RED);
		
		if(txtNombre.getText().length() < 3) {
			
			i = false;
			lblMensageError.setText("El nombre debe tener más de dos caracteres.");
		}else if(txtApellido.getText().length() < 3) {
			
			i = false;
			lblMensageError.setText("El apellido debe tener más de dos caracteres.");
		}else if(txtDni.getText().length() < 7 ||
				!isNumeric(txtDni.getText())) {
			
			i = false;
			lblMensageError.setText("Error en el formato del DNI (solamente números).");
		}else if(txtDia.getText().length() == 0 || 
				 Integer.parseInt(txtDia.getText()) < 1 ||
				 Integer.parseInt(txtDia.getText()) > 31) {
			
			i = false;
			lblMensageError.setText("Error en el formato del día.");
		}else if(txtMes.getText().length() == 0 || 
				 Integer.parseInt(txtMes.getText()) < 1 ||
				 Integer.parseInt(txtMes.getText()) > 12) {
			
			i = false;
			lblMensageError.setText("Error en el formato del mes.");
		}else if(txtAño.getText().length() == 0 ||
				Integer.parseInt(txtAño.getText()) < 1920) {
			
			i = false;
			lblMensageError.setText("Error en el formato del año.");
		}else if(txtDireccion.getText().length() == 0) {
			
			i = false;
			lblMensageError.setText("La dirección no puede estar vacía.");
		}else if(txtTelefono.getText().length() == 0 ||
				!isNumeric(txtTelefono.getText())) {
			
			i = false;
			lblMensageError.setText("Error en el formato del teléfono (solamente números).");
		}
		return i;
	}
	
	private void configurarJTextField(Component nombre, int cantidadCaracteres) {
		
		((JTextField) nombre).setColumns(cantidadCaracteres);
		nombre.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				
				if(((JTextField) nombre).getText().length() >= cantidadCaracteres)
					e.consume();
			}
		});
	}
	
	private static boolean isNumeric(String cadena){
		
		try {
			
			Double.parseDouble(cadena);
			return true;
		} catch (NumberFormatException e){
			return false;
		}
	}
	
	private void actualizoTablaDias(int curso) {

		String resDiasCursado[][] = ABMCDiasCurso.buscarDiasCursado(respuestaCurso[comboBoxCurso.getSelectedIndex()][0]);
		int u=0;
		txtLunes.setText("");
		txtLunesDuracion.setText("");
		txtMartes.setText("");
		txtMartesDuracion.setText("");
		txtMiercoles.setText("");
		txtMiercolesDuracion.setText("");
		txtJueves.setText("");
		txtJuevesDuracion.setText("");
		txtViernes.setText("");
		txtViernesDuracion.setText("");
		txtSabado.setText("");
		txtSabadoDuracion.setText("");
		
		while(u < resDiasCursado.length) {
			
			if(resDiasCursado[u][0].equals("Lunes")) {
				
				txtLunes.setText(resDiasCursado[u][1]);
				txtLunesDuracion.setText(resDiasCursado[u][2]);
			}
			if(resDiasCursado[u][0].equals("Martes")) {
				
				txtMartes.setText(resDiasCursado[u][1]);
				txtMartesDuracion.setText(resDiasCursado[u][2]);
			}
			if(resDiasCursado[u][0].equals("Miércoles")) {
				
				txtMiercoles.setText(resDiasCursado[u][1]);
				txtMiercolesDuracion.setText(resDiasCursado[u][2]);
			}
			if(resDiasCursado[u][0].equals("Jueves")) {
				
				txtJueves.setText(resDiasCursado[u][1]);
				txtJuevesDuracion.setText(resDiasCursado[u][2]);
			}
			if(resDiasCursado[u][0].equals("Viernes")) {
				
				txtViernes.setText(resDiasCursado[u][1]);
				txtViernesDuracion.setText(resDiasCursado[u][2]);
			}
			if(resDiasCursado[u][0].equals("Sábado")) {
				
				txtSabado.setText(resDiasCursado[u][1]);
				txtSabadoDuracion.setText(resDiasCursado[u][2]);
			}
			u++;
		}
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		
		if (e.getSource()==comboBoxCurso)
			actualizoTablaDias(comboBoxCurso.getSelectedIndex());
	}
}
