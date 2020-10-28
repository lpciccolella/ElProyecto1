package afront;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import aback.BDConexion;
import aback.LeerProperties;

public class VideoController {
	private static LeerProperties lp = LeerProperties.getInstance();
	private static BDConexion cdb = BDConexion.getIntsnaces();
	private static OutputStream fileOut;
	private static InputStream in;
	
	public void getSingleVideo() {
	}
	
	public void getAlluserVideo() {
	}
	
	public void searchVideo() {
	}
	
	public static String setVideo(Part video, String videoTitle, Part thumnail, String description, HttpSession  session) {
		// TODO Auto-generated method stub
	
		if(session !=null) {
			try {
				String videoName =getFileInfo (video, "name");
				String thumnailName = getFileInfo (thumnail, "name");
				
				//creacion del documentos
				File newFolderVideo = new File(lp.getValue2("folder") + "/" + session.getAttribute("user") + "/" + "video");
				File newFolderImage = new File(lp.getValue2("folder") + "/" + session.getAttribute("user") + "/" + "image");
				
				if(newFolderVideo.mkdirs() && newFolderImage.mkdir()) {
					System.out.println("Las carpetas del usurio" + session.getAttribute("user") + "han sido creadas");
				}
				fileOut = new FileOutputStream (new File(newFolderVideo.getAbsolutePath() + "/" + videoName));
				in = video.getInputStream();
				Upload(in);
				
				fileOut = new FileOutputStream (new File(newFolderVideo.getAbsolutePath() + "/" + thumnailName));
				in = thumnail.getInputStream();
				Upload(in);
				
				System.out.println(newFolderVideo.getAbsolutePath() + "/" +videoName);
				
				Object[] obj = {
						session.getAttribute("user"),
						newFolderVideo.getAbsolutePath() + "/" + videoName,
						videoTitle,
						newFolderImage.getAbsolutePath() + "/" + thumnail,
						description
				};
				cdb.dbPrepareStatement(lp.getValue("postvideo", obj));
				return "{\"message\": \"el video fue descargado con exito\", \"status\": 200}";
						
				}catch(IOException e) {
					e.printStackTrace();
					return "{\"message\": \"hay problema con la descarga\", \"status\": 500}";
				}catch (NullPointerException e)
				{
					e.printStackTrace();
					return "{\"message\": \"there are not file attached\", \"status\": 500}";
				}catch (Exception e) {
					e.printStackTrace();
					return "{\"message\": \"...\", \"status\": 500}";
				}finally {
					try {
						fileOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	}else {
	} return "{\"message\": \"el usuario no esta logueado o es incorrecto \", \"status\": 500}"; 
	}
	
	public void updateVideo() {
	}
	
	public void deleteVideo() {
	}
	

	private static String getFileInfo(Part part, String info) {
		// TODO Auto-generated method stub
		System.out.println(part.getHeaderNames());
			for (String content : part.getHeader("content-disposition").split(";")){
				System.out.println(content);
				if (content.trim().startsWith("filename")) {
					
					switch(info) {
					case "name":
						return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");	
					case "type":
						return content.substring(content.indexOf('.')).trim().replace("\"", "");
					}
				}
		}
			return null;
	}


		private static void Upload(InputStream in) {
			int read = 0;
			final byte[] bytes = new byte[1024];
			try {
				while ((read = in.read(bytes)) !=-1) {
					fileOut.write(bytes, 0, read);
				}
			}catch (Exception e) {
				System.out.println(e);
			}
		}	
}
