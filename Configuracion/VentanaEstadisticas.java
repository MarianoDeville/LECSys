import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;

public class VentanaEstadisticas extends JFrame implements ItemListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tabla;
	private JComboBox<String> comboBoxAño;
	private JTextField txtIngresosTotales;
	private JTextField txtEgresosTotales;
	

	public VentanaEstadisticas() {
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(LECSys.rutaImagenes + "LEC.jpg"));
		setTitle("LECSys - Estadísticas."+ CheckUsuario.getNombreUsuario());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(10, 10, 680, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblResumirInformacinPor = new JLabel("Seleccione el a\u00F1o:");
		lblResumirInformacinPor.setBounds(31, 33, 130, 23);
		contentPane.add(lblResumirInformacinPor);
		
		comboBoxAño = new JComboBox<String>();
		comboBoxAño.setModel(new DefaultComboBoxModel<String>(new String[] {"2021", "2022"}));
		comboBoxAño.setSelectedIndex(0);
		comboBoxAño.setEnabled(true);
		comboBoxAño.setBounds(160, 33, 90, 23);
		comboBoxAño.addItemListener(this);
		contentPane.add(comboBoxAño);
	
		txtIngresosTotales = new JTextField();
		txtIngresosTotales.setBounds(424, 432, 100, 23);
		contentPane.add(txtIngresosTotales);
		txtIngresosTotales.setColumns(10);
		
		txtEgresosTotales = new JTextField();
		txtEgresosTotales.setBounds(539, 432, 100, 23);
		contentPane.add(txtEgresosTotales);
		txtEgresosTotales.setColumns(10);
		
		JScrollPane scrollTabla = new JScrollPane();
		scrollTabla.setBounds(31, 74, 610, 359);
		contentPane.add(scrollTabla);
		
		tabla = new JTable();
		actualizarTabla();
		scrollTabla.setViewportView(tabla);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
			}
		});
		btnVolver.setBounds(539, 522, 90, 23);
		contentPane.add(btnVolver);
		
		Estadisticas.actualizoEstadisticas();
	}
	
	private void actualizarTabla() {
		
		String titulo [] = {"Mes", "Cant. prof.", "Cant. alumnos", "Ingreso $", "Egreso $"};
		int sumaIngresos = 0;
		int sumaEgresos = 0;
		
		String cuerpo[][] = Estadisticas.getEstadisticasAnuales((String)comboBoxAño.getSelectedItem());
		DefaultTableModel tablaModelo = new DefaultTableModel(cuerpo, titulo);
		tabla.setModel(tablaModelo);
	
		for(int i = 0; i < 12; i++) {
			
			if(cuerpo[i][3] != null)
				sumaIngresos += Integer.parseInt(cuerpo[i][3]);
			if(cuerpo[i][4] != null)
				sumaEgresos += Integer.parseInt(cuerpo[i][4]);
		}

		txtIngresosTotales.setText(sumaIngresos + "");
		txtEgresosTotales.setText(sumaEgresos + "");
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		
		if (e.getSource() == comboBoxAño) {
			actualizarTabla();
		}
		
		
	}
}
