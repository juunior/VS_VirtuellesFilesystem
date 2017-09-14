package filesystem;

import filesystem.GUI.help.HelpFileThread;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception{
        (new HelpFileThread()).start(); // load to help file from the resource folder to the local temp folder.

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Virtual Filesystem");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                FileSystemManger.getInstance().close();
            }
        });
    }
}
