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

	public VentanaDocentes() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\LECSys\\Images\\LEC.jpg"));
		setTitle("LECSys - Gestión de docentes."+ CheckUsuario.getNombreUsuario());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(10, 20, 1000, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
				try {
					VentanaBusquedaDocente frame = new VentanaBusquedaDocente();
					frame.setVisible(true);
				} catch (Exception d) {
					d.printStackTrace();
				}
			}
		});
		btnBuscar.setBounds(890, 70, 90, 23);
		contentPane.add(btnBuscar);
		
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
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
			}
		});
		btnVolver.setBounds(890, 520, 90, 23);
		contentPane.add(btnVolver);
		
		JScrollPane scrollTabla = new JScrollPane();
		scrollTabla.setBounds(0, 30, 880, 517);
		contentPane.add(scrollTabla);
		tablaProfesores = new JTable();
		actualizarTabla(true);
		scrollTabla.setViewportView(tablaProfesores);
		
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
	}
	
	public final void abrirVentana() {
		
		int nivelAcceso = CheckUsuario.getNivelNivelAcceso();
		
		if(nivelAcceso < 3)
		{
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
		
		DefaultTableModel tablaModelo;
		String titulo [] = {"Legajo", "Nombre", "Apellido", "DNI", "Dirección", "Teléfono", "e-mail"};
		tablaModelo = new DefaultTableModel(ABMCDocentes.getProfesores(estado), titulo);
		tablaProfesores.setModel(tablaModelo);
		tablaProfesores.setEnabled(false);
		tablaProfesores.getColumnModel().getColumn(0).setPreferredWidth(50);
		tablaProfesores.getColumnModel().getColumn(0).setMinWidth(50);
		tablaProfesores.getColumnModel().getColumn(0).setMaxWidth(50);
		tablaProfesores.getColumnModel().getColumn(1).setPreferredWidth(100);
		tablaProfesores.getColumnModel().getColumn(1).setMaxWidth(150);
		tablaProfesores.getColumnModel().getColumn(2).setPreferredWidth(60);
		tablaProfesores.getColumnModel().getColumn(2).setMaxWidth(150);
		tablaProfesores.getColumnModel().getColumn(3).setPreferredWidth(70);
		tablaProfesores.getColumnModel().getColumn(3).setMinWidth(60);
		tablaProfesores.getColumnModel().getColumn(3).setMaxWidth(80);
		tablaProfesores.getColumnModel().getColumn(4).setPreferredWidth(115);
		tablaProfesores.getColumnModel().getColumn(5).setPreferredWidth(100);
		tablaProfesores.getColumnModel().getColumn(5).setMinWidth(99);
		tablaProfesores.getColumnModel().getColumn(5).setMaxWidth(120);
		tablaProfesores.getColumnModel().getColumn(6).setPreferredWidth(122);
	}
}
