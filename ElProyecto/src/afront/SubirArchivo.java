package afront;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.json.JSONObject;

import aback.Queries;


/**
 * Servlet implementation class SubirArchivo
 */


@WebServlet("/SubirArchivo")
@MultipartConfig
public class SubirArchivo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubirArchivo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject mensaje = new JSONObject();
		Queries db = new Queries();
		ArrayList <JSONObject> arrayFile = new ArrayList<JSONObject>();
		
		System.out.println("Estoy en el metodo get de Subir archivo");
		Integer id_usuario = Integer.parseInt(request.getParameter("id_usurio"));
		System.out.println("El id_usurio es "+ id_usuario);
		
		try {
			System.out.println("Comenzamos con la lectura de los archivos");
			arrayFile = db.LeerArchivo(id_usuario);
			if(!arrayFile.isEmpty()) {
				mensaje.put("status", 200).put("response",arrayFile);
				System.out.println("Se ha realizado la lectura de los archivos");
				System.out.println(arrayFile);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			db.closeResources();
		}
		out.println(mensaje.toString());
	}

	/**
	 *
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject mensaje = new JSONObject();
		JSONObject file_id = new JSONObject();
		JSONObject data = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		boolean status = false;
		
		Queries db = new Queries();
		Part file = request.getPart("file");
		InputStream filecontent = file.getInputStream();
		OutputStream os = null;
		//Integer id = Integer.parseInt(request.getParameter("id_usurio"));
		
		String video_title= request.getParameter("video_title");
		System.out.println("Estamos Aqui FileUp");
		Integer id_usuario = Integer.parseInt(request.getParameter("id_usurio"));
		String file_name = getFileName(file);
		String file_url = file.getName();
		System.out.println("id_usurio: "+id_usuario+" video_title: "+video_title+" file_name: "+file_name+" file_url: "+file_url);
		try {
			String []part = file_name.split("\\.");
	        String tipo = part[1];
			if(tipo=="mp4" || tipo == "mp3") {//tipo de video
				String baseDir = "C:\\Users\\Loriana Ciccolella\\eclipse-workspace\\ElProyecto\\WebContent\\Upload";
				
				//os = new FileOutputStream(baseDir );
				if(!db.verificarVideo(video_title)) {
					if(db.AgregarVideo(data)) {
						status = true;
						file_id = db.ObtenerIdArchivo(id_usuario, video_title);
						System.out.println("El id del archivo es: " + file_id.getInt("id_video"));
					}
				
				
				os = new FileOutputStream(baseDir + "/" + file_id.getInt("id_video") + "." + tipo);
				int read = 0;
				byte[] bytes = new byte[1024];

				while ((read = filecontent.read(bytes)) != -1) {
					os.write(bytes, 0, read);
				}
			}else {
				mensaje.put("status", 500).put("response","No se puede subir ese tipo de archivo de video");
			}
			
			}
			if(status) {
				mensaje.put("status", 200).put("response","La subida de archivo se realizo con exito");
				System.out.println("La subida de archivo se realizo con exito");
				}else {
					mensaje.put("status", 409).put("response","Ya existe el nombre del archivo ");
				}
			System.out.println("Todo Bien");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (filecontent != null) {
				filecontent.close();
			}
			if (os != null) {
				os.close();
			}
		}
		out.println(mensaje.toString());
		
	}	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject mensaje = new JSONObject();
		JSONObject data = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		Queries db = new Queries();
		//ArrayList <JSONObject> arrayFile = new ArrayList<JSONObject>();
		JSONObject file_name = new JSONObject();
		
		//JSONObject FileData = new JSONObject();
		
		System.out.println("la data es: "+data);
		
		
		try {
			file_name= db.obtenerNombreArchivo(data.getInt("id_video"));
			String []part = file_name.getString("nombre_archivo").split("\\.");
	        String tipo = part[1];
	        File fichero = new File("C:\\Users\\Loriana Ciccolella\\eclipse-workspace\\ElProyecto\\WebContent\\Upload" + data.getInt("id_video") + "." + tipo);
			
			if (db.borrarVideo(data.getInt("id_video"))) {
				if(fichero.delete()) {
					mensaje.put("status", 200).put("response", "El archivo fue borrado");
				}else {
					mensaje.put("status", 500).put("response","No pudo ser borrado");
				}
			}else {
				mensaje.put("status", 500).put("response","No se puedo borrar el archivo de la base de datos");
			}
	       
		}catch(SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeResources();
		}
		out.println(mensaje.toString());
	}
	
	private String getFileName(Part part) {
		for(String content : part.getHeader("content-disposition").split(";")) {
			if(content.trim().startsWith("filename")) {
				return content.substring(content.indexOf("=") + 1).trim().replace("\"", "");
			}
		}
		return null;
	}

}
