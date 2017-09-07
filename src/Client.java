import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

    private Client() {}

    public static void main(String[] args) {

        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(1089);
            Hello stub = (Hello) registry.lookup("Hello");
            Test stub2 = (Test) registry.lookup("Test");
            String response = stub.sayHello();
            System.out.println("response: " + response);
            response = stub.sayHello2();
            System.out.println("response: " + response);
            response = stub2.sayTest();
            System.out.println("response: " + response);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}