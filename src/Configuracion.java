import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.JOptionPane;

public class Configuracion {

	static public String LeerConfiguracion(String parametro) {
		
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		String respuesta = null;
		boolean bandera = true;

		try {

			archivo = new File ("C:\\LECSys\\configuracion.txt");
			fr = new FileReader (archivo);
			br = new BufferedReader(fr);
			String lineaLeida;
			
			while((lineaLeida=br.readLine())!=null) {
				
				if(lineaLeida.startsWith(parametro)) {
					
					String[] output = lineaLeida.split(":");
					respuesta = output[1];
					
					if(parametro.contentEquals("LOG:"))
						respuesta += ":" + output[2];
					bandera = false;
				}
			}
		} catch(Exception e){
			
			JOptionPane.showMessageDialog(null, "No se encuentra archivo de configuración.");
		} finally {

			try {
				
				if( null != fr ) {   
					
					fr.close();     
				}                  
			} catch (Exception e2) {
				
				e2.printStackTrace();
			}
		}
		
		if(bandera && parametro.contentEquals("LOG:"))
			JOptionPane.showMessageDialog(null, "No se encuentra configuración de log.");
		
		if(bandera && parametro.contentEquals("IP:"))
			JOptionPane.showMessageDialog(null, "No se encuentra configuración de red.");

		return respuesta;
	}
}