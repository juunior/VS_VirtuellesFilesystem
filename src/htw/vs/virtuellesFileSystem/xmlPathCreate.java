package htw.vs.virtuellesFileSystem;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;


public class xmlPathCreate {

    private final static String DATNAM = "xmlTest.xml";

    private final static File FILE = new File(DATNAM);

//    private final static String NAMESPACE = "";

    private Document createDoc(String rootElement) {
        Document doc = new Document();
        Element root = new Element(rootElement);
        doc.setRootElement(root);
        return doc;
    }

    private Element buildElement(String[] files, String dirName, String eDir) {
        Element p;
        if (dirName.startsWith(".")) {
            p = new Element(eDir.substring(1));
            p.setAttribute("dotdir", "yes");
        } else if (Character.isDigit(dirName.charAt(0))) {
            p = new Element("_" + eDir);
        } else {
            p = new Element(eDir);
        }
        p.setAttribute("name", dirName);

        if (files != null) {
            for (String file : files) {
                eDir = file;
                Element e1;
                if (file.contains(" ")) {
                    eDir = file.replace(" ", "-_-");
                }
                if (file.startsWith(".")) {
                    e1 = new Element(eDir.substring(1));
                    e1.setAttribute("dotfile", "yes");
                } else if (Character.isDigit(file.charAt(0))) {
                    e1 = new Element("_" + eDir);
                } else {
                    e1 = new Element(eDir);
                }
                e1.setAttribute("name", file);
                e1.setAttribute("file", "true");
                p.addContent(e1);
            }

        }
        return p;
    }

    private String readDir(Document doc, String[] files, String dirName) {
        Arrays.sort(files);
        String eDir = dirName; //eDir ist XML conform
        if (dirName.contains(" ")) {
            eDir = dirName.replace(" ", "-_-");
        }
        Element p = buildElement(files, dirName, eDir);
        doc.getRootElement().addContent(p);

        return p.getName();
    }

    private void readParentDir(Document doc, String[] files, String dirName, String child) {
        String eDir = dirName; //eDir ist XML conform
        if (dirName.contains(" ")) {
            eDir = dirName.replace(" ", "-_-");
        }
        Element p = buildElement(files, dirName, eDir);

        doc.getRootElement().getChild(child).addContent(p);


    }


    private void writeDoc(Document doc) {

        File rootDir = new File("/home/kai/studium/");

        getDir(rootDir, doc);
    }

    private void buildRec(String[] directories, Document doc, File file, String child) {
        File file_tmp = null;
        for (String dir : directories) {
            file_tmp = new File(file.getAbsolutePath() + "/" + dir);
            String[] files = file_tmp.list((current, name) -> new File(current, name).isFile());
            readParentDir(doc, files, dir, child);
        }
        if (directories.length != 0) {
            for (String dir : directories) {
                file = new File(file.getAbsolutePath() + "/" + dir);
                buildRec(directories, doc, file, child);
            }
        }
    }


    private void getDir(File file, Document doc) {
        String[] directories = file.list((current, name) -> new File(current, name).isDirectory());

        if (directories != null) {
            Arrays.sort(directories);
        }

        String[] files = file.list((current, name) -> new File(current, name).isFile());
        String child = readDir(doc, files, file.getName());

        buildRec(directories, doc, file, child);

    }

    private void writeXML(Document doc) {
        Format format = Format.getPrettyFormat();
        format.setIndent("\t");
        try (FileOutputStream fos = new FileOutputStream(FILE)) {
            XMLOutputter op = new XMLOutputter(format);
            op.output(doc, fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        xmlPathCreate jds = new xmlPathCreate();
        Document doc = jds.createDoc("VSFS");
        jds.writeDoc(doc);
        jds.writeXML(doc);
    }
}


//            Element e1 = new Element(rootDir.getName());
//            e1.setAttribute("name", e1.getName());
//            doc.getRootElement().addContent(e1);


//        Element e2 = new Element("e2");
//        Element n2 = new Element("name");
//        n2.setText("e2");
//        Element v2 = new Element("value");
//        v2.setText("Wert 2");
//        e2.addContent(n2);
//        e2.addContent(v2);
//        doc.getRootElement().addContent(e2);