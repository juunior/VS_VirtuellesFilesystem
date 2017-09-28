package htw.ws.binary.server;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import javax.xml.ws.Endpoint;
import java.io.File;
import java.io.IOException;

import static com.google.common.io.Files.asByteSource;

/**
 * A simple web service server.
 *
 *
 */
public class WebServiceServer {

	public static void main(String[] args) {
		String bindingURI = "http://192.168.15.1/vsFS/fileService";
		FileTransferer service = new FileTransfererImpl();
		Endpoint.publish(bindingURI, service);
		System.out.println("Server started at: " + bindingURI);
	}

	public static boolean hash(HashCode message) {
		File file = new File("xmlTest.xml");
		HashFunction hf = Hashing.sha256();
		HashCode hc = null;
		try {
			hc = asByteSource(file).hash(hf);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return message.equals(hc);
	}
}