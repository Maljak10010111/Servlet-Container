import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;




public class HttpServer {
    public static final String web_root = "C:/SP";
    static ExecutorService threadPool;
    ServerSocket serverSoc= null;
    

    public static void main(String[] args) throws Exception {
        int port = 7654;
        int threadPoolSize = 2;

        HttpServer server = new HttpServer();
        ServletHashTable hashTable = new ServletHashTable();
        
        MgmConsole console = new MgmConsole();
        console.start();
        server.methodStart(port, threadPoolSize);
        }


    

    public void methodStart(int port, int sizeOfThrPool) {
        threadPool = Executors.newFixedThreadPool(sizeOfThrPool);
        try {
            serverSoc = new ServerSocket(port, 1 , InetAddress.getByName("127.0.0.1")); 

        }catch(Exception e){
            e.printStackTrace();
            System.exit(-1);
        }
        while(true){
                Socket socket = null;
                try{
                    socket = serverSoc.accept();
                    
                    Connection connection = new Connection(socket);
                    threadPool.execute(connection);
                  }catch(Exception e){
                    e.printStackTrace();
                 }
                 
        }
       }

    }    
    

