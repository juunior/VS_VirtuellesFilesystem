import java.io.*;
import java.nio.file.Files;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import com.google.common.hash.*;


import static com.google.common.io.Files.asByteSource;
import static com.google.common.io.Files.newReader;


public class Server implements Hello, Test {
    private Server() {}

    public static void main(String args[]) {

        try {
            Server obj = new Server();
            Server obj2 = new Server();
            Hello stub = (Hello) UnicastRemoteObject.exportObject(obj, 0);
            Test stub2 = (Test) UnicastRemoteObject.exportObject(obj2, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.createRegistry(1089);
            registry.bind("Hello", stub);
            registry.bind("Test", stub2);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

    /* public FileInputStream updateXML() {
        BufferedInputStream bis = null;
        FileInputStream in = null;
        File file = new File("remoteFiles\\Testhash.java");
        try {
            in = new FileInputStream(file);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return in;
    }*/

    /*  Funktion zum Bilden des hash über die serverseitige Datei sowie den Vergleich.
        Antwort: boolean.
     */
    public boolean hash(HashCode message) {
        File file = new File("remoteFiles\\Testhash.java");
        HashFunction hf = Hashing.sha256();
        HashCode hc = null;
        try {
            hc = asByteSource(file).hash(hf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message.equals(hc);
    }

    public String sayHello() {
        return "Hello, world!";
    }

    public String sayHello2() {
        return "Hello, world2!";
    }

    public String sayTest() {
        return "Hello, Test!";
    }
}