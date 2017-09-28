package htw.vs.virtuellesFileSystem;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
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
import java.util.*;

import static com.google.common.io.Files.asByteSource;

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

        Document document = getxmlFile("xmlTest.xml");

        Set<String> substringmapping = new HashSet<>();
        str = xmlPathCreate.removeIllegalCharacter(str);

        try (BufferedReader br = new BufferedReader(new FileReader("xmlTest.xml"))) {
            for (String line; (line = br.readLine()) != null; ) {
                if (line.contains(str) && line.contains("name=\"")) {
                    substringmapping.add(line.substring((line.indexOf("<") + 1), line.indexOf("Host") - 1));
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

    static Document getxmlFile(String xml) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        return docBuilder.parse(new File(xml));
    }

    public static void findRename() throws ParserConfigurationException, SAXException, IOException {
        String toRename = xmlDiffs();
        if (!(toRename == null)) {
            String path = "";
            String newName = "";
            String name = "";
            Document doc = getxmlFile("output.xml");
            NodeList nodeList = doc.getElementsByTagName(toRename);
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    path = xmlPathCreate.revertIllegalCharacter(nodeToString(node));
                    newName = getAttributeByString(node, "rename");
                    name = getAttributeByString(node, "name");
                    System.out.println(path);
                    System.out.println(xmlPathCreate.solveIP());
                    System.out.println(getAttributeByString(node, "Host"));
                    if (Objects.equals(xmlPathCreate.solveIP(), getAttributeByString(node, "Host"))) {
                        rename(name, path, newName);
                    }


                }
            }
        }

    }

    private static void rename(String name, String path, String newName) {
        String newPath = path.substring(0, path.lastIndexOf(xmlPathCreate.DELIMITER) + 1) + newName;

        File file = new File(path);
//
        File file2 = new File(newPath);
//
//
        boolean success = file.renameTo(file2);

        if (!success) {
            System.out.println("Datei nicht umbenannt");
        } else {
            System.out.println("Datei umbenannt");
        }


    }

    public static void setToRename(String str, String newName) throws ParserConfigurationException, SAXException, IOException {
        str = xmlPathCreate.removeIllegalCharacter(str);
        String toRename = new String();
        Transformer transformer = null;
        try {
            transformer = TransformerFactory.newInstance().newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = getxmlFile("xmlTest.xml");
        Result output = new StreamResult(new File("xmlTest.xml"));
        Source input = new DOMSource(doc);

        try {
            transformer.transform(input, output);
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        NodeList nodeList = doc.getElementsByTagName(str);

        Element rename = (Element) nodeList.item(0);
        if (!(rename == null)) {
            rename.setIdAttribute("rename", true);
            rename.setAttribute("rename", newName);
        }

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

    public static String xmlDiffs() {
        List<String> original = fileToLines("xmlTest.xml");
        List<String> newer = fileToLines("output.xml");

        List<String> diff = new ArrayList<>();
        for (int i = 0; i < original.size(); i++) {
            String line = original.get(i);
            if (!line.equals(newer.get(i))) {
                diff.add(line);
            }
        }
        String different = null;
        if (diff.size() > 0) {
            different = diff.get(0);
        }
        if (!(different == null)) {
            if (!different.contains("rename=\"false\"")) {
                return null;
            } else {
                return different.substring((different.indexOf("<") + 1), different.indexOf("Host") - 1);
            }
        }
        return null;
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
        String dir = str.toString();
        dir = dir.replace(xmlPathCreate.DELIMITER + "VSFS" + xmlPathCreate.DELIMITER, xmlPathCreate.ROOTDIR);
        return (dir);
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
    /*
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
    */
}
