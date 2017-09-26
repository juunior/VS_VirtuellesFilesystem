
import htw.vs.virtuellesFileSystem.*;

public class testDirectoryStructure {
    public static void main(String[] args){
        String rootPath = DirectoryStructure.askPaths();
        String txtPath = rootPath + "\\Paths.txt"; //Muss evtl. angepasst werden
        DirectoryStructure.createPaths(rootPath);
        DirectoryStructure.createTxt(rootPath, txtPath);
    }
}
