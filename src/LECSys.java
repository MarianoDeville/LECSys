import java.awt.EventQueue;
import javax.swing.JOptionPane;

/********************************************************************************/
/*			Sistema de gestión offline para academias - LECSys					*/
/*------------------------------------------------------------------------------*/
/*		Revisión:				1.05											*/
/*		IDE:					Eclipse IDE Ver. 2021-12 (4.22.0).				*/
/*		Lenguaje:				Java SE-1.8										*/
/*		Versionado:				git - github.com								*/
/*		Base de Datos:			MySQL Workbench 8.00 CE							*/
/*		Plugin:					WindowBuilder 1.9.7								*/
/*								UMLet 14.3										*/
/*		Estado:					Instalado en el cliente.						*/
/*		Fecha creación:			12/09/2020										*/
/*		Última modificación:	09/05/2022										*/
/********************************************************************************/

public class LECSys {
	
	public static final String rutaImagenes = "C:\\LECSys\\Images\\";
	
	public static void main(String[] args) {
		
		Configuracion.generoIni();
		VentanaIngresoUsuario.nuevoIngreso();

		if(CheckUsuario.getNivelNivelAcceso() == 100)
			System.exit(0);
		
		JOptionPane.showMessageDialog(null, "Welcome to LECSys.\nVer.1.05\nRev. 09052022.1322");

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
