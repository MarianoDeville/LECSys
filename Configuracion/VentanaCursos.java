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

	public VentanaCursos() {

		setTitle("LECSys - Cursos."+ CheckUsuario.getNombreUsuario());
		setIconImage(Toolkit.getDefaultToolkit().getImage(LECSys.rutaImagenes + "LEC.jpg"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(10, 20, 680, 480);
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
		btnVolver.setBounds(560, 400, 90, 23);
		contentPane.add(btnVolver);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
				try {
					VentanaBuscarCurso frame = new VentanaBuscarCurso();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnBuscar.setBounds(560, 65, 90, 23);
		contentPane.add(btnBuscar);
		
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
		btnCrear.setBounds(560, 30, 90, 23);
		contentPane.add(btnCrear);
		
		JScrollPane scrollTabla = new JScrollPane();
		scrollTabla.setBounds(5, 30, 530, 410);
		contentPane.add(scrollTabla);
		
		String titulo[] = {"Año", "Nivel", "Profesor", "Día", "Precio"};
		String respuesta[][] = ABMCCurso.getListaCursos(true);
		DefaultTableModel tablaModelo;
		
		if(respuesta != null) {
			
			for(int i = 0; i < respuesta.length; i++)
			{
				respuesta[i][0] = respuesta[i][1];
				respuesta[i][1] = respuesta[i][2];
				respuesta[i][2] = respuesta[i][4];
				respuesta[i][3] = respuesta[i][5];
				respuesta[i][4] = respuesta[i][6];
			}
			
			tablaModelo = new DefaultTableModel(respuesta, titulo);
		} else
			tablaModelo = new DefaultTableModel(null, titulo);

		tablaCursos = new JTable(tablaModelo);
		tablaCursos.setEnabled(false);
		tablaCursos.getColumnModel().getColumn(0).setPreferredWidth(100);
		tablaCursos.getColumnModel().getColumn(0).setMinWidth(50);
		tablaCursos.getColumnModel().getColumn(0).setMaxWidth(250);
		tablaCursos.getColumnModel().getColumn(1).setPreferredWidth(100);
		tablaCursos.getColumnModel().getColumn(1).setMinWidth(50);
		tablaCursos.getColumnModel().getColumn(1).setMaxWidth(250);
		tablaCursos.getColumnModel().getColumn(2).setPreferredWidth(130);
		tablaCursos.getColumnModel().getColumn(2).setMinWidth(70);
		tablaCursos.getColumnModel().getColumn(2).setMaxWidth(250);
		tablaCursos.getColumnModel().getColumn(3).setPreferredWidth(150);
		tablaCursos.getColumnModel().getColumn(3).setMinWidth(100);
		tablaCursos.getColumnModel().getColumn(3).setMaxWidth(250);
		tablaCursos.getColumnModel().getColumn(4).setPreferredWidth(150);
		tablaCursos.getColumnModel().getColumn(4).setMinWidth(100);
		tablaCursos.getColumnModel().getColumn(4).setMaxWidth(250);
		scrollTabla.setViewportView(tablaCursos);
		
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
		btnImprimir.setBounds(561, 100, 90, 23);
		contentPane.add(btnImprimir);
	}
}
