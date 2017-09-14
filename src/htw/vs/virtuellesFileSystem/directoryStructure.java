package htw.vs.virtuellesFileSystem;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.*;
import java.nio.file.*;
import java.util.Collection;

public class directoryStructure {

    public static void createPaths(String txtPath) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(txtPath));
            String part = "";
            String[] uri = null;
            part = br.readLine();
            uri = part.split(";\n");
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
            try {
                if (br != null)
                    br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void createTxt(String rootPath, String txtPath) {
        File directory = new File(rootPath);
        File txtFile = new File(txtPath);
        Collection<File> collection = org.apache.commons.io.FileUtils.listFilesAndDirs(directory, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
        String list = "";
        for (File file : collection) {
            if (file.isFile()) {
                list += "FIL" + (file.getAbsolutePath()) + ";";
            } else if (file.isDirectory()) {
                list += "DIR" + (file.getAbsolutePath()) + ";";
            }
        }
        try {
            FileUtils.writeStringToFile(txtFile, list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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


}