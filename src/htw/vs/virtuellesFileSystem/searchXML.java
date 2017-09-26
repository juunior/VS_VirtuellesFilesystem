package htw.vs.virtuellesFileSystem;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class searchXML {

    /**
     * search sucht in xml-Datei nach Datei/Ordnernamen
     *
     * @param str   zu suchender Name
     * @param document  zu durchsuchende Datei
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static void search(String str, Document document) throws ParserConfigurationException, IOException, SAXException {

        str = xmlPathCreate.removeIllegalCharacter(str);
        NodeList nodeList = document.getElementsByTagName(str);

        //Geht die Datei durch und ueberprueft String
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {

                //Beispielausgabe, kann geloescht werden
                System.out.println(getAttributeByString(node, "name"));
                System.out.println("file: " + getAttributeByString(node, "file"));
                System.out.println("directory: " + getAttributeByString(node, "directory"));
                System.out.print(getAttributeByString(node, "Host") + ":");
                System.out.println(xmlPathCreate.revertIllegalCharacter(nodeToString(node)));
            }
        }
    }

    /**
     * zieht weitere Informationen aus xml-Datei
     * @param node  momentaner knoten
     * @param str   welches element soll ausgegeben weden
     * @return      Element, kann ausgegeben werden
     */
    private static String getAttributeByString(Node node, String str) {
        Element e = (Element) node;
        return e.getAttribute(str);
    }

    /**
     * setzt aus einzelnen Ordnernamen String in richtiger Reihenfolge zusammen
     * entfernt unbrauchbares [#document] am Anfang
     * @param node  momentaner knoten
     * @return      String
     */
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
