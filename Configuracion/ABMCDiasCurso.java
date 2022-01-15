import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ABMCDiasCurso {

	public ABMCDiasCurso() {
	
	}

	private static Conexión conexion = new Conexión();
	private static Connection cn = null;
	private static Statement stm = null;
	private static ResultSet rs = null;
	
	private static int getRegistrosDiasCurso(String valorId) {
		
		int cantColumnas=0;
		try {
			cn = conexion.conectar();
			
			if(cn == null)
				return 0;
			
			stm = cn.createStatement();
			rs = stm.executeQuery("SELECT * FROM diascursado");
			
			while (rs.next()) {
				String temporal = rs.getInt(5) + "";	// idCurso
				if(temporal.equals(valorId) || valorId.length() == 0)
					cantColumnas++;
			}

		} catch (SQLException e) {
			System.out.println("Error al acceder a la tabla diascursado(1).");
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("Error al acceder a la base de datos ABMCDiasCurso(1).");
		} finally {
			cerrarConexiones();
		}
		return cantColumnas;
	}

	public static String [] buscarDiaCurso(String campo, String valor) {
		
		String temporal[] = new String[5];
		int i=0;
		
		if(campo.equals("PROFESOR"))
			i=4;
		
		try {
			cn = conexion.conectar();
			
			if(cn == null)
				return null;
			
			stm = cn.createStatement();
			rs = stm.executeQuery("SELECT * FROM diascursado");
		
			while (rs.next()) {
				temporal[0] = rs.getInt(1) + "";	// idDíaCurso
				temporal[1] = rs.getString(2);		// Dia
				temporal[2] = rs.getTime(3) + "";	// Horario
				temporal[3] = rs.getTime(4) + "";	// Duración
				temporal[4] = rs.getInt(5) + "";	// idCurso

				if(temporal[i].equals(valor)) {
					break;
				} else {
					temporal[0] = null;
				}
			}
			
		} catch (SQLException e) {
			System.out.println("Error al acceder a la tabla diascursado(2).");
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("Error al acceder a la base de datos ABMCDiasCurso(2).");
		} finally {
			cerrarConexiones();
		}
		return temporal;
	}
	
	public static String [][] buscarDiasCursado(String valor) {
		
		int numero=getRegistrosDiasCurso(valor);
		String matriz[][] = null;
		
		try {
			cn = conexion.conectar();
			stm = cn.createStatement();
			rs = stm.executeQuery("SELECT * FROM diascursado WHERE idCurso = " + valor);
			
			matriz = new String[numero][3];
			int i=0;
		
			while (rs.next()) {

				matriz[i][0] = rs.getString(2);
				matriz[i][1] = rs.getString(3);
				matriz[i][2] = rs.getString(4);
				i++;
			}
		} catch (SQLException e) {
			System.out.println("Error al acceder a la tabla diascursado(3).");
		} catch (NullPointerException e) {
			System.out.println("Error al acceder a la base de datos ABMCDiasCurso(3).");
		} finally {
			cerrarConexiones();
		}
		return matriz;
	}
	
	public static String [][] obtenerTablaDiasCurso()
	{
		int numero=getRegistrosDiasCurso("");
		String matriz[][] = null;
		
		try {
			cn = conexion.conectar();
			
			if(cn == null)
				return null;
			
			stm = cn.createStatement();
			rs = stm.executeQuery("SELECT * FROM diascursado");
			matriz = new String[numero][5];
			int i=0;
			
			while (rs.next()) {

				matriz[i][0] = rs.getInt(1) + "";	// idDiasCurso
				matriz[i][1] = rs.getString(2);		// Dia
				matriz[i][2] = rs.getString(3);		// Horario
				matriz[i][3] = rs.getString(4);		// Duracion
				matriz[i][4] = rs.getInt(5) + "";	// idCurso
				i++;
			}
		} catch (SQLException e) {
			System.out.println("Error al acceder a la tabla diascursado(4).");
		} catch (NullPointerException e) {
			System.out.println("Error al acceder a la base de datos ABMCDiasCurso(4).");
		} finally {
			cerrarConexiones();
		}
		return matriz;
	}
	
	public static boolean crearDiasCurso(String valor[]) {

		boolean bandera = true;		
		String comandoStatement = "INSERT INTO diascursado (día, horario, duración, idCurso) VALUES ('"
				+ valor[0] + "', '" + valor[1] + "', '" + valor[2] + "', '" + valor[3] + "')";
		
		try {
			
			cn = conexion.conectar();
			
			if(cn == null)
				return false;
			
			stm = cn.createStatement();
			stm.executeLargeUpdate(comandoStatement);

		} catch (SQLException e) {
			System.out.println("Error al acceder a la tabla diascurso(5).");
			bandera = false;
		} catch (NullPointerException e) {
			System.out.println("Error al acceder a la base de datos ABMCDiasCurso(5).");
			bandera = false;
		} finally {
			cerrarConexiones();
		}
		return bandera;
	}
	
	public static boolean borrarDiasCurso(String valor) {

		boolean bandera = true;		
		String comandoStatement = "DELETE FROM diascursado WHERE "
				+ "idCurso = "
				+ valor
				+ " ORDER BY idCurso LIMIT 10";

		try {
			cn = conexion.conectar();
			
			if(cn == null)
				return false;
			
			stm = cn.createStatement();
			stm.executeLargeUpdate(comandoStatement);

		} catch (SQLException e) {
			System.out.println("Error al acceder a la tabla diascurso(6).");
			bandera = false;
		} catch (NullPointerException e) {
			System.out.println("Error al acceder a la base de datos ABMCDiasCurso(6).");
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
