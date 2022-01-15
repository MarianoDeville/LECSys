import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;

public class VentanaEditarUsuario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPasswordField pwdRePassword;
	private JPasswordField pwdPassword;
	private JTextField txtUserName;
	private JComboBox<String> comboBoxAccessLevel = new JComboBox<String>();
	private JLabel lblMensageError = new JLabel("");
	private ABMCConfiguracion usuario = new ABMCConfiguracion();


	public VentanaEditarUsuario(String idUsuario) {
		setTitle("LECSys - Editar usuarios."+ CheckUsuario.getNombreUsuario());
		setIconImage(Toolkit.getDefaultToolkit().getImage(LECSys.rutaImagenes + "LEC.jpg"));
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(10, 20, 380, 270);
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
		btnBack.setBounds(261, 194, 89, 23);
		contentPane.add(btnBack);
		
		JButton btnSave = new JButton("Guardar");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(checkCampos())
				{
					String acceso = (comboBoxAccessLevel.getSelectedIndex()+1)+"";
					@SuppressWarnings("deprecation")
					String infoUsuario[] = {idUsuario,
											pwdPassword.getText(),
											acceso};
					
					if(usuario.actualizarUsuario(infoUsuario))
					{
						lblMensageError.setForeground(Color.BLUE);
						lblMensageError.setText("Usuario guardado.");				
					} else {
						lblMensageError.setForeground(Color.RED);
						lblMensageError.setText("Error al guardar la información.");
					}
				}
			}
		});
		btnSave.setBounds(20, 194, 89, 23);
		contentPane.add(btnSave);
		
		JButton btnDelete = new JButton("Borrar");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(usuario.borrarUsuario(idUsuario)) {
					dispose();
					try {
						VentanaUsuarios frame = new VentanaUsuarios();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					lblMensageError.setForeground(Color.RED);
					lblMensageError.setText("Error al borrar el usuario.");
				}
			}
		});
		btnDelete.setBounds(138, 194, 89, 23);
		contentPane.add(btnDelete);
		
		lblMensageError.setForeground(Color.RED);
		lblMensageError.setBackground(Color.LIGHT_GRAY);
		lblMensageError.setBounds(20, 140, 330, 25);
		contentPane.add(lblMensageError);
		
		JLabel lblUserName = new JLabel("Nombre de usuario:");
		lblUserName.setBounds(20, 23, 120, 20);
		contentPane.add(lblUserName);
		
		JLabel lblPassword = new JLabel("Contraseña:");
		lblPassword.setBounds(20, 48, 120, 20);
		contentPane.add(lblPassword);
		
		JLabel lblAccessLevel = new JLabel("Nivel de acceso:");
		lblAccessLevel.setBounds(20, 98, 120, 20);
		contentPane.add(lblAccessLevel);
		
		comboBoxAccessLevel.setModel(new DefaultComboBoxModel<String>(new String[] {"Administrador (full acces)", "Secretary (Data upload)", "Teacher (Take assistence)"}));
		comboBoxAccessLevel.setBounds(140, 98, 220, 20);
		contentPane.add(comboBoxAccessLevel);
		
		pwdRePassword = new JPasswordField();
		pwdRePassword.setBounds(140, 73, 140, 20);
		contentPane.add(pwdRePassword);
		
		pwdPassword = new JPasswordField();
		pwdPassword.setBounds(140, 48, 140, 20);
		contentPane.add(pwdPassword);
		
		txtUserName = new JTextField();
		txtUserName.setEditable(false);
		txtUserName.setColumns(10);
		txtUserName.setBounds(140, 23, 140, 20);
		contentPane.add(txtUserName);
		
		ABMCConfiguracion usuario = new ABMCConfiguracion();
		String respuesta [] = usuario.buscarUsuario("IDUSUARIO", idUsuario);
		txtUserName.setText(respuesta[1]);
		pwdPassword.setText(respuesta[2]);
		comboBoxAccessLevel.setSelectedIndex(Integer.parseUnsignedInt(respuesta[3])-1);
	}
	
	@SuppressWarnings("deprecation")
	private boolean checkCampos() {
		boolean bandera = true;

		if(txtUserName.getText().length() < 3 || pwdPassword.getText().length() < 3)
		{
			lblMensageError.setForeground(Color.RED);
			lblMensageError.setText("Nombre de usuario muy corto.");
			bandera = false;			
		}
		
		if(!pwdPassword.getText().contentEquals(pwdRePassword.getText()))
		{
			pwdPassword.setText(null);
			pwdRePassword.setText(null);
			lblMensageError.setForeground(Color.RED);
			lblMensageError.setText("Error en la contraseña.");
			bandera = false;
		}
		return bandera;
	}
}
