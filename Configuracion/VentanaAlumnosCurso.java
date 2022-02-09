import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.print.PrinterException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class VentanaAlumnosCurso extends JFrame implements ItemListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tablaAlumnos;
	private String respuesta[][];
	private JComboBox<String> comboBoxValor;
	private JComboBox<String> comboBoxCriterio;
	private DefaultComboBoxModel<String> listado;
	private JTextField txtCantAlumnos;
	private JButton btnImprimir;
	
	public VentanaAlumnosCurso() {

		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(LECSys.rutaImagenes + "LEC.jpg"));
		setTitle("LECSys - Listado por curso."+ CheckUsuario.getNombreUsuario());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(10, 20, 1000, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		comboBoxCriterio = new JComboBox<String>();
		comboBoxCriterio.setModel(new DefaultComboBoxModel<String>(new String[] {"CURSO", "PROFESOR"}));
		comboBoxCriterio.setSelectedIndex(0);
		comboBoxCriterio.setBounds(35, 35, 100, 20);
		comboBoxCriterio.addItemListener(this);
		contentPane.add(comboBoxCriterio);
		
		respuesta = ABMCCurso.getListaCursos(true);
		String listaCursos[]; 
		
		if(respuesta != null) {
			
			listaCursos = new String[respuesta.length]; 
			
			for(int i=0 ; i < respuesta.length ; i++) {
				
				listaCursos[i] = respuesta[i][1] + " " + respuesta[i][2] + " - " + respuesta[i][4] + " - " + respuesta[i][7];
			}
			listado = new DefaultComboBoxModel<String>(listaCursos);
		} else {
			
			listado = new DefaultComboBoxModel<String>(new String[] {""});
		}
		
		comboBoxValor = new JComboBox<String>();
		comboBoxValor.setModel(listado);
		comboBoxValor.setSelectedIndex(0);
		comboBoxValor.setBounds(155, 35, 350, 20);
		comboBoxValor.addItemListener(this);
		contentPane.add(comboBoxValor);
		
		JLabel lblCantidadAlumnos = new JLabel("Cantidad de alumnos:");
		lblCantidadAlumnos.setBounds(590, 35, 130, 20);
		contentPane.add(lblCantidadAlumnos);
		
		txtCantAlumnos = new JTextField();
		txtCantAlumnos.setEditable(false);
		txtCantAlumnos.setBounds(720, 35, 80, 20);
		contentPane.add(txtCantAlumnos);
		txtCantAlumnos.setColumns(10);
		
		JScrollPane scrollTabla = new JScrollPane();
		scrollTabla.setBounds(1, 82, 880, 465);
		contentPane.add(scrollTabla);
		tablaAlumnos = new JTable();
		tablaAlumnos.setRowSelectionAllowed(false);
		if(respuesta != null)
			actualizarTabla((String)comboBoxCriterio.getSelectedItem(), respuesta[comboBoxValor.getSelectedIndex()][0]);
		
		scrollTabla.setViewportView(tablaAlumnos);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
			}
		});
		btnVolver.setBounds(885, 510, 90, 23);
		contentPane.add(btnVolver);
		
		btnImprimir = new JButton("Imprimir");
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					tablaAlumnos.print();
				} catch (PrinterException d) {
					
					d.printStackTrace();
				}
			}
		});
		btnImprimir.setBounds(885, 110, 90, 23);
		contentPane.add(btnImprimir);
	}
	
	private void actualizarTabla(String campo, String valor) {
		
		String titulo [] = {"Legajo", "Apellido", "Nombre", "Dirección", "Teléfono", "e-mail"};
		String respuesta[][] = ABMCAlumnos.getAlumnos(campo, valor, true, "apellido");
		Object cuerpo[][];
		
		if(respuesta != null) {
			
			cuerpo = new Object[respuesta.length][6];
			txtCantAlumnos.setText(respuesta.length + "");
			
			for(int i = 0 ; i < respuesta.length ; i++) {
				
				cuerpo[i][0] = respuesta[i][0];
				cuerpo[i][1] = respuesta[i][2];
				cuerpo[i][2] = respuesta[i][1];
				cuerpo[i][3] = respuesta[i][4];
				cuerpo[i][4] = respuesta[i][5];
				cuerpo[i][5] = respuesta[i][6];
			}
		} else {
			
			cuerpo = null;	
			txtCantAlumnos.setText("0");
		}
			
		DefaultTableModel tablaModelo = new DefaultTableModel(cuerpo, titulo);
		tablaAlumnos.setModel(tablaModelo);
		tablaAlumnos.setEnabled(false);
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {

		if (e.getSource()==comboBoxCriterio) {

			if(comboBoxCriterio.getSelectedItem().equals("CURSO")) {
			
				respuesta = ABMCCurso.getListaCursos(true);
				
				if(respuesta != null) {
					String listaCursos[] = new String[respuesta.length];
				
					for(int i=0 ; i < respuesta.length ; i++) {
						
						listaCursos[i] = respuesta[i][1] + " " + respuesta[i][2] + " - " + respuesta[i][4] + " - " + respuesta[i][7];
					}
					listado = new DefaultComboBoxModel<String>(listaCursos);
				} else
					
					listado = new DefaultComboBoxModel<String>(new String[] {""});
			} else {
				
				respuesta = ABMCDocentes.getProfesores(true);
				
				if(respuesta != null) {
					String listaCursos[] = new String[respuesta.length];
					
					for(int i=0 ; i < respuesta.length ; i++) {
						
						listaCursos[i] = respuesta[i][1] + " " + respuesta[i][2];
					}
					listado = new DefaultComboBoxModel<String>(listaCursos);
				} else
					
					listado = new DefaultComboBoxModel<String>(new String[] {""}); 
			}
			comboBoxValor.setModel(listado);
			
			if(respuesta != null)
				actualizarTabla((String)comboBoxCriterio.getSelectedItem(), respuesta[comboBoxValor.getSelectedIndex()][0]);
		}
		
		if (e.getSource()==comboBoxValor || e.getSource()==comboBoxCriterio) {

			if(respuesta != null)
			
				actualizarTabla((String)comboBoxCriterio.getSelectedItem(), respuesta[comboBoxValor.getSelectedIndex()][0]);
		}
	}
}
