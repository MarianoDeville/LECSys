import java.net.InetAddress;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ABMCActividad {

	private static Conexión conexion = new Conexión();
	private static Connection cn = null;
	private static Statement stm = null;
	private static ResultSet rs = null;	
	private static Calendar fechaSistema;
	
	public static void guardoNuevaActividad(String informacion[]) {

		String comandoStatement = "INSERT INTO lecsys.registroActividad (" 
								+ "idUsuarios, fecha, hora, acción, modulo, ip";
		String miIP = "";

		try {
			
			miIP = InetAddress.getLocalHost().getHostAddress();
		} catch(Exception e) {
			
			e.printStackTrace();
		}
		
		if(Integer.parseInt(informacion[0]) > 0)
			comandoStatement += ") VALUES ('" + informacion[0] + "', '";
		else
			comandoStatement += ") VALUES (null , '";
		
		fechaSistema = new GregorianCalendar();
		informacion[1] = informacion[1].substring(0, informacion[1].length()<80? informacion[1].length():80);
		comandoStatement += fechaSistema.get(Calendar.YEAR) + "/" 
						 + (fechaSistema.get(Calendar.MONTH)+1) + "/" 
						 + fechaSistema.get(Calendar.DAY_OF_MONTH) + "', '"
						 + (fechaSistema.get(Calendar.AM_PM)==0? fechaSistema.get(Calendar.HOUR):fechaSistema.get(Calendar.HOUR)+12) 
						 + ":" +fechaSistema.get(Calendar.MINUTE)
						 + ":" +fechaSistema.get(Calendar.SECOND) + "', '"
						 + informacion[1] + "', '"
						 + informacion[2] + "', '"
						 + miIP + "')";

		try {
			
			cn = conexion.conectar();
			
			if(cn == null)
				return;

			stm = cn.createStatement();
			stm.executeLargeUpdate(comandoStatement);
		} catch (SQLException e) {
			
			LogErrores.escribirLog("Error al acceder a la tabla actividad (1).");
			System.err.println("Error al acceder a la tabla actividad (1).");
			System.out.println(comandoStatement);
		} catch (NullPointerException e) {
			
			LogErrores.escribirLog("Error al acceder a la base de datos ABMCActividad(1).");
			System.err.println("Error al acceder a la base de datos ABMCActividad(1).");
		} finally {
			
			cerrarConexiones();
		}
	}
	
	public static String [][] getActividad() {
		
		int cantRegistros = 0;
		String matriz[][] = null;
		String comandoStatement = "SELECT count(*) FROM lecsys.registroActividad WHERE idUsuarios > 0";

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

			comandoStatement = "SELECT fecha, hora, registroActividad.idUsuarios, nombre, acción, modulo, ip "
							  + "FROM lecsys.registroActividad "
							  + "JOIN lecsys.usuarios on  registroActividad.idUsuarios = usuarios.idUsuarios "
							  + "ORDER BY idRegistroActividad DESC";
			rs = stm.executeQuery(comandoStatement);
			matriz = new String[cantRegistros][6];
			int i=0;
			
			while (rs.next()) {
				
				matriz[i][0] = rs.getString(1);
				matriz[i][1] = rs.getString(2);
				matriz[i][2] = rs.getInt(3) + " - ";
				matriz[i][2] += rs.getString(4);
				matriz[i][3] = rs.getString(5);
				matriz[i][4] = rs.getString(6);
				matriz[i][5] = rs.getString(7);
				i++;
			}
			
		} catch (SQLException e) {
			
			LogErrores.escribirLog("Error al acceder a la tabla actividad (2).");
			System.err.println("Error al acceder a la tabla actividad (2).");
			System.out.println(comandoStatement);
		} catch (NullPointerException e) {
			
			LogErrores.escribirLog("Error al acceder a la base de datos ABMCActividad(2).");
			System.err.println("Error al acceder a la base de datos ABMCActividad(2).");
		} finally {
			
			cerrarConexiones();
		}
		return matriz;
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
			System.err.println("Error al intentar cerrar las conexiones.");
			e2.printStackTrace();
		}
	}
}