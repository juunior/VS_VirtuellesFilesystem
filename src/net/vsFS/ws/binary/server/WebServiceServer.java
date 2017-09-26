package net.vsFS.ws.binary.server;

import javax.xml.ws.Endpoint;

/**
 * A simple web service server.
 *
 *
 */
public class WebServiceServer {

	public static void main(String[] args) {
		String bindingURI = "http://localhost:9898/vsFS/fileService";
		FileTransferer service = new FileTransfererImpl();
		Endpoint.publish(bindingURI, service);
		System.out.println("Server started at: " + bindingURI);
	}
}