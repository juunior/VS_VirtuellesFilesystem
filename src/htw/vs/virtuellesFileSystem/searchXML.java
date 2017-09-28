package htw.vs.virtuellesFileSystem;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class searchXML {

    /**
     * search sucht in xml-Datei nach Datei/Ordnernamen
     *
     * @param str   zu suchender Name
     * @see ParserConfigurationException
     * @see IOException
     * @see SAXException
     */
    //TODO kann man als liveSuche verwenden, in Verbindung mit KeyEvents im Searchfield
    public static void search(String str) throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document document = docBuilder.parse(new File("xmlTest.xml"));

        Set<String> substringmapping = new HashSet<>();
        str = xmlPathCreate.removeIllegalCharacter(str);

        try(BufferedReader br = new BufferedReader(new FileReader("xmlTest.xml"))) {
            for(String line; (line = br.readLine()) != null; ) {
                if (line.contains(str) && line.contains("name=\"")){
                    substringmapping.add(line.substring((line.indexOf("<") + 1),line.indexOf("name") - 1));
                }
            }
        }

        for (String search : substringmapping) {

            NodeList nodeList = document.getElementsByTagName(search);

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
                    System.out.println("-------------------------------------------------------------------------------");
                }
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
        StringBuilder str = new StringBuilder(xmlPathCreate.DELIMITER + node.getNodeName());

        do {
            tmp = tmp.getParentNode();
            if (tmp != null) {
                str.insert(0, xmlPathCreate.DELIMITER + tmp.getNodeName());
            }
        } while (tmp.getParentNode() != null);
        str = new StringBuilder(str.substring(10));// cut first #document
        return (str.toString());
    }
}
