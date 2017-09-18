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
    private String ROOTDIR;

    private final static String DATNAM = "xmlTest.xml";

    private final static File FILE = new File(DATNAM);


    private Document createDoc(String rootElement) {
        Document doc = new Document();
        Element root = new Element(rootElement);
        doc.setRootElement(root);
        return doc;
    }

    private Element buildElement(String[] files, String dirName, String eDir) {
        Boolean allowed = true;
        if (files != null) {
            Arrays.sort(files);
        }
        Element p = null;
        if (dirName.contains(" ")) {
            eDir = dirName.replace(" ", "-_-");
            p = new Element(eDir);
            allowed = false;
        }
        if (dirName.startsWith(".")) {
            p = new Element(eDir.substring(1));
            p.setAttribute("dotdir", "yes");
            allowed = false;
        }
        if (Character.isDigit(dirName.charAt(0))) {
            p = new Element("_" + eDir);
            allowed = false;
        }
        if (allowed) {
            p = new Element(eDir);
        }
        p.setAttribute("name", dirName);

        if (files != null) {
            for (String file : files) {
                allowed = true;
                eDir = file;
                Element e1 = null;
                if (file.contains(" ")) {
                    eDir = file.replace(" ", "-_-");
                    e1 = new Element(eDir);
                    allowed = false;
                }
                if (file.startsWith(".")) {
                    e1 = new Element(eDir.substring(1));
                    e1.setAttribute("dotfile", "yes");
                    allowed = false;
                }
                if (file.contains("+")) {
                    e1 = new Element(file.replace("+", "_plus_"));
                    allowed = false;
                }
                if (Character.isDigit(file.charAt(0))) {
                    e1 = new Element("_" + eDir);
                    allowed = false;
                }
                if (allowed) {
                    e1 = new Element(eDir);
                }

//                System.out.println(file);
                e1.setAttribute("name", file);
                e1.setAttribute("file", "true");
                p.addContent(e1);
            }

        }
        return p;
    }

    private String readDir(Document doc, String[] files, String dirName) {
        if (files != null) {
            Arrays.sort(files);
        }
        String eDir = dirName; //eDir ist XML conform
        Element p = buildElement(files, dirName, eDir);
        doc.getRootElement().addContent(p);

        return p.getName();
    }

    private void readParentDir(Document doc, String[] files, String dirName, File file) {
        String eDir = dirName; //eDir ist XML conform
        Element p = buildElement(files, dirName, eDir);

            Element xml = doc.getRootElement();
            xml = insertChild(file, xml);

            xml.addContent(p);


        File subroot = new File(file.getAbsolutePath() + "/" + dirName);
        String[] directories = subroot.list((current, name) -> new File(current, name).isDirectory());
        buildDirectoryWalk(directories, doc, subroot, subroot.getName());

    }


    private Element insertChild(File file, Element xml) {
        String real = org.apache.commons.lang3.StringUtils.difference(ROOTDIR, file.getAbsolutePath());
        String[] childs = real.split("/");
        if (childs.length != 0 ) {
            if(!childs[0].isEmpty()) {
                for (String child : childs) {
                    xml = xml.getChild(child);
                }
            }
        }else {
            xml = xml.getChild(file.getName());
        }
        System.out.println("REAL :::  " + real + "\t" + Arrays.toString(childs));

        return xml;
    }


    private void buildDirectoryWalk(String[] directories, Document doc, File file, String child) {
        File file_tmp = null;
        for (String dir : directories) {
            file_tmp = new File(file.getAbsolutePath() + "/" + dir);
            String[] files = file_tmp.list((current, name) -> new File(current, name).isFile());
            readParentDir(doc, files, dir, file);

        }
    }



    private void getDir(File file, Document doc) {
        String[] directories = file.list((current, name) -> new File(current, name).isDirectory());
        String[] files = file.list((current, name) -> new File(current, name).isFile());
        String child = readDir(doc, files, file.getName());

        buildDirectoryWalk(directories, doc, file, child);


        if (directories != null) {
            Arrays.sort(directories);
        }


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

    private void writeDoc(Document doc, String dir) {

        File rootDir = new File(dir);
        String[] root = dir.split("/");
        ROOTDIR = dir.replace("/" + root[root.length - 1] + "/","/");

        getDir(rootDir, doc);
    }

    public static void main(String[] args) {
        xmlPathCreate jds = new xmlPathCreate();
        Document doc = jds.createDoc("VSFS");
        jds.writeDoc(doc, "/home/kai/studium/");
        jds.writeXML(doc);

//        try (Stream<Path> paths = Files.walk(Paths.get("/home/kai/studium/"))) {
//            paths.forEach(System.out::println);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
