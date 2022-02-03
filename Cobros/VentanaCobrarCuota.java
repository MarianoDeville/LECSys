import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.event.ActionEvent;

public class VentanaCobrarCuota extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tablaFlia;
	private Object cuerpo[][];
	private String respuesta[][];

	public VentanaCobrarCuota() {
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(LECSys.rutaImagenes + "LEC.jpg"));
		setTitle("LECSys - Cobrar"+ CheckUsuario.getNombreUsuario());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(10, 10, 940, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setResizable(false);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollTabla = new JScrollPane();
		scrollTabla.setBounds(1, 11, 770, 540);
		contentPane.add(scrollTabla);
		tablaFlia = new JTable();
		actualizoTabla();
		scrollTabla.setViewportView(tablaFlia);	
		
		JButton btnCobrar = new JButton("Cobrar");
		btnCobrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				for(int i = 0 ; i < tablaFlia.getRowCount() ; i++) {
					
					if((boolean)tablaFlia.getValueAt(i, 6)) {

						try {
							
							VentanaEfectuarCobro frame = new VentanaEfectuarCobro(respuesta[i]);
							frame.setVisible(true);
						} catch (Exception e) {
							
							e.printStackTrace();
						}
						dispose();
						break;
					}
				}
			}
		});
		btnCobrar.setBounds(796, 35, 110, 23);
		contentPane.add(btnCobrar);
		
		JButton btnBorrarDeuda = new JButton("Borrar deuda");
		btnBorrarDeuda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if(CheckUsuario.getNivelNivelAcceso() < 2) {
					
					for(int i = 0 ; i < tablaFlia.getRowCount() ; i++) {
						
						if((boolean)tablaFlia.getValueAt(i, 5)) {
					
							ABMCGrupoFamiliar.borrarDeuda(respuesta[i][0]);
							actualizoTabla();
							break;
						}
					}
				} else {
					
					JOptionPane.showMessageDialog(null, "No tiene suficientes privilegios.");
				}
			}
		});
		btnBorrarDeuda.setBounds(796, 70, 110, 23);
		contentPane.add(btnBorrarDeuda);
		
		JButton btnImprimir = new JButton("Imprimir");
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					tablaFlia.print();
				} catch (PrinterException d) {
					
					d.printStackTrace();
				}
			}
		});
		btnImprimir.setBounds(796, 105, 110, 23);
		contentPane.add(btnImprimir);

		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
			}
		});
		btnVolver.setBounds(796, 520, 110, 23);
		contentPane.add(btnVolver);
	}
	
	private void actualizoTabla() {
		
		String titulo [] = {"Nombre", "Integrantes", "Cuotas", "Valor cuota", "Descuento" , "Monto total", "Sel."};
		respuesta = ABMCGrupoFamiliar.getGruposFamilias();
				
		if(respuesta != null) {
			
			cuerpo = new Object[respuesta.length][7];
			
			for(int i = 0 ; i < respuesta.length ; i++) {
				
				int cantCuotas = Integer.parseInt(respuesta[i][3]);
				int valorCuotas = Integer.parseInt(respuesta[i][4]);
				int descuento = valorCuotas * Integer.parseInt(respuesta[i][5]) / 100;
				cuerpo[i][0] = respuesta[i][1];
				cuerpo[i][1] = respuesta[i][2];
				cuerpo[i][2] = cantCuotas;
				cuerpo[i][3] = valorCuotas;
				cuerpo[i][4] = descuento;
				cuerpo[i][5] = cantCuotas * (valorCuotas - descuento);
				cuerpo[i][6] = false;
			}
		} else
			
			cuerpo = null;
		
		DefaultTableModel tablaModelo = new DefaultTableModel(cuerpo, titulo) {
			
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false, true
			};
			
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
			
			private static final long serialVersionUID = 1L;
			public Class<?> getColumnClass(int column) {
				
		        if(column == 6)
		        	return Boolean.class;
		        else
		        	return String.class;
		    }
		};
		tablaFlia.setModel(tablaModelo);
		tablaFlia.isCellEditable(0,1);
		tablaFlia.getColumnModel().getColumn(0).setMinWidth(100);
		tablaFlia.getColumnModel().getColumn(0).setPreferredWidth(200);
		tablaFlia.getColumnModel().getColumn(0).setMaxWidth(300);
		tablaFlia.getColumnModel().getColumn(1).setMinWidth(30);
		tablaFlia.getColumnModel().getColumn(1).setPreferredWidth(40);
		tablaFlia.getColumnModel().getColumn(1).setMaxWidth(60);
		tablaFlia.getColumnModel().getColumn(2).setMinWidth(30);
		tablaFlia.getColumnModel().getColumn(2).setPreferredWidth(40);
		tablaFlia.getColumnModel().getColumn(2).setMaxWidth(60);
		tablaFlia.getColumnModel().getColumn(3).setMinWidth(50);
		tablaFlia.getColumnModel().getColumn(3).setPreferredWidth(100);
		tablaFlia.getColumnModel().getColumn(3).setMaxWidth(150);
		tablaFlia.getColumnModel().getColumn(4).setMinWidth(50);
		tablaFlia.getColumnModel().getColumn(4).setPreferredWidth(100);
		tablaFlia.getColumnModel().getColumn(4).setMaxWidth(150);
		tablaFlia.getColumnModel().getColumn(5).setMinWidth(50);
		tablaFlia.getColumnModel().getColumn(5).setPreferredWidth(100);
		tablaFlia.getColumnModel().getColumn(5).setMaxWidth(150);
		tablaFlia.getColumnModel().getColumn(6).setMinWidth(20);
		tablaFlia.getColumnModel().getColumn(6).setPreferredWidth(30);
		tablaFlia.getColumnModel().getColumn(6).setMaxWidth(50);
	}
}
