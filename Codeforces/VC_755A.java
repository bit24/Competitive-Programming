import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class VC_755A {

	public static void main(String[] args) throws IOException {
		new VC_755A().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(reader.readLine());

		for (int m = 1; m <= 1000; m++) {
			if (!isPrime(n * m + 1)) {
				System.out.println(m);
				return;
			}
		}
	}

	boolean isPrime(int x) {
		if (x != 2 && (x & 1) == 0) {
			return false;
		}

		for (int j = 3; j * j <= x; j += 2) {
			if (x % j == 0) {
				return false;
			}
		}
		return true;
	}

}
