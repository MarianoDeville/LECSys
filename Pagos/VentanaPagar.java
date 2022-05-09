import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class VentanaPagar extends JFrame implements ItemListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComboBox<String> comboBoxConcepto;
	private JTextField txtNombre;
	private JTextField txtMonto;
	private JTextField txtFactura;
	private JTextField txtComentario;
	private JLabel lblMensageError;
	private String respuesta[][];
	private String opciones[] = null;
	private JButton btnPagar;
	private Calendar fechaSistema = new GregorianCalendar();
	private JTextField txtConcepto;

	public VentanaPagar() {
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(LECSys.rutaImagenes + "LEC.jpg"));
		setTitle("LECSys - Realizar un pago."+ CheckUsuario.getNombreUsuario());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(10, 10, 400, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		
		respuesta = ABMCDocentes.getProfesores(true);

		if(respuesta!=null) {
			
			opciones = new String[respuesta.length+2];
			
			for(int i = 0; i < respuesta.length; i++) {
				
				opciones[i+2] = respuesta[i][1] + " " + respuesta[i][2]; 
			}
		} else {
			opciones = new String[2];
		}
		
		opciones[0] = "Seleccione";
		opciones[1] = "Ingresar nombre";
		
		comboBoxConcepto = new JComboBox<String>();
		comboBoxConcepto.setModel(new DefaultComboBoxModel<String>(opciones));
		comboBoxConcepto.setBounds(25, 25, 200, 20);
		comboBoxConcepto.addItemListener(this);
		contentPane.add(comboBoxConcepto);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(25, 60, 80, 20);
		contentPane.add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setEditable(false);
		txtNombre.setBounds(105, 60, 120, 20);
		contentPane.add(txtNombre);
		configurarJTextField(txtNombre, 40);
		
		JLabel lblConcepto = new JLabel("Concepto:");
		lblConcepto.setBounds(25, 85, 80, 20);
		contentPane.add(lblConcepto);
		
		txtConcepto = new JTextField();
		txtConcepto.setEditable(false);
		txtConcepto.setBounds(105, 85, 120, 20);
		contentPane.add(txtConcepto);
		txtConcepto.setColumns(10);
		
		JLabel lblMonto = new JLabel("Monto:");
		lblMonto.setBounds(25, 110, 80, 20);
		contentPane.add(lblMonto);
		
		txtMonto = new JTextField();
		txtMonto.setEditable(false);
		txtMonto.setBounds(105, 110, 80, 20);
		contentPane.add(txtMonto);
		txtMonto.setColumns(10);
		
		JLabel lblFactura = new JLabel("Factura:");
		lblFactura.setBounds(25, 135, 80, 20);
		contentPane.add(lblFactura);
		
		txtFactura = new JTextField();
		txtFactura.setBounds(105, 135, 120, 20);
		contentPane.add(txtFactura);
		configurarJTextField(txtComentario, 20);
		
		JLabel lblComentario = new JLabel("Comentario:");
		lblComentario.setBounds(25, 160, 80, 20);
		contentPane.add(lblComentario);
		
		txtComentario = new JTextField();
		txtComentario.setBounds(105, 160, 200, 20);
		contentPane.add(txtComentario);
		configurarJTextField(txtComentario, 45);
		
		lblMensageError = new JLabel("");
		lblMensageError.setForeground(Color.RED);
		lblMensageError.setBackground(Color.LIGHT_GRAY);
		lblMensageError.setBounds(25, 190, 350, 25);
		contentPane.add(lblMensageError);
		
		btnPagar = new JButton("Pagar");
		btnPagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(guardoPago())
					dispose();
			}
		});
		btnPagar.setBounds(48, 230, 90, 23);
		btnPagar.setEnabled(false);
		contentPane.add(btnPagar);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
			}
		});
		btnVolver.setBounds(256, 230, 90, 23);
		contentPane.add(btnVolver);
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		
		if (e.getSource()==comboBoxConcepto) {
			
			txtNombre.setText(null);
			txtMonto.setText(null);
			btnPagar.setEnabled(true);
			
			if(comboBoxConcepto.getSelectedIndex() == 0) {

				btnPagar.setEnabled(false);
				txtNombre.setEditable(false);
				txtMonto.setEditable(false);
				txtConcepto.setEditable(false);
			}
			if(comboBoxConcepto.getSelectedIndex() == 1) {

				txtNombre.setEditable(true);
				txtMonto.setEditable(true);
				txtConcepto.setEditable(true);
			}
			if(comboBoxConcepto.getSelectedIndex() > 1) {
	
				txtNombre.setText((String)comboBoxConcepto.getSelectedItem());
				txtMonto.setText(respuesta[comboBoxConcepto.getSelectedIndex()-2][7]);
				txtConcepto.setText("Sueldo");
				txtNombre.setEditable(false);
				txtMonto.setEditable(false);
				txtConcepto.setEditable(false);
			}
		}
	}
	
	private boolean guardoPago() {
		
		String cuerpo[] = new String[11];

		if(comboBoxConcepto.getSelectedIndex() > 1)
			cuerpo[0] = respuesta[comboBoxConcepto.getSelectedIndex()-2][0];
		else 
			cuerpo[0] = null;
		
		cuerpo[1] = txtNombre.getText();
		cuerpo[2] = txtConcepto.getText();
		cuerpo[3] = fechaSistema.get(Calendar.DAY_OF_MONTH)+"";
		cuerpo[4] = (fechaSistema.get(Calendar.MONTH)+1)+"";
		cuerpo[5] = fechaSistema.get(Calendar.YEAR)+"";
		cuerpo[6] = fechaSistema.get(Calendar.HOUR) +":" +fechaSistema.get(Calendar.MINUTE);
		cuerpo[7] = txtMonto.getText();
		cuerpo[8] = txtFactura.getText();
		cuerpo[9] = txtComentario.getText();
		
		if(txtNombre.getText().length() < 3) {
			
			lblMensageError.setText("El nombre debe tener más de dos caracteres.");
			return false;
		} else if(txtConcepto.getText().length() < 3 ) {
			
			lblMensageError.setText("Debe completar el concepto del pago.");
			return false;
		} else if(txtMonto.getText().length() < 2 || !isNumeric(txtMonto.getText())) {
			
			lblMensageError.setText("Ingre un valor numérico válido para el monto.");
			return false;
		}
		
		if(ABMCPagos.nuevoPago(cuerpo))
			return true;
		
		lblMensageError.setText("Error al intentar guardar en la base de datos.");
		return false;
	}
	
	private static boolean isNumeric(String cadena){
		
		try {
			
			Double.parseDouble(cadena);
			return true;
		} catch (NumberFormatException e){
			
			return false;
		}
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
