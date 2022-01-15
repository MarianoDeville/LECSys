import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Estadisticas {
	
	private static Calendar fechaSistema = new GregorianCalendar();
	private static Conexión conexion = new Conexión();
	private static Connection cn = null;
	private static Statement stm = null;
	private static ResultSet rs = null;
	
	public static boolean nuevoMes() {
		
		String fechaActual[] = {fechaSistema.get(Calendar.DAY_OF_MONTH)+"", (fechaSistema.get(Calendar.MONTH)+1)+"", fechaSistema.get(Calendar.YEAR)+""};
		String ultimoMesCargado = null;
		String comandoStatement = "";
		int cantAlumnos = 0;
		int cantProfesores = 0;
		int cantRegistros = 0;
		int montoPagos = 0;
		int montoCobros = 0;
		
		try {
			
			cn = conexion.conectar();
			stm = cn.createStatement();
			rs = stm.executeQuery("SELECT COUNT(*) FROM lecsys.estadísticas");
						
			if (rs.next())
				cantRegistros = rs.getInt(1);
			
			if(cantRegistros > 0) {
			
				comandoStatement = "SELECT MAX(idEstadísticas) FROM lecsys.estadísticas";
				rs = stm.executeQuery(comandoStatement);
				
				if (rs.next())
					comandoStatement = "SELECT mes FROM lecsys.estadísticas WHERE idEstadísticas = " + rs.getInt(1);
					
				rs = stm.executeQuery(comandoStatement);	
					
				if (rs.next())
					ultimoMesCargado = rs.getInt(1)+"";

				if(ultimoMesCargado.contentEquals(fechaActual[1])) {
					
					cerrarConexiones();
					return false;
				}
			}

			comandoStatement = "SELECT COUNT(*) FROM lecsys.alumnos  WHERE estado = 1";
			rs = stm.executeQuery(comandoStatement);
			
			if (rs.next())
				cantAlumnos = rs.getInt(1);
			
			comandoStatement = "SELECT COUNT(*) FROM lecsys.profesores  WHERE estado = 1";
			rs = stm.executeQuery(comandoStatement);
			
			if (rs.next())
				cantProfesores = rs.getInt(1);
			
			comandoStatement = "SELECT SUM(monto) FROM lecsys.pagos  WHERE mes = " + (fechaSistema.get(Calendar.MONTH)+1);
			rs = stm.executeQuery(comandoStatement);
			
			if (rs.next())
				montoPagos = rs.getInt(1);
			
			comandoStatement = "SELECT SUM(monto) FROM lecsys.cobros  WHERE mes = " + (fechaSistema.get(Calendar.MONTH)+1);
			rs = stm.executeQuery(comandoStatement);
			
			if (rs.next())
				montoCobros = rs.getInt(1);
				
			comandoStatement = "INSERT INTO lecsys.estadísticas (mes, año, estudiantesActivos, profesoresActivos, ingresosMes, egresosMes) VALUES ('"	
								+ fechaActual[1] + "', '" + fechaActual[2]	+ "', '" + cantAlumnos	+ "', '" + cantProfesores + "', '" + montoCobros	+ "', '" + montoPagos + "')";
			stm.executeLargeUpdate(comandoStatement);

		} catch (SQLException e) {
			System.err.println("Error al acceder a la tabla estadisticas (1).");
		} catch (NullPointerException e) {
			System.err.println("Error al acceder a la base de datos Estadisticas (1).");
		} finally {
			cerrarConexiones();
		}
		return true;
	}
	
	public static String [] getUltimoMes() {
		
		String resultado[] = new String[7];
		
		try {
			
			cn = conexion.conectar();
			stm = cn.createStatement();
			rs = stm.executeQuery("SELECT MAX(idEstadísticas) FROM lecsys.estadísticas");
			
			if(rs.next())
				resultado[0] = rs.getInt(1)+"";
			
			rs = stm.executeQuery("SELECT * FROM lecsys.estadísticas WHERE idEstadísticas = " + resultado[0]);
			
			if(rs.next()) {
				
				resultado[1] = rs.getString(2);
				resultado[2] = rs.getInt(3)+"";
				resultado[3] = rs.getInt(4)+"";
				resultado[4] = rs.getInt(5)+"";
				resultado[5] = rs.getInt(6)+"";
				resultado[6] = rs.getInt(7)+"";
			}
		} catch (SQLException e) {
			System.err.println("Error al acceder a la tabla estadisticas (2).");
		} catch (NullPointerException e) {
			System.err.println("Error al acceder a la base de datos Estadisticas (2).");
		} finally {
			cerrarConexiones();
		}
		return resultado;
	}

	public static void actualizoEstadisticas() {
		
		String comandoStatement = "SELECT COUNT(*) FROM lecsys.alumnos  WHERE estado = 1";
		int cantAlumnos = 0;
		int cantProfesores = 0;
		int montoPagos = 0;
		int montoCobros = 0;
		int idEstadísticas = 0;
				
		try {
			
			cn = conexion.conectar();
			stm = cn.createStatement();
			rs = stm.executeQuery(comandoStatement);
			
			if (rs.next())
				cantAlumnos = rs.getInt(1);
			
			comandoStatement = "SELECT COUNT(*) FROM lecsys.profesores  WHERE estado = 1";
			rs = stm.executeQuery(comandoStatement);
			
			if (rs.next())
				cantProfesores = rs.getInt(1);
			
			comandoStatement = "SELECT SUM(monto) FROM lecsys.pagos  WHERE mes = " + (fechaSistema.get(Calendar.MONTH)+1);
			rs = stm.executeQuery(comandoStatement);
			
			if (rs.next())
				montoPagos = rs.getInt(1);
			
			comandoStatement = "SELECT SUM(monto) FROM lecsys.cobros  WHERE mes = " + (fechaSistema.get(Calendar.MONTH)+1);
			rs = stm.executeQuery(comandoStatement);
			
			if (rs.next())
				montoCobros = rs.getInt(1);

			rs = stm.executeQuery("SELECT MAX(idEstadísticas) FROM lecsys.estadísticas");
			
			if (rs.next())
				idEstadísticas = rs.getInt(1);
			
			comandoStatement = "UPDATE lecsys.estadísticas SET "
							 + "estudiantesActivos = '" + cantAlumnos + "', "
							 + "profesoresActivos = '" + cantProfesores + "', "
							 + "ingresosMes = '" + montoCobros + "', "
							 + "egresosMes = '" + montoPagos + "' "
							 + "WHERE idEstadísticas = " + idEstadísticas;
			stm.executeLargeUpdate(comandoStatement);

		} catch (SQLException e) {
			System.err.println("Error al acceder a la tabla estadisticas (3).");
		} catch (NullPointerException e) {
			System.err.println("Error al acceder a la base de datos Estadisticas (3).");
		} finally {
			cerrarConexiones();
		}
	}

	public static String [][] getEstadisticasAnuales(String año) {
		
		String matriz[][] = new String[12][5];
		String meses[]= {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
		String alumnos[]= new String[12];
		String profesores[]= new String[12];
		String cobros[]= new String[12];
		String pagos[]= new String[12];

		try {
			
			cn = conexion.conectar();
			stm = cn.createStatement();
			rs = stm.executeQuery("SELECT * FROM lecsys.estadísticas WHERE año = " + año);

			while(rs.next()) {
			
				int pos = rs.getInt(2) - 1;
				alumnos[pos] = rs.getInt(4)+"";
				profesores[pos] = rs.getInt(5)+"";
				cobros[pos] = rs.getInt(6)+"";
				pagos[pos] = rs.getInt(7)+"";
			}
			
			for(int i = 0; i < 12; i++) {
				
				matriz[i][0] = meses[i];
				matriz[i][1] = profesores[i];
				matriz[i][2] = alumnos[i];
				matriz[i][3] = cobros[i];
				matriz[i][4] = pagos[i];
			}

		} catch (SQLException e) {
			System.err.println("Error al acceder a la tabla estadisticas (4).");
		} catch (NullPointerException e) {
			System.err.println("Error al acceder a la base de datos Estadisticas (4).");
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
			System.err.println("Error al intentar cerrar las conexiones.");
			e2.printStackTrace();
		}
	}
}
