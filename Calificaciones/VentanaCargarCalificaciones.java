import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ListSelectionModel;

public class VentanaCargarCalificaciones extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tablaExamenes;
	private DefaultTableModel tablaModelo;
	private Object matriz[][];
	private JTextField txtDia;
	private JTextField txtMes;
	private JTextField txtAño;
	private JComboBox<String> comboBoxExamen = new JComboBox<String>();
	private JLabel lblMensageError = new JLabel("");


	public VentanaCargarCalificaciones(String idCurso) {
		
		setTitle("LECSys - Cargar calificaciones."+ CheckUsuario.getNombreUsuario());
		setIconImage(Toolkit.getDefaultToolkit().getImage(LECSys.rutaImagenes + "LEC.jpg"));
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 597, 499);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollTabla = new JScrollPane();
		scrollTabla.setBounds(1, 94, 444, 326);
		contentPane.add(scrollTabla);
		String titulo [] = {"Legajo", "Nombre", "Apellido", "Nota","Presente"};
		String respuesta[][] = ABMCAlumnos.getAlumnos("CURSO", idCurso, true, "idCurso");
		
		if(respuesta != null) {
			
			matriz = new Object[respuesta.length][5];
			
			for(int i = 0 ; i < respuesta.length ; i++) {
				
				matriz[i][0] = respuesta[i][0];
				matriz[i][1] = respuesta[i][1];
				matriz[i][2] = respuesta[i][2];
				matriz[i][3] = "";
				matriz[i][4] = true;
			}
		}
		tablaModelo = new DefaultTableModel(matriz, titulo) {
			
			private static final long serialVersionUID = 1L;
			public Class<?> getColumnClass(int column) {
		        if(column == 4)
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
		
		tablaExamenes = new JTable(tablaModelo);
		tablaExamenes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaExamenes.setRowSelectionAllowed(false);
		tablaExamenes.setCellSelectionEnabled(true);

		tablaExamenes.getColumnModel().getColumn(0).setPreferredWidth(50);
		tablaExamenes.getColumnModel().getColumn(0).setMinWidth(50);
		tablaExamenes.getColumnModel().getColumn(0).setMaxWidth(50);
		tablaExamenes.getColumnModel().getColumn(1).setPreferredWidth(100);
		tablaExamenes.getColumnModel().getColumn(1).setMaxWidth(150);
		tablaExamenes.getColumnModel().getColumn(2).setPreferredWidth(100);
		tablaExamenes.getColumnModel().getColumn(2).setMaxWidth(150);
		tablaExamenes.getColumnModel().getColumn(3).setPreferredWidth(60);
		tablaExamenes.getColumnModel().getColumn(3).setMaxWidth(50);
		tablaExamenes.getColumnModel().getColumn(4).setPreferredWidth(50);
		tablaExamenes.getColumnModel().getColumn(4).setMaxWidth(50);

		scrollTabla.setViewportView(tablaExamenes);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
			}
		});
		btnVolver.setBounds(475, 374, 90, 23);
		contentPane.add(btnVolver);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String nombrProfesor[] = ABMCCurso.getProfesor(idCurso);
				boolean bandera = true;
				
				if(chekCampos()) {
					
					String aGuardar[] = new String[6];
					for(int i=0 ; i < matriz.length ; i++) {
						
						aGuardar[0] = (String)matriz[i][0];
						aGuardar[1] = txtAño.getText() + txtMes.getText() + txtDia.getText();
						aGuardar[2] = (String)comboBoxExamen.getSelectedItem();
						aGuardar[3] = tablaExamenes.getValueAt(i,3)+"";
						aGuardar[4] = nombrProfesor[0];
						aGuardar[5] = respuesta[i][8];
						
						if(bandera) {
							
							if(!ABMCCalificaciones.guardarExamen(aGuardar))
								bandera = false;
						}
						else
							break;
					}

					if(bandera) {
						
						lblMensageError.setForeground(Color.BLUE);
						lblMensageError.setText("Se guardaron las notas.");
					} else {
						
						lblMensageError.setForeground(Color.RED);
						lblMensageError.setText("Error al intentar guardar las notas.");
					}
				}
			}
		});
		btnGuardar.setBounds(475, 49, 90, 23);
		contentPane.add(btnGuardar);
		
		JLabel lblFechaDelExamen = new JLabel("Fecha del examen:");
		lblFechaDelExamen.setBounds(10, 25, 100, 20);
		contentPane.add(lblFechaDelExamen);
		
		txtDia = new JTextField();
		txtDia.setHorizontalAlignment(SwingConstants.CENTER);
		txtDia.setBounds(120, 25, 25, 20);
		contentPane.add(txtDia);
		configurarJTextField(txtDia, 2);
		
		JLabel lblSeparador = new JLabel("/");
		lblSeparador.setHorizontalAlignment(SwingConstants.CENTER);
		lblSeparador.setBounds(145, 25, 10, 20);
		contentPane.add(lblSeparador);
		
		txtMes = new JTextField();
		txtMes.setHorizontalAlignment(SwingConstants.CENTER);
		txtMes.setBounds(155, 25, 25, 20);
		contentPane.add(txtMes);
		configurarJTextField(txtMes, 2);
		
		JLabel lblSeparador1 = new JLabel("/");
		lblSeparador1.setHorizontalAlignment(SwingConstants.CENTER);
		lblSeparador1.setBounds(180, 25, 10, 20);
		contentPane.add(lblSeparador1);
		
		txtAño = new JTextField();
		txtAño.setHorizontalAlignment(SwingConstants.CENTER);
		txtAño.setBounds(190, 25, 40, 20);
		contentPane.add(txtAño);
		configurarJTextField(txtAño, 4);
		
		JLabel lblformatoFecha = new JLabel("DD / MM / AAAA");
		lblformatoFecha.setBounds(235, 25, 88, 20);
		contentPane.add(lblformatoFecha);
		
		JLabel lblTipoDeExamen = new JLabel("Tipo de examen:");
		lblTipoDeExamen.setBounds(10, 50, 100, 20);
		contentPane.add(lblTipoDeExamen);
		
		String opcionesExamen[]= {"Seleccione el tipo de exámen", "Primer parcial", "Segundo parcial", "Tercer parcial", "Recuperatorio", "Examen final"};
		
		comboBoxExamen.setModel(new DefaultComboBoxModel<String>(opcionesExamen));
		comboBoxExamen.setBounds(120, 50, 160, 20);
		contentPane.add(comboBoxExamen);
		
		lblMensageError.setForeground(Color.RED);
		lblMensageError.setBackground(Color.LIGHT_GRAY);
		lblMensageError.setBounds(1, 431, 580, 25);
		contentPane.add(lblMensageError);
		
		JButton btnImprimir = new JButton("Imprimir");
		btnImprimir.setBounds(475, 97, 90, 23);
		contentPane.add(btnImprimir);
	}
	
	private boolean chekCampos() {
		
		boolean i = true;
		lblMensageError.setForeground(Color.RED);
		if(txtDia.getText().length() == 0 || 
				 Integer.parseInt(txtDia.getText()) < 1 ||
				 Integer.parseInt(txtDia.getText()) > 31) {
			
			i = false;
			lblMensageError.setText("Error en el formato del día.");
		}else if(txtMes.getText().length() == 0 || 
				 Integer.parseInt(txtMes.getText()) < 1 ||
				 Integer.parseInt(txtMes.getText()) > 12) {
			
			i = false;
			lblMensageError.setText("MError en el formato del mes.");
		}else if(txtAño.getText().length() == 0 ||
				Integer.parseInt(txtAño.getText()) < 1920) {
			
			i = false;
			lblMensageError.setText("Error en el formato del año.");
		} else if(comboBoxExamen.getSelectedIndex()==0) {
			
			i = false;
			lblMensageError.setText("Seleccione el tipo de exámen.");
		} else {
			
			boolean notas = true;
			int temp=0;
			for(int e = 0 ; e < tablaExamenes.getRowCount() ; e++) {
				
				if((boolean)tablaExamenes.getValueAt(e, 4)) {
					
					try {
						
						temp = Integer.parseInt((String) tablaExamenes.getValueAt(e, 3));
					} catch (NumberFormatException e2) {
						
						notas = false;
					}
				}
				 
				if((temp < 1 || temp > 100) && (boolean)tablaExamenes.getValueAt(e, 4))
					notas = false;
			}
			
			if(!notas) {
				
				i = false;
				lblMensageError.setText("La nota debe ser un número entre 0 y 100.");
			}
		}
		return i;
	}
	
	private void configurarJTextField(Component nombre, int cantidadCaracteres) {
		
		((JTextField) nombre).setColumns(cantidadCaracteres);
		nombre.addKeyListener(new KeyAdapter() {
			
			public void keyTyped(KeyEvent e)
			{
				if(((JTextField) nombre).getText().length() >= cantidadCaracteres)
					e.consume();
			}
		});
	}
}
