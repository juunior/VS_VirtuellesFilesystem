import htw.vs.virtuellesFileSystem.xmlPathCreate;
import org.jdom2.Document;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NotDirectoryException;

public class xmlCreateTest {
    public static void main(String[] args) throws IOException {
        try {
            xmlPathCreate.createXML("/home/kai/studium/PTG");
        } catch (FileNotFoundException e) {
            System.out.println("File nicht gefunden");
        } catch (NotDirectoryException e) {
            System.out.println("Pfad ist kein Ordner");
        }

    }
}
