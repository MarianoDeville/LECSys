import java.awt.EventQueue;
import javax.swing.JOptionPane;

/********************************************************************************/
/*			Sistema de gestión offline para academias - LECSys					*/
/*------------------------------------------------------------------------------*/
/*		Revisión:				1.00											*/
/*		IDE:					Eclipse IDE Ver. 2021-09 (4.21.0).				*/
/*		Lenguaje:				Java SE-1.8										*/
/*		Versionado:				VisualSVN										*/
/*		Base de Datos:			MySQL Workbench 8.00 CE							*/
/*		Plugin:					WindowBuilder 1.9.7								*/
/*								UMLet 14.3										*/
/*		Estado:					Pre-producción.									*/
/*		Fecha creación:			12/09/2020										*/
/*		Última modificación:	13/01/2022										*/
/********************************************************************************/

public class LECSys {
	
	public static final String rutaImagenes = "C:\\LECSys\\Images\\";
	
	public static void main(String[] args) {
		
		VentanaIngresoUsuario entrando = new VentanaIngresoUsuario();
		entrando.nuevoIngreso();

		if(CheckUsuario.getNivelNivelAcceso() == 100)
			System.exit(0);
		
		JOptionPane.showMessageDialog(null, "Welcome to LECSys.\nVer.1.00\nRev. 160122.2205");

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				try {
					
					VentanaPrincipal window = new VentanaPrincipal();
					window.frmVentanaPrincipal.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
