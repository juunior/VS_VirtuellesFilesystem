package htw.ws.binary.client;

import htw.Main;
import htw.vs.virtuellesFileSystem.xmlPathCreate;

import java.io.*;

/**
 * A client program that connects to a web service in order to upload
 * and download files.
 */
public class WebServiceClient {

    //if action == true -> upload
    public static void communication(boolean action, String filePath) {
        // connects to the web service
        FileTransfererImplService client = new FileTransfererImplService();
        FileTransfererImpl service = client.getFileTransfererImplPort();
        String fileName = "xmlTest.xml";
        filePath+=fileName;
        if (action) {
            //uploads a file
            File file = new File(filePath);
            try {
                FileInputStream fis = new FileInputStream(file);
                BufferedInputStream inputStream = new BufferedInputStream(fis);
                byte[] fileBytes = new byte[(int) file.length()];
                inputStream.read(fileBytes);

                service.upload(file.getName(), fileBytes);

                inputStream.close();
                System.out.println("File uploaded: " + filePath);
            } catch (IOException ex) {
                System.err.println(ex);
            }
        } else {
            // downloads another file

            byte[] fileBytes = service.download(fileName);
            try {
                FileOutputStream fos = new FileOutputStream(filePath);
                BufferedOutputStream outputStream = new BufferedOutputStream(fos);
                outputStream.write(fileBytes);
                outputStream.close();

                System.out.println("File downloaded: " + filePath);
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }
}