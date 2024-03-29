import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;

public class VentanaIngresoUsuario extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPanel = new JPanel();
	private static JTextField txtUsuario;
	private static JPasswordField txtPassword;

	public VentanaIngresoUsuario() {
		
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(LECSys.rutaImagenes + "LEC.jpg"));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.TOOLKIT_MODAL);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setTitle("LECSys - Login");
		setBounds(350, 200, 419, 220);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		txtUsuario = new JTextField();
		txtUsuario.setBounds(150, 60, 150, 20);
		contentPanel.add(txtUsuario);
		txtUsuario.setColumns(10);
		txtUsuario.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				
				if(txtUsuario.getText().length() >= 20)
					e.consume();
			}
		});
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(74, 63, 58, 14);
		contentPanel.add(lblUsuario);
		
		JLabel lblContraseņa = new JLabel("Password:");
		lblContraseņa.setBounds(74, 91, 76, 14);
		contentPanel.add(lblContraseņa);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(150, 88, 150, 20);
		contentPanel.add(txtPassword);
		
		JLabel txtError = new JLabel("");
		txtError.setHorizontalAlignment(SwingConstants.CENTER);
		txtError.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtError.setForeground(Color.RED);
		txtError.setBounds(10, 24, 383, 25);
		contentPanel.add(txtError);

		JPanel buttonPane = new JPanel();
		
		CheckUsuario login = new CheckUsuario();
		
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton btnOk = new JButton("Aceptar");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int respuesta = login.probarContraseņa();
				if(respuesta < 100) {
					
					dispose();
				} else {
					
					txtUsuario.setText(null);
					txtPassword.setText(null);
					
					if(respuesta == 200) {
						
						txtError.setText("Error al acceder a la base de datos.");
					} else {
					
						txtError.setText("Nombre de usuario o password incorrecto.");
						String cuerpo[] = {CheckUsuario.getIdUsuario(),"Nombre de usuario o cantraseņa no reconocido. " + txtUsuario.getText(),""};
						ABMCActividad.guardoNuevaActividad(cuerpo);
					}
				}
			}
		});
		btnOk.setActionCommand("Aceptar");
		buttonPane.add(btnOk);
		getRootPane().setDefaultButton(btnOk);

		JButton btnSalir = new JButton("Cancelar");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
			}
		});
		btnSalir.setActionCommand("Cancelar");
		buttonPane.add(btnSalir);
	}
	
	public static void nuevoIngreso () {
		
		try {
			
			VentanaIngresoUsuario dialog = new VentanaIngresoUsuario();
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	public static String getUsuario() {
		
		return txtUsuario.getText();
	}
	
	@SuppressWarnings("deprecation")
	public static String getPassword() {
		
		return txtPassword.getText();
	}
}
