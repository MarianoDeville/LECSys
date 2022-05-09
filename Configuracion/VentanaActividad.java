import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.print.PrinterException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class VentanaActividad extends JFrame implements ItemListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tablaActividad;
	private JComboBox<String> comboBoxMes;
	private JComboBox<String> comboBoxAño;
	private Calendar fechaSistema = new GregorianCalendar();

	public VentanaActividad() {
		
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(LECSys.rutaImagenes + "LEC.jpg"));
		setTitle("LECSys - Configuración."+ CheckUsuario.getNombreUsuario());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(10, 10, 900, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
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
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 45, 760, 500);
		contentPane.add(scrollPane);

		tablaActividad = new JTable();
		actualizoTabla();
		scrollPane.setViewportView(tablaActividad);
		
		JButton btnImprimir = new JButton("Imprimir");
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					tablaActividad.print();
				} catch (PrinterException e1) {
					
					e1.printStackTrace();
				}
			}
		});
		btnImprimir.setBounds(780, 45, 90, 25);
		contentPane.add(btnImprimir);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
			}
		});
		btnVolver.setBounds(780, 520, 90, 25);
		contentPane.add(btnVolver);
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		
		if (e.getSource()==comboBoxMes || e.getSource()==comboBoxAño)
			actualizoTabla();
	}
	
	private void actualizoTabla() {
		
		String titulo [] = {"Fecha", "Hora", "Usuario", "Actividad", "Módulo", "IP"};
		String cuerpo[][] = ABMCActividad.getActividad(comboBoxMes.getSelectedIndex() + "", (String) comboBoxAño.getSelectedItem());
		DefaultTableModel tablaModelo = new DefaultTableModel(cuerpo, titulo);
		tablaActividad.setModel(tablaModelo);
		tablaActividad.setEnabled(false);
		tablaActividad.getColumnModel().getColumn(0).setPreferredWidth(65);
		tablaActividad.getColumnModel().getColumn(0).setMinWidth(50);
		tablaActividad.getColumnModel().getColumn(0).setMaxWidth(500);
		tablaActividad.getColumnModel().getColumn(1).setPreferredWidth(45);
		tablaActividad.getColumnModel().getColumn(1).setMinWidth(20);
		tablaActividad.getColumnModel().getColumn(1).setMaxWidth(500);
		tablaActividad.getColumnModel().getColumn(2).setPreferredWidth(80);
		tablaActividad.getColumnModel().getColumn(2).setMinWidth(50);
		tablaActividad.getColumnModel().getColumn(2).setMaxWidth(500);
		tablaActividad.getColumnModel().getColumn(3).setPreferredWidth(380);
		tablaActividad.getColumnModel().getColumn(3).setMinWidth(50);
		tablaActividad.getColumnModel().getColumn(3).setMaxWidth(500);
		tablaActividad.getColumnModel().getColumn(4).setPreferredWidth(60);
		tablaActividad.getColumnModel().getColumn(4).setMinWidth(50);
		tablaActividad.getColumnModel().getColumn(4).setMaxWidth(500);
		tablaActividad.getColumnModel().getColumn(5).setPreferredWidth(80);
		tablaActividad.getColumnModel().getColumn(5).setMinWidth(50);
		tablaActividad.getColumnModel().getColumn(5).setMaxWidth(500);
	}
}
