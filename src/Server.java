import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import com.google.common.hash.*;


import static com.google.common.io.Files.asByteSource;


public class Server implements Hello, Test {
    private Server() {}
    public String sayHello() {
        return "Hello, world!";
    }
    public boolean hash(HashCode message) {
        File file = new File("Testhash.java");
        HashFunction hf = Hashing.sha512();
        HashCode hc = null;
        try {
            hc = asByteSource(file).hash(hf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message.equals(hc);

    }

    public String sayHello2() { return "Hello, world2!"; }

    public String sayTest() {
        return "Hello, Test!";
    }

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
}