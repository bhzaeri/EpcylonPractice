package epcylon;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.codehaus.jackson.map.ObjectMapper;

public class PracticeTest {

	@org.junit.Test
	public void main() {
		// TODO Auto-generated method stub
		String sentence;
		String response;
		Socket clientSocket = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			clientSocket = new Socket("practiceproblem.epcylon.com", 80);
			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			sentence = "login fiInKuFbMzUQtqiCXfJbuowMgFEzJcguLXMirmsfGjfsJMdF";
			outToServer.writeBytes(sentence + '\n');
			response = inFromServer.readLine();
			System.out.println("FROM SERVER: " + response);

			sentence = "subscribe EUR-USD";
			outToServer.writeBytes(sentence + '\n');
			System.out.println("Start liste...");
			for (int i = 0; i < 10; i++) {
				response = inFromServer.readLine();
				// mapper.readValue(response, StockData.class);
				System.out.println(i + " FROM SERVER: " + response);

				// Thread.sleep(30000);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (clientSocket != null) {
					clientSocket.close();
					System.out.println("Connection closed!");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
