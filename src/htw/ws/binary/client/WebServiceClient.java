package htw.ws.binary.client;

import java.io.*;

/**
 * A client program that connects to a web service in order to upload
 * and download files.
 */
public class WebServiceClient {

    public static void main(String[] args) {
        // connects to the web service
        FileTransfererImplService client = new FileTransfererImplService();
        FileTransfererImpl service = client.getFileTransfererImplPort();

        String fileName = "binary.png";
        String filePath = "c:/Test/Client/Upload/" + fileName;
        File file = new File(filePath);

        // uploads a file
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream inputStream = new BufferedInputStream(fis);
            byte[] imageBytes = new byte[(int) file.length()];
            inputStream.read(imageBytes);

            service.upload(file.getName(), imageBytes);

            inputStream.close();
            System.out.println("File uploaded: " + filePath);
        } catch (IOException ex) {
            System.err.println(ex);
        }

        // downloads another file
        fileName = "camera.png";
        filePath = "c:/Test/Client/Download/" + fileName;
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