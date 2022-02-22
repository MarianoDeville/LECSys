import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;
import java.awt.Color;

public class VentanaCrearCurso extends JDialog implements ItemListener{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtLunesHorario;
	private JTextField txtLunesDuracion;
	private JTextField txtMartesHorario;
	private JTextField txtMartesDuracion;
	private JTextField txtMiercolesHorario;
	private JTextField txtMiercolesDuracion;
	private JTextField txtJuevesHorario;
	private JTextField txtJuevesDuracion;
	private JTextField txtViernesHorario;
	private JTextField txtViernesDuracion;
	private JTextField txtSabadoHorario;
	private JTextField txtSabadoDuracion;
	private JTextField txtCuota;
	private JLabel lblMensageError;
	private JComboBox<String> comboBoxProfesor;
	private JComboBox<String> comboBoxAño;
	private JComboBox<String> comboBoxNivel;
	private JComboBox<String> comboBoxTurno;
	private String respuesta[][];
	
	public VentanaCrearCurso() {
		
		setTitle("LECSys - Crear nuevo curso."+ CheckUsuario.getNombreUsuario());
		setIconImage(Toolkit.getDefaultToolkit().getImage(LECSys.rutaImagenes + "LEC.jpg"));
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(10, 20, 480, 330);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
				
				try {
					
					VentanaCursos frame = new VentanaCursos();
					frame.setVisible(true);
				} catch (Exception d) {
					
					d.printStackTrace();
				}
			}
		});
		btnVolver.setBounds(356, 260, 90, 23);
		contentPane.add(btnVolver);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String valorCurso[] = {
										(String)comboBoxAño.getSelectedItem(),
										(String)comboBoxNivel.getSelectedItem(),
										respuesta[comboBoxProfesor.getSelectedIndex()][0],
										(String)comboBoxTurno.getSelectedItem()
				};
				boolean bandera = false;
				
				if((txtLunesHorario.getText().length() != 0 ||
					txtMartesHorario.getText().length() != 0 ||
					txtMiercolesHorario.getText().length() != 0 ||
					txtJuevesHorario.getText().length() != 0 ||
					txtViernesHorario.getText().length() != 0 ||
					txtSabadoHorario.getText().length() != 0 ) &&
					(txtCuota.getText().length() != 0 ||
					isNumeric(txtCuota.getText()))) {

					bandera = ABMCCurso.crearCurso(valorCurso);
					String idCurso = ABMCCurso.getUltimoId() + "";			// Obtengo el id del registro que acabo de cargar.
					String valorDiasCursado[] = new String[6];
					String valorCuota[] = {idCurso, txtCuota.getText()};
					ABMCValorCuota.nuevaCuota(valorCuota);
											
					if(txtLunesHorario.getText().length() != 0 && bandera) {
						
						valorDiasCursado[0] = "Lunes";
						valorDiasCursado[1] = txtLunesHorario.getText();
						valorDiasCursado[2] = txtLunesDuracion.getText();
						valorDiasCursado[3] = idCurso;
						bandera = ABMCDiasCurso.crearDiasCurso(valorDiasCursado);
					}
					if(txtMartesHorario.getText().length() != 0 && bandera) {
						
						valorDiasCursado[0] = "Martes";
						valorDiasCursado[1] = txtMartesHorario.getText();
						valorDiasCursado[2] = txtMartesDuracion.getText();
						valorDiasCursado[3] = idCurso;
						bandera = ABMCDiasCurso.crearDiasCurso(valorDiasCursado);
					}
					if(txtMiercolesHorario.getText().length() != 0 && bandera) {
						
						valorDiasCursado[0] = "Miércoles";
						valorDiasCursado[1] = txtMiercolesHorario.getText();
						valorDiasCursado[2] = txtMiercolesDuracion.getText();
						valorDiasCursado[3] = idCurso;
						bandera = ABMCDiasCurso.crearDiasCurso(valorDiasCursado);
					}
					if(txtJuevesHorario.getText().length() != 0 && bandera) {
						
						valorDiasCursado[0] = "Jueves";
						valorDiasCursado[1] = txtJuevesHorario.getText();
						valorDiasCursado[2] = txtJuevesDuracion.getText();
						valorDiasCursado[3] = idCurso;
						bandera = ABMCDiasCurso.crearDiasCurso(valorDiasCursado);
					}
					if(txtViernesHorario.getText().length() != 0 && bandera) {
						
						valorDiasCursado[0] = "Viernes";
						valorDiasCursado[1] = txtViernesHorario.getText();
						valorDiasCursado[2] = txtViernesDuracion.getText();
						valorDiasCursado[3] = idCurso;
						bandera = ABMCDiasCurso.crearDiasCurso(valorDiasCursado);
					}
					if(txtSabadoHorario.getText().length() != 0 && bandera) {
						
						valorDiasCursado[0] = "Sábado";
						valorDiasCursado[1] = txtSabadoHorario.getText();
						valorDiasCursado[2] = txtSabadoDuracion.getText();
						valorDiasCursado[3] = idCurso;
						bandera = ABMCDiasCurso.crearDiasCurso(valorDiasCursado);
					}
				}
				if(bandera) {
					
					limpiarCampos();
					lblMensageError.setForeground(Color.BLUE);
					lblMensageError.setText("Carga correcta");
				} else {
					
					lblMensageError.setForeground(Color.RED);
					lblMensageError.setText("Error al intentar cargar los datos.");
				}
			}
		});
		btnGuardar.setBounds(236, 260, 90, 23);
		contentPane.add(btnGuardar);
		
		JLabel lblAo = new JLabel("Año:");
		lblAo.setBounds(25, 40, 70, 20);
		contentPane.add(lblAo);
		
		JLabel lblNivel = new JLabel("Nivel:");
		lblNivel.setBounds(25, 15, 70, 20);
		contentPane.add(lblNivel);
		
		JLabel lblProfesor = new JLabel("Profesor:");
		lblProfesor.setBounds(25, 65, 70, 20);
		contentPane.add(lblProfesor);
		
		JLabel lblTurno = new JLabel("Turno:");
		lblTurno.setBounds(25, 90, 70, 20);
		contentPane.add(lblTurno);
		
		JLabel lblSchedule = new JLabel("Horario:");
		lblSchedule.setBounds(25, 175, 60, 20);
		contentPane.add(lblSchedule);
		
		JLabel lblDuration = new JLabel("Duración:");
		lblDuration.setBounds(25, 195, 60, 20);
		contentPane.add(lblDuration);
		
		JLabel lblLunes = new JLabel("Lunes");
		lblLunes.setHorizontalAlignment(SwingConstants.CENTER);
		lblLunes.setBounds(85, 155, 60, 20);
		contentPane.add(lblLunes);
		
		txtLunesHorario = new JTextField();
		txtLunesHorario.setHorizontalAlignment(SwingConstants.CENTER);
		txtLunesHorario.setColumns(10);
		txtLunesHorario.setBounds(85, 175, 60, 20);
		contentPane.add(txtLunesHorario);
		
		txtLunesDuracion = new JTextField();
		txtLunesDuracion.setHorizontalAlignment(SwingConstants.CENTER);
		txtLunesDuracion.setColumns(10);
		txtLunesDuracion.setBounds(85, 195, 60, 20);
		contentPane.add(txtLunesDuracion);
		
		JLabel lblMartes = new JLabel("Martes");
		lblMartes.setHorizontalAlignment(SwingConstants.CENTER);
		lblMartes.setBounds(145, 155, 60, 20);
		contentPane.add(lblMartes);
		
		txtMartesHorario = new JTextField();
		txtMartesHorario.setHorizontalAlignment(SwingConstants.CENTER);
		txtMartesHorario.setColumns(10);
		txtMartesHorario.setBounds(145, 175, 60, 20);
		contentPane.add(txtMartesHorario);
		
		txtMartesDuracion = new JTextField();
		txtMartesDuracion.setHorizontalAlignment(SwingConstants.CENTER);
		txtMartesDuracion.setColumns(10);
		txtMartesDuracion.setBounds(145, 195, 60, 20);
		contentPane.add(txtMartesDuracion);
		
		JLabel lblMiercoles = new JLabel("Miércoles");
		lblMiercoles.setHorizontalAlignment(SwingConstants.CENTER);
		lblMiercoles.setBounds(205, 155, 60, 20);
		contentPane.add(lblMiercoles);
		
		txtMiercolesHorario = new JTextField();
		txtMiercolesHorario.setHorizontalAlignment(SwingConstants.CENTER);
		txtMiercolesHorario.setColumns(10);
		txtMiercolesHorario.setBounds(205, 175, 60, 20);
		contentPane.add(txtMiercolesHorario);
		
		txtMiercolesDuracion = new JTextField();
		txtMiercolesDuracion.setHorizontalAlignment(SwingConstants.CENTER);
		txtMiercolesDuracion.setColumns(10);
		txtMiercolesDuracion.setBounds(205, 195, 60, 20);
		contentPane.add(txtMiercolesDuracion);
		
		JLabel lblJueves = new JLabel("Jueves");
		lblJueves.setHorizontalAlignment(SwingConstants.CENTER);
		lblJueves.setBounds(265, 155, 60, 20);
		contentPane.add(lblJueves);
		
		txtJuevesHorario = new JTextField();
		txtJuevesHorario.setHorizontalAlignment(SwingConstants.CENTER);
		txtJuevesHorario.setColumns(10);
		txtJuevesHorario.setBounds(266, 175, 60, 20);
		contentPane.add(txtJuevesHorario);
		
		txtJuevesDuracion = new JTextField();
		txtJuevesDuracion.setHorizontalAlignment(SwingConstants.CENTER);
		txtJuevesDuracion.setColumns(10);
		txtJuevesDuracion.setBounds(265, 195, 60, 20);
		contentPane.add(txtJuevesDuracion);
		
		JLabel lblViernes = new JLabel("Viernes");
		lblViernes.setHorizontalAlignment(SwingConstants.CENTER);
		lblViernes.setBounds(325, 155, 60, 20);
		contentPane.add(lblViernes);
		
		txtViernesHorario = new JTextField();
		txtViernesHorario.setHorizontalAlignment(SwingConstants.CENTER);
		txtViernesHorario.setColumns(10);
		txtViernesHorario.setBounds(325, 175, 60, 20);
		contentPane.add(txtViernesHorario);
		
		txtViernesDuracion = new JTextField();
		txtViernesDuracion.setHorizontalAlignment(SwingConstants.CENTER);
		txtViernesDuracion.setColumns(10);
		txtViernesDuracion.setBounds(325, 195, 60, 20);
		contentPane.add(txtViernesDuracion);
		
		JLabel lblSabado = new JLabel("Sábado");
		lblSabado.setHorizontalAlignment(SwingConstants.CENTER);
		lblSabado.setBounds(385, 155, 60, 20);
		contentPane.add(lblSabado);
		
		txtSabadoHorario = new JTextField();
		txtSabadoHorario.setHorizontalAlignment(SwingConstants.CENTER);
		txtSabadoHorario.setColumns(10);
		txtSabadoHorario.setBounds(385, 175, 60, 20);
		contentPane.add(txtSabadoHorario);
		
		txtSabadoDuracion = new JTextField();
		txtSabadoDuracion.setHorizontalAlignment(SwingConstants.CENTER);
		txtSabadoDuracion.setColumns(10);
		txtSabadoDuracion.setBounds(385, 195, 60, 20);
		contentPane.add(txtSabadoDuracion);
		
		JButton btnNuevoProfesor = new JButton("Nuevo docente");
		btnNuevoProfesor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					
					VentanaCrearDocente frame = new VentanaCrearDocente(false);
					frame.setVisible(true);
				} catch (Exception e) {
					
					e.printStackTrace();
				}
				cargarListaProfesores();
			}
		});
		btnNuevoProfesor.setBounds(305, 65, 140, 20);
		contentPane.add(btnNuevoProfesor);
		
		comboBoxProfesor = new JComboBox<String>();
		comboBoxProfesor.setBounds(95, 65, 200, 20);
		contentPane.add(comboBoxProfesor);
		cargarListaProfesores();
		
		comboBoxNivel = new JComboBox<String>();
		comboBoxNivel.setModel(new DefaultComboBoxModel<String>(new String[] {"Kinder", "Children", "Junior", "Teens", "Adults"}));
		comboBoxNivel.setBounds(95, 15, 120, 20);
		comboBoxNivel.addItemListener(this);
		contentPane.add(comboBoxNivel);
		
		comboBoxAño = new JComboBox<String>();
		comboBoxAño.setModel(new DefaultComboBoxModel<String>(new String[] {" ", "PRE"}));
		comboBoxAño.setBounds(95, 40, 120, 20);
		contentPane.add(comboBoxAño);
		
		comboBoxTurno = new JComboBox<String>();
		comboBoxTurno.setModel(new DefaultComboBoxModel<String>(new String[] {"Mañana", "Tarde", "Noche"}));
		comboBoxTurno.setBounds(95, 90, 120, 20);
		contentPane.add(comboBoxTurno);
		
		lblMensageError = new JLabel("");
		lblMensageError.setHorizontalAlignment(SwingConstants.CENTER);
		lblMensageError.setBounds(25, 225, 420, 20);
		contentPane.add(lblMensageError);
		
		JLabel lblCuota = new JLabel("Valor cuota:");
		lblCuota.setBounds(25, 115, 70, 20);
		contentPane.add(lblCuota);
		
		txtCuota = new JTextField();
		txtCuota.setBounds(95, 115, 120, 20);
		contentPane.add(txtCuota);
		txtCuota.setColumns(10);
	}
	
	private void limpiarCampos() {
		
		txtLunesHorario.setText(null);
		txtMartesHorario.setText(null);
		txtMiercolesHorario.setText(null);
		txtJuevesHorario.setText(null);
		txtViernesHorario.setText(null);
		txtSabadoHorario.setText(null);
		txtLunesDuracion.setText(null);
		txtMartesDuracion.setText(null);
		txtMiercolesDuracion.setText(null);
		txtJuevesDuracion.setText(null);
		txtViernesDuracion.setText(null);
		txtSabadoDuracion.setText(null);
		txtCuota.setText(null);
		lblMensageError.setText(null);
	}
	
	private void cargarListaProfesores() {
		
		respuesta = ABMCDocentes.getProfesores(true);
		if(respuesta != null) {
			
			String listaProfesores[] = new String[respuesta.length]; 
			
			for(int i=0 ; i < respuesta.length ; i++) {
				
				listaProfesores[i] = respuesta[i][1] + " " +respuesta[i][2];
			}
			DefaultComboBoxModel<String> comboBoxModelo = new DefaultComboBoxModel<String>(listaProfesores);
			comboBoxProfesor.setModel(comboBoxModelo);
		}
	}
	
	private static boolean isNumeric(String cadena) {
		
		try {
	
			Double.parseDouble(cadena);
			return true;
		} catch (NumberFormatException e){
			
			return false;
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
        if (e.getSource()==comboBoxNivel) {
        	
        	if(comboBoxNivel.getSelectedItem().equals("Kinder"))
        		
        		comboBoxAño.setModel(new DefaultComboBoxModel<String>(new String[] {" ", "PRE"}));
        	else if(comboBoxNivel.getSelectedItem().equals("Children"))
        		
            	comboBoxAño.setModel(new DefaultComboBoxModel<String>(new String[] {"First", "Second", "Third"}));
            else if(comboBoxNivel.getSelectedItem().equals("Teens"))
            	
            	comboBoxAño.setModel(new DefaultComboBoxModel<String>(new String[] {"I", "II", "III"}));
            else
            	
            	comboBoxAño.setModel(new DefaultComboBoxModel<String>(new String[] {"First", "Second", "Third", "Fourth", "Fifth", "Sixth"}));
        }
	}
}
