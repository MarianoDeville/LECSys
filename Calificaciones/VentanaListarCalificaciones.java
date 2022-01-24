import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class VentanaListarCalificaciones extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tablaCalificaciones;

	public VentanaListarCalificaciones(String idCurso) {
		setTitle("LECSys - Lista de calificaciones."+ CheckUsuario.getNombreUsuario());
		setIconImage(Toolkit.getDefaultToolkit().getImage(LECSys.rutaImagenes + "LEC.jpg"));
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 680, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
			}
		});
		btnVolver.setBounds(564, 425, 89, 23);
		contentPane.add(btnVolver);
		
		JButton btnImprimir = new JButton("Imprimir");
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					
					tablaCalificaciones.print();
				} catch (PrinterException e) {
					
					JOptionPane.showMessageDialog(null, "Error al intentar imprimir.");
				}
			}
		});
		btnImprimir.setBounds(564, 74, 89, 23);
		contentPane.add(btnImprimir);
		
		JScrollPane scrollTabla = new JScrollPane();
		scrollTabla.setBounds(0, 0, 554, 460);
		contentPane.add(scrollTabla);
		
		tablaCalificaciones = new JTable();
		DefaultTableModel tablaModelo;
		String titulo [] = {"Legajo", "Nombre", "Fecha", "Calificación", "Profesor", "Curso"};
		
		tablaModelo = new DefaultTableModel(ABMCCalificaciones.getExamenes(idCurso), titulo);		
		tablaCalificaciones.setModel(tablaModelo);
		tablaCalificaciones.setEnabled(false);
		tablaCalificaciones.setBounds(0, 0, 439, 424);
		tablaCalificaciones.getColumnModel().getColumn(0).setPreferredWidth(30);
		tablaCalificaciones.getColumnModel().getColumn(0).setMinWidth(30);
		tablaCalificaciones.getColumnModel().getColumn(0).setMaxWidth(200);
		tablaCalificaciones.getColumnModel().getColumn(1).setPreferredWidth(170);
		tablaCalificaciones.getColumnModel().getColumn(1).setMinWidth(30);
		tablaCalificaciones.getColumnModel().getColumn(2).setPreferredWidth(40);
		tablaCalificaciones.getColumnModel().getColumn(2).setMinWidth(30);
		tablaCalificaciones.getColumnModel().getColumn(3).setPreferredWidth(30);
		tablaCalificaciones.getColumnModel().getColumn(3).setMinWidth(30);
		tablaCalificaciones.getColumnModel().getColumn(4).setPreferredWidth(40);
		tablaCalificaciones.getColumnModel().getColumn(4).setMinWidth(30);
		tablaCalificaciones.getColumnModel().getColumn(5).setPreferredWidth(40);
		tablaCalificaciones.getColumnModel().getColumn(5).setMinWidth(30);
		scrollTabla.setViewportView(tablaCalificaciones);
	}
}
