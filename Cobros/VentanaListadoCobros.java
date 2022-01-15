import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.print.PrinterException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;

public class VentanaListadoCobros extends JFrame implements ItemListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tablaCobros;
	private JComboBox<String> comboBoxMes;
	private JComboBox<String> comboBoxAño;
	private Calendar fechaSistema = new GregorianCalendar();
	private JTextField txtSuma;
	private JTextField txtCantOper;
	private String respuesta[][];
	
	public VentanaListadoCobros() {
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(LECSys.rutaImagenes + "LEC.jpg"));
		setTitle("LECSys - Listado de cobros realizados"+ CheckUsuario.getNombreUsuario());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(10, 10, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
			
		JLabel lblMes = new JLabel("Mes:");
		lblMes.setBounds(25, 15, 30, 20);
		contentPane.add(lblMes);
		
		comboBoxMes = new JComboBox<String>();
		comboBoxMes.setModel(new DefaultComboBoxModel<String>(new String[] {"- - - - -", "Enero", "Febrero", "Marzo",
																			"Abril", "Mayo", "Junio", "Julio", "Agosto", 
																			"Septiembre", "Octubre", "Noviembre", "Diciembre"}));
		comboBoxMes.setBounds(55, 15, 100, 20);
		comboBoxMes.setSelectedIndex((fechaSistema.get(Calendar.MONTH)+1));
		comboBoxMes.addItemListener(this);
		contentPane.add(comboBoxMes);

		JLabel lblAño = new JLabel("Año:");
		lblAño.setBounds(190, 15, 30, 20);
		contentPane.add(lblAño);
		
		int año = fechaSistema.get(Calendar.YEAR);
		String años[] = {año+"",(año-1)+"",(año-2)+"",(año-3)+"",(año-4)+""};
		comboBoxAño = new JComboBox<String>();
		comboBoxAño.setModel(new DefaultComboBoxModel<String>(años));
		comboBoxAño.addItemListener(this);
		comboBoxAño.setBounds(220, 15, 80, 20);
		contentPane.add(comboBoxAño);
				
		JLabel lblSuma = new JLabel("Suma:");
		lblSuma.setBounds(340, 15, 40, 20);
		contentPane.add(lblSuma);
		
		txtSuma = new JTextField();
		txtSuma.setBounds(380, 15, 130, 20);
		contentPane.add(txtSuma);
		txtSuma.setColumns(10);
		
		JLabel lblCantOper = new JLabel("Cant. de operaciones:");
		lblCantOper.setBounds(540, 15, 140, 20);
		contentPane.add(lblCantOper);
		
		txtCantOper = new JTextField();
		txtCantOper.setBounds(680, 15, 80, 20);
		contentPane.add(txtCantOper);
		txtCantOper.setColumns(10);

		JScrollPane scrollTabla = new JScrollPane();
		scrollTabla.setBounds(1, 50, 773, 450);
		contentPane.add(scrollTabla);
		
		tablaCobros = new JTable();
		actualizoTabla(comboBoxMes.getSelectedIndex());
		scrollTabla.setViewportView(tablaCobros);
		
		JButton btnImprimir = new JButton("Imprimir");
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					tablaCobros.print();
				} catch (PrinterException d) {
					d.printStackTrace();
				}
			}
		});
		btnImprimir.setBounds(89, 525, 89, 23);
		contentPane.add(btnImprimir);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
			}
		});
		btnVolver.setBounds(569, 525, 89, 23);
		contentPane.add(btnVolver);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				for(int i = 0 ; i < respuesta.length ; i++) {
		
					ABMCCobros.actualizarFactura(respuesta[i][7], (String)tablaCobros.getValueAt(i, 5));
				}
				actualizoTabla(comboBoxMes.getSelectedIndex());
			}
		});
		btnGuardar.setBounds(326, 525, 89, 23);
		contentPane.add(btnGuardar);
	}
	
	private void actualizoTabla(int mes) {
		
		String titulo [] = {"Nombre", "Concepto", "Fecha", "Hora", "Monto", "Factura"};
		respuesta = ABMCCobros.getCobros(mes+"", (String)comboBoxAño.getSelectedItem());
		DefaultTableModel tablaModelo = new DefaultTableModel(respuesta, titulo){

			private static final long serialVersionUID = 1L;
			
			boolean[] columnEditables = new boolean[] {
					false, false, false, false, false, true
			};
			
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		tablaCobros.setModel(tablaModelo);
		tablaCobros.setEnabled(true);
		int sumaIngresos = 0;
		
		if(respuesta != null) {
			
			for(int i = 0 ; i < respuesta.length; i++) {
				sumaIngresos += Integer.parseInt(respuesta[i][4]);
			}
			
			txtSuma.setText(sumaIngresos+"");
			txtCantOper.setText(respuesta.length+"");
		} else {
			
			txtSuma.setText("0");
			txtCantOper.setText("0");
		}
		tablaCobros.getColumnModel().getColumn(0).setPreferredWidth(160);
		tablaCobros.getColumnModel().getColumn(0).setMinWidth(30);
		tablaCobros.getColumnModel().getColumn(0).setMaxWidth(300);
		tablaCobros.getColumnModel().getColumn(1).setPreferredWidth(240);
		tablaCobros.getColumnModel().getColumn(1).setMaxWidth(180);
		tablaCobros.getColumnModel().getColumn(1).setMaxWidth(300);
		tablaCobros.getColumnModel().getColumn(2).setPreferredWidth(60);
		tablaCobros.getColumnModel().getColumn(2).setMaxWidth(40);
		tablaCobros.getColumnModel().getColumn(2).setMaxWidth(300);
		tablaCobros.getColumnModel().getColumn(3).setPreferredWidth(40);
		tablaCobros.getColumnModel().getColumn(3).setMinWidth(40);
		tablaCobros.getColumnModel().getColumn(3).setMaxWidth(300);
		tablaCobros.getColumnModel().getColumn(4).setPreferredWidth(70);
		tablaCobros.getColumnModel().getColumn(4).setMinWidth(50);
		tablaCobros.getColumnModel().getColumn(4).setMaxWidth(300);
		tablaCobros.getColumnModel().getColumn(5).setPreferredWidth(70);
		tablaCobros.getColumnModel().getColumn(5).setMinWidth(50);
		tablaCobros.getColumnModel().getColumn(5).setMaxWidth(300);
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		
		if (e.getSource()==comboBoxMes || e.getSource()==comboBoxAño)
			actualizoTabla(comboBoxMes.getSelectedIndex());
	}
}
