import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

public class VentanaListarAlumnos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Object matriz[][];
	private JTable tablaAlumnos;
	private DefaultTableModel tablaModelo;

	public VentanaListarAlumnos(String idCurso) {
		setTitle("LECSys - Lista de estudiantes."+ CheckUsuario.getNombreUsuario());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(LECSys.rutaImagenes + "LEC.jpg"));
		setBounds(10, 20, 690, 470);
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
		btnVolver.setBounds(572, 398, 90, 23);
		contentPane.add(btnVolver);
		
		JButton btnPrint = new JButton("Imprimir");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				try {
					tablaAlumnos.print();
				} catch (PrinterException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnPrint.setBounds(572, 81, 89, 23);
		contentPane.add(btnPrint);
		
		JScrollPane scrollTabla = new JScrollPane();
		scrollTabla.setBounds(1, 30, 560, 400);
		contentPane.add(scrollTabla);
		
		String titulo [] = {"Legajo", "Nombre", "Apellido", "Seleccionar"};
		String respAlunos[][] = ABMCAlumnos.getAlumnos("CURSO", idCurso, true, "idCurso");
		matriz = new Object[respAlunos.length][4];
		
		for(int i = 0 ; i < respAlunos.length ; i++) {
			
			matriz[i][0] = respAlunos[i][0];
			matriz[i][1] = respAlunos[i][1];
			matriz[i][2] = respAlunos[i][2];
			matriz[i][3] = false;
		}
						
		tablaModelo = new DefaultTableModel(matriz, titulo){
			
			private static final long serialVersionUID = 1L;
			public Class<?> getColumnClass(int column) {
				
			        if(column == 3) return Boolean.class;
			        else return String.class;
		    }
			
			boolean[] columnEditables = new boolean[] {
					false, false, false, true
			};
			
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		
		tablaModelo.fireTableDataChanged();
		tablaAlumnos = new JTable(tablaModelo);
		tablaAlumnos.getColumnModel().getColumn(0).setPreferredWidth(60);
		tablaAlumnos.getColumnModel().getColumn(0).setMinWidth(50);
		tablaAlumnos.getColumnModel().getColumn(0).setMaxWidth(50);
		tablaAlumnos.getColumnModel().getColumn(1).setPreferredWidth(250);
		tablaAlumnos.getColumnModel().getColumn(1).setMaxWidth(350);
		tablaAlumnos.getColumnModel().getColumn(2).setPreferredWidth(250);
		tablaAlumnos.getColumnModel().getColumn(2).setMaxWidth(350);
		scrollTabla.setViewportView(tablaAlumnos);
		
		JButton btnAsistencia = new JButton("Asistencia");
		btnAsistencia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				for(int i=0 ; i < matriz.length ; i++)
				{
					if((boolean) tablaAlumnos.getValueAt(i, 3)) {
						try {
							VentanaHistorialAsistencia frame = new VentanaHistorialAsistencia((String)matriz[i][0]);
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					}

				}
			}
		});
		btnAsistencia.setBounds(573, 33, 89, 23);
		contentPane.add(btnAsistencia);
	}
}
