package htw;

import htw.Log.LogSubscriber;
import htw.Log.NetworkLog;

import java.io.File;

public class FileSystemManger {

    private File helpFile = null;

    private NetworkLog networkLog;

    private static FileSystemManger INSTANCE = new FileSystemManger();

    public static FileSystemManger getInstance() {
        return INSTANCE;
    }

    public void close() {

    }

    public File getHelpFile() {
        return helpFile;
    }

    public void setHelpFile(File helpFile) {
        this.helpFile = helpFile;
    }

    public void addNetworkLogSubscriber(LogSubscriber subscriber) {
        networkLog.addSubscriber(subscriber);
    }

}
