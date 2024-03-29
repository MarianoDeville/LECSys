import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexi�n {
	
	private static final String CONTROLADOR = "com.mysql.cj.jdbc.Driver";
	private static final String USUARIO = "aplicacion";
	private static final String CLAVE = "proyectoLEC";

	static {
		
		try {
			
			Class.forName(CONTROLADOR);
		} catch (ClassNotFoundException e) {
			LogErrores.escribirLog("Error al cargar el controlador.");
		}
	}
	
	public Connection conectar() {
		
		Connection conexion = null;
		String URL= "jdbc:mysql://" + Configuracion.LeerConfiguracion("IP:") + ":3306/lecsys?serverTimezone=UTC";
	
		try {
			
			conexion = DriverManager.getConnection(URL,USUARIO,CLAVE);
		} catch (SQLException e) {
			
			LogErrores.escribirLog("Error al acceder a la base de datos.");
			LogErrores.escribirLog(URL);
		}
		return conexion;
	}
}