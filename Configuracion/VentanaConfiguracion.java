import java.awt.EventQueue;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;

public class VentanaConfiguracion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static boolean recuperar;

	public VentanaConfiguracion() {
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(LECSys.rutaImagenes + "LEC.jpg"));
		setTitle("LECSys - Configuración."+ CheckUsuario.getNombreUsuario());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(10, 20, 673, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblUsuarios = new JLabel("Usuarios");
		lblUsuarios.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsuarios.setBounds(98, 54, 104, 14);
		contentPane.add(lblUsuarios);
		
		JButton btnUsuarios = new JButton("");
		btnUsuarios.setIcon(new ImageIcon(LECSys.rutaImagenes + "Usuarios.jpg"));
		btnUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					
					VentanaUsuarios frame = new VentanaUsuarios();
					frame.setVisible(true);
				} catch (Exception e) {
					
					LogErrores.escribirLog("Error al abrir la ventana usuarios.");
					e.printStackTrace();
				}
			}
		});
		btnUsuarios.setBounds(98, 70, 104, 94);
		contentPane.add(btnUsuarios);

		JLabel lblCurso = new JLabel("Cursos");
		lblCurso.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurso.setBounds(274, 54, 104, 14);
		contentPane.add(lblCurso);
		
		JButton btnCursos = new JButton("");
		btnCursos.setIcon(new ImageIcon(LECSys.rutaImagenes + "Curso.jpg"));
		btnCursos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					VentanaCursos frame = new VentanaCursos();
					frame.setVisible(true);
				} catch (Exception d) {
					
					LogErrores.escribirLog("Error al abrir la ventana curso.");
					d.printStackTrace();
				}
			}
		});
		btnCursos.setBounds(274, 70, 104, 94);
		contentPane.add(btnCursos);
	
		JLabel lblEstadísticas = new JLabel("Estadísticas");
		lblEstadísticas.setHorizontalAlignment(SwingConstants.CENTER);
		lblEstadísticas.setBounds(450, 54, 104, 14);
		contentPane.add(lblEstadísticas);
	
		JButton btnEstadísticas = new JButton("");
		btnEstadísticas.setIcon(new ImageIcon(LECSys.rutaImagenes + "Estadisticas.jpg"));
		btnEstadísticas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					
					VentanaEstadisticas frame = new VentanaEstadisticas();
					frame.setVisible(true);
				} catch (Exception e) {
					
					LogErrores.escribirLog("Error al abrir la ventana estadística.");
					e.printStackTrace();
				}
			}
		});
		btnEstadísticas.setBounds(450, 70, 104, 94);
		contentPane.add(btnEstadísticas);

		JLabel lblActividad = new JLabel("Actividad");
		lblActividad.setHorizontalAlignment(SwingConstants.CENTER);
		lblActividad.setBounds(98, 223, 104, 14);
		contentPane.add(lblActividad);
		
		JButton btnActividad = new JButton("");
		btnActividad.setIcon(new ImageIcon(LECSys.rutaImagenes + "Actividades.png"));
		btnActividad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					
					VentanaActividad frame = new VentanaActividad();
					frame.setVisible(true);
				} catch (Exception e) {
					
					LogErrores.escribirLog("Error al abrir la ventana actividades.");
					e.printStackTrace();
				}
			}
		});
		btnActividad.setBounds(98, 238, 104, 94);
		contentPane.add(btnActividad);
		
		JLabel txtTerminarAño = new JLabel("Finalizar año");
		txtTerminarAño.setHorizontalAlignment(SwingConstants.CENTER);
		txtTerminarAño.setBounds(274, 223, 104, 14);
		contentPane.add(txtTerminarAño);
		
		JButton btnTerminarAño = new JButton("");
		btnTerminarAño.setIcon(new ImageIcon(LECSys.rutaImagenes + "Cierre.jpg"));
		btnTerminarAño.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					
					VentanaConfirmacionFinalización frame = new VentanaConfirmacionFinalización();
					frame.setVisible(true);
				} catch (Exception e) {
					
					LogErrores.escribirLog("Error al abrir la ventana cierre.");
					e.printStackTrace();
				}			
			}
		});
		btnTerminarAño.setBounds(274, 238, 104, 94);
		contentPane.add(btnTerminarAño);
		
		JLabel txtListado = new JLabel("Listado alumnos");
		txtListado.setHorizontalAlignment(SwingConstants.CENTER);
		txtListado.setBounds(450, 223, 104, 14);
		contentPane.add(txtListado);
		
		JButton btnListadoCurso = new JButton("");
		btnListadoCurso.setIcon(new ImageIcon(LECSys.rutaImagenes + "Alumnos curso.png"));
		btnListadoCurso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					VentanaAlumnosCurso frame = new VentanaAlumnosCurso();
					frame.setVisible(true);
				} catch (Exception d) {
					
					LogErrores.escribirLog("Error al abrir la ventana listado alumnos.");
					d.printStackTrace();
				}
			}
		});
		btnListadoCurso.setBounds(450, 238, 104, 94);
		contentPane.add(btnListadoCurso);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(recuperar == true)
					CheckUsuario.recuperarEstado();
				
				dispose();
			}
		});
		btnVolver.setBounds(536, 419, 90, 23);
		contentPane.add(btnVolver);
	}
	
	public final void abrirVentana() {
		
		int nivelAcceso = CheckUsuario.getNivelNivelAcceso();
		recuperar = false;
		
		if(nivelAcceso > 1) {
			
			CheckUsuario.guardarEstado();
			recuperar = true;
			VentanaIngresoUsuario.nuevoIngreso();
			nivelAcceso = CheckUsuario.getNivelNivelAcceso();
		}
	
		if(nivelAcceso < 2) {
			
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					
					try {
						
						VentanaConfiguracion frame = new VentanaConfiguracion();
						frame.setVisible(true);
					} catch (Exception e) {
						
						LogErrores.escribirLog("Error al abrir la ventana configuración.");
						e.printStackTrace();
					}
				}
			});
		} else {
			
			JOptionPane.showMessageDialog(null, "No tiene suficientes privilegios.");
		}
	}
}
