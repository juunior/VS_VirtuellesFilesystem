package filesystem;

import htw.vs.virtuellesFileSystem.DirWatchService;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import static htw.vs.virtuellesFileSystem.Directory_Structure.askPaths;

public class Main extends Application {

    //Dieser rootPath fällt weg, falls Kais Methode dafür verwendet wird
    public static String rootPath = null;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //(new HelpFileThread()).start(); // load to help file from the resource folder to the local temp folder.

        Parent root = FXMLLoader.load(getClass().getResource("/filesystem/GUI/sample.fxml"));
        primaryStage.setTitle("Virtuelles Filesystem");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                FileSystemManger.getInstance().close();
            }
        });
    }

    public static void launchGui(String args[]) {
        if (args.length > 2) {
            //vs1.usage();
        }
        String mode = "client";
        if (args.length > 1) {
            // second parameter equals "s"
            if (args[1].equals("s")) {
                mode = "server";
            }
        } else if (args.length == 1) {
            // only one parameter -> default
            mode = "default";
        }

        /*switch (mode) {
            case "client":
                FileSystemManger.getInstance().initClientOnlyMode();
                break;

            case "server":
                FileSystemManger.getInstance().initServerOnlyMode(args[0], TCPParallelServer.DEFAULT_PORT);
                break;

            default:
                FileSystemManger.getInstance().init(args[0], TCPParallelServer.DEFAULT_PORT);
        }*/


        launch(args);
    }


    public static void main(String args[]) {

        //Es müssen noch die Buttons der GUI angepasst werden

        //Erst GUI starten
        launchGui(args);
        //An dieser Stelle entweder askPaths oder Kais Zeug zum Pfade abfragen
        //Falls Kais Zeug verwendet wird, globale Variable rootPath evtl. löschen
        rootPath = askPaths();
        //Hier Platzhalter für das Einlesen der Directory Informationen
        //DirectoryWatcher starten, bereits bekannte Pfade initialisieren und Funktionalität zum automatischen Hinzufügen neuer Directories und Files starten
        DirWatchService.startWatcher();

    }
}