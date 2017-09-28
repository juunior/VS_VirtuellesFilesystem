
package htw.ws.binary.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 */
@WebService(name = "FileTransfererImpl", targetNamespace = "http://server.binary.ws.htw/")
@XmlSeeAlso({
        ObjectFactory.class
})
public interface FileTransfererImpl {


    /**
     * @param arg0
     * @return returns byte[]
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "download", targetNamespace = "http://server.binary.ws.htw/", className = "htw.ws.binary.client.Download")
    @ResponseWrapper(localName = "downloadResponse", targetNamespace = "http://server.binary.ws.htw/", className = "htw.ws.binary.client.DownloadResponse")
    @Action(input = "http://server.binary.ws.htw/FileTransfererImpl/downloadRequest", output = "http://server.binary.ws.htw/FileTransfererImpl/downloadResponse")
    byte[] download(
            @WebParam(name = "arg0", targetNamespace = "")
                    String arg0);

    /**
     * @param arg1
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "upload", targetNamespace = "http://server.binary.ws.htw/", className = "htw.ws.binary.client.Upload")
    @ResponseWrapper(localName = "uploadResponse", targetNamespace = "http://server.binary.ws.htw/", className = "htw.ws.binary.client.UploadResponse")
    @Action(input = "http://server.binary.ws.htw/FileTransfererImpl/uploadRequest", output = "http://server.binary.ws.htw/FileTransfererImpl/uploadResponse")
    void upload(
            @WebParam(name = "arg0", targetNamespace = "")
                    String arg0,
            @WebParam(name = "arg1", targetNamespace = "")
                    byte[] arg1);

}