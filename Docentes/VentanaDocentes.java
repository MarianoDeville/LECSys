import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.print.PrinterException;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JCheckBox;

public class VentanaDocentes extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tablaProfesores;
	private String respuesta[][] = null;

	public VentanaDocentes() {
		
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(LECSys.rutaImagenes + "LEC.jpg"));
		setTitle("LECSys - Gestión de docentes."+ CheckUsuario.getNombreUsuario());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(10, 20, 1000, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollTabla = new JScrollPane();
		scrollTabla.setBounds(0, 30, 880, 517);
		contentPane.add(scrollTabla);
		tablaProfesores = new JTable();
		actualizarTabla(true);
		scrollTabla.setViewportView(tablaProfesores);
		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				dispose();
				
				try {
					
					VentanaCrearDocente frame = new VentanaCrearDocente(true);
					frame.setVisible(true);
				} catch (Exception d) {
					
					d.printStackTrace();
				}
			}
		});
		btnAgregar.setBounds(890, 35, 90, 23);
		contentPane.add(btnAgregar);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				for(int i = 0 ; i < tablaProfesores.getRowCount() ; i++) {
					
					if((boolean)tablaProfesores.getValueAt(i, 0)) {

						dispose();
						
						try {

							VentanaEditarDocente frame = new VentanaEditarDocente(respuesta[i][0]);
							frame.setVisible(true);
						} catch (Exception f) {
							
							f.printStackTrace();
						}
						break;
					}
				}
			}
		});
		btnEditar.setBounds(890, 70, 90, 23);
		contentPane.add(btnEditar);
		
		JButton btnImprimir = new JButton("Imprimir");
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					tablaProfesores.print();
				} catch (PrinterException d) {
					
					d.printStackTrace();
				}
			}
		});
		btnImprimir.setBounds(890, 105, 90, 23);
		contentPane.add(btnImprimir);
		
		JCheckBox chckbxActivos = new JCheckBox("Activo");
		chckbxActivos.setSelected(true);
		chckbxActivos.setEnabled(true);
		chckbxActivos.setBounds(890, 150, 90, 23);
		chckbxActivos.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
            	boolean estado = true;
            	if(e.getStateChange() == ItemEvent.SELECTED)
            		estado = true;
            	else
            		estado = false;
        		actualizarTabla(estado);
            }
        });
		contentPane.add(chckbxActivos);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
			}
		});
		btnVolver.setBounds(890, 520, 90, 23);
		contentPane.add(btnVolver);
	}
	
	public final void abrirVentana() {
		
		int nivelAcceso = CheckUsuario.getNivelNivelAcceso();
		
		if(nivelAcceso < 3) {
			
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					
					try {
						
						VentanaDocentes frame = new VentanaDocentes();
						frame.setVisible(true);
					} catch (Exception e) {
						
						e.printStackTrace();
					}
				}
			});
		} else {
			
			JOptionPane.showMessageDialog(null, "No tiene suficientes privilegios.");
		}
	}
	
	private void actualizarTabla(boolean estado) {
		
		String titulo [] = {"S", "Legajo", "Nombre", "Apellido", "DNI", "Dirección", "Teléfono", "e-mail"};
		respuesta = ABMCDocentes.getProfesores(estado);
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
		
		DefaultTableModel tablaModelo = new DefaultTableModel(cuerpo, titulo) {
			
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
		tablaProfesores.setModel(tablaModelo);
		tablaProfesores.setEnabled(true);
		tablaProfesores.getColumnModel().getColumn(0).setPreferredWidth(30);
		tablaProfesores.getColumnModel().getColumn(0).setMinWidth(20);
		tablaProfesores.getColumnModel().getColumn(0).setMaxWidth(50);
		tablaProfesores.getColumnModel().getColumn(1).setPreferredWidth(50);
		tablaProfesores.getColumnModel().getColumn(1).setMinWidth(30);
		tablaProfesores.getColumnModel().getColumn(1).setMaxWidth(150);
		tablaProfesores.getColumnModel().getColumn(2).setPreferredWidth(120);
		tablaProfesores.getColumnModel().getColumn(2).setMinWidth(30);
		tablaProfesores.getColumnModel().getColumn(2).setMaxWidth(150);
		tablaProfesores.getColumnModel().getColumn(3).setPreferredWidth(100);
		tablaProfesores.getColumnModel().getColumn(3).setMinWidth(60);
		tablaProfesores.getColumnModel().getColumn(3).setMaxWidth(150);
		tablaProfesores.getColumnModel().getColumn(4).setPreferredWidth(70);
		tablaProfesores.getColumnModel().getColumn(4).setMinWidth(60);
		tablaProfesores.getColumnModel().getColumn(4).setMaxWidth(150);
		tablaProfesores.getColumnModel().getColumn(5).setPreferredWidth(115);
		tablaProfesores.getColumnModel().getColumn(5).setMinWidth(90);
		tablaProfesores.getColumnModel().getColumn(5).setMaxWidth(150);
		tablaProfesores.getColumnModel().getColumn(6).setPreferredWidth(100);
		tablaProfesores.getColumnModel().getColumn(6).setMinWidth(90);
		tablaProfesores.getColumnModel().getColumn(6).setMaxWidth(150);
	}
}