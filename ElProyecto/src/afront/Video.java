package afront;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Video")
@MultipartConfig()
public class Video extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public Video() {
		// TODO Auto-generated constructor stub	
	}

	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    //String videotype = "video/mp4";
		//response.setContentType(videotype);
		resp.setContentType("video/mp4");
		String videot = req.getParameter("videot");
		System.out.println(videot);
		ServletOutputStream out = resp.getOutputStream();
		FileInputStream in = new FileInputStream(videot);
		byte[] bytes = new byte[1024];
		int read =0;
		while ((read=in.read(bytes))!=-1) {
			out.write(bytes, 0, read);
		}
		if (in !=null)in.close();
		if (out !=null)out.close();
		
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = resp.getWriter();
		resp.setContentType("application/json");
		out.println(VideoController.setVideo(
				req.getPart("video"),
				req.getParameter("title"),
			    req.getPart("thumnail"),
				req.getParameter("description"),
				req.getSession(false)
				));
	}



	
}
