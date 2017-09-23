package htw.vs.virtuellesFileSystem;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class searchXML {


    public static void suche(String str, Document document) throws ParserConfigurationException, IOException, SAXException {
        str = xmlPathCreate.removeIllegalCharacter(str);

        str = xmlPathCreate.removeIllegalCharacter(str);

        NodeList nodeList = document.getElementsByTagName(str);

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                System.out.println(xmlPathCreate.rewertIllegalCharacter(nodeToString(node)));
            }
        }
    }



    private static String nodeToString(Node node) {
        Node tmp = node;
        String str = "/" + node.getNodeName();

        do {
            tmp = tmp.getParentNode();
            if (tmp != null) {
                str = "/" + tmp.getNodeName() + str;
            }
        } while (tmp.getParentNode() != null);
        str = str.substring(10);// cut first #document
        return (str);
    }
}
