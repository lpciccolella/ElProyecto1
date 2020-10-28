package afront;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.http.Part;

import aback.BDConexion;
import aback.M_administrador_conexion;

public class VideoHelper {
	//con este metodo agarro un archivo tipo part que es como vendran los archivos de donde los este enviando 
 	public static void UploadVideo(Part mpart, String savePath) throws IOException {
		// TODO Auto-generated method stub
		String fileName = extractFileName(mpart);
		fileName=new File(fileName).getName();
		System.out.println("la ruta del video es " +savePath);
		System.out.println("el nombre del video es " +fileName);
		mpart.write(savePath + File.separator + fileName);
		
		Connection con = M_administrador_conexion.ReturnConnection();
		try { 
			String video_path=savePath;
			String video_title=fileName;
			
			PreparedStatement PS = con.prepareStatement("INSERT INTO video (id_usuario, video_path, video_title, thumnail,  video_description) VALUES (?, ?, ?, ?, ?)");
			PS.setString(1, video_path);
			PS.setString(2, video_title);
			PS.execute();
			System.out.println("los videos estan subidos");
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
	

	}

	//este metodo devuelve el nombre del archivo
	private static String extractFileName(Part part) {
		// TODO Auto-generated method stub
		String contentdisp = part.getHeader("content-disposition");
		String [] items = contentdisp.split(";");
		for (String s: items) {
			if (s.trim().startsWith("fileName")) {
				return s.substring(s.indexOf("=") + 2, s.length() -1);
			}
		}
		return "";
	}
}
