import java.awt.Toolkit;
import java.awt.Dialog.ModalExclusionType;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class VentanaCumpleaños extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	public VentanaCumpleaños(String listado[][]) {
		
		setResizable(false);
		setTitle("LECSys - Listado cumpleaños."+ CheckUsuario.getNombreUsuario());
		setIconImage(Toolkit.getDefaultToolkit().getImage(LECSys.rutaImagenes + "LEC.jpg"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setBounds(10, 20, 400, 330);
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("CheckBox.background"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitulo = new JLabel("En el día de hoy cumplen años:");
		lblTitulo.setBounds(10, 22, 352, 23);
		contentPane.add(lblTitulo);
		
		String titulo [] = {"Nombre", "Apellido"};
		
		JScrollPane scrollTabla = new JScrollPane();
		scrollTabla.setBounds(10, 56, 364, 168);
		contentPane.add(scrollTabla);
		JTable tablaCumpleaños = new JTable(new DefaultTableModel(listado, titulo));
		scrollTabla.setViewportView(tablaCumpleaños);
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
			}
		});
		btnCerrar.setBounds(142, 245, 90, 23);
		contentPane.add(btnCerrar);
	}
}
