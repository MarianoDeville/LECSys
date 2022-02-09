import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

public class Configuracion {

	static public String LeerConfiguracion(String parametro) {
		
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		String respuesta = null;
		boolean bandera = true;

		try {

			archivo = new File ("C:\\LECSys\\LECSys.ini");
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
				
				if( null != fr )   
					fr.close();     
           
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
	
	static public void generoIni() {
		
		BufferedWriter bw = null;
	    FileWriter fw = null;


	    try {
	    	
	        File archivo = new File("C:\\LECSys\\LECSys.ini");

	        if (!archivo.exists()) {
	        	
	        	archivo.createNewFile();
		        fw = new FileWriter(archivo.getAbsoluteFile());
		        bw = new BufferedWriter(fw);
		        bw.write("LECSys - Archivo de configuración.\r\n"
		        		+ "IP:localhost\r\n"
		        		+ "// Si el archivo no existe se genera una nuevo, pero la carpeta debe existir.\r\n"
		        		+ "LOG:C:\\LECSys\\log.txt");
		        
	        }
	    } catch (IOException e) {
	    	
	    	System.err.println("No se pudo escribir en el archivo.");
	    } finally {
	    	
	        try {

	            if (bw != null)
	                bw.close();
	            if (fw != null)
	                fw.close();
	        } catch (IOException ex) {
	        	
	            ex.printStackTrace();
	        }
	    }
	}
}