import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class VentanaHistorialAsistencia extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtCantidadTotal;
	private JTextField txtCantidadAusentes;
	private JTextField txtCantidadPresentes;
	private JTextField txtTarde;
	private int cantidadPresentes = 0;
	private int cantidadAusentes = 0;
	private int cantidadLlegadasTarde = 0;
	private JTable tablaFaltas;
	private ABMCAsistencia asistencia = new ABMCAsistencia();
	
	public VentanaHistorialAsistencia(String idAlumno) {
		
		setTitle("LECSys - Asistencia"+ CheckUsuario.getNombreUsuario());
		setIconImage(Toolkit.getDefaultToolkit().getImage(LECSys.rutaImagenes + "LEC.jpg"));
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(10, 20, 494, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollTabla = new JScrollPane();
		scrollTabla.setBounds(10, 106, 340, 354);
		contentPane.add(scrollTabla);

		String titulo[] = { " ", "Fecha", "Estado"};
		String matriz[][] = asistencia.getAsistencia(idAlumno);
		DefaultTableModel tablaModeloFaltas = new DefaultTableModel(matriz, titulo);
		
		tablaFaltas = new JTable(tablaModeloFaltas);
		tablaFaltas.setFillsViewportHeight(false);
		tablaFaltas.getColumnModel().getColumn(0).setPreferredWidth(20);
		tablaFaltas.getColumnModel().getColumn(0).setMinWidth(20);
		tablaFaltas.getColumnModel().getColumn(0).setMaxWidth(50);
		tablaFaltas.getColumnModel().getColumn(1).setPreferredWidth(50);
		tablaFaltas.getColumnModel().getColumn(1).setMinWidth(30);
		tablaFaltas.getColumnModel().getColumn(1).setMaxWidth(150);
		tablaFaltas.getColumnModel().getColumn(2).setPreferredWidth(50);
		tablaFaltas.getColumnModel().getColumn(2).setMinWidth(30);
		tablaFaltas.getColumnModel().getColumn(2).setMaxWidth(150);
		scrollTabla.setViewportView(tablaFaltas);
		
		for(int i = 0 ; i < matriz.length ; i++) {
			
			if(matriz[i][2].contentEquals("Ausente"))
				cantidadAusentes++;
			else if(matriz[i][2].contentEquals("Presente"))
				cantidadPresentes++;
			else
				cantidadLlegadasTarde++;
		}
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
			}
		});
		btnVolver.setBounds(375, 424, 89, 23);
		contentPane.add(btnVolver);
		
		JLabel lblCantidadDeClases = new JLabel("Cantidad de clases:");
		lblCantidadDeClases.setBounds(35, 25, 120, 20);
		contentPane.add(lblCantidadDeClases);
		
		JLabel lblAusentes = new JLabel("Ausente:");
		lblAusentes.setBounds(255, 50, 50, 20);
		contentPane.add(lblAusentes);
		
		JLabel lblPresentes = new JLabel("Presente:");
		lblPresentes.setBounds(255, 25, 50, 20);
		contentPane.add(lblPresentes);
		
		txtCantidadTotal = new JTextField();
		txtCantidadTotal.setBounds(155, 25, 50, 20);
		contentPane.add(txtCantidadTotal);
		txtCantidadTotal.setColumns(10);
		txtCantidadTotal.setText((cantidadAusentes+cantidadPresentes+cantidadLlegadasTarde)+"");
		
		txtCantidadAusentes = new JTextField();
		txtCantidadAusentes.setBounds(305, 50, 50, 20);
		contentPane.add(txtCantidadAusentes);
		txtCantidadAusentes.setColumns(10);
		txtCantidadAusentes.setText(cantidadAusentes+"");
		
		txtCantidadPresentes = new JTextField();
		txtCantidadPresentes.setBounds(305, 25, 50, 20);
		contentPane.add(txtCantidadPresentes);
		txtCantidadPresentes.setColumns(10);
		txtCantidadPresentes.setText(cantidadPresentes+"");
		
		txtTarde = new JTextField();
		txtTarde.setBounds(305, 75, 50, 20);
		contentPane.add(txtTarde);
		txtTarde.setColumns(10);
		txtTarde.setText(cantidadLlegadasTarde+"");
		
		JButton btnImprimir = new JButton("Imprimir");
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					tablaFaltas.print();
				} catch (PrinterException d) {
					
					d.printStackTrace();
				}
			}
		});
		btnImprimir.setBounds(375, 109, 89, 23);
		contentPane.add(btnImprimir);
		
		JLabel lblTarde = new JLabel("Tarde:");
		lblTarde.setBounds(255, 75, 50, 20);
		contentPane.add(lblTarde);
	}
}
