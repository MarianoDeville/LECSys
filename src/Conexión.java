import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexión {
	
	private static final String CONTROLADOR = "com.mysql.cj.jdbc.Driver";
//	private static final String URL = "jdbc:mysql://192.168.1.9:3306/lecsys?serverTimezone=UTC";
	private static final String USUARIO = "aplicacion";
	private static final String CLAVE = "proyectoLEC";

	static {
		try {
			Class.forName(CONTROLADOR);
		} catch (ClassNotFoundException e) {
			System.out.println("Error al cargar el controlador.");
		}
	}
	
	public Connection conectar() {
		Connection conexion = null;
		ConfiguracionRed SetUp = new ConfiguracionRed();
		String URL= "jdbc:mysql://" + SetUp.LeerConfiguracion() + ":3306/lecsys?serverTimezone=UTC";
	
		try {
			conexion = DriverManager.getConnection(URL,USUARIO,CLAVE);
		} catch (SQLException e) {
			System.err.println("Error de loging.");
			JOptionPane.showMessageDialog(null,"Error al acceder a la base de datos.");
			return null;
		}
		return conexion;
	}
}