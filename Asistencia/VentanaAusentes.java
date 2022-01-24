import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.print.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaAusentes extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tablaAusentes;
	private DefaultTableModel tablaModelo;
	private String matriz[][];
	
	public VentanaAusentes(String idCurso) {
		
		setResizable(false);
		setTitle("LECSys - Lista de ausentes."+ CheckUsuario.getNombreUsuario());
		setIconImage(Toolkit.getDefaultToolkit().getImage(LECSys.rutaImagenes + "LEC.jpg"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(10, 20, 690, 470);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollTabla = new JScrollPane();
		scrollTabla.setBounds(1, 30, 560, 400);
		contentPane.add(scrollTabla);
		
		String titulo [] = {"Legajo", "Nombre", "Apellido"};

		ABMCAsistencia ausente = new ABMCAsistencia();
		String respAusentes[][] = ausente.getAusentes("CURSO", idCurso);
		matriz = new String[respAusentes.length][4];
		String respAlunos[] = new String[13];
		
		for(int i = 0 ; i < respAusentes.length ; i++) {
			
			respAlunos = ABMCAlumnos.buscarAlumno("", respAusentes[i][1]);
			matriz[i][0] = respAusentes[i][0];
			matriz[i][1] = respAlunos[1];
			matriz[i][2] = respAlunos[2];
		}
						
		tablaModelo = new DefaultTableModel(matriz, titulo);
		tablaModelo.fireTableDataChanged();
		tablaAusentes = new JTable(tablaModelo);
		tablaAusentes.getColumnModel().getColumn(0).setPreferredWidth(60);
		tablaAusentes.getColumnModel().getColumn(0).setMinWidth(50);
		tablaAusentes.getColumnModel().getColumn(0).setMaxWidth(50);
		tablaAusentes.getColumnModel().getColumn(1).setPreferredWidth(250);
		tablaAusentes.getColumnModel().getColumn(1).setMaxWidth(350);
		tablaAusentes.getColumnModel().getColumn(2).setPreferredWidth(250);
		tablaAusentes.getColumnModel().getColumn(2).setMaxWidth(350);
		scrollTabla.setViewportView(tablaAusentes);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
			}
		});
		btnVolver.setBounds(572, 398, 90, 23);
		contentPane.add(btnVolver);
		
		JButton btnPrint = new JButton("Imprimir");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				try {
					
					tablaAusentes.print();
				} catch (PrinterException e1) {
					
					e1.printStackTrace();
				}
			}
		});
		btnPrint.setBounds(573, 335, 89, 23);
		contentPane.add(btnPrint);
	}
}
