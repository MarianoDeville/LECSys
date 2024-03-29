import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class LogErrores {

	static public void escribirLog(String contenido) {
		
		BufferedWriter bw = null;
	    FileWriter fw = null;
	    String nombreArchivo = Configuracion.LeerConfiguracion("LOG:");
		Calendar fechaSistema = new GregorianCalendar();
	    String dateEvento =  (fechaSistema.get(Calendar.DAY_OF_MONTH)<10? "0" + fechaSistema.get(Calendar.DAY_OF_MONTH):fechaSistema.get(Calendar.DAY_OF_MONTH)) + "/" 
							 + ((fechaSistema.get(Calendar.MONTH)+1)<10? "0" + (fechaSistema.get(Calendar.MONTH)+1):(fechaSistema.get(Calendar.MONTH)+1)) + "/" 
							 + fechaSistema.get(Calendar.YEAR) + " "
							 + (fechaSistema.get(Calendar.AM_PM)==0? fechaSistema.get(Calendar.HOUR):fechaSistema.get(Calendar.HOUR)+12) 
							 + ":" +(fechaSistema.get(Calendar.MINUTE)<10? "0" + fechaSistema.get(Calendar.MINUTE):fechaSistema.get(Calendar.MINUTE))
							 + ":" +(fechaSistema.get(Calendar.SECOND)<10? "0" + fechaSistema.get(Calendar.SECOND):fechaSistema.get(Calendar.SECOND));
	    System.err.println(contenido);

	    try {
	    	
	        File archivo = new File(nombreArchivo);

	        if (!archivo.exists())
	        	archivo.createNewFile();

	        fw = new FileWriter(archivo.getAbsoluteFile(), true);		// flag true, indica adjuntar información al archivo.
	        bw = new BufferedWriter(fw);
	        bw.write(dateEvento + " - " + contenido + "\r\n");
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
