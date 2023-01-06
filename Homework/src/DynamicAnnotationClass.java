import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.RetentionPolicy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Retention;


@Retention(RetentionPolicy.RUNTIME)
@interface MyAnnotation {
    String name();
    String value();
}


@MyAnnotation(name =  "URLServletName", value = "myservlet2")
public class DynamicAnnotationClass extends HttpServlet{
    
    private String message;
    

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String alphabet = "ABCDEFGHIKLMNOPQRSTVXYZ";
        PrintWriter out = response.getWriter();
        message = "HTTP/1.1 200 OK\r\n"+
				"Content-Type: text/html\r\n"+
				"Content-Length: 65\r\n" +
				"\r\n";
    
        out.println(message);
     for(int i = 0; i < alphabet.length(); i++){
        out.println(alphabet.charAt(i));
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
     }
    
         out.close();
    }
}

