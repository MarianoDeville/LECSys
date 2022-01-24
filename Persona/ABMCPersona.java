import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ABMCPersona {
	
	public ABMCPersona(){
		
	}
	
	private static Conexión conexion = new Conexión();
	private static Connection cn = null;
	private static Statement stm = null;
	private static ResultSet rs = null;	
	
	public static int crearPersona(String valor[]) {
		
		int registro = 0;
		String comandoStatement = "INSERT INTO lecsys.persona (nombre, apellido, dni, "
								+ "dirección, fechaNacimiento, teléfono, email) VALUES ('"
								+ valor[0] + "', '" + valor[1] + "', '" + valor[2] + "', '" + valor[3]
								+ "', '" + valor[4] + "', '" + valor[5] + "', '" + valor[6] + "')";
								
		try {
			
			cn = conexion.conectar();
			
			if(cn == null)
				return 0;
			
			stm = cn.createStatement();
			stm.executeLargeUpdate(comandoStatement);
			
			rs = stm.executeQuery("SELECT MAX(idPersona) FROM lecsys.persona");

			if(rs.next())
				registro = rs.getInt(1);
		
		} catch (SQLException e) {
			
			System.out.println("Error al acceder a la tabla persona(1).");
			System.out.println(comandoStatement);
			registro = 0;
		} catch (NullPointerException e) {
			
			System.out.println("Error al acceder a la base de datos  ABMCPersona(1).");
			System.out.println(comandoStatement);
			registro = 0;
		} finally {
			
			cerrarConexiones();
		}
		return registro;
	}
	
	private static void cerrarConexiones() {
		
		try {
			
			if (rs != null)
				rs.close();
			if (stm != null)
				stm.close();
			if (cn != null)
				cn.close();
		} catch (Exception e2) {
			
			System.err.println("Error al intentar cerrar las conexiones.");
			e2.printStackTrace();
		}
	}
}
