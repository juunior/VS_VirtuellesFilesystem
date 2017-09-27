package filesystem.GUI;

import filesystem.FileSystemManger;
import filesystem.Log.LogEntry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML
    public ImageView imageViewServerIndicator;
    public javafx.scene.control.Button clearSelTabBtn;
    public javafx.scene.control.Button deleteBtn;
    public javafx.scene.control.Button renameBtn;
    public javafx.scene.control.Button lockBtn;
    public Tab tabLog;
    @FXML
    public javafx.scene.control.Button createDirBtn;
    @FXML
    public javafx.scene.control.Button createFileBtn;
    @FXML
    private TableView<FileType> tableView;
    @FXML
    private TableView tableViewSearch;
    @FXML
    private ListView<LogEntry> listViewTabLog;
    @FXML
    private javafx.scene.control.TextField textFieldDirectory;
    @FXML
    private TextField textFieldSearch;
    @FXML
    private TableColumn<FileType, javafx.scene.image.Image> tableColumnIcon;
    @FXML
    private TableColumn<FileType, FileObject> tableColumnName;
    @FXML
    private TableColumn<FileType, String> tableColumnType;
    @FXML
    public TableColumn<SearchItem, Image> tableColumnSearchIcon;
    @FXML
    private TableColumn<SearchItem, String> tableColumnSearchName;
    @FXML
    private TableColumn<SearchItem, String> tableColumnSearchDirectory;
    @FXML
    private Tab tabSearch;
    @FXML
    private Tab tabServer;
    @FXML
    private TabPane tabPane;
    @FXML
    //private ListView<FileSystemServer> listViewTabServer;

    private ObservableList<FileType> currentDirectory = FXCollections.observableArrayList();
    private ObservableList<SearchItem> searchResults = FXCollections.observableArrayList();
    private ObservableList<LogEntry> logEntries = FXCollections.observableArrayList();
    //private ObservableList<FileSystemServer> serverEntrys = FXCollections.observableArrayList();

    public void changeDirectory (String directory) {
    }

    public void home(ActionEvent actionEvent)
    {
        changeDirectory("/");
    }

    public void refresh(ActionEvent actionEvent) {
        listDirectoryContent();
    }

    public void listDirectoryContent(){
        listDirectoryContent(true);
    }

    public void listDirectoryContent(boolean showErrorMessage){
    }

    public void close(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void showErrorMessage(Throwable e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(e.getClass().getSimpleName() + ": " + e.getMessage());
        alert.showAndWait();
    }

    public void mount(ActionEvent actionEvent) {
    }

    public void toggleLock(ActionEvent actionEvent) {
    }

    public void createDir(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create Folder");
        dialog.setHeaderText("Please enter a foldername");
        dialog.setContentText("Foldername:");

        Optional<String> result = dialog.showAndWait();
    }

    public void createFile(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create File");
        dialog.setHeaderText("Please enter a filename");
        dialog.setContentText("Filename:");

        Optional<String> result = dialog.showAndWait();
    }

    public FileType getSelectedFileType() {
        TablePosition position = tableView.getSelectionModel().getSelectedCells().get(0);
        int row = position.getRow();
        //TableColumn column = position.getTableColumn();
        //return column.getCellObservableValue(row).getValue();

        return currentDirectory.get(row);
    }

    public FileObject getSelectedCellContent() {
        return getSelectedFileType().fileNameProperty().getValue();
    }

    public void rename(ActionEvent actionEvent) {
        Object cellContent = getSelectedCellContent();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Rename File/Folder");
        dialog.setHeaderText("Please enter new name");
        dialog.setContentText("New name:");

        Optional<String> result = dialog.showAndWait();
    }

    public void delete(ActionEvent actionEvent) {
    }

    public void openHelp(ActionEvent actionEvent) {
        try {
            Desktop.getDesktop().open(FileSystemManger.getInstance().getHelpFile());
        } catch (Throwable e) {
            Alert io = new Alert(Alert.AlertType.ERROR);
            io.setTitle("Error");
            io.setContentText("Could not open Help PDF!");
            io.showAndWait();
        }
    }

    public void showAbout() {
        Alert about = new Alert(Alert.AlertType.INFORMATION);
        about.setTitle("About");
        about.setHeaderText("Virtuelles Filesystem v9000");
        about.setContentText("\u00A9 2017 by richtig kuhle bois ey");
        about.showAndWait();
    }

    public void changeDirectoryManually(ActionEvent actionEvent) {
        String dirStr = textFieldDirectory.getText();
        if (dirStr == null || dirStr.isEmpty()) return;
        changeDirectory(dirStr);
    }

    public void search(ActionEvent actionEvent) {

    }

    public void clearSelectedTab(ActionEvent actionEvent) {
        Tab currentTab = tabPane.getSelectionModel().getSelectedItem();
        if (currentTab.equals(tabLog)) {
            logEntries.clear();
        } else if (currentTab.equals(tabSearch)) {
            searchResults.clear();
        }
    }

    private void setServerStatusIndicator(ServerStatus status) {
        switch (status) {
            case RUNNING:
                imageViewServerIndicator.setImage(Resources.server_status_indicator_green);
                break;

            case STOPPED:
                imageViewServerIndicator.setImage(Resources.server_status_indicator_red);
                break;

            default:
                break;
        }
    }

    private void refreshServerList(){
        //serverEntrys.clear();
        //serverEntrys.addAll(FileSystemManger.getInstance().listAvailableFileSystemServers());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
