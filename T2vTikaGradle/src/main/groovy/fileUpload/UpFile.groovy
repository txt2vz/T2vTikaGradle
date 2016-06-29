package fileUpload;


import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.commons.fileupload.FileItem;
//import org.apache.commons.fileupload.FileItemFactory;
//import org.apache.commons.fileupload.disk.DiskFileItemFactory;
//import org.apache.commons.fileupload.servlet.ServletFileUpload;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;

public class UpFile extends HttpServlet {
        private static final long serialVersionUID = 1L;
       // private final String UPLOAD_DIRECTORY = "C:/data";

        protected void doPost(HttpServletRequest request,
             HttpServletResponse response) throws ServletException, IOException {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        
      println "in upload G"

        // process only if it is multipart content
  	System.out.println("in  upload doppost ");
		ServletFileUpload upload = new ServletFileUpload();
		FileItemIterator iter;
	
		String s = "failed to parse file ";
		try {
			iter = upload.getItemIterator(request);
			FileItemStream fileItem = iter.next();
			InputStream i = fileItem.openStream();
			
			//println " i.text " +i.text

			Tika t = new Tika();			
			s = t.parseToString(i);
			
			//println "tika s $s"

		} catch (FileUploadException e1) {
			// TODO Auto-generated catch block
			println "e1 in tike $e1"
			e1.printStackTrace();
		//} catch (TikaException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		}
		
		response.getWriter().println( s); 
		
		//System.out.println("in  upload doppost datta " + s );
		

	//	GetJSONpairs gt = new GetJSONpairs();
	//	String set = gt.getWordPairs(s);
		//System.out.println(set.toString());
		
	//	response.getWriter().println( set);
		
		//response.getWriter().println(s); 
	}

}
//}