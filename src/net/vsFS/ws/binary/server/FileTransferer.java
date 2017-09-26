package net.vsFS.ws.binary.server;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * A web service endpoint interface.
 *
 *
 */
@WebService
public interface FileTransferer {
	@WebMethod
	public void upload(String fileName, byte[] imageBytes);
	
	@WebMethod
	public byte[] download(String fileName);	
}