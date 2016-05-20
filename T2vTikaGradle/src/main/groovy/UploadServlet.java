
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
//import javax.servlet.annotation.MultipartConfig;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.Part;



import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;

/**
 * Servlet implementation class Upload
 */
// @WebServlet("/Upload")
// @MultipartConfig
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// String description = request.getParameter("description"); //

		System.out.println("in  upload doppost ");
		ServletFileUpload upload = new ServletFileUpload();
		FileItemIterator iter;
	
		String s = "failed to parse file ";
		try {
			iter = upload.getItemIterator(request);
			FileItemStream fileItem = iter.next();
			InputStream i = fileItem.openStream();

			Tika t = new Tika();			
			s = t.parseToString(i);			

		} catch (FileUploadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (TikaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println("in  upload doppost datta " + s );
		

		GetJSONpairs gt = new GetJSONpairs();
		String set = gt.getWordPairs(s);
		//System.out.println(set.toString());
		
		response.getWriter().println( set);
		
		//response.getWriter().println(s);
	}

	// private static String getFilename(Part part) {
	// for (String cd : part.getHeader("content-disposition").split(";")) {
	// if (cd.trim().startsWith("filename")) {
	// String filename = cd.substring(cd.indexOf('=') + 1).trim()
	// .replace("\"", "");
	// return filename.substring(filename.lastIndexOf('/') + 1)
	// .substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
	// }
	// }
	// return null;
	// }
}
