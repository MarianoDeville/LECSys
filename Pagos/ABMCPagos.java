import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ABMCPagos {
	
	public ABMCPagos() {
	
	}
	
	private static Conexión conexion = new Conexión();
	private static Connection cn = null;
	private static Statement stm = null;
	private static ResultSet rs = null;	

	public static String [][] getPagos(String mes, String año) {

		int cantRegistros = 0;
		String matriz[][] = null;
		String comandoStatement = "SELECT COUNT(*) FROM lecsys.pagos ";
		String armoWhere = "";
		
		if(!mes.contentEquals("0"))
			armoWhere = "mes = " + mes + " && ";
		
		armoWhere = "WHERE(" + armoWhere + "año = " + año + ")";
	
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

			comandoStatement = "SELECT * FROM lecsys.pagos " + armoWhere;
			rs = stm.executeQuery(comandoStatement);
			matriz = new String[cantRegistros][9];
			int i=0;

			while (rs.next()) {
				
				matriz[i][8] = rs.getInt(1) + "";
				matriz[i][7] = rs.getInt(2) + "";
				matriz[i][0] = rs.getString(3);
				matriz[i][1] = rs.getString(4);
				matriz[i][2] = rs.getInt(5) + "/" + rs.getInt(6) + "/" + rs.getInt(7);
				matriz[i][3] = rs.getString(8);
				matriz[i][4] = rs.getInt(9) + "";
				matriz[i][5] = rs.getString(10);
				matriz[i][6] = rs.getString(11);
				i++;
			}
			
		} catch (SQLException e) {
			
			LogErrores.escribirLog("Error al acceder a la tabla pagos (1).");
			LogErrores.escribirLog(comandoStatement);
		} catch (NullPointerException e) {
			
			LogErrores.escribirLog("Error al acceder a la base de datos ABMCPagos (1).");
		} finally {
			
			cerrarConexiones();
		}

		return matriz;
	}
	
	public static boolean nuevoPago(String campos[]) {
		
		boolean bandera = true;
		String comandoStatement;
		
		if(campos[0] == null)
			comandoStatement = "INSERT INTO lecsys.pagos (nombre, concepto, día, mes, año, hora, monto, factura, comentario) VALUES ('"
							 + campos[1] + "', '" + campos[2] + "', '"+ campos[3] + "', '" + campos[4] + "', '"+ campos[5] + "', '" 
							 + campos[6] + "', '" +  campos[7] + "', '" + campos[8] + "', '" + campos[9] + "')";
		else
			comandoStatement = "INSERT INTO lecsys.pagos (idProfesores, nombre, concepto, día, mes, año, hora, monto, factura, comentario) VALUES ('"
							 + campos[0] + "', '" + campos[1] + "', '" + campos[2] + "', '"+ campos[3] + "', '" + campos[4] + "', '"
							 + campos[5] + "', '" + campos[6] + "', '" +  campos[7] + "', '" + campos[8] + "', '" + campos[9] + "')";

		try {
			
			cn = conexion.conectar();
			
			if(cn == null)
				return false;
		
			stm = cn.createStatement();
			stm.executeLargeUpdate(comandoStatement);
			
		} catch (SQLException e) {
			
			LogErrores.escribirLog("Error al acceder a la tabla pagos (2).");
			LogErrores.escribirLog(comandoStatement);
			bandera = false;
		} catch (NullPointerException e) {
			
			LogErrores.escribirLog("Error al acceder a la base de datos ABMCPagos (2).");
			bandera = false;
		} finally {
			
			cerrarConexiones();
		}
		
		if(bandera) {
			
			String cuerpo[] = {CheckUsuario.getIdUsuario(),"Pago realizado a: " + campos[1] + ", " + campos[2],"Pagos"};
			ABMCActividad.guardoNuevaActividad(cuerpo);
		}
		return bandera;
	}
	
	public static boolean actualizarPago(String idPago, String factura, String comentario) {
		
		boolean bandera = true;		
		String comandoStatement = "UPDATE lecsys.pagos SET factura = '" + factura + "', comentario = '" + comentario
								+ "' WHERE idPagos = "+ idPago;
		
		try {
			
			cn = conexion.conectar();
			
			if(cn == null)
				return false;
		
			stm = cn.createStatement();
			stm.executeLargeUpdate(comandoStatement);
		
		} catch (SQLException e) {
			
			LogErrores.escribirLog("Error al acceder a la tabla pagos (3).");
			LogErrores.escribirLog(comandoStatement);
			bandera = false;
		} catch (NullPointerException e) {
			
			LogErrores.escribirLog("Error al acceder a la base de datos ABMCPagos (3).");
			bandera = false;
		} finally {
			
			cerrarConexiones();
		}
		
		if(bandera) {
			
			String cuerpo[] = {CheckUsuario.getIdUsuario(),"Carga del nro. de factura.", "Pagos"};
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
			
			LogErrores.escribirLog("Error al intentar cerrar las conexiones.");
			e2.printStackTrace();
		}
	}
}
