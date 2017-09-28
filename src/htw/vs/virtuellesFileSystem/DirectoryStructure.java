package htw.vs.virtuellesFileSystem;

import htw.vs.virtuellesFileSystem.xmlPathCreate;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.nio.file.*;
import java.util.Collection;

public class DirectoryStructure {

    public static String rootPath = null;

    public static String askPaths() {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String rootPath = null;
        System.out.println("Geben Sie den root-Ordner fuer die interne Ordnerstruktur an. Verwenden Sie zur Trennung der Hierarchie-ebenen zwei Backslashs.");
        try {
            rootPath = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rootPath;
    }

    public static void createPaths(String txtPath) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(txtPath));
            String part = "";
            String[] uri = null;
            //Lies txt-Datei aus
            part = br.readLine();
            //Teile den Inhalt der Textdatei an Semicolons
            uri = part.split(";");
            //Pruefe, ob Pfad zu einer Datei oder einem Ordner fuehrt, schreibe um und erstelle jeweiliges
            for (String u : uri) {
                if (u.startsWith("DIR")) {
                    u = u.substring(3);
                    if (!(new File(u)).exists())
                        Files.createDirectories(Paths.get(u));
                } else if (u.startsWith("FIL")) {
                    u = u.substring(3);
                    if (!(new File(u)).exists())
                        Files.createFile(Paths.get(u));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //Schliesse Reader
            try {
                if (br != null)
                    br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void createTxt(String rootPath, String txtPath) {
        //Lege erforderliche Dateistrukturen an
        File directory = new File(rootPath);
        File txtFile = new File(txtPath);
        //Lies Ordnerstruktur rekursiv aus
        Collection<File> collection = org.apache.commons.io.FileUtils.listFilesAndDirs(directory, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
        String list = "";
        //Pruefe, ob Pfad zu Datei oder Ordner fuehrt, passe entsprechend Pfad an
        for (File file : collection) {
            if (file.isFile()) {
                list += "FIL" + (file.getAbsolutePath()) + ";";
            } else if (file.isDirectory()) {
                list += "DIR" + (file.getAbsolutePath()) + ";";
            }
        }
        //Schreibe Pfade nacheinander in txt-Datei
        try {
            FileUtils.writeStringToFile(txtFile, list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(txtFile.getAbsolutePath() + " erfolgreich erstellt.\n");
    }


//    public static void main(String[] args) {
//
//        rootPath = askPaths();
//        String txtPath = rootPath + "\\Paths.txt";
//        //Eine der nachfolgenden zwei Funktionen zum Verwenden auskommentieren
//        //createPaths(txtPath);
//        //createTxt(rootPath,txtPath);
//        DirWatchService.startWatcher();
//    }

    public static void search(String str) throws ParserConfigurationException, IOException, SAXException {

        Document document = getxmlFile("xmlTest.xml");

        NodeList nodeList = document.getElementsByTagName(str);

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (isDirectory(node)) {
                String dirToCreate = xmlPathCreate.revertIllegalCharacter(nodeToString(node));
                System.out.println(dirToCreate);
                System.out.println("-------------------------------------------------------------------------------");

            } else System.out.println("Kein Ordner");
        }
    }

    private static boolean isDirectory(Node node) {
        Element e = (Element) node;
        boolean b = Boolean.parseBoolean(e.getAttribute("directory"));
        if (!b) {
            return false;
        } else return true;
    }

    static Document getxmlFile(String xml) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        return docBuilder.parse(new File(xml));
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
        dir = dir.replace("/VSFS/", xmlPathCreate.ROOTDIR);
        return (dir);
    }
}