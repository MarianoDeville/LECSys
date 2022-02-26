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
			
			LogErrores.escribirLog("Error al acceder a la tabla persona(1).");
			LogErrores.escribirLog(comandoStatement);
			registro = 0;
		} catch (NullPointerException e) {
			
			LogErrores.escribirLog("Error al acceder a la base de datos  ABMCPersona(1).");
			registro = 0;
		} finally {
			
			cerrarConexiones();
		}
		return registro;
	}
	
	public static String [][] listadoCumpleAños() {
		
		int cantRegistros = 0;
		
		String armoWhere = "WHERE DAY(fechaNacimiento)=DAY(NOW()) AND MONTH(fechaNacimiento)=MONTH(NOW())";
		String comandoStatement = "SELECT count(*) FROM lecsys.persona " + armoWhere;
		String respuesta[][] = null;

		try {
			
			cn = conexion.conectar();
			
			if(cn == null)
				return null;
			
			stm = cn.createStatement();
			rs = stm.executeQuery(comandoStatement);
			
			if(rs.next())
				cantRegistros = rs.getInt(1);
			
			if(cantRegistros == 0) {
				
				cerrarConexiones();
				return null;
			}
			
			respuesta = new String[cantRegistros][2];
			comandoStatement = "SELECT nombre, apellido FROM lecsys.persona " + armoWhere;
			rs = stm.executeQuery(comandoStatement);
			int i=0;
			
			while (rs.next()) {
				
				respuesta[i][0] = rs.getString(1);
				respuesta[i][1] = rs.getString(2);
				i++;
			}
		
		} catch (SQLException e) {
			
			LogErrores.escribirLog("Error al acceder a la tabla persona(2).");
			LogErrores.escribirLog(comandoStatement);
		} catch (NullPointerException e) {
			
			LogErrores.escribirLog("Error al acceder a la base de datos  ABMCPersona(2).");
		} finally {
			
			cerrarConexiones();
		}
		return respuesta;
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
			
			LogErrores.escribirLog("Error al intentar cerrar las conexiones.");
			e2.printStackTrace();
		}
	}
}
