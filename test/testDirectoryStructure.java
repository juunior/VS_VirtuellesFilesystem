
import htw.vs.virtuellesFileSystem.*;

public class testDirectoryStructure {
    public static void main(String[] args){
        String rootPath = directoryStructure.askPaths();
        directoryStructure.createPaths(rootPath);
        directoryStructure.createTxt(rootPath);
    }
}
