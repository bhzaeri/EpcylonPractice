package epcylon;

public class ZTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(new ZTest().getD());
	}

	public synchronized Double getD() {
		return getF();
	}

	public synchronized Double getF() {
		return 10.0;
	}
}
