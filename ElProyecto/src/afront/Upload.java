package afront;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.swing.JOptionPane;


/**
 * Servlet implementation class Upload
 */
@MultipartConfig
@WebServlet("/Upload")
public class Upload extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SAVE_DIR = "watchVideo";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Upload() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */ 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("hello").append(request.getContextPath());
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // gets absolute path of the web application
		String appPath = request.getServletContext().getRealPath("");
        // constructs path of the directory to save uploaded file
		String savePath = appPath + File.separator + SAVE_DIR;
		System.out.println(savePath);
		
		File fsd = new File(savePath);
	
		if (!fsd.exists()) {
			fsd.mkdirs(); //mkdir: Creates the directory named by this abstract pathname.	Returns:true if and only if the directory was created; false otherwise
		}
		System.out.println("llego a la parte de file");
		for (Part mpart : request.getParts()) {
			VideoHelper.UploadVideo(mpart, savePath);
		}		
		request.setAttribute("mesanje", "subio con exito");
		response.getWriter().write("el archivo se subio con exito");
		System.out.println("ya se subio el archivo");
	}
	}

