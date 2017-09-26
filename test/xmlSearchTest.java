import htw.vs.virtuellesFileSystem.searchXML;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;

public class xmlSearchTest {
    public static void main(String[] args) throws org.xml.sax.SAXException, IOException,
            ParserConfigurationException, TransformerException {

        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document document = docBuilder.parse(new File("xmlTest.xml"));

        // File Name aendern
        String str = "dev-jan";
        searchXML.search(str, document);
    }
}
