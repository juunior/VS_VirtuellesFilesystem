//package htw.vs.virtuellesFileSystem;
//
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//import org.xml.sax.SAXException;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;
//import java.io.File;
//import java.io.IOException;
//
//public class searchXML {
//
//    /**
//     * search sucht in xml-Datei nach Datei/Ordnernamen
//     *
//     * @param str zu suchender Name
//     * @see ParserConfigurationException
//     * @see IOException
//     * @see SAXException
//     */
//    //TODO kann man als liveSuche verwenden, in Verbindung mit KeyEvents im Searchfield
//    public static void search(String str) throws ParserConfigurationException, IOException, SAXException {
//
//        Document document = getxmlFile("xmlTest.xml");
//
//        NodeList nodeList = document.getElementsByTagName(str);
//
//        for (int i = 0; i < nodeList.getLength(); i++) {
//            Node node = nodeList.item(i);
//
//            if (isDirectory(node)) {
//                String dirToCreate = xmlPathCreate.revertIllegalCharacter(nodeToString(node));
//                System.out.println(dirToCreate);
//                System.out.println("-------------------------------------------------------------------------------");
//
//            } else System.out.println("Kein Ordner");
//        }
//    }
//
//    /**
//     * prueft, ob uebergebener Knoten directory/file ist
//     * @param node
//     * @return
//     */
//    private static boolean isDirectory(Node node) {
//        Element e = (Element) node;
//        boolean b = Boolean.parseBoolean(e.getAttribute("directory"));
//        if (!b) {
//            return false;
//        } else return true;
//    }
//
//    static Document getxmlFile(String xml) throws IOException, SAXException, ParserConfigurationException {
//        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
//        return docBuilder.parse(new File(xml));
//    }
//
//    /**
//     * zieht weitere Informationen aus xml-Datei
//     *
//     * @param node momentaner knoten
//     * @param str  welches element soll ausgegeben weden
//     * @return Element, kann ausgegeben werden
//     */
//    private static String getAttributeByString(Node node, String str) {
//        Element e = (Element) node;
//        return e.getAttribute(str);
//    }
//
//    /**
//     * setzt aus einzelnen Ordnernamen String in richtiger Reihenfolge zusammen
//     * entfernt unbrauchbares [#document] am Anfang
//     *
//     * @param node momentaner knoten
//     * @return String
//     */
//    private static String nodeToString(Node node) {
//        Node tmp = node;
//        StringBuilder str = new StringBuilder(xmlPathCreate.DELIMITER + node.getNodeName());
//
//        do {
//            tmp = tmp.getParentNode();
//            if (tmp != null) {
//                str.insert(0, xmlPathCreate.DELIMITER + tmp.getNodeName());
//            }
//        } while (tmp.getParentNode() != null);
//        str = new StringBuilder(str.substring(10));// cut first #document
//        String dir = str.toString();
//        dir = dir.replace("/VSFS/", xmlPathCreate.ROOTDIR);
//        return (dir);
//    }
//}
