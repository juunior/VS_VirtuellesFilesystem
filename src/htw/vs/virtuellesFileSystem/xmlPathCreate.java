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

    private String removeIllegalCharacter(String tag){
        if (tag.contains(" ")) {
            tag = tag.replace(" ", "-_-");
        }
        if (tag.contains("+")) {
            tag = tag.replace("+", "_plus_");
        }
        if (tag.contains("[")) {
            tag = tag.replace("[", "_sbraco_");
        }
        if (tag.contains("]")) {
            tag = tag.replace("]", "_sbracc_");
        }
        if (tag.contains("\\")) {
            tag = tag.replace("\\", "_backsl_");
        }
        if (tag.contains("(")) {
            tag = tag.replace("(", "_braco_");
        }
        if (tag.contains(")")) {
            tag = tag.replace(")", "_bracc_");
        }
        if (tag.contains("=")) {
            tag = tag.replace("=", "_eq_");
        }
        if (tag.contains("!")) {
            tag = tag.replace("!", "_exc_");
        }
        if (tag.contains("#")) {
            tag = tag.replace("#", "_hash_");
        }
        if (tag.contains(",")) {
            tag = tag.replace(",", "_komma_");
        }
        if (tag.contains("$")) {
            tag = tag.replace("$", "_dollar_");
        }
        if (tag.contains("~")) {
            tag = tag.replace("~", "_tilde_");
        }
        if (tag.contains("{")) {
            tag = tag.replace("{", "_cbraco_");
        }
        if (tag.contains("}")) {
            tag = tag.replace("}", "_cbracc_");
        }
        if (tag.contains("&")) {
            tag = tag.replace("&", "_and_");
        }
        if (tag.startsWith(".")) {
            tag = tag.replace(".", "_punkt_");
        }
        if (tag.contains("®")) {
            tag = tag.replace("®", "_copyRight_");
        }
        if (tag.contains("@")) {
            tag = tag.replace("@", "_atat_");
        }
        if (tag.contains(":")) {
            tag = tag.replace(":", "_dPoint_");
        }
        if (tag.contains("'")) {
            tag = tag.replace("'", "_hochkomma_");
        }
        if (tag.contains("\"")) {
            tag = tag.replace("\"", "_gansFuss_");
        }
        if (tag.contains("|")) {
            tag = tag.replace("|", "_pipe_");
        }
        if (Character.isDigit(tag.charAt(0))) {
            tag = "_" + tag;
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
        p.setAttribute("name", dirName);


        if (files != null) {
            for (String file : files) {
                eDir = file;
                Element e1 = new Element(removeIllegalCharacter(eDir));


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
    }


}
