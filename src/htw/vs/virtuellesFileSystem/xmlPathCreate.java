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
    private String ROOTDIR;

    private String DELIMITER;

    private final static String DATNAM = "xmlTest.xml";

    private final static File FILE = new File(DATNAM);


    public Document createDoc(String rootElement) {
        Document doc = new Document();
        Element root = new Element(rootElement);
        doc.setRootElement(root);
        return doc;
    }

    private String removeIllegalCharacter(String tag){

        int i = 0;
        LinkedHashMap<String,String> illegalCharaterAndReplacement = new LinkedHashMap<>();

        illegalCharaterAndReplacement.put(" ","-_-");
        illegalCharaterAndReplacement.put("+","_plus_");
        illegalCharaterAndReplacement.put("[","_sbracc_");
        illegalCharaterAndReplacement.put("]","_sbraco_");
        illegalCharaterAndReplacement.put("=","_eq_");
        illegalCharaterAndReplacement.put("!","_exc_");
        illegalCharaterAndReplacement.put("#","_hash_");
        illegalCharaterAndReplacement.put(",","_komma_");
        illegalCharaterAndReplacement.put("$","_dollar_");
        illegalCharaterAndReplacement.put("~","_tilde_");
        illegalCharaterAndReplacement.put("{","_cbracoo_");
        illegalCharaterAndReplacement.put("}","_cbracoc_");
        illegalCharaterAndReplacement.put("&","_and_");
        illegalCharaterAndReplacement.put(".","_punk_");
        illegalCharaterAndReplacement.put("®","_copyRight_");
        illegalCharaterAndReplacement.put("@","_atat_");
        illegalCharaterAndReplacement.put(":","_dPoint_");
        illegalCharaterAndReplacement.put("'","_hochkomma_");
        illegalCharaterAndReplacement.put("\"","gansFuss");
        illegalCharaterAndReplacement.put("|","_pipe_");
        illegalCharaterAndReplacement.put("%","_proz_");




        ArrayList<String> illegal = new ArrayList<>(illegalCharaterAndReplacement.keySet());
        ArrayList<String> replacement = new ArrayList<>(illegalCharaterAndReplacement.values());

        for (String symbol :illegal){
            if (tag.contains(symbol)){
                tag = tag.replace(symbol, replacement.get(i));
            }
            if (Character.isDigit(tag.charAt(0))) {
            tag = "_" + tag;
            }
            i++;
        }

        return tag;
    }

    private Element buildElement(String[] files, String dirName) {
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
    private String solveIP(){
        Enumeration<NetworkInterface> n = null;
        try {
            n = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        for (; n.hasMoreElements();)
        {
            NetworkInterface e = n.nextElement();

            Enumeration<InetAddress> a = e.getInetAddresses();
            for (; a.hasMoreElements();)
            {
                InetAddress addr = a.nextElement();
                if (addr.getHostAddress().length() <=16) {
                    if (!addr.getHostAddress().contains("0"))
                    return addr.getHostAddress();
                }
            }
        }
        return "IP_not_solved";
    }


    private void readDir(Document doc, String[] files, String dirName) {
        if (files != null) {
            Arrays.sort(files);
        }
        Element p = buildElement(files, dirName);
        doc.getRootElement().addContent(p);


    }

    private void readParentDir(Document doc, String[] files, String dirName, File file) {
        Element p = buildElement(files, dirName);

        Element xml = doc.getRootElement();
        xml = insertChild(file, xml);

        xml.addContent(p);


        File subroot = new File(file.getAbsolutePath() + DELIMITER + dirName);
        String[] directories = subroot.list((current, name) -> new File(current, name).isDirectory());
        buildDirectoryWalk(directories, doc, subroot);

    }


    private Element insertChild(File file, Element xml) {
        String real = StringUtils.difference(ROOTDIR, file.getAbsolutePath());
        String[] childs = real.split(DELIMITER);
        if (childs.length != 0) {
            if (!childs[0].isEmpty()) {
                for (String child : childs) {
                    //gleiches Ersetzungsmuster wie in den Filtern
                    child = removeIllegalCharacter(child);
                    xml = xml.getChild(child);
                }
            }
        } else {
            xml = xml.getChild(file.getName());
        }
        System.out.println("REAL :::  " + real + "\t" + "CHILD::::" + Arrays.toString(childs) + "\t FILE::::" + file.getName());

        return xml;
    }


    private void buildDirectoryWalk(String[] directories, Document doc, File file) {
        File file_tmp;
        for (String dir : directories) {
            file_tmp = new File(file.getAbsolutePath() + "/" + dir);
            String[] files = file_tmp.list((current, name) -> new File(current, name).isFile());
            readParentDir(doc, files, dir, file);

        }
    }


    private void getDir(File file, Document doc) {
        String[] directories = file.list((current, name) -> new File(current, name).isDirectory());
        String[] files = file.list((current, name) -> new File(current, name).isFile());
        readDir(doc, files, file.getName());

        buildDirectoryWalk(directories, doc, file);


        if (directories != null) {
            Arrays.sort(directories);
        }


    }

    public void writeXML(Document doc) {
        Format format = Format.getPrettyFormat();
        format.setIndent("\t");
        try (FileOutputStream fos = new FileOutputStream(FILE)) {
            XMLOutputter op = new XMLOutputter(format);
            op.output(doc, fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeDoc(Document doc, String dir) throws FileNotFoundException, NotDirectoryException {
        String os = System.getProperty("os.name");
        if (os.contains("windows")){
            DELIMITER = "\\";
        }else {
            DELIMITER = "/";
        }

        if (!dir.endsWith(DELIMITER)){
            dir = dir.concat(DELIMITER);
        }

        if (!new File(dir).exists()){
            throw new FileNotFoundException();
        }
        if (!new File(dir).isDirectory()){
            throw new NotDirectoryException(dir);
        }
        File rootDir = new File(dir);

        String[] root = dir.split(DELIMITER);
        ROOTDIR = dir.replace(DELIMITER + root[root.length - 1] + DELIMITER, DELIMITER);



        getDir(rootDir, doc);
        solveIP();
    }


}
