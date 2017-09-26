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
import java.nio.file.NotDirectoryException;
import java.util.Arrays;


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

    private Element buildElement(String[] files, String dirName) {
        String eDir = dirName; // eDir ist XML Konform
        if (files != null) {
            Arrays.sort(files);
        }
        Element p = new Element("tmp");
        if (eDir.contains(" ")) {
            eDir = eDir.replace(" ", "-_-");
        }
        if (eDir.contains("+")) {
            eDir = eDir.replace("+", "_plus_");
        }
        if (eDir.contains("[")) {
            eDir = eDir.replace("[", "_sbraco_");
        }
        if (eDir.contains("]")) {
            eDir = eDir.replace("]", "_sbracc_");
        }
        if (eDir.contains("\\")) {
            eDir = eDir.replace("\\", "_backsl_");
        }
        if (eDir.startsWith(".")) {
            eDir = eDir.replace(".", "_punkt_");
            p.setAttribute("dotdir", "yes");
        }
        if (Character.isDigit(eDir.charAt(0))) {
            eDir = "_" + eDir;
        }

        p = new Element(eDir);

        p.setAttribute("name", dirName);


        if (files != null) {
            for (String file : files) {
                eDir = file;
                Element e1 = new Element("tmp");
                if (eDir.contains(" ")) {
                    eDir = eDir.replace(" ", "-_-");
                }
                if (eDir.startsWith(".")) {
                    eDir = eDir.replace(".", "_punkt_");
                    e1.setAttribute("dotfile", "yes");
                }
                if (eDir.contains("[")) {
                    eDir = eDir.replace("[", "_sbraco_");
                }
                if (eDir.contains("]")) {
                    eDir = eDir.replace("]", "_sbracc_");
                }
                if (eDir.contains("+")) {
                    eDir = eDir.replace("+", "_plus_");
                }
                if (eDir.contains("\\")) {
                    eDir = eDir.replace("\\", "_backsl_");
                }
                if (eDir.contains("~")) {
                    eDir = eDir.replace("~", "_tilde_");
                    e1.setAttribute("tempFile", "yes");
                }
                if (Character.isDigit(eDir.charAt(0))) {
                    eDir = "_" + eDir;
                }

                e1 = new Element(eDir);


//                System.out.println(file);
                e1.setAttribute("name", file);
                e1.setAttribute("file", "true");
                p.addContent(e1);
            }

        }
        return p;
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
                    if (child.contains(" ")) {
                        child = child.replace(" ", "-_-");
                    }
                    if (child.startsWith(".")) {
                        child = child.replace(".", "_punkt_");
                    }
                    if (child.contains("+")) {
                        child = child.replace("+", "_plus_");
                    }
                    if (child.contains("~")) {
                        child = child.replace("~", "_tilde_");
                    }
                    if (child.contains("]")) {
                        child = child.replace("]", "_sbracc_");
                    }
                    if (child.contains("[")) {
                        child = child.replace("[", "_sbraco_");
                    }
                    if (child.contains("\\")) {
                        child = child.replace("\\", "_backsl_");
                    }
                    if (Character.isDigit(child.charAt(0))) {
                        child = "_" + child;
                    }
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
    }


}
