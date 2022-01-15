import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.EventQueue;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;


public class VentanaAsistencia extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComboBox<String> comboBoxCurso;
	private String listadoCursos[][];
	private String cursoElegido;


	public VentanaAsistencia() {
		setResizable(false);
			
		setTitle("LECSys - Asistencia"+ CheckUsuario.getNombreUsuario());
		setIconImage(Toolkit.getDefaultToolkit().getImage(LECSys.rutaImagenes + "LEC.jpg"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(10, 20, 600, 270);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnVolver.setBounds(470, 190, 90, 23);
		contentPane.add(btnVolver);
		
		JLabel lblCurso = new JLabel("Curso:");
		lblCurso.setBounds(35, 35, 70, 20);
		contentPane.add(lblCurso);
		
		listadoCursos = ABMCCurso.getListaCursos(true);
		String listaCursos[] = new String[listadoCursos.length]; 
		
		for(int i=0 ; i < listaCursos.length ; i++)
		{
			listaCursos[i] = listadoCursos[i][1] + " " + listadoCursos[i][2] + " - " + listadoCursos[i][3];
		}
		
		comboBoxCurso = new JComboBox<String>();
		comboBoxCurso.setModel(new DefaultComboBoxModel<String>(listaCursos));
		comboBoxCurso.setBounds(105, 35, 270, 20);
		contentPane.add(comboBoxCurso);
		
		JButton btnTomarAsistencia = new JButton("Tomar asistencia");
		btnTomarAsistencia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				cursoElegido = listadoCursos[comboBoxCurso.getSelectedIndex()][0];
				try {
					VentanaTomarAsistencia frame = new VentanaTomarAsistencia(cursoElegido);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnTomarAsistencia.setBounds(35, 90, 150, 23);
		contentPane.add(btnTomarAsistencia);
		
		JButton btnListaAusentes = new JButton("Lista de ausentes");
		btnListaAusentes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				cursoElegido = listadoCursos[comboBoxCurso.getSelectedIndex()][0];
				try {
					VentanaAusentes frame = new VentanaAusentes(cursoElegido);
					frame.setVisible(true);
				} catch (Exception d) {
					System.err.println("Error al entrar en VentanaAusentes.");
				}
			}
		});
		btnListaAusentes.setBounds(220, 90, 150, 23);
		contentPane.add(btnListaAusentes);
		
		JButton btnListaAlumnos = new JButton("Lista de estudiantes");
		btnListaAlumnos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				cursoElegido = listadoCursos[comboBoxCurso.getSelectedIndex()][0];
				try {
					VentanaListarAlumnos frame = new VentanaListarAlumnos(cursoElegido);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnListaAlumnos.setBounds(405, 90, 150, 23);
		contentPane.add(btnListaAlumnos);
	}
	
	public final void abrirVentana() {
		
		int nivelAcceso = CheckUsuario.getNivelNivelAcceso();
		
		if(nivelAcceso < 5)
		{
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						VentanaAsistencia frame = new VentanaAsistencia();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} else {
			JOptionPane.showMessageDialog(null, "No tiene suficientes privilegios para esta operación.");
		}
	}
}
