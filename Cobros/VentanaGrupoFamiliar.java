import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class VentanaGrupoFamiliar extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombreFamilia;
	private JLabel lblMensageError;
	private JTextField txtValorInscripción;
	private int totalCuota = 0;
	private int inscripcion = 0;
	private int descuentoGrupo = 0;
	private int descuentoEfectivo = 0;
	private Calendar fechaSistema = new GregorianCalendar();
	private JTextField txtTotalPagar;
	private JTextField txtDescuento;
	private JTextField txtDescEfectivo;

	public VentanaGrupoFamiliar(String lista[][]) {
		
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(LECSys.rutaImagenes + "LEC.jpg"));
		setTitle("LECSys - Cobrar a grupo familiar."+ CheckUsuario.getNombreUsuario());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 540);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		int cantIntegrantes = lista.length;
		
		for(int i = 0; i < lista.length; i++ ) {
			
			totalCuota += Integer.parseInt(lista[i][5]);
		}
		
		JLabel lblNombreFamilia = new JLabel("Nombre de familia:");
		lblNombreFamilia.setBounds(35, 325, 110, 20);
		contentPane.add(lblNombreFamilia);
		
		txtNombreFamilia = new JTextField();
		txtNombreFamilia.setBounds(145, 325, 230, 20);
		contentPane.add(txtNombreFamilia);
		configurarJTextField(txtNombreFamilia, 40);
		
		lblMensageError = new JLabel("");
		lblMensageError.setBounds(25, 405, 650, 25);
		lblMensageError.setForeground(Color.RED);
		lblMensageError.setBackground(Color.LIGHT_GRAY);
		contentPane.add(lblMensageError);
		
		
		JLabel lblInscripción = new JLabel("Inscripción:");
		lblInscripción.setBounds(35, 350, 110, 20);
		contentPane.add(lblInscripción);
		
		txtValorInscripción = new JTextField();
		txtValorInscripción.setBounds(145, 350, 70, 20);
		txtValorInscripción.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
            	
            	checkCampos();
            	inscripcion *= cantIntegrantes;
            	txtTotalPagar.setText((inscripcion + totalCuota - descuentoGrupo - descuentoEfectivo) + "");
            }});
		contentPane.add(txtValorInscripción);
				
		JScrollPane scrollTabla = new JScrollPane();
		scrollTabla.setBounds(1, 30, 680, 284);
		contentPane.add(scrollTabla);
		JTable tablaAlumnos = new JTable();
		tablaAlumnos.setEnabled(false);
		tablaAlumnos.setRowSelectionAllowed(false);
		String titulo [] = {"Legajo", "Nombre", "Apellido", "DNI", "Curso", "Valor cuota"};
		DefaultTableModel tablaModelo = new DefaultTableModel(lista, titulo);
		tablaAlumnos.setModel(tablaModelo);
		tablaAlumnos.getColumnModel().getColumn(0).setPreferredWidth(50);
		tablaAlumnos.getColumnModel().getColumn(0).setMinWidth(30);
		tablaAlumnos.getColumnModel().getColumn(0).setMaxWidth(300);
		tablaAlumnos.getColumnModel().getColumn(1).setPreferredWidth(160);
		tablaAlumnos.getColumnModel().getColumn(1).setMaxWidth(180);
		tablaAlumnos.getColumnModel().getColumn(1).setMaxWidth(300);
		tablaAlumnos.getColumnModel().getColumn(2).setPreferredWidth(160);
		tablaAlumnos.getColumnModel().getColumn(2).setMaxWidth(180);
		tablaAlumnos.getColumnModel().getColumn(2).setMaxWidth(300);
		tablaAlumnos.getColumnModel().getColumn(3).setPreferredWidth(160);
		tablaAlumnos.getColumnModel().getColumn(3).setMinWidth(60);
		tablaAlumnos.getColumnModel().getColumn(3).setMaxWidth(300);
		tablaAlumnos.getColumnModel().getColumn(4).setPreferredWidth(160);
		tablaAlumnos.getColumnModel().getColumn(4).setMinWidth(60);
		tablaAlumnos.getColumnModel().getColumn(4).setMaxWidth(300);
		tablaAlumnos.getColumnModel().getColumn(5).setPreferredWidth(120);
		tablaAlumnos.getColumnModel().getColumn(5).setMinWidth(60);
		tablaAlumnos.getColumnModel().getColumn(5).setMaxWidth(300);
		scrollTabla.setViewportView(tablaAlumnos);
		
		JLabel lblMontoAPagar = new JLabel("Monto a pagar:");
		lblMontoAPagar.setBounds(35, 375, 110, 20);
		contentPane.add(lblMontoAPagar);
		
		txtTotalPagar = new JTextField();
		txtTotalPagar.setEditable(false);
		txtTotalPagar.setColumns(10);
		txtTotalPagar.setBounds(145, 375, 110, 20);
		txtTotalPagar.setText(totalCuota + "");
		contentPane.add(txtTotalPagar);
		
		JLabel lblDescuento = new JLabel("Descuento grupo:");
		lblDescuento.setBounds(390, 325, 120, 20);
		contentPane.add(lblDescuento);
		
		txtDescuento = new JTextField();
		txtDescuento.setBounds(510, 325, 25, 20);
		txtDescuento.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
            	
            	checkCampos();
            	inscripcion *= cantIntegrantes;
            	txtTotalPagar.setText((inscripcion + totalCuota - descuentoGrupo - descuentoEfectivo) + "");
            }});
		contentPane.add(txtDescuento);
		configurarJTextField(txtDescuento, 2);

		JLabel lblPorcentaje = new JLabel("%");
		lblPorcentaje.setBounds(540, 325, 20, 20);
		contentPane.add(lblPorcentaje);
		
		JLabel lblDescPEfectivo = new JLabel("Desc. pago efectivo:");
		lblDescPEfectivo.setBounds(390, 350, 120, 20);
		contentPane.add(lblDescPEfectivo);
		
		txtDescEfectivo = new JTextField((String) null);
		txtDescEfectivo.setEditable(true);
		txtDescEfectivo.setColumns(10);
		txtDescEfectivo.setBounds(510, 350, 100, 20);
		contentPane.add(txtDescEfectivo);
		
		JButton btnGuardar = new JButton("Activar y cobrar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if(checkCampos()) {

					String familia[] = {txtNombreFamilia.getText(),cantIntegrantes+"","0","1"};
					int registroCreado = ABMCGrupoFamiliar.crearGrupoFamilia(familia);
	            	txtTotalPagar.setText((inscripcion * cantIntegrantes + totalCuota - descuentoGrupo - descuentoEfectivo) + "");

					if(registroCreado > 0) {
						
						for(int i = 0; i < lista.length; i++) {
							ABMCAlumnos.actualizarGrupoFamiliar(lista[i][0], registroCreado + "", txtDescuento.getText());
						}

						String cuerpo[] = new String [10];
						cuerpo[0] = registroCreado + "";
						cuerpo[1] = txtNombreFamilia.getText();
						cuerpo[2] = "Inscripción : " + inscripcion * cantIntegrantes 
									+ " primer cuota: " + (totalCuota - descuentoGrupo)
									+ " descuento pago efectivo: " +  descuentoEfectivo;
						cuerpo[3] = fechaSistema.get(Calendar.DAY_OF_MONTH)+"";
						cuerpo[4] = (fechaSistema.get(Calendar.MONTH)+1)+"";
						cuerpo[5] = fechaSistema.get(Calendar.YEAR)+"";
						cuerpo[6] = fechaSistema.get(Calendar.HOUR) +":" +fechaSistema.get(Calendar.MINUTE);
						cuerpo[7] = txtTotalPagar.getText();
						cuerpo[8] = "";
						cuerpo[9] = "0";
						ABMCCobros.nuevoCobro(cuerpo);
						
						try {
							
							VentanaHabilitarCobrar frame = new VentanaHabilitarCobrar();
							frame.setVisible(true);
							VentanaReciboCobro frame1 = new VentanaReciboCobro(cuerpo);
							frame1.setVisible(true);
						} catch (Exception d) {
						
							d.printStackTrace();
						}
						dispose();
					} else {
						
						lblMensageError.setForeground(Color.RED);
						lblMensageError.setText("Error al intentar guardar el registro.");
					}
				} 
			}
		});
		btnGuardar.setBounds(80, 445, 130, 23);
		contentPane.add(btnGuardar);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
				
				try {
					
					VentanaHabilitarCobrar frame = new VentanaHabilitarCobrar();
					frame.setVisible(true);
				} catch (Exception d) {
					
					d.printStackTrace();
				}
			}
		});
		btnVolver.setBounds(450, 445, 130, 23);
		contentPane.add(btnVolver);
	}
	
	private boolean checkCampos() {
		
		boolean bandera = true;
		lblMensageError.setForeground(Color.RED);
		
		if(txtNombreFamilia.getText().length() < 3) {
			
			bandera = false;
			lblMensageError.setText("El nombre debe tener más de dos caracteres.");
		} else {
			
			if(ABMCGrupoFamiliar.nombreDuplicado(txtNombreFamilia.getText())) {
				
				bandera = false;
				lblMensageError.setText("El nombre de familia ya está en uso, elija otro.");
			}
		}
		
		try {
			
			descuentoGrupo = Integer.parseInt(txtDescuento.getText());
			descuentoGrupo = (totalCuota*descuentoGrupo)/100;
		}catch (Exception f) {
			
			bandera = false;
			lblMensageError.setText("El campo descuento debe ser un número entre 0 y 99.");
		}
		
		try {

			descuentoEfectivo = Integer.parseInt(txtDescEfectivo.getText());
		}catch (Exception f) {
			
			bandera = false;
			lblMensageError.setText("El campo descuento debe ser un número.");
		}
		
		try {
			
			inscripcion = Integer.parseInt(txtValorInscripción.getText());
		}catch (Exception f) {
			
			bandera = false;
			lblMensageError.setText("El campo Inscripción no puede estar vacío y debe ser un número");
		}
		return bandera;
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
}
