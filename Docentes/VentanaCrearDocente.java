import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JDialog;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.SwingConstants;

public class VentanaCrearDocente extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtTelefono;
	private JTextField txtEmail;
	private JTextField txtDireccion;
	private JTextField txtDni;
	private JTextField txtApellido;
	private JTextField txtNombre;
	private JTextField txtSueldo;
	private JLabel lblMensageError;
	private JTextField txtDia;
	private JTextField txtMes;
	private JTextField txtAño;

	public VentanaCrearDocente(boolean volver) {
		
		setTitle("LECSys - Crear un nuevo registro de docente."+ CheckUsuario.getNombreUsuario());
		setIconImage(Toolkit.getDefaultToolkit().getImage(LECSys.rutaImagenes + "LEC.jpg"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setBounds(10, 20, 370, 340);
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("Button.background"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(25, 15, 70, 20);
		contentPane.add(lblNombre);
		
		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(25, 40, 70, 20);
		contentPane.add(lblApellido);
		
		JLabel lblDni = new JLabel("D.N.I.:");
		lblDni.setBounds(25, 65, 70, 20);
		contentPane.add(lblDni);
		
		JLabel lblDireccion = new JLabel("Dirección:");
		lblDireccion.setBounds(25, 140, 70, 20);
		contentPane.add(lblDireccion);
		
		JLabel lblTelefono = new JLabel("Teléfono:");
		lblTelefono.setBounds(25, 115, 70, 20);
		contentPane.add(lblTelefono);
		
		JLabel lblEmail = new JLabel("e-mail:");
		lblEmail.setBounds(25, 165, 70, 20);
		contentPane.add(lblEmail);
		
		txtTelefono = new JTextField();
		txtTelefono.setBounds(95, 115, 140, 20);
		contentPane.add(txtTelefono);
		configurarJTextField(txtTelefono, 20);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(95, 165, 240, 20);
		contentPane.add(txtEmail);
		configurarJTextField(txtEmail, 40);
		
		txtDireccion = new JTextField();
		txtDireccion.setBounds(95, 140, 240, 20);
		contentPane.add(txtDireccion);
		configurarJTextField(txtDireccion, 45);
		
		txtDni = new JTextField();
		txtDni.setBounds(95, 65, 140, 20);
		contentPane.add(txtDni);
		configurarJTextField(txtDni, 10);
		
		txtApellido = new JTextField();
		txtApellido.setBounds(95, 40, 240, 20);
		contentPane.add(txtApellido);
		configurarJTextField(txtApellido, 20);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(95, 15, 240, 20);
		contentPane.add(txtNombre);
		configurarJTextField(txtNombre, 20);
		
		
		JButton btnSave = new JButton("Guardar");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Calendar fechaSistema = new GregorianCalendar();
				String fechaActual = fechaSistema.get(Calendar.YEAR)+"" + "-" + 
									(fechaSistema.get(Calendar.MONTH)+1)+"" + "-" +
									fechaSistema.get(Calendar.DAY_OF_MONTH)+"";
				String fechaNacimiento = txtAño.getText() + "-" + txtMes.getText() + "-" + txtDia.getText();
						
				String cuerpo[]= {txtNombre.getText(),
								txtApellido.getText(),
								txtDni.getText(),
								txtDireccion.getText(),
								fechaNacimiento,
								txtTelefono.getText(),
								txtEmail.getText(),
								txtSueldo.getText(),
								fechaActual,
								"1"};
				
				ABMCDocentes nuevoProfe = new ABMCDocentes();
				String respuesta[] = ABMCDocentes.buscarProfesor("DNI", txtDni.getText());
				
				if(respuesta[0] == null) {
					
					if(checkCampos()) {
						
						limpiarCampos();
						
						if(nuevoProfe.crearProfesor(cuerpo) == true) {
							
							lblMensageError.setForeground(Color.BLUE);
							lblMensageError.setText("Registro creado.");
						} else {
							lblMensageError.setForeground(Color.RED);
							lblMensageError.setText("Error al guardar.");
						}
					}
				} else {
					lblMensageError.setForeground(Color.RED);
					lblMensageError.setText("Error, el DNI ya existe en un registro guardado.");
				}
			}
		});
		btnSave.setBounds(135, 255, 90, 23);
		contentPane.add(btnSave);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
				
				if(volver) {
					
					VentanaDocentes interfaceProfesores = new VentanaDocentes();
					interfaceProfesores.abrirVentana();
				}
			}
		});
		btnVolver.setBounds(245, 255, 90, 23);
		contentPane.add(btnVolver);
		
		lblMensageError = new JLabel("");
		lblMensageError.setBackground(Color.LIGHT_GRAY);
		lblMensageError.setBounds(25, 220, 325, 25);
		contentPane.add(lblMensageError);
		
		JLabel lblSueldo = new JLabel("Salario:");
		lblSueldo.setBounds(25, 190, 70, 20);
		contentPane.add(lblSueldo);
		
		txtSueldo = new JTextField();
		txtSueldo.setBounds(95, 190, 140, 20);
		contentPane.add(txtSueldo);
		txtSueldo.setColumns(10);
		
		JLabel lblNacimiento = new JLabel("Nacimiento:");
		lblNacimiento.setBounds(25, 90, 70, 20);
		contentPane.add(lblNacimiento);
		
		txtDia = new JTextField();
		txtDia.setHorizontalAlignment(SwingConstants.CENTER);
		txtDia.setBounds(95, 90, 25, 20);
		contentPane.add(txtDia);
		configurarJTextField(txtDia, 2);
		
		JLabel label_1 = new JLabel("/");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(120, 90, 10, 20);
		contentPane.add(label_1);
		
		txtMes = new JTextField();
		txtMes.setHorizontalAlignment(SwingConstants.CENTER);
		txtMes.setBounds(130, 90, 25, 20);
		contentPane.add(txtMes);
		configurarJTextField(txtMes, 2);

		JLabel label_2 = new JLabel("/");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(155, 90, 10, 20);
		contentPane.add(label_2);
		
		txtAño = new JTextField();
		txtAño.setHorizontalAlignment(SwingConstants.CENTER);
		txtAño.setBounds(165, 90, 40, 20);
		contentPane.add(txtAño);
		configurarJTextField(txtAño, 4);
		
		JLabel lblFormato = new JLabel("DD / MM / AAAA");
		lblFormato.setBounds(210, 90, 88, 20);
		contentPane.add(lblFormato);
	}
	
	private void configurarJTextField(Component nombre, int cantidadCaracteres) {
		
		((JTextField) nombre).setColumns(10);
		nombre.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				
				if(((JTextField) nombre).getText().length() >= cantidadCaracteres)
					e.consume();
			}
		});
	}
	
	private void limpiarCampos() {
		
		txtNombre.setText(null);
		txtApellido.setText(null);
		txtDni.setText(null);
		txtDia.setText(null);
		txtMes.setText(null);
		txtAño.setText(null);
		txtDireccion.setText(null);
		txtTelefono.setText(null);
		txtEmail.setText(null);
		txtSueldo.setText(null);
		lblMensageError.setText(null);
	}
	
	private boolean checkCampos() {
		
		boolean i = true;
		lblMensageError.setForeground(Color.RED);
		if(txtNombre.getText().length() < 3) {
			
			i = false;
			lblMensageError.setText("El nombre debe tener más de dos caracteres.");
		}else if(txtApellido.getText().length() < 2) {
			
			i = false;
			lblMensageError.setText("El apellido debe tener más de dos caracteres.");
		}else if(txtDni.getText().length() < 7 ||
				!isNumeric(txtDni.getText())) {
			
			i = false;
			lblMensageError.setText("Error en el formato del DNI (solamente números).");
		}else if(txtDireccion.getText().length() == 0) {
			
			i = false;
			lblMensageError.setText("La dirección no puede estar vacía.");
		}else if(txtTelefono.getText().length() == 0 ||
				!isNumeric(txtTelefono.getText())) {
			
			i = false;
			lblMensageError.setText("Error en el formato del teléfono (solamente números).");
		}else if(txtSueldo.getText().length() < 2 ||
				!isNumeric(txtSueldo.getText())) {
			
			i = false;
			lblMensageError.setText("Error, ingrese sueldo.");
		}
		return i;
	}
		
	private static boolean isNumeric(String cadena){
		
		try {
			
			Double.parseDouble(cadena);
			return true;
		} catch (NumberFormatException e){
			
			return false;
		}
	}
}
