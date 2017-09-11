import com.google.common.hash.HashCode;

import java.io.*;
import java.nio.file.Files;
import java.rmi.Remote;
import java.rmi.RemoteException;
import com.healthmarketscience.rmiio.*;

public interface Hello extends Remote {
    String sayHello() throws RemoteException;
    String sayHello2() throws RemoteException;
    boolean hash(HashCode message) throws RemoteException;
    //RemoteInputStream updateXML() throws IOException,RemoteException;
    void sendXML(RemoteInputStream ristream) throws IOException;
}