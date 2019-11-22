package ch12;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.util.Enumeration;

/*
 * If you want to try the web.xml version of building a WAR file
 * for use in Tomcat, you can use these XML blocks. (The web.xml
 * file in the ch12 examples folder already has these included.)
    <servlet>
        <servlet-name>showsession1</servlet-name>
        <servlet-class>ch12.ShowSession</servlet-class>
    </servlet>

    <servlet-mapping>
        <servlet-name>showsession1</servlet-name>
        <url-pattern>/showsession</url-pattern>
    </servlet-mapping>
 */
@WebServlet(urlPatterns={"/showsession"})
public class ShowSession extends HttpServlet
{
    public void doPost(
		HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException
	{ 
        doGet( request, response );
    }

    public void doGet(
		HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException 
	{
        HttpSession session = request.getSession(  );
        boolean clear = request.getParameter("clear") != null;
		if ( clear )
			session.invalidate();
		else {
			String name = request.getParameter("Name");
			String value = request.getParameter("Value");
			if ( name != null && value != null )
				session.setAttribute( name, value );
		}

        response.setContentType("text/html");
        PrintWriter out = response.getWriter(  );
        out.println(
          "<html><head><title>Show Session</title></head><body>");

		if ( clear )
        	out.println("<h1>Session Cleared:</h1>");
		else {
			out.println("<h1>In this session:</h1><ul>");

			Enumeration names = session.getAttributeNames();
			while ( names.hasMoreElements() ) {
				String name = (String)names.nextElement();
				out.println( "<li>"+name+" = " +session.getAttribute( name ) );
			}
		}

        out.println(
          "</ul><p><hr><h1>Add String</h1>"
          + "<form method=\"POST\" action=\""
          + request.getRequestURI(  ) +"\">"
          + "Name: <input name=\"Name\" size=20><br>"
          + "Value: <input name=\"Value\" size=20><br>"
          + "<br><input type=\"submit\" value=\"Submit\">"
          + "<input type=\"submit\" name=\"clear\" value=\"Clear\"></form>"
        );
    }
}
