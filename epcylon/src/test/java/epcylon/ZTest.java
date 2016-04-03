package epcylon;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class ZTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(new ZTest().getD());
		Set<String> ss = new HashSet<String>();
		ss.add("1");
		ss.add("1");
		System.out.println("OK");

		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(2001);
			Socket socket = serverSocket.accept();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (serverSocket != null)
				try {
					serverSocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	public synchronized Double getD() {
		return getF();
	}

	public synchronized Double getF() {
		return 10.0;
	}
}
