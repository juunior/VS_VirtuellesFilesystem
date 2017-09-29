package htw;

import htw.vs.virtuellesFileSystem.DirWatchService;
import htw.vs.virtuellesFileSystem.xmlPathCreate;
import htw.ws.binary.client.WebServiceClient;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.FileNotFoundException;
import java.nio.file.NotDirectoryException;

import static htw.vs.virtuellesFileSystem.Main.askPaths;

public class Main {

    //Dieser rootPath fällt weg, falls Kais Methode dafür verwendet wird
    public static String rootPath = "";

    public static void main(String args[]) {
        //xmlPathCreate.detectOS();
        rootPath = "/home/wolf/Documents/";
        try {
            xmlPathCreate.createXML(rootPath);
        } catch (FileNotFoundException | NotDirectoryException e) {
            e.printStackTrace();
        }
        //DirectoryWatcher starten, bereits bekannte Pfade initialisieren und Funktionalität zum automatischen Hinzufügen neuer Directories und Files starten
        DirWatchService.startWatcher();
    }
}