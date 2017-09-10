import java.io.File;
import java.io.IOException;

public class createDirectory {

    public static void createDirectory(File f) {
        if (f.exists() == false) {
            //mkdir legt einen Ordner an mkdirs mehrere
            if (f.mkdirs() == false) {
                System.out.println("Ordner " + f + " konnte nicht angelegt werden.");
            } else {
                System.out.println("Ordner " + f + " wurde angelegt.");
            }
        } else {
            System.out.println("Ordner " + f + " existiert bereits");
        }
    }

    public static void createFile(File f) {
        if (f.exists() == false) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                System.err.println("Error creating " + f);
            }

            System.out.println("Datei " + f + " wurde angelegt.");
        } else {
            System.out.println("Datei " + f + " existiert bereits.");
        }

    }

    public static void rename(File old, File RN) {
        //Egal ob Directory oder File
        //Ist auch gleichzeitig verschieben
        old.renameTo(RN);
    }

    public static void main(String[] args) {
        //lege object vom typ datei an
        File f = new File("./testOrdner/testOrdner");
        File f2 = new File("testOrdner/testOrdner/test.txt");
        //Wenn man bei RN "testOrdner/" loescht, wird die Datei
        //in den Ordner darueber verschoben
        File RN = new File("testOrdner/testOrdner/testNeu.txt");

        System.out.println(f);

        //existiert object?
        System.out.println(f.exists());

        //Lege Ordner an
        createDirectory(f);
        System.out.println("Absoluter Pfad: " + f.getAbsolutePath());

        //lege Datei an
        System.out.println(f2);
        createFile(f2);

        //rename/mv File/Dorectory
        rename(f2, RN);

    }
}
