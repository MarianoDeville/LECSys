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
	private JComboBox<String> comboBoxA�o;
	private Calendar fechaSistema = new GregorianCalendar();

	public VentanaActividad() {
		
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(LECSys.rutaImagenes + "LEC.jpg"));
		setTitle("LECSys - Configuraci�n."+ CheckUsuario.getNombreUsuario());
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

		JLabel lblA�o = new JLabel("A�o:");
		lblA�o.setBounds(190, 15, 30, 20);
		contentPane.add(lblA�o);
		
		int a�o = fechaSistema.get(Calendar.YEAR);
		String a�os[] = {a�o+"",(a�o-1)+"",(a�o-2)+"",(a�o-3)+"",(a�o-4)+""};
		comboBoxA�o = new JComboBox<String>();
		comboBoxA�o.setModel(new DefaultComboBoxModel<String>(a�os));
		comboBoxA�o.addItemListener(this);
		comboBoxA�o.setBounds(220, 15, 80, 20);
		contentPane.add(comboBoxA�o);
		
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
		
		if (e.getSource()==comboBoxMes || e.getSource()==comboBoxA�o)
			actualizoTabla();
	}
	
	private void actualizoTabla() {
		
		String titulo [] = {"Fecha", "Hora", "Usuario", "Actividad", "M�dulo", "IP"};
		String cuerpo[][] = ABMCActividad.getActividad(comboBoxMes.getSelectedIndex() + "", (String) comboBoxA�o.getSelectedItem());
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
