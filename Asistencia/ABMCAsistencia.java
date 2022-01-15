import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ABMCAsistencia {
	
	public ABMCAsistencia() {
		
	}

	private static Conexión conexion = new Conexión();
	private static Connection cn = null;
	private static Statement stm = null;
	private static ResultSet rs = null;
	private String matriz[][];
	
	public boolean asistenciaRepetida(String curso, String fecha) {
		
		boolean bandera = false;
		
		
		try {
			cn = conexion.conectar();
			stm = cn.createStatement();
			rs = stm.executeQuery("SELECT * FROM faltas");

			while (rs.next()) {
				
				String idCurso = rs.getInt(5)+"";
				String dia = rs.getString(3);
				
				if(curso.contentEquals(idCurso) && fecha.contentEquals(dia))
					bandera = true;
			}
			
		} catch (SQLException e) {
			System.out.println("Error al acceder a la tabla faltas (2).");
		} catch (NullPointerException e) {
			System.out.println("Error al acceder a la base de datos ABMCAsistencia (2).");
		} finally {
			cerrarConexiones();
		}

		return bandera;
	}
	
	public boolean guardarDia(String valor[]) {
		
		boolean bandera = true;		
		String comandoStatement = "INSERT INTO faltas ("
				+ "idAlumnos, "
				+ "fecha, "
				+ "idCurso, "
				+ "estado"
				+ ") VALUES ('"
				+ valor[0]
				+ "', '"
				+ valor[1]
				+ "', '"
				+ valor[2]
				+ "', '"
				+ valor[3]
				+ "')";
		try {
			cn = conexion.conectar();
			stm = cn.createStatement();
			stm.executeLargeUpdate(comandoStatement);

		} catch (SQLException e) {
			System.out.println("Error al acceder a la tabla faltas (1).");
			bandera = false;
		} catch (NullPointerException e) {
			System.out.println("Error al acceder a la base de datos ABMCAsistencia (1).");
			bandera = false;
		} finally {
			cerrarConexiones();
		}
		return bandera;
	}
	
	public String [][] getAusentes(String campo, String valor) {

		int numero=getRegistrosAusentes(campo, valor);
	
		try {
			cn = conexion.conectar();
			stm = cn.createStatement();
			rs = stm.executeQuery("SELECT * FROM faltas");

			int pos=0;
			matriz = new String[numero][5];
			String temporal[] = new String[5];

			if(campo == "ALUMNO")
				pos=1;
			if(campo == "CURSO")
				pos=4;
			
			int i=0;
			while (rs.next() && i < numero) {
				temporal[0] = rs.getInt(1)+"";
				temporal[1] = rs.getInt(2)+"";
				temporal[2] = rs.getString(3);
				
				if(rs.getInt(4) == 1)
					temporal[3] = "Present";
				else
					temporal[3] = "Absent";
				
				temporal[4] = rs.getInt(5)+"";
			
				if((temporal[pos].contentEquals(valor) || valor.contentEquals("")) && temporal[3].contentEquals("Absent"))
				{
				
					matriz[i][0] = temporal[0];
					matriz[i][1] = temporal[1];
					matriz[i][2] = temporal[2];
					matriz[i][3] = temporal[3];
					matriz[i][4] = temporal[4];
					i++;
				}
			}
			
		} catch (SQLException e) {
			System.out.println("Error al acceder a la tabla faltas (2).");
		} catch (NullPointerException e) {
			System.out.println("Error al acceder a la base de datos ABMCAsistencia (2).");
		} finally {
			cerrarConexiones();
		}
		
		return matriz;
	}
	
	public String [][] getAsistencia(String idAlumno) {

		int numero=getRegistrosAsistencia(idAlumno);
		matriz = new String[numero][5];
		
		try {
			cn = conexion.conectar();
			stm = cn.createStatement();
			rs = stm.executeQuery("SELECT * FROM faltas");

			int i=0;
			while (rs.next() && i < numero) {
				String id = rs.getInt(2)+"";
				String fecha = rs.getString(3);
				int aux =rs.getInt(4);
				String estado;
				if(aux == 0)
					estado = "Absent";
				else if(aux == 1)
					estado = "Present";
				else 
					estado = "Late";
			
				if(id.contentEquals(idAlumno))
				{
					matriz[i][0] = (i+1)+"";
					matriz[i][1] = fecha;
					matriz[i][2] = estado;
					i++;
				}
			}
			
		} catch (SQLException e) {
			System.out.println("Error al acceder a la tabla faltas (4).");
		} catch (NullPointerException e) {
			System.out.println("Error al acceder a la base de datos ABMCAsistencia (4).");
		} finally {
			cerrarConexiones();
		}
		
		return matriz;
	}	
	
	private int getRegistrosAusentes(String campo, String valor) {
		
		int cantColumnas=0;
		int pos=0;
		String respuesta[] = new String[5];
		
		if(campo == "CURSO")
			pos = 1;
		
		try {
			cn = conexion.conectar();
			stm = cn.createStatement();
			rs = stm.executeQuery("SELECT * FROM faltas");
			
			while (rs.next()) {
				
				respuesta[0] = rs.getInt(2)+"";		// idAlumno
				int estado = rs.getInt(4);			// estado
				respuesta[1] = rs.getInt(5)+"";		// idCurso

				if((respuesta[pos].contentEquals(valor) || valor.contentEquals("")) && estado == 0)
					cantColumnas++;
			}

		} catch (SQLException e) {
			System.out.println("Error al acceder a la tabla faltas (3).");
		} catch (NullPointerException e) {
			System.out.println("Error al acceder a la base de datos ABMCAsistencia (3).");
		} finally {
			cerrarConexiones();
		}
		return cantColumnas;
	}

	private int getRegistrosAsistencia(String idAlumno) {
		
		int cantColumnas=0;
				
		try {
			cn = conexion.conectar();
			stm = cn.createStatement();
			rs = stm.executeQuery("SELECT * FROM faltas");
			
			while (rs.next()) {
				
				String id = rs.getInt(2)+"";		// idAlumno

				if(id.contentEquals(idAlumno))
					cantColumnas++;
			}

		} catch (SQLException e) {
			System.out.println("Error al acceder a la tabla faltas (5).");
		} catch (NullPointerException e) {
			System.out.println("Error al acceder a la base de datos ABMCAsistencia (5).");
		} finally {
			cerrarConexiones();
		}
		return cantColumnas;
	}
	
	private void cerrarConexiones() {
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