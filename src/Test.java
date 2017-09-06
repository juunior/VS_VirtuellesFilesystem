import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Test extends Remote {
    String sayTest() throws RemoteException;
}