package htw.vs.virtuellesFileSystem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;


public class JDOMSchreiben {

    private final static String DATNAM = "xmlTest.xml";

    private final static File FILE = new File(DATNAM);

//    private final static String NAMESPACE = "";

    private Document createDoc(String rootElement) {
        Document doc = new Document();
        Element root = new Element(rootElement);
        doc.setRootElement(root);
        return doc;
    }

    private void writeDoc(Document doc) {


        File rootDir = new File("/tmp/");
            String[] directories = rootDir.list(new FilenameFilter() {
                @Override
                public boolean accept(File current, String name) {
                    return new File(current, name).isDirectory();
                }
            });
            for (String dir:directories) {
                String eDir = dir;
                if(dir.contains(" ")){
                    eDir = dir.replace(" ", "-_-");
                }
                if(dir.startsWith(".")){
                    Element e1 = new Element(eDir.substring(1));
                    e1.setAttribute("name", dir);
                    e1.setAttribute("dotfile", "yes");
                    doc.getRootElement().addContent(e1);
                }else {
                    Element e1 = new Element(eDir);
                    e1.setAttribute("name", dir);
                    doc.getRootElement().addContent(e1);
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
    }

    private void writeXML(Document doc) {
        Format format = Format.getPrettyFormat();
        format.setIndent("    ");
        try (FileOutputStream fos = new FileOutputStream(FILE)) {
            XMLOutputter op = new XMLOutputter(format);
            op.output(doc, fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JDOMSchreiben jds = new JDOMSchreiben();
        Document doc = jds.createDoc("VSFS");
        jds.writeDoc(doc);
        jds.writeXML(doc);
    }
}