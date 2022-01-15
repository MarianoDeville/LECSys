import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JLabel;

public class VentanaConfirmacionFinalización extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public VentanaConfirmacionFinalización() {
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(LECSys.rutaImagenes + "LEC.jpg"));
		setTitle("LECSys - Finalización año."+ CheckUsuario.getNombreUsuario());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(10, 100, 450, 280);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextPane txtpnElMundoFue = new JTextPane();
		txtpnElMundoFue.setForeground(Color.RED);
		txtpnElMundoFue.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtpnElMundoFue.setText("Al realizar esta operaci\u00F3n se borrar\u00E1 la deuda que puedan llegar a tener los alumnos. Se recomienda imprimir la deuda antes proceder con esta operaci\u00F3n.");
		txtpnElMundoFue.setBounds(28, 28, 370, 100);
		contentPane.add(txtpnElMundoFue);
		
		JLabel lblMensageError = new JLabel("");
		lblMensageError.setForeground(Color.RED);
		lblMensageError.setBackground(Color.LIGHT_GRAY);
		lblMensageError.setBounds(28, 139, 370, 25);
		contentPane.add(lblMensageError);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
			}
		});
		btnCancelar.setBounds(252, 199, 89, 23);
		contentPane.add(btnCancelar);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				if(ABMCAlumnos.resetEstado()) {
					
					dispose();
					JOptionPane.showMessageDialog(null, "Operación realida.");	
				} else {

					lblMensageError.setText("Hubo un problema y no se pudo realizar la operación..");
				}
			}
		});
		btnAceptar.setBounds(57, 199, 89, 23);
		contentPane.add(btnAceptar);
	}
}
