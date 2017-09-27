package htw.GUI;

public class FileObject {

    private String name;
    private boolean isFolder;

    public FileObject(String name, boolean isFolder) {
        this.name = name;
        this.isFolder = isFolder;
    }

    public String getName() {
        return name;
    }

    public boolean isFolder() {
        return isFolder;
    }

    @Override
    public String toString() {
        return name;
    }
}
