import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;

public class VentanaCursos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tablaCursos;
	private Object cuerpo[][];
	private String respuesta[][];

	public VentanaCursos() {

		setTitle("LECSys - Cursos."+ CheckUsuario.getNombreUsuario());
		setIconImage(Toolkit.getDefaultToolkit().getImage(LECSys.rutaImagenes + "LEC.jpg"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(10, 20, 760, 480);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.controlHighlight);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);

		JScrollPane scrollTabla = new JScrollPane();
		scrollTabla.setBounds(5, 30, 620, 410);
		contentPane.add(scrollTabla);
		
		String titulo[] = {"Sel.", "Año", "Nivel", "Turno", "Profesor", "Días", "Cuota"};
		respuesta = ABMCCurso.getListaCursos(true);
		
		if(respuesta != null) {
			
			cuerpo = new Object[respuesta.length][7];
			
			for(int i = 0; i < respuesta.length; i++) {
				
				cuerpo[i][0] = false;
				cuerpo[i][1] = respuesta[i][1];
				cuerpo[i][2] = respuesta[i][2];
				cuerpo[i][3] = respuesta[i][7];
				cuerpo[i][4] = respuesta[i][4];
				cuerpo[i][5] = respuesta[i][5];
				cuerpo[i][6] = "$ " + respuesta[i][6];
			}
		} else
			cuerpo = null;
		
		DefaultTableModel tablaModelo = new DefaultTableModel(cuerpo, titulo) {
			
			boolean[] columnEditables = new boolean[] {
				true, false, false, false, false, false, false
			};
			
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
			
			private static final long serialVersionUID = 1L;
			public Class<?> getColumnClass(int column) {
				
		        if(column == 0)
		        	return Boolean.class;
		        else
		        	return String.class;
		    }
		};
		tablaCursos = new JTable(tablaModelo);
		tablaCursos.getColumnModel().getColumn(0).setPreferredWidth(30);
		tablaCursos.getColumnModel().getColumn(0).setMinWidth(30);
		tablaCursos.getColumnModel().getColumn(0).setMaxWidth(100);
		tablaCursos.getColumnModel().getColumn(1).setPreferredWidth(50);
		tablaCursos.getColumnModel().getColumn(1).setMinWidth(30);
		tablaCursos.getColumnModel().getColumn(1).setMaxWidth(100);
		tablaCursos.getColumnModel().getColumn(2).setPreferredWidth(80);
		tablaCursos.getColumnModel().getColumn(2).setMinWidth(50);
		tablaCursos.getColumnModel().getColumn(2).setMaxWidth(250);
		tablaCursos.getColumnModel().getColumn(3).setPreferredWidth(80);
		tablaCursos.getColumnModel().getColumn(3).setMinWidth(50);
		tablaCursos.getColumnModel().getColumn(3).setMaxWidth(250);
		tablaCursos.getColumnModel().getColumn(4).setPreferredWidth(180);
		tablaCursos.getColumnModel().getColumn(4).setMinWidth(100);
		tablaCursos.getColumnModel().getColumn(4).setMaxWidth(250);
		tablaCursos.getColumnModel().getColumn(5).setPreferredWidth(250);
		tablaCursos.getColumnModel().getColumn(5).setMinWidth(100);
		tablaCursos.getColumnModel().getColumn(5).setMaxWidth(250);
		tablaCursos.getColumnModel().getColumn(6).setPreferredWidth(100);
		tablaCursos.getColumnModel().getColumn(6).setMinWidth(50);
		tablaCursos.getColumnModel().getColumn(6).setMaxWidth(250);
		scrollTabla.setViewportView(tablaCursos);
		
		JButton btnCrear = new JButton("Agregar");
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
				
				try {
					
					VentanaCrearCurso frame = new VentanaCrearCurso();
					frame.setVisible(true);
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}
		});
		btnCrear.setBounds(640, 30, 90, 23);
		contentPane.add(btnCrear);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				for(int i = 0 ; i < tablaCursos.getRowCount() ; i++) {

					if((boolean)tablaCursos.getValueAt(i, 0)) {
					
						dispose();
						
						try {
							
							VentanaEditarCurso frame = new VentanaEditarCurso(respuesta[i]);
							frame.setVisible(true);
						} catch (Exception e) {
							
							e.printStackTrace();
						}
						break;
					}
				}
			}
		});
		btnEditar.setBounds(640, 65, 90, 23);
		contentPane.add(btnEditar);
		
		JButton btnImprimir = new JButton("Imprimir");
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					
					tablaCursos.print();
				} catch (PrinterException e) {
					
					e.printStackTrace();
				}
			}
		});
		btnImprimir.setBounds(640, 100, 90, 23);
		contentPane.add(btnImprimir);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
			}
		});
		btnVolver.setBounds(640, 400, 90, 23);
		contentPane.add(btnVolver);
	}
}
