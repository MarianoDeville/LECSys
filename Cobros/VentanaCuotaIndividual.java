import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

public class VentanaCuotaIndividual extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblMensageError;
	private JTextField txtValorInscripción;
	private int inscripcion = 0;
	private int descuento = 0;
	private int totalCuota = 0;
	private Calendar fechaSistema = new GregorianCalendar();
	private JTextField txtDescEfectivo;

	public VentanaCuotaIndividual(String lista[][]) {
		
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(LECSys.rutaImagenes + "LEC.jpg"));
		setTitle("LECSys - Cobrar y habilitar cursado."+ CheckUsuario.getNombreUsuario());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 548, 330);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		totalCuota += Integer.parseInt(lista[0][5]);
		
		lblMensageError = new JLabel("");
		lblMensageError.setBounds(30, 175, 482, 25);
		lblMensageError.setForeground(Color.RED);
		lblMensageError.setBackground(Color.LIGHT_GRAY);
		contentPane.add(lblMensageError);
		
		JLabel lblNombreYApellido = new JLabel("Nombre y apellido:");
		lblNombreYApellido.setBounds(50, 45, 120, 20);
		contentPane.add(lblNombreYApellido);
		
		JTextField txtNombreApellido = new JTextField();
		txtNombreApellido.setEditable(false);
		txtNombreApellido.setBounds(170, 45, 200, 20);
		contentPane.add(txtNombreApellido);
		txtNombreApellido.setText(lista[0][1] + " " + lista[0][2]);
		txtNombreApellido.setColumns(10);
		
		JLabel lblTotalAPagar = new JLabel("Total a pagar:");
		lblTotalAPagar.setBounds(50, 120, 120, 20);
		contentPane.add(lblTotalAPagar);
		
		JTextField txtTotalPagar = new JTextField();
		txtTotalPagar.setEditable(false);
		txtTotalPagar.setColumns(10);
		txtTotalPagar.setBounds(170, 120, 110, 20);
		txtTotalPagar.setText(totalCuota + "");
		contentPane.add(txtTotalPagar);
		
		JLabel lblInscripción = new JLabel("Inscripción");
		lblInscripción.setBounds(50, 70, 120, 20);
		contentPane.add(lblInscripción);
		
		txtValorInscripción = new JTextField();
		txtValorInscripción.setColumns(10);
		txtValorInscripción.setBounds(170, 70, 70, 20);
		txtValorInscripción.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
            	
            	inscripcion = Integer.parseInt(txtValorInscripción.getText());
            	txtTotalPagar.setText((inscripcion + totalCuota - descuento) + "");
            }});
		contentPane.add(txtValorInscripción);
		
		JLabel lblDescPEfectivo = new JLabel("Desc. pago efectivo:");
		lblDescPEfectivo.setBounds(50, 95, 120, 20);
		contentPane.add(lblDescPEfectivo);
		
		txtDescEfectivo = new JTextField((String) null);
		txtDescEfectivo.setEditable(true);
		txtDescEfectivo.setColumns(10);
		txtDescEfectivo.setBounds(170, 95, 70, 20);
		txtDescEfectivo.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
            	
            	inscripcion = Integer.parseInt(txtValorInscripción.getText());
            	descuento = Integer.parseInt(txtDescEfectivo.getText());
            	txtTotalPagar.setText((inscripcion + totalCuota - descuento) + "");
            }});
		contentPane.add(txtDescEfectivo);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
				
				try {
					
					VentanaHabilitarCobrar frame = new VentanaHabilitarCobrar();
					frame.setVisible(true);
				} catch (Exception d) {
					
					d.printStackTrace();
				}
			}
		});
		btnVolver.setBounds(301, 230, 140, 23);
		contentPane.add(btnVolver);
		
		JButton btnCobrarYHabilitar = new JButton("Cobrar y habilitar");
		btnCobrarYHabilitar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(checkCampos()) {

					String datosAlumno[] = ABMCAlumnos. buscarAlumno("ID", lista[0][0]);
					int idGrupoFamiliar = Integer.parseInt(datosAlumno[10]);
					txtTotalPagar.setText((inscripcion + totalCuota - descuento) + "");
					
					if(idGrupoFamiliar == 0) {
						
						String familia[] = {lista[0][2] + ", " + lista[0][1],"1","0","1","0"};
						idGrupoFamiliar = ABMCGrupoFamiliar.crearGrupoFamilia(familia);						
					}
					
					if(idGrupoFamiliar > 0) {
						
						ABMCAlumnos.actualizarGrupoFamiliar(lista[0][0], idGrupoFamiliar + "");
						String cuerpo[] = new String [10];
						cuerpo[0] = idGrupoFamiliar + "";
						cuerpo[1] = lista[0][1] + " " + lista[0][2];
						cuerpo[2] = "Inscripción : " + inscripcion + " primer cuota: " + totalCuota + " descuento pago contado: " + descuento;
						cuerpo[3] = fechaSistema.get(Calendar.DAY_OF_MONTH)+"";
						cuerpo[4] = (fechaSistema.get(Calendar.MONTH)+1)+"";
						cuerpo[5] = fechaSistema.get(Calendar.YEAR)+"";
						cuerpo[6] = fechaSistema.get(Calendar.HOUR) +":" +fechaSistema.get(Calendar.MINUTE);
						cuerpo[7] = txtTotalPagar.getText();
						cuerpo[8] = "";
						cuerpo[9] = "0";
						ABMCCobros.nuevoCobro(cuerpo);
						
						try {
							
							VentanaHabilitarCobrar frame = new VentanaHabilitarCobrar();
							frame.setVisible(true);
							VentanaReciboCobro frame1 = new VentanaReciboCobro(cuerpo);
							frame1.setVisible(true);
						} catch (Exception d) {
						
							d.printStackTrace();
						}
						dispose();
					} else {
						
						lblMensageError.setForeground(Color.RED);
						lblMensageError.setText("Error al intentar guardar el registro.");
					}
				}
			}
		});
		btnCobrarYHabilitar.setBounds(50, 230, 140, 23);
		contentPane.add(btnCobrarYHabilitar);
	}
	
	private boolean checkCampos() {
		
		boolean bandera = true;
		lblMensageError.setForeground(Color.RED);
		
		try {
			
			inscripcion = Integer.parseInt(txtValorInscripción.getText());
			descuento = Integer.parseInt(txtDescEfectivo.getText());
			
		}catch (Exception f) {
			
			bandera = false;
			lblMensageError.setText("Los campos Inscripción y descuanto no puede estar vacío y debe ser un número");
		}
		return bandera;
	}
}
