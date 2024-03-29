import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ABMCCurso {

	private static Conexi�n conexion = new Conexi�n();
	private static Connection cn = null;
	private static Statement stm = null;
	private static ResultSet rs = null;
	
	public static String [][] busarCursos(String valor) {
		
		String matriz[][] = null;
		int cantRegistros=0;
		String armoWhere = "WHERE idProfesor = " + valor + " AND estado = 1";
		String comandoStatement = "SELECT count(*) FROM lecsys.curso " + armoWhere;
				
		try {
			
			cn = conexion.conectar();
			stm = cn.createStatement();
			rs = stm.executeQuery(comandoStatement);
				
			if(rs.next())
				cantRegistros = rs.getInt(1);
			
			if(cantRegistros == 0) {
				
				cerrarConexiones();
				return null;
			}
	
			comandoStatement = "SELECT a�o, nivel, turno FROM lecsys.curso " + armoWhere;
			matriz = new String[cantRegistros][3];
			rs = stm.executeQuery(comandoStatement);
			int i =0;
			
			while(rs.next()) {
				
				matriz[i][0] = rs.getString(1);
				matriz[i][1] = rs.getString(2);
				matriz[i][2] = rs.getString(3);
				i++;
			}
		} catch (SQLException e) {
			
			LogErrores.escribirLog("Error al acceder a la tabla curso(1).");
			LogErrores.escribirLog(comandoStatement);
		} catch (NullPointerException e) {
			
			
			LogErrores.escribirLog("Error al acceder a la base de datos curso(1).");
		} finally {
			
			cerrarConexiones();
		}
		return matriz;
	}
	
	public static int getUltimoId() {
		
		int resultado = 0;
		
		try {
			
			cn = conexion.conectar();
			stm = cn.createStatement();
			rs = stm.executeQuery("SELECT MAX(idCurso) FROM lecsys.curso");

			if(rs.next())
				resultado = rs.getInt(1);
			
		} catch (SQLException e) {
			
			LogErrores.escribirLog("Error al acceder a la tabla curso(2).");
		} catch (NullPointerException e) {
			
			LogErrores.escribirLog("Error al acceder a la base de datos curso(2).");
		} finally {
			cerrarConexiones();
		}
		return resultado;
	}
		
	public static boolean crearCurso(String valor[]) {
		
		boolean bandera = true;		
		String comandoStatement = "INSERT INTO lecsys.curso (a�o, nivel, idProfesor, estado, turno ) VALUES ('"
								+ valor[0] + "', '" + valor[1] + "', '" + valor[2] + "', '" + "1" + "', '" + valor[3] + "')";
		
		try {
			
			cn = conexion.conectar();
			stm = cn.createStatement();
			stm.executeLargeUpdate(comandoStatement);

		} catch (SQLException e) {
			
			LogErrores.escribirLog("Error al acceder a la tabla curso (3).");
			LogErrores.escribirLog(comandoStatement);
			bandera = false;
		} catch (NullPointerException e) {
			
			LogErrores.escribirLog("Error al acceder a la base de datos ABMCCurso (3).");
			bandera = false;
		} finally {
			
			cerrarConexiones();
		}
		
		if(bandera) {
			
			String cuerpo[] = {CheckUsuario.getIdUsuario(),"Nuevo curso: " + valor[0] + " " + valor[1], "Cursos"};
			ABMCActividad.guardoNuevaActividad(cuerpo);
		}
		return bandera;
	}
	
	public static boolean actualizarTabla(String valor[]) {
		
		boolean bandera = true;
		String comandoStatement = "SELECT a�o, nivel FROM lecsys.curso WHERE idCurso = " + valor[0];
		String temporal="";
		try {
			
			cn = conexion.conectar();
			stm = cn.createStatement();
			rs = stm.executeQuery(comandoStatement);
			
			if(rs.next()) {
				temporal = rs.getString(1) + " ";
				temporal += rs.getString(1);
			}

			comandoStatement = "UPDATE lecsys.curso SET "
					+ "idProfesor = '" + valor[1]
					+ "', estado = '" + valor[2]
					+ "', turno = '" + valor[3]		
					+ "' WHERE idCurso = " + valor[0];
			stm.executeLargeUpdate(comandoStatement);

		} catch (SQLException e) {
			
			LogErrores.escribirLog("Error al acceder a la tabla curso (4).");
			LogErrores.escribirLog(comandoStatement);
			bandera = false;
		} catch (NullPointerException e) {
			
			LogErrores.escribirLog("Error al acceder a la base de datos ABMCCurso (4).");
			bandera = false;
		} finally {
			
			cerrarConexiones();
		}
		
		if(bandera) {
			
			String cuerpo[] = {CheckUsuario.getIdUsuario(),"Modificar curso: " + temporal, "Cursos"};
			ABMCActividad.guardoNuevaActividad(cuerpo);
		}
		return bandera;
	}
	
	public static String [][] getListaCursos(boolean estado) {
		
		String matriz[][] = null;
		int cantRegistros = 0;
		String tablaDias[][] = ABMCDiasCurso.obtenerTablaDiasCurso();
		String comandoStatement = "SELECT count(*) FROM lecsys.curso ";
		String armoWhere = "WHERE curso.estado = " + ((estado)?  "1" :"0");
		
		try {
			
			cn = conexion.conectar();
			stm = cn.createStatement();
			rs = stm.executeQuery(comandoStatement + armoWhere);
			
			if(rs.next())
				cantRegistros = rs.getInt(1);
			
			if(cantRegistros == 0) {
				
				cerrarConexiones();
				return null;
			}

			matriz = new String[cantRegistros][9];
			comandoStatement = "SELECT curso.idCurso, a�o, nivel, curso.idProfesor, nombre, apellido, precio, turno, valorCuota.idValorCuota FROM lecsys.curso "
							 + "JOIN lecsys.profesores ON curso.idProfesor = profesores.idProfesor "
							 + "JOIN lecsys.persona ON profesores.idPersona = persona.idPersona "
							 + "JOIN lecsys.valorCuota on curso.idCurso = valorCuota.idCurso "
							 + armoWhere + " GROUP BY curso.idCurso";
	
			rs = stm.executeQuery(comandoStatement);
			int i=0;

			while (rs.next()) {
				
				matriz[i][0] = rs.getInt(1) + "";							// ID curso
				matriz[i][1] = rs.getString(2);								// A�o
				matriz[i][2] = rs.getString(3);								// Nivel
				matriz[i][3] = rs.getInt(4) + "";							// ID profesor
				matriz[i][4] = rs.getString(5) + " " + rs.getString(6);		// Nombre y apellido
				matriz[i][6] = rs.getInt(7) + "";							// Precio cuota
				matriz[i][7] = rs.getString(8);								// Turno
				matriz[i][8] = rs.getString(9);								// ID cuota
				boolean bandera = true;
				
				for(int e=0; e < tablaDias.length; e++) {
					
					if(matriz[i][0].equals(tablaDias[e][4])) {
						
						if(bandera) {
							
							matriz[i][5] = tablaDias[e][1];
							bandera = false;
						} else
							matriz[i][5] = matriz[i][5] + ", " + tablaDias[e][1];
					}
				}
				bandera = true;
				i++;
			}
		} catch (SQLException e) {
			
			LogErrores.escribirLog("Error al acceder a la tabla curso (5).");
			LogErrores.escribirLog(comandoStatement);
		} catch (NullPointerException e) {
			
			LogErrores.escribirLog("Error al acceder a la base de datos ABMCCurso (5).");
		} finally {
			
			cerrarConexiones();
		}
		return matriz;
	}

	public static String[] getProfesor(String idCurso) {
		
		String datosProfesor[] = {"", ""};
		String comandoStatement = "SELECT curso.idProfesor, nombre, apellido FROM lecsys.curso "
								+ "JOIN lecsys.profesores on curso.idProfesor = profesores.idProfesor "
								+ "JOIN lecsys.persona on profesores.idPersona = persona.idPersona "
								+ "WHERE idCurso = " + idCurso;
		
		try {
			cn = conexion.conectar();
			stm = cn.createStatement();
			rs = stm.executeQuery(comandoStatement);
			
			if(rs.next()) {
				
				datosProfesor[0] = rs.getInt(1) + "";
				datosProfesor[1] = rs.getString(2) + " " + rs.getString(3);
			}
		} catch (SQLException e) {
			
			LogErrores.escribirLog("Error al acceder a la tabla curso(6).");
			LogErrores.escribirLog(comandoStatement);
		} catch (NullPointerException e) {
			
			LogErrores.escribirLog("Error al acceder a la base de datos curso(6).");
		} finally {
			
			cerrarConexiones();
		}
		return datosProfesor;
	}
	
	public static String getNombreCurso(String idCurso) {
		
		String a�o = "";
		String nivel = "";
		String comandoStatement = "SELECT * FROM lecsys.curso WHERE idCurso = " + idCurso;
		
		try {
			
			cn = conexion.conectar();
			stm = cn.createStatement();
			rs = stm.executeQuery(comandoStatement);
			
			if(rs.next()) {
				
				a�o = rs.getString(2);
				nivel = rs.getString(3);
			}
		} catch (SQLException e) {
			
			LogErrores.escribirLog("Error al acceder a la tabla curso(7).");
			LogErrores.escribirLog(comandoStatement);
		} catch (NullPointerException e) {
			
			LogErrores.escribirLog("Error al acceder a la base de datos curso(7).");
		} finally {
			
			cerrarConexiones();
		}
		return (a�o + " " + nivel);
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
