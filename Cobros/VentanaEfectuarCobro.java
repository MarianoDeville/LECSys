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
	private JTextField txtRecargo;
	private JTextField txtCalculoMontoPagar;
	private JLabel lblMensageError;
	private JComboBox<String> comboBoxCantCuotas;
	private int montoCalculado;
	private int valorCuotas;
	private int descuento;
	private boolean debe;
	private int recargo;
	private Calendar fechaSistema = new GregorianCalendar();
	private JTextField txtDescEfectivo;
	
	
	public VentanaEfectuarCobro(String informacion[]) {

		setIconImage(Toolkit.getDefaultToolkit().getImage(LECSys.rutaImagenes + "LEC.jpg"));
		setTitle("LECSys - Cobrar"+ CheckUsuario.getNombreUsuario());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 390);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);

		valorCuotas = Integer.parseInt(informacion[4]);
		descuento = Integer.parseInt(informacion[5]) * valorCuotas /100;
		informacion[5] = descuento + "";

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
		
		JTextField txtIntegrantes = new JTextField(informacion[2]);
		txtIntegrantes.setEditable(false);
		txtIntegrantes.setBounds(155, 85, 40, 20);
		contentPane.add(txtIntegrantes);
		txtIntegrantes.setColumns(10);
		
		JLabel lblValorCuota = new JLabel("Valor cuota:");
		lblValorCuota.setBounds(25, 110, 130, 20);
		contentPane.add(lblValorCuota);
		
		JTextField txtValorCuota = new JTextField(informacion[4]);
		txtValorCuota.setEditable(false);
		txtValorCuota.setBounds(155, 110, 100, 20);
		contentPane.add(txtValorCuota);
		txtValorCuota.setColumns(10);
		
		JLabel lblDescuento = new JLabel("Descuento grupo:");
		lblDescuento.setBounds(25, 135, 130, 20);
		contentPane.add(lblDescuento);
		
		JTextField txtDescuento = new JTextField(informacion[5]);
		txtDescuento.setEditable(false);
		txtDescuento.setBounds(155, 135, 100, 20);
		contentPane.add(txtDescuento);
		txtDescuento.setColumns(10);
		
		JLabel lblRecargo = new JLabel("Recargo:");
		lblRecargo.setBounds(25, 160, 130, 20);
		contentPane.add(lblRecargo);
		
		txtRecargo = new JTextField();
		txtRecargo.setText("10");
		txtRecargo.setBounds(155, 160, 60, 20);
		txtRecargo.addActionListener(new java.awt.event.ActionListener() {
			
			public void actionPerformed(java.awt.event.ActionEvent e) {
				
				actualizoMonto();
			}
		});
		txtRecargo.setColumns(10);
		contentPane.add(txtRecargo);
		
		JLabel lblSigno = new JLabel("%");
		lblSigno.setBounds(220, 160, 30, 20);
		contentPane.add(lblSigno);
		
		JLabel lblDescPEfectivo = new JLabel("Desc. pago efectivo:");
		lblDescPEfectivo.setBounds(25, 185, 130, 20);
		contentPane.add(lblDescPEfectivo);
		
		txtDescEfectivo = new JTextField((String) null);
		txtDescEfectivo.setEditable(true);
		txtDescEfectivo.setColumns(10);
		txtDescEfectivo.setBounds(155, 185, 100, 20);
		txtDescEfectivo.addActionListener(new java.awt.event.ActionListener() {
			
			public void actionPerformed(java.awt.event.ActionEvent e) {
				
				actualizoMonto();
			}
		});
		contentPane.add(txtDescEfectivo);
				
		JLabel lblMontoPagar = new JLabel("Monto a pagar:");
		lblMontoPagar.setBounds(25, 210, 130, 20);
		contentPane.add(lblMontoPagar);
		
		txtCalculoMontoPagar = new JTextField();
		txtCalculoMontoPagar.setEditable(false);
		txtCalculoMontoPagar.setBounds(155, 210, 100, 20);
		contentPane.add(txtCalculoMontoPagar);
		txtCalculoMontoPagar.setColumns(10);

		JLabel lblFactura = new JLabel("Factura:");
		lblFactura.setBounds(25, 235, 130, 20);
		contentPane.add(lblFactura);
		
		JTextField txtFactura = new JTextField();
		txtFactura.setBounds(155, 235, 100, 20);
		contentPane.add(txtFactura);
		txtFactura.setColumns(10);

		lblMensageError = new JLabel("");
		lblMensageError.setForeground(Color.RED);
		lblMensageError.setBackground(Color.LIGHT_GRAY);
		lblMensageError.setBounds(25, 270, 350, 25);
		contentPane.add(lblMensageError);
		
		JButton btnCobrar = new JButton("Cobrar");
		btnCobrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(!txtCalculoMontoPagar.getText().contentEquals("")) {

					if(txtDescEfectivo.getText().contentEquals("")  || isNumeric(txtDescEfectivo.getText())) {
						
						String cuerpo[] = new String [10];
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
						cuerpo[9] = recargo + "";
	
						if(ABMCCobros.nuevoCobro(cuerpo)) {
	
							ABMCGrupoFamiliar.modificarMeses(informacion[0], "-", comboBoxCantCuotas.getSelectedIndex()+"");
							dispose();
							
							try {
								
								VentanaCobrarCuota frame1 = new VentanaCobrarCuota();
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
						
						lblMensageError.setText("El descuento debe ser numérico.");	
					}
						
				} else {
					
					lblMensageError.setText("Falta información para efectuar el cobro.");
				}
			}
		});
		btnCobrar.setBounds(25, 305, 89, 23);
		contentPane.add(btnCobrar);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
				
				try {
					
					VentanaCobrarCuota frame = new VentanaCobrarCuota();
					frame.setVisible(true);
				} catch (Exception f) {
					
					f.printStackTrace();
				}
			}
		});
		btnVolver.setBounds(246, 305, 89, 23);
		contentPane.add(btnVolver);
	}
	
	private String [] listadoDeuda(int cantMeses) {
		
		if(cantMeses > 1)

			debe = true;
		else
			
			debe = false;
		
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
	
	private void actualizoMonto() {
		
		int cantMeses = comboBoxCantCuotas.getSelectedIndex();
		int totalMeses = comboBoxCantCuotas.getItemCount();
       	montoCalculado = (valorCuotas - descuento);
       	recargo = 0;
 
       	if(debe) {
       		
       		recargo = montoCalculado * Integer.parseInt(txtRecargo.getText()) / 100;
       		
       		if(cantMeses == 1) {
       			
       			montoCalculado += recargo;
       			montoCalculado *= cantMeses;
       		} else {
       			
       			montoCalculado *= cantMeses;
       			
       			if(cantMeses == (totalMeses - 1))
       				
       				montoCalculado += (recargo * (cantMeses - 1));
       			else
       				
       				montoCalculado += (recargo * cantMeses);
       		}
       	} else
       		
       		montoCalculado *= cantMeses;
       	
       	if(!txtDescEfectivo.getText().contentEquals("") 
       		&& isNumeric(txtDescEfectivo.getText())) {
       		
       		montoCalculado -= Integer.parseInt(txtDescEfectivo.getText());
       	}
    	txtCalculoMontoPagar.setText(montoCalculado+"");
    	lblMensageError.setText(null);
	}
	
	private static boolean isNumeric(String cadena) {
		
		try {
			
			Double.parseDouble(cadena);
			return true;
		} catch (NumberFormatException e) {
			
			return false;
		}
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource()==comboBoxCantCuotas) {
		
			actualizoMonto();
		}
	}
}
