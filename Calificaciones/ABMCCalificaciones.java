import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ABMCCalificaciones {
	
	private static Conexión conexion = new Conexión();
	private static Connection cn = null;
	private static Statement stm = null;
	private static ResultSet rs = null;
		
	public static String [][] getExamenes(String valor) {
		
		int cantRegistros = 0;
		int i=0;
		String matriz[][] = null;
		String comandoStatement = "SELECT count(*) FROM lecsys.examenes ";
		String armoWhere = "WHERE examenes.idCurso = " + valor;

		try {
		
			cn = conexion.conectar();
			
			if(cn == null)
				return null;
			
			stm = cn.createStatement();
			rs = stm.executeQuery(comandoStatement + armoWhere);
			
			if(rs.next())
				cantRegistros = rs.getInt(1);
			
			if(cantRegistros == 0) {
				
				cerrarConexiones();
				return null;
			}
			
			comandoStatement = "SELECT examenes.idAlumno, estudiante.nombre, estudiante.apellido, "
							 + "fecha, nota, profe.nombre, profe.apellido ,año, nivel FROM lecsys.examenes "
							 + "JOIN lecsys.alumnos ON examenes.idAlumno = alumnos.idAlumno "
							 + "JOIN lecsys.persona estudiante ON alumnos.idPersona = estudiante.idPersona "
							 + "JOIN lecsys.profesores ON examenes.idProfesor = profesores.idProfesor "
							 + "JOIN lecsys.persona profe ON profesores.idPersona = profe.idPersona "
							 + "JOIN lecsys.curso ON examenes.idCurso = curso.idCurso ";
			rs = stm.executeQuery(comandoStatement + armoWhere);
			matriz = new String[cantRegistros][6];
			
			while (rs.next()) {
				
				matriz[i][0] = rs.getInt(1) + "";
				matriz[i][1] = rs.getString(2) + " " + rs.getString(3);
				matriz[i][2] = rs.getString(4);
				matriz[i][3] = rs.getInt(5) + "";
				matriz[i][4] =  rs.getString(6) + " " + rs.getString(7);
				matriz[i][5] = rs.getString(8) + " " + rs.getString(9);
				i++;
			}
		} catch (SQLException e) {
			System.out.println("Error al acceder a la tabla examenes (1).");
		} catch (NullPointerException e) {
			System.out.println("Error al acceder a la base de datos ABMCCalificaciones (1).");
		} finally {
			cerrarConexiones();
		}
		return matriz;
	}
	
	public static boolean guardarExamen(String valor[]) {
		
		boolean bandera = true;
		String comandoStatement = "INSERT INTO lecsys.examenes ("
								+ "idAlumno, fecha, tipo, nota, idProfesor, idCurso) VALUES ('"
								+ valor[0] + "', '" + valor[1] + "', '" + valor[2] + "', '"
								+ valor[3] + "', '" + valor[4] + "', '" + valor[5] + "')";

		try {
			
			cn = conexion.conectar();
			
			if(cn == null)
				return false;
			
			stm = cn.createStatement();
			stm.executeLargeUpdate(comandoStatement);

		} catch (SQLException e) {
			System.out.println("Error al acceder a la tabla calificaciones (2).");
			bandera = false;
		} catch (NullPointerException e) {
			System.out.println("Error al acceder a la base de datos ABMCCalificaciones(2).");
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
			System.err.println("Error al intentar cerrar las conexiones.");
			e2.printStackTrace();
		}
	}
}
