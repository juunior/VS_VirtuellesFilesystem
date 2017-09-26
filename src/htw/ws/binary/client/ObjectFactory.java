
package htw.ws.binary.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the htw.ws.binary.client package.
 * <p>An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _UploadResponse_QNAME = new QName("http://server.binary.ws.vsFS.net/", "uploadResponse");
    private final static QName _Download_QNAME = new QName("http://server.binary.ws.vsFS.net/", "download");
    private final static QName _DownloadResponse_QNAME = new QName("http://server.binary.ws.vsFS.net/", "downloadResponse");
    private final static QName _Upload_QNAME = new QName("http://server.binary.ws.vsFS.net/", "upload");
    private final static QName _UploadArg1_QNAME = new QName("", "arg1");
    private final static QName _DownloadResponseReturn_QNAME = new QName("", "return");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: htw.ws.binary.client
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Download }
     */
    public Download createDownload() {
        return new Download();
    }

    /**
     * Create an instance of {@link Upload }
     */
    public Upload createUpload() {
        return new Upload();
    }

    /**
     * Create an instance of {@link DownloadResponse }
     */
    public DownloadResponse createDownloadResponse() {
        return new DownloadResponse();
    }

    /**
     * Create an instance of {@link UploadResponse }
     */
    public UploadResponse createUploadResponse() {
        return new UploadResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadResponse }{@code >}}
     */
    @XmlElementDecl(namespace = "http://server.binary.ws.vsFS.net/", name = "uploadResponse")
    public JAXBElement<UploadResponse> createUploadResponse(UploadResponse value) {
        return new JAXBElement<UploadResponse>(_UploadResponse_QNAME, UploadResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Download }{@code >}}
     */
    @XmlElementDecl(namespace = "http://server.binary.ws.vsFS.net/", name = "download")
    public JAXBElement<Download> createDownload(Download value) {
        return new JAXBElement<Download>(_Download_QNAME, Download.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DownloadResponse }{@code >}}
     */
    @XmlElementDecl(namespace = "http://server.binary.ws.vsFS.net/", name = "downloadResponse")
    public JAXBElement<DownloadResponse> createDownloadResponse(DownloadResponse value) {
        return new JAXBElement<DownloadResponse>(_DownloadResponse_QNAME, DownloadResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Upload }{@code >}}
     */
    @XmlElementDecl(namespace = "http://server.binary.ws.vsFS.net/", name = "upload")
    public JAXBElement<Upload> createUpload(Upload value) {
        return new JAXBElement<Upload>(_Upload_QNAME, Upload.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "arg1", scope = Upload.class)
    public JAXBElement<byte[]> createUploadArg1(byte[] value) {
        return new JAXBElement<byte[]>(_UploadArg1_QNAME, byte[].class, Upload.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "return", scope = DownloadResponse.class)
    public JAXBElement<byte[]> createDownloadResponseReturn(byte[] value) {
        return new JAXBElement<byte[]>(_DownloadResponseReturn_QNAME, byte[].class, DownloadResponse.class, value);
    }

}
