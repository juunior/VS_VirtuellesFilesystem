import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.Collection;

public class DirectoryStructure {

    public static void createPaths(String txtPath) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(txtPath));
            String part = "";
            String[] uri = null;
            part = br.readLine();
            uri = part.split(";");
            for (String u : uri) {
                if (u.startsWith("DIR")) {
                    u = u.substring(3);
                    if(!(new File(u)).exists())
                    Files.createDirectories(Paths.get(u));
                } else if (u.startsWith("FIL")) {
                    u = u.substring(3);
                    if(!(new File(u)).exists())
                    Files.createFile(Paths.get(u));
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
            if(br!=null)
                br.close();
        }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void createTxt(String rootPath, String txtPath) {
        File directory = new File(rootPath);
        File txtFile = new File(txtPath);
        Collection<File> collection = org.apache.commons.io.FileUtils.listFilesAndDirs(directory,TrueFileFilter.INSTANCE,TrueFileFilter.INSTANCE);
        String list = "";
        for (File file : collection) {
            if (file.isFile()) {
                list += "FIL" + (file.getAbsolutePath()) + ";";
            } else if (file.isDirectory()) {
                list += "DIR" + (file.getAbsolutePath()) + ";";
            }
        }
        try {
            FileUtils.writeStringToFile(txtFile,list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        String txtPath = "C:\\VS1\\Paths.txt";
        String rootPath = "C:\\VS1";
        //DirectoryStructure.createPaths(txtPath);
        //createTxt(rootPath,txtPath);
    }
}
