import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.*;

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

    public static void listf(String rootPath) {
        File directory = new File(rootPath);
        File[] fList = directory.listFiles();
        String list = "";
        for (File file : fList) {
            if (file.isFile()) {
                list += "FIL" + (file.getAbsolutePath()) + ";";
            } else if (file.isDirectory()) {
                list += "DIR" + (file.getAbsolutePath()) + ";";
            }
        }
        System.out.println(fList);

       // FileUtils.writeStringToFile(new File("C:\\VS1\\Paths.txt"), list);

    }

    public static void main(String[] args){
        String txtPath = "C:\\VS1\\Paths.txt";
        String rootPath = "C:\\VS1";
      //  DirectoryStructure.createPaths(txtPath);
        listf(rootPath);
    }
}
