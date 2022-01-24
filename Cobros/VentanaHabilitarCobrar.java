import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.print.PrinterException;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;

public class VentanaHabilitarCobrar extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tablaAlumnos;
	private Object cuerpo[][];
	private String respuesta[][];
	private JLabel lblMensageError;
	private JButton btnCrearGrupo;
	private JCheckBox chckbxTodos;
	private JCheckBox chckbxReinscripcion;

	public VentanaHabilitarCobrar() {
		
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(LECSys.rutaImagenes + "Images\\LEC.jpg"));
		setTitle("LECSys - Cobrar y habilitar cursado."+ CheckUsuario.getNombreUsuario());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 911, 495);
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
		btnVolver.setBounds(770, 415, 120, 23);
		contentPane.add(btnVolver);
		
		btnCrearGrupo = new JButton("Crear grupo");
		btnCrearGrupo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(itemsSeleccionados() != null) {
					
					try {
						
						VentanaGrupoFamiliar frame = new VentanaGrupoFamiliar(itemsSeleccionados());
						frame.setVisible(true);
					} catch (Exception e) {
						
						e.printStackTrace();
					}
					dispose();
				} else {
					
					lblMensageError.setText("Debe seleccionar por lo menos un item.");
				}
			}
		});
		btnCrearGrupo.setBounds(770, 70, 120, 23);
		contentPane.add(btnCrearGrupo);
		
		JButton btnCobrar = new JButton("Cobrar");
		btnCobrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(itemsSeleccionados() != null) {
					
					try {
						
						VentanaCuotaIndividual frame = new VentanaCuotaIndividual(itemsSeleccionados());
						frame.setVisible(true);
					} catch (Exception e1) {
						
						e1.printStackTrace();
					}
					dispose();
				} else {
					
					lblMensageError.setText("Debe seleccionar un item.");
				}
			}
		});
		btnCobrar.setBounds(770, 35, 120, 23);
		contentPane.add(btnCobrar);
		
		JButton btnImprimir = new JButton("Imprimir");
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					tablaAlumnos.print();
				} catch (PrinterException d) {
					
					d.printStackTrace();
				}
			}
		});
		btnImprimir.setBounds(770, 105, 120, 23);
		contentPane.add(btnImprimir);
		
		lblMensageError = new JLabel("");
		lblMensageError.setBounds(25, 5, 670, 20);
		lblMensageError.setForeground(Color.RED);
		lblMensageError.setBackground(Color.LIGHT_GRAY);
		contentPane.add(lblMensageError);
		
		chckbxTodos = new JCheckBox("Todos");
		chckbxTodos.setBounds(770, 175, 120, 23);
		chckbxTodos.setEnabled(true);
		chckbxTodos.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
            	
            	actualizarTabla();
            }
        });
		contentPane.add(chckbxTodos);
		
		chckbxReinscripcion = new JCheckBox("Reinscripción");
		chckbxReinscripcion.setBounds(770, 140, 120, 23);
		chckbxReinscripcion.setEnabled(true);
		chckbxReinscripcion.addItemListener(new ItemListener() {

	        @Override
	        public void itemStateChanged(ItemEvent e) {
	        	
	        	actualizarTabla();
	        }
	    });
		contentPane.add(chckbxReinscripcion);
		
		JScrollPane scrollTabla = new JScrollPane();
		scrollTabla.setBounds(1, 30, 760, 410);
		contentPane.add(scrollTabla);
		tablaAlumnos = new JTable();
		actualizarTabla();
		scrollTabla.setViewportView(tablaAlumnos);
	}
	
	private String[][] itemsSeleccionados() {
		
		int cantidadElementos=0;
		
		try {
		
			for(int i = 0; i < cuerpo.length ; i++) {
				
				if((boolean) tablaAlumnos.getValueAt(i, 5))
					cantidadElementos++;
			}
		} catch (NullPointerException e) {
			
			return null;
		}
				
		if(cantidadElementos == 0)
			return null;

		String listaSeleccionados[][] = new String[cantidadElementos][10];
		int e = 0;
		
		for(int i = 0; i < respuesta.length; i++) {

			if((boolean) tablaAlumnos.getValueAt(i, 5)) {
				
				listaSeleccionados[e][0] = respuesta[i][0];
				listaSeleccionados[e][1] = respuesta[i][1];
				listaSeleccionados[e][2] = respuesta[i][2];
				listaSeleccionados[e][3] = respuesta[i][3];
				listaSeleccionados[e][4] = ABMCCurso.getNombreCurso(respuesta[i][8]);
				listaSeleccionados[e][5] = ABMCValorCuota.buscarValorCuota(respuesta[i][8]);
				e++;
			}
		}
		return listaSeleccionados;
	}
	
	private void actualizarTabla() {

		boolean estado = chckbxTodos.isSelected();
		boolean inscripción = chckbxReinscripcion.isSelected();
	
		String titulo [] = {"Legajo", "Nombre", "Apellido", "DNI", "Dirección", "Seleccionar"};
		
		if(inscripción) {
			
			respuesta = ABMCAlumnos.getAlumnos("ESTADOFAMILIA", "0", false, "idAlumno");
			btnCrearGrupo.setEnabled(false);
		} else {
			
			respuesta = ABMCAlumnos.getAlumnos("GRUPOFAMILIAR", "0", false, "idAlumno");
			btnCrearGrupo.setEnabled(true);
		}
		
		if(respuesta != null) {
			
			cuerpo = new Object[respuesta.length][6];
			
			for(int i = 0 ; i < respuesta.length ; i++) {
				
				cuerpo[i][0] = respuesta[i][0];
				cuerpo[i][1] = respuesta[i][1];
				cuerpo[i][2] = respuesta[i][2];
				cuerpo[i][3] = respuesta[i][3];
				cuerpo[i][4] = respuesta[i][4];
				cuerpo[i][5] = false;
			}
		} else
			cuerpo = null;
		
		DefaultTableModel tablaModelo = new DefaultTableModel(cuerpo, titulo) {
			
			private static final long serialVersionUID = 1L;
			public Class<?> getColumnClass(int column) {
				
			        if(column == 5)
			        	return Boolean.class;
			        else
			        	return String.class;
		    }
			
			boolean[] columnEditables = new boolean[] {
					false, false, false, false, false, true
			};
			
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		tablaAlumnos.setModel(tablaModelo);
		tablaAlumnos.getColumnModel().getColumn(0).setPreferredWidth(50);
		tablaAlumnos.getColumnModel().getColumn(0).setMinWidth(30);
		tablaAlumnos.getColumnModel().getColumn(0).setMaxWidth(50);
		tablaAlumnos.getColumnModel().getColumn(1).setPreferredWidth(140);
		tablaAlumnos.getColumnModel().getColumn(1).setMaxWidth(180);
		tablaAlumnos.getColumnModel().getColumn(2).setPreferredWidth(140);
		tablaAlumnos.getColumnModel().getColumn(2).setMaxWidth(180);
		tablaAlumnos.getColumnModel().getColumn(3).setPreferredWidth(160);
		tablaAlumnos.getColumnModel().getColumn(3).setMinWidth(60);
		tablaAlumnos.getColumnModel().getColumn(3).setMaxWidth(300);
		tablaAlumnos.getColumnModel().getColumn(4).setPreferredWidth(160);
		tablaAlumnos.getColumnModel().getColumn(4).setMinWidth(50);
		tablaAlumnos.getColumnModel().getColumn(4).setMaxWidth(300);
		tablaAlumnos.getColumnModel().getColumn(5).setPreferredWidth(70);
		tablaAlumnos.getColumnModel().getColumn(5).setMinWidth(50);
		tablaAlumnos.getColumnModel().getColumn(5).setMaxWidth(70);
		
		if(cuerpo != null) {
			
			for(int i = 0 ; i < cuerpo.length ; i++) {
				
				tablaAlumnos.setValueAt(estado, i, 5);;
			}
		}
	}
}