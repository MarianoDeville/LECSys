import java.awt.EventQueue;
import javax.swing.JOptionPane;

/********************************************************************************/
/*			Sistema de gesti�n offline para academias - LECSys					*/
/*------------------------------------------------------------------------------*/
/*		Revisi�n:				1.06											*/
/*		IDE:					Eclipse IDE Ver. 2021-12 (4.22.0).				*/
/*		Lenguaje:				Java SE-1.8										*/
/*		Versionado:				git - github.com								*/
/*		Base de Datos:			MySQL Workbench 8.00 CE							*/
/*		Plugin:					WindowBuilder 1.9.7								*/
/*								UMLet 14.3										*/
/*		Estado:					Instalado en el cliente.						*/
/*		Fecha creaci�n:			12/09/2020										*/
/*		�ltima modificaci�n:	01/09/2022										*/
/********************************************************************************/

public class LECSys {
	
	public static final String rutaImagenes = "C:\\LECSys\\Images\\";
	
	public static void main(String[] args) {
		
		Configuracion.generoIni();
		VentanaIngresoUsuario.nuevoIngreso();

		if(CheckUsuario.getNivelNivelAcceso() == 100)
			System.exit(0);
		
		JOptionPane.showMessageDialog(null, "Welcome to LECSys.\nVer.1.06\nRev. 01092022.1320");

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				try {
					
					VentanaPrincipal window = new VentanaPrincipal();
					window.frmVentanaPrincipal.setVisible(true);
				} catch (Exception e) {
					
					LogErrores.escribirLog("Error al abrir la ventana principal.");
					e.printStackTrace();
				}
			}
		});
	}
}
