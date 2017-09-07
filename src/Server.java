import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Server implements Hello, Test {

    public Server() {}

    public String sayHello() {
        return "Hello, world!";
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