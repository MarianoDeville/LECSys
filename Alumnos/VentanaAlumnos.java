import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.print.PrinterException;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class VentanaAlumnos extends JFrame implements ItemListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tablaAlumnos;
	private JComboBox<String> comboBoxOrden;
	private JCheckBox chckbxActivos;
	private String respuesta[][];
		
	public VentanaAlumnos() {
		
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(LECSys.rutaImagenes + "LEC.jpg"));
		setTitle("LECSys - Alumnos."+ CheckUsuario.getNombreUsuario());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(10, 20, 1000, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				dispose();
			}
		});
		btnVolver.setBounds(890, 520, 90, 23);
		contentPane.add(btnVolver);
		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
				
				try {
					
					VentanaCrearAlumno frame = new VentanaCrearAlumno();
					frame.setVisible(true);
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}
		});
		btnAgregar.setBounds(890, 35, 90, 23);
		contentPane.add(btnAgregar);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
				
				try {
					
					VentanaBusquedaAlumno dialog = new VentanaBusquedaAlumno();
					dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}
		});
		btnBuscar.setBounds(890, 70, 90, 23);
		contentPane.add(btnBuscar);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				for(int i = 0 ; i < tablaAlumnos.getRowCount() ; i++) {
					
					if((boolean)tablaAlumnos.getValueAt(i, 0)) {

						dispose();
						
						try {

							VentanaEditarAlumno frame = new VentanaEditarAlumno(respuesta[i][0]);
							frame.setVisible(true);
						} catch (Exception e) {
							
							e.printStackTrace();
						}
						break;
					}
				}
			}
		});
		btnEditar.setBounds(890, 105, 90, 23);
		contentPane.add(btnEditar);
		
		comboBoxOrden = new JComboBox<String>();
		comboBoxOrden.setModel(new DefaultComboBoxModel<String>(new String[] {"Ordenar por", "Legajo", "Apellido", "Nombre", "DNI", "Dirección", "Fecha ingreso"}));
		comboBoxOrden.setSelectedIndex(0);
		comboBoxOrden.setBounds(890, 210, 90, 20);
		comboBoxOrden.addItemListener(this);
		contentPane.add(comboBoxOrden);
		
		JScrollPane scrollTabla = new JScrollPane();
		scrollTabla.setBounds(1, 30, 880, 517);
		contentPane.add(scrollTabla);
		tablaAlumnos = new JTable();
		actualizarTabla(true);
		scrollTabla.setViewportView(tablaAlumnos);
		
		JButton bntImprimir = new JButton("Imprimir");
		bntImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					
					tablaAlumnos.print();
				} catch (PrinterException e) {
					
					e.printStackTrace();
				}
			}
		});
		bntImprimir.setBounds(890, 140, 90, 23);
		contentPane.add(bntImprimir);
		
		chckbxActivos = new JCheckBox("Activo");
		chckbxActivos.setSelected(true);
		chckbxActivos.setEnabled(true);
		chckbxActivos.setBounds(890, 175, 90, 23);
		chckbxActivos.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
            	
            	boolean estado=true;
            	if(e.getStateChange() == ItemEvent.SELECTED)
            		estado = true;
            	else
            		estado = false;
            	actualizarTabla(estado);
            }
        });
		contentPane.add(chckbxActivos);
	}

	private void actualizarTabla(boolean estado){
		
		String titulo [] = {"S","Legajo", "Nombre", "Apellido", "DNI", "Dirección", "Teléfono", "e-mail"};
		String ordenado[] = {"idAlumno","idAlumno", "apellido", "nombre", "dni", "dirección", "fechaIngreso"};
		respuesta = ABMCAlumnos.getAlumnos("ID", "", estado, ordenado[comboBoxOrden.getSelectedIndex()]);
		Object cuerpo[][];
		
		if(respuesta != null) {
			
			cuerpo = new Object[respuesta.length][8];
			
			for(int i = 0 ; i < respuesta.length ; i++) {
				
				cuerpo[i][0] = false;
				cuerpo[i][1] = respuesta[i][0];
				cuerpo[i][2] = respuesta[i][1];
				cuerpo[i][3] = respuesta[i][2];
				cuerpo[i][4] = respuesta[i][3];
				cuerpo[i][5] = respuesta[i][4];
				cuerpo[i][6] = respuesta[i][5];
				cuerpo[i][7] = respuesta[i][6];
			}
		} else
			cuerpo = null;	
			
		DefaultTableModel tablaModelo = new DefaultTableModel(cuerpo, titulo){

			private static final long serialVersionUID = 1L;
			
			boolean[] columnEditables = new boolean[] {
					true, false, false, false, false, false, false, false
			};
			
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
			
			public Class<?> getColumnClass(int column) {
				
		        if(column == 0)
		        	return Boolean.class;
		        else
		        	return String.class;
		    }
		};
		tablaAlumnos.setModel(tablaModelo);
		tablaAlumnos.setEnabled(true);
		tablaAlumnos.getColumnModel().getColumn(0).setPreferredWidth(30);
		tablaAlumnos.getColumnModel().getColumn(0).setMinWidth(20);
		tablaAlumnos.getColumnModel().getColumn(0).setMaxWidth(50);
		tablaAlumnos.getColumnModel().getColumn(1).setPreferredWidth(50);
		tablaAlumnos.getColumnModel().getColumn(1).setMinWidth(30);
		tablaAlumnos.getColumnModel().getColumn(1).setMaxWidth(150);
		tablaAlumnos.getColumnModel().getColumn(2).setPreferredWidth(120);
		tablaAlumnos.getColumnModel().getColumn(2).setMinWidth(30);
		tablaAlumnos.getColumnModel().getColumn(2).setMaxWidth(150);
		tablaAlumnos.getColumnModel().getColumn(3).setPreferredWidth(100);
		tablaAlumnos.getColumnModel().getColumn(3).setMinWidth(60);
		tablaAlumnos.getColumnModel().getColumn(3).setMaxWidth(150);
		tablaAlumnos.getColumnModel().getColumn(4).setPreferredWidth(70);
		tablaAlumnos.getColumnModel().getColumn(4).setMinWidth(60);
		tablaAlumnos.getColumnModel().getColumn(4).setMaxWidth(150);
		tablaAlumnos.getColumnModel().getColumn(5).setPreferredWidth(115);
		tablaAlumnos.getColumnModel().getColumn(5).setMinWidth(90);
		tablaAlumnos.getColumnModel().getColumn(5).setMaxWidth(150);
		tablaAlumnos.getColumnModel().getColumn(6).setPreferredWidth(100);
		tablaAlumnos.getColumnModel().getColumn(6).setMinWidth(90);
		tablaAlumnos.getColumnModel().getColumn(6).setMaxWidth(150);
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		
		if (e.getSource()==comboBoxOrden)
			actualizarTabla(chckbxActivos.isSelected());
	}
}