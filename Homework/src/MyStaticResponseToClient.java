import java.io.IOException;

public class MyStaticResponseToClient {
    public void process(Request request, Response response){
        try {
            response.sendStaticResponseToClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
