import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.http.HttpServlet;

public class ServletHashTable {
    
    static Hashtable<String, HttpServlet> hashTable;

    ServletHashTable(){
        hashTable = new Hashtable<String, HttpServlet>();
    }
    
    static void put(String string, HttpServlet h){

        hashTable.put(string, h);
    } 

    static boolean contains(String string){
        return hashTable.containsKey(string);
    }

    static HttpServlet get(String string){
        return hashTable.get(string);
    }

    static void remove(String string){
        hashTable.remove(string);
    }
    static Enumeration<String> e(){
        return ServletHashTable.hashTable.keys();
    }
}