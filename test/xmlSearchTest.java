import htw.vs.virtuellesFileSystem.*;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NotDirectoryException;

public class xmlSearchTest {
    public static void main(String[] args) throws org.xml.sax.SAXException, IOException,
            ParserConfigurationException, TransformerException {

        try {
            xmlPathCreate.createXML("C:\\VS1");
        } catch (FileNotFoundException e) {
            System.out.println("File nicht gefunden");
        } catch (NotDirectoryException e) {
            System.out.println("Pfad ist kein Ordner");
        }
        xmlPathCreate.detectOS();
        String str = "directoryStructure.xml";
        searchXML.search(str);
//        searchXML.addID();
//        searchXML.xmlDiffs();
//        searchXML.findRename();

        HashCode h = searchXML.hashC();
        System.out.println(searchXML.hash(h));
    }
}
