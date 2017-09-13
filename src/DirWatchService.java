import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class DirWatchService {

    public static void main(String[] args) {
        try {
            WatchService watcher = FileSystems.getDefault().newWatchService();
            Path testDir = Paths.get("test/");
            testDir.register(watcher, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);
            WatchKey watchKey = null;
            while (true) {
                watchKey = watcher.poll();
                if (watchKey != null) {
                    watchKey.pollEvents().stream().forEach(e -> System.out.println(e.context() + " (" + e.kind() + ")"));
                    watchKey.reset();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}