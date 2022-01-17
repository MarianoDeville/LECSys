import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ConfiguracionRed {

	public String LeerConfiguracion() {
		
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		String urlLeido = null;

		try {

			archivo = new File ("C:\\LECSys\\configuracion.txt");
			fr = new FileReader (archivo);
			br = new BufferedReader(fr);
			
			String lineaLeida;
			while((lineaLeida=br.readLine())!=null) {
				
				if(lineaLeida.startsWith("IP:")) {
					
					String[] output = lineaLeida.split(":");
					urlLeido = output[1];
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}finally{

			try{
				
				if( null != fr ){   
					fr.close();     
				}                  
			}catch (Exception e2){ 
				e2.printStackTrace();
			}
		}
		return urlLeido;
	}
}