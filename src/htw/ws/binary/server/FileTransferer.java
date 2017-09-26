package htw.ws.binary.server;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * A web service endpoint interface.
 */
@WebService
public interface FileTransferer {
    @WebMethod
    void upload(String fileName, byte[] imageBytes);

    @WebMethod
    byte[] download(String fileName);
}