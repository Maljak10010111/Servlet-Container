import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;

import javax.servlet.http.HttpServlet;

public class MgmConsole extends Thread{


    String firstWordOfCmd(String command){
        if(command.contains(" ")){
            int index = command.indexOf(" ");
            String cmd = command.substring(0, index);
            return cmd;
        }else
            return command;
    }

    String secondWordOfCmd(String command){
        if(command.contains(" ")){
            int index = command.indexOf(" ");
            String cmd = command.substring(index + 1, command.length());
            return cmd;
        }else
            return command;
    }

    public void remove(String servletName){
          if(servletName.equals("remove")){
              System.out.println("You have to write the name of the servlet if you want to remove it!");
          }else{
             if(!ServletHashTable.contains(servletName)){
                System.out.println("Servlet: "+servletName+" is not in the internal repository!");
                 
             }else{
                ServletHashTable.remove(servletName);
                System.out.println("Servlet: "+servletName+" has been removed!");
                }
             }
    }



    // first solution of checking wheter servlet is in the external repository
    public boolean isInTheExternalRepository(String servletName){
        String servletExternalName = new String(HttpServer.web_root + File.separator + "servletrepository" + File.separator + "myservlet").trim();
        int lastIndexOf = servletExternalName.lastIndexOf("/");
            if(servletExternalName.substring(lastIndexOf + 1, servletExternalName.length()).equals(servletName)){
                return true;
             } 
            return false;
    }

    // second solution of checking wheter servlet is in the external repository
    public boolean isServletInTheExternalRepository(String servletName){
        String servName = new String(HttpServer.web_root + File.separator + "servletrepository" + File.separator + servletName).trim();
        File file = new File(servName);
        if((file.exists() && file.isDirectory())){
            return true;
        }
        return false;
    }


    public void load(String servletName){
        if((!ServletHashTable.contains(servletName)) && (isServletInTheExternalRepository(servletName) == true)){
            String servletClassName = null;
            String servletRepository = new String(HttpServer.web_root + File.separator + "servletrepository").trim(); // + File.separrator
            String servletMyServlet = new String(servletRepository + File.separator+ servletName).trim();

            try {
                String metadata = servletMyServlet + File.separator + "metadata.txt";
                BufferedReader reader = new BufferedReader(new FileReader(metadata));
                String readMeta = reader.readLine();
                while(readMeta != null){
                    if(readMeta.contains("=")){
                        int index = readMeta.indexOf("=");
                        servletClassName = readMeta.substring(index + 1, readMeta.length());
                    }
                    readMeta = reader.readLine();
                }
            } catch (FileNotFoundException e) {
                    System.err.println("File not found!");
            }catch(IOException e){
                    e.printStackTrace();
            }

            URLClassLoader loader = null;
            try{
                URL[] urls = new URL[1];
                urls[0] = new URL("file:"+ servletMyServlet + File.separator);
                loader = new URLClassLoader(urls);
            }catch(IOException e){
                System.err.println("Loader error!");
                //e.printStackTrace();
            }

            Class myClass = null;
            try {
                myClass = loader.loadClass(servletClassName);
            } catch (ClassNotFoundException e) {
                System.err.println("Class not found!");
            }

            HttpServlet servlet = null;
            try {
                servlet = (HttpServlet) myClass.newInstance();
            } catch (Exception e) {
                System.err.println("Servlet error!");
                //e.printStackTrace();
            }
            ServletHashTable.put(servletName, servlet);
            System.out.println("Servlet: "+servletName+ " has been added to internal repository!"+" ");
            
        }else{
            System.err.println("This servlet: "+servletName+" is either in the internal repository or it is not in the external repository!");
            return;
        }
    }

    public void loadWithAnnotation(String servletName){
        if(!ServletHashTable.contains(servletName)){
            String servletRepository = new String(HttpServer.web_root + File.separator + "servletrepository"+ File.separator).trim(); 
            String targetClass = new String(servletRepository + File.separator+"class").trim();
            Class myClass = null;
            boolean isFound =false;

            File folder = new File(targetClass);
            File[] listOfFiles = folder.listFiles();
            for(int i = 0; i < listOfFiles.length;i++){
                if(listOfFiles[i].getName().endsWith(".class")){
                    URLClassLoader loader = null;
                    try {
                        URL[] urls = new URL[1];
                        urls[0] = new URL("file:"+ targetClass + File.separator);
                        loader = new URLClassLoader(urls);
                        
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                    try {
                        myClass = loader.loadClass(listOfFiles[i].getName().replace(".class", ""));
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                    Annotation[] listOfAnn = myClass.getAnnotations();
                    MyAnnotation myAnn = null;
                    if(listOfAnn.length != 0){
                        for(int k=0; k<listOfAnn.length; k++){
                            myAnn = (MyAnnotation) listOfAnn[k];
                        }
                        if(myAnn.name().equals("URLServletName") && myAnn.value().equals(servletName)){
                            isFound = true;
                            break;
                        }
                    }
                    if(isFound)
                    break;
                }
                }
                HttpServlet servlet = null;
                try {
                    servlet = (HttpServlet) myClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ServletHashTable.put(servletName, servlet);
                System.out.println("Servlet: "+servletName+ " has been added to internal repository!"+" ");
            }
           

    }

    void executeList(){
        Enumeration<String> e = (Enumeration<String>)ServletHashTable.e();
        while(e.hasMoreElements()){
            System.out.println("  " + e.nextElement());
        }
    }

    void executeCommand(String command) {
		if (firstWordOfCmd(command).equals("load")){
            load(secondWordOfCmd(command));
            return;
        } 
		if (firstWordOfCmd(command).equals("remove")){
            remove(secondWordOfCmd(command));
			return;
        }
        if (firstWordOfCmd(command).equals("list")) {
            executeList();
			return;
		}
        if (firstWordOfCmd(command).equals("load-with-annotation")){
            loadWithAnnotation(secondWordOfCmd(command));
            return;
        }
		if (firstWordOfCmd(command).equals("quit")) {
            System.exit(-1);
        }

		System.out.println("Commands which are supported are: load servlet, remove servlet, load-with-annotation servlet, list, quit");
	}

    public void run(){
        String command= null;
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		System.out.print ("Command: ");
		try {
			command  = bufferedReader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		executeCommand(command);
		while (!command.equals("quit")){
			System.out.print ("Command: ");
			try {
				command  = bufferedReader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			executeCommand(command);
		}
        Shutdown.flag = true;
    }
    
}
