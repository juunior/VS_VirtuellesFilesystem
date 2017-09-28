package htw.vs.virtuellesFileSystem;

import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import com.google.common.hash.*;
import static com.google.common.io.Files.asByteSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
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
     * @param str zu suchender Name
     * @see ParserConfigurationException
     * @see IOException
     * @see SAXException
     */
    //TODO kann man als liveSuche verwenden, in Verbindung mit KeyEvents im Searchfield
    public static void search(String str) throws ParserConfigurationException, IOException, SAXException {

        Document document = getxmlFile();

        Set<String> substringmapping = new HashSet<>();
        str = xmlPathCreate.removeIllegalCharacter(str);

        try (BufferedReader br = new BufferedReader(new FileReader("xmlTest.xml"))) {
            for (String line; (line = br.readLine()) != null; ) {
                if (line.contains(str) && line.contains("name=\"")) {
                    substringmapping.add(line.substring((line.indexOf("<") + 1), line.indexOf("name") - 1));
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

    static Document getxmlFile() throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        return docBuilder.parse(new File("xmlTest.xml"));
    }

    public static void findRename() throws ParserConfigurationException, SAXException, IOException {
        Document document = getxmlFile();
        Element e = document.getElementById("rename");

        System.out.println(e.getAttribute("name"));
    }

    public static void addID() throws ParserConfigurationException, SAXException, IOException {
        Transformer transformer = null;
        try {
            transformer = TransformerFactory.newInstance().newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = getxmlFile();
        Result output = new StreamResult(new File("xmlTest.xml"));
        Source input = new DOMSource(doc);

        try {
            transformer.transform(input, output);
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        NodeList nodeList = doc.getElementsByTagName("netio132_punkt_zip");

        Element rename = (Element) nodeList.item(0);
        rename.setIdAttribute("rename", true);
        rename.setAttribute("rename", "netio");
        System.out.println(rename.getTagName());

        output = new StreamResult(new File("output.xml"));
        input = new DOMSource(doc);

        try {
            transformer.transform(input, output);
        } catch (TransformerException e) {
            e.printStackTrace();
        }


//        Format format = Format.getPrettyFormat();
//        format.setIndent("\t");
//        try (FileOutputStream fos = new FileOutputStream(new File("xmlTest.xml"))) {
//            XMLOutputter op = new XMLOutputter(format);
//            op.output(doc, fos);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //TODO try with jdom also
    }

    static List<String> fileToLines(String filename) {
        List<String> lines = new ArrayList<>();


        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            for (String line; (line = br.readLine()) != null; ) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    public static void xmlDiffs() {
        List<String> original = fileToLines("xmlTest.xml");
        List<String> newer = fileToLines("output.xml");

        List<String> diff = new ArrayList<>();
        for (int i = 0; i < original.size(); i++) {
            String line = original.get(i);
            if (!line.equals(newer.get(i))) {
                diff.add(line);
            }
        }
        System.out.println(diff);
    }

    /**
     * zieht weitere Informationen aus xml-Datei
     *
     * @param node momentaner knoten
     * @param str  welches element soll ausgegeben weden
     * @return Element, kann ausgegeben werden
     */
    private static String getAttributeByString(Node node, String str) {
        Element e = (Element) node;
        return e.getAttribute(str);
    }

    /**
     * setzt aus einzelnen Ordnernamen String in richtiger Reihenfolge zusammen
     * entfernt unbrauchbares [#document] am Anfang
     *
     * @param node momentaner knoten
     * @return String
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

    /**
     * generiert Hashcode
     * @return  HashCode
     */
    public static HashCode hashC() {
        File fileC = new File("xmlTest.xml");
        HashFunction hfC = Hashing.sha256();
        HashCode hcC = null;
        try {
            hcC = asByteSource(fileC).hash(hfC);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hcC;
    }

    /**
     * ueberprueft uebergebenen Hashcode mit angegebenem File
     * @param message
     * @return
     */
    public static boolean hash(HashCode message) {
        File file = new File("xmlTest.xml");
        HashFunction hf = Hashing.sha256();
        HashCode hc = null;
        try {
            hc = asByteSource(file).hash(hf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message.equals(hc);
    }
}
