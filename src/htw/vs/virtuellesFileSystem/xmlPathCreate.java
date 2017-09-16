package htw.vs.virtuellesFileSystem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.print.Doc;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


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


    private int readDir(Document doc, String[] files, String dirName) {
        Element p;
        String eDir = dirName; //eDir ist XML conform
        if (dirName.contains(" ")) {
            eDir = dirName.replace(" ", "-_-");
        }
        if (dirName.startsWith(".")) {
            p = new Element(eDir.substring(1));
            p.setAttribute("name", dirName);
            p.setAttribute("dotdir", "yes");
        } else {
            p = new Element(eDir);
            p.setAttribute("name", dirName);
        }

        if (files != null) {
            for (String file : files) {
                eDir = file;
                Element e1;
                if (file.contains(" ")) {
                    eDir = file.replace(" ", "-_-");
                }
                if (file.startsWith(".")) {
                    e1 = new Element(eDir.substring(1));
                    e1.setAttribute("name", file);
                    e1.setAttribute("dotfile", "yes");
                } else {
                    e1 = new Element(eDir);
                    e1.setAttribute("name", file);
                }
                e1.setAttribute("file", "true");
                p.addContent(e1);
            }

        }
        doc.getRootElement().addContent(p);

        return doc.getRootElement().indexOf(p);
    }
    private void readDir(Document doc, String[] files, String dirName, int parent) {
        Element p;
        String eDir = dirName; //eDir ist XML conform
        if (dirName.contains(" ")) {
            eDir = dirName.replace(" ", "-_-");
        }
        if (dirName.startsWith(".")) {
            p = new Element(eDir.substring(1));
            p.setAttribute("name", dirName);
            p.setAttribute("dotdir", "yes");
        } else {
            p = new Element(eDir);
            p.setAttribute("name", dirName);
        }

        if (files != null) {
            for (String file : files) {
                eDir = file;
                Element e1;
                if (file.contains(" ")) {
                    eDir = file.replace(" ", "-_-");
                }
                if (file.startsWith(".")) {
                    e1 = new Element(eDir.substring(1));
                    e1.setAttribute("name", file);
                    e1.setAttribute("dotfile", "yes");
                } else {
                    e1 = new Element(eDir);
                    e1.setAttribute("name", file);
                }
                e1.setAttribute("file", "true");
                p.addContent(e1);
            }

        }
        doc.getRootElement().getChild("").addContent(p);



    }



    private void writeDoc(Document doc) {

        File rootDir = new File("/home/kai/studium/");

        System.out.println(getLastSubDir(rootDir, doc));
    }

    private String getLastSubDir(File file, Document doc) {
        String[] directories = file.list((current, name) -> new File(current, name).isDirectory());
        File subroot = null;
        boolean goUp = false;
        boolean depthGot = false;
        int i = 0;
        int subDepth = 0;
        String child = "";

//        while ((directories != null) && (directories.length != 0)) {
        do{
            try {
                if (subroot != null) {
                    subroot = new File(subroot.getAbsoluteFile() + "/" + directories[i]);
                    if(!depthGot){subDepth++;}
                } else {
                    subroot = new File(file.getAbsoluteFile() + "/" + directories[i]);
                    if(!depthGot){subDepth++;}
                }
            }catch (ArrayIndexOutOfBoundsException e){
                subDepth = 0;
            }
            directories = subroot.list((current, name) -> new File(current, name).isDirectory());
            if (directories.length == 0){
                depthGot = true;
                goUp = true;
                i++;
            }
            if(goUp) {
                String[] files = subroot.list((current, name) -> new File(current, name).isFile());
                int parent = readDir(doc, files, subroot.getName());
                child = subroot.getName();
                goUp = false;
                files = new File(subroot.getParent()).list((current, name) -> new File(current, name).isFile());
                readDir(doc, files, new File(subroot.getParent()).getName(), parent);
                directories = new File(subroot.getParent()).list((current, name) -> new File(current, name).isDirectory());
                subDepth= 0;
            }
        }while (subDepth != 0);




        System.out.println(subroot.getParent());


        return subroot.getName();
    }

//    Element buildParent(Document doc,String[] directories, Element parent){
//
//    }

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