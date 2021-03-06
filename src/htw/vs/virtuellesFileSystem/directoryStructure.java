package htw.vs.virtuellesFileSystem;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.*;
import java.nio.file.*;
import java.util.Collection;

public class directoryStructure {

    public static String rootPath = null;

    public static String askPaths() {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String rootPath = null;
        System.out.println("Geben Sie den root-Ordner fuer die interne Ordnerstruktur an.");
        System.out.println("Verwenden Sie zur Trennung der Hierarchie-ebenen zwei Backslashs (Win), ein Slash (Unix).");
        System.out.println("Beenden Sie Ihre Eingabe mit diesem Trennzeichen.");
        try {
            rootPath = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String os = System.getProperty("os.name");

        if (rootPath != null) {
            if (os.contains("Windows")) {
                rootPath.replace("/", "\\");
                if(!rootPath.endsWith("\\")){
                    rootPath.concat("\\");
                }
            } else {
                rootPath.replace("\\", "/");
                if(!rootPath.endsWith("/")){
                    rootPath = rootPath.concat("/");
                }
            }

        }

        return rootPath;
    }

    public static void createPaths(String rootPath) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(rootPath));
            String part = "";
            String[] uri = null;
            //Lies txt-Datei aus
            part = br.readLine();
            //Teile den Inhalt der Textdatei an Semicolons
            uri = part.split(";");
            //Pruefe, ob Pfad zu einer Datei oder einem Ordner fuehrt, schreibe um und erstelle jeweiliges
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
            //Schliesse Reader
            try {
                if (br != null)
                    br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void createTxt(String rootPath) {
        //Lege erforderliche Dateistrukturen an
        File directory = new File(rootPath);
        File txtFile = new File(rootPath + "Path.txt");
        //Lies Ordnerstruktur rekursiv aus
        Collection<File> collection = org.apache.commons.io.FileUtils.listFilesAndDirs(directory, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
        String list = "";
        //Pruefe, ob Pfad zu Datei oder Ordner fuehrt, passe entsprechend Pfad an
        for (File file : collection) {
            if (file.isFile()) {
                list += "FIL" + (file.getAbsolutePath()) + ";";
            } else if (file.isDirectory()) {
                list += "DIR" + (file.getAbsolutePath()) + ";";
            }
        }
        //Schreibe Pfade nacheinander in txt-Datei
        try {
            FileUtils.writeStringToFile(txtFile, list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(txtFile.getAbsolutePath() + " erfolgreich erstellt.\n");
    }
}
