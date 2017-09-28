package htw.vs.virtuellesFileSystem;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.*;
import java.nio.file.NotDirectoryException;
import java.util.*;


public class xmlPathCreate {
    private static String ROOTDIR;
    private static String DELIMITER;
    private static LinkedHashMap<String, String> illegalCharacterAndReplacement;

    private final static String DATNAM = "xmlTest.xml";

    private final static File FILE = new File(DATNAM);


    private static Document createDoc() {
        Document doc = new Document();
        Element root = new Element("VSFS");
        doc.setRootElement(root);
        return doc;
    }

    /**
     * Funktion die die zu ersetztenden Zeichen in die Hasmap einfuegt
     */
    private static void setIllegalCharacter() {
        illegalCharacterAndReplacement = new LinkedHashMap<>();

        illegalCharacterAndReplacement.put(" ", "_space_");
        illegalCharacterAndReplacement.put("+", "_plus_");
        illegalCharacterAndReplacement.put("[", "_sbracc_");
        illegalCharacterAndReplacement.put("]", "_sbraco_");
        illegalCharacterAndReplacement.put("=", "_eq_");
        illegalCharacterAndReplacement.put("!", "_exc_");
        illegalCharacterAndReplacement.put("#", "_hash_");
        illegalCharacterAndReplacement.put(",", "_komma_");
        illegalCharacterAndReplacement.put("$", "_dollar_");
        illegalCharacterAndReplacement.put("~", "_tilde_");
        illegalCharacterAndReplacement.put("{", "_cbracoo_");
        illegalCharacterAndReplacement.put("}", "_cbracoc_");
        illegalCharacterAndReplacement.put("&", "_and_");
        illegalCharacterAndReplacement.put(".", "_punkt_");
        illegalCharacterAndReplacement.put("®", "_copyRight_");
        illegalCharacterAndReplacement.put("℗", "_scopyRight_");
        illegalCharacterAndReplacement.put("①", "_circ1_");
        illegalCharacterAndReplacement.put("@", "_atat_");
        illegalCharacterAndReplacement.put(":", "_dPoint_");
        illegalCharacterAndReplacement.put("'", "_hochkomma_");
        illegalCharacterAndReplacement.put("\"", "gansFuss");
        illegalCharacterAndReplacement.put("|", "_pipe_");
        illegalCharacterAndReplacement.put("%", "_proz_");
        illegalCharacterAndReplacement.put("*", "_star_");
        illegalCharacterAndReplacement.put("(", "_braco_");
        illegalCharacterAndReplacement.put(")", "_bracc_");
        illegalCharacterAndReplacement.put("-", "_minus_");
        illegalCharacterAndReplacement.put("?", "_ask_");
        illegalCharacterAndReplacement.put("¶", "_newL_");
        illegalCharacterAndReplacement.put("`", "_backtick_");
        illegalCharacterAndReplacement.put("…","_ppp_");
        illegalCharacterAndReplacement.put(";","_semic_");
        illegalCharacterAndReplacement.put("†","_cross_");
    }

    /**
     * Funktion, die die nicht XML konformen Zeichen umwandelt
     *
     * @param tag String mit nicht zulässigen Zeichen
     * @return XML konformer String
     */
    static String removeIllegalCharacter(String tag) {

        setIllegalCharacter();
        int i = 0;
        ArrayList<String> illegal = new ArrayList<>(illegalCharacterAndReplacement.keySet());
        ArrayList<String> replacement = new ArrayList<>(illegalCharacterAndReplacement.values());

        StringBuilder tagBuilder = new StringBuilder(tag);
        for (String symbol : illegal) {
            if (tagBuilder.toString().contains(symbol)) {
                tagBuilder = new StringBuilder(tagBuilder.toString().replace(symbol, replacement.get(i)));
            }
            if (Character.isDigit(tagBuilder.charAt(0))) {
                tagBuilder.insert(0, "_");
            }
            i++;
        }
        tag = tagBuilder.toString();

        return tag;
    }

    /**
     * wandelt einen angepassten String wieder in eine normale Form zurück
     *
     * @param tag String mit ersetzten Zeichen
     * @return normaliesierter lesbaere String
     */
    static String revertIllegalCharacter(String tag) {

        setIllegalCharacter();
        int i = 0;
        ArrayList<String> illegal = new ArrayList<>(illegalCharacterAndReplacement.keySet());
        ArrayList<String> replacement = new ArrayList<>(illegalCharacterAndReplacement.values());

        for (String symbol : replacement) {
            if (tag.contains(symbol)) {
                tag = tag.replace(symbol, illegal.get(i));
            }
            if (Character.isDigit(tag.charAt(1))) {
                tag = tag.substring(1, tag.length());
            }
            i++;
        }

        return tag;
    }


    /**
     * baut ein Element bzw einen Ordner zusammen
     *
     * @param files   List von Dateien in einem Ordner
     * @param dirName Name des Ordners
     * @return Element eines Ordners mit den zugehörigen Datein
     */
    private static Element buildElement(String[] files, String dirName) {
        String eDir = dirName; // eDir ist XML Konform
        if (files != null) {
            Arrays.sort(files);
        }

        Element p = new Element(removeIllegalCharacter(eDir));

        p.setAttribute("directory", "true");
        p.setAttribute("Host", solveIP());
        p.setAttribute("name", dirName);


        if (files != null) {
            for (String file : files) {
                eDir = file;
                Element e1 = new Element(removeIllegalCharacter(eDir));


                e1.setAttribute("name", file);
                e1.setAttribute("file", "true");
                e1.setAttribute("Host", solveIP());
                p.addContent(e1);
            }

        }
        return p;
    }

