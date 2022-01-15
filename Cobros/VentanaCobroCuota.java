import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaCobroCuota extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public VentanaCobroCuota() {
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(LECSys.rutaImagenes + "LEC.jpg"));
		setTitle("LECSys - Cobros"+ CheckUsuario.getNombreUsuario());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(10, 20, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		
		JButton btnCobrar = new JButton("Cobrar");
		btnCobrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					VentanaCobrar frame = new VentanaCobrar();
					frame.setVisible(true);
				} catch (Exception f) {
					f.printStackTrace();
				}
			}
		});
		btnCobrar.setBounds(50, 45, 150, 50);
		contentPane.add(btnCobrar);
		
		JButton btnListadoCobros = new JButton("Listado de cobros");
		btnListadoCobros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					VentanaListadoCobros frame = new VentanaListadoCobros();
					frame.setVisible(true);
				} catch (Exception d) {
					d.printStackTrace();
				}
			}
		});
		btnListadoCobros.setBounds(274, 45, 150, 50);
		contentPane.add(btnListadoCobros);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnVolver.setBounds(351, 311, 89, 23);
		contentPane.add(btnVolver);
		
		JButton btnHabilitarYCobrar = new JButton("Habilitar y cobrar");
		btnHabilitarYCobrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					VentanaHabilitarCobrar frame = new VentanaHabilitarCobrar();
					frame.setVisible(true);
				} catch (Exception d) {
					d.printStackTrace();
				}
			}
		});
		btnHabilitarYCobrar.setBounds(50, 151, 150, 50);
		contentPane.add(btnHabilitarYCobrar);
	}
}
