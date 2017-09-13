import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import htw.vs.virtuellesFileSystem.*;


public class testOperations {


    public static void main(String[] args) {
        //lege object vom typ datei an
        File f = new File("./testOrdner/testOrdner");
        File f2 = new File("./testOrdner/testOrdner/test.txt");

        //Wenn man bei RN "testOrdner/" loescht, wird die Datei
        //in den Ordner darueber verschoben
        File RN = new File("testOrdner/testOrdner/testNeu.txt");

        System.out.println(f);

        //existiert object?
        System.out.println("Existiert: \t" + f.exists());

        //Lege Ordner an
        createDirectory.createDirectory(f);
        System.out.println("Absoluter Pfad: " + f.getAbsolutePath());

        //lege Datei an
        System.out.println(f2);
        createDirectory.createFile(f2);

        //rename/mv File/Dorectory
        rename.rename(f2.toPath(), RN.toPath());



        Path infile = Paths.get( "./testOrdner/testOrdner/test.txt" );
        Path outfile = Paths.get( "./testOrdner/testOrdner/testKopie.txt" );

        rename.rename(infile, outfile);

    }
}
