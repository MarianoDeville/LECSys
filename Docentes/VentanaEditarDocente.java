import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import java.awt.Color;
import java.awt.Component;
import java.awt.SystemColor;
import javax.swing.SwingConstants;

public class VentanaEditarDocente extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtDni;
	private JTextField txtDireccion;
	private JTextField txtTelefono;
	private JLabel lblMensageError;
	
	public VentanaEditarDocente(String legajo) {
		
		setTitle("LECSys - Edición de profesores."+ CheckUsuario.getNombreUsuario());
		setIconImage(Toolkit.getDefaultToolkit().getImage(LECSys.rutaImagenes + "LEC.jpg"));
		setModalityType(ModalityType.APPLICATION_MODAL);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(10, 20, 430, 530);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		String informacion[] = ABMCDocentes.buscarProfesor("ID", legajo);
		
		JLabel lblLegajo = new JLabel("Legajo:");
		lblLegajo.setBounds(25, 15, 80, 20);
		contentPanel.add(lblLegajo);
		
		JTextField txtIdProfesor = new JTextField();
		txtIdProfesor.setEditable(false);
		txtIdProfesor.setBounds(105, 15, 140, 20);
		txtIdProfesor.setText(informacion[0]);
		contentPanel.add(txtIdProfesor);
		configurarJTextField(txtIdProfesor, 20);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(25, 40, 80, 20);
		contentPanel.add(lblNombre);
		
		txtNombre = new JTextField();
		contentPanel.add(txtNombre);
		txtNombre.setBounds(105, 40, 240, 20);
		txtNombre.setText(informacion[1]);
		configurarJTextField(txtNombre, 20);
		
		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(25, 65, 80, 20);
		contentPanel.add(lblApellido);
		
		txtApellido = new JTextField();
		contentPanel.add(txtApellido);
		txtApellido.setBounds(105, 65, 240, 20);
		txtApellido.setText(informacion[2]);
		configurarJTextField(txtApellido, 20);
		
		JLabel lblDni = new JLabel("DNI:");
		lblDni.setBounds(25, 90, 80, 20);
		contentPanel.add(lblDni);
		
		txtDni = new JTextField();
		contentPanel.add(txtDni);
		txtDni.setBounds(105, 90, 140, 20);
		txtDni.setText(informacion[3]);
		configurarJTextField(txtDni, 10);
		
		JLabel lblDireccion = new JLabel("Dirección:");
		lblDireccion.setBounds(25, 115, 80, 20);
		contentPanel.add(lblDireccion);
		
		txtDireccion = new JTextField();
		contentPanel.add(txtDireccion);
		txtDireccion.setBounds(105, 115, 240, 20);
		txtDireccion.setText(informacion[4]);
		configurarJTextField(txtDireccion, 45);
		
		String[] parts = informacion[10].split("-");
		
		JLabel lblCumpleaños = new JLabel("Cumpleaños:");
		lblCumpleaños.setBounds(25, 140, 80, 20);
		contentPanel.add(lblCumpleaños);
		
		JTextField txtDia = new JTextField();
		txtDia.setHorizontalAlignment(SwingConstants.CENTER);
		txtDia.setBounds(105, 140, 30, 20);
		txtDia.setText(parts[2]);
		contentPanel.add(txtDia);
		
		JLabel label_1 = new JLabel("/");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(133, 140, 15, 20);
		contentPanel.add(label_1);
		
		JTextField txtMes = new JTextField();
		txtMes.setHorizontalAlignment(SwingConstants.CENTER);
		txtMes.setBounds(145, 140, 30, 20);
		txtMes.setText(parts[1]);
		contentPanel.add(txtMes);
		
		JLabel label_2 = new JLabel("/");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(173, 140, 15, 20);
		contentPanel.add(label_2);
		
		JTextField txtAño = new JTextField();
		txtAño.setHorizontalAlignment(SwingConstants.CENTER);
		txtAño.setBounds(185, 140, 50, 20);
		txtAño.setText(parts[0]);
		contentPanel.add(txtAño);
		
		JLabel lblFormato = new JLabel("DD / MM / AAAA");
		lblFormato.setBounds(240, 140, 90, 20);
		contentPanel.add(lblFormato);
		
		JLabel lblTelefono = new JLabel("Teléfono:");
		lblTelefono.setBounds(25, 165, 80, 20);
		contentPanel.add(lblTelefono);
		
		txtTelefono = new JTextField();
		contentPanel.add(txtTelefono);
		txtTelefono.setBounds(105, 165, 140, 20);
		txtTelefono.setText(informacion[5]);
		configurarJTextField(txtTelefono, 20);
		
		JLabel lblEmail = new JLabel("e-mail:");
		lblEmail.setBounds(25, 190, 80, 20);
		contentPanel.add(lblEmail);
		
		JTextField txtEmail = new JTextField();
		contentPanel.add(txtEmail);
		txtEmail.setBounds(105, 190, 240, 20);
		txtEmail.setText(informacion[6]);
		configurarJTextField(txtEmail, 40);
		
		JLabel lblSueldo = new JLabel("Salario:");
		lblSueldo.setBounds(25, 215, 80, 20);
		contentPanel.add(lblSueldo);
		
		JTextField txtSueldo = new JTextField();
		contentPanel.add(txtSueldo);
		txtSueldo.setBounds(105, 215, 140, 20);
		txtSueldo.setText(informacion[7]);
		configurarJTextField(txtSueldo, 20);
		
		JLabel lblIngreso = new JLabel("Ingreso:");
		lblIngreso.setBounds(25, 240, 80, 20);
		contentPanel.add(lblIngreso);
		
		JTextField txtFechaIngreso = new JTextField();
		txtFechaIngreso.setEnabled(false);
		txtFechaIngreso.setColumns(10);
		txtFechaIngreso.setBounds(105, 240, 90, 20);
		txtFechaIngreso.setText(informacion[8]);
		contentPanel.add(txtFechaIngreso);
		
		JCheckBox checkBoxActivo = new JCheckBox("Activo");
		checkBoxActivo.setBackground(SystemColor.menu);
		checkBoxActivo.setBounds(105, 265, 90, 20);
		
		if(informacion[9].contains("1"))
			checkBoxActivo.setSelected(true);
		else
			checkBoxActivo.setSelected(false);
		
		contentPanel.add(checkBoxActivo);
		
		JScrollPane scrollTabla = new JScrollPane();
		scrollTabla.setBounds(25, 300, 360, 100);
		contentPanel.add(scrollTabla);

		String titulo [] = {"Año", "Nivel", "Turno"};
		
		DefaultTableModel tablaModelo = new DefaultTableModel(ABMCCurso.busarCursos(legajo), titulo);
		JTable tablaCursos = new JTable(tablaModelo);
		tablaCursos.setEnabled(false);
		tablaCursos.getColumnModel().getColumn(0).setPreferredWidth(50);
		tablaCursos.getColumnModel().getColumn(0).setMinWidth(50);
		tablaCursos.getColumnModel().getColumn(0).setMaxWidth(350);
		tablaCursos.getColumnModel().getColumn(1).setPreferredWidth(100);
		tablaCursos.getColumnModel().getColumn(1).setMinWidth(50);
		tablaCursos.getColumnModel().getColumn(1).setMaxWidth(350);
		tablaCursos.getColumnModel().getColumn(2).setPreferredWidth(100);
		tablaCursos.getColumnModel().getColumn(2).setMinWidth(50);
		tablaCursos.getColumnModel().getColumn(2).setMaxWidth(350);
		scrollTabla.setViewportView(tablaCursos);
		
		lblMensageError = new JLabel("");
		lblMensageError.setBackground(Color.LIGHT_GRAY);
		lblMensageError.setBounds(25, 415, 390, 25);
		contentPanel.add(lblMensageError);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ABMCDocentes guardarCambios = new ABMCDocentes();
				String estado;
				
				if(checkBoxActivo.isSelected() == true)
					estado = "1";
				else
					estado = "0";
				
				String fechaNacimiento = txtAño.getText() + "-" + txtMes.getText() + "-" + txtDia.getText();
				String registroModificado[] = { legajo,
												txtNombre.getText(),
												txtApellido.getText(),
												txtDni.getText(),
												txtDireccion.getText(),
												fechaNacimiento,
												txtTelefono.getText(),
												txtEmail.getText(),
												txtSueldo.getText(),
												estado};
				
				if(checkCampos()) {
					
					if(guardarCambios.modificarDatoProfesor(registroModificado)) {
						
						lblMensageError.setForeground(Color.BLUE);
						lblMensageError.setText("Cambios guardados.");
					} else {
						
						lblMensageError.setForeground(Color.RED);
						lblMensageError.setText("Error al intentar guardar los cambios.");
					}
				}
			}
		});
		btnGuardar.setBounds(35, 450, 90, 23);
		contentPanel.add(btnGuardar);
		
		JButton btnImprimir = new JButton("Print");
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				contentPanel.setBackground(Color.WHITE);
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
				btnGuardar.setVisible(true);
				btnImprimir.setVisible(true);
			}
		});
		btnImprimir.setBounds(165, 450, 90, 23);
		contentPanel.add(btnImprimir);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
				VentanaDocentes interfaceProfesores = new VentanaDocentes();
				interfaceProfesores.abrirVentana();
			}
		});
		btnVolver.setActionCommand("Cancelar");
		btnVolver.setBounds(300, 450, 90, 23);
		contentPanel.add(btnVolver);
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
	
	private static boolean isNumeric(String cadena) {
		
		try {
			
			Double.parseDouble(cadena);
			return true;
		} catch (NumberFormatException e){
			
			return false;
		}
	}
}
