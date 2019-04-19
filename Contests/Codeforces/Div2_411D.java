import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Div2_411D {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String line = reader.readLine();
		reader.close();

		int bLen = 0;
		int oCnt = 0;
		for (int i = line.length() - 1; i >= 0; i--) {
			if (line.charAt(i) == 'a') {
				oCnt += bLen;
				if (oCnt >= 1_000_000_007) {
					oCnt -= 1_000_000_007;
				}

				bLen <<= 1;
				if (bLen >= 1_000_000_007) {
					bLen -= 1_000_000_007;
				}
			} else {
				bLen++;
				if (bLen >= 1_000_000_007) {
					bLen -= 1_000_000_007;
				}
			}
		}
		System.out.println(oCnt);
	}
}
