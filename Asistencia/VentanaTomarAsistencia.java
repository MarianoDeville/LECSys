import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class VentanaTomarAsistencia extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tablaAsistencia;
	private DefaultTableModel tablaModelo;
	private Object matriz[][];
		
	public VentanaTomarAsistencia(String idCurso) {
		
		setTitle("LECSys - Tomar asistencia."+ CheckUsuario.getNombreUsuario());
		setIconImage(Toolkit.getDefaultToolkit().getImage(LECSys.rutaImagenes + "LEC.jpg"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(10, 20, 690, 470);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(572, 398, 90, 23);
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		contentPane.setLayout(null);
		contentPane.add(btnVolver);
		
		JScrollPane scrollTabla = new JScrollPane();
		scrollTabla.setBounds(1, 30, 560, 390);
		contentPane.add(scrollTabla);
		
		String titulo [] = {"Legajo", "Nombre", "Apellido", "Asistencia","Llegadas tarde"};
		String respuesta[][] = ABMCAlumnos.getAlumnos("CURSO", idCurso, true, "idCurso");
		matriz = new Object[respuesta.length][5];
		
		for(int i = 0 ; i < respuesta.length ; i++) {
			
			matriz[i][0] = respuesta[i][0];
			matriz[i][1] = respuesta[i][1];
			matriz[i][2] = respuesta[i][2];
			matriz[i][3] = true;
			matriz[i][4] = false;
		}

		tablaModelo = new DefaultTableModel(matriz, titulo){
			
			private static final long serialVersionUID = 1L;
			
			public Class<?> getColumnClass(int column) {
			        if(column == 3 || column == 4)
			        	return Boolean.class;
			        else
			        	return String.class;
		    }
			
			boolean[] columnEditables = new boolean[] {
					false, false, false, true, true
			};
			
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		tablaModelo.fireTableDataChanged();
		tablaAsistencia = new JTable(tablaModelo);
		tablaAsistencia.getColumnModel().getColumn(0).setPreferredWidth(50);
		tablaAsistencia.getColumnModel().getColumn(0).setMinWidth(50);
		tablaAsistencia.getColumnModel().getColumn(0).setMaxWidth(50);
		tablaAsistencia.getColumnModel().getColumn(1).setPreferredWidth(100);
		tablaAsistencia.getColumnModel().getColumn(1).setMaxWidth(150);
		tablaAsistencia.getColumnModel().getColumn(2).setPreferredWidth(60);
		tablaAsistencia.getColumnModel().getColumn(2).setMaxWidth(150);
		tablaAsistencia.getColumnModel().getColumn(3).setPreferredWidth(60);
		tablaAsistencia.getColumnModel().getColumn(3).setMaxWidth(150);
		tablaAsistencia.getColumnModel().getColumn(4).setPreferredWidth(60);
		tablaAsistencia.getColumnModel().getColumn(4).setMaxWidth(150);
		scrollTabla.setViewportView(tablaAsistencia);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				ABMCAsistencia asistencia = new ABMCAsistencia();
				boolean bandera = true;
				Calendar fechaSistema = new GregorianCalendar();
				String fechaActual = fechaSistema.get(Calendar.YEAR)+"" + "-" + 
									(fechaSistema.get(Calendar.MONTH)+1)+"" + "-" +
									fechaSistema.get(Calendar.DAY_OF_MONTH)+"";
				
				String aGuardar[] = new String[4];
				
				if(!asistencia.asistenciaRepetida(idCurso, fechaActual)) {
					
					for(int i=0 ; i < matriz.length ; i++)
					{
						aGuardar[0] = (String) matriz[i][0];
						aGuardar[1] = fechaActual;
						aGuardar[2] = idCurso;
						if((boolean) tablaAsistencia.getValueAt(i, 4))
							aGuardar[3] = "2";
						else if((boolean) tablaAsistencia.getValueAt(i, 3))
							aGuardar[3] = "1";
						else
							aGuardar[3] = "0";
						if(!asistencia.guardarDia(aGuardar))
							bandera = false;
					}
					if(bandera)
						JOptionPane.showMessageDialog(null, "Información guardada.");
					else
						JOptionPane.showMessageDialog(null, "Error al guardar la información.");
				} else {
					
					JOptionPane.showMessageDialog(null, "La asistencia ya ha sido tomada.");
				}
				dispose();
			}
		});
		btnGuardar.setBounds(572, 33, 89, 23);
		contentPane.add(btnGuardar);
	}

}
