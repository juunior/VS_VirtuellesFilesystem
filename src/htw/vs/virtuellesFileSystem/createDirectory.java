package htw.vs.virtuellesFileSystem;

import java.io.File;
import java.io.IOException;

public class createDirectory {

    public static void createDirectory(File f) {
        if (!f.exists()) {
            //mkdir legt einen Ordner an mkdirs mehrere
            if (!f.mkdirs()) {
                System.out.println("Ordner " + f + " konnte nicht angelegt werden.");
            } else {
                System.out.println("Ordner " + f + " wurde angelegt.");
            }
        } else {
            System.out.println("Ordner " + f + " existiert bereits");
        }
    }

    public static void createFile(File f) {
        try {
            if (f.createNewFile()) {
                System.out.println("Datei " + f + " wurde angelegt.");
            } else {
                System.out.println("Datei " + f + " existiert bereits.");
            }
        } catch (IOException e) {
            System.err.println("Error creating " + f);
        }
    }

    public static void rename(File old, File RN) {
        //Egal ob Directory oder File
        //Ist auch gleichzeitig verschieben
        if (old.renameTo(RN)) {
            System.out.println("Umbennenung erfolgreich");
        }
    }


}
