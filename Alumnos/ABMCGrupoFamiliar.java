import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ABMCGrupoFamiliar {
	
	private static Conexión conexion = new Conexión();
	private static Connection cn = null;
	private static Statement stm = null;
	private static ResultSet rs = null;
		
	public static String [][] getGruposFamilias(boolean sinDeuda, String campoBusqueda) {

		int cantColumnas=0;
		String matriz[][] = null;
		String armoWhere = "WHERE(grupoFamiliar.estado = 1 && deuda > 0 && nombreFamilia LIKE '" + campoBusqueda + "%') ";
		
		if(sinDeuda)
			
			armoWhere = "WHERE(grupoFamiliar.estado = 1 && deuda = 0 && nombreFamilia LIKE '" + campoBusqueda + "%') ";
		
		String comandoStatement = "SELECT count(*) FROM lecsys.grupoFamiliar " +armoWhere;
				
		try {

			cn = conexion.conectar();
			stm = cn.createStatement();
			rs = stm.executeQuery(comandoStatement);
			
			if (rs.next())
				cantColumnas = rs.getInt(1);
			
			if(cantColumnas == 0)
				return null;
			
			matriz = new String[cantColumnas][6];
			comandoStatement = "SELECT grupoFamiliar.idGrupoFamiliar, grupoFamiliar.nombreFamilia, integrantes , deuda, SUM(precio), descuento " +
								"FROM lecsys.alumnos " + 
								"JOIN lecsys.grupoFamiliar ON alumnos.idGrupoFamiliar = grupoFamiliar.idGrupoFamiliar " + 
								"JOIN lecsys.curso ON alumnos.idCurso = curso.idCurso " + 
								"JOIN lecsys.valorCuota ON curso.idCurso = valorCuota.idCurso " + 
								armoWhere +
								"GROUP BY grupoFamiliar.idGrupoFamiliar " +
								"ORDER BY grupoFamiliar.nombreFamilia";
			rs = stm.executeQuery(comandoStatement);
			int i=0;
			
			while (rs.next()) {
				
				matriz[i][0] =rs.getInt(1) + "";
				matriz[i][1] = rs.getString(2);
				matriz[i][2] = rs.getInt(3) + "";
				matriz[i][3] = rs.getInt(4) + "";
				matriz[i][4] = rs.getInt(5) + "";
				matriz[i][5] = rs.getInt(6) + "";
				i++;
			}
			
		} catch (SQLException e) {
			
			LogErrores.escribirLog("Error al acceder a la tabla grupoFamiliar (1).");
			LogErrores.escribirLog(comandoStatement);
		} catch (NullPointerException e) {
			
			LogErrores.escribirLog("Error al acceder a la base de datos ABMCGrupoFamiliar (1).");
		} finally {
			
			cerrarConexiones();
		}
		return matriz;
	}
	
	public static int crearGrupoFamilia(String valor[]) {

		int resultado = 0;
		String comandoStatement = "INSERT INTO lecsys.grupoFamiliar (nombreFamilia, integrantes, deuda, estado, descuento) "
								+ "VALUES ('" + valor[0] + "', '" + valor[1] + "', '" + valor[2] + "', '" + valor[3] + "', '" + valor[4] + "')";

		try {

			cn = conexion.conectar();
			stm = cn.createStatement();
			stm.executeLargeUpdate(comandoStatement);
			rs = stm.executeQuery("SELECT MAX(idGrupoFamiliar) FROM lecsys.grupoFamiliar");
			
			if (rs.next())
				resultado = rs.getInt(1);
			
		} catch (SQLException e) {
			
			LogErrores.escribirLog("Error al acceder a la tabla grupoFamiliar (2).");
			LogErrores.escribirLog(comandoStatement);
			resultado = 0;
		} catch (NullPointerException e) {
			
			LogErrores.escribirLog("Error al acceder a la base de datos ABMCGrupoFamiliar(2).");
			resultado = 0;
		} finally {
			
			cerrarConexiones();
		}
		return resultado;
	}
	
	public static String [] getGrupoFamiliar(String idGrupoFamiliar) {
		
		String respuesta[] = new String[6];		
		
		try {
			
			cn = conexion.conectar();
			stm = cn.createStatement();
			rs = stm.executeQuery("SELECT * FROM lecsys.grupofamiliar WHERE idGrupoFamiliar = " + idGrupoFamiliar);
				
			if(rs.next()) {
				
				respuesta[0] = rs.getInt(1) + "";	// idGrupoFamiliar
				respuesta[1] = rs.getString(2);		// nombreFamilia
				respuesta[2] = rs.getInt(3) + "";	// integrantes
				respuesta[3] = rs.getInt(4) + "";	// deuda
				respuesta[4] = rs.getInt(5) + "";	// estado
				respuesta[5] = rs.getInt(6) + "";	// descuento
			}
			
		} catch (SQLException e) {
			
			LogErrores.escribirLog("Error al acceder a la tabla grupoFamiliar (3).");
		} catch (NullPointerException e) {
			
			LogErrores.escribirLog("Error al acceder a la base de datos ABMCGrupoFamiliar (3).");
		} finally {
			
			cerrarConexiones();
		}
		return respuesta;
	}
	
	public static boolean modificarIntegrantes(String idGrupoFamiliar, String accion) {
	
		String comandoStatement = "";
		String respuesta[] = getGrupoFamiliar(idGrupoFamiliar);
		int integrantes = Integer.parseInt(respuesta[2]);

		if(integrantes > 0 || accion.contentEquals("+"))
			comandoStatement = "UPDATE lecsys.grupofamiliar SET integrantes = integrantes " + accion + " 1 WHERE idGrupoFamiliar = " + idGrupoFamiliar;	
		else
			return false;

		try {
			
			cn = conexion.conectar();
			stm = cn.createStatement();
			stm.executeLargeUpdate(comandoStatement);
	
			if(integrantes == 1 && accion.contentEquals("-")) {
				
				comandoStatement = "UPDATE lecsys.grupofamiliar SET estado = 0 WHERE idGrupoFamiliar = " + idGrupoFamiliar;
				stm.executeLargeUpdate(comandoStatement);
			}

			if(integrantes == 2 && accion.contentEquals("-")) {
				
				comandoStatement = "UPDATE lecsys.grupofamiliar SET descuento = 0 WHERE idGrupoFamiliar = " + idGrupoFamiliar;
				stm.executeLargeUpdate(comandoStatement);
			}
			
			if(accion.contentEquals("+") && respuesta[4].contentEquals("0")) {
				
				comandoStatement = "UPDATE lecsys.grupofamiliar SET estado = 1 WHERE idGrupoFamiliar = " + idGrupoFamiliar;
				stm.executeLargeUpdate(comandoStatement);
			}
			
		} catch (SQLException e) {
			
			LogErrores.escribirLog("Error al acceder a la tabla grupoFamiliar (4).");
			LogErrores.escribirLog(comandoStatement);
		} catch (NullPointerException e) {
			
			LogErrores.escribirLog("Error al acceder a la base de datos ABMCGrupoFamiliar (4).");
		} finally {
			
			cerrarConexiones();
		}
		return true;
	}

	public static boolean modificarMeses(String idGrupoFamiliar, String accion, String cantidadMeses) {
		
		String armoWhere = idGrupoFamiliar.contentEquals("")? " WHERE estado = '1'" : " WHERE idGrupoFamiliar = '" + idGrupoFamiliar + "'";

		try {
			
			cn = conexion.conectar();
			stm = cn.createStatement();
			stm.executeLargeUpdate("UPDATE lecsys.grupofamiliar SET deuda=deuda" + accion + cantidadMeses + armoWhere);
			
		} catch (SQLException e) {
			
			LogErrores.escribirLog("Error al acceder a la tabla grupoFamiliar (5).");
		} catch (NullPointerException e) {
			
			LogErrores.escribirLog("Error al acceder a la base de datos ABMCGrupoFamiliar (5).");
		} finally {
			
			cerrarConexiones();
		}
		return true;
	}

	public static boolean borrarDeuda(String idGrupoFamiliar) {
		
		boolean bandera = true;
		String nombre = "";
		try {
			
			cn = conexion.conectar();
			stm = cn.createStatement();
			stm.executeLargeUpdate("UPDATE lecsys.grupofamiliar SET deuda = 0 WHERE idGrupoFamiliar = " + idGrupoFamiliar);
			
			rs = stm.executeQuery("SELECT nombreFamilia FROM lecsys.grupofamiliar WHERE idGrupoFamiliar = " + idGrupoFamiliar);

			if(rs.next())
				nombre = rs.getString(1);
			
		} catch (SQLException e) {
			
			LogErrores.escribirLog("Error al acceder a la tabla grupoFamiliar (6).");
			bandera = false;
		} catch (NullPointerException e) {
			
			LogErrores.escribirLog("Error al acceder a la base de datos ABMCGrupoFamiliar (6).");
			bandera = false;
		} finally {
			
			cerrarConexiones();
		}
		
		if(bandera) {
			
			String cuerpo[] = {CheckUsuario.getIdUsuario(),"Se eliminó la deuda de: " + nombre, "Cobros"};
			ABMCActividad.guardoNuevaActividad(cuerpo);
		}
		return true;
	}
	
	public static boolean nombreDuplicado(String nombreFamilia) {
		
		boolean bandera = false;
		String armoQuery = "SELECT idGrupoFamiliar FROM lecsys.grupofamiliar WHERE nombreFamilia = '" + nombreFamilia + "'";
		
		try {
			
			cn = conexion.conectar();
			stm = cn.createStatement();
			rs = stm.executeQuery(armoQuery);

			if(rs.next())
				bandera = true;
			
		} catch (SQLException e) {
			
			LogErrores.escribirLog("Error al acceder a la tabla grupoFamiliar (7).");
			LogErrores.escribirLog(armoQuery);
			bandera = false;
		} catch (NullPointerException e) {
			
			LogErrores.escribirLog("Error al acceder a la base de datos ABMCGrupoFamiliar (7).");
			bandera = false;
		} finally {
			
			cerrarConexiones();
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
