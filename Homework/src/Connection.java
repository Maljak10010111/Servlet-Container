import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Connection implements Runnable{
    
    Socket socket;

    Connection(Socket socket){
        this.socket=socket;
    }

    public void run(){
        
        InputStream input= null;
        OutputStream output = null;
          try {
            
           input = socket.getInputStream();
           output = socket.getOutputStream();
           Request request = new Request(input);
           request.parse();
           Response response = new Response(output);
           response.setRequest(request);

           if(request.getUri() != null){
               if(request.getUri().startsWith("/servlet")){
                   ServletProcessor processor = new ServletProcessor();
                   processor.process(request, response);
               }else{
                   MyStaticResponseToClient processor = new MyStaticResponseToClient();
                   processor.process(request, response);
               }
           }
           
           socket.close();
            } catch (Exception e) {
                e.printStackTrace();
                
         }
 }
}

