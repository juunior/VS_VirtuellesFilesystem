import org.apache.commons.io.filefilter.TrueFileFilter;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Collection;

public class DirWatchService {

    public static void main(String[] args) {
        try {
            WatchService watcher = FileSystems.getDefault().newWatchService();
            Path testDir = Paths.get("C:\\VS1");
            File directory = new File("C:\\VS1");
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
            WatchKey watchKey = null;
            while (true) {
                watchKey = watcher.poll();
                if (watchKey != null) {
                    watchKey.pollEvents().stream()
                            .forEach(e -> System.out.println(e.context() + " (" + e.kind() + ")"));
                    watchKey.reset();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}