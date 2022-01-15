import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ABMCConfiguracion {

	public ABMCConfiguracion() {
		
	}
	
	private static Conexión conexion = new Conexión();
	private static Connection cn = null;
	private static Statement stm = null;
	private static ResultSet rs = null;		
	
	public String [][] getUsuarios() {
		
		int cantRegistros = 0;
		String matriz[][] = null;
		
		try {
			cn = conexion.conectar();
			
			if(cn == null)
				return null;
			
			stm = cn.createStatement();
			rs = stm.executeQuery("SELECT count(*) FROM lecsys.usuarios WHERE estado = 1");
			
			if(rs.next())
				cantRegistros = rs.getInt(1);
			
			if(cantRegistros == 0)
			{
				cerrarConexiones();
				return null;
			}

			rs = stm.executeQuery("SELECT * FROM lecsys.usuarios WHERE estado = 1 ORDER BY nivelAcceso, nombre");
			matriz = new String[cantRegistros][5];
			
			int i=0;
			while (rs.next()) {
				
				String idUsuario = rs.getInt(1)+"";
				String nombre = rs.getString(2);
				int nivelAcceso = rs.getInt(4);
				matriz[i][0] = nombre;
				if(nivelAcceso==0)
					matriz[i][1]="Root";
				else if(nivelAcceso==1)
					matriz[i][1]="Administrator";
				else if(nivelAcceso==2)
					matriz[i][1]="Secretary";
				else if(nivelAcceso>=2)
					matriz[i][1]="Teacher";
				matriz[i][2] = idUsuario;
				i++;
			}
		} catch (SQLException e) {
			System.out.println("Error al acceder a la tabla usuarios (1).");
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("Error al acceder a la base de datos ABMCConfiguración (1).");
		} finally {
			cerrarConexiones();
		}
		return matriz;
	}
		
	public boolean crearUsuario(String valor[]) {
		
		boolean bandera = true;		
		String comandoStatement = "INSERT INTO lecsys.usuarios (nombre, contraseña, nivelAcceso, estado) VALUES ('"
								+ valor[0] + "', '" + valor[1] + "', '" + valor[2] + "', 1)";
		
		try {
			cn = conexion.conectar();
			stm = cn.createStatement();
			stm.executeLargeUpdate(comandoStatement);
			
		} catch (SQLException e) {
			System.out.println("Error al acceder a la tabla usuarios (2).");
			bandera = false;
		} catch (NullPointerException e) {
			System.out.println("Error al acceder a la base de datos ABMCConfiguración (2).");
			bandera = false;
		} finally {
			cerrarConexiones();
		}
		String cuerpo[] = {CheckUsuario.getIdUsuario(),"Nuevo usuario: " + valor[0],"Usuarios"};
		ABMCActividad.guardoNuevaActividad(cuerpo);
		return bandera;
	}
	
	public String [] buscarUsuario(String campo, String valor) {
		
		String temporal[] = new String[4];
		String comandoStatement = "SELECT * FROM lecsys.usuarios "
								+ "WHERE (" + (campo.contentEquals("NOMBRE")? "nombre = '" : "idUsuarios = '")
								+ valor + "' AND estado = 1)";

		try {
			
			cn = conexion.conectar();
			
			if(cn == null)
				return null;
			
			stm = cn.createStatement();
			rs = stm.executeQuery(comandoStatement);
		
			if(rs.next()) {
				temporal[0] = rs.getInt(1) + "";
				temporal[1] = rs.getString(2);
				temporal[2] = rs.getString(3);
				temporal[3] = rs.getInt(4) + "";
			}
			
		} catch (SQLException e) {
			System.out.println("Error al acceder a la tabla usuarios (3).");
		} catch (NullPointerException e) {
			System.out.println("Error al acceder a la base de datos ABMCConfiguración (3).");
		} finally {
			cerrarConexiones();
		}
		return temporal;
	}
	
	public boolean borrarUsuario(String idUsuario) {
		
		boolean bandera = true;
		String nombre = "";
		String comandoStatement = "SELECT nombre FROM lecsys.usuarios WHERE idUsuarios = '" + idUsuario + "'";

		try {
			
			cn = conexion.conectar();
			
			if(cn == null)
				return false;
			
			stm = cn.createStatement();
			rs = stm.executeQuery(comandoStatement);
			
			if(rs.next())
				nombre = rs.getString(1);
			
			comandoStatement = "UPDATE lecsys.usuarios SET contraseña = '', nivelAcceso = 100, estado = 0 "
									+ " WHERE idUsuarios = " + idUsuario;
			stm.executeLargeUpdate(comandoStatement);
			
		} catch (SQLException e) {
			System.out.println("Error al acceder a la tabla usuarios (4).");
			bandera = false;
		} catch (NullPointerException e) {
			System.out.println("Error al acceder a la base de datos ABMCConfiguración (4).");
			bandera = false;
		} finally {
			cerrarConexiones();
		}
		if(bandera) {
			String cuerpo[] = {CheckUsuario.getIdUsuario(),"Borrar usuario: " + nombre ,"Usuarios"};
			ABMCActividad.guardoNuevaActividad(cuerpo);
		}
		return bandera;
	}
	
	public boolean actualizarUsuario(String valor[]) {
		
		boolean bandera = true;		
		String comandoStatement = "UPDATE lecsys.usuarios SET "
				+ "`contraseña` = '" + valor[1]
				+ "', `nivelAcceso` = '" + valor[2]
				+ "' WHERE (`idUsuarios` = '" + valor[0] + "')";

		try {
			
			cn = conexion.conectar();
			stm = cn.createStatement();
			stm.executeLargeUpdate(comandoStatement);
		} catch (SQLException e) {
			System.out.println("Error al acceder a la tabla usuarios (5).");
			bandera = false;
		} catch (NullPointerException e) {
			System.out.println("Error al acceder a la base de datos ABMCConfiguración (5).");
			bandera = false;
		} finally {
			cerrarConexiones();
		}
		return bandera;
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