import java.awt.EventQueue;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JComboBox;

public class VentanaCalificaciones extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComboBox<String> comboBoxCurso;
	private String listadoCursos[][];
	private String cursoElegido;

	public VentanaCalificaciones() {
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(LECSys.rutaImagenes + "LEC.jpg"));
		setTitle("LECSys - Calificaciones."+ CheckUsuario.getNombreUsuario());
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 470, 330);
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
		btnVolver.setBounds(317, 253, 90, 23);
		contentPane.add(btnVolver);
		
		JLabel lblCurso = new JLabel("Curso:");
		lblCurso.setBounds(35, 35, 70, 20);
		contentPane.add(lblCurso);
		
		listadoCursos = ABMCCurso.getListaCursos(true);
		String listaCursos[] = new String[listadoCursos.length]; 
		
		for(int i=0 ; i < listaCursos.length ; i++)
		{
			listaCursos[i] = listadoCursos[i][1] + " " + listadoCursos[i][2] + " - " + listadoCursos[i][4];
		}
		
		comboBoxCurso = new JComboBox<String>();
		comboBoxCurso.setModel(new DefaultComboBoxModel<String>(listaCursos));
		comboBoxCurso.setBounds(105, 35, 270, 20);
		contentPane.add(comboBoxCurso);
		
		JButton btnCargarNota = new JButton("Cargar nota");
		btnCargarNota.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				cursoElegido = listadoCursos[comboBoxCurso.getSelectedIndex()][0];
				
				try {
					
					VentanaCargarCalificaciones frame = new VentanaCargarCalificaciones(cursoElegido);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		btnCargarNota.setBounds(59, 78, 150, 23);
		contentPane.add(btnCargarNota);
		
		JButton btnListarNotas = new JButton("Listar notas");
		btnListarNotas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				cursoElegido = listadoCursos[comboBoxCurso.getSelectedIndex()][0];
				
				try {
					VentanaListarCalificaciones frame = new VentanaListarCalificaciones(cursoElegido);
					frame.setVisible(true);
				} catch (Exception d) {
					d.printStackTrace();
				}
				
			}
		});
		btnListarNotas.setBounds(237, 78, 150, 23);
		contentPane.add(btnListarNotas);
	}
	
	public final void abrirVentana() {
		
		int nivelAcceso = CheckUsuario.getNivelNivelAcceso();
		
		if(nivelAcceso < 5)
		{
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						VentanaCalificaciones frame = new VentanaCalificaciones();
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
}
