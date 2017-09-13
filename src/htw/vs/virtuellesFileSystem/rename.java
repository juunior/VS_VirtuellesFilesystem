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


    public static void main (String[] args) {
        //Path infile = Paths.get( "/home/werkstatt/IdeaProjects/rename/src/neuerOrdner/test.txt" );
        //Path outfile = Paths.get( "/home/werkstatt/IdeaProjects/rename/src/neuerOrdner/testKopie.txt" );
        Path infile = Paths.get( "src/neuerOrdner/test.txt" );
        Path outfile = Paths.get( "src/neuerOrdner/testKopie.txt" );

        rename(infile, outfile);
    }
}
