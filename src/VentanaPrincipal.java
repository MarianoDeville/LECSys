import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.EventQueue;
import java.awt.Font;

public class VentanaPrincipal {

	JFrame frmVentanaPrincipal;

	public VentanaPrincipal() {
		
		frmVentanaPrincipal = new JFrame();
		frmVentanaPrincipal.setResizable(false);
		frmVentanaPrincipal.setIconImage(Toolkit.getDefaultToolkit().getImage(LECSys.rutaImagenes + "LEC.jpg"));
		frmVentanaPrincipal.setTitle("LEC-SYS"+ CheckUsuario.getNombreUsuario());
		frmVentanaPrincipal.setBounds(10, 20, 800, 600);
		frmVentanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmVentanaPrincipal.setMinimumSize(new Dimension(800, 600));
		frmVentanaPrincipal.getContentPane().setLayout(null);
		
		if(Estadisticas.nuevoMes())
			ABMCGrupoFamiliar.modificarMeses("", "+", "1");
		
		String cuerpo[] = {CheckUsuario.getIdUsuario(),"Arranque del sistema","Principal"};
		ABMCActividad.guardoNuevaActividad(cuerpo);

		JLabel lblAlumnos = new JLabel("ABML estudiantes");
		lblAlumnos.setHorizontalAlignment(SwingConstants.CENTER);
		lblAlumnos.setBounds(146, 55, 108, 14);
		frmVentanaPrincipal.getContentPane().add(lblAlumnos);
		
		JButton btnABMAlumnos = new JButton("");
		btnABMAlumnos.setIcon(new ImageIcon(LECSys.rutaImagenes + "Alumno.jpg"));
		btnABMAlumnos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(CheckUsuario.getNivelNivelAcceso() < 3) {
					
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								VentanaAlumnos frame = new VentanaAlumnos();
								frame.setVisible(true);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				} else {
					mensageNoAutorizado();
				}
			}
		});
		btnABMAlumnos.setBounds(148, 70, 104, 94);
		frmVentanaPrincipal.getContentPane().add(btnABMAlumnos);
		
		JLabel lblProfesores = new JLabel("ABML docentes");
		lblProfesores.setHorizontalAlignment(SwingConstants.CENTER);
		lblProfesores.setBounds(346, 55, 108, 14);
		frmVentanaPrincipal.getContentPane().add(lblProfesores);
		
		JButton btnABMProfesores = new JButton("");
		btnABMProfesores.setIcon(new ImageIcon(LECSys.rutaImagenes + "Profesores.png"));
		btnABMProfesores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(CheckUsuario.getNivelNivelAcceso() < 3) {
					
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
					mensageNoAutorizado();
				}
			}
		});
		btnABMProfesores.setBounds(348, 70, 104, 94);
		frmVentanaPrincipal.getContentPane().add(btnABMProfesores);
		
		JLabel lblAsistencia = new JLabel("Asistencia");
		lblAsistencia.setHorizontalAlignment(SwingConstants.CENTER);
		lblAsistencia.setBounds(546, 55, 108, 14);
		lblAsistencia.setVisible(false);	// Está invisible porque no está correctamente implementado aún. ////////////////////////////////////////////////////////////////
		frmVentanaPrincipal.getContentPane().add(lblAsistencia);
		
		JButton btnAsistencia = new JButton("");
		btnAsistencia.setIcon(new ImageIcon(LECSys.rutaImagenes + "asistencia.png"));
		btnAsistencia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				VentanaAsistencia interfaceAsistencia = new VentanaAsistencia();
				interfaceAsistencia.abrirVentana();
			}
		});
		btnAsistencia.setBounds(548, 70, 104, 94);
		btnAsistencia.setVisible(false);	// Está invisible porque no está correctamente implementado aún. ////////////////////////////////////////////////////////////////
		frmVentanaPrincipal.getContentPane().add(btnAsistencia);
		
		JLabel lblCobros = new JLabel("Cobros");
		lblCobros.setHorizontalAlignment(SwingConstants.CENTER);
		lblCobros.setBounds(146, 237, 108, 14);
		frmVentanaPrincipal.getContentPane().add(lblCobros);
		
		JButton btnCobros = new JButton("");
		btnCobros.setIcon(new ImageIcon(LECSys.rutaImagenes + "Cobros.jpg"));
		btnCobros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(CheckUsuario.getNivelNivelAcceso() < 3) {
					
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								VentanaCobroCuota frame = new VentanaCobroCuota();
								frame.setVisible(true);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				} else {
					mensageNoAutorizado();
				}
			}
		});
		btnCobros.setBounds(148, 252, 104, 94);
		frmVentanaPrincipal.getContentPane().add(btnCobros);
		
		JLabel lblPagos = new JLabel("Pagos");
		lblPagos.setHorizontalAlignment(SwingConstants.CENTER);
		lblPagos.setBounds(346, 237, 108, 14);
		frmVentanaPrincipal.getContentPane().add(lblPagos);
		
		JButton btnPagos = new JButton("");
		btnPagos.setIcon(new ImageIcon(LECSys.rutaImagenes + "Pagos.jpg"));
		btnPagos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(CheckUsuario.getNivelNivelAcceso() < 3)
				{
					EventQueue.invokeLater(new Runnable() {
						@Override
						public void run() {
							try {
								VentanaPagos frame = new VentanaPagos();
								frame.setVisible(true);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				} else {
					mensageNoAutorizado();
				}
			}
		});
		btnPagos.setBounds(348, 252, 104, 94);
		frmVentanaPrincipal.getContentPane().add(btnPagos);
		
		JLabel lblExamenes = new JLabel("Exámenes");
		lblExamenes.setHorizontalAlignment(SwingConstants.CENTER);
		lblExamenes.setBounds(546, 237, 108, 14);
		lblExamenes.setVisible(false);	// Está invisible porque no está correctamente implementado aún. ////////////////////////////////////////////////////////////////
		frmVentanaPrincipal.getContentPane().add(lblExamenes);
		
		JButton btnCalificaciones = new JButton("");
		btnCalificaciones.setIcon(new ImageIcon(LECSys.rutaImagenes + "Calificaciones.jpg"));
		btnCalificaciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				VentanaCalificaciones interfaceCalificaciones = new VentanaCalificaciones();
				interfaceCalificaciones.abrirVentana();
			}
		});
		btnCalificaciones.setBounds(548, 252, 104, 94);
		btnCalificaciones.setVisible(false);	// Está invisible porque no está correctamente implementado aún. ////////////////////////////////////////////////////////////////
		frmVentanaPrincipal.getContentPane().add(btnCalificaciones);
		
		JLabel lblConfiguracin = new JLabel("Configuración");
		lblConfiguracin.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblConfiguracin.setHorizontalAlignment(SwingConstants.CENTER);
		lblConfiguracin.setBounds(156, 455, 84, 14);
		frmVentanaPrincipal.getContentPane().add(lblConfiguracin);
		
		JButton btnConfiguracion = new JButton("");
		btnConfiguracion.setIcon(new ImageIcon(LECSys.rutaImagenes + "Configuracion.jpg"));
		btnConfiguracion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				VentanaConfiguracion interfaceConfiguracion = new VentanaConfiguracion();
				interfaceConfiguracion.abrirVentana();
			}
		});
		btnConfiguracion.setBounds(160, 470, 76, 69);
		frmVentanaPrincipal.getContentPane().add(btnConfiguracion);
		
		JLabel lblSalir = new JLabel("Salir");
		lblSalir.setHorizontalAlignment(SwingConstants.CENTER);
		lblSalir.setBounds(689, 455, 76, 14);
		frmVentanaPrincipal.getContentPane().add(lblSalir);
		
		JButton btnSalir = new JButton("");
		btnSalir.setIcon(new ImageIcon(LECSys.rutaImagenes + "Salir.png"));
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				System.exit(0);
			}
		});
		btnSalir.setBounds(690, 470, 76, 69);
		frmVentanaPrincipal.getContentPane().add(btnSalir);
		
		JLabel lblRelogin = new JLabel("Cambiar usuario");
		lblRelogin.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblRelogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblRelogin.setBounds(25, 455, 96, 14);
		frmVentanaPrincipal.getContentPane().add(lblRelogin);
		
		JButton btnReLogin = new JButton("");
		btnReLogin.setIcon(new ImageIcon(LECSys.rutaImagenes + "Cambiar de usuario.jpg"));
		btnReLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				VentanaIngresoUsuario entrando = new VentanaIngresoUsuario();
				entrando.nuevoIngreso();
				frmVentanaPrincipal.setTitle("LEC-SYS"+ CheckUsuario.getNombreUsuario());
			}
		});
		btnReLogin.setBounds(35, 470, 76, 69);
		frmVentanaPrincipal.getContentPane().add(btnReLogin);	
	}
	
	private static void mensageNoAutorizado() {
		
		JOptionPane.showMessageDialog(null, "No posee suficientes privilegios para esta operación.");
	}
}