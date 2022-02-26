import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.UIManager;

public class VentanaCrearAlumno extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtDireccion;
	private JTextField txtTelefono;
	private JTextField txtEmail;
	private JTextField txtDia;
	private JTextField txtMes;
	private JTextField txtAño;
	private JTextField txtDni;
	private JLabel lblMensageError;
	private JComboBox<String> comboBoxCurso;
		
	public VentanaCrearAlumno() {
		
		setResizable(false);
		setTitle("LECSys - Cargar nuevo alumno."+ CheckUsuario.getNombreUsuario());
		setIconImage(Toolkit.getDefaultToolkit().getImage(LECSys.rutaImagenes + "LEC.jpg"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setBounds(10, 20, 400, 330);
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("CheckBox.background"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(25, 25, 70, 20);
		contentPane.add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(95, 25, 200, 20);
		contentPane.add(txtNombre);
		configurarJTextField(txtNombre, 20);
		
		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(25, 50, 70, 20);
		contentPane.add(lblApellido);
		
		txtApellido = new JTextField();
		txtApellido.setBounds(95, 50, 200, 20);
		contentPane.add(txtApellido);
		configurarJTextField(txtApellido, 20);
		
		JLabel lblDni = new JLabel("D.N.I.:");
		lblDni.setBounds(25, 75, 70, 20);
		contentPane.add(lblDni);
		
		txtDni = new JTextField();
		txtDni.setBounds(95, 75, 140, 20);
		contentPane.add(txtDni);
		configurarJTextField(txtDni, 20);
		
		JLabel lblFechaNacimiento = new JLabel("Nacimiento:");
		lblFechaNacimiento.setBounds(25, 100, 70, 20);
		contentPane.add(lblFechaNacimiento);
		
		JLabel lblFormato = new JLabel("DD / MM / AAAA");
		lblFormato.setBounds(210, 100, 88, 20);
		contentPane.add(lblFormato);
		
		txtDia = new JTextField();
		contentPane.add(txtDia);
		txtDia.setHorizontalAlignment(SwingConstants.CENTER);
		txtDia.setBounds(95, 100, 25, 20);
		configurarJTextField(txtDia, 2);
		
		JLabel label = new JLabel("/");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(120, 100, 10, 20);
		contentPane.add(label);
		
		txtMes = new JTextField();
		contentPane.add(txtMes);
		txtMes.setHorizontalAlignment(SwingConstants.CENTER);
		txtMes.setBounds(130, 100, 25, 20);
		configurarJTextField(txtMes, 2);
		
		JLabel label_1 = new JLabel("/");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(155, 100, 10, 20);
		contentPane.add(label_1);
		
		txtAño = new JTextField();
		contentPane.add(txtAño);
		txtAño.setHorizontalAlignment(SwingConstants.CENTER);
		txtAño.setBounds(165, 100, 40, 20);
		configurarJTextField(txtAño, 4);
		
		JLabel lblDireccin = new JLabel("Dirección:");
		lblDireccin.setBounds(25, 125, 70, 20);
		contentPane.add(lblDireccin);
		
		txtDireccion = new JTextField();
		txtDireccion.setBounds(95, 125, 240, 20);
		contentPane.add(txtDireccion);
		configurarJTextField(txtDireccion, 45);
		
		JLabel lblTelfono = new JLabel("Teléfono:");
		lblTelfono.setBounds(25, 175, 70, 20);
		contentPane.add(lblTelfono);
		
		txtTelefono = new JTextField();
		txtTelefono.setBounds(95, 175, 140, 20);
		contentPane.add(txtTelefono);
		configurarJTextField(txtTelefono, 20);
		
		JLabel lblEmail = new JLabel("e-mail:");
		lblEmail.setBounds(25, 150, 70, 20);
		contentPane.add(lblEmail);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(95, 150, 240, 20);
		contentPane.add(txtEmail);
		configurarJTextField(txtEmail, 40);
		
		JLabel lblCurso = new JLabel("Curso:");
		lblCurso.setBounds(25, 200, 70, 20);
		contentPane.add(lblCurso);

		String respuestaCurso[][] = ABMCCurso.getListaCursos(true);
		String listaCursos[] = new String[respuestaCurso.length]; 
		
		for(int i=0 ; i < respuestaCurso.length ; i++) {
			
			listaCursos[i] = respuestaCurso[i][1] + " " + respuestaCurso[i][2] + " - " + respuestaCurso[i][4] + " - " + respuestaCurso[i][7];
		}
		
		comboBoxCurso = new JComboBox<String>();
		comboBoxCurso.setModel(new DefaultComboBoxModel<String>(listaCursos));
		comboBoxCurso.setBounds(95, 200, 240, 20);
		contentPane.add(comboBoxCurso);
		
		lblMensageError = new JLabel("");
		lblMensageError.setForeground(Color.RED);
		lblMensageError.setBackground(Color.LIGHT_GRAY);
		lblMensageError.setBounds(25, 230, 350, 25);
		contentPane.add(lblMensageError);
		
		JButton btnAceptar = new JButton("Guardar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String fechaNacimiento = txtAño.getText() + "-" + txtMes.getText() + "-" + txtDia.getText();
				Calendar fechaSistema = new GregorianCalendar();
				String fechaActual = fechaSistema.get(Calendar.YEAR) + "-" + 
									(fechaSistema.get(Calendar.MONTH)+1) + "-" +
									fechaSistema.get(Calendar.DAY_OF_MONTH);
				String idCurso = respuestaCurso[comboBoxCurso.getSelectedIndex()][0];
				
				try	{
					
					String ceda[]= {txtNombre.getText(),
									txtApellido.getText(),
									txtDni.getText(),
									txtDireccion.getText(),
									fechaNacimiento,
									txtTelefono.getText(),
									txtEmail.getText(),
									idCurso,
									fechaActual,
									"0",
									ABMCValorCuota.buscarIdCuota(idCurso)};
					String respuesta[] = ABMCAlumnos.buscarAlumno("DNI", txtDni.getText());
	
					if(respuesta[0] == null) {
						
						if(checkCampos()) {
							
							limpiarCampos();
							
							if(ABMCAlumnos.crearAlumno(ceda) == true) {
								
								lblMensageError.setForeground(Color.BLUE);
								lblMensageError.setText("Registro creado.");
							} else {
								
								lblMensageError.setForeground(Color.RED);
								lblMensageError.setText("No se pudo realizar la operación.");
							}
						}
					} else {
						
						lblMensageError.setForeground(Color.RED);
						lblMensageError.setText("Error, el DNI ya está cargado.");
					}
				} catch (ArrayIndexOutOfBoundsException f) {
					
					lblMensageError.setForeground(Color.RED);
					lblMensageError.setText("Primero debe existir un curso para ser asignado.");
				}

			}
		});
		btnAceptar.setBounds(155, 260, 90, 23);
		contentPane.add(btnAceptar);
		
		JButton btnCancelar = new JButton("Volver");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						
						try {
							
							VentanaAlumnos frame = new VentanaAlumnos();
							frame.setVisible(true);
						} catch (Exception e) {
							
							e.printStackTrace();
						}
					}
				});
			}
		});
		btnCancelar.setBounds(280, 260, 90, 23);
		contentPane.add(btnCancelar);
	}
	
	private void limpiarCampos() {
		
		txtNombre.setText(null);
		txtApellido.setText(null);
		txtDni.setText(null);
		txtDireccion.setText(null);
		txtTelefono.setText(null);
		txtEmail.setText(null);
		txtDia.setText(null);
		txtMes.setText(null);
		txtAño.setText(null);
		lblMensageError.setText(null);
	}
	
	private boolean checkCampos() {
		
		boolean i = true;
		lblMensageError.setForeground(Color.RED);
		if(txtNombre.getText().length() < 3) {
			
			i = false;
			lblMensageError.setText("El nombre debe tener más de dos caracteres.");
		} else if(txtApellido.getText().length() < 3) {
			
			i = false;
			lblMensageError.setText("El apellido debe tener más de dos caracteres.");
		} else if(txtDni.getText().length() < 7 ||
				!isNumeric(txtDni.getText())) {
			
			i = false;
			lblMensageError.setText("Error en el formato del DNI (solamente números).");
		} else if(txtDia.getText().length() == 0 || 
				 Integer.parseInt(txtDia.getText()) < 1 ||
				 Integer.parseInt(txtDia.getText()) > 31) {
			
			i = false;
			lblMensageError.setText("Error en el formato del día.");
		} else if(txtMes.getText().length() == 0 || 
				 Integer.parseInt(txtMes.getText()) < 1 ||
				 Integer.parseInt(txtMes.getText()) > 12) {
			
			i = false;
			lblMensageError.setText("Error en el formato del mes.");
		} else if(txtAño.getText().length() == 0 ||
				Integer.parseInt(txtAño.getText()) < 1920) {
			
			i = false;
			lblMensageError.setText("Error en el formato del año.");
		} else if(txtDireccion.getText().length() == 0) {
			
			i = false;
			lblMensageError.setText("La dirección no puede estar vacía.");
		} else if(txtTelefono.getText().length() == 0 ||
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
	
	private static boolean isNumeric(String cadena) {
		
		try {
			
			Double.parseDouble(cadena);
			return true;
		} catch (NumberFormatException e){
			
			return false;
		}
	}
}
