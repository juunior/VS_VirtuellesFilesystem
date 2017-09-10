import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.io.File;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static com.google.common.io.Files.asByteSource;

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
            if(stub.hash(hashC())){
                System.out.println("True");
            }
            else{
                System.out.println("False or error");
            }
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    private static HashCode hashC() {
        File fileC = new File("TesthashC.java");
        HashFunction hfC = Hashing.sha512();
        HashCode hcC = null;
        try {
            hcC = asByteSource(fileC).hash(hfC);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return hcC;

    }

}