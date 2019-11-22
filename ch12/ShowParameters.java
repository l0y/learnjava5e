package ch12;

import java.io.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.util.*;

/*
 * If you want to try the web.xml version of building a WAR file
 * for use in Tomcat, you can use these XML blocks. (The web.xml
 * file in the ch12 examples folder already has these included.)
    <servlet>
        <servlet-name>showparameters1</servlet-name>
        <servlet-class>ch12.ShowParameters</servlet-class>
    </servlet>

    <servlet-mapping>
        <servlet-name>showparameters1</servlet-name>
        <url-pattern>/showparameters</url-pattern>
    </servlet-mapping>
 */
@WebServlet(urlPatterns={"/showparameters"})
public class ShowParameters extends HttpServlet
{
    public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException
    {
        showRequestParameters( request, response );
    }

    void showRequestParameters(HttpServletRequest request, HttpServletResponse response)
        throws IOException
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println(
          "<html><head><title>Show Parameters</title></head><body>"
          + "<h1>Parameters</h1><ul>");

        Map<String, String[]> params = request.getParameterMap();
        for ( String name : params.keySet() )
        {
            String [] values = params.get( name );
            out.println("<li>"+ name +" = "+ Arrays.asList(values) );
        }

        out.close(  );
    }
}
