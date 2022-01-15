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

public class VentanaBusquedaDocente extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtDireccion;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtDni;
	private JTextField txtIdProfesor;
	private JTextField txtTelefono;
	private JTextField txtEmail;
	private JTextField txtSueldo;
	private JTextField txtFechaIngreso;
	private JLabel lblMensageError;
	private JCheckBox checkBoxActivo;
	private JButton btnGuardar;
	private JButton btnImprimir;
	private JButton btnBuscarLegajo;
	private JButton btnBuscarDNI;
	private String idProfesor;
	private JTable tablaCursos;
	private DefaultTableModel tablaModelo;
	private String titulo [] = {"Año", "Level"};
	private JTextField txtDia;
	private JTextField txtMes;
	private JTextField txtAño;
	
	public VentanaBusquedaDocente() {
		setTitle("LECSys - Buscar y editar fichas de profesores."+ CheckUsuario.getNombreUsuario());
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
		
		JLabel lblLegajo = new JLabel("Legajo:");
		lblLegajo.setBounds(25, 15, 80, 20);
		contentPanel.add(lblLegajo);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(25, 65, 80, 20);
		contentPanel.add(lblNombre);
		
		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(25, 90, 80, 20);
		contentPanel.add(lblApellido);
		
		JLabel lblDni = new JLabel("DNI:");
		lblDni.setBounds(25, 40, 80, 20);
		contentPanel.add(lblDni);
		
		JLabel lblDireccion = new JLabel("Dirección:");
		lblDireccion.setBounds(25, 115, 80, 20);
		contentPanel.add(lblDireccion);
		
		txtIdProfesor = new JTextField();
		txtIdProfesor.setBounds(105, 15, 140, 20);
		contentPanel.add(txtIdProfesor);
		configurarJTextField(txtIdProfesor, 20);
		
		txtNombre = new JTextField();
		txtNombre.setEnabled(false);
		contentPanel.add(txtNombre);
		txtNombre.setBounds(105, 65, 240, 20);
		configurarJTextField(txtNombre, 20);
		
		txtApellido = new JTextField();
		txtApellido.setEnabled(false);
		contentPanel.add(txtApellido);
		txtApellido.setBounds(105, 90, 240, 20);
		configurarJTextField(txtApellido, 20);
		
		txtDni = new JTextField();
		contentPanel.add(txtDni);
		txtDni.setBounds(105, 40, 140, 20);
		configurarJTextField(txtDni, 10);
		
		txtDireccion = new JTextField();
		txtDireccion.setEnabled(false);
		contentPanel.add(txtDireccion);
		txtDireccion.setBounds(105, 115, 240, 20);
		configurarJTextField(txtDireccion, 45);
		
		txtTelefono = new JTextField();
		txtTelefono.setEnabled(false);
		contentPanel.add(txtTelefono);
		txtTelefono.setBounds(105, 165, 140, 20);
		configurarJTextField(txtTelefono, 20);
		
		txtEmail = new JTextField();
		txtEmail.setEnabled(false);
		contentPanel.add(txtEmail);
		txtEmail.setBounds(105, 190, 240, 20);
		configurarJTextField(txtEmail, 40);
		
		txtSueldo = new JTextField();
		txtSueldo.setEnabled(false);
		contentPanel.add(txtSueldo);
		txtSueldo.setBounds(105, 215, 140, 20);
		configurarJTextField(txtSueldo, 20);
		
		txtFechaIngreso = new JTextField();
		txtFechaIngreso.setEditable(false);
		txtFechaIngreso.setColumns(10);
		txtFechaIngreso.setBounds(105, 240, 90, 20);
		contentPanel.add(txtFechaIngreso);

		JScrollPane scrollTabla = new JScrollPane();
		scrollTabla.setBounds(25, 300, 360, 100);
		contentPanel.add(scrollTabla);

		tablaModelo = new DefaultTableModel(null, titulo);
		tablaCursos = new JTable(tablaModelo);
		tablaCursos.setEnabled(false);
		tablaCursos.getColumnModel().getColumn(0).setPreferredWidth(165);
		tablaCursos.getColumnModel().getColumn(0).setMinWidth(50);
		tablaCursos.getColumnModel().getColumn(0).setMaxWidth(350);
		tablaCursos.getColumnModel().getColumn(1).setPreferredWidth(165);
		tablaCursos.getColumnModel().getColumn(1).setMaxWidth(350);
		scrollTabla.setViewportView(tablaCursos);
		
		btnBuscarLegajo = new JButton("Buscar");
		btnBuscarLegajo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				mostrarBusqueda("ID", txtIdProfesor.getText());
			}
		});
		btnBuscarLegajo.setBounds(255, 15, 90, 20);
		contentPanel.add(btnBuscarLegajo);
		
		btnBuscarDNI = new JButton("Buscar");
		btnBuscarDNI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				mostrarBusqueda("DNI", txtDni.getText());
			}
		});
		btnBuscarDNI.setBounds(255, 40, 90, 20);
		contentPanel.add(btnBuscarDNI);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.setEnabled(false);
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ABMCDocentes guardarCambios = new ABMCDocentes();
				String estado;
				
				if(checkBoxActivo.isSelected() == true)
					estado = "1";
				else
					estado = "0";
				
				String fechaNacimiento = txtAño.getText() + "-" + txtMes.getText() + "-" + txtDia.getText();
				String registroModificado[] = { idProfesor,
												txtNombre.getText(),
												txtApellido.getText(),
												txtDni.getText(),
												txtDireccion.getText(),
												fechaNacimiento,
												txtTelefono.getText(),
												txtEmail.getText(),
												txtSueldo.getText(),
												estado};
				
				if(checkCampos())
				{
					if(guardarCambios.modificarDatoProfesor(registroModificado))
					{
						limpiarCampos();
						lblMensageError.setForeground(Color.BLUE);
						lblMensageError.setText("Cambios guardados.");
					}
					else
					{
						lblMensageError.setForeground(Color.RED);
						lblMensageError.setText("Error al intentar guardar los cambios.");
					}
				}
			}
		});
		btnGuardar.setBounds(14, 450, 90, 23);
		contentPanel.add(btnGuardar);
		
		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				limpiarCampos();
			}
		});
		btnLimpiar.setBounds(214, 450, 90, 23);
		contentPanel.add(btnLimpiar);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
				VentanaDocentes interfaceProfesores = new VentanaDocentes();
				interfaceProfesores.abrirVentana();
			}
		});
		btnVolver.setActionCommand("Cancelar");
		btnVolver.setBounds(314, 450, 90, 23);
		contentPanel.add(btnVolver);
		
		JLabel lblTelefono = new JLabel("Teléfono:");
		lblTelefono.setBounds(25, 165, 80, 20);
		contentPanel.add(lblTelefono);
		
		JLabel lblEmail = new JLabel("e-mail:");
		lblEmail.setBounds(25, 190, 80, 20);
		contentPanel.add(lblEmail);
		
		JLabel lblSueldo = new JLabel("Salario:");
		lblSueldo.setBounds(25, 215, 80, 20);
		contentPanel.add(lblSueldo);
		
		JLabel lblIngreso = new JLabel("Ingreso:");
		lblIngreso.setBounds(25, 240, 80, 20);
		contentPanel.add(lblIngreso);
		
		checkBoxActivo = new JCheckBox("Activo");
		checkBoxActivo.setEnabled(false);
		checkBoxActivo.setBackground(SystemColor.menu);
		checkBoxActivo.setBounds(105, 265, 90, 20);
		contentPanel.add(checkBoxActivo);
		
		lblMensageError = new JLabel("");
		lblMensageError.setBackground(Color.LIGHT_GRAY);
		lblMensageError.setBounds(25, 415, 390, 25);
		contentPanel.add(lblMensageError);
		
		btnImprimir = new JButton("Print");
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				contentPanel.setBackground(Color.WHITE);
				btnGuardar.setVisible(false);
				btnImprimir.setVisible(false);
				btnBuscarDNI.setVisible(false);
				btnBuscarLegajo.setVisible(false);
				btnVolver.setVisible(false);
				
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
				btnBuscarDNI.setVisible(true);
				btnBuscarLegajo.setVisible(true);
				btnVolver.setVisible(true);
			}
		});
		btnImprimir.setEnabled(false);
		btnImprimir.setBounds(114, 450, 90, 23);
		contentPanel.add(btnImprimir);
		
		JLabel lblCumpleaños = new JLabel("Cumplea\u00F1os:");
		lblCumpleaños.setBounds(25, 140, 80, 20);
		contentPanel.add(lblCumpleaños);
		
		txtDia = new JTextField();
		txtDia.setHorizontalAlignment(SwingConstants.CENTER);
		txtDia.setEnabled(false);
		txtDia.setBounds(105, 140, 30, 20);
		contentPanel.add(txtDia);
		
		JLabel label_1 = new JLabel("/");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(133, 140, 15, 20);
		contentPanel.add(label_1);
		
		txtMes = new JTextField();
		txtMes.setHorizontalAlignment(SwingConstants.CENTER);
		txtMes.setEnabled(false);
		txtMes.setBounds(145, 140, 30, 20);
		contentPanel.add(txtMes);
		
		JLabel label_2 = new JLabel("/");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(173, 140, 15, 20);
		contentPanel.add(label_2);
		
		txtAño = new JTextField();
		txtAño.setHorizontalAlignment(SwingConstants.CENTER);
		txtAño.setEnabled(false);
		txtAño.setBounds(185, 140, 50, 20);
		contentPanel.add(txtAño);
		
		JLabel lblFormato = new JLabel("DD / MM / AAAA");
		lblFormato.setBounds(240, 140, 90, 20);
		contentPanel.add(lblFormato);
	}

	private void limpiarCampos() {
		idProfesor = null;
		txtIdProfesor.setText(null);
		txtNombre.setText(null);
		txtApellido.setText(null);
		txtDni.setText(null);
		txtDireccion.setText(null);
		txtTelefono.setText(null);
		txtEmail.setText(null);
		txtFechaIngreso.setText(null);
		txtSueldo.setText(null);
		lblMensageError.setText(null);
		checkBoxActivo.setSelected(false);
		btnGuardar.setEnabled(false);
		btnImprimir.setEnabled(false);
		tablaModelo.setDataVector(null, titulo);
		tablaCursos.setModel(tablaModelo);
		txtNombre.setEnabled(false);
		txtApellido.setEnabled(false);
		txtSueldo.setEnabled(false);
		checkBoxActivo.setEnabled(false);
		txtDireccion.setEnabled(false);
		txtEmail.setEnabled(false);
		txtTelefono.setEnabled(false);
		txtDia.setEnabled(false);
		txtMes.setEnabled(false);
		txtAño.setEnabled(false);
		txtIdProfesor.setEnabled(true);
		txtDni.setEnabled(true);
		btnBuscarDNI.setEnabled(true);
		btnBuscarLegajo.setEnabled(true);
	}
	
	private void configurarJTextField(Component nombre, int cantidadCaracteres)
	{
		((JTextField) nombre).setColumns(cantidadCaracteres);
		nombre.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e)
			{
				if(((JTextField) nombre).getText().length() >= cantidadCaracteres)
					e.consume();
			}
		});
	}
	
	private void mostrarBusqueda(String campo, String valor) {
		
		if(valor.contentEquals("")) {
			
			limpiarCampos();
			lblMensageError.setForeground(Color.RED);
			lblMensageError.setText("No ingresó valor para buscar.");
		} else {
			
			limpiarCampos();
			ABMCDocentes buscar = new ABMCDocentes();
			String resProfe[] = buscar.buscarProfesor(campo, valor);
			
			if(resProfe[0] != null) {
				idProfesor = resProfe[0];
				txtIdProfesor.setText(idProfesor);
				txtNombre.setText(resProfe[1]);
				txtApellido.setText(resProfe[2]);
				txtDni.setText(resProfe[3]);
				txtDireccion.setText(resProfe[4]);
				txtTelefono.setText(resProfe[5]);
				txtEmail.setText(resProfe[6]);
				txtSueldo.setText(resProfe[7]);
				txtFechaIngreso.setText(resProfe[8]);
				txtIdProfesor.setEnabled(false);
				btnBuscarDNI.setEnabled(false);
				btnBuscarLegajo.setEnabled(false);
				
				if(resProfe[9].equals("1"))
					checkBoxActivo.setSelected(true);
				else
					checkBoxActivo.setSelected(false);
				
				String fechaNac[] = resProfe[10].split("-");
				txtDia.setText(fechaNac[2]);
				txtMes.setText(fechaNac[1]);
				txtAño.setText(fechaNac[0]);
				btnGuardar.setEnabled(true);
				btnImprimir.setEnabled(true);
				txtNombre.setEnabled(true);
				txtApellido.setEnabled(true);
				txtSueldo.setEnabled(true);
				checkBoxActivo.setEnabled(true);
				txtDireccion.setEnabled(true);
				txtEmail.setEnabled(true);
				txtTelefono.setEnabled(true);
				txtDia.setEnabled(true);
				txtMes.setEnabled(true);
				txtAño.setEnabled(true);
				
				String listaCursos[][] = ABMCCurso.getListaCursos(true);
				
				if(listaCursos != null) {

					int e=0;
					
					for(int i = 0 ; i < listaCursos.length ; i++)
					{
						if(listaCursos[i][3].contentEquals(idProfesor))
						{
							listaCursos[e][0] = listaCursos[i][1];
							listaCursos[e][1] = listaCursos[i][2];
							e++;
						}
					}
				
					String procesado[][] = new String[e][2];
					
					for(int i = 0 ; i < procesado.length ; i++)
					{
						procesado[i][0] = listaCursos[i][0];
						procesado[i][1] = listaCursos[i][1];
					}
					
					tablaModelo.setDataVector(procesado, titulo);
				} else
					tablaModelo.setDataVector(null, titulo);

				tablaCursos.setModel(tablaModelo);
	
			} else {
				limpiarCampos();
				lblMensageError.setForeground(Color.RED);
				lblMensageError.setText("No se encontraron coincidencias.");
			}
		}
	}
	
	private boolean checkCampos()
	{
		boolean i = true;
		lblMensageError.setForeground(Color.RED);
		if(txtNombre.getText().length() < 3)
		{
			i = false;
			lblMensageError.setText("El nombre debe tener más de dos caracteres.");
		}else if(txtApellido.getText().length() < 3)
		{
			i = false;
			lblMensageError.setText("El apellido debe tener más de dos caracteres.");
		}else if(txtDni.getText().length() < 7 ||
				!isNumeric(txtDni.getText()))
		{
			i = false;
			lblMensageError.setText("Error en el formato del DNI (solamente números).");
		}else if(txtDireccion.getText().length() == 0)
		{
			i = false;
			lblMensageError.setText("La dirección no puede estar vacía.");
		}else if(txtTelefono.getText().length() == 0 ||
				!isNumeric(txtTelefono.getText()))
		{
			i = false;
			lblMensageError.setText("Error en el formato del teléfono (solamente números).");
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
