import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class VentanaEditarCurso extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblMensageError;
	private JTextField txtHorarioLunes;
	private JTextField txtDuracionLunes;
	private JTextField txtHorarioMartes;
	private JTextField txtDuracionMartes;
	private JTextField txtHorarioMiercoles;
	private JTextField txtDuracionMiercoles;
	private JTextField txtHorarioJueves;
	private JTextField txtDuracionJueves;
	private JTextField txtHorarioViernes;
	private JTextField txtDuracionViernes;
	private JTextField txtHorarioSabado;
	private JTextField txtDuracionSabado;
	private JTextField txtCuota;
	private JComboBox<String> comboBoxProfesor;
	private String idCurso;
	private String respuestaProfesor[][];
	private JButton btnGuardar;
	private JTextField txtCurso;
	
	public VentanaEditarCurso(String [] cursoAModificar) {
	
		setTitle("LECSys - Buscar y editar cursos."+ CheckUsuario.getNombreUsuario());
		setIconImage(Toolkit.getDefaultToolkit().getImage(LECSys.rutaImagenes + "LEC.jpg"));
		setModalityType(ModalityType.APPLICATION_MODAL);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(10, 20, 480, 310);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblCurso = new JLabel("Curso:");
		lblCurso.setBounds(25, 15, 70, 20);
		contentPanel.add(lblCurso);
		
		txtCurso = new JTextField();
		txtCurso.setEditable(false);
		txtCurso.setText(cursoAModificar[1] + " " + cursoAModificar[2]);
		txtCurso.setBounds(95, 15, 200, 20);
		contentPanel.add(txtCurso);
		txtCurso.setColumns(10);
		
		JLabel lblProfesor = new JLabel("Profesor:");
		lblProfesor.setBounds(25, 40, 70, 20);
		contentPanel.add(lblProfesor);

		respuestaProfesor = ABMCDocentes.getProfesores(true);
		String listaProfesores[] = new String[respuestaProfesor.length];
		int profesorSeleccionado = 0;

		for(int i=0 ; i < respuestaProfesor.length ; i++) {
			
			listaProfesores[i] = respuestaProfesor[i][1] + " " + respuestaProfesor[i][2];
			if(respuestaProfesor[i][0].equals(cursoAModificar[3]))
				profesorSeleccionado = i;
		}
		
		comboBoxProfesor = new JComboBox<String>();
		comboBoxProfesor.setModel(new DefaultComboBoxModel<String>(listaProfesores));
		comboBoxProfesor.setBounds(95, 40, 200, 20);
		comboBoxProfesor.setSelectedIndex(profesorSeleccionado);
		contentPanel.add(comboBoxProfesor);
		
		JLabel lblTurno = new JLabel("Turno:");
		lblTurno.setBounds(25, 65, 70, 20);
		contentPanel.add(lblTurno);
		
		JComboBox<String> comboBoxTurno = new JComboBox<String>();
		comboBoxTurno.setModel(new DefaultComboBoxModel<String>(new String[] {"Mañana", "Tarde", "Noche"}));
		comboBoxTurno.setSelectedItem(cursoAModificar[7]);
		comboBoxTurno.setBounds(95, 65, 120, 20);
		contentPanel.add(comboBoxTurno);
		
		JLabel lblCuota = new JLabel("Valor cuota:");
		lblCuota.setBounds(25, 90, 70, 20);
		contentPanel.add(lblCuota);
		
		txtCuota = new JTextField();
		txtCuota.setColumns(10);
		txtCuota.setText(cursoAModificar[6]);
		txtCuota.setBounds(95, 90, 120, 20);
		contentPanel.add(txtCuota);

		JLabel lblHorario = new JLabel("Horario:");
		lblHorario.setBounds(25, 160, 60, 20);
		contentPanel.add(lblHorario);
		
		JLabel lblDuracion = new JLabel("Duración:");
		lblDuracion.setBounds(25, 180, 60, 20);
		contentPanel.add(lblDuracion);
		
		JLabel lblLunes = new JLabel("Lunes");
		lblLunes.setHorizontalAlignment(SwingConstants.CENTER);
		lblLunes.setBounds(85, 140, 60, 20);
		contentPanel.add(lblLunes);
		
		JLabel lblMartes = new JLabel("Martes");
		lblMartes.setHorizontalAlignment(SwingConstants.CENTER);
		lblMartes.setBounds(145, 140, 60, 20);
		contentPanel.add(lblMartes);
		
		JLabel lblMiercoles = new JLabel("Miércoles");
		lblMiercoles.setHorizontalAlignment(SwingConstants.CENTER);
		lblMiercoles.setBounds(205, 141, 60, 20);
		contentPanel.add(lblMiercoles);
		
		JLabel lblJueves = new JLabel("Jueves");
		lblJueves.setHorizontalAlignment(SwingConstants.CENTER);
		lblJueves.setBounds(265, 141, 60, 20);
		contentPanel.add(lblJueves);
		
		JLabel lblViernes = new JLabel("Viernes");
		lblViernes.setHorizontalAlignment(SwingConstants.CENTER);
		lblViernes.setBounds(325, 141, 60, 20);
		contentPanel.add(lblViernes);
		
		JLabel lblSabado = new JLabel("Sábado");
		lblSabado.setHorizontalAlignment(SwingConstants.CENTER);
		lblSabado.setBounds(385, 141, 60, 20);
		contentPanel.add(lblSabado);
		
		txtHorarioLunes = new JTextField();
		txtHorarioLunes.setHorizontalAlignment(SwingConstants.CENTER);
		txtHorarioLunes.setColumns(10);
		txtHorarioLunes.setBounds(85, 160, 60, 20);
		contentPanel.add(txtHorarioLunes);
		
		txtDuracionLunes = new JTextField();
		txtDuracionLunes.setHorizontalAlignment(SwingConstants.CENTER);
		txtDuracionLunes.setColumns(10);
		txtDuracionLunes.setBounds(85, 180, 60, 20);
		contentPanel.add(txtDuracionLunes);
		
		txtHorarioMartes = new JTextField();
		txtHorarioMartes.setHorizontalAlignment(SwingConstants.CENTER);
		txtHorarioMartes.setColumns(10);
		txtHorarioMartes.setBounds(145, 160, 60, 20);
		contentPanel.add(txtHorarioMartes);
		
		txtDuracionMartes = new JTextField();
		txtDuracionMartes.setHorizontalAlignment(SwingConstants.CENTER);
		txtDuracionMartes.setColumns(10);
		txtDuracionMartes.setBounds(145, 180, 60, 20);
		contentPanel.add(txtDuracionMartes);

		txtHorarioMiercoles = new JTextField();
		txtHorarioMiercoles.setHorizontalAlignment(SwingConstants.CENTER);
		txtHorarioMiercoles.setColumns(10);
		txtHorarioMiercoles.setBounds(205, 160, 60, 20);
		contentPanel.add(txtHorarioMiercoles);
		
		txtDuracionMiercoles = new JTextField();
		txtDuracionMiercoles.setHorizontalAlignment(SwingConstants.CENTER);
		txtDuracionMiercoles.setColumns(10);
		txtDuracionMiercoles.setBounds(205, 180, 60, 20);
		contentPanel.add(txtDuracionMiercoles);

		txtHorarioJueves = new JTextField();
		txtHorarioJueves.setHorizontalAlignment(SwingConstants.CENTER);
		txtHorarioJueves.setColumns(10);
		txtHorarioJueves.setBounds(265, 160, 60, 20);
		contentPanel.add(txtHorarioJueves);
		
		txtDuracionJueves = new JTextField();
		txtDuracionJueves.setHorizontalAlignment(SwingConstants.CENTER);
		txtDuracionJueves.setColumns(10);
		txtDuracionJueves.setBounds(265, 180, 60, 20);
		contentPanel.add(txtDuracionJueves);

		txtHorarioViernes = new JTextField();
		txtHorarioViernes.setHorizontalAlignment(SwingConstants.CENTER);
		txtHorarioViernes.setColumns(10);
		txtHorarioViernes.setBounds(325, 160, 60, 20);
		contentPanel.add(txtHorarioViernes);
		
		txtDuracionViernes = new JTextField();
		txtDuracionViernes.setHorizontalAlignment(SwingConstants.CENTER);
		txtDuracionViernes.setColumns(10);
		txtDuracionViernes.setBounds(325, 180, 60, 20);
		contentPanel.add(txtDuracionViernes);
		
		txtHorarioSabado = new JTextField();
		txtHorarioSabado.setHorizontalAlignment(SwingConstants.CENTER);
		txtHorarioSabado.setColumns(10);
		txtHorarioSabado.setBounds(385, 160, 60, 20);
		contentPanel.add(txtHorarioSabado);
		
		txtDuracionSabado = new JTextField();
		txtDuracionSabado.setHorizontalAlignment(SwingConstants.CENTER);
		txtDuracionSabado.setColumns(10);
		txtDuracionSabado.setBounds(385, 180, 60, 20);
		contentPanel.add(txtDuracionSabado);
		
		idCurso = cursoAModificar[0];
		mostrarDiasCurso();
		
		lblMensageError = new JLabel("");
		lblMensageError.setBounds(25, 205, 421, 25);
		lblMensageError.setBackground(Color.LIGHT_GRAY);
		contentPanel.add(lblMensageError);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String registroModificado[] = { idCurso, respuestaProfesor[comboBoxProfesor.getSelectedIndex()][0], "1", comboBoxTurno.getSelectedItem().toString()};
				boolean bandera = ABMCCurso.actualizarTabla(registroModificado);
				ABMCValorCuota.actualizarCuota(idCurso, txtCuota.getText());
				ABMCDiasCurso.borrarDiasCurso(idCurso);
				String cadena[] = new String[5];
				
				if(txtHorarioLunes.getText().length() != 0 && bandera) {
					
					cadena[0] = "Lunes";
					cadena[1] = txtHorarioLunes.getText();
					cadena[2] = txtDuracionLunes.getText();
					cadena[3] = idCurso;
					bandera = ABMCDiasCurso.crearDiasCurso(cadena);
				}
				if(txtHorarioMartes.getText().length() != 0 && bandera) {
					
					cadena[0] = "Martes";
					cadena[1] = txtHorarioMartes.getText();
					cadena[2] = txtDuracionMartes.getText();
					cadena[3] = idCurso;
					bandera = ABMCDiasCurso.crearDiasCurso(cadena);
				}
				if(txtHorarioMiercoles.getText().length() != 0 && bandera) {
					
					cadena[0] = "Miércoles";
					cadena[1] = txtHorarioMiercoles.getText();
					cadena[2] = txtDuracionMiercoles.getText();
					cadena[3] = idCurso;
					bandera = ABMCDiasCurso.crearDiasCurso(cadena);
				}
				if(txtHorarioJueves.getText().length() != 0 && bandera) {
					
					cadena[0] = "Jueves";
					cadena[1] = txtHorarioJueves.getText();
					cadena[2] = txtDuracionJueves.getText();
					cadena[3] = idCurso;
					bandera = ABMCDiasCurso.crearDiasCurso(cadena);
				}
				if(txtHorarioViernes.getText().length() != 0 && bandera) {
					
					cadena[0] = "Viernes";
					cadena[1] = txtHorarioViernes.getText();
					cadena[2] = txtDuracionViernes.getText();
					cadena[3] = idCurso;
					bandera = ABMCDiasCurso.crearDiasCurso(cadena);
				}
				if(txtHorarioSabado.getText().length() != 0 && bandera) {
					
					cadena[0] = "Sábado";
					cadena[1] = txtHorarioSabado.getText();
					cadena[2] = txtDuracionSabado.getText();
					cadena[3] = idCurso;
					bandera = ABMCDiasCurso.crearDiasCurso(cadena);
				}
				
				if(bandera) {
					
					dispose();
					JOptionPane.showMessageDialog(null, "Se actualizó la información.");
					
					try {
						
						VentanaCursos frame = new VentanaCursos();
						frame.setVisible(true);
					} catch (Exception d) {
						
						d.printStackTrace();
					}
				} else {
					
					lblMensageError.setForeground(Color.RED);
					lblMensageError.setText("Error al guardar.");					
				}
			}
		});
		btnGuardar.setBounds(25, 240, 90, 23);
		contentPanel.add(btnGuardar);
		
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
		btnVolver.setBounds(365, 240, 90, 23);
		contentPanel.add(btnVolver);
		
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String registroModificado[] = { idCurso, cursoAModificar[3], "0", cursoAModificar[7]};
				boolean bandera = ABMCCurso.actualizarTabla(registroModificado);
				if(bandera) {
					
					dispose();
					JOptionPane.showMessageDialog(null, "Se eliminó la información.");
					
					try {
						
						VentanaCursos frame = new VentanaCursos();
						frame.setVisible(true);
					} catch (Exception d) {
						
						d.printStackTrace();
					}
				} else {
					
					lblMensageError.setForeground(Color.RED);
					lblMensageError.setText("Error al guardar.");					
				}
			}
		});
		btnBorrar.setBounds(190, 240, 90, 23);
		contentPanel.add(btnBorrar);
	}
	
	private void mostrarDiasCurso() {

		String resDiasCursado[][] = ABMCDiasCurso.buscarDiasCursado(idCurso);
		int u=0;
		
		while(u < resDiasCursado.length) {
			
			if(resDiasCursado[u][0].equals("Lunes")) {
				
				txtHorarioLunes.setText(resDiasCursado[u][1]);
				txtDuracionLunes.setText(resDiasCursado[u][2]);
			}
			if(resDiasCursado[u][0].equals("Martes")) {
				
				txtHorarioMartes.setText(resDiasCursado[u][1]);
				txtDuracionMartes.setText(resDiasCursado[u][2]);
			}
			if(resDiasCursado[u][0].equals("Miércoles")) {
				
				txtHorarioMiercoles.setText(resDiasCursado[u][1]);
				txtDuracionMiercoles.setText(resDiasCursado[u][2]);
			}
			if(resDiasCursado[u][0].equals("Jueves")) {
				
				txtHorarioJueves.setText(resDiasCursado[u][1]);
				txtDuracionJueves.setText(resDiasCursado[u][2]);
			}
			if(resDiasCursado[u][0].equals("Viernes")) {
				
				txtHorarioViernes.setText(resDiasCursado[u][1]);
				txtDuracionViernes.setText(resDiasCursado[u][2]);
			}
			if(resDiasCursado[u][0].equals("Sábado")) {
				
				txtHorarioSabado.setText(resDiasCursado[u][1]);
				txtDuracionSabado.setText(resDiasCursado[u][2]);
			}
			u++;
		}
	}
}