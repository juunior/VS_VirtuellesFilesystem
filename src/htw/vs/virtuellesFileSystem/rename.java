package htw.vs.virtuellesFileSystem;

import java.nio.file.*;
import java.io.IOException;

public class rename {

    public static void rename (Path infile, Path outfile) {
        try {
            Files.copy(infile, outfile);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

}
