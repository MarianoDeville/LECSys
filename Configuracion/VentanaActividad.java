import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class VentanaActividad extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tablaActividad;

	public VentanaActividad() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(LECSys.rutaImagenes + "LEC.jpg"));
		setTitle("LECSys - Configuración."+ CheckUsuario.getNombreUsuario());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(10, 10, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
			}
		});
		btnVolver.setBounds(680, 520, 90, 25);
		contentPane.add(btnVolver);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 24, 660, 520);
		contentPane.add(scrollPane);
		
		tablaActividad = new JTable();
		String titulo [] = {"Fecha", "Hora", "Usuario", "Actividad", "Módulo", "IP"};
		String cuerpo[][] = ABMCActividad.getActividad();
		DefaultTableModel tablaModelo = new DefaultTableModel(cuerpo, titulo);
		tablaActividad.setModel(tablaModelo);
		tablaActividad.setEnabled(false);
		tablaActividad.getColumnModel().getColumn(0).setPreferredWidth(80);
		tablaActividad.getColumnModel().getColumn(0).setMinWidth(50);
		tablaActividad.getColumnModel().getColumn(0).setMaxWidth(500);
		tablaActividad.getColumnModel().getColumn(1).setPreferredWidth(60);
		tablaActividad.getColumnModel().getColumn(1).setMinWidth(50);
		tablaActividad.getColumnModel().getColumn(1).setMaxWidth(500);
		tablaActividad.getColumnModel().getColumn(2).setPreferredWidth(100);
		tablaActividad.getColumnModel().getColumn(2).setMinWidth(50);
		tablaActividad.getColumnModel().getColumn(2).setMaxWidth(500);
		tablaActividad.getColumnModel().getColumn(3).setPreferredWidth(260);
		tablaActividad.getColumnModel().getColumn(3).setMinWidth(50);
		tablaActividad.getColumnModel().getColumn(3).setMaxWidth(500);
		tablaActividad.getColumnModel().getColumn(4).setPreferredWidth(80);
		tablaActividad.getColumnModel().getColumn(4).setMinWidth(50);
		tablaActividad.getColumnModel().getColumn(4).setMaxWidth(500);
		tablaActividad.getColumnModel().getColumn(5).setPreferredWidth(80);
		tablaActividad.getColumnModel().getColumn(5).setMinWidth(50);
		tablaActividad.getColumnModel().getColumn(5).setMaxWidth(500);
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
		btnImprimir.setBounds(680, 27, 90, 25);
		contentPane.add(btnImprimir);
	}
}
