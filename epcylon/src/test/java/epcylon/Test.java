package epcylon;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.codehaus.jackson.map.ObjectMapper;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String sentence;
		String response;
		Socket clientSocket = null;
		try {
			// ObjectMapper mapper = new ObjectMapper();
			clientSocket = new Socket("137.207.234.97", 10000);
			// clientSocket = new Socket("127.0.0.1", 10000);

			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			sentence = "login iamprogrammerihavenolife";
			outToServer.writeBytes(sentence + '\n');
			response = inFromServer.readLine();
			System.out.println("FROM SERVER: " + response);

			sentence = "subscribe USD-JPY 1";
			outToServer.writeBytes(sentence + '\n');

			while (true) {
				response = inFromServer.readLine();
				if (response == null)
					break;
				// mapper.readValue(response, StockData.class);
				System.out.println("FROM SERVER: " + response);

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
