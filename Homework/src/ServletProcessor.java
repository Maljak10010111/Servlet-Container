import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

public class ServletProcessor {
   
     public void process(Request request, Response response){
        String uri = request.getUri();
        String servletName = uri.substring(uri.lastIndexOf("/") + 1); 
        if(!ServletHashTable.contains(servletName)){
            response.errorMessage();
        }else{
                HttpServlet servlet = ServletHashTable.get(servletName);
            try {
                servlet.service((ServletRequest)request, (ServletResponse)response);
            } catch (Exception e) {
                System.out.println(e.toString());
            }catch (Throwable e) {
				System.out.println(e.toString());
            }
        }
     }
    
}