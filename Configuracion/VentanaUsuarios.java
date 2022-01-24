import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.SystemColor;

public class VentanaUsuarios extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tablaUsuarios;
	private DefaultTableModel tablaModelo;
	private JButton btnAdd;
	private JButton btnEdit;
	private Object matriz[][];
	private JLabel lblMensageError;

	public VentanaUsuarios() {
		
		setTitle("LECSys - Usuarios."+ CheckUsuario.getNombreUsuario());
		setIconImage(Toolkit.getDefaultToolkit().getImage(LECSys.rutaImagenes + "LEC.jpg"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(10, 20, 580, 480);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.controlHighlight);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
			}
		});
		btnVolver.setBounds(465, 405, 90, 23);
		contentPane.add(btnVolver);
		
		JScrollPane scrollTabla = new JScrollPane();
		scrollTabla.setBounds(0, 30, 445, 410);
		contentPane.add(scrollTabla);
		String titulo [] = {"Nombre de usuario", "Nivel de acceso", "Seleccionar"};
		ABMCConfiguracion obtenerDatos = new ABMCConfiguracion();
		String respuesta[][] =  obtenerDatos.getUsuarios();

		if(respuesta != null) {
			
			matriz = new Object[respuesta.length][3];

			for(int i = 0 ; i < respuesta.length ; i++) {
				
				matriz[i][0] = respuesta[i][0];
				matriz[i][1] = respuesta[i][1];
				matriz[i][2] = false;
			}
		}
		
		tablaModelo = new DefaultTableModel(matriz, titulo) {
			
			private static final long serialVersionUID = 1L;
			public Class<?> getColumnClass(int column) {
			        if(column == 2) return Boolean.class;
			        else return String.class;
		    }
			
			boolean[] columnEditables = new boolean[] {
					false, false, true
			};
				
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
				
		tablaUsuarios = new JTable(tablaModelo);
		tablaUsuarios.getColumnModel().getColumn(0).setPreferredWidth(100);
		tablaUsuarios.getColumnModel().getColumn(0).setMinWidth(50);
		tablaUsuarios.getColumnModel().getColumn(0).setMaxWidth(200);
		tablaUsuarios.getColumnModel().getColumn(1).setPreferredWidth(80);
		tablaUsuarios.getColumnModel().getColumn(1).setMinWidth(50);
		tablaUsuarios.getColumnModel().getColumn(1).setMaxWidth(150);
		tablaUsuarios.getColumnModel().getColumn(2).setPreferredWidth(20);
		tablaUsuarios.getColumnModel().getColumn(2).setMinWidth(30);
		tablaUsuarios.getColumnModel().getColumn(2).setMaxWidth(100);
		scrollTabla.setViewportView(tablaUsuarios);
		
		btnAdd = new JButton("Agregar");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
				
				try {
					
					VentanaNuevoUsuario frame = new VentanaNuevoUsuario();
					frame.setVisible(true);
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}
		});
		btnAdd.setBounds(465, 35, 90, 23);
		contentPane.add(btnAdd);
		
		btnEdit = new JButton("Editar");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				String idUsuario=null;
				boolean bandera = false;
				
				for(int i = 0 ; i < tablaUsuarios.getRowCount() ; i++) {
					
					if((boolean)tablaUsuarios.getValueAt(i, 2)) {
						
						idUsuario = respuesta[i][2];
						bandera = true;
					}
				}
				
				if(bandera) {
					
					dispose();
					
					try {
						
						VentanaEditarUsuario frame = new VentanaEditarUsuario(idUsuario);
						frame.setVisible(true);
					} catch (Exception e) {
						
						e.printStackTrace();
					}
				} else {
					lblMensageError.setForeground(Color.RED);
					lblMensageError.setText("No ha seleccionado un usuario.");
				}
			}
		});
		btnEdit.setBounds(465, 70, 90, 23);
		contentPane.add(btnEdit);
		
		lblMensageError = new JLabel("");
		lblMensageError.setForeground(Color.RED);
		lblMensageError.setBackground(Color.LIGHT_GRAY);
		lblMensageError.setBounds(10, 419, 303, 25);
		contentPane.add(lblMensageError);
	}
}
