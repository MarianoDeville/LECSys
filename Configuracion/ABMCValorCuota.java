import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ABMCValorCuota {
	
	private static Conexi�n conexion = new Conexi�n();
	private static Connection cn = null;
	private static Statement stm = null;
	private static ResultSet rs = null;	
	
	public static String buscarValorCuota(String idcurso) {
		
		String temporal = null;
		
		try {
			
			cn = conexion.conectar();
			stm = cn.createStatement();
			rs = stm.executeQuery("SELECT precio FROM lecsys.valorcuota WHERE idCurso = " + idcurso);
		
			if(rs.next())
				temporal = rs.getInt(1) + "";
			
		} catch (SQLException e) {
			
			LogErrores.escribirLog("Error al acceder a la tabla valorcuota(1).");
		} catch (NullPointerException e) {
			
			LogErrores.escribirLog("Error al acceder a la base de datos ABMCValorCuota(1).");
		} finally {
			
			cerrarConexiones();
		}
		return temporal;
	}
	
	public static String buscarIdCuota(String idcurso) {
		
		String temporal = null;
		
		try {
			
			cn = conexion.conectar();
			stm = cn.createStatement();
			rs = stm.executeQuery("SELECT idValorCuota FROM lecsys.valorcuota WHERE idCurso = " + idcurso);
		
			if(rs.next())
				temporal = rs.getInt(1) + "";
			
		} catch (SQLException e) {
			
			LogErrores.escribirLog("Error al acceder a la tabla valorcuota(2).");
		} catch (NullPointerException e) {
			
			LogErrores.escribirLog("Error al acceder a la base de datos ABMCValorCuota(2).");
		} finally {
			
			cerrarConexiones();
		}
		return temporal;
	}
	
	public static boolean nuevaCuota(String valor[]) {
		
		boolean bandera = true;		
		String comandoStatement = "INSERT INTO lecsys.valorcuota ("
				+ "idCurso, "
				+ "precio "
				+ ") VALUES ('"
				+ valor[0]
				+ "', '"
				+ valor[1]
				+ "')";
		
		try {
			
			cn = conexion.conectar();
			stm = cn.createStatement();
			stm.executeLargeUpdate(comandoStatement);

		} catch (SQLException e) {
			
			LogErrores.escribirLog("Error al acceder a la tabla valorcuota (3).");
			LogErrores.escribirLog(comandoStatement);
			bandera = false;
		} catch (NullPointerException e) {
			
			LogErrores.escribirLog("Error al acceder a la base de datos ABMCValorcuota (3).");
			bandera = false;
		} finally {
			
			cerrarConexiones();
		}
		
		if(bandera) {
			
			String cuerpo[] = {CheckUsuario.getIdUsuario(),"Nuevo valor de cuota: " + valor[0],"Valor cuota"};
			ABMCActividad.guardoNuevaActividad(cuerpo);
		}
		return bandera;
	}

	public static boolean actualizarCuota(String idCurso, String valor)
	{
		boolean bandera = true;		
		String comandoStatement = "UPDATE lecsys.valorcuota SET "
				+ "precio = '" + valor  
				+ "' WHERE idCurso = " + idCurso;
		try {
			
			cn = conexion.conectar();
			stm = cn.createStatement();
			stm.executeLargeUpdate(comandoStatement);

		} catch (SQLException e) {
			
			LogErrores.escribirLog("Error al acceder a la tabla valorcuota (4).");
			LogErrores.escribirLog(comandoStatement);
			bandera = false;
		} catch (NullPointerException e) {
			
			LogErrores.escribirLog("Error al acceder a la base de datos ABMCValorcuota (4).");
			bandera = false;
		} finally {
			
			cerrarConexiones();
		}
		
		if(bandera) {
			
			String cuerpo[] = {CheckUsuario.getIdUsuario(),"Cambio del valor de la cuota: ", "Valor cuota"};
			ABMCActividad.guardoNuevaActividad(cuerpo);
		}
		return bandera;
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
