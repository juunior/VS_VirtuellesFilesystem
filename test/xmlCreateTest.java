import htw.vs.virtuellesFileSystem.xmlPathCreate;
import org.jdom2.Document;

import java.io.FileNotFoundException;
import java.nio.file.NotDirectoryException;

public class xmlCreateTest {
    public static void main(String[] args) {
        xmlPathCreate jds = new xmlPathCreate();
        Document doc = jds.createDoc("VSFS");
        try {
            jds.writeDoc(doc, "/home/kai/studium/");
        } catch (FileNotFoundException e) {
            System.out.println("File nicht gefunden");
        } catch (NotDirectoryException e) {
            System.out.println("Pfad ist kein Ordner");
        }
        jds.writeXML(doc);
    }
}
