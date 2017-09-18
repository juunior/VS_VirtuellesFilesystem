package filesystem.GUI;

import filesystem.FileSystemManger;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import java.awt.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    public void home(ActionEvent actionEvent) {
    }

    public void refresh(ActionEvent actionEvent) {
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
    }

    public void createFile(ActionEvent actionEvent) {
    }

    public void rename(ActionEvent actionEvent) {
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

    public void showAbout(ActionEvent actionEvent) {
        Alert about = new Alert(Alert.AlertType.INFORMATION);
        about.setTitle("About");
        about.setHeaderText("Virtuelles Filesystem v9000");
        about.setContentText("\u00A9 2017 by richtig kuhle bois ey");
        about.showAndWait();
    }

    public void changeDirectoryManually(ActionEvent actionEvent) {
    }

    public void search(ActionEvent actionEvent) {
    }

    public void clearSelectedTab(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
