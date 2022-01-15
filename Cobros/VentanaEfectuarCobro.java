import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;

public class VentanaEfectuarCobro extends JFrame implements ItemListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtIntegrantes;
	private JTextField txtCalculoMontoPagar;
	private JTextField txtValorCuota;
	private JTextField txtFactura;
	private JLabel lblMensageError;
	private JComboBox<String> comboBoxCantCuotas;
	private int montoCalculado;
	private int valorCuotas;
	private int descuento;
	private Calendar fechaSistema = new GregorianCalendar();
	private JTextField txtDescuento;
	
	public VentanaEfectuarCobro(String informacion[]) {

		setIconImage(Toolkit.getDefaultToolkit().getImage(LECSys.rutaImagenes + "LEC.jpg"));
		setTitle("LECSys - Cobrar"+ CheckUsuario.getNombreUsuario());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 370);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);

		valorCuotas = Integer.parseInt(informacion[4]);
		descuento = Integer.parseInt(informacion[5]) * valorCuotas /100;
		informacion[5] = descuento + "";

		JButton btnCobrar = new JButton("Cobrar");
		btnCobrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(!txtCalculoMontoPagar.getText().contentEquals("")) {

					String cuerpo[] = new String [9];
					cuerpo[0] = informacion[0];
					cuerpo[1] = informacion[1];
					cuerpo[2] = (String) comboBoxCantCuotas.getSelectedItem();
					cuerpo[3] = fechaSistema.get(Calendar.DAY_OF_MONTH)+"";
					cuerpo[4] = (fechaSistema.get(Calendar.MONTH)+1)+"";
					cuerpo[5] = fechaSistema.get(Calendar.YEAR)+"";
					cuerpo[6] = (fechaSistema.get(Calendar.AM_PM)==0? fechaSistema.get(Calendar.HOUR):fechaSistema.get(Calendar.HOUR)+12) +"" ;
					cuerpo[6] += ":" +fechaSistema.get(Calendar.MINUTE);
					cuerpo[7] = txtCalculoMontoPagar.getText();
					cuerpo[8] = txtFactura.getText();

					if(ABMCCobros.nuevoCobro(cuerpo)) {

						ABMCGrupoFamiliar.modificarMeses(informacion[0], "-", comboBoxCantCuotas.getSelectedIndex()+"");
						dispose();
						
						try {
							VentanaCobrar frame1 = new VentanaCobrar();
							frame1.setVisible(true);
							VentanaReciboCobro frame2 = new VentanaReciboCobro(cuerpo);
							frame2.setVisible(true);
						} catch (Exception f) {
							f.printStackTrace();
						}

					
					} else {
						lblMensageError.setText("Error al intentar guardar en la base de datos.");
					}
						
				} else {
					lblMensageError.setText("Falta información para efectuar el cobro.");
				}
			}
		});
		btnCobrar.setBounds(25, 270, 89, 23);
		contentPane.add(btnCobrar);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
				
				try {
					VentanaCobrar frame = new VentanaCobrar();
					frame.setVisible(true);
				} catch (Exception f) {
					f.printStackTrace();
				}
			}
		});
		btnVolver.setBounds(246, 270, 89, 23);
		contentPane.add(btnVolver);
		
		JLabel lblNombre = new JLabel(informacion[1]);
		lblNombre.setBounds(25, 25, 350, 25);
		contentPane.add(lblNombre);
		
		JLabel lblCantidadDeCuotas = new JLabel("Concepto:");
		lblCantidadDeCuotas.setBounds(25, 60, 130, 20);
		contentPane.add(lblCantidadDeCuotas);

		comboBoxCantCuotas = new JComboBox<String>();
		comboBoxCantCuotas.setModel(new DefaultComboBoxModel<String>(listadoDeuda(Integer.parseInt(informacion[3]))));
		comboBoxCantCuotas.setBounds(155, 60, 180, 20);
		comboBoxCantCuotas.addItemListener(this);
		contentPane.add(comboBoxCantCuotas);
		
		JLabel lblCantIntegrantes = new JLabel("Cantidad integrantes:");
		lblCantIntegrantes.setBounds(25, 85, 130, 20);
		contentPane.add(lblCantIntegrantes);
		
		txtIntegrantes = new JTextField(informacion[2]);
		txtIntegrantes.setEditable(false);
		txtIntegrantes.setBounds(155, 85, 40, 20);
		contentPane.add(txtIntegrantes);
		txtIntegrantes.setColumns(10);
		
		JLabel lblMontoPagar = new JLabel("Monto a pagar:");
		lblMontoPagar.setBounds(25, 160, 130, 20);
		contentPane.add(lblMontoPagar);
		
		txtCalculoMontoPagar = new JTextField();
		txtCalculoMontoPagar.setEditable(false);
		txtCalculoMontoPagar.setBounds(155, 160, 100, 20);
		contentPane.add(txtCalculoMontoPagar);
		txtCalculoMontoPagar.setColumns(10);
				
		lblMensageError = new JLabel("");
		lblMensageError.setForeground(Color.RED);
		lblMensageError.setBackground(Color.LIGHT_GRAY);
		lblMensageError.setBounds(25, 235, 350, 25);
		contentPane.add(lblMensageError);
		
		JLabel lblValorCuota = new JLabel("Valor cuota:");
		lblValorCuota.setBounds(25, 110, 130, 20);
		contentPane.add(lblValorCuota);
		
		txtValorCuota = new JTextField(informacion[4]);
		txtValorCuota.setEditable(false);
		txtValorCuota.setBounds(155, 110, 100, 20);
		contentPane.add(txtValorCuota);
		txtValorCuota.setColumns(10);
		
		JLabel lblFactura = new JLabel("Factura:");
		lblFactura.setBounds(25, 185, 130, 20);
		contentPane.add(lblFactura);
		
		txtFactura = new JTextField();
		txtFactura.setBounds(155, 185, 100, 20);
		contentPane.add(txtFactura);
		txtFactura.setColumns(10);
		
		JLabel lblDescuento = new JLabel("Descuento");
		lblDescuento.setBounds(25, 135, 130, 20);
		contentPane.add(lblDescuento);
		
		txtDescuento = new JTextField(informacion[5]);
		txtDescuento.setEditable(false);
		txtDescuento.setBounds(155, 135, 100, 20);
		contentPane.add(txtDescuento);
		txtDescuento.setColumns(10);
	}
	
	private String [] listadoDeuda(int cantMeses) {
		
		String meses[]= {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
		String listaCuotasDeuda[] = new String[cantMeses + 1];
		listaCuotasDeuda[0] = "Seleccione uno";

		if(listaCuotasDeuda.length > 1) {

			if(fechaSistema.get(Calendar.MONTH)+1 >= cantMeses) {

				listaCuotasDeuda[1] = meses[fechaSistema.get(Calendar.MONTH)-cantMeses+1];
				
				for(int aux = 2; aux < listaCuotasDeuda.length; aux++) {
					
					for(int i = 0; i < aux; i++) {
					
						if(i == 0)
							listaCuotasDeuda[aux] = meses[fechaSistema.get(Calendar.MONTH)-cantMeses+1];
						else
							listaCuotasDeuda[aux] += " + " + meses[fechaSistema.get(Calendar.MONTH)+1+i-cantMeses];
					}
				}
			} else {
			
				for(int aux = 1; aux < listaCuotasDeuda.length; aux++) {
					
					String temp = (aux == 1)? " mes.":" meses."; 	
					listaCuotasDeuda[aux] =aux + temp;
				}
			}
		}
		return listaCuotasDeuda;
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource()==comboBoxCantCuotas) {
		
	       	int cantMeses = comboBoxCantCuotas.getSelectedIndex();
	       	montoCalculado = (valorCuotas - descuento) * cantMeses;
	    	txtCalculoMontoPagar.setText(montoCalculado+"");
	    	lblMensageError.setText(null);
		}
	}
}
