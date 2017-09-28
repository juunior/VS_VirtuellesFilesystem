import htw.vs.virtuellesFileSystem.DirectoryStructure;
import org.apache.commons.io.filefilter.TrueFileFilter;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Collection;

public class DirWatchService {

    //Methode zur Inititalisierung des Watcher-Dienstes
    public static WatchService initWatchkeyService() {
        WatchService watcher = null;
        try {
            watcher = FileSystems.getDefault().newWatchService();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return watcher;
    }

    //Methode, um Directories und Files in die Liste der zu ueberwachenden Objekte zu nehmen
    public static void registerDirs(WatchService watcher) {
        File directory = new File(DirectoryStructure.rootPath);
        Collection<File> collection = org.apache.commons.io.FileUtils.listFilesAndDirs(directory, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
        for (File f : collection) {
            Path tempPath = f.toPath();
            try {
                if (!(f.isFile())) {
                    tempPath.register(watcher, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static WatchService handleEvents(WatchService watcher) {
            WatchKey watchKey = null;
        while (true) {
            watchKey = watcher.poll();
            //Wenn Ereignis geworfen wird, gebe Ereignis aus und fuehre erneut Registrierung der Ordner und Files durch
            if (watchKey != null) {
                watchKey.pollEvents().stream()
                        .forEach(e -> System.out.println(e.context() + " (" + e.kind() + ")"));
                watchKey.reset();
                registerDirs(watcher);
            }
            try {
                //An dieser Stelle das Intervall bestimmen, in dem auf Aenderungen geprueft werden soll
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void startWatcher() {
        WatchService watcher = initWatchkeyService();
        registerDirs(watcher);
        handleEvents(watcher);
    }
}
