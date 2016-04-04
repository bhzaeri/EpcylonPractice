package epcylon;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class ZTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Double d = 0.3446;
		BigDecimal b = new BigDecimal(d);
		b = b.setScale(2, BigDecimal.ROUND_HALF_DOWN);

		System.out.println(new ZTest().getD());
		Set<String> ss = new HashSet<String>();
		ss.add("1");
		ss.add("1");
		System.out.println("OK ---- " + b.doubleValue());

	}

	public synchronized Double getD() {
		return getF();
	}

	public synchronized Double getF() {
		return 10.0;
	}
}
