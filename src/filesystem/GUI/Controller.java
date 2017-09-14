package filesystem.GUI;

import filesystem.FileSystemManger;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import java.util.List;

public class Controller {

    public void close(ActionEvent actionEvent) {
        System.exit(0);
    }

    /*public void search() {
        String searchStr = textFieldSearch.getText();
        if (searchStr == null || searchStr.isEmpty()) return;

        searchResults.clear();
        List<FSObject> list;
        try {
            list = fileSystem.search(searchStr);
            for (FSObject fsObject : list) {

                searchResults.add(
                        new SearchItem(fsObject.getName(), (fsObject instanceof Folder), fsObject.getPermissions(), fsObject.getParentFolder().getAbsolutePath()));
            }
        } catch (Throwable e) {
            if (FileSystemManger.DEBUG) {
                e.printStackTrace();
            }
            showErrorMessage(e);
        }
        tableViewSearch.setItems(searchResults);
        tabPane.getSelectionModel().select(tabSearch);
    }*/

    public void showErrorMessage(Throwable e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(e.getClass().getSimpleName() + ": " + e.getMessage());
        alert.showAndWait();
    }

}
