import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;

public class VentanaReciboCobro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnVolver;
	private JButton btnImprimir;
	private JLabel lblFecha;
	private JLabel lblNombre;
	private JLabel lblConcepto;
	private JLabel lblMontoTotal;
	private JLabel lblLearningEnglishCentre_1;
	private JLabel lblEnglishInstitute;


	public VentanaReciboCobro(String cuerpo[]) {
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(LECSys.rutaImagenes + "LEC.jpg"));
		setTitle("LECSys - Imprimir recibo"+ CheckUsuario.getNombreUsuario());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 590, 504);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setResizable(false);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnVolver = new JButton("Cerrar");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
			}
		});
		btnVolver.setBounds(340, 417, 89, 23);
		contentPane.add(btnVolver);
		
		btnImprimir = new JButton("Imprimir");
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				btnVolver.setVisible(false);
				btnImprimir.setVisible(false);
				PrinterJob imprimir = PrinterJob.getPrinterJob();
				PageFormat preformat = imprimir.defaultPage();
				PageFormat postformat = imprimir.pageDialog(preformat);
				imprimir.setPrintable(new Printer(contentPane), postformat);
								
				if (imprimir.printDialog()) {
					
					try {
							imprimir.print();
					} catch (PrinterException d) {
						
							JOptionPane.showMessageDialog(null, "Error al intentar imprimir.");
					}
				}
				dispose();
			}
		});
		btnImprimir.setBounds(130, 417, 89, 23);
		contentPane.add(btnImprimir);
		
		JLabel lblTitulo = new JLabel("RECIBO");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTitulo.setBounds(340, 15, 200, 25);
		contentPane.add(lblTitulo);
		
		lblFecha = new JLabel("");
		lblFecha.setHorizontalAlignment(SwingConstants.CENTER);
		lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFecha.setBounds(340, 70, 200, 25);
		lblFecha.setText("Fecha: " + cuerpo[3] + "/" + cuerpo[4] + "/" + cuerpo[5]);
		contentPane.add(lblFecha);
		
		lblNombre = new JLabel("");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNombre.setBounds(25, 250, 373, 25);
		lblNombre.setText("Recibí de " + cuerpo[1]);
		contentPane.add(lblNombre);
		
		lblConcepto = new JLabel("");
		lblConcepto.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblConcepto.setBounds(25, 275, 373, 25);
		lblConcepto.setText("En concepto de: " + cuerpo[2]);
		contentPane.add(lblConcepto);
		
		lblMontoTotal = new JLabel("");
		lblMontoTotal.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblMontoTotal.setBounds(25, 300, 405, 25);
		lblMontoTotal.setText("La suma de pesos " + cuerpo[7]);
		contentPane.add(lblMontoTotal);
		
		JLabel lblNro = new JLabel("");
		lblNro.setHorizontalAlignment(SwingConstants.CENTER);
		lblNro.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNro.setBounds(340, 40, 200, 25);
		lblNro.setText("Nro.: " + numeroRecibo());
		contentPane.add(lblNro);
		
		lblLearningEnglishCentre_1 = new JLabel("LEARNING ENGLISH CENTRE");
		lblLearningEnglishCentre_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblLearningEnglishCentre_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblLearningEnglishCentre_1.setBounds(25, 15, 250, 25);
		contentPane.add(lblLearningEnglishCentre_1);
		
		lblEnglishInstitute = new JLabel("ENGLISH INSTITUTE");
		lblEnglishInstitute.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnglishInstitute.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEnglishInstitute.setBounds(25, 40, 250, 25);
		contentPane.add(lblEnglishInstitute);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setIcon(new ImageIcon(LECSys.rutaImagenes + "LEC - Min.jpg"));
		btnNewButton.setBounds(40, 73, 150, 150);
		contentPane.add(btnNewButton);
	}
	
	private String numeroRecibo() {
		
		int registro = ABMCCobros.getUltimoId()+1; 
		String respuesta = String.format("%012d" , registro);
		StringBuilder sb = new StringBuilder(respuesta);
		sb.insert(4, '-');
		return sb.toString();
	}
}
