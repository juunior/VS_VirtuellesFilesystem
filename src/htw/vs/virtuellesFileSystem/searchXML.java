package htw.vs.virtuellesFileSystem;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;

public class searchXML {

    public static void main(String[] args) throws org.xml.sax.SAXException, IOException,
            ParserConfigurationException, TransformerException {

        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document document = docBuilder.parse(new File("xmlTest.xml"));

        // File Name aendern
        String str = "test";
        suche(str, document);
    }


    public static void suche(String str, Document document) throws ParserConfigurationException, IOException, SAXException {

        NodeList nodeList = document.getElementsByTagName(str);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                System.out.println(nodeToString(node));
            }
        }
    }

    public static String nodeToString(Node node) {
        Node tmp = node;
        String str = "/" + node.getNodeName();

        do {
            tmp = tmp.getParentNode();
            if (tmp != null) {
                str = "/" + tmp.getNodeName() + str;
            }
        } while (tmp.getParentNode() != null);
        str = str.substring(10);// cut first #document
        return(str);
    }
}
