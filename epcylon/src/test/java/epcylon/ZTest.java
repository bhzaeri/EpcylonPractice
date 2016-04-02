package epcylon;

import java.util.HashSet;
import java.util.Set;

public class ZTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(new ZTest().getD());
		Set<String> ss=new HashSet<String>();
		ss.add("1");
		ss.add("1");
		System.out.println("OK");
	}

	public synchronized Double getD() {
		return getF();
	}

	public synchronized Double getF() {
		return 10.0;
	}
}
