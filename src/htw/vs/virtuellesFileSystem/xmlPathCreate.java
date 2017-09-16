package htw.vs.virtuellesFileSystem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import javax.print.Doc;


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


    private void readDir(Document doc, String[] directories, String []files, String dirName) {
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


        //TODO braucht man das Listing der Ordner noch wenn man jeden betreten muss, eigentlich nein weil man diese auch betreten muss
        if(directories != null) {
            for (String dir : directories) {
                eDir = dir;
                Element e1;
                if (dir.contains(" ")) {
                    eDir = dir.replace(" ", "-_-");
                }
                if (dir.startsWith(".")) {
                    e1 = new Element(eDir.substring(1));
                    e1.setAttribute("name", dir);
                    e1.setAttribute("dotdir", "yes");
                } else {
                    e1 = new Element(eDir);
                    e1.setAttribute("name", dir);
                }
                p.addContent(e1);
            }
        }
        if(files != null) {
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
    }


    private void writeDoc(Document doc) {


        File rootDir = new File("/home/kai/studium/");
        String[] directories = rootDir.list((current, name) -> new File(current, name).isDirectory());
        String[] files = rootDir.list((current, name) -> new File(current, name).isFile());


//        readDir(doc,directories,files);
//
//        File subroot = new File(directories[0]);
//        directories = subroot.list((current, name) -> new File(current, name).isDirectory());
//        files = subroot.list((current, name) -> new File(current, name).isFile());
//        System.out.println(subroot.getAbsolutePath());


        System.out.println(getLastSubDir(rootDir, doc));
//        readDir(doc,directories,files);
    }

    String getLastSubDir(File file,Document doc){
        String[] directories = file.list((current, name) -> new File(current, name).isDirectory());
        File subroot = null;


        while((directories != null) && (directories.length != 0)){
            if(subroot != null){
                subroot = new File(subroot.getAbsoluteFile() + "/" + directories[0]);
            }else {
                subroot = new File(file.getAbsoluteFile() + "/" + directories[0]);
            }
            directories = subroot.list((current, name) -> new File(current, name).isDirectory());
//            buildParent(doc,directories);
        }
        String[] files = subroot.list((current, name) -> new File(current, name).isFile());
        readDir(doc, directories,files, subroot.getName());
        System.out.println(subroot.getAbsolutePath());


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