import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class Response implements HttpServletResponse{

   
    Request request;
    OutputStream output;
    PrintWriter print_writer;

    public Response(OutputStream outputStream){
        this.output = outputStream;
    }

    public void setRequest(Request request){
        this.request = request;
    
    }
    public void errorMessage() {
		String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
		"Content-Type: text/html\r\n" +
		"Content-Length: 35\r\n" +
		"\r\n" +
		"<h1>File Not Found</h1>";
		try {
			output.write(errorMessage.getBytes());
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}
    public void sendStaticResponseToClient() throws IOException{
        String headerHttpBeforeLength = "HTTP/1.1 200 OK"+"\r\n"+"Content-Type: text/html"+"\r\n"+"Content-Length: ";
		String headerHttpAfterLength = "\r\n" + "\r\n";
        try {
            String fileName = HttpServer.web_root +File.separator+"staticcontentrepository"+ request.getUri() ; 
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while((line = bufferedReader.readLine()) != null){ 
                stringBuilder.append(line);
            }
            bufferedReader.close();
            int length = stringBuilder.toString().length();
            String outMsg = headerHttpBeforeLength + Integer.toString(length) + headerHttpAfterLength + stringBuilder.toString();

            output.write(outMsg.getBytes());
        } catch (Exception e) {
            String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
			"Content-Type: text/html\r\n" +
			"Content-Length: 23\r\n" +
			"\r\n" +
			"<h1>File Not Found</h1>";
            output.write(errorMessage.getBytes());
        }
    }

    @Override
    public void flushBuffer() throws IOException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int getBufferSize() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getCharacterEncoding() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getContentType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Locale getLocale() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
            print_writer = new PrintWriter(output, true);
        return print_writer;
    }

    @Override
    public boolean isCommitted() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void reset() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void resetBuffer() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setBufferSize(int arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setCharacterEncoding(String arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setContentLength(int arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setContentLengthLong(long arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setContentType(String arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setLocale(Locale arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addCookie(Cookie arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addDateHeader(String arg0, long arg1) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addHeader(String arg0, String arg1) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addIntHeader(String arg0, int arg1) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean containsHeader(String arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String encodeRedirectURL(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String encodeRedirectUrl(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String encodeURL(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String encodeUrl(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getHeader(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<String> getHeaderNames() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<String> getHeaders(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getStatus() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void sendError(int arg0) throws IOException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void sendError(int arg0, String arg1) throws IOException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void sendRedirect(String arg0) throws IOException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setDateHeader(String arg0, long arg1) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setHeader(String arg0, String arg1) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setIntHeader(String arg0, int arg1) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setStatus(int arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setStatus(int arg0, String arg1) {
        // TODO Auto-generated method stub
        
    }
    
}