    /**
     * findet eine IPv4 Addresse die nicht loopback ist
     *
     * @return die IPv4 Addresse des Hosts
     */
    private static String solveIP() {
        Enumeration<NetworkInterface> n = null;
        try {
            n = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        for (; n != null && n.hasMoreElements(); ) {
            NetworkInterface e = n.nextElement();

            Enumeration<InetAddress> a = e.getInetAddresses();
            for (; a.hasMoreElements(); ) {
                InetAddress addr = a.nextElement();
                if (addr.getHostAddress().length() <= 16) {
                    if (!addr.getHostAddress().contains("127") && !addr.getHostAddress().contains(":"))
                        return addr.getHostAddress();
                }
            }
        }
        return "IP_not_solved";
    }

    /**
     * Liest die Files des ersten Directory (RootElement)
     *
     * @param doc     Document file
     * @param files   Datei des Ordners
     * @param dirName Ordnername
     */
    private static void readDir(Document doc, String[] files, String dirName) {
        if (files != null) {
            Arrays.sort(files);
        }
        Element p = buildElement(files, dirName);
        doc.getRootElement().addContent(p);


    }

    /**
     * Liest die Datein der Ordner, verpackt sie und fügt sie als Child ein
     *
     * @param doc     Document file
     * @param files   Dateien des Ordners
     * @param dirName Ordnername
     * @param dir     Ordnerverweis
     */
    private static void readParentDir(Document doc, String[] files, String dirName, File dir) {
        Element p = buildElement(files, dirName);

        Element xml = doc.getRootElement();
        xml = insertChild(dir, xml);

        xml.addContent(p);


        File subroot = new File(dir.getAbsolutePath() + DELIMITER + dirName);
        String[] directories = subroot.list((current, name) -> new File(current, name).isDirectory());
        buildDirectoryWalk(directories, doc, subroot);

    }

    /**
     * sucht anhand des absoluten Pfad das passende XMl Child für einen weiteren Eintrag
     *
     * @param dir Ordnerverweis
     * @param xml xmlRootElement
     * @return das gefunde Child Element
     */
    private static Element insertChild(File dir, Element xml) {
        String real = StringUtils.difference(ROOTDIR, dir.getAbsolutePath());
        String[] children;
        if (Objects.equals(DELIMITER, "\\")) {
            children = real.split("\\\\");
        } else {
            children = real.split("/");
        }
        if (children.length != 0) {
            if (!children[0].isEmpty()) {
                for (String child : children) {
                    //gleiches Ersetzungsmuster wie in den Filtern
                    child = removeIllegalCharacter(child);
                    xml = xml.getChild(child);
                }
            }
        } else {
            xml = xml.getChild(dir.getName());
        }


        return xml;
    }

    /**
     * rekursiver Durchlauf aller Ordner
     *
     * @param directories liste mit zu durchlaufenden Ordnern
     * @param doc         Document file
     * @param dirs        Ordnerverweis
     * @return Document file
     */
    private static Document buildDirectoryWalk(String[] directories, Document doc, File dirs) {
        File file_tmp;
        for (String dir : directories) {
            file_tmp = new File(dirs.getAbsolutePath() + "/" + dir);
            String[] files = file_tmp.list((current, name) -> new File(current, name).isFile());
            readParentDir(doc, files, dir, dirs);

        }
        return doc;
    }


    /**
     * Erstellen des ersten Directories und Anstoß zum Durchlauf aller Subdirectories
     *
     * @param dirs Ordnerverweis
     * @param doc  Document file
     */
    private static Document getDirs(File dirs, Document doc) {
        String[] directories = dirs.list((current, name) -> new File(current, name).isDirectory());
        String[] files = dirs.list((current, name) -> new File(current, name).isFile());
        readDir(doc, files, dirs.getName());

        if (directories != null) {
            Arrays.sort(directories);
        }

        return buildDirectoryWalk(directories, doc, dirs);

    }

    /**
     * Write XML to disk
     */
    public static void createXML(String dir) throws FileNotFoundException, NotDirectoryException {
        Document doc = createDoc();

        doc = writeDoc(doc, dir);


        Format format = Format.getPrettyFormat();
        format.setIndent("\t");
        try (FileOutputStream fos = new FileOutputStream(FILE)) {
            XMLOutputter op = new XMLOutputter(format);
            op.output(doc, fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create Document for generating the XML file
     *
     * @param doc Document file
     * @param dir Ordnername
     * @see FileNotFoundException
     */
    private static Document writeDoc(Document doc, String dir) throws FileNotFoundException, NotDirectoryException {


        if (!dir.endsWith(DELIMITER)) {
            dir = dir.concat(DELIMITER);
        }

        if (!new File(dir).exists()) {
            throw new FileNotFoundException();
        }
        if (!new File(dir).isDirectory()) {
            throw new NotDirectoryException(dir);
        }

        String[] root;

        if (Objects.equals(DELIMITER, "\\")) {
            root = dir.split("\\\\");
        } else {
            root = dir.split("/");
        }
        ROOTDIR = dir.replace(DELIMITER + root[root.length - 1] + DELIMITER, DELIMITER);
        File rootDir = new File(dir);
        return getDirs(rootDir, doc);

    }

    public static void detectOS() {
        String os = System.getProperty("os.name");
        if (os.toLowerCase().contains("windows")) {
            DELIMITER = "\\";
        } else {
            DELIMITER = "/";
        }
    }


}
