package ch12;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/*
 * If you want to try the web.xml version of building a WAR file
 * for use in Tomcat, you can use these XML blocks. (The web.xml
 * file in the ch12 examples folder already has these included.)
    <servlet>
        <servlet-name>helloclient1</servlet-name>
        <servlet-class>ch12.HelloClient</servlet-class>
    </servlet>

    <servlet-mapping>
        <servlet-name>helloclient1</servlet-name>
        <url-pattern>/hello</url-pattern>
    </servlet-mapping>
 *
 * If you do try the web.xml file approach, be sure to comment out
 * the @WebServlet line below or make the url pattern in the XML
 * file different than the ones listed here in the annotation.
 */
@WebServlet(urlPatterns={"/hello"})
public class HelloClient extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html"); // must come first
        PrintWriter out = response.getWriter();
        out.println(
                "<html><head><title>Hello Client!</title></head><body>"
                        + "<h1>Hello Client!</h1>"
                        + "</body></html>" );
    }
}