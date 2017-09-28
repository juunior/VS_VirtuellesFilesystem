package htw.vs.virtuellesFileSystem;

import com.sun.media.sound.EmergencySoundbank;
import htw.Main;
import htw.ws.binary.client.WebServiceClient;
import org.apache.commons.io.filefilter.TrueFileFilter;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.File;
import java.io.FileNotFoundException;
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
        File directory = new File(Main.rootPath);
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

    public static void handleEvents(WatchService watcher) {
//        WatchKey watchKey;
//        Thread thread = new Thread(() -> {


        while (true) {
            WatchKey watchKey = watcher.poll();
            //Wenn Ereignis geworfen wird, gebe Ereignis aus und fuehre erneut Registrierung der Ordner und Files durch
            if (watchKey != null) {
                watchKey.pollEvents().stream()
                        .forEach(e -> System.out.println(e.context() + " (" + e.kind() + ")"));
                watchKey.reset();
                //TODO:Neuerstellung der XML nur bei CREATE und DELETE, nicht bei MODIFY
                //if ((watchKey.pollEvents().stream().toString().equals("ENTRY_CREATE")) || (watchKey.pollEvents().stream().toString().equals("ENTRY_DELETE"))) {
                    try {
                        xmlPathCreate.createXML("c:\\VS1");
                        WebServiceClient.communication(true, xmlPathCreate.detectOS());
                    } catch (FileNotFoundException | NotDirectoryException e) {
                        e.printStackTrace();
                    }
                    registerDirs(watcher);
                }
            try {
                //An dieser Stelle das Intervall bestimmen, in dem auf Aenderungen geprueft werden soll
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //      }
        }
//        thread.start();
    }

    public static void startWatcher() {
        WatchService watcher = initWatchkeyService();
        registerDirs(watcher);
        handleEvents(watcher);
    }
}
