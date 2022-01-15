import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class VentanaNuevoUsuario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUserName;
	private JPasswordField pwdRePassword;
	private JPasswordField pwdPassword;
	private JComboBox<String> comboBoxAcceso = new JComboBox<String>();
	private JLabel lblMensageError = new JLabel("");
	ABMCConfiguracion usuario = new ABMCConfiguracion();

	public VentanaNuevoUsuario() {
		setResizable(false);
		setTitle("LECSys - Agregar usuario."+ CheckUsuario.getNombreUsuario());
		setIconImage(Toolkit.getDefaultToolkit().getImage(LECSys.rutaImagenes + "LEC.jpg"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(10, 20, 370, 260);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnBack = new JButton("Volver");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
				try {
					VentanaUsuarios frame = new VentanaUsuarios();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnBack.setBounds(230, 180, 89, 23);
		contentPane.add(btnBack);
		
		JButton btnAdd = new JButton("Agregar");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(checkCampos())
				{
					String acceso = (comboBoxAcceso.getSelectedIndex()+1)+"";

					@SuppressWarnings("deprecation")
					String infoUsuario[] = {txtUserName.getText(),
											pwdPassword.getText(),
											acceso};
					limpiarCampos();
					if(usuario.crearUsuario(infoUsuario))
					{
						lblMensageError.setForeground(Color.BLUE);
						lblMensageError.setText("Usuario agregado.");				
					} else {
						lblMensageError.setForeground(Color.RED);
						lblMensageError.setText("Error al agregar el usuario.");
					}
				}
			}
		});
		btnAdd.setBounds(54, 180, 89, 23);
		contentPane.add(btnAdd);
		
		JLabel lblUserName = new JLabel("Nombre de usuario:");
		lblUserName.setBounds(20, 15, 120, 20);
		contentPane.add(lblUserName);
		
		JLabel lblPassword = new JLabel("Contraseña:");
		lblPassword.setBounds(20, 40, 120, 20);
		contentPane.add(lblPassword);
		
		comboBoxAcceso.setModel(new DefaultComboBoxModel<String>(new String[] {"Administrator (full acces)", "Secretary (Data upload)", "Teacher (Take assistence)"}));
		comboBoxAcceso.setBounds(140, 90, 194, 20);
		contentPane.add(comboBoxAcceso);
		
		JLabel lblAccesLevel = new JLabel("Nivel de acceso:");
		lblAccesLevel.setBounds(20, 90, 100, 20);
		contentPane.add(lblAccesLevel);
		
		txtUserName = new JTextField();
		txtUserName.setBounds(140, 15, 140, 20);
		contentPane.add(txtUserName);
		txtUserName.setColumns(10);
		
		pwdRePassword = new JPasswordField();
		pwdRePassword.setBounds(140, 65, 140, 20);
		contentPane.add(pwdRePassword);
		
		pwdPassword = new JPasswordField();
		pwdPassword.setBounds(140, 40, 140, 20);
		contentPane.add(pwdPassword);
		
		lblMensageError.setForeground(Color.RED);
		lblMensageError.setBackground(Color.LIGHT_GRAY);
		lblMensageError.setBounds(20, 130, 314, 25);
		contentPane.add(lblMensageError);
	}

	private void limpiarCampos() {
		txtUserName.setText(null);
		pwdPassword.setText(null);
		pwdRePassword.setText(null);
		lblMensageError.setText(null);
	}
	
	@SuppressWarnings("deprecation")
	private boolean checkCampos() {
	
		boolean bandera = true;
		String respuesta[] = usuario.buscarUsuario("NOMBRE", txtUserName.getText());

		if(txtUserName.getText().length() < 3 || pwdPassword.getText().length() < 3) {
			lblMensageError.setForeground(Color.RED);
			lblMensageError.setText("Nombre de usuario o contraseña muy corto.");
			bandera = false;			
		} else if(!pwdPassword.getText().contentEquals(pwdRePassword.getText())) {
			pwdPassword.setText(null);
			pwdRePassword.setText(null);
			lblMensageError.setForeground(Color.RED);
			lblMensageError.setText("Error en la contraseña.");
			bandera = false;
		}
		
		try {
			
			if(respuesta[1].contentEquals(txtUserName.getText())) {
				lblMensageError.setForeground(Color.RED);
				lblMensageError.setText("El nombre de usuario ya está en uso.");
				pwdPassword.setText(null);
				pwdRePassword.setText(null);
				bandera = false;			
			} 
			
		} catch (NullPointerException e) {

		}

		return bandera;
	}
}
