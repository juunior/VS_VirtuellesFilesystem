package htw.vs.virtuellesFileSystem;

import java.nio.file.*;
import java.io.IOException;

public class rename {

    /**
     * benennt datei um
     * @param source
     * @param newName
     * @throws IOException
     */
    public static void rename (Path source, String newName) throws IOException {
        Files.move(source, source.resolve(newName));
    }

    /**
     * verschiebt Datei in anderen Ordner
     * @param source
     * @param dest
     * @throws IOException
     */
    public static void moveTo (Path source, Path dest) throws IOException {
        Files.move(source, dest.resolve(source.getFileName()));
    }
}
