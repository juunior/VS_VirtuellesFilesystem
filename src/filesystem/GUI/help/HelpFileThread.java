package filesystem.GUI.help;

import filesystem.FileSystemManger;
import filesystem.GUI.Controller;
import filesystem.GUI.Tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class HelpFileThread extends Thread {

    private static final String PATH_TO_PDF = "resources/GUIHelp.pdf";

    @Override
    public void run() {

        try {
            InputStream inputStream = Controller.class.getResourceAsStream(PATH_TO_PDF);
            File tmpFile = File.createTempFile("helpVFiles", ".pdf");
            FileOutputStream outputStream = new FileOutputStream(tmpFile);
            Tools.copyStream(inputStream, outputStream);
            inputStream.close();
            outputStream.close();
            outputStream.close();
            FileSystemManger.getInstance().setHelpFile(tmpFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}