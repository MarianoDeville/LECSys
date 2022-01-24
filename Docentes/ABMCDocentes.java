import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ABMCDocentes {
	
	public ABMCDocentes() {
		
	}
	
	private static Conexión conexion = new Conexión();
	private static Connection cn = null;
	private static Statement stm = null;
	private static ResultSet rs = null;		
	
	public static String [][] getProfesores(boolean estado) {

			int cantRegistros = 0;
			String matriz[][] = null;
			String comandoStatement = "SELECT count(*) FROM lecsys.profesores ";
			String armoWhere = " WHERE estado = ";
			armoWhere = armoWhere + (estado?  "1" : "0");
	
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
				
				matriz = new String[cantRegistros][10];
				comandoStatement = "SELECT idProfesor, nombre, apellido, dni, dirección, teléfono, email, sueldo, estado FROM lecsys.profesores "
								 + "JOIN lecsys.persona ON profesores.idPersona = persona.idPersona"
								 + armoWhere 
								 + " ORDER BY apellido, nombre";
				rs = stm.executeQuery(comandoStatement);
				int i=0;
				
				while (rs.next()) {
					
					matriz[i][0] = rs.getInt(1) + "";
					matriz[i][1] = rs.getString(2);
					matriz[i][2] = rs.getString(3);
					matriz[i][3] = rs.getString(4);
					matriz[i][4] = rs.getString(5);
					matriz[i][5] = rs.getString(6);
					matriz[i][6] = rs.getString(7);
					matriz[i][7] = rs.getInt(8) + "";
					matriz[i][9] = (rs.getInt(9) == 1)? "Activo":"Inactivo";
					i++;
				}
			} catch (SQLException e) {
				System.out.println("Error al acceder a la tabla profesores (1).");
				System.out.println(comandoStatement);
			} catch (NullPointerException e) {
				System.out.println("Error al acceder a la base de datos ABMCProfesores (1).");
				System.out.println(comandoStatement);
			} finally {
				cerrarConexiones();
			}
			return matriz;
		}

	public static String [] buscarProfesor(String campo, String valor) {

		String temporal[] = new String[12];
		String comandoStatement = "SELECT idProfesor, nombre, apellido, dni, dirección, teléfono, email, sueldo, fechaIngreso, estado, fechaNacimiento FROM lecsys.profesores "
								+ "JOIN lecsys.persona ON profesores.idPersona = persona.idPersona";
		String armoWhere = " WHERE ";
		
		if(campo == "ID")
			armoWhere = armoWhere + "idProfesor = ";
		
		if(campo == "DNI")
			armoWhere = armoWhere + "dni = ";
		
		armoWhere = armoWhere + valor;

		try {
			
			cn = conexion.conectar();
			stm = cn.createStatement();
			rs = stm.executeQuery(comandoStatement + armoWhere);

			if(rs.next()) {
				
				temporal[0] = rs.getInt(1) + "";		// IdProfesor
				temporal[1] = rs.getString(2);			// Nombre
				temporal[2] = rs.getString(3);			// Apellido
				temporal[3] = rs.getString(4);			// DNI
				temporal[4] = rs.getString(5);			// Dirección
				temporal[5] = rs.getString(6);			// Teléfono
				temporal[6] = rs.getString(7);			// Email
				temporal[7] = rs.getInt(8) + "";		// Sueldo
				temporal[8] = rs.getDate(9) + "";		// Fecha de ingreso
				temporal[9] = rs.getInt(10) + "";		// Estado
				temporal[10] = rs.getDate(11) + "";		// Fecha nacimiento
			}
			
		} catch (SQLException e) {
			System.out.println("Error al acceder a la tabla profesores (2).");
			System.out.println(comandoStatement);
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("Error al acceder a la base de datos ABMCProfesores (2).");
			System.out.println(comandoStatement);
		} finally {
			cerrarConexiones();
		}
		return temporal;
	}

	public boolean modificarDatoProfesor(String valor[]) {
		
		boolean bandera = true;		
		int idPersona = 0;
		String comandoStatement = "SELECT idPersona FROM lecsys.profesores WHERE idProfesor = " + valor[0];
		
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
			comandoStatement = "UPDATE lecsys.profesores SET "
							 + "sueldo = '" + valor[8]						
							 + "' , estado = '" + valor[9]
							 + "' WHERE idProfesor = '" + valor[0] + "'";
			stm.executeLargeUpdate(comandoStatement);
		
		} catch (SQLException e) {
			System.out.println("Error al acceder a la tabla profesores (3).");
			System.out.println(comandoStatement);
			bandera = false;
		} catch (NullPointerException e) {
			System.out.println("Error al acceder a la base de datos ABMCProfesores(3).");
			System.out.println(comandoStatement);
			bandera = false;
		} finally {
			cerrarConexiones();
		}
		
		if(bandera) {
			
			String cuerpo[] = {CheckUsuario.getIdUsuario(),"Modificar datos del profesor: " + valor[1] + " " + valor[2] , "Profesores"};
			ABMCActividad.guardoNuevaActividad(cuerpo);
		}
		
		return bandera;
	}
	
	public boolean crearProfesor(String valor[]) {
		
		boolean bandera = true;
		int idPersona = ABMCPersona.crearPersona(valor);
		String comandoStatement = "INSERT INTO lecsys.profesores (idPersona, sueldo, fechaIngreso, estado) VALUES ('"
								+ idPersona + "', '" + valor[7] + "', '" + valor[8] + "', '" + valor[9] + "')";
				
		try {
			
			cn = conexion.conectar();
			stm = cn.createStatement();
			stm.executeLargeUpdate(comandoStatement);

		} catch (SQLException e) {
			System.out.println("Error al acceder a la tabla profesores (4).");
			System.out.println(comandoStatement);
			bandera = false;
		} catch (NullPointerException e) {
			System.out.println("Error al acceder a la base de datos  ABMCProfesores(4).");
			System.out.println(comandoStatement);
			bandera = false;
		} finally {
			cerrarConexiones();
		}
		
		if(bandera) {
			
			String cuerpo[] = {CheckUsuario.getIdUsuario(),"Nuevo profesor: " + valor[0] + " " + valor[1] , "Profesores"};
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