package processText

import groovy.servlet.GroovyServlet

import javax.servlet.ServletConfig
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import processText.GenerateWordLinks

public class TextToNetwork extends GroovyServlet {

	void init(ServletConfig config) {
		System.out.println " Text Servlet initialized"
	}	

	void service(HttpServletRequest request, HttpServletResponse response) {
		def text = request.getParameter("text");
		def m = request.getParameterMap();
		
		println "in textin parameter map is $m"
	
		GenerateWordLinks gw = new GenerateWordLinks(m);
		def json = gw.getJSONnetwork(text)
		println "in textToNetwork Json is $json"

		response.getWriter().println(json)
	}
}