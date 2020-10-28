package aback;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONObject;


public class Queries extends BDConexion {
	private ResultSet rs;
	private ResultSetMetaData rsmd;
	private ArrayList <JSONObject> array = new ArrayList<JSONObject>();
	
	public Queries() {
		super();
	}
	
	private JSONObject getData() throws SQLException {
		JSONObject userData = new JSONObject();
		if(this.rs.next()) {		
			this.rsmd = rs.getMetaData();
			for(int i = 1; i <= this.rsmd.getColumnCount(); i++) {
				userData.put(rsmd.getColumnLabel(i), rs.getObject(i));
			}
		}
		return userData;
	}
	
	private ArrayList<JSONObject> getArray() throws SQLException {
		
		int f = 0;
		
		while(this.rs.next()) {	
			JSONObject userData = new JSONObject();
			this.rsmd = rs.getMetaData();
			for(int i = 1; i <= this.rsmd.getColumnCount(); i++) {
				userData.put(rsmd.getColumnLabel(i), rs.getObject(i));
			}
			System.out.println("contenido: "+ userData);
			array.add( f,userData);
			f++;	
			
		}
		System.out.println("array f: "+ array);
		return array;	
	}
	
		
	//Verificar el email
		public boolean VerificarCorreo(String value) throws SQLException{
			this.rs = executeStatement("BuscarCorreo", value);
			return this.rs.next();
		}
		
	//Verifica si el usuario existe y retorna sus datos
		public JSONObject ObtenerDatos (JSONObject user)throws SQLException{
			String encriptada = Encriptamiento.HashPassword(user.getString("contrasena"));
			this.rs = executeStatement("VerificarIngreso", user.getString("email"), encriptada);
			return this.getData();
		}
	
		
	//Registrar la cuenta
		public boolean Registrar(JSONObject data) throws SQLException{
		 String clave = Encriptamiento.HashPassword(data.getString("contrasena"));
			int i = executeUpdate("IngresarUsuario", data.getString("nombre"), data.getString("apellido"),
					data.getString("email"), clave);
			return (i == 1)?true:false;
		}
		
	//cambiar constrasena
		public boolean CambiarContrasena (JSONObject user)throws SQLException{
			String clave = Encriptamiento.HashPassword(user.getString("contrasena"));
			int i = executeUpdate("CambiarContrasena", clave,user.getInt("id_usuario"));
			return (i == 1)?true:false;
		}
		
	//leer la informacion
		public JSONObject LeerInfo (Integer id)throws SQLException{
			this.rs = executeStatement("LeerInfo", id);
			return this.getData();
		}
		
	//eliminar usuario
		public boolean EliminarUsuario(int data) throws SQLException{
				int i = executeUpdate("EliminarUsuario", data);
				return (i == 1)?true:false;
		}
		
		
	//actualizar usuario
		public boolean ActualizarUsuario(JSONObject data) throws SQLException{
			int i = executeUpdate("ActualizarUsuario", data.getString("nombre"), data.get("apellido"),
					data.get("email"), data.getInt("id_usuario"));
			return (i == 1)?true:false;
		}
		
	//Cerrar los recursos	public void closeResources() {
		public void closeResources() {
			try {
				closeMainResource();
				if(this.rs != null)
					this.rs.close();
			} catch (SQLException e) {
				System.out.println("Problema al cerrar los recursos");
				e.printStackTrace();
			}
		}
		
	//leer los archivos	
		public ArrayList<JSONObject> LeerArchivo(Integer id_usuario )throws SQLException{
			this.rs = executeStatement("LeerArchivo", id_usuario);
			return this.getArray();
		}
		
	//verificar video
		public boolean verificarVideo(String nombre) throws SQLException{
			int i = executeUpdate("verificarArchivo", nombre);
			return (i == 1)?true:false;
		}
				
	//agregar video
		public boolean AgregarVideo(JSONObject data) throws SQLException{
				int i = executeUpdate("subirVideo", data.getInt("id_usuario"), data.getString("nombre"),
						data.getString("tipo"), data.getString("nombre_archivo"));
				return (i == 1)?true:false;
		}
	
	//obtener id del archivo
		public JSONObject ObtenerIdArchivo (Integer id_usuario, String nombre)throws SQLException{
			this.rs = executeStatement("ObtenerIdArchivo", id_usuario, nombre);
			return this.getData();
		}
		
	//borrar video
		public boolean borrarVideo(int id_video) throws SQLException{
			int i = executeUpdate("borrarVideo", id_video);
			return (i == 1)?true:false;
	}
		
	//obtener el nombre del archivo(video)
		public JSONObject obtenerNombreArchivo (int id_video)throws SQLException{
			this.rs = executeStatement("obtenerNombreArchivo", id_video);
			return this.getData();
		}
		
	//
		public boolean addView(int mediaId) throws SQLException {
			int result = executeUpdate("addView", mediaId);
			return (result == 1) ? true : false;
		}
		
	// Function to retrieve an existing videos' URL and file name.
		public JSONObject getMedia(int id_video) throws SQLException {
			JSONObject mediaData = new JSONObject();
			this.rs = executeStatement("descargarVideo", id_video);
			if(this.rs.next()) {
			return mediaData.put("url", rs.getString("tipo")).put("fileName", rs.getString("nombre"));
				}
			return null;
				}
		

}
	
	