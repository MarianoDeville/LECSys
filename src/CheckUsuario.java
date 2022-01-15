import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CheckUsuario {
	
	private static int nivelAcceso = 100;
	private static String nombreUsuario;
	private static int previoNivelAcceso = 6;
	private static String previoNombreUsuario;
	private static int idUsuario;
	private static int previoIdUsuario;
	private static Conexión conexion = new Conexión();
	private static Connection cn = null;
	private static Statement stm = null;
	private static ResultSet rs = null;	
	
	public int probarContraseña() {
		
		String usuario = VentanaIngresoUsuario.getUsuario();
		String password = VentanaIngresoUsuario.getPassword();
		int nivel= 100;
		boolean bandera = false;
		String oldUsuario = idUsuario+"";
		
		if(usuario.equals("") && password.equals("")) {
			
			nivelAcceso=0;
			nombreUsuario = "root";
			return nivelAcceso;
		}
		
		try {
			
			cn = conexion.conectar();
			stm = cn.createStatement();
			rs = stm.executeQuery("SELECT * FROM lecsys.usuarios WHERE (nombre = '" + usuario + "' AND estado = 1)");
			
			if(rs.next()) {
				
				idUsuario = rs.getInt(1);
				String pass = rs.getString(3);
				
				if(password.equals(pass)) {
				
					nivel = rs.getInt(4);
					if(nivelAcceso < 100)
						bandera = true;
					nombreUsuario = usuario;
					nivelAcceso = nivel;
				}
			}
		} catch (SQLException e) {
			System.err.println("Error en el módulo CheckUsuario.");
		} catch (NullPointerException e) {
			System.err.println("Error al acceder a la base de datos, en el módulo CheckUsuario.");
		} finally {
			
			try {
				
				if (rs != null)
					rs.close();
				if (stm != null)
					stm.close();
				if (cn != null)
					cn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		if(bandera) {
			
			String cuerpo[] = {oldUsuario, "Cambio de usuario a: " + nombreUsuario, "Cambiar usuario"};
			ABMCActividad.guardoNuevaActividad(cuerpo);
		}
		return nivel;
	}
	
	public static int getNivelNivelAcceso() {
		
		return nivelAcceso;
	}
	
	public static String getNombreUsuario() {
		
		return (" - "+nombreUsuario);
	}
	
	public static String getIdUsuario() {
		
		return idUsuario+"";
	}
	
	public static void guardarEstado() {
		
		previoIdUsuario = idUsuario;
		previoNivelAcceso = nivelAcceso;
		previoNombreUsuario = nombreUsuario;
	}
	
	public static void recuperarEstado() {
		
		nivelAcceso = previoNivelAcceso;
		nombreUsuario = previoNombreUsuario;
		idUsuario = previoIdUsuario;
		previoNivelAcceso = 100;
		previoNombreUsuario = null;
		previoIdUsuario = 0;
	}
}
