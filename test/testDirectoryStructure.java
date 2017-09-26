
import htw.vs.virtuellesFileSystem.*;

public class testDirectoryStructure {
    public static void main(String[] args){
        String rootPath = Directory_Structure.askPaths();
        String txtPath = rootPath + "\\Paths.txt"; //Muss evtl. angepasst werden
        Directory_Structure.createPaths(rootPath);
        Directory_Structure.createTxt(rootPath, txtPath);
    }
}
