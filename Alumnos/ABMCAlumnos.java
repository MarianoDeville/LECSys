import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ABMCAlumnos {
	
	private static Conexión conexion = new Conexión();
	private static Connection cn = null;
	private static Statement stm = null;
	private static ResultSet rs = null;		
	
	public static String [][] getAlumnos(String campo, String valor, boolean estado, String ordenado) {

		int cantRegistros = 0;
		String matriz[][] = null;
		String comandoStatement = "SELECT count(*) FROM lecsys.alumnos";
		String armoWhere = " WHERE (estado=";
		armoWhere += estado? "1" : "0";
		
		if(campo == "" || valor == "")
			armoWhere += ")";
		else {
			
			armoWhere += " && ";
			
			if(campo.contentEquals("ID"))
				armoWhere += "idAlumno = " + valor + ") ";

			if(campo.contentEquals("CURSO"))
				armoWhere += "idCurso = " + valor + ") ";
			
			if(campo.contentEquals("GRUPOFAMILIAR"))
				armoWhere += valor.contentEquals("0")? "idGrupoFamiliar IS NULL)" : "idGrupoFamiliar =" + valor + ") ";
			
			if(campo.contentEquals("ESTADOFAMILIA"))
				armoWhere += "idGrupoFamiliar > 0) ";
		}

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

			comandoStatement = "SELECT idAlumno, nombre, apellido, dni, dirección, teléfono, email, estado, idCurso, idGrupoFamiliar FROM lecsys.alumnos "
							 + "JOIN lecsys.persona on alumnos.idPersona = persona.idPersona"
							 + armoWhere + " ORDER BY " + ordenado;
			rs = stm.executeQuery(comandoStatement);
			matriz = new String[cantRegistros][10];
			int i=0;
			
			while (rs.next()) {
				
				matriz[i][0] = rs.getInt(1)+"";
				matriz[i][1] = rs.getString(2);
				matriz[i][2] = rs.getString(3);
				matriz[i][3] = rs.getString(4);
				matriz[i][4] = rs.getString(5);
				matriz[i][5] = rs.getString(6);
				matriz[i][6] = rs.getString(7);
				matriz[i][7] = (rs.getInt(8) == 1)? "Activo":"Inactivo";
				matriz[i][8] = rs.getInt(9)+"";
				matriz[i][9] = rs.getInt(10)+"";
				i++;
			}
			
		} catch (SQLException e) {
			System.err.println("Error al acceder a la tabla alumnos (1).");
			System.err.println(comandoStatement);
		} catch (NullPointerException e) {
			System.err.println("Error al acceder a la base de datos ABMCAlumnos (1).");
		} finally {
			cerrarConexiones();
		}
		return matriz;
	}
	
	public static String [] buscarAlumno(String campo, String valor) {

		String temporal[] = new String[12];
		String comandoStatement = "SELECT idAlumno, idCurso, nombre, apellido, dni, fechaNacimiento,"
								+ " dirección, teléfono, email, fechaIngreso, estado, idGrupoFamiliar FROM lecsys.alumnos "
								+ "JOIN lecsys.persona on alumnos.idPersona = persona.idPersona";
		String armoWhere = " WHERE(";
		
		if(campo == "ID")
			armoWhere += "idAlumno = " + valor + ")";
		
		if(campo == "DNI")
			armoWhere += "dni = " + valor + ")";
		
		try {
			
			cn = conexion.conectar();
			stm = cn.createStatement();
			rs = stm.executeQuery(comandoStatement + armoWhere);

			if(rs.next()) {
				
				temporal[0] = rs.getInt(1) + "";	// ID alumno
				temporal[1] = rs.getString(3);		// Nombre
				temporal[2] = rs.getString(4);		// Apellido
				temporal[3] = rs.getString(5);		// DNI
				temporal[4] = rs.getDate(6) + "";	// Fecha nacimiento
				temporal[5] = rs.getString(7);		// Dirección
				temporal[6] = rs.getString(8);		// Teléfono
				temporal[7] = rs.getString(9);		// Email
				temporal[8] = rs.getDate(10) + "";	// Fecha ingreso
				temporal[9] = rs.getInt(11) + "";	// Estado
				temporal[10] = rs.getInt(12) + "";	// ID grupo familiar
				temporal[11] = rs.getInt(2) + "";	// ID curso
			}
			
		} catch (SQLException e) {
			System.err.println("Error al acceder a la tabla alumnos (2).");
			System.err.println(comandoStatement);
		} catch (NullPointerException e) {
			System.err.println("Error al acceder a la base de datos ABMCAlumnos (2).");
		} finally {
			cerrarConexiones();
		}
		return temporal;
	}

	public static boolean modificarAlumno(String valor[]) {
		
		boolean bandera = true;		
		int idPersona = 0;
		String comandoStatement = "SELECT idPersona FROM lecsys.alumnos WHERE idAlumno = " + valor[0];
		
		try {
			cn = conexion.conectar();
			stm = cn.createStatement();
			rs = stm.executeQuery(comandoStatement);
			
			if(rs.next())
				idPersona = rs.getInt(1);
			
			comandoStatement = "UPDATE lecsys.persona SET "
							 + "nombre = '" + valor[1]
							 + "', apellido = '" + valor[2]
							 + "' , dni = '" + valor[3]
							 + "' , dirección = '" + valor[4]
							 + "' , fechaNacimiento = '" + valor[5]	
							 + "' , teléfono = '" + valor[6]
							 + "' , email = '" + valor[7]
							 + "' WHERE (idPersona = '" + idPersona + "')";
			stm.executeLargeUpdate(comandoStatement);
			comandoStatement = "UPDATE lecsys.alumnos SET "
							 + "idCurso = '" + valor[8]						
							 + "' , estado = '" + valor[9]
							 + "' WHERE idAlumno = '" + valor[0] + "'";
			stm.executeLargeUpdate(comandoStatement);

		} catch (SQLException e) {
			System.err.println("Error al acceder a la tabla alumnos (3).");
			System.err.println(comandoStatement);
			bandera = false;
		} catch (NullPointerException e) {
			System.err.println("Error al acceder a la base de datos ABMCAlumnos(3).");
			bandera = false;
		} finally {
			cerrarConexiones();
		}
		
		if(bandera) {
			
			String cuerpo[] = {CheckUsuario.getIdUsuario(),"Modificar datos del alumno: " + valor[1] + " " + valor[2] , "Alumnos"};
			ABMCActividad.guardoNuevaActividad(cuerpo);
		}
		
		return bandera;
	}
	
	public static boolean crearAlumno(String valor[]) {

		boolean bandera = true;
		int idPersona = ABMCPersona.crearPersona(valor);
		String comandoStatement = "INSERT INTO lecsys.alumnos (idPersona, idCurso, fechaIngreso, estado) VALUES ('"
								+ idPersona + "', '" + valor[7] + "', '" + valor[8] + "', '" + valor[9] + "')";
		
		try {
			
			cn = conexion.conectar();
			stm = cn.createStatement();
			stm.executeLargeUpdate(comandoStatement);
			
		} catch (SQLException e) {
			System.err.println("Error al acceder a la tabla alumnos (4).");
			System.err.println(comandoStatement);
			bandera = false;
		} catch (NullPointerException e) {
			System.err.println("Error al acceder a la base de datos ABMCAlumnos(4).");
			bandera = false;
		} finally {
			cerrarConexiones();
		}
		
		if(bandera) {
			
			String cuerpo[] = {CheckUsuario.getIdUsuario(),"Nuevo alumno: " + valor[0] + " " + valor[1] , "Alumnos"};
			ABMCActividad.guardoNuevaActividad(cuerpo);
		}
		
		return bandera;
	}
	
	public static String getNombreAlumno(String idAlumno) {

		String nombre = "";
		String comandoStatement = "SELECT nombre, apellido FROM lecsys.alumnos "
								+ "JOIN lecsys.persona on alumnos.idPersona = persona.idPersona"
								+ " WHERE idAlumno = " + idAlumno;
		
		try {
			
			cn = conexion.conectar();
			stm = cn.createStatement();
			rs = stm.executeQuery(comandoStatement);
			
			if(rs.next())
				nombre = rs.getString(1) + " " + rs.getString(2);

		} catch (SQLException e) {
			System.err.println("Error al acceder a la tabla alumnos (5).");
			System.err.println(comandoStatement);
		} catch (NullPointerException e) {
			System.err.println("Error al acceder a la base de datos ABMCAlumnos (5).");
		} finally {
			cerrarConexiones();
		}
		return nombre;	
	}
	
	public static boolean actualizarGrupoFamiliar(String idAlumno, String idGrupoFamiliar, String descuento) {
		
		boolean bandera = true;		
		String comandoStatement = "UPDATE lecsys.alumnos SET idGrupoFamiliar = " + idGrupoFamiliar + ", estado = 1, descuento = " + descuento
								+ " WHERE idAlumno = "+ idAlumno;
		
		try {
			
			cn = conexion.conectar();
			stm = cn.createStatement();
			stm.executeLargeUpdate(comandoStatement);
			comandoStatement = "SELECT COUNT(*) FROM lecsys.alumnos WHERE idGrupoFamiliar = " + idGrupoFamiliar;
			rs = stm.executeQuery(comandoStatement);
			int cantIntegrantes = 0;
			
			if(rs.next())
				cantIntegrantes = rs.getInt(1);
			
			comandoStatement = "UPDATE lecsys.grupoFamiliar SET integrantes = " + cantIntegrantes + " WHERE idGrupoFamiliar = " + idGrupoFamiliar;
			stm.executeLargeUpdate(comandoStatement);
		
		} catch (SQLException e) {
			System.err.println("Error al acceder a la tabla alumnos (6).");
			System.err.println(comandoStatement);
			bandera = false;
		} catch (NullPointerException e) {
			System.err.println("Error al acceder a la base de datos ABMCAlumnos(6).");
			bandera = false;
		} finally {
			cerrarConexiones();
		}
		
		if(bandera) {
			
			String cuerpo[] = {CheckUsuario.getIdUsuario(),"Se actualizó el grupo familiar." , "Alumnos"};
			ABMCActividad.guardoNuevaActividad(cuerpo);
		}
		return bandera;	
	}
	
	public static boolean resetEstado() {
		
		boolean bandera = true;

		try {
			
			cn = conexion.conectar();
			stm = cn.createStatement();
			stm.executeLargeUpdate("UPDATE lecsys.alumnos SET estado = 0 WHERE estado = 1");
			stm.executeLargeUpdate("UPDATE lecsys.grupoFamiliar SET deuda = 0");

		} catch (SQLException e) {
			System.err.println("Error al acceder a la tabla alumnos (7).");
			bandera = false;
		} catch (NullPointerException e) {
			System.err.println("Error al acceder a la base de datos ABMCAlumnos (7).");
			bandera = false;
		} finally {
			cerrarConexiones();
		}
		
		if(bandera) {
			
			String cuerpo[] = {CheckUsuario.getIdUsuario(),"Reseteo del estado de todos los alumnos." , "Alumnos"};
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
			System.err.println("Error al intentar cerrar las conexiones.");
			e2.printStackTrace();
		}
	}
}
